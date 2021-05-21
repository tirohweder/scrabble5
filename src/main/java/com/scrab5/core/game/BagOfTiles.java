package com.scrab5.core.game;

import com.scrab5.util.database.UseDatabase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class BagOfTiles implements Serializable {

  public ArrayList<Tile> getBag() {
    return bag;
  }

  public void setBag(ArrayList<Tile> bag) {
    this.bag = bag;
  }

  private static final long serialVersionUID = 1L;

  private ArrayList<Tile> bag = new ArrayList<>();

  /**
   * Empty Constructor
   *
   * @return
   * @author trohwede
   */
  public BagOfTiles() {
  }


  /**
   * adds tile to bag
   *
   * @param t
   * @author trohwede
   */
  public void add(Tile t) {
    this.bag.add(t);
  }


  /**
   * @return
   * @author trohwede
   */
  public Tile pick() {
    // picks last Tile in Bag of Tiles, returns it and removes it from bag
    if (bag.isEmpty()) {
      return null;
    }

    Tile test = bag.get(new Random().nextInt(bag.size()));
    bag.remove(test);

    System.out.println("BAG SIZE IS : " + bag.size());
    return test;
  }


  /**
   * @return
   * @author trohwede
   */
  public int getSize() {
    // returns how many Tiles are left in the Bag
    return this.bag.size();
  }

  //
  //public int removeSpecific() {

  //}


  public HashMap<String, Integer> getCurrentBagDisrubtion() {

    HashMap<String, Integer> wordDistro = null;

    for (int i = 0; i < bag.size(); i++) {
      wordDistro.merge(bag.get(i).getLetter(), 1, Integer::sum);
    }

    return wordDistro;
  }


  public void setBagWithDisto(HashMap<String, Integer> newWordDistro) {
    bag.clear();

    Iterator it = newWordDistro.entrySet().iterator();
    int count = 0;
    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry) it.next();
      for (int i = 0; i < (int) pair.getValue(); i++) {
        bag.add(count, new Tile((String) pair.getKey(),
            UseDatabase.getPointForLetter((String) pair.getKey())));
        count++;
      }

      it.remove();
    }

  }

  //TODO String[][] a, b,
  //TODO            5, 2,

  //TODO currentBagDisrubition. word. ""APFEL"

  // String[][] a
  // Sring [][] 5-1

  // newBagDisrubtion

  // intilaize bag (newBagDisrubiton)

}
