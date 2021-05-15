package com.scrab5.core.player;

import com.scrab5.core.game.GameBoard;
import com.scrab5.core.game.GameSession;
import com.scrab5.core.game.Tile;
import com.scrab5.ui.Data;
import com.scrab5.util.textParser.DictionaryScanner;
import java.util.ArrayList;

public class AiPlayer extends Player {

  private ArrayList<String> possibleWords;
  private ArrayList<Integer> possiblePoints;
  private ArrayList<Integer> x;
  private ArrayList<Integer> y;

  /**
   * @param name
   * @author hraza
   */

  public AiPlayer(String name) {
    super(name);
  }

  /**
   * In this method all other methods will be called
   *
   * @param
   * @author hraza
   */

  public void playerMove() {
    int x = this.findTile(Data.getGameSession().getGameBoard()).getColumn();
    int y = this.findTile(Data.getGameSession().getGameBoard()).getRow();
    

  }

  /**
   * In this method the current Gameboard will be scanned for occupied Spots
   *
   * @param
   * @author hraza
   */

  public Tile findTile(GameBoard currentBoard) {
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        if (!currentBoard.isSpotFree(i, j)) {
          return currentBoard.getTile(i, j);
        }
      }
    }
    return null;
  }

  /**
   * This Method is looking for free valid Spots around the given position
   *
   * @param x
   * @param y
   * @author hraza
   */

  public void getSpotsfree(int x, int y, GameBoard g) {
    int counterRight = 0;
    int counterLeft = 0;
    int counterUp = 0;
    int counterDown = 0;

    // Checking the right Side of the Position on the Board
    while (y < 14 && y > 0 && x + 1 + counterRight <= 14
        && g.isSpotFree(y, x + 1 + counterRight)
        && g.isSpotFree(y - 1, x + 1 + counterRight)
        && g.isSpotFree(y + 1, x + 1 + counterRight)) {
      counterRight++;
    }
    while (y == 0 && x + 1 + counterRight <= 14
        && g.isSpotFree(y, x + 1 + counterRight)
        && g.isSpotFree(y + 1, x + 1 + counterRight)) {
      counterRight++;
    }
    while (y == 14 && x + 1 + counterRight <= 14
        && g.isSpotFree(y, x + 1 + counterRight)
        && g.isSpotFree(y - 1, x + 1 + counterRight)) {
      counterRight++;
    }

    // Checking the left Side of the Position on the Board
    while (y < 14 && y > 0 && x - 1 - counterLeft >= 0
        && g.isSpotFree(y, x - 1 - counterLeft)
        && g.isSpotFree(y - 1, x - 1 - counterLeft)
        && g.isSpotFree(y + 1, x - 1 - counterLeft)) {
      counterLeft++;
    }
    while (y == 0 && x - 1 - counterLeft >= 0
        && g.isSpotFree(y, x - 1 - counterLeft)
        && g.isSpotFree(y + 1, x - 1 - counterLeft)) {
      counterLeft++;
    }
    while (y == 14 && x - 1 - counterLeft >= 0
        && g.isSpotFree(y, x - 1 - counterLeft)
        && g.isSpotFree(y - 1, x - 1 - counterLeft)) {
      counterLeft++;
    }

    // Checking for free Spots under the Position x,y
    while (y + 1 + counterDown < 14 && x > 0 && x < 14
        && g.isSpotFree(y + 1 + counterDown, x)
        && g.isSpotFree(y + 1 + counterDown, x + 1)
        && g.isSpotFree(y + 1 + counterDown, x - 1)) {
      counterDown++;
    }
    while (y + 1 + counterDown < 14 && x == 0
        && g.isSpotFree(y + 1 + counterDown, x)
        && g.isSpotFree(y + 1 + counterDown, x + 1)) {
      counterDown++;
    }
    while (y + 1 + counterDown < 14 && x == 14
        && g.isSpotFree(y + 1 + counterDown, x)
        && g.isSpotFree(y + 1 + counterDown, x - 1)) {
      counterDown++;
    }
    // Checking for free Spots over the Position x,y
    while (y - 1 - counterUp < 14 && x > 0 && x < 14
        && g.isSpotFree(y - 1 - counterUp, x)
        && g.isSpotFree(y - 1 - counterUp, x + 1)
        && g.isSpotFree(y - 1 - counterUp, x - 1)) {
      counterUp++;
    }
    while (y - 1 - counterUp < 14 && x == 0
        && g.isSpotFree(y - 1 - counterUp, x)
        && g.isSpotFree(y - 1 - counterUp, x + 1)) {
      counterUp++;
    }
    while (y - 1 - counterUp < 14 && x == 14
        && g.isSpotFree(y - 1 - counterUp, x)
        && g.isSpotFree(y - 1 - counterUp, x - 1)) {
      counterUp++;
    }
    this.wordGenerator(g.getTile(y, x).getLetter(), counterLeft,
        counterRight);
    this.wordGenerator(g.getTile(y, x).getLetter(), counterUp, counterDown);
  }

  /**
   * This Method will scan the Dictionary for words that fit to the spots on the gameboard
   *
   * @param fixLetter
   * @param before
   * @param after
   * @author hraza
   */
  public void wordGenerator(String fixLetter, int before, int after) {

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

  public static void main(String[] args) {
    // testing the methods
    System.out.println(calculatePoints("HELLO"));
  }
}
