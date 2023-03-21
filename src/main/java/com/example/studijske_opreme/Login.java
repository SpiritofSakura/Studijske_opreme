package com.example.studijske_opreme;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import javax.imageio.ImageIO;

public class Login implements Initializable {

    @FXML
    private Button button_vpis;

    @FXML
    private Hyperlink label_register,pozabil_geslo ;

    @FXML
    private TextField text_username;

    @FXML
    private PasswordField geslo_text;


    @FXML
    private AnchorPane aktivno_form;

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
                    getData.username=text_username.getText();
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


        pozabil_geslo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Main.changeScene("resetpass.fxml",800,500);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });



    }



}


