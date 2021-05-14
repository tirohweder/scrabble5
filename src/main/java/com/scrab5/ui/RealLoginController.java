package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.scrab5.util.database.Database;
import com.scrab5.util.database.PlayerProfileDatabase;
import com.scrab5.util.database.UseDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The RealLoginController class controls the components of the "RealLogin.fxml".
 * 
 * @author mherre
 */
public class RealLoginController extends Controller implements Initializable {

  @FXML
  private ComboBox<String> profileSelection;
  @FXML
  private Label nameDisplaying;

  private boolean isProfileSelected = false;
  private static String predecessor = "";

  /**
   * Call certain methods as soon as the Controller is loaded.
   * 
   * @author mherre
   */
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    profileSelection.setItems(UseDatabase.getAllPlayer());
  }

  /**
   * Event method that is called when the "Login"-button in the UI is clicked. If a Profile has been
   * selected the volume settings are loaded and the scene gets changed to "MainMenu.fxml",
   * otherwise a Pop-Up message is created.
   * 
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void login(MouseEvent event) throws IOException {

    playSound("ButtonClicked.mp3");

    if (this.isProfileSelected) {
      Data.setSFXVolume(PlayerProfileDatabase.getSoundEffectVolume(Data.getCurrentUser()) / 100);
      App.setMusicVolume(PlayerProfileDatabase.getMusicVolume(Data.getCurrentUser()) / 100);
      App.setRoot("MainMenu");

    } else {
      String message = "You must select a profile to continue. "
          + "Click on the 'Arrow Down' button and select a profile";
      PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.ERROR);
      pum.show();

    }
  }

  /**
   * Event method that is called when an item in <code>profileSelection</code> has been selected.
   * Displays the name of the selected profile and sets <code>isProfileSelected</code> on
   * <code>true</code>.
   * 
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void setSelectedProfile(ActionEvent event) {
    String selected = (String) this.profileSelection.getValue();
    Data.setCurrentUser(selected);
    this.nameDisplaying.setText(selected);
    this.isProfileSelected = true;
  }

  @FXML
  private void clickComboBox(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    this.profileSelection.show();
  }

  @FXML
  private void dontShow(MouseEvent event) throws IOException {
    this.profileSelection.hide();
  }

  @FXML
  private void close(MouseEvent event) throws IOException {
    switch (predecessor) {
      case "Profile":
        App.setRoot(predecessor);
        break;
      default:
        Database.disconnect();
        Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        s.close();
        break;
    }
  }

  /**
   * Sets the predescessor scene of AccountCreationController.
   * 
   * @param predecessorPara
   */
  public static void setPredecessor(String predecessorPara) {
    predecessor = predecessorPara;

  }
}
