module com.example.studijske_opreme {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires AnimateFX;
    requires java.mail;
    requires com.jfoenix;
    requires java.desktop;
    requires apache.commons.net;
    requires javafx.swing;


    opens com.example.studijske_opreme to javafx.fxml;
    exports com.example.studijske_opreme;
}