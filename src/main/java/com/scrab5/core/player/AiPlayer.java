package com.scrab5.core.player;

import com.scrab5.core.game.AiPosition;
import com.scrab5.core.game.BagOfTiles;
import com.scrab5.core.game.GameBoard;
import com.scrab5.core.game.GameSession;
import com.scrab5.core.game.Tile;
import com.scrab5.ui.Data;
import com.scrab5.util.database.UseDatabase;
import com.scrab5.util.textParser.DictionaryScanner;
import java.util.ArrayList;
import java.util.HashMap;


public class AiPlayer extends Player {

  ArrayList<AiPosition> pos;
  int counterUp;
  int counterDown;
  int counterRight;
  int counterLeft;
  String[] lettersFromDatabase;
  int[] pointsPerLetterFromDatabase;


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
  /*
   * public void playerMoveHard() { for (int i = 0; i < 15; i++) { for (int j = 0; j < 15; j++) { if
   * (!Data.getGameSession().getGameBoard().isSpotFree(i, j)) { int x =
   * Data.getGameSession().getGameBoard().getTile(i, j).getColumn(); int y =
   * Data.getGameSession().getGameBoard().getTile(i, j).getRow(); this.getSpotsfree(x, y,
   * Data.getGameSession().getGameBoard()); if (this.counterLeft > 0 | this.counterRight > 0) {
   * this.wordGenerator(Data.getGameSession().getGameBoard().getTile(y, x).getLetter(),
   * this.counterLeft, this.counterRight, x, y, true); } else if (this.counterUp > 0 |
   * this.counterDown > 0) { this.wordGenerator(Data.getGameSession().getGameBoard().getTile(y,
   * x).getLetter(), this.counterUp, this.counterDown, x, y, false); } else { continue; } } } } int
   * idxBestPos = 0; for (int i = 1; i < pos.size(); i++) { if
   * (pos.get(idxBestPos).getPoints(pos.get(idxBestPos).getIndexOfMostPoints()) < pos.get(i)
   * .getPoints(pos.get(i).getIndexOfMostPoints())) { idxBestPos = i; } } String word=
   * pos.get(idxBestPos).getWord(pos.get(idxBestPos).getIndexOfMostPoints()); int
   * before=pos.get(idxBestPos).getBefore(pos.get(idxBestPos).getIndexOfMostPoints()); int after=
   * pos.get(idxBestPos).getAfter(pos.get(idxBestPos).getIndexOfMostPoints());
   * if(pos.get(idxBestPos).getHorizontal(pos.get(idxBestPos).getIndexOfMostPoints())) { for(int
   * i=1;i<=before;i++) {
   * Data.getGameSession().getGameBoard().placeTile(Data.getGameSession().getBag().pick(),
   * pos.get(idxBestPos).getY(), pos.get(idxBestPos).getX()); } } else {
   * 
   * } }
   */

  /**
   * In this method all other methods will be called for the Easy AI
   *
   * @param
   * @author hraza
   */
  /*
   * public void playerMoveEasy() { for (int i = 0; i < 15; i++) { for (int j = 0; j < 15; j++) { if
   * (!Data.getGameSession().getGameBoard().isSpotFree(i, j)) { int x =
   * Data.getGameSession().getGameBoard().getTile(i, j).getColumn(); int y =
   * Data.getGameSession().getGameBoard().getTile(i, j).getRow(); this.getSpotsfree(x, y,
   * Data.getGameSession().getGameBoard()); if (this.counterLeft > 0 | this.counterRight > 0) {
   * this.wordGenerator(Data.getGameSession().getGameBoard().getTile(y, x).getLetter(),
   * this.counterLeft, this.counterRight, x, y, true); } else if (this.counterUp > 0 |
   * this.counterDown > 0) { this.wordGenerator(Data.getGameSession().getGameBoard().getTile(y,
   * x).getLetter(), this.counterUp, this.counterDown, x, y, false); } else { continue; } } } } }
   */

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
   * Method to generate a fitting word and create a ArrayList containing a ArrayList of Tiles for
   * the possible words.
   * 
   * @author lengist
   * @param fixLetter the Letter that is already placed on the GameBoard where the Ai wants to lay a
   *        word next to
   * @param before the amount of tiles that are free before this letter
   * @param after the amount of tiles that are free after this letter
   * @param x the x-coordinate of the tile with letter fixLetter on the board
   * @param y the y-coordinate of the tile with letter fixLetter on the board
   * @param horizontal is true, when the word needs to get laid horizontal and false if vertical.
   *        This parameter is needed later on
   */
  public static void wordGenerator(String fixLetter, int before, int after, int x, int y,
      boolean horizontal) {
    ArrayList<Tile> listOfTiles = new ArrayList<Tile>();
    ArrayList<String> possibleLetters1 = new ArrayList<String>();
    ArrayList<String> finalWords = new ArrayList<String>();
    ArrayList<String> deletionRound1 = new ArrayList<String>();
    ArrayList<String> deletionRound2 = new ArrayList<String>();
    int maximumLength = before + 1 + after;
    int before2 = 0;
    int after2 = 0;

    BagOfTiles bag = Data.getGameSession().getBag();
    listOfTiles = bag.getBag();

    for (Tile tile : listOfTiles) {
      String letter = tile.getLetter();
      possibleLetters1.add(letter);
    }

    String[] possibleLetters = new String[possibleLetters1.size()];
    for (int i = 0; i < possibleLetters.length; i++) {
      possibleLetters[i] = possibleLetters1.get(i);
    }


    ArrayList<String> first = DictionaryScanner.getWordsIncluding(fixLetter, maximumLength);
    for (String b1 : possibleLetters) {
      first = DictionaryScanner.getWordsIncludingFrom(first, b1);
      for (String s : first) {
        finalWords.add(s);
      }
    }

    /*
     * Now: finalWords includes now all words with at least one of the letters from possibleLetters.
     * The next part of the method checks that the words in finalWords at the end only consist of
     * the letters in possibleLetters.
     */
    StringBuilder sb = new StringBuilder();
    for (String s : possibleLetters) {
      sb.append(s);
    }
    String b = sb.toString();
    for (String s : finalWords) {
      for (int i = 0; i < s.length(); i++) {
        if (b.indexOf(s.charAt(i)) == -1) {
          deletionRound1.add(s);
        }
      }
    }
    finalWords.removeAll(deletionRound1);

    if (finalWords.isEmpty()) {
      /* TODO: change next spot */
    }

    /* The next part checks the words in finalWords if they fulfill the requirements */
    for (String s : finalWords) {
      for (int i = 0; i < s.length(); i++) {

        if (s.charAt(i) == fixLetter.charAt(0)) {
          before2 = i;
          after2 = s.length() - i;

          if (before2 > before) {
            deletionRound2.add(s);
          } else if (after2 > after) {
            deletionRound2.add(s);
          }
        }
      }
    }
    finalWords.removeAll(deletionRound2);

    if (finalWords.isEmpty()) {
      /* TODO: change next spot */
    }

    /*
     * TODO: check that the letters only appear the given amount --> Hedis Methode aufrufen, gibt
     * boolean zurueck --> true, dass okay --> Wort bleibt, ansonsten remove
     */

    /* TODO: create the following list with a list of tiles for Timm */
    /*
     * TODO: einmal alle punkte fuer Buchstaben aus Database in Klassenvariable, damit ich value
     * hinzufuegen kann
     */
    ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
    for (String s : finalWords) {

    }

    /* TODO: Possible add: condition to end this method after for example 15 words. */
  }

  /**
   * Method to get all the points for the letters in the database to find out the points for a
   * possible word. The values will be saved in a local variable.
   * 
   * @author lengist
   */
  public void setLetterPoints() {
    lettersFromDatabase = UseDatabase.getAllLetters();
    pointsPerLetterFromDatabase = UseDatabase.getAllPointsPerLetter();
  }

  /**
   * Method to test the function for the AiPlayer to generate a fitting word. Just created to test
   * this function in the JUnit test class, not for usage in the game.
   * 
   * @author lengist
   * @param fixLetter the Letter that is already placed on the GameBoard where the Ai wants to lay a
   *        word next to
   * @param before the amount of tiles that are free before this letter
   * @param after the amount of tiles that are free after this letter
   * @param x the x-coordinate of the tile with letter fixLetter on the board
   * @param y the y-coordinate of the tile with letter fixLetter on the board
   * @param horizontal is true, when the word needs to get laid horizontal and false if vertical.
   *        This parameter is needed later on
   */
  public static String[] wordGeneratorTest(String fixLetter, int before, int after, int x, int y,
      boolean horizontal) {
    ArrayList<String> finalWords = new ArrayList<String>();
    ArrayList<String> deletionRound1 = new ArrayList<String>();
    ArrayList<String> deletionRound2 = new ArrayList<String>();
    int maximumLength = before + 1 + after;
    int before2 = 0;
    int after2 = 0;
    String[] possibleLetters = {"A", "L", "I", "V", "E", "O", "N"};

    ArrayList<String> first = DictionaryScanner.getWordsIncluding(fixLetter, maximumLength);
    for (String b1 : possibleLetters) {
      first = DictionaryScanner.getWordsIncludingFrom(first, b1);
      for (String s : first) {
        finalWords.add(s);
      }
    }

    StringBuilder sb = new StringBuilder();
    for (String s : possibleLetters) {
      sb.append(s);
    }
    String b = sb.toString();
    for (String s : finalWords) {
      for (int i = 0; i < s.length(); i++) {
        if (b.indexOf(s.charAt(i)) == -1) {
          deletionRound1.add(s);
        }
      }
    }
    finalWords.removeAll(deletionRound1);

    for (String s : finalWords) {
      for (int i = 0; i < s.length(); i++) {

        if (s.charAt(i) == fixLetter.charAt(0)) {
          before2 = i;
          after2 = s.length() - i;

          if (before2 > before) {
            deletionRound2.add(s);
          } else if (after2 > after) {
            deletionRound2.add(s);
          }
        }
      }
    }
    finalWords.removeAll(deletionRound2);

    String[] ready = new String[finalWords.size()];
    for (int i = 0; i < ready.length; i++) {
      ready[i] = finalWords.get(i);
    }
    return ready;
  }

  /**
   * This Method is gettig a String word and the current Bag-Occurence hashmap handed over and then
   * checks if there are enough letters in the Bag to create the word. For every Letter in the word
   * it substracts 1 from the value in the Hashmap and then checks if the value of this Letter is
   * still positive. If it is negative, the boolean will be changed to false, the loop breaks and
   * the hashmap will be resetted and false is given back
   *
   * @param currentDistribution
   * @param word
   * @author hraza
   */
  public static Boolean checkBagDistributionLegal(HashMap<String, Integer> currentDistribution,
      String word) {
    boolean b = true;
    int j = 0;
    for (int i = 0; i < word.length(); i++) {
      currentDistribution.put(Character.toString(word.charAt(i)),
          currentDistribution.get(Character.toString(word.charAt(i))) - 1);
      if (currentDistribution.get(Character.toString(word.charAt(i))) < 0) {
        b = false;
        j = i;
        break;
      } else {
        j = word.length() - 1;
      }
    }
    System.out.println(j);
    for (int i = 0; i <= j; i++) {
      currentDistribution.put(Character.toString(word.charAt(i)),
          currentDistribution.get(Character.toString(word.charAt(i))) + 1);
    }
    return b;
  }

  /* Just for direct testing: */
  public static void main(String[] args) {
    // wordGenerator("A", 3, 4, 0, 0, true);
    String[] test = wordGeneratorTest("A", 3, 4, 0, 0, true);
    for (String s : test) {
      System.out.println(s);
    }
  }
}
