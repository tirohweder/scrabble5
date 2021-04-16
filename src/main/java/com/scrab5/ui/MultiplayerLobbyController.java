package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

/**
 * The MultiplayerLobbyController class is supposed to control the of the MultiplayerLobby screen
 * 
 * @author mherre
 *
 */
public class MultiplayerLobbyController extends Controller implements Initializable {

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // TODO Auto-generated method stub

  }

  /**
   * This method is called when the "Back"-button is clicked. It sets the scene to
   * "MultiplayerOverview"
   * 
   * @author mherre
   * @param event
   * @throws IOException
   */
  @FXML
  private void back(MouseEvent event) throws IOException {
    App.setRoot("MultiplayerOverview");
  }

}
