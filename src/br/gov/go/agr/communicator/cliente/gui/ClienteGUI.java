package br.gov.go.agr.communicator.cliente.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.util.Duration;

import br.gov.go.agr.communicator.cliente.Cliente;

public class ClienteGUI extends Application {

    private Timeline cronometro;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane pane = new StackPane();
        pane.setPrefSize(0, 0);
        pane.setStyle("-fx-background-color: transparent;");
        Scene scene = new Scene(pane);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        String os = System.getProperty("os.name");
        if (os.equalsIgnoreCase("linux")) {
            stage.initStyle(StageStyle.TRANSPARENT);
        } else {
            stage.initStyle(StageStyle.UTILITY);
        }
        stage.setMaxHeight(0);
        stage.setMaxWidth(0);
        stage.setX(Double.MAX_VALUE);
        stage.show();
        stage.toFront();
        Cliente cliente = new Cliente();
        cliente.conectar();
        cliente.iniciarCaixaDeEntrada();
        cronometro = new Timeline(new KeyFrame(
        Duration.millis(1000), 
        c -> {
                try {
                    String mensagem = cliente.getMensagem();
                    if (mensagem != null) {
                        ComunicadoGUI comunicado = new ComunicadoGUI(mensagem);
                        Stage janela = new Stage();
                        comunicado.start(janela);
                        cliente.setMensagem(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        ));
        cronometro.setCycleCount(Animation.INDEFINITE);
        cronometro.play();
    }
}
