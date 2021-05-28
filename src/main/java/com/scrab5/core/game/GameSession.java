package com.scrab5.core.game;

import com.scrab5.core.player.AiPlayer;
import com.scrab5.core.player.Player;
import com.scrab5.core.player.PlayerProfile;
import com.scrab5.ui.Controller;
import com.scrab5.ui.Data;
import com.scrab5.ui.PopUpMessage;
import com.scrab5.ui.PopUpMessageType;
import com.scrab5.util.database.Database;
import com.scrab5.util.database.FillDatabase;
import com.scrab5.util.database.PlayerProfileDatabase;
import com.scrab5.util.database.UseDatabase;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
  private BagOfTiles bag = new BagOfTiles();
  private ArrayList<Player> listOfPlayers;
  private int skippedTurn = 0;
  private int roundNumber = 0;
  private boolean canEnd = false;
  private Player currentPlayer;
  private boolean shouldEnd = false;
  private boolean running = true;
  private boolean online;

  /**
   * Intitializes the Gamsession, sets currentplayer, calls to create the correct bag, and fills the
   * rack of each player.
   *
   * @author trohwede
   * @param listOfPlayers list of players in the correct order
   * @param letters how often each letter is there
   * @param points how many * points each letter gives
   * @param isOnline is the game multiplayer or singleplayer
   */
  public GameSession(ArrayList<Player> listOfPlayers, ArrayList<Integer> letters,
      ArrayList<Integer> points, boolean isOnline) throws SQLException {
    this.listOfPlayers = listOfPlayers;
    currentPlayer = listOfPlayers.get(0);

    this.online = isOnline;
    if (this.online) {
      Data.getHostedServer().startGame();
    }
    initializeBag(letters, points);
    for (Player player : listOfPlayers) {
      // we fill rack of every player even ai, this acts like it blocks the stuff
      player.getRack().fill(bag);
    }
    gameBoard = new GameBoard();
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
   * Setter for the bag.
   *
   * @author trohwede
   * @param bag sets the bag for the game.
   */
  public void setBag(BagOfTiles bag) {
    this.bag = bag;
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

  public void setListOfPlayers(ArrayList<Player> listOfPlayers) {
    this.listOfPlayers = listOfPlayers;
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

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  /**
   * Setter if we can end.
   *
   * @author trohwede
   * @return if you can end the game
   */
  public boolean isCanEnd() {
    return canEnd;
  }

  /**
   * Setter for setting the possibility to end the game.
   *
   * @author trohwede
   * @param canEnd sets if its possible to end the game
   */
  public void setCanEnd(boolean canEnd) {
    this.canEnd = canEnd;
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

  public void setCurrentPlayer(Player currentPlayer) {
    this.currentPlayer = currentPlayer;
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
   * Setter if the game is online or offline.
   *
   * @author trohwede
   * @param online is game multiplayer(online) or not
   */
  public void setOnline(boolean online) {
    this.online = online;
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
   * Reads the distribution of tiles from the database and creates the bag accordingly.
   *
   * @author trohwede
   * @throws SQLException if cant connect to the database
   */
  public void initializeBag() throws SQLException {

    System.out.println("Initialized Bag");

    FillDatabase.fillLetters();
    Database.disconnect();
    Database.reconnect();
    ResultSet rs = UseDatabase.viewLetters();
    while (rs.next()) {
      this.bag.add(new Tile(rs.getString("Letter"), rs.getInt("Points")));
      // System.out.println(rs.getString("Letter") + " : " + rs.getInt("Points"));
    }

    System.out.println("Finished Initialized Bag");
    rs.close();
    Database.disconnect();
  }

  /**
   * When the gameSession is created, fills the bagOfTiles with the tiles set in the lobby.
   *
   * @author trohwede
   * @param lettersOccurrence how often each letter is there
   * @param points how many points each letter gives
   */
  public void initializeBag(ArrayList<Integer> lettersOccurrence, ArrayList<Integer> points) {

    // TODO joker richtig bennen
    String[] buchstaben = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
        "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "*"};

    for (int i = 0; i < lettersOccurrence.size(); i++) {
      for (int j = 0; j < lettersOccurrence.get(i); j++) {

        this.bag.add(new Tile(buchstaben[i], points.get(i)));
      }
    }

    Database.disconnect();
  }

  /**
   * When turn is finished correctly, rack need to be refilled, round number increased,
   * currentplayer set correctly.
   *
   * @author trohwede
   */
  public void finishTurn() throws IOException {
    for (int i = 0; i < Data.getGameSession().getListOfPlayers().size(); i++) {
      if (Data.getGameSession().getListOfPlayers().get(i) instanceof AiPlayer) {
        Data.getGameSession().getListOfPlayers().get(i).getRack()
            .fill(Data.getGameSession().getBag());
      }
    }

    currentPlayer.getRack().fill(bag);

    roundNumber++;
    currentPlayer = listOfPlayers.get(roundNumber % listOfPlayers.size());
    // System.out.println("Current Player= " + currentPlayer.getName());
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

  // TODO
  public void checkEndScreen() {}

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
      if (!(player instanceof AiPlayer)) {
        PlayerProfile temp = player.getPlayerProfile();
        temp.addPoints(player.getName(), player.getPoints());
        temp.addWords(player.getCorrectWords());

        temp.addGames(1);

        if (temp.getLaidWords() != 0) {
          temp.adjustPointsPerWordRate(temp.getTotalPoints() / temp.getLaidWords());
        }
        if (temp.getLongestWord() > PlayerProfileDatabase.getLongestWord(player.getName())) {
          temp.adjustLongestWord(temp.getLongestWord());
          /* TODO: connect to change the value for the longest word */
        }
        if (player.getPoints() > PlayerProfileDatabase.getPersonalHighscore(player.getName())) {
          temp.adjustPersonalHighscore(player.getPoints());
        }
        if ((player.getName().equals(name)) && (player.getPoints() != 0)
            && (player.getPoints() == mostPoints)) {
          temp.addWins(1);
        }
        if (temp.getTotalPlayedGames() != 0) {
          double newWinRate = (double) temp.getTotalWins() / (double) temp.getTotalPlayedGames();
          temp.adjustWinRate(Math.round(newWinRate * 100.0) / 100.0);
        }
      }
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
      System.out.println("Ai will try to play now: ");
      AiPlayer aiPlayer = (AiPlayer) currentPlayer;
      aiPlayer.aiPlay();
    }
  }

  // TODO
  public boolean giveUp() {
    return false;
  }

  /**
   * Checks if rack and bag are empty, if yes shouldEnd is set to true.
   *
   * @author trohwede
   * @param player that should be checked
   */
  public void checkBagAndRack(Player player) {

    int size = bag.getSize();

    System.out.println("WHAT HAPPEND ? " + size);
    if (size == 0 && !player.getRack().isRackFull()) {
      this.shouldEnd = true;
      System.out.println("Good shit happend");
    }
  }

  /**
   * Calculates if the more than 5 turns have been skipped in a row.
   *
   * @author trohwede
   * @return if its possible to end the game, because of skipped turns
   */
  public boolean calculateEndPossibility() {
    return this.skippedTurn >= 6;
  }

  /**
   * Method that plays a sound file and adjusts the volume to the volume that has been set by the
   * user in the "Settings.fxml" scene.
   *
   * @author mherre
   * @param tob - the variable if Triple.mp3 or Bingo.mp3 shall be played.
   */
  public void playSound(boolean tob) {
    String file = "";
    if (tob) {
      file = "Bingo.mp3";
    } else {
      file = "Triple.mp3";
    }
    Media sound = new Media(
        Controller.class.getResource("/com/scrab5/ui/sound_effects/" + file).toExternalForm());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.setVolume(Data.getSFXVolume());
    mediaPlayer.play();
  }
}
