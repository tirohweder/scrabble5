package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class MultiplayerOverviewController extends Controller implements Initializable {

  @FXML
  private Label userPlaying;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub

  }

  /**
   * @author marku
   * @param event
   * @throws IOException
   * 
   *         Event method that is called when the "Back"-button is clicked. Scene gets changed to
   *         the predecessor "Main Menu" scene
   */
  @FXML
  private void back(MouseEvent event) throws IOException {
    App.setRoot("AccountCreation"); // Placeholder
  }


}
