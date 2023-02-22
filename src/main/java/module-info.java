module com.example.studijska_oprema {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.studijska_oprema to javafx.fxml;
    exports com.example.studijska_oprema;
}