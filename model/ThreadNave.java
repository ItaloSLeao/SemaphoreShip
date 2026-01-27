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
