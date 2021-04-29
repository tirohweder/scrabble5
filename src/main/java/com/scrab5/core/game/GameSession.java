package com.scrab5.core.game;

import com.scrab5.core.player.Player;
import com.scrab5.util.database.UseDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class GameSession {


  public static GameBoard getGameBoard() {
    return gameBoard;
  }

  public static void setGameBoard(GameBoard gameBoard) {
    GameSession.gameBoard = gameBoard;
  }

  private static GameBoard gameBoard = new GameBoard();
  private static BagOfTiles bag = new BagOfTiles();
  private static ArrayList<Player> listOfPlayers = new ArrayList<>();
  private static int skippedTurn = 0;
  private static int roundNumber = 0;
  private static boolean canEnd = false;
  private static Player currentPlayer;

  // initialize bag fills the bag with the selected tiles

  /**
   * Starts GameSession with up to 4 Players.
   *
   * @throws SQLException
   * @author trohwede
   */
  public GameSession(ArrayList<Player> listOfPlayers) throws SQLException {
    this.listOfPlayers = listOfPlayers;
    currentPlayer = listOfPlayers.get(0);

    initializeBag();
    Iterator<Player> iter = listOfPlayers.iterator();
    while (iter.hasNext()) {
      iter.next().getRack().fill(bag);
    }


  }

  public void initializeBag() throws SQLException {
    ResultSet rs = UseDatabase.viewLetters();
    while (rs.next()) {
      this.bag.add(new Tile(rs.getString("Letter"), rs.getInt("Points")));
    }
  }


  public boolean play(Tile t, int rackNumber, int row, int column) {
    if (gameBoard.checkWordsLegit()) {
      int points = gameBoard.countScore();
      roundNumber++;
      setCurrentPlayer();
      gameBoard.finishTurn();


    }
    return true;
  }

  public void setCurrentPlayer() {
    currentPlayer = listOfPlayers.get(roundNumber % listOfPlayers.size());
  }


  public void endGame() {
  }
}
