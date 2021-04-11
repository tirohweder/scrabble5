package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class AccountCreationController extends Controller implements Initializable {

  @FXML
  private TextField nickname;
  private String createdUsername;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // TODO Auto-generated method stub
  }

  /**
   * @author marku
   * 
   *         Event method that is called when the "Back"-button is clicked. Scene gets changed to
   *         the predecessor "Login" scene
   */
  @FXML
  private void back(MouseEvent event) throws IOException {
    App.setRoot("Login");
  }

  /**
   * @author marku
   * @param event
   * @throws IOException
   * 
   *         When "Enter" is pressed on the keyboard the nickname gets checked on validity (e.g.
   *         maximum amount of chars, invalid chars,...). Sets the currentUser in Data.
   * 
   *         TODO: Pop-Up why username is invalid
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
   * @author marku
   * @param event
   * @throws IOException
   * 
   *         When "Enter" is clicked in the UI the nickname gets checked on validity (e.g. maximum
   *         amount of chars, invalid chars,...). Sets the currentUser in Data.
   * 
   *         TODO: Pop-Up why username is invalid
   */
  @FXML
  private void enter(MouseEvent event) throws IOException {
    this.isUsernameValid();
  }

  /**
   * @author marku
   * @return
   * 
   *         Returns the generated username.
   */
  public String getCreatedUsername() {
    return createdUsername;
  }

  public boolean isUsernameValid() throws IOException {

    String regex = "[a-zA-Z0-9_]{1,12}";

    if (this.nickname.getText().matches(regex)) {

      this.createdUsername = nickname.getText();
      Data.setCurrentUser(this.createdUsername); // DUMMY
                                                 // Pop Up dass Erstellung erfolgreich war
      App.setRoot("MainMenu");

    } else {
      Alert at = new Alert(AlertType.ERROR);
      at.show();
      System.out.println("ERROR");// Placeholder
    }

    return false;
  }

}
