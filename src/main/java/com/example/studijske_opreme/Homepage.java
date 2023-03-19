package com.example.studijske_opreme;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;

public class Homepage implements Initializable {


    @FXML
    private Button btn;
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
    private AnchorPane aktivno_form;

    @FXML
    private ImageView oprema_icon;
    @FXML
    private ImageView domov_icon,img_slika;

    @FXML
    private TextField text_oznaka,text_opis,text_ime,text_isci;
    
    @FXML
    private ComboBox combo_vrsta;

    @FXML
    private TableView<Opreme> tabela_studijo;

    @FXML
    private TableColumn<Opreme,String> row_ime,row_opis,row_vrsta,row_oznaka;

    @FXML
    private Button btn_posodobi,btn_izbrisi,btn_clear,btn_vstavi,btn_dodaj;

    @FXML
    private JFXButton izposoja_btn;

    @FXML
    private ImageView izposoja_icon;

    @FXML
    private Label error_ime,error_vrsta,error_opis,error_oznaka,lo,lop,lv,li;


    private int id;
    private boolean aktiven;
    String vrsta;

    private Image slika;
    public static Stage stg;


    private static File file = null;

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


    public void IskanjeOpreme()
    {
        FilteredList<Opreme> filter = new FilteredList<>(Baza.list_iskanje, e -> true);
        text_isci.textProperty().addListener((Observable, oldValue, newValue) ->{
            filter.setPredicate(ugibajOpreme -> {


                if(newValue == null ||newValue.isEmpty())
                {

                    return true;
                }

                String searchKey = newValue;
                if(ugibajOpreme.getIme().contains(searchKey))
                {

                    return true;
                }
                else if(ugibajOpreme.getOpis().contains(searchKey))
                {
                    return true;
                }
                else if(ugibajOpreme.getOznaka().contains(searchKey))
                {
                    return true;
                }
                else if(ugibajOpreme.getVrsta().contains(searchKey))
                {
                    return true;
                }
                return false;
            });
        });

        SortedList<Opreme> urejenList = new SortedList<>(filter);
        System.out.println(urejenList);

        urejenList.comparatorProperty().bind(tabela_studijo.comparatorProperty());

        System.out.println(urejenList);

        tabela_studijo.setItems(urejenList);


    }


//    public void naloziSliko() throws IOException {
//        FileChooser open = new FileChooser();
//        open.setTitle("Naloži sliko");
//        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File","jpg","*png"));
//
//        File file = open.showOpenDialog(aktivno_form.getScene().getWindow());
//
//
//        if(file != null)
//        {
//            pridobiPodatke.path = file.getAbsolutePath();
//            Path applicationFolder = Paths.get(System.getProperty("user.dir"));
//            Path path = Paths.get(applicationFolder + "/slike");
//
//
//            slika = new Image(file.toURI().toString(),94,79,false, true);
//            img_slika.setImage(slika);
//            System.out.println(pridobiPodatke.path);
//            Files.copy(Path.of(pridobiPodatke.path), path.resolve(file.getName()));
//
//
//        }
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        img_slika.fitHeightProperty();
        img_slika.fitWidthProperty();
        //Napolnemo vrsto
        Baza.napolniVrsto(combo_vrsta);
        btn_posodobi.setDisable(true);
        btn_izbrisi.setDisable(true);

        Baza.napolniOpremo(Baza.studio_id,tabela_studijo); //Napolne studio tabelo

        tabela_studijo.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        pane1.setVisible(false);

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.5),pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        row_ime.setCellValueFactory(new PropertyValueFactory<Opreme, String>("Ime"));
        row_vrsta.setCellValueFactory(new PropertyValueFactory<Opreme,String>("Vrsta"));
        row_oznaka.setCellValueFactory(new PropertyValueFactory<Opreme,String>("Oznaka"));
        row_opis.setCellValueFactory(new PropertyValueFactory<Opreme,String>("Opis"));



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




            //KODA


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

        //Če uporanbik prisloni miško stran od tabele, se table počisti.
        tabela_studijo.setOnMouseExited(event -> {
        });


        tabela_studijo.setRowFactory( tv -> { //Če uporabnik 2x pritisne na tabelo, se mu zabeleži vrednost vrstice ki jo je pritisnil
            TableRow<Opreme> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    id = row.getItem().getId(); //Pridobimo id od elementa ki ga nanj pritisnemo
                    text_ime.setText(row.getItem().getIme());
                    combo_vrsta.setValue(row.getItem().getVrsta());
                    text_opis.setText(row.getItem().getOpis());
                    text_oznaka.setText(row.getItem().getOznaka());
                    btn_posodobi.setDisable(false);

                    btn_izbrisi.setDisable(false);

                    System.out.println(row.getItem().getId());

                    //Pridobi sliko funkcija
                    img_slika.setImage(null);
                    slika = SwingFXUtils.toFXImage(Baza.DobiSliko(id),null);

                    img_slika.setImage(slika);






                }
            });
            return row;
        });

        text_isci.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getSource().equals(KeyCode.ENTER))
                {
                    if(text_isci.equals(null) || text_isci.equals(""))
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Prosim vnesite ime v vnesno polje!");
                        alert.show();
                    }
                    else
                    {
                        tabela_studijo.getItems().clear();
                        IskanjeOpreme();
                    }
                }
            }
        });


        btn_clear.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {


                    tabela_studijo.getSelectionModel().clearSelection();
                    id = 0;
                    text_isci.clear();
                    text_opis.clear();
                    text_ime.clear();
                    text_oznaka.clear();
                    combo_vrsta.setValue("");

                    img_slika.setImage(null);
                    btn_posodobi.setDisable(true);
                    btn_izbrisi.setDisable(true);
                }catch (Exception e)
                {
                    System.out.println("Vse ok");
                }

            }
        });

        btn_vstavi.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(text_ime.getText().isEmpty())
                {
                    aktiven = false;
                    error_ime.setText("Vnesite ime!");
                    new animatefx.animation.Shake(text_ime).play();
                    new animatefx.animation.Shake(li).play();

                }
                else {
                    aktiven = true;
                    error_ime.setText("");
                }
                if(combo_vrsta.getItems().isEmpty())
                {
                    aktiven = false;
                    error_vrsta.setText("Vnesite vrsto!");
                    new animatefx.animation.Shake(combo_vrsta).play();
                    new animatefx.animation.Shake(lv).play();

                }
                else {
                    error_vrsta.setText("");
                    aktiven = true;
                }
                if(text_oznaka.getText().isEmpty())
                {
                    //Preveri ali je vse pravilno vnešeno v polja
                    error_oznaka.setText("Vnesite oznako!");
                    new animatefx.animation.Shake(text_oznaka).play();
                    new animatefx.animation.Shake(lo).play();

                    aktiven = false;
                }
                else {
                    error_oznaka.setText("");
                    aktiven = true;
                }
                if(text_opis.getText().isEmpty())
                {

                    error_opis.setText("Vnesite opis!");
                    new animatefx.animation.Shake(text_opis).play();
                    new animatefx.animation.Shake(lop).play();

                    aktiven = false;
                }
                else {
                    error_opis.setText("");
                    aktiven = true;
                }
                if(aktiven == true)
                {
                    System.out.println(Baza.studio_id);
                    try {
                        Baza.dodajOpremo(text_ime.getText(),vrsta,text_opis.getText(),text_oznaka.getText(),Baza.studio_id,file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    tabela_studijo.getItems().clear();
                    Baza.napolniOpremo(Baza.studio_id,tabela_studijo);
                    text_isci.clear();
                    text_opis.clear();
                    text_ime.clear();
                    text_oznaka.clear();
                    combo_vrsta.setValue("");

                }

            }

        });

        btn_posodobi.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(text_ime.getText().isEmpty())
                {
                    aktiven = false;
                    error_ime.setText("Vnesite ime!");
                    new animatefx.animation.Shake(text_ime).play();
                    new animatefx.animation.Shake(li).play();

                }
                else {
                    aktiven = true;
                    error_ime.setText("");
                }
                if(combo_vrsta.getItems().isEmpty())
                {
                    aktiven = false;
                    error_vrsta.setText("Vnesite vrsto!");
                    new animatefx.animation.Shake(combo_vrsta).play();
                    new animatefx.animation.Shake(lv).play();

                }
                else {
                    error_vrsta.setText("");
                    aktiven = true;
                }
                if(text_oznaka.getText().isEmpty())
                {
                    //Preveri ali je vse pravilno vnešeno v polja
                    error_oznaka.setText("Vnesite oznako!");
                    new animatefx.animation.Shake(text_oznaka).play();
                    new animatefx.animation.Shake(lo).play();

                    aktiven = false;
                }
                else {
                    error_oznaka.setText("");
                    aktiven = true;
                }
                if(text_opis.getText().isEmpty())
                {

                    error_opis.setText("Vnesite opis!");
                    new animatefx.animation.Shake(text_opis).play();
                    new animatefx.animation.Shake(lop).play();

                    aktiven = false;
                }
                else {
                    error_opis.setText("");
                    aktiven = true;
                }
                if(aktiven == true)
                {

                    Baza.posodobiOpremo(id,text_ime.getText(),text_oznaka.getText(),vrsta,text_opis.getText(),Baza.studio_id,file);
                    tabela_studijo.getItems().clear();
                     Baza.napolniOpremo(Baza.studio_id,tabela_studijo);
                    text_isci.clear();
                    text_opis.clear();
                    text_ime.clear();
                    text_oznaka.clear();
                    combo_vrsta.setValue("");
                    btn_posodobi.setDisable(true);
                    btn_izbrisi.setDisable(true);

                }

            }

        });


        btn_izbrisi.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Baza.izbrisiOpremo(id);
                tabela_studijo.getItems().clear();
                Baza.napolniOpremo(Baza.studio_id,tabela_studijo);
                text_isci.clear();
                text_opis.clear();
                text_ime.clear();
                text_oznaka.clear();
                combo_vrsta.setValue("");
                btn_izbrisi.setDisable(true);
                btn_posodobi.setDisable(true);

            }
        });

        combo_vrsta.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                //Pretvorimo v string kar kliknamo na choicebox.
                vrsta = (String) combo_vrsta.getItems().get((Integer) t1);

            }
        });

        btn_dodaj.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Parent root;
                stg = new Stage();
                stg.initModality(Modality.APPLICATION_MODAL);
                try {
                     root = FXMLLoader.load(getClass().getResource("dodaj-vrsto.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stg.setScene(new Scene(root,414,292));
                stg.setTitle("Dodaj vrsto");
                stg.showAndWait();


            }
        });

        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                FileChooser open = new FileChooser();
                open.setTitle("Naloži sliko");
                open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "png", "*jpg"));

                file = open.showOpenDialog(aktivno_form.getScene().getWindow());

                if (file != null) {
                    pridobiPodatke.path = file.getAbsolutePath();
                    System.out.println(pridobiPodatke.path);
                    try {
                        BufferedImage defaultpic = ImageIO.read(new File(file.getAbsolutePath()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    slika = new Image(file.toURI().toString(),94,79,false, true);


                    //Prikaze sliko
                    img_slika.setImage(slika);
                }
            }

        });

    }
}
