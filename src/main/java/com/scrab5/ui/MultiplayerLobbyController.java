package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * The MultiplayerLobbyController class is supposed to control the of the MultiplayerLobby screen
 * 
 * @author mherre
 *
 */
public class MultiplayerLobbyController extends Controller implements Initializable {

  @FXML
  private Label player1, ready1, playerNameStats1;

  private boolean isReady1 = false;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.player1.setText(Data.getCurrentUser());
    this.ready1.setText("Not Ready");
  }

  /**
   * This method is called when the "Back"-button is clicked. It sets the scene to
   * "MultiplayerOverview"
   * 
   * @author mherre @author nitterhe
   * @param event
   * @throws IOException
   */
  @FXML
  private void back(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    Data.getPlayerServer().shutDownServer();
    App.setRoot("MultiplayerOverview");
  }

  @FXML
  private void ready(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    if (!isReady1) {
      this.ready1.setText("Ready");
      this.isReady1 = true;

    } else {
      this.ready1.setText("Not Ready");
      this.isReady1 = false;
      Data.getPlayerServer().startGame();
    }

  }

}
