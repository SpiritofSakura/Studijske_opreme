package com.example.studijske_opreme;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import com.sun.javafx.charts.Legend;
import javafx.util.StringConverter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


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
    private ComboBox combo_vrsta;
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
    private JFXButton izposoja_btn;

    @FXML
    private ImageView izposoja_icon;
    @FXML
    private ComboBox combo_izposoja;
    @FXML
    private AnchorPane vracilo_pane;
    @FXML
    private AnchorPane izposodi_pane;
    @FXML
    private TableColumn<opremaData, String> ime_col;
    @FXML
    private TableColumn<opremaData, String> opis_col;
    @FXML
    private TableColumn<opremaData, String> oznaka_col;
    @FXML
    private TableColumn<opremaData, String> studio_col;
    @FXML
    private TableColumn<opremaData, String> vrsta_col;
    @FXML
    private TableColumn<opremaData, String> izposoja_datum_col;
    @FXML
    private TableView<opremaData> zaizposojo_table;

    @FXML
    private TableView<Opreme> tabela_studijo;

    @FXML
    private TableColumn<Opreme,String> row_ime,row_opis,row_vrsta,row_oznaka;

    @FXML
    private Label error_ime,error_vrsta,error_opis,error_oznaka,lo,lop,lv,li;
    @FXML
    private Button btn_posodobi,btn_izbrisi,btn_clear,btn_vstavi,btn_dodaj;


    @FXML
    private Label user_lbl;
    @FXML
    private Label studio_lbl;
    @FXML
    private DatePicker datum_vrnitve;
    @FXML
    private TextField oznaka_tbox;
    @FXML
    private TextField ime_tbox;
    @FXML
    private TextArea opis_tbox;
    @FXML
    private ComboBox studio_combo;
    @FXML
    private DatePicker datum_izposoje;
    @FXML
    private TextField oznaka_izposoja;
    @FXML
    private TextField ime_izposoja;
    @FXML
    private TextArea opis_izposoja;
    @FXML
    private ComboBox studio_izposoja;
    @FXML
    private TableColumn<opremaData, String> ime_col1;
    @FXML
    private TableColumn<opremaData, String> opis_col1;
    @FXML
    private TableColumn<opremaData, String> oznaka_col1;
    @FXML
    private TableColumn<opremaData, String> studio_col1;
    @FXML
    private TableColumn<opremaData, String> vrsta_col1;
    @FXML
    private TableView<opremaData> zaizposojo_table1;
    @FXML
    private AnchorPane izposoja_table_pane;
    @FXML
    private AnchorPane vracilo_table_pane;
    @FXML
    private TextField id_txt;
    @FXML
    private TextField id_txt2;
    @FXML
    private Button vrni_gumb;
    @FXML
    private Button btn_import;
    @FXML
    private Button izposodi_gumb;
    @FXML
    private Label st_kosov;
    @FXML
    private Label st_uporabnikov;
    @FXML
    private PieChart pie_chart;

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
            try {
                st_kosov.setText(izpisi_num1(getData.studio).toString());
                st_uporabnikov.setText(izpisi_num2(getData.studio).toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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

    //nova koda
    public void poisciCombo() throws SQLException {
        if(combo_izposoja.getSelectionModel().getSelectedItem() == "Izposoja")
        {
            izposodi_pane.setVisible(true);
            vracilo_pane.setVisible(false);
            izposoja_table_pane.setVisible(true);
            vracilo_table_pane.setVisible(false);
            izposojaPrikaziListData();
            System.out.print("izposoja");
        }
        else
        {
            izposodi_pane.setVisible(false);
            vracilo_pane.setVisible(true);
            izposoja_table_pane.setVisible(false);
            vracilo_table_pane.setVisible(true);
            izposojaPrikaziVrniListData();
            System.out.print("vracilo");
        }

    }

    private Connection connect = null;
    private PreparedStatement prepare = null;
    private ResultSet result = null;

    public ObservableList<opremaData> izposojaListData() throws SQLException {
        ObservableList<opremaData> listData = FXCollections.observableArrayList();
        //connect = Baza.connectDb();
        connect=DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp", "iwlqnlkp", "4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
        try{
            prepare= connect.prepareStatement("SELECT * FROM get_izposojalistdata(?);");
            prepare.setString(1,getData.studio);
            result=prepare.executeQuery();
            opremaData opremaD;

            while(result.next()){
                opremaD= new opremaData(result.getInt("id"),
                        result.getString("oznaka"),
                        result.getString("ime"),
                        result.getString("vrsta"),
                        result.getString("studio"),
                        result.getString("opis"));
                listData.add(opremaD);
            }
            prepare.close();
            connect.close();
            result.close();

        }catch (Exception e){e.printStackTrace();}
        return listData;
    }

    private ObservableList<opremaData> dostopnaOpremaList;
    public void izposojaPrikaziListData() throws SQLException {
        dostopnaOpremaList = izposojaListData();
        oznaka_col1.setCellValueFactory(new PropertyValueFactory<>("oznaka"));
        ime_col1.setCellValueFactory(new PropertyValueFactory<>("ime"));
        vrsta_col1.setCellValueFactory(new PropertyValueFactory<>("vrsta"));
        studio_col1.setCellValueFactory(new PropertyValueFactory<>("studio"));
        opis_col1.setCellValueFactory(new PropertyValueFactory<>("opis"));
        zaizposojo_table1.setItems(dostopnaOpremaList);
    }

    public ObservableList<opremaData> vrniListData() throws SQLException {
        ObservableList<opremaData> listData = FXCollections.observableArrayList();
        //connect = Baza.connectDb();
        connect = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp", "iwlqnlkp", "4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
        try{
            prepare= connect.prepareStatement("SELECT * FROM get_vrnilistdata(?);");
            prepare.setString(1,getData.studio);
            result = prepare.executeQuery();
            opremaData opremaD;

            while(result.next()){
                opremaD= new opremaData(result.getInt("id"),
                        result.getString("oznaka"),
                        result.getString("ime"),
                        result.getString("vrsta"),
                        result.getString("studio"),
                        result.getString("opis"),
                        result.getString("datum_izposoje"));
                listData.add(opremaD);
                System.out.print("Dela 1");
            }
            prepare.close();
            connect.close();
            result.close();

        }catch (Exception e){e.printStackTrace();}
        return listData;
    }

    private ObservableList<opremaData> dostopnaOpremaVrniList;
    public void izposojaPrikaziVrniListData() throws SQLException {
        dostopnaOpremaVrniList = vrniListData();
        oznaka_col.setCellValueFactory(new PropertyValueFactory<>("oznaka"));
        ime_col.setCellValueFactory(new PropertyValueFactory<>("ime"));
        vrsta_col.setCellValueFactory(new PropertyValueFactory<>("vrsta"));
        studio_col.setCellValueFactory(new PropertyValueFactory<>("studio"));
        opis_col.setCellValueFactory(new PropertyValueFactory<>("opis"));
        izposoja_datum_col.setCellValueFactory(new PropertyValueFactory<>("datum_izposoje"));
        zaizposojo_table.setItems(dostopnaOpremaVrniList);
        System.out.print("dela 2");
    }

    public void prikaziUsername()
    {
        user_lbl.setText(getData.username);
    }

    public void findStudio(String user) throws SQLException {
        //connect = Baza.connectDb();
        connect = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp", "iwlqnlkp", "4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
        try{
            prepare= connect.prepareStatement("SELECT s.ime FROM studiji s INNER JOIN uporabniki u ON s.id=u.studio_id WHERE u.username=?;");
            prepare.setString(1,user);
            result = prepare.executeQuery();
            while(result.next())
            {
                getData.studio=result.getString("ime");
            }
            prepare.close();
            connect.close();
            result.close();
        }catch (Exception e){e.printStackTrace();}
        studio_lbl.setText(getData.studio);
    }

    public void prikazIzbranihPodatkov()
    {
        opremaData o = zaizposojo_table1.getSelectionModel().getSelectedItem();
        int num = zaizposojo_table1.getSelectionModel().getSelectedIndex();

        if((num -1) < -1){
            return;
        }

        oznaka_izposoja.setText(String.valueOf(o.getOznaka()));
        ime_izposoja.setText(String.valueOf(o.getIme()));
        opis_izposoja.setText(String.valueOf(o.getOpis()));
        studio_izposoja.setValue(o.getStudio());
        datum_izposoje.setValue(LocalDate.now());
        id_txt2.setText(String.valueOf(o.getId()));
    }
    public void prikazIzbranihPodatkov2()
    {
        opremaData o = zaizposojo_table.getSelectionModel().getSelectedItem();
        int num = zaizposojo_table.getSelectionModel().getSelectedIndex();

        if((num -1) < -1){
            return;
        }

        oznaka_tbox.setText(String.valueOf(o.getOznaka()));
        ime_tbox.setText(String.valueOf(o.getIme()));
        opis_tbox.setText(String.valueOf(o.getOpis()));
        studio_combo.setValue(o.getStudio());
        datum_vrnitve.setValue(LocalDate.now());
        id_txt.setText(String.valueOf(o.getId()));
    }

    public void vrniGumb(Integer oprema, Timestamp datum) throws SQLException {
        //connect = Baza.connectDb();
        connect = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp", "iwlqnlkp", "4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
        try{
            prepare= connect.prepareStatement("UPDATE izposoja_oprema io SET datum_vrnitve=? WHERE (io.izposoja_id=(SELECT i.id FROM izposoja i WHERE i.id=io.izposoja_id)) AND (oprema_id=?);");
            prepare.setTimestamp(1,datum);
            prepare.setInt(2,oprema);
            prepare.execute();
            prepare.close();
            connect.close();
        }catch (Exception e){e.printStackTrace();}
    }

    public void izposodiGumb(Timestamp datum_i, String uporabnik, Integer oprema) throws SQLException {
        //connect = Baza.connectDb();
        connect = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp", "iwlqnlkp", "4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
        try{
            prepare= connect.prepareStatement("SELECT vnesi_izposojo(?,?,?)");
            prepare.setTimestamp(1,datum_i);
            prepare.setString(2,uporabnik);
            prepare.setInt(3,oprema);
            prepare.execute();
            prepare.close();
            connect.close();
        }catch (Exception e){e.printStackTrace();}
    }

    public Integer izpisi_num1(String studio_ime) throws SQLException {
        //connect = Baza.connectDb();
        connect = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp", "iwlqnlkp", "4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
        try{
            prepare= connect.prepareStatement("SELECT st_kosov_opreme FROM studiji WHERE ime=?;");
            prepare.setString(1,studio_ime);
            result = prepare.executeQuery();
            if (result.next()) {
                return result.getInt("st_kosov_opreme");
            }
            prepare.close();
            connect.close();
            result.close();
        }catch (Exception e){e.printStackTrace();}
        return 0;
    }

    public Integer izpisi_num2(String studio_ime) throws SQLException {
        //connect = Baza.connectDb();
        connect = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp", "iwlqnlkp", "4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
        try{
            prepare= connect.prepareStatement("SELECT st_uporabnikov FROM studiji WHERE ime=?;");
            prepare.setString(1,studio_ime);
            result = prepare.executeQuery();
            if (result.next()) {
                return result.getInt("st_uporabnikov");
            }
            prepare.close();
            connect.close();
            result.close();
        }catch (Exception e){e.printStackTrace();}
        return 0;
    }
    public Integer izpisi_pie1(String studio_ime) throws SQLException {
        //connect = Baza.connectDb();
        connect = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp", "iwlqnlkp", "4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
        try{
            prepare= connect.prepareStatement("SELECT * FROM get_pielistdata(?);");
            prepare.setString(1,studio_ime);
            result = prepare.executeQuery();
            if (result.next()) {
                return result.getInt("get_pielistdata");
            }
            prepare.close();
            connect.close();
            result.close();
        }catch (Exception e){e.printStackTrace();}
        return 0;
    }
    public Integer izpisi_pie2(String studio_ime) throws SQLException {
        //connect = Baza.connectDb();
        connect = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp", "iwlqnlkp", "4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
        try{
            prepare= connect.prepareStatement("SELECT * FROM get_pie2listdata(?);");
            prepare.setString(1,studio_ime);
            result = prepare.executeQuery();
            if (result.next()) {
                return result.getInt("get_pie2listdata");
            }
            prepare.close();
            connect.close();
            result.close();
        }catch (Exception e){e.printStackTrace();}
        return 0;
    }

    //Tjas
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
                if(ugibajOpreme.getIme().toLowerCase().contains(searchKey))
                {
                    return true;
                }
                else if(ugibajOpreme.getOpis().toLowerCase().contains(searchKey))
                {
                    return true;
                }
                else if(ugibajOpreme.getOznaka().toLowerCase().contains(searchKey))
                {
                    return true;
                }
                else if(ugibajOpreme.getVrsta().toLowerCase().contains(searchKey))
                {
                    return true;
                }
                return false;
            });
        });

        SortedList<Opreme> urejenList = new SortedList<>(filter);
        urejenList.comparatorProperty().bind(tabela_studijo.comparatorProperty());
        tabela_studijo.setItems(urejenList);


    }

    private FileInputStream fis;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        img_slika.fitHeightProperty();
        img_slika.fitWidthProperty();
        //Napolnemo vrsto
        Baza.napolniVrsto(combo_vrsta);
        btn_posodobi.setDisable(true);
        btn_izbrisi.setDisable(true);
        Baza.napolniOpremo(Baza.studio_id,tabela_studijo); //Napolne studio tabelo
        row_ime.setCellValueFactory(new PropertyValueFactory<Opreme, String>("Ime"));
        row_vrsta.setCellValueFactory(new PropertyValueFactory<Opreme,String>("Vrsta"));
        row_oznaka.setCellValueFactory(new PropertyValueFactory<Opreme,String>("Oznaka"));
        row_opis.setCellValueFactory(new PropertyValueFactory<Opreme,String>("Opis"));



        combo_izposoja.getItems().addAll("Izposoja", "Vrni");
        combo_izposoja.setPromptText("Izberi");
        izposodi_pane.setVisible(false);
        vracilo_pane.setVisible(true);
        try {
            poisciCombo();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        prikaziUsername();
        try {
            findStudio(getData.username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        datum_vrnitve.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

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
                    new Shake(text_ime).play();
                    new Shake(li).play();

                }
                else {
                    aktiven = true;
                    error_ime.setText("");
                }
                if(combo_vrsta.getItems().isEmpty())
                {
                    aktiven = false;
                    error_vrsta.setText("Vnesite vrsto!");
                    new Shake(combo_vrsta).play();
                    new Shake(lv).play();

                }
                else {
                    error_vrsta.setText("");
                    aktiven = true;
                }
                if(text_oznaka.getText().isEmpty())
                {
                    //Preveri ali je vse pravilno vnešeno v polja
                    error_oznaka.setText("Vnesite oznako!");
                    new Shake(text_oznaka).play();
                    new Shake(lo).play();

                    aktiven = false;
                }
                else {
                    error_oznaka.setText("");
                    aktiven = true;
                }
                if(text_opis.getText().isEmpty())
                {

                    error_opis.setText("Vnesite opis!");
                    new Shake(text_opis).play();
                    new Shake(lop).play();

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
                    file=null;
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
                    new Shake(text_ime).play();
                    new Shake(li).play();

                }
                else {
                    aktiven = true;
                    error_ime.setText("");
                }
                if(combo_vrsta.getItems().isEmpty())
                {
                    aktiven = false;
                    error_vrsta.setText("Vnesite vrsto!");
                    new Shake(combo_vrsta).play();
                    new Shake(lv).play();

                }
                else {
                    error_vrsta.setText("");
                    aktiven = true;
                }
                if(text_oznaka.getText().isEmpty())
                {
                    //Preveri ali je vse pravilno vnešeno v polja
                    error_oznaka.setText("Vnesite oznako!");
                    new Shake(text_oznaka).play();
                    new Shake(lo).play();

                    aktiven = false;
                }
                else {
                    error_oznaka.setText("");
                    aktiven = true;
                }
                if(text_opis.getText().isEmpty())
                {

                    error_opis.setText("Vnesite opis!");
                    new Shake(text_opis).play();
                    new Shake(lop).play();

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
                    file=null;
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
        vrni_gumb.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    vrniGumb(Integer.parseInt(id_txt.getText()), Timestamp.valueOf(datum_vrnitve.getValue().atStartOfDay()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Alert alert;
                alert= new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Vračilo");
                alert.setHeaderText(null);
                alert.setContentText("Uspešno ste vrnili opremo!");
                alert.showAndWait();
                try {
                    izposojaPrikaziVrniListData();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                ObservableList<PieChart.Data> pieChartData = pie_chart.getData();

                try {
                    pieChartData.get(0).setPieValue(izpisi_pie1(getData.studio));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                try {
                    pieChartData.get(1).setPieValue(izpisi_pie2(getData.studio));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                pie_chart.setData(pieChartData);
            }
        });

        izposodi_gumb.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    izposodiGumb(Timestamp.valueOf(datum_izposoje.getValue().atStartOfDay()), getData.username, Integer.parseInt(id_txt2.getText()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Alert alert;
                alert= new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Izposoja");
                alert.setHeaderText(null);
                alert.setContentText("Uspešno ste si izposodili opremo!");
                alert.showAndWait();
                try {
                    izposojaPrikaziListData();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                ObservableList<PieChart.Data> pieChartData = pie_chart.getData();

                try {
                    pieChartData.get(0).setPieValue(izpisi_pie1(getData.studio));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                try {
                    pieChartData.get(1).setPieValue(izpisi_pie2(getData.studio));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                pie_chart.setData(pieChartData);
            }
        });

        ObservableList<PieChart.Data> pieChartData = null;
        try {
            pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Prosto", izpisi_pie1(getData.studio)),
                    new PieChart.Data("Izposojeno", izpisi_pie2(getData.studio))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        pie_chart.getData().addAll(pieChartData);


        try {
            st_kosov.setText(izpisi_num1(getData.studio).toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            st_uporabnikov.setText(izpisi_num2(getData.studio).toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        btn_import.setOnAction(e -> {
            String query = "INSERT INTO oprema(oznaka, ime, opis, studio_id, vrsta_id) VALUES (?, ?, ?, (SELECT id FROM studiji WHERE ime=?), (SELECT id FROM vrsta WHERE ime=?));";
            try {
                connect = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp", "iwlqnlkp", "4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
                prepare = connect.prepareStatement(query);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            FileInputStream fileIn;
            try {
                fileIn = new FileInputStream(new File("Oprema.xlsx"));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            XSSFWorkbook wb;
            try {
                wb = new XSSFWorkbook(fileIn);
                XSSFSheet sheet = wb.getSheetAt(0);
                Row row;
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    prepare.setString(1, row.getCell(0).getStringCellValue());
                    prepare.setString(2, row.getCell(1).getStringCellValue());
                    prepare.setString(3, row.getCell(2).getStringCellValue());
                    prepare.setString(4, row.getCell(3).getStringCellValue());
                    prepare.setString(5, row.getCell(4).getStringCellValue());
                    prepare.execute();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Vračilo");
            alert.setHeaderText(null);
            alert.setContentText("Uspešno ste uvozili opremo!");
            alert.showAndWait();
            try {
                wb.close();
                fileIn.close();
                prepare.close();
                Baza.napolniOpremo(Baza.studio_id,tabela_studijo); //Napolne studio tabelo
                row_ime.setCellValueFactory(new PropertyValueFactory<Opreme, String>("Ime"));
                row_vrsta.setCellValueFactory(new PropertyValueFactory<Opreme,String>("Vrsta"));
                row_oznaka.setCellValueFactory(new PropertyValueFactory<Opreme,String>("Oznaka"));
                row_opis.setCellValueFactory(new PropertyValueFactory<Opreme,String>("Opis"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
