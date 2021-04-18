package com.scrab5.core.game;

import com.scrab5.core.player.Player;
import com.scrab5.util.database.FillDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameSession {


  /**
   * @return
   */
  public static GameBoard getGameBoard() {
    return gameBoard;
  }

  /**
   * @param gameBoard
   * @author trohwede
   */
  public static void setGameBoard(GameBoard gameBoard) {
    GameSession.gameBoard = gameBoard;
  }

  /**
   * @return
   * @author trohwede
   */
  public static BagOfTiles getBag() {
    return bag;
  }

  /**
   * @param bag
   * @author trohwede
   */
  public static void setBag(BagOfTiles bag) {
    GameSession.bag = bag;
  }

  /**
   * @return
   * @author trohwede
   */
  public static Player[] getList_of_players() {
    return list_of_players;
  }

  /**
   * @param list_of_players
   * @author trohwede
   */
  public static void setList_of_players(Player[] list_of_players) {
    GameSession.list_of_players = list_of_players;
  }

  /**
   * @return
   * @author trohwede
   */
  public static int getSkipped_turn() {
    return skipped_turn;
  }

  /**
   * @param skipped_turn
   * @author trohwede
   */
  public static void setSkipped_turn(int skipped_turn) {
    GameSession.skipped_turn = skipped_turn;
  }

  /**
   * @return
   * @author trohwede
   */
  public static int getRound_number() {
    return round_number;
  }

  /**
   * @param round_number
   * @author trohwede
   */
  public static void setRound_number(int round_number) {
    GameSession.round_number = round_number;
  }

  /**
   * @return
   * @author trohwede
   */
  public static boolean isCan_end() {
    return can_end;
  }

  /**
   * @param can_end
   * @author trohwede
   */
  public static void setCan_end(boolean can_end) {
    GameSession.can_end = can_end;
  }

  /**
   * @return
   * @author trohwede
   */
  public static Player getCurrent_Player() {
    return current_Player;
  }

  /**
   * @param current_Player
   * @author trohwede
   */
  public static void setCurrent_Player(Player current_Player) {
    GameSession.current_Player = current_Player;
  }


  private static GameBoard gameBoard = new GameBoard();
  private static BagOfTiles bag = new BagOfTiles();
  private static Player[] list_of_players;
  private static int skipped_turn = 0;
  private static int round_number = 0;
  private static boolean can_end = false;
  private static Player current_Player;

  // initialize bag fills the bag with the selected tiles

  /**
   * Starts GameSession with up to 4 Players.
   *
   * @param gamePlayer
   * @throws SQLException
   * @author trohwede
   */
  public GameSession(Player[] gamePlayer) throws SQLException {
    list_of_players = new Player[gamePlayer.length];
    current_Player = list_of_players[0];

    initializeBag();

    for (int i = 0; i < gamePlayer.length; i++) {
      list_of_players[i].getRack().fill(bag);
    }

  }


  /**
   * @author trohwede
   */
  public void initializeBag() throws SQLException {
    ResultSet rs = FillDatabase.viewLetters();
    while (rs.next()) {
      this.bag.add(new Tile(rs.getString("Letter"), rs.getInt("Points")));
    }
  }

  /**
   * @param bag
   * @param p
   * @param gameBoard
   * @author trohwede
   */
  public void turn(BagOfTiles bag, Player p, GameBoard gameBoard) {

  }

  /**
   * @author trohwede
   */
  public void endGame() {
  }
}
