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
