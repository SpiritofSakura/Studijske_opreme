module com.example.studijske_opreme {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires AnimateFX;
    requires com.jfoenix;
    requires java.desktop;
    requires javafx.swing;
    requires java.mail;
    requires apache.commons.net;
    requires org.apache.poi.ooxml;


    opens com.example.studijske_opreme to javafx.fxml;
    exports com.example.studijske_opreme;
}