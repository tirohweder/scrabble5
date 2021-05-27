package com.scrab5.ui;

import com.scrab5.util.database.Database;
import com.scrab5.util.database.PlayerProfileDatabase;
import com.scrab5.util.database.UseDatabase;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

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
    Database.disconnect();
  }

  /**
   * Event method that is called when the "Login"-button in the UI is clicked. If a Profile has been
   * selected the volume settings are loaded and the scene gets changed to "MainMenu.fxml",
   * otherwise a Pop-Up message is created.
   *
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   * @author mherre
   */
  @FXML
  private void login() throws IOException {

    playSound("ButtonClicked.mp3");
    Database.reconnect();

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
    Database.disconnect();
  }

  /**
   * Event method that is called when an item in <code>profileSelection</code> has been selected.
   * Displays the name of the selected profile and sets <code>isProfileSelected</code> on
   * <code>true</code>.
   *
   * @author mherre
   */
  @FXML
  private void setSelectedProfile() {
    String selected = this.profileSelection.getValue();
    Data.setCurrentUser(selected);
    this.nameDisplaying.setText(selected);
    this.isProfileSelected = true;
  }

  /**
   * Event method that is called when the "Arrow Down"-button in the UI is clicked. Opens the
   * dropbox which displays the profiles.
   *
   * @author mherre
   */
  @FXML
  private void clickComboBox() {
    playSound("ButtonClicked.mp3");
    this.profileSelection.show();
  }

  /**
   * Event method that is called when <code>profileSelection</code> is clicked. Ensures that
   * <code>profileSelection</code> only opens when the "Arrow Down"-button is clicked.
   *
   * @author mherre
   */
  @FXML
  private void dontShow() {
    this.profileSelection.hide();
  }

  /**
   * Event method that is called when the "Quit"-button in the UI is clicked. Depending on the
   * predecessor scene the game either closes or changes the scene to "Profile.fxml".
   *
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   * @author mherre
   */
  @FXML
  private void close(MouseEvent event) throws IOException {
    if ("Profile".equals(predecessor)) {
      App.setRoot(predecessor);
    } else {
      closeGame(event);
    }
  }

  /**
   * Sets the predecessor scene of this scene.
   *
   * @param predecessorPara the String valuing the name of the predecessor scene
   * @author mherre
   */
  public static void setPredecessor(String predecessorPara) {
    predecessor = predecessorPara;

  }
}
