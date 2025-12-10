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

