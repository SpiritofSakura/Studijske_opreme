package com.example.studijske_opreme;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class reset implements Initializable {

    @FXML
    private Hyperlink domov;

    @FXML
    private PasswordField geslo,geslo1;
    private String encryptedpassword = null;

    @FXML
    private Button btn_spremeni;
    @FXML
    private Label napacniPodatki,napacniPodatki1;
    private boolean aktiven = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        btn_spremeni.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(geslo.getText().isEmpty())
                {
                    napacniPodatki.setText("Prosim, vpišite novo geslo!");
                    new animatefx.animation.Shake(geslo).play();
                    aktiven = false;
                }
                else
                {
                    napacniPodatki.setText("");
                    aktiven = true;
                }
                if(geslo.getText().isEmpty())
                {
                    napacniPodatki1.setText("Prosim, ponovno vpišite geslo!");
                    new animatefx.animation.Shake(geslo1).play();
                    aktiven = false;
                }
                else
                {
                    napacniPodatki1.setText("");
                    aktiven = true;
                }

                if(aktiven == true){


                    if(geslo.getText().equals(geslo1.getText())) //preveri če se geslo ujema
                    {
                        napacniPodatki.setText("");
                        napacniPodatki1.setText("");
                        try {
                            encryptedpassword = Baza.SifrirajGeslo(geslo.getText());
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }
                        Baza.spremeniGeslo(encryptedpassword,ResetPass.email);
                    }
                    else
                    {
                        napacniPodatki.setText("");
                        napacniPodatki1.setText("Gesli se ne ujemata!");
                        new animatefx.animation.Shake(geslo1).play();
                    }


                }
            }
        });
    }
}
