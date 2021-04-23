/**
 * @author trohwede
 */

module com.scrab5.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.junit.jupiter.api;
    requires java.desktop;
    requires java.sql;
    requires javafx.base;
    requires javafx.media;

    opens com.scrab5.ui to javafx.fxml;

    exports com.scrab5.ui;
    }
