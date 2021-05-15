package com.scrab5.core.game;

import com.scrab5.core.player.Player;
import com.scrab5.ui.Data;
import com.scrab5.util.database.FillDatabase;
import com.scrab5.util.database.UseDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class GameSession {


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

  private GameBoard gameBoard = new GameBoard();
  private BagOfTiles bag = new BagOfTiles();
  private ArrayList<Player> listOfPlayers = new ArrayList<>();
  private int skippedTurn = 0;
  private int roundNumber = 0;
  private boolean canEnd = false;
  private Player currentPlayer;
  private String currentDic;

  // initialize bag fills the bag with the selected tiles

  /**
   * Starts GameSession with up to 4 Players.
   *
   * @throws SQLException
   * @author trohwede
   */
  public GameSession(ArrayList<Player> listOfPlayers, ArrayList<Integer> letters,
      ArrayList<Integer> points) throws SQLException {
    this.listOfPlayers = listOfPlayers;
    currentPlayer = listOfPlayers.get(0);

    initializeBag(letters, points);
    Iterator<Player> iter = listOfPlayers.iterator();
    while (iter.hasNext()) {
      iter.next().getRack().fill(bag);
    }
  }

  public GameSession(ArrayList<Player> listOfPlayers) throws SQLException {
    this.listOfPlayers = listOfPlayers;
    currentPlayer = listOfPlayers.get(0);

    System.out.println("Created Game Session");

    initializeBag();

    for (int i = 0; i < listOfPlayers.size(); i++) {
      listOfPlayers.get(i).getRack().fill(bag);
      System.out.println();
      System.out.println("Tile AT 0:" + currentPlayer.getRack().getTileAt(0));
    }
  }


  public void createGameSession(ArrayList<Player> listOfPlayers, ArrayList<Integer> letters,
      ArrayList<Integer> points) throws SQLException {
    this.listOfPlayers = listOfPlayers;
    currentPlayer = listOfPlayers.get(0);

    initializeBag(letters, points);
    Iterator<Player> iter = listOfPlayers.iterator();
    while (iter.hasNext()) {
      iter.next().getRack().fill(bag);
    }
    System.out.println(bag.getSize());
  }

  public void initializeBag()
      throws SQLException {

    System.out.println("Initialized Bag");

    FillDatabase.fillLetters();

    ResultSet rs = UseDatabase.viewLetters();
    while (rs.next()) {
      this.bag.add(new Tile(rs.getString("Letter"), rs.getInt("Points")));
      System.out.println(rs.getString("Lettter") + " : " + rs.getInt("Points"));
    }

    System.out.println("Finished Initialized Bag");
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
  }

  public void finishTurn() {
    currentPlayer.getRack().fill(bag);
    currentPlayer = listOfPlayers.get(roundNumber % listOfPlayers.size());
  }

  public void endGame() {
  }

  public boolean giveUp() {
    return false;
  }
}
