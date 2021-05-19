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
  /*public void playerMoveHard() {
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
    int idxBestPos = 0;
    for (int i = 1; i < pos.size(); i++) {
        if (pos.get(idxBestPos).getPoints(pos.get(idxBestPos).getIndexOfMostPoints()) < pos.get(i)
            .getPoints(pos.get(i).getIndexOfMostPoints())) {
          idxBestPos = i;
        }
    }
    String word= pos.get(idxBestPos).getWord(pos.get(idxBestPos).getIndexOfMostPoints());
    int before=pos.get(idxBestPos).getBefore(pos.get(idxBestPos).getIndexOfMostPoints());
    int after= pos.get(idxBestPos).getAfter(pos.get(idxBestPos).getIndexOfMostPoints());
    if(pos.get(idxBestPos).getHorizontal(pos.get(idxBestPos).getIndexOfMostPoints())) {
      for(int i=1;i<=before;i++) {
        Data.getGameSession().getGameBoard().placeTile(Data.getGameSession().getBag().pick(), pos.get(idxBestPos).getY(), pos.get(idxBestPos).getX());
      }
    }
    else {
      
    }
  }*/

  /**
   * In this method all other methods will be called for the Easy AI
   *
   * @param
   * @author hraza
   */
  /*public void playerMoveEasy() {
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
  }*/

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
    while (y + 1 + counterDown <= 14 && x > 0 && x < 14 && g.isSpotFree(y + 1 + counterDown, x)
        && g.isSpotFree(y + 1 + counterDown, x + 1) && g.isSpotFree(y + 1 + counterDown, x - 1)) {
      counterDown++;
    }
    while (y + 1 + counterDown <= 14 && x == 0 && g.isSpotFree(y + 1 + counterDown, x)
        && g.isSpotFree(y + 1 + counterDown, x + 1)) {
      counterDown++;
    }
    while (y + 1 + counterDown <= 14 && x == 14 && g.isSpotFree(y + 1 + counterDown, x)
        && g.isSpotFree(y + 1 + counterDown, x - 1)) {
      counterDown++;
    }
    // Checking for free Spots over the Position x,y
    while (y - 1 - counterUp >= 0 && x > 0 && x < 14 && g.isSpotFree(y - 1 - counterUp, x)
        && g.isSpotFree(y - 1 - counterUp, x + 1) && g.isSpotFree(y - 1 - counterUp, x - 1)) {
      counterUp++;
    }
    while (y - 1 - counterUp >= 0 && x == 0 && g.isSpotFree(y - 1 - counterUp, x)
        && g.isSpotFree(y - 1 - counterUp, x + 1)) {
      counterUp++;
    }
    while (y - 1 - counterUp >= 0 && x == 14 && g.isSpotFree(y - 1 - counterUp, x)
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
}
