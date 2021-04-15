package com.scrab5.core.game;

import com.scrab5.core.player.Player;

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

  public Player[] getList_of_players() {
    return list_of_players;
  }

  public void setList_of_players(Player[] list_of_players) {
    this.list_of_players = list_of_players;
  }

  public int getSkipped_turn() {
    return skipped_turn;
  }

  public void setSkipped_turn(int skipped_turn) {
    this.skipped_turn = skipped_turn;
  }

  public int getRound_number() {
    return round_number;
  }

  public void setRound_number(int round_number) {
    this.round_number = round_number;
  }

  public boolean isCan_end() {
    return can_end;
  }

  public void setCan_end(boolean can_end) {
    this.can_end = can_end;
  }

  public Player getCurrent_Player() {
    return current_Player;
  }

  public void setCurrent_Player(Player current_Player) {
    this.current_Player = current_Player;
  }

  private GameBoard gameBoard = new GameBoard();
  private BagOfTiles bag = new BagOfTiles();
  private Player[] list_of_players;
  private int skipped_turn = 0;
  private int round_number = 0;
  private boolean can_end = false;
  private Player current_Player;

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
