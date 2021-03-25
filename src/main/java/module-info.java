module com.scrab5.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.scrab5.example to javafx.fxml;
    exports com.scrab5.example;
}