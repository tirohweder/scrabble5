package com.scrab5.core.game;

import java.util.ArrayList;
import java.util.Random;

public class Rack {

  private Tile[] rack = new Tile[7];


  /**
   * @param bag
   * @author trohwede
   */
  public void fill(BagOfTiles bag) {
    for (int i = 0; i < 7; i++) {
      System.out.println(rack[i]);
      System.out.println(rack[i] == null);
      if (this.rack[i] == null && bag.getSize() >= 1) {
        System.out.println("FillsRack: " + i);
        rack[i] = bag.pick();
        rack[i].setRackPlace(i);
      }
    }

    System.out.println("Rack at 0 = " + rack[0]);
  }

  /**
   * @return
   * @author trohwede
   */
  public boolean rackFull() {
    for (int i = 0; i < 7; i++) {
      if (this.rack[i] == null) {
        return false;
      }
    }
    return true;
  }


  public boolean exchangeRack(int place) {

    if (rack[place] != null) {

    }

    return false;
  }

  /**
   * @param t
   * @param pos
   * @author trohwede
   */
  public void addToRack(Tile t, int pos) {
    this.rack[pos] = t;
  }


  /**
   * @param pos
   * @author trohwede
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
   * @author trohwede
   */
  public void resetRack() {
    for (int i = 6; i >= 0; i--) {
      this.rack[i] = null;
    }
  }

  /**
   * @author hraza
   */
  public String toString() {
    String s = new String();
    for (int i = 0; i < 7; i++) {
      s += this.rack[i].getLetter();
    }
    return s;
  }

  /**
   * @author apilgrim
   */
  public void shuffleRack() {

    ArrayList<Integer> order = new ArrayList<>();

    order.add(0);
    order.add(1);
    order.add(2);
    order.add(3);
    order.add(4);
    order.add(5);
    order.add(6);

    Random rand = new Random();
    int random, values = 7, swapWith;
    Tile swap;

    for (int i = 1; i <= 7; i++) {
      random = rand.nextInt(values);
      swapWith = order.get(random);
      order.remove(random);
      values--;
      swap = rack[i];
      rack[i] = rack[swapWith];
      rack[swapWith] = swap;
      rack[i].setRackPlace(swapWith);
      rack[swapWith].setRackPlace(i);
    }
  }

  /**
   * @author apilgrim
   */
  public Tile getTileAt(int pos) {
    return rack[pos];
  }
}
