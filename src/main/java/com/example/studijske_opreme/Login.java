package com.example.studijske_opreme;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private Button button_vpis;

    @FXML
    private Hyperlink label_register;

    @FXML
    private TextField text_username;

    @FXML
    private PasswordField geslo_text;



    @FXML
    private Label napacniPodatki;

    @FXML
    private Label napacniPodatki1;

    private String encryptedpassword = null;
    private boolean aktiven = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_vpis.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //Preveri vsako polje ali je vse pravilno vpisano
                if(text_username.getText().isEmpty())
                {
                    napacniPodatki.setText("Prosim, vpišite svoje uporabniško ime!");
                    new animatefx.animation.Shake(text_username).play();

                    aktiven = false;
                }
                else
                {
                    napacniPodatki.setText("");
                    aktiven = true;
                }

                if(geslo_text.getText().isEmpty())
                {
                    napacniPodatki1.setText("Prosim, vpišite svoje geslo!");
                    new animatefx.animation.Shake(geslo_text).play();
                    aktiven = false;
                }
                else
                {
                    napacniPodatki1.setText("");
                    aktiven = true;
                }

                if(aktiven == true){

                    napacniPodatki.setText("");
                    napacniPodatki1.setText("");
                    try {
                        encryptedpassword = Baza.SifrirajGeslo(geslo_text.getText());
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                    Baza.Prijava(text_username.getText(),encryptedpassword);

                }

            }
        });

        label_register.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Main.changeScene("registration.fxml",800,500);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });









    }


}
