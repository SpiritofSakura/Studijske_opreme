module com.example.studijske_opreme {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.studijske_opreme to javafx.fxml;
    exports com.example.studijske_opreme;
}