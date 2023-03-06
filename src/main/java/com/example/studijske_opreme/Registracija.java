package com.example.studijske_opreme;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Registracija implements Initializable {


    @FXML
    private TextField text_ime;

    @FXML
    private TextField text_priimek;

    @FXML
    private TextField text_eposta;

    @FXML
    private TextField text_username;

    @FXML
    private ComboBox text_ime_p;

    @FXML
    private PasswordField text_geslo;

    @FXML
    private PasswordField text_geslo1;

    @FXML
    private Button btn_vpis;

    @FXML
    private Button btn_dodaj;

    @FXML
    private Hyperlink link;

    @FXML
    private Label errorime,errorpriimek,erroreposta,errorusername,errorgeslo,errorgeslo1,errorstudio;
    private boolean aktiven = false;

    private String preveri;
    private String geslo;
    private String firma = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Baza.napolniStudije(text_ime_p); //Napolne combo box z studiji
        btn_vpis.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(text_ime.getText().isEmpty())
                {
                    aktiven = false;
                    errorime.setText("Vnesite ime!");
                    new animatefx.animation.Shake(text_ime).play();
                }
                else {
                    aktiven = true;
                    errorime.setText("");
                }
                if(text_priimek.getText().isEmpty())
                {
                    aktiven = false;
                    errorpriimek.setText("Vnesite priimek!");
                    new animatefx.animation.Shake(text_priimek).play();
                }
                else {
                    errorpriimek.setText("");
                    aktiven = true;
                }
                if(text_eposta.getText().isEmpty())
                {
                                                                                                //Preveri ali je vse pravilno vnešeno v polja
                    erroreposta.setText("Vnesite email!");
                    new animatefx.animation.Shake(text_eposta).play();
                    aktiven = false;
                }
                else {
                    erroreposta.setText("");
                    aktiven = true;
                }
                if(text_username.getText().isEmpty())
                {

                    errorusername.setText("Vnesite uporabniško ime!");
                    new animatefx.animation.Shake(text_username).play();
                    aktiven = false;
                }
                else {
                    errorusername.setText("");
                    aktiven = true;
                }
                if(text_geslo.getText().isEmpty())
                {

                    errorgeslo.setText("Vnesite geslo!");
                    new animatefx.animation.Shake(text_geslo).play();
                    aktiven = false;
                }
                else {
                    errorgeslo.setText("");
                    aktiven = true;
                    try {
                        geslo = Baza.SifrirajGeslo(text_geslo.getText());
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }

                }
                if(text_geslo1.getText().isEmpty())
                {

                    errorgeslo1.setText("Ponovno vnesite geslo!");
                    new animatefx.animation.Shake(text_geslo1).play();
                    aktiven = false;

                }
                else {
                    errorgeslo1.setText("");
                    try {
                        preveri = Baza.SifrirajGeslo(text_geslo1.getText());
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                    aktiven = true;

                }
                if(firma == null)
                {
                    errorstudio.setText("Izberite vaš studijo!");
                    new animatefx.animation.Shake(text_ime_p).play();
                    aktiven = false;
                }
                else
                {
                    errorstudio.setText("");
                    aktiven = true;
                }
                if(geslo.equals(preveri))
                {
                    errorgeslo.setText("");
                    errorgeslo1.setText("");

                }
                else
                {
                    errorgeslo.setText("Geslo se ne ujema!");
                    errorgeslo1.setText("Geslo se ne ujema!");
                    new animatefx.animation.Shake(text_geslo).play();
                    new animatefx.animation.Shake(text_geslo1).play();
                }


                if(geslo.equals(preveri) && aktiven == true)
                {
                    Baza.registrijajUporabnika(text_ime.getText(),text_priimek.getText(),text_eposta.getText(),text_username.getText(),firma ,geslo);
                }

            }
        });
        link.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Main.changeScene("login.fxml",800,500);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btn_dodaj.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Main.changeScene("dodaj-studijo.fxml",800,500);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        text_ime_p.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                //Pretvorimo v string kar kliknamo na choicebox.
                firma = (String) text_ime_p.getItems().get((Integer) t1);

            }
        });





    }
}
