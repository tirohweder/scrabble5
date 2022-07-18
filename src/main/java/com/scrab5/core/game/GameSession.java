package com.scrab5.core.game;

import com.scrab5.core.player.AiPlayer;
import com.scrab5.core.player.Player;
import com.scrab5.core.player.PlayerProfile;
import com.scrab5.ui.Controller;
import com.scrab5.ui.Data;
import com.scrab5.ui.PopUpMessage;
import com.scrab5.ui.PopUpMessageType;
import com.scrab5.util.database.Database;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * GameSession host everything needed to play the game.
 *
 * @author trohwede
 */
public class GameSession implements Serializable {

  private static final long serialVersionUID = 1L;
  private final GameBoard gameBoard;
  private final ArrayList<Player> listOfPlayers;
  private BagOfTiles bag = new BagOfTiles();
  private int skippedTurn = 0;
  private int roundNumber = 0;
  private Player currentPlayer;
  private boolean shouldEnd = false;
  private boolean running = true;
  private boolean online;
  private int lastWordsSize = 0;

  /**
   * Initializes the gameSession, sets currentPlayer, calls to create the correct bag, and fills the
   * rack of each player.
   *
   * @author trohwede
   * @param listOfPlayers list of players in the correct order
   * @param letters how often each letter is there
   * @param points how many * points each letter gives
   * @param isOnline is the game multiplayer or SinglePlayer.
   */
  public GameSession(
      ArrayList<Player> listOfPlayers,
      ArrayList<Integer> letters,
      ArrayList<Integer> points,
      boolean isOnline) {
    this.listOfPlayers = listOfPlayers;
    currentPlayer = listOfPlayers.get(0);

    this.online = isOnline;
    if (this.online) {
      Data.getHostedServer().startGame();
    }
    initializeBag(letters, points);
    for (Player player : listOfPlayers) {
      // we fill rack of every player even ai, this makes it more realistic.
      player.getRack().fill(bag);
    }
    gameBoard = new GameBoard();
  }

  /**
   * Returns how many words were already on the board last turn.
   *
   * @author trohwede
   * @return lastWordSize
   */
  public int getLastWordsSize() {
    return lastWordsSize;
  }

  /**
   * Getter for gameBoard.
   *
   * @author trohweder
   * @return gameBoard
   */
  public GameBoard getGameBoard() {
    return gameBoard;
  }

  /**
   * Getter for the current bag used.
   *
   * @author trohwede
   * @return bag
   */
  public BagOfTiles getBag() {
    return bag;
  }

  /**
   * Getter for the current list of players.
   *
   * @author trohwede
   * @return current list of players
   */
  public ArrayList<Player> getListOfPlayers() {
    return listOfPlayers;
  }

  /**
   * Getter current skipped turns.
   *
   * @author trohwede
   * @return skippedTurns
   */
  public int getSkippedTurn() {
    return skippedTurn;
  }

  /**
   * Setter for skipped turns.
   *
   * @author trohwede
   * @param skippedTurn amount of skipped turns in a game
   */
  public void setSkippedTurn(int skippedTurn) {
    this.skippedTurn = skippedTurn;
  }

  /**
   * Getter for the current round number.
   *
   * @author trohwede
   * @return roundNumber
   */
  public int getRoundNumber() {
    return roundNumber;
  }

  /**
   * Getter for current player.
   *
   * @author trohwede
   * @return the current player
   */
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Returns if the game is online or offline.
   *
   * @author trohwede
   * @return if the game is online
   */
  public boolean isOnline() {
    return online;
  }

  /**
   * Returns if the game is running or not.
   *
   * @author trohwede
   * @return if the game is running
   */
  public boolean isRunning() {
    return running;
  }

  /**
   * Set if the game is running or not.
   *
   * @author trohwede
   * @param running is the game live
   */
  public void setRunning(boolean running) {
    this.running = running;
  }

  // initialize bag fills the bag with the selected tiles

  /**
   * Returns boolean if the game should end.
   *
   * @author trohwede
   * @return if the game shouldEnd
   */
  public boolean isShouldEnd() {
    return shouldEnd;
  }

  /**
   * Setter for shouldEnd.
   *
   * @author trohwede
   * @param shouldEnd if the game should end when checked upon
   */
  public void setShouldEnd(boolean shouldEnd) {
    this.shouldEnd = shouldEnd;
  }

  /**
   * When the gameSession is created, fills the bagOfTiles with the tiles set in the lobby.
   *
   * @author trohwede
   * @param lettersOccurrence how often each letter is there
   * @param points how many points each letter gives
   */
  public void initializeBag(ArrayList<Integer> lettersOccurrence, ArrayList<Integer> points) {
    String[] letters = {
      "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
      "T", "U", "V", "W", "X", "Y", "Z", "*"
    };

    for (int i = 0; i < lettersOccurrence.size(); i++) {
      for (int j = 0; j < lettersOccurrence.get(i); j++) {
        this.bag.add(new Tile(letters[i], points.get(i)));
      }
    }
    Database.disconnect();
  }

  /**
   * When turn is finished correctly, rack need to be refilled, round number increased,
   * currentPlayer set correctly.
   *
   * @author trohwede
   */
  public void finishTurn() throws IOException {
    lastWordsSize = gameBoard.getWords().size();
    for (int i = 0; i < Data.getGameSession().getListOfPlayers().size(); i++) {
      if (Data.getGameSession().getListOfPlayers().get(i) instanceof AiPlayer) {

        Data.getGameSession()
            .getListOfPlayers()
            .get(i)
            .getRack()
            .fill(Data.getGameSession().getBag());
        System.out.println(
            "Player: "
                + Data.getGameSession().getListOfPlayers().get(i).getName()
                + " Size: "
                + Data.getGameSession().getListOfPlayers().get(i).getRack().getRackSize());
        if (Data.getGameSession().getListOfPlayers().get(i).getRack().getRackSize() == 0) {
          this.shouldEnd = true;
        }
      }
    }
    if (!shouldEnd) {
      currentPlayer.getRack().fill(bag);
      roundNumber++;
      currentPlayer = listOfPlayers.get(roundNumber % listOfPlayers.size());
      gameBoard.finishTurn();
      if (currentPlayer instanceof AiPlayer) {
        PopUpMessage pum = new PopUpMessage("AI will Play now.", PopUpMessageType.NOTIFICATION);
        pum.show();
        AiPlayer aiPlayer = (AiPlayer) currentPlayer;
        aiPlayer.aiPlay();
      }

      if (online) {
        Data.getPlayerClient().makeTurn();
      }
    }
  }

  /**
   * This method is called when the "Exit"- or "Give Up"-button in the UI is clicked. It first
   * determines the winner of the game. In the second step all statistics which can be found in
   * "Profile.fxml" are getting updated.
   *
   * @author mherre
   */
  public void endGame() {

    int mostPoints = 0;
    String name = "";
    for (int i = 0; i < Data.getGameSession().getListOfPlayers().size(); i++) {
      Player one = Data.getGameSession().getListOfPlayers().get(i);
      if (mostPoints < one.getPoints()) {
        mostPoints = one.getPoints();
        name = one.getName();
      }
    }

    for (Player player : Data.getGameSession().getListOfPlayers()) {
      if (!(player instanceof AiPlayer) && (Data.getCurrentUser().equals(player.getName()))) {
        PlayerProfile tmp = player.getPlayerProfile();
        tmp.addPoints(player.getName(), player.getPoints());
        tmp.addWords(player.getCorrectWords());

        tmp.addGames(1);

        if (tmp.getLaidWords() != 0) {
          tmp.adjustPointsPerWordRate(tmp.getTotalPoints() / tmp.getLaidWords());
        }
        if (player.getPoints() > tmp.getPersonalHighscore()) {
          tmp.adjustPersonalHighscore(player.getPoints());
        }
        if ((player.getName().equals(name))
            && (player.getPoints() != 0)
            && (player.getPoints() == mostPoints)) {
          tmp.addWins(1);
        }
        if (tmp.getTotalPlayedGames() != 0) {
          double newWinRate = (double) tmp.getTotalWins() / (double) tmp.getTotalPlayedGames();
          tmp.adjustWinRate(Math.round(newWinRate * 100.0) / 100.0);
        }
      }
    }
    if (online) {
      Data.getPlayerClient().makeTurn();
    }
    this.running = false;
  }

  /**
   * Checks if its the Ai Turn and if the first tile has to be played yet. If yes makes it play.
   *
   * @author trohwede
   */
  public void isAiFirstTurn() throws IOException {
    if (currentPlayer instanceof AiPlayer && this.gameBoard.isFirstTile()) {
      AiPlayer aiPlayer = (AiPlayer) currentPlayer;
      aiPlayer.aiPlay();
    }
  }

  /**
   * Checks if rack and bag are empty, if yes shouldEnd is set to true.
   *
   * @author trohwede
   * @param player that should be checked
   */
  public void checkBagAndRack(Player player) {
    int size = bag.getSize();
    if (size == 0 && !player.getRack().isRackFull()) {
      this.shouldEnd = true;
    }
  }

  /**
   * Method that plays a sound file and adjusts the volume to the volume that has been set by the
   * user in the "Settings.fxml" scene.
   *
   * @author mherre
   * @param tob - the variable if Triple.mp3 or Bingo.mp3 shall be played.
   */
  public void playSound(boolean tob) {
    String file;
    if (tob) {
      file = "Bingo.mp3";
    } else {
      file = "Triple.mp3";
    }
    Media sound =
        new Media(
            Objects.requireNonNull(
                    Controller.class.getResource("/com/scrab5/ui/sound_effects/" + file))
                .toExternalForm());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.setVolume(Data.getSFXVolume());
    mediaPlayer.play();
  }
}
