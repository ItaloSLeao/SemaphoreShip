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
