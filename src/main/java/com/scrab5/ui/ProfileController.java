package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

public class ProfileController extends Controller implements Initializable {

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub

  }

  @FXML
  private void back(MouseEvent event) throws IOException {
    App.setRoot("MainMenu");
  }

}
