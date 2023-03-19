package com.example.studijske_opreme;

import animatefx.animation.AnimationFX;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;

public class ResetPass implements Initializable {

    @FXML
    private Button btnspremeni,btnkoda;

    @FXML
    private TextField text_eposta,text_koda;

    @FXML
    private Label napacniPodatki;
    @FXML
    private Label napacniPodatki1,reset,stevilke;

    @FXML
    private Hyperlink home;



    private Integer koda = null;
    private boolean check;
    public static String email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        btnkoda.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(text_eposta.getText().isEmpty())
                {
                    napacniPodatki.setText("Vnesite epostni naslov!");
                    new animatefx.animation.Shake(text_eposta).play();

                }
                else
                {
                    try {
                        if(!Baza.pridobiEmail(text_eposta.getText()))
                        {
                            napacniPodatki.setText("Ta epoštni račun ne obstaja");
                            new animatefx.animation.Shake(text_eposta).play();
                        }
                        else
                        {

                            email = text_eposta.getText();
                            Random rand = new Random();

                            koda = rand.nextInt(999999);


                            check = new EmailSender().posljiEmail(koda,text_eposta.getText());
                            text_eposta.setDisable(true);
                            btnkoda.setDisable(true);
                            btnspremeni.setDisable(false);
                            text_koda.setDisable(false);
                            if(check == true)
                            {
                                reset.setText("Koda je bila uspešno poslana na vaš email!");

                            }

                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        btnspremeni.setOnMouseClicked(new EventHandler<MouseEvent>() {//Če se koda ujema
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(koda.toString().equals(text_koda.getText()))
                {
                    try {
                        Main.changeScene(".fxml",800,500);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else
                {
                    napacniPodatki1.setText("Napacna koda!");
                    new animatefx.animation.Shake(text_koda).play();
                }
            }
        });

        home.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
