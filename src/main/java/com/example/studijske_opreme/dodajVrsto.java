package com.example.studijske_opreme;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.studijske_opreme.Homepage.stg;

public class dodajVrsto implements Initializable {
    @FXML
    private Button btn_dodaj;

    @FXML
    private TextField vrsta,opis;

    @FXML
    private Label error;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        btn_dodaj.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(vrsta.getText().isEmpty())
                {
                    error.setText("Vnesite polje.");
                    new animatefx.animation.Shake(vrsta).play();

                }
                else
                {
                    error.setText("");
                    Baza.dodajVrsto(vrsta.getText(),opis.getText());
                    stg.close();


                }
            }
        });
    }
}
