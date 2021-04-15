package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.scrab5.util.database.FillDatabase;
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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // TODO Auto-generated method stub
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
    App.setRoot("Login");
  }

  /**
   * In case that "Enter" is pressed on the keyboard the entered nickname gets checked on validity
   * 
   * @author mherre
   * @param event
   * @throws IOException
   *
   */
  @FXML
  private void enterPressed(KeyEvent event) throws IOException {

    switch (event.getCode()) {
      case ENTER:
        this.isUsernameValid();
      default:
        break;
    }
  }

  /**
   * In case that "Enter" is clicked in the UI the entered nickname gets checked on validity
   * 
   * @author mherre
   * @param event
   * @throws IOException
   * 
   */
  @FXML
  private void enter(MouseEvent event) throws IOException {
    if (this.isUsernameValid()) {
      FillDatabase.createPlayer(this.createdUsername, null);

    }
  }

  /**
   * Returns the generated username.
   * 
   * @author mherre
   * @return
   * 
   */
  public String getCreatedUsername() {
    return createdUsername;
  }

  /**
   * 
   * @author mherre
   * @return
   * @throws IOException
   */
  public boolean isUsernameValid() throws IOException {

    String regex = "[a-zA-Z0-9_]{1,12}";
    String message;
    PopUpMessage pum;

    if (this.nickname.getText().matches(regex)) {

      this.createdUsername = nickname.getText();

      Data.setCurrentUser(this.createdUsername); // DUMMY

      message = "Congratulations! Your account has been created";
      pum = new PopUpMessage(message, PopUpMessageType.NOTIFICATION);
      pum.show();
      App.setRoot("MainMenu");

    } else {

      message = "Please make sure your nickname consists only of letters, numbers and underscores";
      pum = new PopUpMessage(message, PopUpMessageType.ERROR);
      pum.show();

    }

    return false;
  }

}
