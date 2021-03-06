package br.gov.go.agr.communicator.cliente.gui;

import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ComunicadoGUI extends Application {

    private Stage stage;
    private Scene scene;
    private AnchorPane content;
    private AnchorPane pane;
    private Button buttonClose;
    private Button buttonMinus;
    private Image close;
    private Image minus;
    private Double screenWidth;
    private Double screenHeight;
    private Double xOffset;
    private Double yOffset;
    private String texto;

    public ComunicadoGUI() {}

    public ComunicadoGUI(String texto) {
        this.texto = texto;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        initComponents();
        initLayout();
        initEventHandlers();
        scene = new Scene(pane);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle(texto);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.show();
        initEffects();
    }

    private void initComponents() {
        pane = new AnchorPane();
        pane.setStyle("-fx-background-color: transparent");
        content = new AnchorPane();
        String css = "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, silver 0%, white 100%); ";
        css += "-fx-background-radius:5;";
        content.setStyle(css);
        close = new Image(getClass().getResourceAsStream("imagens/dialog-close.png"));
        buttonClose = new Button();
        buttonClose.setGraphic(new ImageView(close));
        buttonClose.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        buttonClose.setCursor(Cursor.HAND);
        minus = new Image(getClass().getResourceAsStream("imagens/minus.png"));
        buttonMinus = new Button();
        buttonMinus.setGraphic(new ImageView(minus));
        buttonMinus.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        buttonMinus.setCursor(Cursor.HAND);
        Image agr = new Image(getClass().getResourceAsStream("imagens/agr.jpg"));
        ImageView agrLogo = new ImageView(agr);
        Text text = new Text(250, 80, texto);
        text.setFill(Color.rgb(11, 2, 92));
        text.setFont(Font.font("Verdana", 60));
        content.getChildren().addAll(buttonClose, buttonMinus, agrLogo, text);
        pane.getChildren().add(content);
    }

    private void initLayout() {
        screenWidth = Screen.getPrimary().getBounds().getWidth() - 100;
        screenHeight = Screen.getPrimary().getBounds().getHeight() - 100;
        pane.setPrefSize(screenWidth, screenHeight);
        content.setPrefSize(700, 400);
        content.setLayoutX((screenWidth - 700) / 2);
        content.setLayoutY((screenHeight - 400) / 2);
        buttonClose.setLayoutX(700);
        buttonMinus.setLayoutX(667);
        buttonMinus.setLayoutY(-4);
    }

    private void initEventHandlers() {
        buttonClose.setOnAction(event -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(400), content);
            scale.setToX(0.0);
            scale.setToY(0.0);
            scale.play();
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(350), closeOnFinish -> stage.close()));
            timeline.play();
        });
        buttonClose.setOnMouseEntered(event -> buttonClose.setEffect(new DropShadow()));
        buttonClose.setOnMouseExited(event -> buttonClose.setEffect(null));
        buttonMinus.setOnAction(event ->{
            ScaleTransition scale = new ScaleTransition(Duration.millis(400), content);
            scale.setToX(0.2);
            scale.setToY(0.2);
            scale.play();
            RotateTransition rotate = new RotateTransition(Duration.millis(750), content);
            rotate.setByAngle(360);
            rotate.play();
            TranslateTransition translate = new TranslateTransition(Duration.millis(750), content);
            translate.setToY(500);
            translate.play();
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(750), backToNormal -> {
                stage.setIconified(true);
                ScaleTransition scale2 = new ScaleTransition(Duration.millis(50), content);
                scale2.setToX(1.0);
                scale2.setToY(1.0);
                scale2.play();
                TranslateTransition translate2 = new TranslateTransition(Duration.millis(50), content);
                translate2.setToY(0);
                translate2.play();
            }));
            timeline.play();
        });
        buttonMinus.setOnMouseEntered(event -> buttonMinus.setEffect(new DropShadow()));
        buttonMinus.setOnMouseExited(event -> buttonMinus.setEffect(null));
        content.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown()){
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
                scene.setCursor(Cursor.MOVE);
            }
        });
        content.setOnMouseDragged(event -> {
            if(event.isPrimaryButtonDown()){
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        content.setOnMouseReleased(event -> scene.setCursor(Cursor.DEFAULT));
    }
    
    private void initEffects() {
        ScaleTransition scale = new ScaleTransition(Duration.millis(1300), content);
        scale.setFromX(0);
        scale.setFromY(0);
        scale.setToX(1.0);
        scale.setToY(1.0);
        scale.setRate(0.85);
        scale.play();
        RotateTransition rotate = new RotateTransition(Duration.millis(800), content);
        rotate.setByAngle(360);
        rotate.play();
        TranslateTransition translate1 = new TranslateTransition(Duration.millis(800));
        translate1.setNode(content);
        translate1.setFromY(0 - screenHeight / 2);
        translate1.setToY(screenHeight / 4);
        TranslateTransition translate2 = new TranslateTransition(Duration.millis(600));
        translate2.setNode(content);
        translate2.setFromY(screenHeight / 4);
        translate2.setToY(0);
        SequentialTransition sequence = new SequentialTransition();
        sequence.getChildren().addAll(translate1, translate2);
        sequence.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}