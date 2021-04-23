package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.scrab5.util.database.UseDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class RealLoginController extends Controller implements Initializable {

  @FXML
  private ComboBox profileSelection;
  @FXML
  private Label nameDisplaying;

  private boolean isProfileSelected = false;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    profileSelection.setItems(UseDatabase.getAllPlayer());
  }

  @FXML
  private void login(MouseEvent event) throws IOException {
    if (this.isProfileSelected) {
      App.setRoot("MainMenu");
    } else {
      PopUpMessage pum = new PopUpMessage(
          "You must select a profile to continue. Click on the 'Arrow Down' button and select a profile",
          PopUpMessageType.ERROR);
      pum.show();
    }
  }

  @FXML
  private void setSelectedProfile(ActionEvent event) {
    String selected = (String) this.profileSelection.getValue();
    Data.setCurrentUser(selected);
    this.nameDisplaying.setText(selected);
    this.isProfileSelected = true;
  }

  @FXML
  private void clickComboBox(MouseEvent event) throws IOException {
    this.profileSelection.show();
  }

  @FXML
  private void dontShow(MouseEvent event) throws IOException {
    this.profileSelection.hide();
  }
}
