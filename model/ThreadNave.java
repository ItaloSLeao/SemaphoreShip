/****************************************************************
* Autor............: Italo de Souza Leao
* Matricula........: 202410120
* Inicio...........: 30/06/2025
* Ultima alteracao.: 05/07/2025
* Nome.............: ThreadNave.java
* Funcao...........: Classe que controla todo a dinamica de movimento das Naves, por meio de uma thread. 
* Essa classe define a imagem da Nave, a velocidade, o percurso pre-definido, o slider dela no ControllerTelaPrincipal, 
* bem como controla os botoes que controlam a pausa e a retomada de cada thread da nave.
*****************************************************************/

package model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.control.Slider;
import javafx.scene.shape.Circle;

public class ThreadNave extends Thread {

  private class TimerNave extends AnimationTimer{

    private ThreadNave nave;

    public TimerNave(ThreadNave nave){
      this.nave = nave;
    } //Fim do construtor TimerNave

    @Override
    /****************************************************************
    * Metodo: handle
    * Funcao: Metodo que eh invocado a cada frame da animacao chamando 
    * o metodo moverNave para controlar o movimento da nave
    * Parametros: long now, o tempo atual do frame
    * Retorno: void
    ****************************************************************/
    public void handle(long now) {
      Platform.runLater(() -> {nave.moverNave();}); //Fim do Platform.runLater
    } //Fim handle

  } //Fim TimerNave

  private final ImageView imagemNave;
  private double velocidade = 0.0;
  private final int percurso;
  private final Slider slider;
  private final Circle[] circulos;
  private int indicePontoAtual = 0;
  private Semaphore semaforoRuaAtual = null;
  private Semaphore semaforoEsquinaAtual = null;
  private boolean zonaInferiorAtual = false;

  private static final double[] OFFSET_X = {0, 25, 34, 34, 22, 25, 23, 25, 22};
  private static final double[] OFFSET_Y = {0, 28, 28, 27, 27, 28, 28, 30, 29};
  private static final Semaphore SEMAFORO_ZONA_INFERIOR = new Semaphore(1, true);
  private static final int[] POSICOES_ATUAIS = new int[9];

  private static final double[][] PONTOS = {
    {},
    {140, 63}, {140, 173}, {140, 292}, {140, 404}, {140, 525}, {140, 638},
    {257, 638}, {368, 638}, {485, 638}, {600, 638}, {719, 638},
    {719, 525}, {719, 404}, {719, 292}, {719, 173}, {719, 63},
    {600, 63}, {485, 63}, {368, 62}, {257, 62},
    {257, 173}, {257, 294}, {257, 409}, {257, 525},
    {368, 525}, {485, 525}, {600, 525},
    {600, 409}, {600, 292}, {603, 173},
    {485, 173}, {368, 173}, {368, 292}, {368, 409}, {485, 409}, {485, 293}
  };

  private static final int[] ROTA_1 = {1, 20, 19, 32, 31, 18, 17, 16, 15, 14, 13, 12, 11, 10, 27, 28, 35, 36, 33, 34, 23, 24, 7, 6, 5, 4, 3, 2};
  private static final int[] ROTA_2 = {11, 10, 9, 8, 7, 6, 5, 24, 25, 26, 27, 28, 35, 34, 23, 4, 3, 2, 1, 20, 19, 18, 17, 16, 15, 30, 31, 32, 21, 22, 33, 36, 29, 14, 13, 12};
  private static final int[] ROTA_3 = {14, 29, 36, 33, 22, 3, 4, 5, 24, 25, 26, 27, 12, 13};
  private static final int[] ROTA_4 = {20, 21, 22, 23, 24, 7, 8, 9, 26, 35, 36, 31, 18, 19};
  private static final int[] ROTA_5 = {18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 26, 35, 36, 31};
  private static final int[] ROTA_6 = {8, 9, 10, 27, 28, 29, 30, 17, 18, 19, 32, 33, 34, 25};
  private static final int[] ROTA_7 = {27, 28, 13, 14, 29, 30, 31, 18, 19, 32, 21, 22, 3, 4, 23, 24, 25, 8, 9, 26};
  private static final int[] ROTA_8 = {22, 23, 34, 25, 26, 9, 10, 11, 12, 13, 28, 29, 36, 31, 32, 19, 20, 1, 2, 3};

  private static final Semaphore[] SEMAFOROS_ESQUINAS = new Semaphore[37];
  private static final Map<String, Semaphore> SEMAFOROS_RUAS = new HashMap<>();

  static {
    for(int i = 1; i < SEMAFOROS_ESQUINAS.length; i++){
      SEMAFOROS_ESQUINAS[i] = new Semaphore(1, true);
    }

    cadastrarRota(ROTA_1);
    cadastrarRota(ROTA_2);
    cadastrarRota(ROTA_3);
    cadastrarRota(ROTA_4);
    cadastrarRota(ROTA_5);
    cadastrarRota(ROTA_6);
    cadastrarRota(ROTA_7);
    cadastrarRota(ROTA_8);
  }

  AnimationTimer timerDeMovimento = new TimerNave(this);

  public ThreadNave(ImageView imagemNave, int percurso, Slider slider, Circle[] circulos) {
    this.imagemNave = imagemNave;
    this.percurso = percurso;
    this.slider = slider;
    this.circulos = circulos == null ? new Circle[0] : circulos.clone();
    this.velocidade = slider.getValue();
    POSICOES_ATUAIS[this.percurso] = getRota()[indicePontoAtual];
    ocuparEsquinaAtual();
  } //Fim do construtor de ThreadNave

  /****************************************************************
  * Metodo: reposicionar
  * Funcao: Metodo que faz o posicionamento inicial das naves de acordo com sua imagem e seu percurso 
  * Parametros: nenhum
  * Retorno: void
  ****************************************************************/
  public void reposicionar(){
    liberarRuaAtual();
    liberarEsquinaAtual();
    liberarZonaInferior();
    indicePontoAtual = 0;

    switch(this.percurso){
    case 1: //Caso em que o percurso da nave eh o primeiro
      imagemNave.setLayoutX(115);
      imagemNave.setLayoutY(35);
      imagemNave.setRotate(90.0);
      break;
    case 2: //Caso em que eh o segundo
      imagemNave.setLayoutX(685); 
      imagemNave.setLayoutY(610);
      imagemNave.setRotate(270.0);
      break;
    case 3: //Caso em que eh o terceiro
      imagemNave.setLayoutX(685); 
      imagemNave.setLayoutY(265);
      imagemNave.setRotate(270.0);
      break;
    case 4: //Caso em que eh o quarto
      imagemNave.setLayoutX(235);
      imagemNave.setLayoutY(35);
      imagemNave.setRotate(180.0);
      break;
    case 5: //Caso em que eh o quinto
      imagemNave.setLayoutX(460);
      imagemNave.setLayoutY(35);
      imagemNave.setRotate(90.0);
      break;
    case 6: //Caso em que eh o sexto
      imagemNave.setLayoutX(345); 
      imagemNave.setLayoutY(610);
      imagemNave.setRotate(90.0);
      break;
    case 7: //Caso em que eh o setimo
      imagemNave.setLayoutX(575); 
      imagemNave.setLayoutY(495);
      imagemNave.setRotate(0);
      break;
    case 8: //Caso em que eh o oitavo
      imagemNave.setLayoutX(235); 
      imagemNave.setLayoutY(265);
      imagemNave.setRotate(180.0);
      break;
    default: //Qualquer outro caso fora desse intervalo
      System.out.println("ERRO NO METODO reposicionar DA CLASSE ThreadNave");
      break;
    } //Fim switch-case

    POSICOES_ATUAIS[this.percurso] = getRota()[indicePontoAtual];
    ocuparEsquinaAtual();

  } //Fim reposicionar

  /****************************************************************
  * Metodo: getTimerDeMovimento
  * Funcao: Metodo que retorna o timer de movimento da nave para ser controlado na tela
  * Parametros: nenhum
  * Retorno: AnimationTimer, o timer de movimento da nave
  ****************************************************************/
  public AnimationTimer getTimerDeMovimento(){
    return timerDeMovimento;
  } //Fim getTimerDeMovimento

  @Override
  /****************************************************************
  * Metodo: run
  * Funcao: Metodo que eh chamado quando a thread eh iniciada, iniciando o timer de movimento da nave
  * Parametros: void
  * Retorno: void
  ****************************************************************/
  public void run() {
    timerDeMovimento.start();
  } //Fim run

  /****************************************************************
  * Metodo: moverNave
  * Funcao: Metodo que controla o movimento da nave
  * Parametros: nenhum
  * Retorno: void
  ****************************************************************/
  public void moverNave() {

    this.setVelocidade(slider.getValue() * 0.025); //Definicao da velocidade do nave 1 para o valor do slider de velocidade com ajuste escalar para 2,5% da velocidade original

    if(this.getVelocidade() <= 0){
      return;
    }

    int[] rota = getRota();
    int pontoAtual = rota[indicePontoAtual];
    int proximoIndice = (indicePontoAtual + 1) % rota.length;
    int pontoDestino = rota[proximoIndice];

    if(precisaZonaInferior(pontoAtual, pontoDestino) && !zonaInferiorAtual){
      if(!podeTentarZonaInferior() || !SEMAFORO_ZONA_INFERIOR.tryAcquire()){
        return;
      }
      zonaInferiorAtual = true;
    }

    if(semaforoRuaAtual == null){
      Semaphore semaforoDaRua = getSemaforoRua(pontoAtual, pontoDestino);
      Semaphore semaforoDaEsquinaDestino = getSemaforoEsquina(pontoDestino);

      if(!semaforoDaRua.tryAcquire()){
        return;
      }

      if(!semaforoDaEsquinaDestino.tryAcquire()){
        semaforoDaRua.release();
        return;
      }

      liberarEsquinaAtual();
      semaforoRuaAtual = semaforoDaRua;
      semaforoEsquinaAtual = semaforoDaEsquinaDestino;
    }

    if(vaiChegarNoPonto(pontoDestino)){
      posicionarNoPonto(pontoDestino);
      liberarRuaAtual();
      indicePontoAtual = proximoIndice;
      POSICOES_ATUAIS[this.percurso] = pontoDestino;
      if(zonaInferiorAtual && !pontoNaZonaInferior(pontoDestino)){
        liberarZonaInferior();
      }
      return;
    }

    moverAtePonto(pontoDestino);
  } //Fim moverNave

  private int[] getRota(){
    switch(this.percurso){
    case 1:
      return ROTA_1;
    case 2:
      return ROTA_2;
    case 3:
      return ROTA_3;
    case 4:
      return ROTA_4;
    case 5:
      return ROTA_5;
    case 6:
      return ROTA_6;
    case 7:
      return ROTA_7;
    case 8:
      return ROTA_8;
    default:
      return ROTA_1;
    }
  }

  private void moverAtePonto(int ponto){
    double destinoX = getLayoutXDoPonto(ponto);
    double destinoY = getLayoutYDoPonto(ponto);
    double distanciaX = destinoX - this.getImagemNave().getLayoutX();
    double distanciaY = destinoY - this.getImagemNave().getLayoutY();
    double deslocamento = this.getVelocidade();

    if(Math.abs(distanciaX) <= deslocamento && Math.abs(distanciaY) <= deslocamento){
      this.getImagemNave().setLayoutX(destinoX);
      this.getImagemNave().setLayoutY(destinoY);
      return;
    }

    if(Math.abs(distanciaX) >= Math.abs(distanciaY)){
      this.getImagemNave().setRotate(distanciaX > 0 ? 90.0 : 270.0);
      this.getImagemNave().setLayoutX(this.getImagemNave().getLayoutX() + Math.copySign(Math.min(deslocamento, Math.abs(distanciaX)), distanciaX));
      if(Math.abs(distanciaY) <= deslocamento){
        this.getImagemNave().setLayoutY(destinoY);
      }
    } else{
      this.getImagemNave().setRotate(distanciaY > 0 ? 180.0 : 0.0);
      this.getImagemNave().setLayoutY(this.getImagemNave().getLayoutY() + Math.copySign(Math.min(deslocamento, Math.abs(distanciaY)), distanciaY));
      if(Math.abs(distanciaX) <= deslocamento){
        this.getImagemNave().setLayoutX(destinoX);
      }
    }
  }

  private boolean vaiChegarNoPonto(int ponto){
    return Math.abs(this.getImagemNave().getLayoutX() - getLayoutXDoPonto(ponto)) <= this.getVelocidade() &&
           Math.abs(this.getImagemNave().getLayoutY() - getLayoutYDoPonto(ponto)) <= this.getVelocidade();
  }

  private void posicionarNoPonto(int ponto){
    this.getImagemNave().setLayoutX(getLayoutXDoPonto(ponto));
    this.getImagemNave().setLayoutY(getLayoutYDoPonto(ponto));
  }

  private double getLayoutXDoPonto(int ponto){
    return PONTOS[ponto][0] - OFFSET_X[this.percurso];
  }

  private double getLayoutYDoPonto(int ponto){
    return PONTOS[ponto][1] - OFFSET_Y[this.percurso];
  }

  private boolean intersectaCirculo(int ponto){
    if(ponto < 1 || ponto > circulos.length || circulos[ponto - 1] == null){
      return true;
    }

    Bounds boundsCirculoNoPai = circulos[ponto - 1].localToParent(circulos[ponto - 1].getBoundsInLocal());
    Bounds boundsCirculoNaNave = this.getImagemNave().parentToLocal(boundsCirculoNoPai);
    return this.getImagemNave().intersects(boundsCirculoNaNave);
  }

  private void liberarRuaAtual(){
    if(semaforoRuaAtual != null){
      semaforoRuaAtual.release();
      semaforoRuaAtual = null;
    }
  }

  private void liberarEsquinaAtual(){
    if(semaforoEsquinaAtual != null){
      semaforoEsquinaAtual.release();
      semaforoEsquinaAtual = null;
    }
  }

  private void ocuparEsquinaAtual(){
    Semaphore semaforo = getSemaforoEsquina(getRota()[indicePontoAtual]);
    if(semaforo.tryAcquire()){
      semaforoEsquinaAtual = semaforo;
    }
  }

  private boolean precisaZonaInferior(int pontoAtual, int pontoDestino){
    return pontoNaZonaInferior(pontoAtual) || pontoNaZonaInferior(pontoDestino);
  }

  private boolean pontoNaZonaInferior(int ponto){
    return ponto == 7 || ponto == 8 || ponto == 9 || ponto == 10 || ponto == 11 || ponto == 12 ||
           ponto == 24 || ponto == 25 || ponto == 26 || ponto == 27 || ponto == 28;
  }

  private boolean podeTentarZonaInferior(){
    int minhaPrioridade = getPrioridadeZonaInferior(this.percurso);
    for(int i = 1; i < POSICOES_ATUAIS.length; i++){
      if(i != this.percurso && pontoNaZonaInferior(POSICOES_ATUAIS[i]) && getPrioridadeZonaInferior(i) < minhaPrioridade){
        return false;
      }
    }
    return true;
  }

  private int getPrioridadeZonaInferior(int percurso){
    switch(percurso){
    case 7:
      return 1;
    case 6:
      return 2;
    case 2:
      return 3;
    default:
      return 4 + percurso;
    }
  }

  private void liberarZonaInferior(){
    if(zonaInferiorAtual){
      SEMAFORO_ZONA_INFERIOR.release();
      zonaInferiorAtual = false;
    }
  }

  private static Semaphore getSemaforoRua(int pontoA, int pontoB){
    String chave = getChaveRua(pontoA, pontoB);
    synchronized(SEMAFOROS_RUAS){
      Semaphore semaforo = SEMAFOROS_RUAS.get(chave);
      if(semaforo == null){
        semaforo = new Semaphore(1, true);
        SEMAFOROS_RUAS.put(chave, semaforo);
      }
      return semaforo;
    }
  }

  private static Semaphore getSemaforoEsquina(int ponto){
    return SEMAFOROS_ESQUINAS[ponto];
  }
