/*****************************************************************
* Autor............: Italo de Souza Leao
* Matricula........: 202410120
* Inicio...........: 01/07/2025
* Ultima alteracao.: 05/07/2025
* Nome.............: ControllerTelaPrincipal.java
* Funcao...........: Controla a tela principal da aplicacao, onde, nesta: realiza a chamada das threads de movimentacao das naves; 
* controla: as velocidades destas, dinamicamente; as funcoes de: fechar a aplicacao e resetar as posicoes e as velocidades, 
* de pausar a movimentacao das naves, de visualizar o percurso das naves
*****************************************************************/

package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import model.ThreadNave;

public class ControllerTelaPrincipal implements Initializable {
	@FXML
	private Slider sliderNave1, sliderNave2, sliderNave3, sliderNave4, sliderNave5, sliderNave6, sliderNave7, sliderNave8;

  @FXML
  private Button botaoDeFechar, botaoReset, botaoPR1, botaoPR2, botaoPR3, botaoPR4, botaoPR5, botaoPR6, botaoPR7, botaoPR8;
  @FXML
  private Button botaoPercurso1, botaoPercurso2, botaoPercurso3, botaoPercurso4, botaoPercurso5, botaoPercurso6, botaoPercurso7, botaoPercurso8;

	@FXML
	private ImageView imagemNave1, imagemNave2, imagemNave3, imagemNave4, imagemNave5, imagemNave6, imagemNave7, imagemNave8;
  @FXML
  private ImageView imagemBotaoPausa1, imagemBotaoPausa2, imagemBotaoPausa3, imagemBotaoPausa4, imagemBotaoPausa5, imagemBotaoPausa6, imagemBotaoPausa7, imagemBotaoPausa8;
  @FXML
  private ImageView imagemBotaoRetomada1, imagemBotaoRetomada2, imagemBotaoRetomada3, imagemBotaoRetomada4, imagemBotaoRetomada5, imagemBotaoRetomada6, imagemBotaoRetomada7, imagemBotaoRetomada8;
  @FXML
  private ImageView imagemPercurso1, imagemPercurso2, imagemPercurso3, imagemPercurso4, imagemPercurso5, imagemPercurso6, imagemPercurso7, imagemPercurso8;

  @FXML
  private Circle circulo1, circulo2, circulo3, circulo4, circulo5, circulo6, circulo7, circulo8, circulo9, circulo10, circulo11, circulo12, circulo13, circulo14, 
                 circulo15, circulo16, circulo17, circulo18, circulo19, circulo20, circulo21, circulo22, circulo23, circulo24, circulo25, circulo26, circulo27, 
                 circulo28, circulo29, circulo30, circulo31, circulo32, circulo33, circulo34, circulo35, circulo36;

  private ThreadNave threadNave1, threadNave2, threadNave3, threadNave4, threadNave5, threadNave6, threadNave7, threadNave8;

  private boolean percurso1 = false, percurso2 = false, percurso3 = false, percurso4 = false, percurso5 = false, percurso6 = false, percurso7 = false, percurso8 = false;
  private boolean pausado1 = true, pausado2 = true, pausado3 = true, pausado4 = true, pausado5 = true, pausado6 = true, pausado7 = true, pausado8 = true;

  /*****************************************************************
  * Metodo: aplicarEscala
  * Funcao: Aplicar um fator de escala nas imagens (ImageView) dos botoes, quando o mouse é passado acima dos mesmos.
  * Parametros: Uma ImageView imageView e um double escala
  * Retorno: void
  *****************************************************************/
  @FXML
  private void aplicarEscala(ImageView imageView, double escala) {
    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), imageView);
    scaleTransition.setToX(escala);
    scaleTransition.setToY(escala);
    scaleTransition.play();
  } //Fim aplicarEscala


	/*****************************************************************
  * Metodo: fecharAplicacao
  * Funcao: Fecha a aplicacao quando o usuario clica no botao de fechar.
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML 
  public void fecharAplicacao() {
    System.exit(0);
  } //Fim fecharAplicacao


  /*****************************************************************
  * Metodo: resetarPosicoesEVelocidades
  * Funcao: Reiniciar a animacao, de acordo as posicoes iniciais das naves e a velocidade padrao estabelecida
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML
  public void resetarPosicoesEVelocidades(){
    posicionarNaves();
    sliderNave1.setValue(12);
    sliderNave2.setValue(9);
    sliderNave3.setValue(15);
    sliderNave4.setValue(11);
    sliderNave5.setValue(8);
    sliderNave6.setValue(13);
    sliderNave7.setValue(10);
    sliderNave8.setValue(14);
  } //Fim resetarPosicoesEVelocidades


  /*****************************************************************
  * Metodo: mostrarPercurso1
  * Funcao: Habilita ou desabilita a visualizacao do percurso de acordo o estado do botao
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML //Anotacao para desigFXML
  public void mostrarPercurso1() {
    if(!percurso1){ //Se o percurso nao foi habilitado ainda
      imagemPercurso1.setOpacity(1);
      percurso1 = true;
    } else{
      imagemPercurso1.setOpacity(0);
      percurso1 = false;
    } //Fim if-else
  } //Fim mostrarPercurso1

  /*****************************************************************
  * Metodo: mostrarPercurso2
  * Funcao: Habilita ou desabilita a visualizacao do percurso de acordo o estado do botao
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML //Anotacao para desigFXML
  public void mostrarPercurso2() {
    if(!percurso2){ //Se o percurso nao foi habilitado ainda
      imagemPercurso2.setOpacity(1);
      percurso2 = true;
    } else{ //Caso contrario, ja esta habilitado
      imagemPercurso2.setOpacity(0);
      percurso2 = false;
    } //Fim if-else
  } //Fim mostrarPercurso2

  /*****************************************************************
  * Metodo: mostrarPercurso3
  * Funcao: Habilita ou desabilita a visualizacao do percurso de acordo o estado do botao
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML //Anotacao para desigFXML
  public void mostrarPercurso3() {
    if(!percurso3){ //Se o percurso nao foi habilitado ainda
      imagemPercurso3.setOpacity(1);
      percurso3 = true;
    } else{ //Caso contrario, ja esta habilitado
      imagemPercurso3.setOpacity(0);
      percurso3 = false;
    } //Fim if-else
  } //Fim mostrarPercurso3

  /*****************************************************************
  * Metodo: mostrarPercurso4
  * Funcao: Habilita ou desabilita a visualizacao do percurso de acordo o estado do botao
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML //Anotacao para desigFXML
  public void mostrarPercurso4() {
    if(!percurso4){ //Se o percurso nao foi habilitado ainda
      imagemPercurso4.setOpacity(1);
      percurso4 = true;
    } else{ //Caso contrario, ja esta habilitado
      imagemPercurso4.setOpacity(0);
      percurso4 = false;
    } //Fim if-else
  } //Fim mostrarPercurso4

  /*****************************************************************
  * Metodo: mostrarPercurso5
  * Funcao: Habilita ou desabilita a visualizacao do percurso de acordo o estado do botao
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML //Anotacao para desigFXML
  public void mostrarPercurso5() {
    if(!percurso5){ //Se o percurso nao foi habilitado ainda
      imagemPercurso5.setOpacity(1);
      percurso5 = true;
    } else{ //Caso contrario, ja esta habilitado
      imagemPercurso5.setOpacity(0);
      percurso5 = false;
    } //Fim if-else
  } //Fim mostrarPercurso5

  /*****************************************************************
  * Metodo: mostrarPercurso6
  * Funcao: Habilita ou desabilita a visualizacao do percurso de acordo o estado do botao
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML //Anotacao para desigFXML
  public void mostrarPercurso6() {
    if(!percurso6){ //Se o percurso nao foi habilitado ainda
      imagemPercurso6.setOpacity(1);
      percurso6 = true;
    } else{ //Caso contrario, ja esta habilitado
      imagemPercurso6.setOpacity(0);
      percurso6 = false;
    } //Fim if-else
  } //Fim mostrarPercurso6

  /*****************************************************************
  * Metodo: mostrarPercurso7
  * Funcao: Habilita ou desabilita a visualizacao do percurso de acordo o estado do botao
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML //Anotacao para desigFXML
  public void mostrarPercurso7() {
    if(!percurso7){ //Se o percurso nao foi habilitado ainda
      imagemPercurso7.setOpacity(1);
      percurso7 = true;
    } else{ //Caso contrario, ja esta habilitado
      imagemPercurso7.setOpacity(0);
      percurso7 = false;
    } //Fim if-else
  } //Fim mostrarPercurso7

  /*****************************************************************
  * Metodo: mostrarPercurso8
  * Funcao: Habilita ou desabilita a visualizacao do percurso da nave de acordo o estado do botao
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML //Anotacao para desigFXML
  public void mostrarPercurso8() {
    if(!percurso8){ //Se o percurso nao foi habilitado ainda
      imagemPercurso8.setOpacity(1);
      percurso8 = true;
    } else{ //Caso contrario, ja esta habilitado
      imagemPercurso8.setOpacity(0);
      percurso8 = false;
    } //Fim if-else
  } //Fim mostrarPercurso8


  /*****************************************************************
  * Metodo: pausarRetomar1
  * Funcao: Pausa a movimentacao da nave 1 e troca as imagens correspondentes ao estado do botao
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML //Anotacao FXML
  public void pausarRetomar1() {
    if(!pausado1){ //Se a nave nao estiver pausada, o clique eh para pausar
      threadNave1.getTimerDeMovimento().stop();
      imagemBotaoPausa1.setOpacity(0);
      imagemBotaoRetomada1.setOpacity(1);
      pausado1 = true;
    } else{ //Caso contrario, ja esta pausada, o clique eh para retomar a movimentacao
      threadNave1.run();
      imagemBotaoRetomada1.setOpacity(0);
      imagemBotaoPausa1.setOpacity(1);
      pausado1 = false;
    } //Fim if-else
  } //Fim pausarRetomar1

  /*****************************************************************
  * Metodo: pausarRetomar2
  * Funcao: Pausa a movimentacao da nave 2 e troca as imagens correspondentes ao estado do botao
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML //Anotacao FXML
  public void pausarRetomar2() {
    if(!pausado2){ //Se a nave nao estiver pausada, o clique eh para pausar
      threadNave2.getTimerDeMovimento().stop();
      imagemBotaoPausa2.setOpacity(0);
      imagemBotaoRetomada2.setOpacity(1);
      pausado2 = true;
    } else{ //Caso contrario, ja esta pausada, o clique eh para retomar a movimentacao
      threadNave2.run();
      imagemBotaoRetomada2.setOpacity(0);
      imagemBotaoPausa2.setOpacity(1);
      pausado2 = false;
    } //Fim if-else
  } //Fim pausarRetomar2
  
  /*****************************************************************
  * Metodo: pausarRetomar3
  * Funcao: Pausa a movimentacao da nave 3 e troca as imagens correspondentes ao estado do botao
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML //Anotacao FXML
  public void pausarRetomar3() {
    if(!pausado3){ //Se a nave nao estiver pausada, o clique eh para pausar
      threadNave3.getTimerDeMovimento().stop();
      imagemBotaoPausa3.setOpacity(0);
      imagemBotaoRetomada3.setOpacity(1);
      pausado3 = true;
    } else{ //Caso contrario, ja esta pausada, o clique eh para retomar a movimentacao
      threadNave3.run();
      imagemBotaoRetomada3.setOpacity(0);
      imagemBotaoPausa3.setOpacity(1);
      pausado3 = false;
    } //Fim if-else
  } //Fim pausarRetomar3

  /*****************************************************************
  * Metodo: pausarRetomar4
  * Funcao: Pausa a movimentacao da nave 4 e troca as imagens correspondentes ao estado do botao
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML //Anotacao FXML
  public void pausarRetomar4() {
    if(!pausado4){ //Se a nave nao estiver pausada, o clique eh para pausar
      threadNave4.getTimerDeMovimento().stop();
      imagemBotaoPausa4.setOpacity(0);
      imagemBotaoRetomada4.setOpacity(1);
      pausado4 = true;
    } else{ //Caso contrario, ja esta pausada, o clique eh para retomar a movimentacao
      threadNave4.run();
      imagemBotaoRetomada4.setOpacity(0);
      imagemBotaoPausa4.setOpacity(1);
      pausado4 = false;
    } //Fim if-else
  } //Fim pausarRetomar4

  /*****************************************************************
  * Metodo: pausarRetomar5
  * Funcao: Pausa a movimentacao da nave 5 e troca as imagens correspondentes ao estado do botao
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML //Anotacao FXML
  public void pausarRetomar5() {
    if(!pausado5){ //Se a nave nao estiver pausada, o clique eh para pausar
      threadNave5.getTimerDeMovimento().stop();
      imagemBotaoPausa5.setOpacity(0);
      imagemBotaoRetomada5.setOpacity(1);
      pausado5 = true;
    } else{ //Caso contrario, ja esta pausada, o clique eh para retomar a movimentacao
      threadNave5.run();
      imagemBotaoRetomada5.setOpacity(0);
      imagemBotaoPausa5.setOpacity(1);
      pausado5 = false;
    } //Fim if-else
  } //Fim pausarRetomar5
  
  /*****************************************************************
  * Metodo: pausarRetomar6
  * Funcao: Pausa a movimentacao da nave 6 e troca as imagens correspondentes ao estado do botao
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML //Anotacao FXML
  public void pausarRetomar6() {
    if(!pausado6){ //Se a nave nao estiver pausada, o clique eh para pausar
      threadNave6.getTimerDeMovimento().stop();
      imagemBotaoPausa6.setOpacity(0);
      imagemBotaoRetomada6.setOpacity(1);
      pausado6 = true;
    } else{ //Caso contrario, ja esta pausada, o clique eh para retomar a movimentacao
      threadNave6.run();
      imagemBotaoRetomada6.setOpacity(0);
      imagemBotaoPausa6.setOpacity(1);
      pausado6 = false;
    } //Fim if-else
  } //Fim pausarRetomar6

  /*****************************************************************
  * Metodo: pausarRetomar7
  * Funcao: Pausa a movimentacao da nave 7 e troca as imagens correspondentes ao estado do botao
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML //Anotacao FXML
  public void pausarRetomar7() {
    if(!pausado7){ //Se a nave nao estiver pausada, o clique eh para pausar
      threadNave7.getTimerDeMovimento().stop();
      imagemBotaoPausa7.setOpacity(0);
      imagemBotaoRetomada7.setOpacity(1);
      pausado7 = true;
    } else{ //Caso contrario, ja esta pausada, o clique eh para retomar a movimentacao
      threadNave7.run();
      imagemBotaoRetomada7.setOpacity(0);
      imagemBotaoPausa7.setOpacity(1);
      pausado7 = false;
    } //Fim if-else
  } //Fim pausarRetomar7

  /*****************************************************************
  * Metodo: pausarRetomar8
  * Funcao: Pausa a movimentacao da nave 8 e troca as imagens correspondentes ao estado do botao
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  @FXML //Anotacao FXML
  public void pausarRetomar8() {
    if(!pausado8){ //Se a nave nao estiver pausada, o clique eh para pausar
      threadNave8.getTimerDeMovimento().stop();
      imagemBotaoPausa8.setOpacity(0);
      imagemBotaoRetomada8.setOpacity(1);
      pausado8 = true;
    } else{ //Caso contrario, ja esta pausada, o clique eh para retomar a movimentacao
      threadNave8.run();
      imagemBotaoRetomada8.setOpacity(0);
      imagemBotaoPausa8.setOpacity(1);
      pausado8 = false;
    } //Fim if-else
  } //Fim pausarRetomar8


  /*****************************************************************
  * Metodo: posicionarNaves
  * Funcao: Para a movimentacao das naves, reposiciona-os de volta as suas posicoes iniciais e reinicializa suas movimentacoes
  * Parametros: void
  * Retorno: void
  *****************************************************************/
  public void posicionarNaves(){
    //Para o movimento grafico das naves na tela, para que o reposicionamento seja feito
    threadNave1.getTimerDeMovimento().stop();
    threadNave2.getTimerDeMovimento().stop();
    threadNave3.getTimerDeMovimento().stop();
    threadNave4.getTimerDeMovimento().stop();
    threadNave5.getTimerDeMovimento().stop();
    threadNave6.getTimerDeMovimento().stop();
    threadNave7.getTimerDeMovimento().stop();
    threadNave8.getTimerDeMovimento().stop();
    
    //Chamada do metodo de reposicionamento das imagens das naves
    threadNave1.reposicionar();
    threadNave2.reposicionar();
    threadNave3.reposicionar();
    threadNave4.reposicionar();
    threadNave5.reposicionar();
    threadNave6.reposicionar();
    threadNave7.reposicionar();
    threadNave8.reposicionar();

    //Chamada do metodo que inicia a thread da Nave, junto ao seu movimento grafico na tela
    threadNave1.run();
    threadNave2.run();
    threadNave3.run();
    threadNave4.run();
    threadNave5.run();
    threadNave6.run();
    threadNave7.run();
    threadNave8.run();
  } //Fim posicionarNaves





	/*****************************************************************
  * Metodo: initialize
  * Funcao: Configurar os componentes da interface gráfica, os dados iniciais ou realizar 
  * qualquer outra inicializacao necessaria para a aplicacao, antes que a mesma seja exibida para o usuario.
  * Parametros: Um URL location e um ResourceBundle resources, que sao parametros padroes do metodo initialize
  * Retorno: void
  *****************************************************************/
	@Override
  public void initialize(URL location, ResourceBundle resources) {
    //Instalacao de Tooltip texts nas imagens das naves para indicar a identificacao correspondente a cada uma
    Tooltip.install(imagemNave1, new Tooltip("NAVE 1"));
    Tooltip.install(imagemNave2, new Tooltip("NAVE 2"));
    Tooltip.install(imagemNave3, new Tooltip("NAVE 3"));
    Tooltip.install(imagemNave4, new Tooltip("NAVE 4"));
