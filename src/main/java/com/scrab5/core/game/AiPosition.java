package com.scrab5.core.game;

import com.scrab5.ui.Data;
import java.util.ArrayList;
import java.util.Collections;

public class AiPosition {

  private int x;
  private int y;
  String fixLetter;
  private ArrayList<String> possibleWords = new ArrayList();
  private ArrayList<Integer> possiblePoints = new ArrayList();
  private ArrayList<Integer> before = new ArrayList();
  private ArrayList<Integer> after = new ArrayList();
  private ArrayList<Boolean> horizontal = new ArrayList();

  /**
   * Constructor
   *
   * @param x
   * @param y
   * @param fixLetter
   * @author hraza
   */
  public AiPosition(int x, int y, String fixLetter) {
    this.x = x;
    this.y = y;
    this.fixLetter = fixLetter;
  }

  /**
   * With this method you can add elements to the possibleWords list
   *
   * @param word
   * @param before
   * @param after
   * @param horizontal
   * @author hraza
   */
  public void add(String word, int before, int after, Boolean horizontal) {
    this.possibleWords.add(word);
    this.possiblePoints.add(calculatePoints(word));
    this.after.add(after);
    this.before.add(before);
    this.horizontal.add(horizontal);
  }

  /**
   * Word getter
   *
   * @param idx
   * @author hraza
   */
  public String getWord(int idx) {
    return this.possibleWords.get(idx);
  }

  /**
   * Word getter
   *
   * @param idx
   * @author hraza
   */
  public int getX() {
    return this.x;
  }

  /**
   * Word getter
   *
   * @param idx
   * @author hraza
   */
  public int getY() {
    return this.y;
  }

  /**
   * Direction getter
   *
   * @param idx
   * @author hraza
   */
  public Boolean getHorizontal(int idx) {
    return this.horizontal.get(idx);
  }

  /**
   * Before-counter getter
   *
   * @param idx
   * @author hraza
   */
  public int getBefore(int idx) {
    return this.before.get(idx);
  }

  /**
   * After-counter getter
   *
   * @param idx
   * @author hraza
   */
  public int getAfter(int idx) {
    return this.after.get(idx);
  }

  /**
   * Points getter
   *
   * @param idx
   * @author hraza
   */
  public int getPoints(int idx) {
    return this.possiblePoints.get(idx);
  }

  /**
   * This method will give back the index of the word with most points
   *
   * @param
   * @author hraza
   */
  public int getIndexOfMostPoints() {
    Integer maxPoints = Collections.max(this.possiblePoints);
    Integer maxIdx = this.possiblePoints.indexOf(maxPoints);
    return maxIdx;
  }

  /**
   * This method will give back the index of the word with fewest points
   *
   * @param
   * @author hraza
   */
  public int getIndexOfSmallestPoints() {
    Integer minPoints = Collections.min(this.possiblePoints);
    Integer minIdx = this.possiblePoints.indexOf(minPoints);
    return minIdx;
  }

  /**
   * This Method will calculate the the Points of a word
   *
   * @param word
   * @author hraza
   */
  public static int calculatePoints(String word) {
    String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int[] points =
        new int[]{1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 2, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
    int score = 0;
    for (int i = 0; i < word.length(); i++) {
      for (int j = 0; j < s.length(); j++) {
        if (word.charAt(i) == s.charAt(j)) {
          score += points[j];
        }
      }
    }
    return score;
  }

  public int countScore() {

    String[][] gameBoardSpecial = Data.getGameSession().getGameBoard().getGameBoardSpecial();

    int score = 0;
    boolean tws = false;
    boolean dws = false;

    StringBuilder word = new StringBuilder();
    
    return score;


  }

}
