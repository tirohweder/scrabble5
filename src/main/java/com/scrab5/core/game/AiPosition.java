package com.scrab5.core.game;

import java.util.ArrayList;

public class AiPosition {
  private int x;
  private int y;
  private ArrayList<String> possibleWords;
  private ArrayList<Integer> possiblePoints;
  private ArrayList<Integer> before;
  private ArrayList<Integer> after;
  private ArrayList<Boolean> horizontal;
  int maxPoints;
  int minPoints;

  public AiPosition(int x, int y, ArrayList<String> possibleWords, ArrayList<Integer> before,
      ArrayList<Integer> after) {
    this.x = x;
    this.y = y;
    this.possibleWords = possibleWords;
    this.before = before;
    this.after = after;
    // CALCULATE POINTS FOR EVERY WORD IN THE WORDSLIST(FILL THE POINTS LIST)
  }

  public int getMaxPoints() {
    return this.maxPoints;
  }

  public int getMinPoints() {
    return this.minPoints;
  }

  /**
   * This Method will calculate the the Points of a word
   *
   * @param word
   * @author hraza
   */
  public static int calculatePoints(String word) {
    // call the method from Gameboard to calculate the Points
    String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int[] points =
        new int[] {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 2, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
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

  /**
   * This Method will sort the lists "possiblePoints", "possibleWords", "before", "after" and "horizontal" at the same Time, so the word
   * with the lowest points is first and initializes the minPoint and maxPoint
   *
   * @param
   * @author hraza
   */
  public void sortPossibleWordsAscending() {
    int temp;
    String tempString;
    for (int i = 0; i < this.possiblePoints.size(); i++) {
      for (int j = 1; j < this.possiblePoints.size(); j++) {
        if (this.possiblePoints.get(i) > this.possiblePoints.get(j)) {
          temp = this.possiblePoints.get(i);
          tempString = this.possibleWords.get(i);
          this.possibleWords.add(i, this.possibleWords.get(j));
          this.possibleWords.add(j, tempString);
          this.possiblePoints.add(i, this.possiblePoints.get(j));
          this.possiblePoints.add(j, temp);
          // YOU HAVE TO SORT THE LISTS BEFORE AND AFTER AND HORIZONTAL TOOO
          // YOU HAVE TO INITIALIZE THE MIN AND MAXPOINT

        }
      }
    }
  }

  /**
   * This Method will sort the lists "possiblePoints", "possibleWords", "before", "after" and "horizontal" at the same Time, so the word
   * with the highest points is first. It also intializes "minPoint" and "maxPoint"
   *
   * @param
   * @author hraza
   */
  public void sortPossibleWordsDescending() {
    // sorting the pointslist and the wordlist
    int temp;
    String tempString;
    for (int i = 0; i < this.possiblePoints.size(); i++) {
      for (int j = 1; j < this.possiblePoints.size(); j++) {
        if (this.possiblePoints.get(i) < this.possiblePoints.get(j)) {
          temp = this.possiblePoints.get(i);
          tempString = this.possibleWords.get(i);
          this.possibleWords.add(i, this.possibleWords.get(j));
          this.possibleWords.add(j, tempString);
          this.possiblePoints.add(i, this.possiblePoints.get(j));
          this.possiblePoints.add(j, temp);
          // YOU HAVE TO SORT THE LISTS BEFORE AND AFTER AND HORIZONTAL TOOO
          // YOU HAVE TO INITIALIZE THE MIN AND MAXPOINT
        }
      }
    }
  }
  
}
