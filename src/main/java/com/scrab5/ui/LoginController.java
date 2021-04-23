package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * The LoginController class controls the components of the "Login" scene
 * 
 * @author mherre
 *
 */

public class LoginController extends Controller implements Initializable {

  @FXML
  private ImageView quitButton;
  @FXML
  private ImageView createButton;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub

  }



  /**
   * Is called when "Create" is clicked. Sets the next scene to "AccountCreation"
   * 
   * @author mherre
   * @param event
   * @throws IOException
   */
  @FXML
  private void create(MouseEvent event) throws IOException {
    App.setRoot("AccountCreation");

  }

}
