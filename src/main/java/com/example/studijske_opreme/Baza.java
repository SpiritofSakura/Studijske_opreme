package com.example.studijske_opreme;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.concurrent.ExecutionException;

public class Baza {

    public static Integer studio_id;
    public static ObservableList<Opreme> list_iskanje = null;

    public static Connection connectDb(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp", "iwlqnlkp", "4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
            return connection;
        }catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void Prijava(String username, String geslo)
    {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        //Ucitelj ucitelj;
        try{
            connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp","iwlqnlkp","4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
            stmt = connection.prepareStatement("SELECT studio_id,username,pass FROM uporabniki WHERE username = ?");
            stmt.setString(1,username);
            resultSet = stmt.executeQuery();

            if(!resultSet.isBeforeFirst()) {
                System.out.println("Napačno uporabniško ime!"); //POSODOBI
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Vnešeni podatki so napačni!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String pridobljeno_geslo = resultSet.getString("pass");
                    //Še pridobiš vse ostale parametre...
                    //..
                    if (pridobljeno_geslo.equals(geslo)) {
                        Integer st_id = resultSet.getInt("studio_id"); //<--- Bo shranilo njegove podatke v nek objekt da se izpišejo dokončaj
                        Baza.studio_id = st_id; //zabeležu studio_id;
                        Main.changeScene("homepage.fxml",900,600);

                    } else {
                        System.out.println("Geslo se ne ujema!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Vnešeni podatki so napačni!");
                        alert.show();
                    }
                }
            }
        }catch (SQLException | IOException e){
            e.printStackTrace();
        }
        finally {
            if (resultSet != null)
            {
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try {
                    stmt.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }


    public static void registrijajUporabnika(String ime, String priimek, String eposta, String username, String ime_s, String geslo){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement check = null;
        ResultSet resultSet = null;
        //Ucitelj ucitelj;

        try {

            connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp","iwlqnlkp","4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
            check = connection.prepareStatement("SELECT username,eposta FROM uporabniki WHERE username = ? OR eposta = ?");
            check.setString(1,username);
            check.setString(2,eposta);
            resultSet = check.executeQuery();


            if (resultSet.isBeforeFirst()) { //Če uporabnik že obstaja
                System.out.println("Uporabnik že obstaja.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Ne moraš uporabiti tega uporabniškega gesla oz epoštnega računa ker že obstaja, ker že obstaja.");
                alert.show();
            }
            else {
                psInsert = connection.prepareStatement("INSERT INTO uporabniki(ime,priimek,username,pass,eposta,studio_id,admin) VALUES(?,?,?,?,?,(select id from studiji where ime = ?),false)"); //Vstavljanje uporabnikov
                psInsert.setString(1,ime);
                psInsert.setString(2,priimek);
                psInsert.setString(3,username);
                psInsert.setString(4,geslo);
                psInsert.setString(5,eposta);
                psInsert.setString(6,ime_s);



                psInsert.execute();


//                PreparedStatement stmt = null;
//                ResultSet resultSet1 = null;
//
//                stmt = connection.prepareStatement("SELECT studio_id FROM studiji WHERE ime = ?");
//                stmt.setString(1,ime_s);
//                resultSet1 = stmt.executeQuery();
//
//                while(resultSet1.next()){
//                    Integer st_id = resultSet.getInt("studio_id"); //<--- Bo shranilo njegove podatke v nek objekt da se izpišejo dokončaj
//                    Baza.studio_id = st_id;
//                }
//                resultSet1.close();
//                stmt.close();


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Uporabnik uspešno ustvarjen!");
                alert.show();
                Main.changeScene("login.fxml",800,500); //Preusmeritev
            }
        } catch (SQLException | IOException e)
        {
            e.printStackTrace();
        }
        finally {
            if (resultSet != null)
            {
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }                                                                       //Pravilno zapiranje povezave na varen nacin.
            }
            if (check != null)
            {
                try {
                    check.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert != null)
            {
                try {
                    psInsert.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }
    public static String  SifrirajGeslo(String geslo) throws NoSuchAlgorithmException {
        //Šifriranje gesla
        /* MessageDigest instance for MD5. */
        MessageDigest m = MessageDigest.getInstance("MD5");

        /* Add plain-text password bytes to digest using MD5 update() method. */
        m.update(geslo.getBytes());

        /* Convert the hash value into bytes */
        byte[] bytes = m.digest();

        /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
        StringBuilder s = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        /* Complete hashed password in hexadecimal format */
        String encryptedpassword = s.toString();
        return encryptedpassword;
    }

    public static void  napolniKraje(ComboBox combo_kraji) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;


        try{
            connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp","iwlqnlkp","4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
            stmt = connection.prepareStatement("SELECT ime FROM kraji LIMIT 1");

            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String kraj = resultSet.getString("ime");
                combo_kraji.getItems().add(kraj);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (resultSet != null)
            {
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try {
                    stmt.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }

    public static void dodajStudijo(String ime_s, String naslov,String eposta, String telefon, String kraj)
    {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement check = null;
        ResultSet resultSet = null;
        //Ucitelj ucitelj;

        try {

            connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp","iwlqnlkp","4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
            check = connection.prepareStatement("SELECT ime FROM studiji WHERE ime = ?");
            check.setString(1,ime_s);
            resultSet = check.executeQuery();

            if (resultSet.isBeforeFirst()) { //Če uporabnik že obstaja
                System.out.println("Studijo že obstaja.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Ne moraš uporabiti tega imena za vaš studijo, ker že obstaja.");
                alert.show();
            }
            else {
                psInsert = connection.prepareStatement("INSERT INTO studiji(ime,naslov,eposta,telefon,kraj_id) VALUES(?,?,?,?,(select id from kraji where ime = ?))"); //Vstavljanje uporabnikov
                psInsert.setString(1,ime_s);
                psInsert.setString(2,naslov);
                psInsert.setString(3,eposta);
                psInsert.setString(4,telefon);
                psInsert.setString(5,kraj);


                psInsert.execute();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Studijo uspešno ustvarjen!");
                alert.show();
                Main.changeScene("registration.fxml",800,500); //Preusmeritev
            }
        } catch (SQLException | IOException e)
        {
            e.printStackTrace();
        }
        finally {
            if (resultSet != null)
            {
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }                                                                       //Pravilno zapiranje povezave na varen nacin.
            }
            if (check != null)
            {
                try {
                    check.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert != null)
            {
                try {
                    psInsert.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }


    public static void  napolniStudije(ComboBox combo_studijo) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;


        try{
            connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp","iwlqnlkp","4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
            stmt = connection.prepareStatement("SELECT ime FROM studiji");

            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String studijo = resultSet.getString("ime");
                combo_studijo.getItems().add(studijo);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (resultSet != null)
            {
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try {
                    stmt.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }

    public static boolean pridobiEmail(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;


        connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp", "iwlqnlkp", "4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
        stmt = connection.prepareStatement("SELECT * FROM uporabniki WHERE eposta = ?");
        stmt.setString(1, email);
        resultSet = stmt.executeQuery();

        if (resultSet.isBeforeFirst()) {
            stmt.close();
            resultSet.close();
            connection.close();
            return true;


        } else {
            stmt.close();
            resultSet.close();
            connection.close();
            return false;


        }

    }



    public static void  napolniOpremo(Integer s_id, TableView<Opreme> tabela) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        Opreme oprema;
        ObservableList<Opreme> list = FXCollections.observableArrayList();


        try{

            connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp","iwlqnlkp","4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
            stmt = connection.prepareStatement("SELECT o.id,o.oznaka,o.ime as ime,o.opis,v.ime as vrsta FROM oprema o INNER JOIN vrsta v ON v.id = o.vrsta_id INNER JOIN studiji s on s.id = o.studio_id WHERE s.id = ?");
            stmt.setInt(1, s_id);

            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Integer id = (resultSet.getInt("id"));
                String oznaka = resultSet.getString("oznaka");
                String ime = resultSet.getString("ime");
                String opis = resultSet.getString("opis");
                String vrsta = resultSet.getString("vrsta");

                oprema = new Opreme(id,oznaka,ime,opis,vrsta);
                list.add(oprema);

            }
            if(list.isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("POZOR: Ta studijo nima še nobene opreme.");
                alert.show();
            }
            else
            {
                list_iskanje = list;
                tabela.setItems(list);
            }



        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (resultSet != null)
            {
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try {
                    stmt.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }


    public static void dodajOpremo(String text_ime, String text_vrsta,String text_opis, String text_oznaka,Integer s_id,File img) throws FileNotFoundException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement check = null;
        ResultSet resultSet = null;


        try {

            connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp","iwlqnlkp","4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
            check = connection.prepareStatement("SELECT oznaka FROM oprema WHERE oznaka = ?");
            check.setString(1,text_oznaka);
            resultSet = check.executeQuery();

            if (resultSet.isBeforeFirst()) { //Če uporabnik že obstaja
                System.out.println("Ta oznaka že obstaja!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Ne moraš uporabiti te oznake za vašo novo opremo, ker že obstaja. Prosim vnesite novo");
                alert.show();
            }
            else {
                if(img != null)
                {
                    FileInputStream slika = new FileInputStream(img);
                    psInsert = connection.prepareStatement("INSERT INTO oprema(oznaka,ime,opis,studio_id,vrsta_id,slika) VALUES(?,?,?,?,(select id from vrsta where ime = ?),?)"); //Vstavljanje uporabnikov
                    psInsert.setString(1, text_oznaka);
                    psInsert.setString(2, text_ime);
                    psInsert.setString(3, text_opis);
                    psInsert.setInt(4, s_id);
                    psInsert.setString(5, text_vrsta);
                    psInsert.setBinaryStream(6, slika,(int) img.length());
                }
                else
                {
                    psInsert = connection.prepareStatement("INSERT INTO oprema(oznaka,ime,opis,studio_id,vrsta_id,slika) VALUES(?,?,?,?,(select id from vrsta where ime = ?),?)"); //Vstavljanje uporabnikov
                    psInsert.setString(1, text_oznaka);
                    psInsert.setString(2, text_ime);
                    psInsert.setString(3, text_opis);
                    psInsert.setInt(4, s_id);
                    psInsert.setString(5, text_vrsta);
                    psInsert.setBinaryStream(6, null);
                }

                psInsert.execute();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Oprema uspešno ustvarjena!");
                alert.show();

            }
        } catch (SQLException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.toString());
            alert.show();
        }
        finally {
            if (resultSet != null)
            {
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }                                                                       //Pravilno zapiranje povezave na varen nacin.
            }
            if (check != null)
            {
                try {
                    check.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert != null)
            {
                try {
                    psInsert.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }

    public static void  posodobiOpremo(Integer o_id,String text_ime, String text_oznaka, String text_vrsta, String text_opis, Integer st_id,File slika) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try{

            connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp","iwlqnlkp","4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
            stmt = connection.prepareStatement("UPDATE oprema SET id = ?,oznaka = ?, ime = ?, opis = ?, studio_id = ?, vrsta_id = (select id from vrsta where ime = ?),slika = ? WHERE id = ?");
            if(slika != null)
            {
                FileInputStream img = new FileInputStream(slika);
                stmt.setInt(1, o_id);
                stmt.setString(2, text_oznaka);
                stmt.setString(3, text_ime);
                stmt.setString(4, text_opis);
                stmt.setInt(5, st_id);
                stmt.setString(6, text_vrsta);
                stmt.setBinaryStream(7,img,(int) slika.length());
                stmt.setInt(8, o_id);
            }
            else
            {
                stmt.setInt(1, o_id);
                stmt.setString(2, text_oznaka);
                stmt.setString(3, text_ime);
                stmt.setString(4, text_opis);
                stmt.setInt(5, st_id);
                stmt.setString(6, text_vrsta);
                stmt.setBinaryStream(7,null);
                stmt.setInt(8, o_id);
            }
            stmt.execute();


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Oprema je bila uspešno posodobljena");
            alert.show();


        }catch (SQLException e){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.toString());
            alert.show();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {

            if (stmt != null)
            {
                try {
                    stmt.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }

    public static void  napolniVrsto(ComboBox combo_vrsta) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;


        try{
            connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp","iwlqnlkp","4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
            stmt = connection.prepareStatement("SELECT ime FROM vrsta");

            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String vrsta = resultSet.getString("ime");
                combo_vrsta.getItems().add(vrsta);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (resultSet != null)
            {
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try {
                    stmt.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }

    public static void dodajVrsto(String vrsta,String opis)
    {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement check = null;
        ResultSet resultSet = null;
        //Ucitelj ucitelj;

        try {

            connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp","iwlqnlkp","4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
            check = connection.prepareStatement("SELECT ime FROM vrsta WHERE ime = ?");
            check.setString(1,vrsta);
            resultSet = check.executeQuery();

            if (resultSet.isBeforeFirst()) { //Če uporabnik že obstaja
                System.out.println("Vrsta že obstaja");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Ne moraš uporabiti tega imena za vrsto, ker že obstaja.");
                alert.show();
            }
            else {
                psInsert = connection.prepareStatement("INSERT INTO vrsta(ime,opis) VALUES(?,?)"); //Vstavljanje uporabnikov
                psInsert.setString(1,vrsta);
                psInsert.setString(2,opis);



                psInsert.execute();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Vrsta uspešno dodana!");
                alert.show();
                Main.changeScene("homepage.fxml",900,600); //Preusmeritev
            }
        } catch (SQLException | IOException e)
        {
            e.printStackTrace();
        }
        finally {
            if (resultSet != null)
            {
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }                                                                       //Pravilno zapiranje povezave na varen nacin.
            }
            if (check != null)
            {
                try {
                    check.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert != null)
            {
                try {
                    psInsert.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }

    public static void  spremeniGeslo(String g,String posta) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try{

            connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp","iwlqnlkp","4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
            stmt = connection.prepareStatement("UPDATE uporabniki SET pass = ? WHERE eposta = ?");
            stmt.setString(1, g);
            stmt.setString(2, posta);

            stmt.execute();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Geslo je bilo uspešno posodobljeno!");
            alert.show();
            Main.changeScene("login.fxml",800,500);


        }catch (SQLException e){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.toString());
            alert.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

            if (stmt != null)
            {
                try {
                    stmt.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }


    public static void UvoziSliko(File img,Integer  s_id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;


        try {

            connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp","iwlqnlkp","4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
            stmt = connection.prepareStatement("UPDATE oprema SET slika = ? WHERE id = ?");
            try(FileInputStream slika = new FileInputStream(img))
            {
                stmt.setBinaryStream(1, slika,(int) img.length());
                stmt.setInt(2, s_id);
                stmt.executeUpdate();
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }

            stmt.close();
            connection.close();
            System.out.println("Success!");

        } catch (Exception e1) {
            System.out.println(e1.toString());
            e1.printStackTrace();
            stmt.close();
            connection.close();
        }

    }


    public static BufferedImage DobiSliko(Integer o_id)
    {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        BufferedImage slika = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp","iwlqnlkp","4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
            stmt = connection.prepareStatement("SELECT slika FROM oprema WHERE id = ?");
            stmt.setInt(1,o_id);
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {


                byte[] buf = resultSet.getBytes("slika");
                if (buf != null) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(buf);
                    slika = ImageIO.read(bis);
                }
            }

            stmt.close();
            resultSet.close();
            connection.close();
            System.out.println("Succenss!");

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                /* Ignored */ }
        }
        return slika;
    }

    public static void  izbrisiOpremo(Integer o_id) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try{

            connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp","iwlqnlkp","4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
            stmt = connection.prepareStatement("DELETE FROM oprema WHERE id = ?");
            stmt.setInt(1, o_id);

            stmt.execute();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Oprema je bila uspešno izbrisana");
            alert.show();


        }catch (SQLException e){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.toString());
            alert.show();
        }
        finally {
            if (stmt != null)
            {
                try {
                    stmt.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }

}
