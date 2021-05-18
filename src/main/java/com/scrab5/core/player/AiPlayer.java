package com.scrab5.core.player;

import com.scrab5.core.game.AiPosition;
import com.scrab5.core.game.GameBoard;
import com.scrab5.ui.Data;
import java.util.ArrayList;


public class AiPlayer extends Player {

  ArrayList<AiPosition> pos;
  int counterUp;
  int counterDown;
  int counterRight;
  int counterLeft;

  /**
   * @param name
   * @author hraza
   */
  public AiPlayer(String name) {
    super(name);
  }
  
  public int getCounterUp() {
    return this.counterUp;
  }
  
  public int getCounterDown() {
    return this.counterDown;
  }
  
  public int getCounterLeft() {
    return this.counterLeft;
  }
  
  public int getCounterRight() {
    return this.counterRight;
  }
  
  

  /**
   * In this method all other methods will be called for the Hard AI
   *
   * @param
   * @author hraza
   */
  public void playerMoveHard() {
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        if (!Data.getGameSession().getGameBoard().isSpotFree(i, j)) {
          int x = Data.getGameSession().getGameBoard().getTile(i, j).getColumn();
          int y = Data.getGameSession().getGameBoard().getTile(i, j).getRow();
          this.getSpotsfree(x, y, Data.getGameSession().getGameBoard());
          if (this.counterLeft > 0 | this.counterRight > 0) {
            this.wordGenerator(Data.getGameSession().getGameBoard().getTile(y, x).getLetter(),
                this.counterLeft, this.counterRight, x, y, true);
          } else if (this.counterUp > 0 | this.counterDown > 0) {
            this.wordGenerator(Data.getGameSession().getGameBoard().getTile(y, x).getLetter(),
                this.counterUp, this.counterDown, x, y, false);
          } else {
            continue;
          }
        }
      }
    }
    for (int i = 0; i < pos.size(); i++) {
      pos.get(i).sortPossibleWordsDescending();
    }
    this.sortPosMax();
    // JETZT FEHLT NOCH,DASS MAN VON POS(0) DAS WORT ERSTE WORT LEGT
  }

  /**
   * In this method all other methods will be called for the Easy AI
   *
   * @param
   * @author hraza
   */
  public void playerMoveEasy() {
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        if (!Data.getGameSession().getGameBoard().isSpotFree(i, j)) {
          int x = Data.getGameSession().getGameBoard().getTile(i, j).getColumn();
          int y = Data.getGameSession().getGameBoard().getTile(i, j).getRow();
          this.getSpotsfree(x, y, Data.getGameSession().getGameBoard());
          if (this.counterLeft > 0 | this.counterRight > 0) {
            this.wordGenerator(Data.getGameSession().getGameBoard().getTile(y, x).getLetter(),
                this.counterLeft, this.counterRight, x, y, true);
          } else if (this.counterUp > 0 | this.counterDown > 0) {
            this.wordGenerator(Data.getGameSession().getGameBoard().getTile(y, x).getLetter(),
                this.counterUp, this.counterDown, x, y, false);
          } else {
            continue;
          }
        }
      }
    }
    for (int i = 0; i < pos.size(); i++) {
      pos.get(i).sortPossibleWordsAscending();
    }
    this.sortPosMin();
    // JETZT FEHLT NOCH,DASS MAN VON POS(0) DAS WORT ERSTE WORT LEGT
  }

  /**
   * This Method is looking for free valid Spots around the given position and initializes the
   * counters
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
    while (y < 14 && y > 0 && x + 1 + counterRight <= 14 && g.isSpotFree(y, x + 1 + counterRight)
        && g.isSpotFree(y - 1, x + 1 + counterRight) && g.isSpotFree(y + 1, x + 1 + counterRight)) {
      counterRight++;
    }
    while (y == 0 && x + 1 + counterRight <= 14 && g.isSpotFree(y, x + 1 + counterRight)
        && g.isSpotFree(y + 1, x + 1 + counterRight)) {
      counterRight++;
    }
    while (y == 14 && x + 1 + counterRight <= 14 && g.isSpotFree(y, x + 1 + counterRight)
        && g.isSpotFree(y - 1, x + 1 + counterRight)) {
      counterRight++;
    }

    // Checking the left Side of the Position on the Board
    while (y < 14 && y > 0 && x - 1 - counterLeft >= 0 && g.isSpotFree(y, x - 1 - counterLeft)
        && g.isSpotFree(y - 1, x - 1 - counterLeft) && g.isSpotFree(y + 1, x - 1 - counterLeft)) {
      counterLeft++;
    }
    while (y == 0 && x - 1 - counterLeft >= 0 && g.isSpotFree(y, x - 1 - counterLeft)
        && g.isSpotFree(y + 1, x - 1 - counterLeft)) {
      counterLeft++;
    }
    while (y == 14 && x - 1 - counterLeft >= 0 && g.isSpotFree(y, x - 1 - counterLeft)
        && g.isSpotFree(y - 1, x - 1 - counterLeft)) {
      counterLeft++;
    }

    // Checking for free Spots under the Position x,y
    while (y + 1 + counterDown < 14 && x > 0 && x < 14 && g.isSpotFree(y + 1 + counterDown, x)
        && g.isSpotFree(y + 1 + counterDown, x + 1) && g.isSpotFree(y + 1 + counterDown, x - 1)) {
      counterDown++;
    }
    while (y + 1 + counterDown < 14 && x == 0 && g.isSpotFree(y + 1 + counterDown, x)
        && g.isSpotFree(y + 1 + counterDown, x + 1)) {
      counterDown++;
    }
    while (y + 1 + counterDown < 14 && x == 14 && g.isSpotFree(y + 1 + counterDown, x)
        && g.isSpotFree(y + 1 + counterDown, x - 1)) {
      counterDown++;
    }
    // Checking for free Spots over the Position x,y
    while (y - 1 - counterUp < 14 && x > 0 && x < 14 && g.isSpotFree(y - 1 - counterUp, x)
        && g.isSpotFree(y - 1 - counterUp, x + 1) && g.isSpotFree(y - 1 - counterUp, x - 1)) {
      counterUp++;
    }
    while (y - 1 - counterUp < 14 && x == 0 && g.isSpotFree(y - 1 - counterUp, x)
        && g.isSpotFree(y - 1 - counterUp, x + 1)) {
      counterUp++;
    }
    while (y - 1 - counterUp < 14 && x == 14 && g.isSpotFree(y - 1 - counterUp, x)
        && g.isSpotFree(y - 1 - counterUp, x - 1)) {
      counterUp++;
    }
    this.counterDown = counterDown;
    this.counterUp = counterUp;
    this.counterRight = counterRight;
    this.counterLeft = counterLeft;
  }

  /**
   * This Method will scan the Dictionary for words that fit to the size of the spot on the
   * gameboard, will create an AiPosition object,will fill this AiPosition with posWords and will
   * add it to the List pos
   *
   * @param fixLetter
   * @param before
   * @param after
   * @param x
   * @param y
   * @param horizontal
   * @author hraza
   */
  public void wordGenerator(String fixLetter, int before, int after, int x, int y,
      boolean horizontal) {
    // THIS METHOD SHOULD ONLY ADD VALID WORDS TO THE LIST THAT CAN BE BUILD WITH THE BAG


  }

  /**
   * This Method will sort the list pos, so that the Position with the biggest maxPoint is first
   *
   * @param
   * @author hraza
   */
  public void sortPosMax() {
    // sorting the pointslist and the wordlist
    AiPosition temp;
    for (int i = 0; i < this.pos.size(); i++) {
      for (int j = 1; j < this.pos.size(); j++) {
        if (this.pos.get(i).getMaxPoints() < this.pos.get(j).getMaxPoints()) {
          temp = this.pos.get(i);
          this.pos.add(i, this.pos.get(j));
          this.pos.add(j, temp);
        }
      }
    }
  }

  /**
   * This Method will sort the list pos, so that the Position who has the smallest minPoints is
   * first in the list pos
   *
   * @param
   * @author hraza
   */
  public void sortPosMin() {
    // sorting the pointslist and the wordlist
    AiPosition temp;
    for (int i = 0; i < this.pos.size(); i++) {
      for (int j = 1; j < this.pos.size(); j++) {
        if (this.pos.get(i).getMinPoints() > this.pos.get(j).getMinPoints()) {
          temp = this.pos.get(i);
          this.pos.add(i, this.pos.get(j));
          this.pos.add(j, temp);
        }
      }
    }
  }

  public static void main(String[] args) {
    // testing the methods
    System.out.println("Hi");
  }
}