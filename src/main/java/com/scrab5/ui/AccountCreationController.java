package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.scrab5.util.database.FillDatabase;
import com.scrab5.util.database.UseDatabase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * The AccountCreationController class is supposed to control the components of the
 * AccountCreation.fxml.
 * 
 * @author mherre
 *
 */
public class AccountCreationController extends Controller implements Initializable {

  @FXML
  private TextField nickname;
  private String createdUsername;
  private static String predecessor = "";

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.nickname.setFocusTraversable(false);
  }

  /**
   * Event method that is called when the "Back"-button is clicked. Scene gets changed to the
   * predecessor "Login" scene
   * 
   * @author mherre
   * @param event
   * 
   */
  @FXML
  private void back(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");

    switch (predecessor) {

      case "Profile":
        App.setRoot("Profile");
        break;

      default:
        App.setRoot("Login");
        break;
    }

  }

  /**
   * In case that "Enter" is pressed on the keyboard the entered nickname gets checked on validity.
   * 
   * @author mherre
   * @param event the KeyEvent triggering this method
   * @throws IOException If an input or output exception occurred
   */
  @FXML
  private void enterPressed(KeyEvent event) throws IOException {

    switch (event.getCode()) {
      case ENTER:
        this.isUsernameValid();
        break;
      default:
        break;
    }
  }

  /**
   * In case that "Enter" is clicked in the UI the entered nickname gets checked on validity.
   * 
   * @author mherre
   * @param event the MouseEvent triggering this method
   * @throws IOException If an input or output exception occurred
   */
  @FXML
  private void enter(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    this.isUsernameValid();
  }

  /**
   * Returns a String containing the last created username that has been entered in the nickname
   * textfield.
   * 
   * @author mherre
   * @return returns <code>createdUsername</code>
   */
  public String getCreatedUsername() {
    return createdUsername;
  }

  /**
   * Checks if a nickname only consists of letters, numbers and underscores, also is the maximum
   * amount of chars set to 12. In case the User types in nothing or doesn't fullfill the other
   * criteria, the game will show an error message explaining why the username could not be valid.
   * 
   * In case the nickname fullfills the criteria a new profile gets generated in the database and
   * the user gets shown a confirmation message.
   * 
   * 
   * @author mherre
   * @return
   * @throws IOException
   */
  private boolean isUsernameValid() throws IOException {

    String regex = "[a-zA-Z0-9_]{1,12}";
    String message;
    PopUpMessage pum;

    if (this.nickname.getText().matches(regex)) {

      if (!UseDatabase.playerExists(this.nickname.getText())) {
        this.createdUsername = nickname.getText();
        Data.setCurrentUser(this.createdUsername);
        FillDatabase.createPlayer(this.createdUsername, null);

        message = "Congratulations! Your account has been created";
        pum = new PopUpMessage(message, PopUpMessageType.NOTIFICATION);
        pum.show();
        App.setRoot("MainMenu");

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

  /**
   * Sets the predescessor scene of AccountCreationController.
   * 
   * @param predecessorPara
   */
  public static void setPredecessor(String predecessorPara) {
    predecessor = predecessorPara;

  }
}
