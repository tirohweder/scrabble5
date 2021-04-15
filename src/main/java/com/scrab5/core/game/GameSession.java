package com.scrab5.core.game;

import com.scrab5.core.player.Player;

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

  public static Rack[] getList_of_racks() {
    return list_of_racks;
  }

  public static void setList_of_racks(Rack[] list_of_racks) {
    GameSession.list_of_racks = list_of_racks;
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

  public static Rack getCurrent_Rack() {
    return current_Rack;
  }

  public static void setCurrent_Rack(Rack current_Rack) {
    GameSession.current_Rack = current_Rack;
  }

  private static GameBoard gameBoard = new GameBoard();
  private static BagOfTiles bag = new BagOfTiles();
  private static Rack[] list_of_racks;
  private static Player[] list_of_players;
  private static int skipped_turn = 0;
  private static int round_number = 0;
  private static boolean can_end = false;
  private static Player current_Player;
  private static Rack current_Rack;

  // initialize bag fills the bag with the selected tiles

  public GameSession(int number_of_players) {
    list_of_players = new Player[number_of_players];
  }

  public void setPlayers(Player p1) {}

  public void initializeBag() {
    int length_of_Tiles_list = 10; // TO-DO get how many tiles need to be added

    for (int i = 0; i < length_of_Tiles_list; i++) {
      String letter = "T"; // TO-DO get letter of tile next in line
      int value = 5; // TO-DO get value of tile next in line
      Tile newTile = new Tile(letter, value);

      bag.add(newTile);
    }
  }

  public void turn(BagOfTiles bag, Player p, GameBoard gameBoard) {}

  public void endGame() {}
}
