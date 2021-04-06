package com.scrab5.ui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class MultiplayerOverviewController extends Controller {

  @FXML
  private Label userPlaying;

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
