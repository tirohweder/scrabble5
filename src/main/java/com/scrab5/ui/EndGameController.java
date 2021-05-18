package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import com.scrab5.core.game.GameSession;
import com.scrab5.core.player.Player;
import com.scrab5.util.database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EndGameController extends InGameController implements Initializable {

  @FXML
  private ImageView wonScreen;
  @FXML
  private ImageView lostScreen;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    initPlayers();

    initRack();

    initEndGame();
  }

  private void initEndGame() {
    ArrayList<Player> players = Data.getGameSession().getListOfPlayers();
    Player owner;
    Iterator<Player> it = players.iterator();
    while (it.hasNext()) {
      Player p = it.next();
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


