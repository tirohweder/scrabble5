package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

public class RealLoginController extends Controller implements Initializable {

  @FXML
  private ComboBox<String> profileSelection;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    profileSelection.setItems(null);

  }

  @FXML
  private void login(MouseEvent event) throws IOException {
    App.setRoot("MainMenu");
  }

}
