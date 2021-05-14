package com.scrab5.core.player;

import java.util.ArrayList;

public class AiPlayer extends Player {

  private ArrayList<String> possibleWords;
  private ArrayList<Integer> possiblePoints;
  private ArrayList<Integer> x;
  private ArrayList<Integer> y;
  private int diff;

  /**
   * @param name
   * @author hraza
   */

  public AiPlayer(String name, int diff) {
    super(name);
    this.diff = diff;
  }


}
