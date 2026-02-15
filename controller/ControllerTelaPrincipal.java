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
