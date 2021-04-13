package com.scrab5.core.game;

import com.scrab5.core.player.Player;

public class GameSession {
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
