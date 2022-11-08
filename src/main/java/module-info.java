module com.example.loginsql {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.loginsql to javafx.fxml;
    exports com.example.loginsql;
}