module com.scrab5.main {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;

  opens com.scrab5.ui to javafx.fxml;

  exports com.scrab5.ui;
}
