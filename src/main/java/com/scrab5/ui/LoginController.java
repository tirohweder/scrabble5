package com.scrab5.ui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * The LoginController class controls the components of the Login.fxml.
 * 
 * @author mherre
 *
 */

public class LoginController extends Controller {

  @FXML
  private ImageView quitButton;
  @FXML
  private ImageView createButton;

  /**
   * Event method that is called when the "Create"-button in the UI is clicked. Sets the next scene
   * to "AccountCreation". Changes the scene to "AccountCreation.fxml".
   * 
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void create(MouseEvent event) throws IOException {
    App.setRoot("AccountCreation");
  }
}
