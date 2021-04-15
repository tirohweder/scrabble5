package com.scrab5.core.game;

import com.scrab5.util.database.*;
import com.scrab5.core.player.Player;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameSession {


  public static GameBoard getGameBoard() {
    return gameBoard;
  }

  public static void setGameBoard(GameBoard gameBoard) {
    GameSession.gameBoard = gameBoard;
  }

  public static BagOfTiles getBag() {
    return bag;
  }

  public static void setBag(BagOfTiles bag) {
    GameSession.bag = bag;
  }

  public static Player[] getList_of_players() {
    return list_of_players;
  }

  public static void setList_of_players(Player[] list_of_players) {
    GameSession.list_of_players = list_of_players;
  }

  public static int getSkipped_turn() {
    return skipped_turn;
  }

  public static void setSkipped_turn(int skipped_turn) {
    GameSession.skipped_turn = skipped_turn;
  }

  public static int getRound_number() {
    return round_number;
  }

  public static void setRound_number(int round_number) {
    GameSession.round_number = round_number;
  }

  public static boolean isCan_end() {
    return can_end;
  }

  public static void setCan_end(boolean can_end) {
    GameSession.can_end = can_end;
  }

  public static Player getCurrent_Player() {
    return current_Player;
  }

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

  public GameSession(String[] gamePlayer) throws SQLException {
    list_of_players = new Player[gamePlayer.length];
    current_Player= list_of_players[0];

    initializeBag();

    for (int i=0; i< gamePlayer.length; i++) {
        list_of_players[i].getRack().fill(bag);

    }

  }


  /**
   *
   * @author trohwede
   */
  public void initializeBag() throws SQLException {
    ResultSet rs = FillDatabase.viewLetters();
    while (rs.next()) {
      this.bag.add(new Tile(rs.getString("Letter"),rs.getInt("Points")));
    }
  }

  public void turn(BagOfTiles bag, Player p, GameBoard gameBoard) {

  }

  public void endGame() {}
}
