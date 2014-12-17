package br.gov.go.agr.communicator.cliente.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Modality;
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
    	stage.initStyle(StageStyle.TRANSPARENT);
    	stage.show();
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
                        janela.initModality(Modality.WINDOW_MODAL);
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
