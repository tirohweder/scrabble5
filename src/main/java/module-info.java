module com.scrab5.main {
<<<<<<< HEAD
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
=======
  requires javafx.controls;
  requires javafx.fxml;
  requires org.junit.jupiter.api;
  requires java.desktop;
>>>>>>> refs/heads/network

  opens com.scrab5.ui to javafx.fxml;

  exports com.scrab5.ui;
}
