package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
   *         TODO: Pop-Up why username is invalid
   */
  @FXML
  private void enterPressed(KeyEvent event) throws IOException {

    switch (event.getCode()) {
      case ENTER:
        if (nickname.getText().length() <= 12) {
          this.createdUsername = nickname.getText();
          App.setRoot("MultiplayerOverview");
        } else {
          // System.out.println("ERROR"); Placeholder
        }
      default:
        break;
    }
  }

  /**
   * @author marku
   * @param event
   * @throws IOException
   */
  @FXML
  private void enter(MouseEvent event) throws IOException {

    if (nickname.getText().length() <= 12) {
      this.createdUsername = nickname.getText();
      App.setRoot("MultiplayerOverview");
    } else {
      // System.out.println("ERROR"); Placeholder
    }
  }

  /**
   * @author marku
   * @return
   * 
   *         Returns the generated username.
   */
  public String getCreatedUsername() {
    return this.createdUsername;
  }

}
