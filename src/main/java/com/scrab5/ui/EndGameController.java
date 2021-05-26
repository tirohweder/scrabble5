package com.scrab5.ui;

import com.scrab5.core.player.Player;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class EndGameController extends InGameController implements Initializable {

  @FXML
  private ImageView wonScreen;
  @FXML
  private ImageView lostScreen;

  @FXML
  private Label firstPlayer;
  @FXML
  private Label secondPlayer;
  @FXML
  private Label thirdPlayer;
  @FXML
  private Label fourthPlayer;
  @FXML
  private Label firstPlayerPoints;
  @FXML
  private Label secondPlayerPoints;
  @FXML
  private Label thirdPlayerPoints;
  @FXML
  private Label fourthPlayerPoints;


  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    try {
      initPlayers();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    initEndGame();
  }

  private void initEndGame() {
    Data.getGameSession().setRunning(false);
    ArrayList<Player> players = Data.getGameSession().getListOfPlayers();
    Player p, swap;
    Iterator<Player> it = players.iterator();
    int counter = 0;

    Player[] order = new Player[players.size()];
    while (it.hasNext()) {
      p = it.next();
      if (counter == 0) {
        order[counter] = p;
      } else if (order[counter - 1].getPoints() > p.getPoints()) {
        swap = order[counter - 1];
        order[counter - 1] = p;
        order[counter] = swap;
      } else {
        order[counter] = p;
      }
      counter++;
    }

    counter--;

    if (order[counter].getName().equalsIgnoreCase(Data.getCurrentUser())) {
      wonScreen.setOpacity(1);
    } else {
      lostScreen.setOpacity(1);
    }


    if (Data.getGameSession().isOnline() && Data.getPlayerClient().getCurrentServer().getHost()
        .equals(Data.getPlayerClient().getUsername())) {
      Data.getHostedServer().endGame(order[counter].getName());
    }

    if (0 < players.size()) {
      firstPlayer.setText("1. " + order[counter].getName());
      firstPlayerPoints.setText(Integer.toString(order[counter].getPoints()));
      firstPlayer.setOpacity(1);
      firstPlayerPoints.setOpacity(1);
      counter--;
    }
    if (1 < players.size()) {
      secondPlayer.setText("2. " + order[counter].getName());
      secondPlayerPoints.setText(Integer.toString(order[counter].getPoints()));
      secondPlayer.setOpacity(1);
      secondPlayerPoints.setOpacity(1);
      counter--;
    }
    if (2 < players.size()) {
      thirdPlayer.setText("3. " + order[counter].getName());
      thirdPlayerPoints.setText(Integer.toString(order[counter].getPoints()));
      thirdPlayer.setOpacity(1);
      thirdPlayerPoints.setOpacity(1);
      counter--;
    }
    if (3 < players.size()) {
      fourthPlayer.setText("4. " + order[counter].getName());
      fourthPlayerPoints.setText(Integer.toString(order[counter].getPoints()));
      fourthPlayer.setOpacity(1);
      fourthPlayerPoints.setOpacity(1);
      counter--;
    }
  }


  @FXML
  private void mainMenuClicked(MouseEvent event) throws IOException {
    Data.getPlayerClient().disconnectFromServer();
    App.setRoot("MainMenu");
  }

  @FXML
  private void playAgainClicked(MouseEvent event) throws IOException {

    App.setRoot("SingleplayerLobby");
  }
}


