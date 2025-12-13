/*****************************************************************
* Autor............: Italo de Souza Leao
* Matricula........: 202410120
* Inicio...........: 04/07/2025
* Ultima alteracao.: 04/07/2025
* Nome.............: Principal.java
* Funcao...........: Realiza as importacoes necessarias para o pleno funcionamento proposto ao JavaFX no trabalho, inicia a aplicacao, 
inicializa a tela principal do programa, carregando o respectivo arquivo fxml, e executa a aplicacao escolhida.
*****************************************************************/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import controller.ControllerTelaPrincipal;

@SuppressWarnings("unused")

public class Principal extends Application {
  /*****************************************************************
  * Metodo: start
  * Funcao: Inicializa a aplicacao, carregando o  telaPrincipal.fxml no palco (Stage) primaryStage
  * Parametros: Um Stage primaryStage, que eh o palco da aplicacao
  * Retorno: void
  *****************************************************************/
  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("view/telaPrincipal.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
    Image icon = new Image(getClass().getResourceAsStream("assets/semaphore-icon.png"));

		primaryStage.setScene(scene);
    primaryStage.getIcons().add(icon);
		primaryStage.setTitle("SEMAPHORE-SHIP");
		primaryStage.resizableProperty().setValue(false);
		primaryStage.show();
  } //Fim start

  /*****************************************************************
  * Metodo: main
  * Funcao: Atua como ponto de partida para a execucao de um codigo java. 
  * Esse eh o metodo ao qual o Java Virtual Machine (JVM) procura para iniciar o programa.
  * Parametros: Um array de Strings chamado args, contendo os argumentos de linhas de comando java
  * Retorno: void
  *****************************************************************/
  public static void main(String[] args) {
    launch(args);
