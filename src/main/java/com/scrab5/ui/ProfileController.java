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
  private void deleteProfile(MouseEvent event) throws IOException {

    String message = "Are you sure you want to delete this profile? (You can't undo this)";
    PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.CONFIRMATION);
    pum.show();

    if (Data.isConfirmed()) {

      // Hier muss das Profile gelöscht werden @Laura

      App.setRoot("AccountCreation");
    }
  }

  @FXML
  private void editName(MouseEvent event) throws IOException {

    String message = "Enter your changes and click 'Okay'";
    PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.INPUT);
    pum.show();

    // Mit Data.getCurrentUser holst du dir den aktualisierten Benutzernamen, den solltest du dann
    // auch hier ändern
  }

  @FXML
  private void createNewPlayerProfile(MouseEvent event) {

  }

  @FXML
  private void back(MouseEvent event) throws IOException {
    App.setRoot("MainMenu");
  }

}
