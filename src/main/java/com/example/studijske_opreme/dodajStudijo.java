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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class dodajStudijo implements Initializable {


    @FXML
    private TextField text_studijo,text_naslov,text_eposta,text_telefon;

    @FXML
    private Label errorstudijo,errornaslov,erroreposta,errortelefon,errorkraj;
    @FXML
    private ComboBox text_kraj;

    @FXML
    private Button btn_dodaj;
    private boolean aktiven = false;
    private String kraj = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Baza.napolniKraje(text_kraj);

        btn_dodaj.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(text_studijo.getText().isEmpty())
                {
                    aktiven = false;
                    errorstudijo.setText("Vnesite ime vašega studija!");
                    new animatefx.animation.Shake(text_studijo).play();

                }
                else {
                    aktiven = true;
                    errorstudijo.setText("");
                }
                if(text_naslov.getText().isEmpty())
                {
                    aktiven = false;
                    errornaslov.setText("Vnesite priimek!");
                    new animatefx.animation.Shake(text_naslov).play();

                }
                else {
                    errornaslov.setText("");
                    aktiven = true;
                }
                if(text_eposta.getText().isEmpty())
                {

                    erroreposta.setText("Vnesite email!");
                    new animatefx.animation.Shake(text_eposta).play();

                    aktiven = false;
                }
                else {
                    erroreposta.setText("");
                    aktiven = true;
                }
                if (kraj == null)
                {
                    aktiven = false;
                    new animatefx.animation.Shake(text_kraj).play();

                    errorkraj.setText("Izberite kraj!");
                }
                else
                {
                    aktiven = true;
                    errorkraj.setText("");
                }

                if(aktiven == true)
                {
                    Baza.dodajStudijo(text_studijo.getText(),text_naslov.getText(),text_eposta.getText(),text_telefon.getText(),kraj);
                }
            }
        });


        text_kraj.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                //Pretvorimo v string kar kliknamo na choicebox.
                kraj = (String) text_kraj.getItems().get((Integer) t1);


            }
        });
    }
}
