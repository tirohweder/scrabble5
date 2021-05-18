package com.scrab5.core.game;

import com.scrab5.core.player.Player;
import com.scrab5.ui.Data;
import com.scrab5.util.database.Database;
import com.scrab5.util.database.FillDatabase;
import com.scrab5.util.database.UseDatabase;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GameSession implements Serializable {

  private static final long serialVersionUID = 1L;

  public GameBoard getGameBoard() {
    return gameBoard;
  }

  public void setGameBoard(GameBoard gameBoard) {
    this.gameBoard = gameBoard;
  }


  public BagOfTiles getBag() {
    return bag;
  }

  public void setBag(BagOfTiles bag) {
    this.bag = bag;
  }

  public ArrayList<Player> getListOfPlayers() {
    return listOfPlayers;
  }

  public void setListOfPlayers(ArrayList<Player> listOfPlayers) {
    this.listOfPlayers = listOfPlayers;
  }

  public int getSkippedTurn() {
    return skippedTurn;
  }

  public void setSkippedTurn(int skippedTurn) {
    this.skippedTurn = skippedTurn;
  }

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public boolean isCanEnd() {
    return canEnd;
  }

  public void setCanEnd(boolean canEnd) {
    this.canEnd = canEnd;
  }

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  public void setCurrentPlayer(Player currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  public boolean isOnline() {
    return online;
  }

  public void setOnline(boolean online) {
    this.online = online;
  }

  public boolean isRunning() {
    return running;
  }

  public void setRunning(boolean running) {
    running = running;
  }

  public boolean isShouldEnd() {
    return shouldEnd;
  }

  public void setShouldEnd(boolean shouldEnd) {
    this.shouldEnd = shouldEnd;
  }

  private GameBoard gameBoard = new GameBoard();
  private BagOfTiles bag = new BagOfTiles();
  private ArrayList<Player> listOfPlayers;
  private int skippedTurn = 0;
  private int roundNumber = 0;
  private boolean canEnd = false;
  private Player currentPlayer;
  private String currentDic;
  private boolean shouldEnd = false;
  private boolean running = true;


  private boolean online;

  // initialize bag fills the bag with the selected tiles

  /**
   * Starts GameSession with up to 4 Players.
   *
   * @author trohwede
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
    for (Player listOfPlayer : listOfPlayers) {
      listOfPlayer.getRack().fill(bag);
    }
  }

  public GameSession(ArrayList<Player> listOfPlayers, boolean isOnline) throws SQLException {
    this.listOfPlayers = listOfPlayers;
    currentPlayer = listOfPlayers.get(0);
    this.online = isOnline;
    if (this.online) {
      Data.getHostedServer().startGame();
    }
    System.out.println("Created Game Session");

    initializeBag();

    for (Player listOfPlayer : listOfPlayers) {
      listOfPlayer.getRack().fill(bag);
      System.out.println();
      System.out.println("Tile AT 0:" + currentPlayer.getRack().getTileAt(0));
    }
  }


  public void initializeBag() throws SQLException {

    System.out.println("Initialized Bag");

    FillDatabase.fillLetters();
    ResultSet rs = UseDatabase.viewLetters();
    while (rs.next()) {
      this.bag.add(new Tile(rs.getString("Letter"), rs.getInt("Points")));
      System.out.println(rs.getString("Letter") + " : " + rs.getInt("Points"));
    }

    System.out.println("Finished Initialized Bag");
    Database.disconnect();
  }

  public void initializeBag(ArrayList<Integer> letters, ArrayList<Integer> points)
      throws SQLException {
    if (!Data.getHasBeenEdited()) {
      ResultSet rs = UseDatabase.viewLetters();
      while (rs.next()) {
        this.bag.add(new Tile(rs.getString("Letter"), rs.getInt("Points")));
      }
    } else {
      for (int i = 0; i < letters.size(); i++) {

      }
    }
    Database.disconnect();
  }

  public void finishTurn() {
    currentPlayer.getRack().fill(bag);
    roundNumber++;
    currentPlayer = listOfPlayers.get(roundNumber % listOfPlayers.size());
    gameBoard.finishTurn();
    if (online) {
      Data.getPlayerClient().makeTurn();
    }


  }

  public void checkEndScreen() {

  }

  public void endGame() {
    // Data.getHostedServer().endGame(winner);
    // TODO call server method

    this.running = false;
  }

  public boolean giveUp() {
    return false;
  }

  public void checkBagAndRack(Player player) {
    if (bag.getSize() == 0 && !player.getRack().isRackFull()) {
      this.shouldEnd = true;
    }
  }

  public boolean calculateEndPossibility() {
    if (this.skippedTurn >= 6) {
      return true;
    }
    return false;


  }

}
