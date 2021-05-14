package com.scrab5.core.player;

import java.util.ArrayList;
import com.scrab5.core.game.GameSession;
import com.scrab5.util.textParser.DictionaryScanner;

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

  /**
   * In this method all other methods will be called
   *
   * @param
   * @author hraza
   */

  public void playerMove() {

  }

  /**
   * In this method the current Gameboard will be scanned for occupied Spots
   *
   * @param
   * @author hraza
   */

  public void findTileWithLetter() {
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        if (!GameSession.getGameBoard().isSpotFree(i, j)) {
          getSpotsfree(j, i);
        }
      }
    }

  }

  /**
   * This Method is looking for free valid Spots around the given position
   *
   * @param x
   * @param y
   * @author hraza
   */

  public static void getSpotsfree(int x, int y) {
    int counterRight = 0;
    int counterLeft = 0;
    int counterUp = 0;
    int counterDown = 0;

    // Checking the right Side of the Position on the Board
    while (y < 14 && y > 0 && x + 1 + counterRight <= 14
        && GameSession.getGameBoard().isSpotFree(y, x + 1 + counterRight)
        && GameSession.getGameBoard().isSpotFree(y - 1, x + 1 + counterRight)
        && GameSession.getGameBoard().isSpotFree(y + 1, x + 1 + counterRight)) {
      counterRight++;
    }
    while (y == 0 && x + 1 + counterRight <= 14
        && GameSession.getGameBoard().isSpotFree(y, x + 1 + counterRight)
        && GameSession.getGameBoard().isSpotFree(y + 1, x + 1 + counterRight)) {
      counterRight++;
    }
    while (y == 14 && x + 1 + counterRight <= 14
        && GameSession.getGameBoard().isSpotFree(y, x + 1 + counterRight)
        && GameSession.getGameBoard().isSpotFree(y - 1, x + 1 + counterRight)) {
      counterRight++;
    }

    // Checking the left Side of the Position on the Board
    while (y < 14 && y > 0 && x - 1 - counterLeft >= 0
        && GameSession.getGameBoard().isSpotFree(y, x - 1 - counterLeft)
        && GameSession.getGameBoard().isSpotFree(y - 1, x - 1 - counterLeft)
        && GameSession.getGameBoard().isSpotFree(y + 1, x - 1 - counterLeft)) {
      counterLeft++;
    }
    while (y == 0 && x - 1 - counterLeft >= 0
        && GameSession.getGameBoard().isSpotFree(y, x - 1 - counterLeft)
        && GameSession.getGameBoard().isSpotFree(y + 1, x - 1 - counterLeft)) {
      counterLeft++;
    }
    while (y == 14 && x - 1 - counterLeft >= 0
        && GameSession.getGameBoard().isSpotFree(y, x - 1 - counterLeft)
        && GameSession.getGameBoard().isSpotFree(y - 1, x - 1 - counterLeft)) {
      counterLeft++;
    }

    // Checking for free Spots under the Position x,y
    while (y + 1 + counterDown < 14 && x > 0 && x < 14
        && GameSession.getGameBoard().isSpotFree(y + 1 + counterDown, x)
        && GameSession.getGameBoard().isSpotFree(y + 1 + counterDown, x + 1)
        && GameSession.getGameBoard().isSpotFree(y + 1 + counterDown, x - 1)) {
      counterDown++;
    }
    while (y + 1 + counterDown < 14 && x == 0
        && GameSession.getGameBoard().isSpotFree(y + 1 + counterDown, x)
        && GameSession.getGameBoard().isSpotFree(y + 1 + counterDown, x + 1)) {
      counterDown++;
    }
    while (y + 1 + counterDown < 14 && x == 14
        && GameSession.getGameBoard().isSpotFree(y + 1 + counterDown, x)
        && GameSession.getGameBoard().isSpotFree(y + 1 + counterDown, x - 1)) {
      counterDown++;
    }
    // Checking for free Spots over the Position x,y
    while (y - 1 - counterUp < 14 && x > 0 && x < 14
        && GameSession.getGameBoard().isSpotFree(y - 1 - counterUp, x)
        && GameSession.getGameBoard().isSpotFree(y - 1 - counterUp, x + 1)
        && GameSession.getGameBoard().isSpotFree(y - 1 - counterUp, x - 1)) {
      counterUp++;
    }
    while (y - 1 - counterUp < 14 && x == 0
        && GameSession.getGameBoard().isSpotFree(y - 1 - counterUp, x)
        && GameSession.getGameBoard().isSpotFree(y - 1 - counterUp, x + 1)) {
      counterUp++;
    }
    while (y - 1 - counterUp < 14 && x == 14
        && GameSession.getGameBoard().isSpotFree(y - 1 - counterUp, x)
        && GameSession.getGameBoard().isSpotFree(y - 1 - counterUp, x - 1)) {
      counterUp++;
    }
    wordGenerator(GameSession.getGameBoard().getTile(y, x).getLetter(), counterLeft, counterRight,
        x, y);
    wordGenerator(GameSession.getGameBoard().getTile(y, x).getLetter(), counterUp, counterDown, x,
        y);

  }

  /**
   * This Method will create all combinations of Bag-tiles and the tile on the Gameboard and will
   * add the valid ones to the wordslist
   *
   * @param fixLetter
   * @param before
   * @param after
   * @param xFixLetter
   * @param yFixLetter
   * @author hraza
   */

  public static void wordGenerator(String fixLetter, int before, int after, int xFixLetter,
      int yFixLetter) {
    // get all the remaining tiles from the bag
    while (GameSession.getBag().getSize() > 0) {

    }
  }

  public void printAllKLength(char[] set, int k, int fixPos, char fixLetter) {
    int counter = 0;
    int n = set.length;
    this.possibleWords.add(printAllKLengthRec(set, "", n, k, fixPos, fixLetter));

  }

  public String printAllKLengthRec(char[] set, String prefix, int n, int k, int fixPos,
      int fixLetter) {

    if (k == 0) {
      if (DictionaryScanner.scan((prefix))) {
        System.out.println(prefix);
        return prefix;
      } else {
        return "";
      }
    }

    for (int i = 0; i < n; ++i) {
      String newPrefix = prefix + set[i];
      if (newPrefix.length() > fixPos && newPrefix.charAt(fixPos) != fixLetter) {
        continue;
      }
      printAllKLengthRec(set, newPrefix, n, k - 1, fixPos, fixLetter);
    }
    return "";
  }

  /**
   * This Method will calculate the the Points of a word
   *
   * @param word
   * @author hraza
   */

  public void calculatePoints(String word) {
    // call the method from Gameboard to calculate the Points

  }

  public static void main(String[] args) {
    // testing the methods

    AiPlayer ai = new AiPlayer("Peter");

    System.out.println("Test");
    char[] set = {'H', 'E', 'L', 'L', 'O'};
    int k = 8;
    ai.printAllKLength(set, k, 2, 'L');
    if (DictionaryScanner.scan(("HALLO"))) {
      System.out.println("ja");

    }
  }

}
