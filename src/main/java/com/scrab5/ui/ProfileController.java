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
  private void deleteProfile(MouseEvent event) {
    String name = Data.getCurrentUser();
    System.out.println(Data.getCurrentUser());
    /* Error: no such colum: name */
    FillDatabase.deletePlayer(name);
  }

  @FXML
  private void editName(MouseEvent event) {
    /* filling name via TextField */
    String name = "";
    FillDatabase.updatePlayer("Name", Data.getCurrentUser(), name, 0);
    Data.setCurrentUser(name);
  }

  @FXML
  private void createNewPlayerProfile(MouseEvent event) {

  }

  @FXML
  private void back(MouseEvent event) throws IOException {
    App.setRoot("MainMenu");
  }

}
