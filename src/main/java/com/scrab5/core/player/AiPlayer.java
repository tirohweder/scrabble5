package com.scrab5.core.player;

import com.scrab5.core.game.AiPosition;
import com.scrab5.core.game.BagOfTiles;
import com.scrab5.core.game.GameBoard;
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
  static String[] lettersFromDatabase;
  static int[] pointsPerLetterFromDatabase;


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
   * the possible words. 1. First it saves the letters from the bag of tiles in the array
   * possibleLetters. Line 205 and following. 2. The DictionaryScanner returns words from the
   * current dictionary that contain the at least the fixLetter or one of the letters from
   * possibleLetters and adds them to the finalWords. Line 219 and following. 3. In a new step, all
   * the words that do not only consist of the letters in possibleLetters will be removed from
   * finalWords. Line 227 and following. 4. Next there is another check to see if the words in
   * finalWords fulfill the requirements. For example, if not too long. Line 245 and following. 5.
   * The words that are still in the finalWords will now be checked if the occurrence of letters in
   * it conform to the occurrence of them in the bag of tiles. If not, they will be removed from
   * finalWords. Line 268 and following. 6. In the last step a ArrayList containing all words the AI
   * could possibly lay as Tiles with coordinates and points is created and passed. Line 277 and
   * following.
   *
   * @param fixLetter  the Letter that is already placed on the GameBoard where the Ai wants to lay
   *                   a word next to
   * @param before     the amount of tiles that are free before this letter
   * @param after      the amount of tiles that are free after this letter
   * @param x          the x-coordinate of the tile with letter fixLetter on the board
   * @param y          the y-coordinate of the tile with letter fixLetter on the board
   * @param horizontal is true, when the word needs to get laid horizontal and false if vertical.
   *                   This parameter is needed later on
   * @author lengist
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

    ArrayList<String> deletionRound3 = new ArrayList<String>();
    HashMap<String, Integer> currentDistribution = bag.getCurrentBagDistribution();
    for (String s : finalWords) {
      if (!checkBagDistributionLegal(currentDistribution, s)) {
        deletionRound3.add(s);
      }
    }
    finalWords.removeAll(deletionRound3);

    ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
    ArrayList<Tile> innerList = new ArrayList<Tile>();
    for (String s : finalWords) {
      for (int i = 0; i < s.length(); i++) {
        int value = getPointForLetter(String.valueOf(s.charAt(i)));
        ArrayList<Integer> coordinates = getCoordinates(s, fixLetter, String.valueOf(s.charAt(i)),
            x, y, horizontal);
        int row = coordinates.get(1);
        int column = coordinates.get(0);
        Tile t = new Tile(String.valueOf(s.charAt(i)), value, row, column);
        innerList.add(t);
      }
      tiles.add(innerList);
    }

    /*TODO: tiles an AiPosition.countScore weitergeben*/

    /* TODO: Possible add: condition to end this method after for example 15 words. */
  }

  /**
   * Method to calculate the coordinates for the tiles of a new word. First it estimates the place
   * of the fixLetter in word and then the place of the newLetter in word. With this information,
   * the Coordinates for the newLetter get calculated.
   *
   * @param word       the word that contains the fixLetter and newLetter to calculate the exact
   *                   positions of the given letters
   * @param fixLetter  the letter that is already n the gameboard
   * @param newLetter  a letter from the word different to fixLetter. This is the letter the
   *                   coordinates need to be calculated for.
   * @param xFixLetter the x-Coordinate of the fixLetter
   * @param yFixLetter the y-Coordinate of the fixLetter
   * @param horizontal a boolean variable for the alignment of the word on the board. If it is true,
   *                   the word will be laid horizontal. If not, vertical.
   * @return coordinates, a ArrayList including the x- and y-Coordinate of the newLetter.
   * @author lengist
   */
  public static ArrayList<Integer> getCoordinates(String word, String fixLetter, String newLetter,
      int xFixLetter, int yFixLetter, boolean horizontal) {
    int placeFixLetter = 0;
    int xNew = 0;
    int yNew = 0;

    for (int i = 0; i < word.length(); i++) {
      if (word.charAt(i) == fixLetter.charAt(0)) {
        placeFixLetter = i;
      }
    }

    for (int i = 0; i < word.length(); i++) {
      if (word.charAt(i) == newLetter.charAt(0)) {
        if (horizontal) {
          yNew = yFixLetter;
          if (i < placeFixLetter) {
            xNew = xFixLetter - (placeFixLetter - i);
          } else {
            xNew = xFixLetter + (i - placeFixLetter);
          }
        } else {
          xNew = xFixLetter;
          if (i < placeFixLetter) {
            yNew = yFixLetter - (placeFixLetter - i);
          } else {
            yNew = yFixLetter + (i - placeFixLetter);
          }
        }
      }
    }

    ArrayList<Integer> coordinates = new ArrayList<Integer>();
    coordinates.add(xNew);
    coordinates.add(yNew);
    return coordinates;
  }

  /**
   * Method to return the points for a letter saved in the database.
   *
   * @param letter the String of the letter for that the points need to be known
   * @return points the points saved in database for the letter.
   * @author lengist
   */
  public static int getPointForLetter(String letter) {
    int points = 0;
    for (int i = 0; i < lettersFromDatabase.length; i++) {
      if (lettersFromDatabase[i].equals(letter)) {
        points = pointsPerLetterFromDatabase[i];
      }
    }
    return points;
  }

  /**
   * Method to get all the points for the letters in the database to find out the points for a
   * possible word. The values will be saved in a local variable.
   *
   * @author lengist
   */
  public static void setLetterPoints() {
    lettersFromDatabase = UseDatabase.getAllLetters();
    pointsPerLetterFromDatabase = UseDatabase.getAllPointsPerLetter();
  }

  /**
   * Method to test the function for the AiPlayer to generate a fitting word. Just created to test
   * this function in the JUnit test class, not for usage in the game.
   *
   * @param fixLetter  the Letter that is already placed on the GameBoard where the Ai wants to lay
   *                   a word next to
   * @param before     the amount of tiles that are free before this letter
   * @param after      the amount of tiles that are free after this letter
   * @param x          the x-coordinate of the tile with letter fixLetter on the board
   * @param y          the y-coordinate of the tile with letter fixLetter on the board
   * @param horizontal is true, when the word needs to get laid horizontal and false if vertical.
   *                   This parameter is needed later on
   * @author lengist
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

    ArrayList<String> deletionRound3 = new ArrayList<String>();
    /*TODO: fill the hashmap for the CurrentDistribution*/

    HashMap<String, Integer> currentDistribution = new HashMap<String, Integer>();
    for (int i = 0; i < possibleLetters.length; i++) {
      currentDistribution.merge(possibleLetters[i], 1, Integer::sum);
    }

    for (String s : finalWords) {
      if (!checkBagDistributionLegal(currentDistribution, s)) {
        deletionRound3.add(s);
      }
    }
    finalWords.removeAll(deletionRound3);

    String[] ready = new String[finalWords.size()];
    for (int i = 0; i < ready.length; i++) {
      ready[i] = finalWords.get(i);
    }

    ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
    ArrayList<Tile> innerList = new ArrayList<Tile>();
    for (String s : finalWords) {
      for (int i = 0; i < s.length(); i++) {
        int value = getPointForLetter(String.valueOf(s.charAt(i)));
        ArrayList<Integer> coordinates = getCoordinates(s, fixLetter, String.valueOf(s.charAt(i)),
            x, y, horizontal);
        int row = coordinates.get(1);
        int column = coordinates.get(0);
        Tile t = new Tile(String.valueOf(s.charAt(i)), value, row, column);
        innerList.add(t);
        /*System.out.println("s: " + s);
        System.out.println("s x: " + x);
        System.out.println("s y: " + y);
        System.out.println("char: " + s.charAt(i));
        System.out.println("xNew: " + column);
        System.out.println("yNew: " + row);*/
      }
      tiles.add(innerList);
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
    setLetterPoints();
    String[] test = wordGeneratorTest("A", 3, 4, 6, 6, true);
    for (String s : test) {
      System.out.println(s);
    }
  }
}
