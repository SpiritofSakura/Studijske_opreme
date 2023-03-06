package com.example.studijske_opreme;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Baza {


    public static void Prijava(String username, String geslo)
    {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        //Ucitelj ucitelj;
        try{
            connection = DriverManager.getConnection("jdbc:postgresql://trumpet.db.elephantsql.com/iwlqnlkp","iwlqnlkp","4d4ciVVucqCHoPNGX6kvzHghAt0QajIq");
            stmt = connection.prepareStatement("SELECT username,pass FROM uporabniki WHERE username = ?");
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
//                        String ime = resultSet.getString("ime"); <--- Bo shranilo njegove podatke v nek objekt da se izpišejo dokončaj
//                        String priimek = resultSet.getString("priimek");
//                        String uporabnik = resultSet.getString("username");
//                        String tel_st = resultSet.getString("tel_st");
//                        String eposta = resultSet.getString("eposta");

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
            check = connection.prepareStatement("SELECT username FROM uporabniki WHERE username = ?");
            check.setString(1,username);
            resultSet = check.executeQuery();

            if (resultSet.isBeforeFirst()) { //Če uporabnik že obstaja
                System.out.println("Uporabnik že obstaja.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Ne moraš uporabiti tega uporabniškega gesla, ker že obstaja.");
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
                //ucitelj = new Ucitelj(ime,priimek,eposta,telefon,username);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Uporabnik uspešno ustvarjen!");
                alert.show();
                Main.changeScene("home-page.fxml",829,451); //Preusmeritev
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

}
