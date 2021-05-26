package com.scrab5.core.game;

import com.scrab5.ui.Data;
import com.scrab5.util.database.UseDatabase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

/**
 * BagOfTiles represents the bag of tiles in the game, all tiles are stored in here at the
 * beginning.
 *
 * @author trohwede
 */
public class BagOfTiles implements Serializable {

  private static final long serialVersionUID = 1L;
  private ArrayList<Tile> bag = new ArrayList<>();

  /**
   * This function adds the given Tile to the bag.
   *
   * @param t - The Tile that is being added
   * @author trohwede
   */
  public void add(Tile t) {
    this.bag.add(t);
  }

  /**
   * Returns a random Tile from the bag.
   *
   * @return a random Tile inside of the bag. If the Bag is empty returns null.
   * @author trohwede
   */
  public Tile pick() {
    if (bag.isEmpty()) {
      return null;
    }
    Tile test = bag.get(new Random().nextInt(bag.size()));
    bag.remove(test);

    return test;
  }

  public ArrayList<Tile> getBag() {
    return bag;
  }

  public void setBag(ArrayList<Tile> bag) {
    this.bag = bag;
  }

  /**
   * Returns the size of the bag.
   *
   * @return the size of the bag.
   * @author trohwede
   */
  public int getSize() {
    return this.bag.size();
  }

  /**
   * Returns the current distribution of the bag, in a HashMap format.
   *
   * @return the current Bags distribution.
   * @author trohwede
   */
  public HashMap<String, Integer> getCurrentBagDistribution() {
    HashMap<String, Integer> wordDistro = new HashMap<>();

    for (Tile tile : Data.getGameSession().getBag().bag) {
      wordDistro.merge(tile.getLetter(), 1, Integer::sum);
    }

    return wordDistro;
  }

  /**
   * Removes all items of the bag first. And then create a new bag with the given distribution.
   *
   * @param newWordDistro the new distribution for the bag.
   * @author trohwede
   */
  public void setBagWithDistribution(HashMap<String, Integer> newWordDistro) {
    bag.clear();

    String[] letter = UseDatabase.getAllLetters();
    //int[] points = UseDatabase.getAllPointsPerLetter();
    int[] occurrences = UseDatabase.getAllOccurrences();
    //distribution
    int set = 0;
    
    Iterator<Entry<String, Integer>> it = newWordDistro.entrySet().iterator();
    int count = 0;
    while (it.hasNext()) {
      Entry<String, Integer> pair = it.next();
      for (int i = 0; i < pair.getValue(); i++) {
        for (int j = 0; j < letter.length; j++) {
          if (pair.getKey().equals(letter[j])) {
            set = j;
          }
        }
        //bag.add(count, new Tile(pair.getKey(), points[set]));
        bag.add(count, new Tile(pair.getKey(), occurrences[set]));
        count++;
      }
      it.remove();
    }

    /*Iterator<Entry<String, Integer>> it = newWordDistro.entrySet().iterator();
    int count = 0;
    while (it.hasNext()) {
      Entry<String, Integer> pair = it.next();
      for (int i = 0; i < pair.getValue(); i++) {
        bag.add(count, new Tile(pair.getKey(), UseDatabase.getPointForLetter(pair.getKey())));
        count++;
      }
      it.remove();
    }*/
  }
}
