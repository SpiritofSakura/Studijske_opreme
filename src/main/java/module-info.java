module com.example.studijske_opreme {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires AnimateFX;


    opens com.example.studijske_opreme to javafx.fxml;
    exports com.example.studijske_opreme;
}