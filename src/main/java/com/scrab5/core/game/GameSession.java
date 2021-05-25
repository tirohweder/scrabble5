package com.scrab5.core.game;

import com.scrab5.core.player.AiPlayer;
import com.scrab5.core.player.Player;
import com.scrab5.ui.Data;
import com.scrab5.util.database.Database;
import com.scrab5.util.database.FillDatabase;
import com.scrab5.util.database.UseDatabase;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * GameSession host everything needed to play the game.
 *
 * @author trohwede
 */
public class GameSession implements Serializable {

  private static final long serialVersionUID = 1L;
  private final GameBoard gameBoard = new GameBoard();
  private BagOfTiles bag = new BagOfTiles();
  private ArrayList<Player> listOfPlayers;
  private int skippedTurn = 0;
  private int roundNumber = 0;
  private boolean canEnd = false;
  private Player currentPlayer;
  private boolean shouldEnd = false;
  private boolean running = true;
  private boolean online;

  // TODO might delete
  public GameSession(
      ArrayList<Player> listOfPlayers,
      ArrayList<Integer> letters,
      ArrayList<Integer> points,
      boolean isOnline)
      throws SQLException {
    this.listOfPlayers = listOfPlayers;
    currentPlayer = listOfPlayers.get(0);
    this.online = isOnline;
    if (this.online) {
      Data.getHostedServer().startGame();
    }
    initializeBag(letters, points);
    for (Player listOfPlayer : listOfPlayers) {
      listOfPlayer.getRack().fill(bag);
    }
  }

  /**
   * Intitializes the Gamsession, sets currentplayer, calls to create the correct bag, and fills the
   * rack of each player.
   *
   * @param listOfPlayers list of players in the correct order
   * @param isOnline is the game multiplayer or singleplayer
   * @throws SQLException if Database cant connect //TODO is this the right way
   * @author trohwede
   */
  public GameSession(ArrayList<Player> listOfPlayers, boolean isOnline) throws SQLException {
    this.listOfPlayers = listOfPlayers;
    currentPlayer = listOfPlayers.get(0);
    this.online = isOnline;
    if (this.online) {
      Data.getHostedServer().startGame();
    }
    System.out.println("Created Game Session");

    initializeBag();

    for (Player player : listOfPlayers) {
      if (player.isHuman()) {
        player.getRack().fill(bag);
      }
      System.out.println("Im creating new GameSession right now");
      System.out.println("Tile AT 0:" + currentPlayer.getRack().getTileAt(0));
    }
  }

  /**
   * Getter for gameBoard.
   *
   * @return gameBoard
   * @author trohweder
   */
  public GameBoard getGameBoard() {
    return gameBoard;
  }

  /**
   * Getter for the current bag used.
   *
   * @return bag
   * @author trohwede
   */
  public BagOfTiles getBag() {
    return bag;
  }

  /**
   * Setter for the bag.
   *
   * @param bag sets the bag for the game.
   * @author trohwede
   */
  public void setBag(BagOfTiles bag) {
    this.bag = bag;
  }

  /**
   * Getter for the current list of players.
   *
   * @return current list of players
   * @author trohwede
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
   * @return skippedTurns
   * @author trohwede
   */
  public int getSkippedTurn() {
    return skippedTurn;
  }

  /**
   * Setter for skipped turns.
   *
   * @param skippedTurn amount of skipped turns in a game
   * @author trohwede
   */
  public void setSkippedTurn(int skippedTurn) {
    this.skippedTurn = skippedTurn;
  }

  /**
   * Getter for the current round number.
   *
   * @return roundNumber
   * @author trohwede
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
   * @return if you can end the game
   * @author trohwede
   */
  public boolean isCanEnd() {
    return canEnd;
  }

  /**
   * Setter for setting the possibility to end the game.
   *
   * @param canEnd sets if its possible to end the game
   * @author trohwede
   */
  public void setCanEnd(boolean canEnd) {
    this.canEnd = canEnd;
  }

  /**
   * Getter for current player.
   *
   * @return the current player
   * @author trohwede
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
   * @return if the game is online
   * @author trohwede
   */
  public boolean isOnline() {
    return online;
  }

  /**
   * Setter if the game is online or offline.
   *
   * @param online is game multiplayer(online) or not
   * @author trohwede
   */
  public void setOnline(boolean online) {
    this.online = online;
  }

  /**
   * Returns if the game is running or not.
   *
   * @return if the game is running
   * @author trohwede
   */
  public boolean isRunning() {
    return running;
  }

  /**
   * Set if the game is running or not.
   *
   * @param running is the game live
   * @author trohwede
   */
  public void setRunning(boolean running) {
    this.running = running;
  }

  // initialize bag fills the bag with the selected tiles

  /**
   * Returns boolean if the game should end.
   *
   * @return if the game shouldEnd
   * @author trohwede
   */
  public boolean isShouldEnd() {
    return shouldEnd;
  }

  /**
   * Setter for shouldEnd.
   *
   * @param shouldEnd if the game should end when checked upon
   * @author trohwede
   */
  public void setShouldEnd(boolean shouldEnd) {
    this.shouldEnd = shouldEnd;
  }

  /**
   * Reads the distribution of tiles from the database and creates the bag accordingly.
   *
   * @throws SQLException if cant connect to the database
   * @author trohwede
   */
  public void initializeBag() throws SQLException {

    System.out.println("Initialized Bag");

    FillDatabase.fillLetters();
    Database.disconnect();
    Database.reconnect();
    ResultSet rs = UseDatabase.viewLetters();
    while (rs.next()) {
      this.bag.add(new Tile(rs.getString("Letter"), rs.getInt("Points")));
      System.out.println(rs.getString("Letter") + " : " + rs.getInt("Points"));
    }

    System.out.println("Finished Initialized Bag");
    rs.close();
    Database.disconnect();
  }

  // TODO might delte later
  public void initializeBag(ArrayList<Integer> lettersOccurrence, ArrayList<Integer> points)
      throws SQLException {

    // TODO joker richtig bennen
    String[] buchstaben = {
      "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
      "T", "U", "V", "W", "X", "Y", "Z", "*"
    };

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
  public void finishTurn() {
    currentPlayer.getRack().fill(bag);
    roundNumber++;
    currentPlayer = listOfPlayers.get(roundNumber % listOfPlayers.size());
    // System.out.println("Current Player= " + currentPlayer.getName());
    if (currentPlayer instanceof AiPlayer) {
      System.out.println("Ai will try to play now: ");
      AiPlayer aiPlayer = (AiPlayer) currentPlayer;
      aiPlayer.aiPlay();
    }
    gameBoard.finishTurn();
    if (online) {
      Data.getPlayerClient().makeTurn();
    }
  }

  // TODO
  public void checkEndScreen() {}

  /**
   * ZEUG.
   * 
   * @author mherre
   */
  public void endGame()  {
    // Data.getHostedServer().endGame(winner); nur beim host.
    // TODO call server method, endGame()

    for (Player player : Data.getGameSession().getListOfPlayers()) {
      if (player.isHuman()) {
        player.getPlayerProfile().addPoints(player.getPoints());
        System.out.println(player.getPlayerProfile().getName() + "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");

      }

    }

    //

    this.running = false;
  }

  // TODO
  public boolean giveUp() {
    return false;
  }

  /**
   * Checks if rack and bag are empty, if yes shouldEnd is set to true.
   *
   * @param player that should be checked
   * @author trohwede
   */
  public void checkBagAndRack(Player player) {
    if (bag.getSize() == 0 && !player.getRack().isRackFull()) {
      this.shouldEnd = true;
    }
  }

  /**
   * Calculates if the more than 5 turns have been skipped in a row.
   *
   * @return if its possible to end the game, because of skipped turns
   * @author trohwede
   */
  public boolean calculateEndPossibility() {
    return this.skippedTurn >= 6;
  }
}
