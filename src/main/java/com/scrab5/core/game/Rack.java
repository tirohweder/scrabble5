package com.scrab5.core.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Rack represents the rack in the game. All functions that change the rack are stored here.
 *
 * @author trohwede
 */
public class Rack implements Serializable {

  private static final long serialVersionUID = 1L;

  private final Tile[] rack = new Tile[7];

  /**
   * For every empty place in the rack, fill will pick a tile from the bag and place it in the rack.
   *
   * @author trohwede
   * @param bag bag of which to fill the rack with
   */
  public void fill(BagOfTiles bag) {
    for (int i = 0; i < 7; i++) {
      if (this.rack[i] == null && bag.getSize() >= 1) {
        rack[i] = bag.pick();
        rack[i].setRackPlace(i);
      }
    }
  }

  /**
   * Returns if the rack is full.
   *
   * @author trohwede
   * @return if the Rack is full
   */
  public boolean isRackFull() {
    for (int i = 0; i < 7; i++) {
      if (this.rack[i] == null) {
        return false;
      }
    }
    return true;
  }

  /**
   * Removes a tile from the rack at the given position.
   *
   * @author trohwede
   * @param pos position of the rack you want to remove the tile from
   */
  public void removeTileFromRack(int pos) {
    if (this.rack[pos] != null) {
      this.rack[pos] = null;
    }
  }

  /**
   * Returns the rack size.
   *
   * @author trohwede
   * @return returns rack size.
   */
  public int getRackSize() {
    int size = 0;
    for (int i = 0; i < 7; i++) {
      if (this.rack[i] != null) {
        size++;
      }
    }
    return size;
  }

  /**
   * Clears the complete Rack.
   *
   * @author trohwede
   */
  public void clearRack() {
    Arrays.fill(rack, null);
  }

  /**
   * Method to shuffle the order of the remaining (not placed) tiles on the rack while playing.
   *
   * @author apilgrim
   * @param order - ArrayList(Integer) with the tiles from the rack not already placed. Comes from
   *     InGameController method shuffleClicked.
   */
  public void shuffleRack(ArrayList<Integer> order) {
    Random rand = new Random();
    int random;
    int values = order.size();
    int swapWith;
    int swapOther;
    Tile swap;

    for (int i = 0; i < order.size(); i++) {
      random = rand.nextInt(values);
      swapWith = order.get(random);
      random = rand.nextInt(values);
      swapOther = order.get(random);
      order.remove(random);
      values--;
      swap = rack[swapOther];
      rack[swapOther] = rack[swapWith];
      rack[swapWith] = swap;
      rack[swapOther].setRackPlace(swapWith);
      rack[swapWith].setRackPlace(swapOther);
    }
  }

  /**
   * Getter to get the tile from the rack at position "pos".
   *
   * @author apilgrim
   * @param pos - position from the tile.
   * @return rack[pos] - Tile from rack at position "pos".
   */
  public Tile getTileAt(int pos) {
    return rack[pos];
  }
}
