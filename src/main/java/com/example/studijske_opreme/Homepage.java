package com.example.studijske_opreme;

import com.jfoenix.controls.*;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Homepage implements Initializable {


    @FXML
    private AnchorPane pane1;
    @FXML
    private AnchorPane izposoja_pane;
    @FXML
    private AnchorPane domov_pane;
    @FXML
    private AnchorPane oprema_pane;

    @FXML
    private AnchorPane pane2;

    @FXML
    private ImageView menu;
    @FXML
    private ImageView logout_img;
    @FXML
    private JFXButton oprema_btn;
    @FXML
    private JFXButton domov_btn;

    @FXML
    private ImageView oprema_icon;
    @FXML
    private ImageView domov_icon;

    @FXML
    private JFXButton izposoja_btn;

    @FXML
    private ImageView izposoja_icon;


    public void switchForm(ActionEvent event){

        if(event.getSource() == domov_btn){
            domov_pane.setVisible(true);
            izposoja_pane.setVisible(false);
            oprema_pane.setVisible(false);
        }else if (event.getSource() == izposoja_btn) {
            domov_pane.setVisible(false);
            izposoja_pane.setVisible(true);
            oprema_pane.setVisible(false);
        } else if (event.getSource() == oprema_btn) {
            domov_pane.setVisible(false);
            izposoja_pane.setVisible(false);
            oprema_pane.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pane1.setVisible(false);

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.5),pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        menu.setOnMouseClicked(event -> {
            System.out.println("klik menu");
            pane1.setVisible(true);

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),pane2);
            translateTransition1.setByX(+600);
            translateTransition1.play();


        });

        pane1.setOnMouseClicked(event -> {

            System.out.println("klik");
            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                pane1.setVisible(false);
            });

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),pane2);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });

        domov_btn.setOnMouseClicked(event -> {

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                pane1.setVisible(false);
            });

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),pane2);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });

        oprema_btn.setOnMouseClicked(event -> {

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                pane1.setVisible(false);
            });

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),pane2);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });

        izposoja_btn.setOnMouseClicked(event -> {

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                pane1.setVisible(false);
            });

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),pane2);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });

        logout_img.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Main.changeScene("login.fxml",800,500);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
