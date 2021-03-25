module com.scrab5 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.scrab5 to javafx.fxml;
    exports com.scrab5;
}