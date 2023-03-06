package com.example.studijske_opreme;

import javafx.scene.control.Label;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    public boolean posljiEmail(Integer koda, String eposta){
        try {


            Properties properties = new Properties();
            properties.put("mail.smtp.auth", true);
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", 587);
            properties.put("mail.smtp.starttls.enable", true);
            properties.put("mail.transport.protocl", "smtp");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("tjas.jelen@gmail.com", "fctnouxffbkxldqu"); //Pošiljatelj
                }
            });


            Message message = new MimeMessage(session);
            message.setSubject("Koda za spremembo gesla!");
            message.setContent("<h1>Pozdravljeni!</h1><br><h3>Tukaj je vaša koda za spremembo gesla: " + koda + "</h3>", "text/html");

            Address addressTo = new InternetAddress(eposta);
            message.setRecipient(Message.RecipientType.TO, addressTo); //Komu pošljemo email

            Transport.send(message);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }
}
