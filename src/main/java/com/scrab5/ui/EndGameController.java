package com.scrab5.ui;

import com.scrab5.core.player.Player;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class EndGameController extends InGameController implements Initializable {

  @FXML
  private ImageView wonScreen;
  @FXML
  private ImageView lostScreen;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    initPlayers();

    initRack();

    // initEndGame();
  }

  private void initEndGame() {
    ArrayList<Player> players = Data.getGameSession().getListOfPlayers();
    Player owner;
    for (Player p : players) {
      if (p.getName().equals(Data.getCurrentUser())) {
        owner = p;
      }
    }

  }

  @FXML
  private void mainMenuClicked(MouseEvent event) throws IOException {
    App.setRoot("MainMenu");
  }

  @FXML
  private void playAgainClicked(MouseEvent event) throws IOException {

    App.setRoot("SingleplayerLobby");
  }
}


