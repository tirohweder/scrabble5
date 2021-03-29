module com.scrab5.main {
  requires javafx.controls;
  requires javafx.fxml;
  requires org.junit.jupiter.api;

  opens com.scrab5.ui to javafx.fxml;

  exports com.scrab5.ui;
}
