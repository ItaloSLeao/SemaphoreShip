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
