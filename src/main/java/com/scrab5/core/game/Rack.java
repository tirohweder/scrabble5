package com.scrab5.core.game;

import com.scrab5.ui.Data;
import java.io.Serializable;
import java.util.ArrayList;
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
      // System.out.println(rack[i]);
      // System.out.println(rack[i] == null);
      if (this.rack[i] == null && bag.getSize() >= 1) {
        rack[i] = bag.pick();
        rack[i].setRackPlace(i);
      }
    }
    System.out.println("Trying to fill rack of " + Data.getCurrentUser());
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
   * Adds the tile t to the position pos in the rack. //TODO ASK why not needed
   *
   * @author trohwede
   * @param t the tile that wants to be added
   * @param pos position of where to add the tile in the rack
   */
  public void addToRack(Tile t, int pos) {
    this.rack[pos] = t;
  }

  /**
   * Removes a tile from the rack at the given position.
   *
   * @author trohwede
   * @param pos position of the rack you want to remove the tile from
   */
  public boolean removeTileFromRack(int pos) {
    if (this.rack[pos] != null) {
      this.rack[pos] = null;
      return true;
    } else {
      return false;
    }
  }

  /**
   * //TODO AARON.
   *
   * @author apilgrim
   */
  public void shuffleRack(ArrayList<Integer> order) {
    Random rand = new Random();
    int random, values = order.size(), swapWith, swapOther;
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
   * //TODO AARON.
   *
   * @author apilgrim
   */
  public Tile getTileAt(int pos) {
    return rack[pos];
  }
}
