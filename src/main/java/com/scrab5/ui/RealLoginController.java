package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.scrab5.util.database.UseDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

public class RealLoginController extends Controller implements Initializable {

  @FXML
  private ComboBox profileSelection;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    profileSelection.setItems(UseDatabase.getAllPlayer());

  }

  @FXML
  private void login(MouseEvent event) throws IOException {
    App.setRoot("MainMenu");
  }

  @FXML
  private void setSelectedProfile(ActionEvent event) {
    System.out.println("HAÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖ");

  }

}
