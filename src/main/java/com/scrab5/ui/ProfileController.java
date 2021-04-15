package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.scrab5.util.database.FillDatabase;
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
      FillDatabase.deletePlayer(Data.getCurrentUser());
      System.out.println("Current: " + Data.getCurrentUser());
      App.setRoot("AccountCreation");
    }
  }

  @FXML
  private void editName(MouseEvent event) throws IOException {

    String message = "Enter your changes and click 'Okay'";
    PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.INPUT);
    pum.show();
    FillDatabase.updatePlayer("Name", Data.getCurrentUser(), Data.getInputFieldText(), 0);
    Data.setCurrentUser(Data.getInputFieldText());

  }

  @FXML
  private void createNewPlayerProfile(MouseEvent event) {

  }

  @FXML
  private void back(MouseEvent event) throws IOException {
    App.setRoot("MainMenu");
  }

  /*
   * public static void main(String[] args) { Database db = new Database();
   * FillDatabase.deleteTable("Player"); FillDatabase.createPlayer("aura", null);
   * FillDatabase.deletePlayer("aura"); // FillDatabase.updatePlayer("Name", "aura", "paulaner", 0);
   * }
   */

}
