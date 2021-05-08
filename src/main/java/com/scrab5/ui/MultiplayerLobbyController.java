package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.scrab5.util.database.PlayerProfileDatabase;
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
  private Label player1, ready1, playerNameStats1, played1, won1, score1;

  private boolean isReady1 = false;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String name = Data.getCurrentUser();
    this.player1.setText(name);
    this.playerNameStats1.setText(name);
    this.played1.setText(PlayerProfileDatabase.getTotalPlayedGames(name) + "");
    this.won1.setText(PlayerProfileDatabase.getTotalWins(name) + "");
    this.score1.setText(PlayerProfileDatabase.getTotalPoints(name) + "");
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
    Data.getPlayerClient().disconnectFromServer();
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
