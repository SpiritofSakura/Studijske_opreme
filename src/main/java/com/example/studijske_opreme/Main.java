package com.example.studijske_opreme;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stg;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stg = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml")); //Napišeš katera stran se prva odpre ob zagonu aplikacije.
        primaryStage.setTitle("StudioRent"); //Naslov
        primaryStage.setScene(new Scene(root, 800, 500)); //Določiš višino in širino
        primaryStage.show();


    }

    public static void changeScene(String fxml,Integer sirina,Integer visina) throws IOException { //Funkcija ki spremeni stran v aplikaciji.
        Parent pane = FXMLLoader.load(Main.class.getResource(fxml));
        stg.setScene(new Scene(pane,sirina,visina)); //Določiš širino, višino

    }
    public static void main(String[] args) {
        launch();
    }
}