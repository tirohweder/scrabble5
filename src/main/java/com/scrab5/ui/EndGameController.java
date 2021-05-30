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

/**
 * The EndGameController class controls the end screen after finishing the game by clicking "give
 * up", "skip" 6 times of the "Exchange.fxml".
 *
 * @author apilgrim
 */
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
    Data.setEndScreen(true);
    initPlayers();

    initEndGame();

  }

  /**
   * Method called, when the game is finished. Checks who has the most points and displays the
   * correct order of the players. The player with the most points get a "you won" anyone else gets
   * a "you lost".
   *
   * @author apilgrim
   */
  private void initEndGame() {
    Data.getGameSession().setRunning(false);
    ArrayList<Player> players = Data.getGameSession().getListOfPlayers();
    Player p;
    Player swap;
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

    if (0 < players.size()) {
      if (order[counter].getGivenUp()) {
        swap = order[counter];
        order[counter] = order[counter - 1];
        order[counter - 1] = swap;
      }
      firstPlayer.setText("1. " + order[counter].getName());
      firstPlayerPoints.setText(Integer.toString(order[counter].getPoints()));
      firstPlayer.setOpacity(1);
      firstPlayerPoints.setOpacity(1);
      counter--;
    }
    if (1 < players.size()) {
      if (order[counter].getGivenUp()) {
        if (counter > 0) {
          swap = order[counter];
          order[counter] = order[counter - 1];
          order[counter - 1] = swap;
        }
      }
      secondPlayer.setText("2. " + order[counter].getName());
      if (order[counter].getGivenUp()) {
        secondPlayerPoints.setText("has given up");
        thirdPlayerPoints.setText("has given up");
      } else {
        secondPlayerPoints.setText(Integer.toString(order[counter].getPoints()));
      }
      secondPlayer.setOpacity(1);
      secondPlayerPoints.setOpacity(1);
      counter--;
    }
    if (2 < players.size()) {
      if (order[counter].getGivenUp()) {
        if (counter > 0) {
          swap = order[counter];
          order[counter] = order[counter - 1];
          order[counter - 1] = swap;
        }
      }
      thirdPlayer.setText("3. " + order[counter].getName());
      if (order[counter].getGivenUp()) {
        thirdPlayerPoints.setText("has given up");
        thirdPlayerPoints.setText("has given up");
      } else {
        thirdPlayerPoints.setText(Integer.toString(order[counter].getPoints()));
      }
      thirdPlayer.setOpacity(1);
      thirdPlayerPoints.setOpacity(1);
      counter--;
    }
    if (3 < players.size()) {
      fourthPlayer.setText("4. " + order[counter].getName());
      if (order[counter].getGivenUp()) {
        fourthPlayerPoints.setText("has given up");
        order[counter].setGivenUp(false);
      } else {
        fourthPlayerPoints.setText(Integer.toString(order[counter].getPoints()));
      }
      fourthPlayer.setOpacity(1);
      fourthPlayerPoints.setOpacity(1);
    }
    
    if (order[order.length - 1].getName().equalsIgnoreCase(Data.getCurrentUser())) {
      wonScreen.setOpacity(1);
      lostScreen.setOpacity(0);
    } else {
      lostScreen.setOpacity(1);
      wonScreen.setOpacity(0);
    }
    if (Data.getGameSession().isOnline() && Data.getPlayerClient().getCurrentServer().getHost()
        .equals(Data.getPlayerClient().getUsername())) {
      Data.getHostedServer().endGame(order[order.length - 1].getName());
    }
  }

  /**
   * Is called when "main menu" - button is clicked, switches to the main menu. Disconnects the
   * server if the game played, was online.
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "main menu" button is clicked
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void mainMenuClicked(MouseEvent event) throws IOException {
    if (Data.getGameSession().isOnline()) {
      Data.getPlayerClient().disconnectFromServer();
    }
    Data.setEndScreen(false);
    App.setRoot("MainMenu");
  }

  /**
   * Is called when "play again" - button is clicked, switches to the singleplayer lobby. Only
   * clickable if the game was offline in the singleplayer.
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "play again" button is clicked
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void playAgainClicked(MouseEvent event) throws IOException {
    if (!Data.getGameSession().isOnline()) {
      App.setRoot("SingleplayerLobby");
      Data.setEndScreen(false);
    }
  }
}
