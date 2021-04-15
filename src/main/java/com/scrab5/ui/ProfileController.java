package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class ProfileController extends Controller implements Initializable {

  @FXML
  private Label nicknameLabel;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    this.nicknameLabel.setText(Data.getCurrentUser());

  }

  @FXML
  private void deleteProfile(MouseEvent event) {}

  @FXML
  private void editName(MouseEvent event) {}

  @FXML
  private void createNewPlayerProfile(MouseEvent event) {

  }

  @FXML
  private void back(MouseEvent event) throws IOException {
    App.setRoot("MainMenu");
  }

}
