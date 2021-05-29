package com.scrab5.ui;

import com.scrab5.util.database.Database;
import com.scrab5.util.database.FillDatabase;
import com.scrab5.util.database.PlayerProfileDatabase;
import com.scrab5.util.database.UseDatabase;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * The AccountCreationController class controls the components of the "AccountCreation.fxml".
 *
 * @author mherre
 */
public class AccountCreationController extends Controller implements Initializable {

  private static String predecessor = "";
  @FXML
  private TextField nickname;

  /**
   * Sets the predescessor scene of AccountCreationController.
   *
   * @author mherre
   * @param predecessorPara the name of the scene that was shown before
   */
  public static void setPredecessor(String predecessorPara) {
    predecessor = predecessorPara;
  }

  /**
   * Call certain methods as soon as the Controller is loaded.
   *
   * @author mherre
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.nickname.setFocusTraversable(false);
  }

  /**
   * Event method that is called when the "Back"-button in the UI is clicked. The scene gets changed
   * to a certain predecessor scene.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void back(MouseEvent event) throws IOException {
    playSound();

    if ("Profile".equals(predecessor)) {
      App.setRoot("Profile");
    } else {
      App.setRoot("Login");
    }
  }

  /**
   * Event method that is called when "Enter" is pressed on the key board. If a valid username has
   * been entered the scene and the audio volume gets adopted.
   *
   * @author mherre
   * @param event the KeyEvent that is created when a key is pressed on the key board
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void enterPressed(KeyEvent event) throws IOException {
    if (event.getCode() == KeyCode.ENTER && this.isUsernameValid(this.nickname.getText())) {
      App.setMusicVolume(PlayerProfileDatabase.getMusicVolume(Data.getCurrentUser()));
      Data.setSFXVolume(PlayerProfileDatabase.getSoundEffectVolume(Data.getCurrentUser()));
      App.setRoot("MainMenu");
    }
  }

  /**
   * Event method that is called when the "Enter"-button is clicked in the UI. If a valid username
   * has been entered the scene and the audio volume gets adopted.
   *
   * @author mherre
   * @param event the MouseEvent that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void enter(MouseEvent event) throws IOException {
    playSound();
    if (this.isUsernameValid(this.nickname.getText())) {
      App.setMusicVolume(PlayerProfileDatabase.getMusicVolume(Data.getCurrentUser()));
      Data.setSFXVolume(PlayerProfileDatabase.getSoundEffectVolume(Data.getCurrentUser()));
      App.setRoot("MainMenu");
    }
  }

  /**
   * Checks if a nickname only consists of letters, numbers and underscores and is at least 1 char
   * long but no longer than 12 chars. In case the entered username doesn't fullfill the criteria
   * the game will show an error message explaining why the username isn't valid.
   *
   * <p>
   * In case the nickname fullfills the criteria a new profile gets generated in the database and
   * the user gets shown a confirmation message.
   *
   * @author mherre
   * @param username the string containing the username thats tested
   * @return the boolean describing if the username is valid
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  private boolean isUsernameValid(String username) throws IOException {

    String regex = "[a-zA-Z0-9_]{1,12}";
    String message;
    PopUpMessage pum;

    if (username.matches(regex)) {
      if (!UseDatabase.playerExists(this.nickname.getText())) {
        Data.setCurrentUser(username);
        FillDatabase.createPlayer(username);
        FillDatabase.createServerRow(Data.getCurrentUser(), Data.getCurrentUser(),
            InetAddress.getLocalHost().getHostAddress());

        message = "Congratulations! Your account has been created";
        pum = new PopUpMessage(message, PopUpMessageType.NOTIFICATION);
        pum.show();
        Database.disconnect();
        return true;

      } else {
        message = "This username already exists. Please choose a different name!";
        pum = new PopUpMessage(message, PopUpMessageType.ERROR);
        pum.show();

        return false;
      }

    } else {
      message = "Please make sure your nickname consists only of letters, numbers, "
          + "underscores and is only 12 signs long";
      pum = new PopUpMessage(message, PopUpMessageType.ERROR);
      pum.show();

      return false;
    }
  }
}
