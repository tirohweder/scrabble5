package com.scrab5.core.game;

import com.scrab5.core.player.Player;
import java.util.Collections;
import java.util.List;

public class GameSession {
  Bag_of_Tiles bag = new Bag_of_Tiles();
  Player[] list_of_players = new Player[4]; // TO-DO set how many players the game is big

  // initialize bag fills the bag with the selected tiles
  public void initialize_bag() {
    int length_of_Tileslist = 10; // TO-DO get how many tiles need to be added

    for (int i = 0; i < length_of_Tileslist; i++) {
      String letter = "T"; // TO-DO get letter of tile next in line
      int value = 5; // TO-DO get value of tile next in line
      Tile newTile = new Tile(letter, value);

      bag.add(newTile);
    }
    Collections.shuffle((List<Tile>) this.bag);
  }
}
