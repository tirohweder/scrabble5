package com.scrab5.core.player;

import static com.scrab5.util.constants.Constants.lettersFromDatabase;
import static com.scrab5.util.constants.Constants.pointsPerLetterFromDatabase;

import com.scrab5.core.game.BagOfTiles;
import com.scrab5.core.game.GameBoard;
import com.scrab5.core.game.Tile;
import com.scrab5.ui.Data;
import com.scrab5.util.constants.Constants;
import com.scrab5.util.database.Database;
import com.scrab5.util.database.UseDatabase;
import com.scrab5.util.parser.DictionaryScanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Ai Class. Here everything concerning the ai is happening.
 *
 * @author trohwede
 * @author lengist
 * @author hraza
 */
public class AiPlayer extends Player {

  static int currentFixX;
  static int currentFixY;
  static String currentFixLetter;
  int counterUp;
  int counterDown;
  int counterRight;
  int counterLeft;
  int aiThreshold;

  /**
   * Constructor for AiPlayer. Needs difficulty set.
   *
   * @param name
   * @author trohwede
   */
  public AiPlayer(String name, int difficulty) {
    super(name);

    if (difficulty == 0) {
      this.aiThreshold = Constants.EASY_AI_THRESHOLD;
    } else {
      this.aiThreshold = Constants.HARD_AI_THRESHOLD;
    }
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
   * @author lengist
   * @param fixLetter the Letter that is already placed on the GameBoard where the Ai wants to lay a
   *     word next to
   * @param before the amount of tiles that are free before this letter
   * @param after the amount of tiles that are free after this letter
   * @param column the column of the tile with letter fixLetter on the board
   * @param row the row of the tile with letter fixLetter on the board
   * @param horizontal is true, when the word needs to get laid horizontal and false if vertical.
   *     This parameter is needed later on
   */
  public static ArrayList<ArrayList<Tile>> wordGenerator(
      String fixLetter, int before, int after, int column, int row, boolean horizontal) {
    System.out.println();
    System.out.println("fixLetter: " + fixLetter);
    System.out.println("before: " + before);
    System.out.println("after: " + after);
    System.out.println("column: " + column);
    System.out.println("row: " + row);
    System.out.println("horizontal: " + horizontal);

    System.out.println();
    ArrayList<Tile> listOfTiles = new ArrayList<Tile>();
    ArrayList<String> possibleLetters1 = new ArrayList<String>();

    ArrayList<String> deletionRound1 = new ArrayList<String>();
    ArrayList<String> deletionRound2 = new ArrayList<String>();

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

    int maximumLength = before + 1 + after;

    ArrayList<String> finalWords = new ArrayList<String>();
    finalWords = DictionaryScanner.getWordsIncluding(fixLetter, maximumLength);
    System.out.println("1. Final Words length: " + finalWords.size());

    /* Deletion round 1: deleting all words that include a letter that is not in the bag. */
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

    System.out.println("After Deltion round 1: " + finalWords.size());

    /*
     * Deletion round 2: deleting all words that do not fit to the requirements regarding the space
     * before and after the fixLetter.
     */
    for (String s : finalWords) {
      for (int i = 0; i < s.length(); i++) {

        if (s.charAt(i) == fixLetter.charAt(0)) {
          before2 = i;
          after2 = s.length() - (i + 1);

          if (before2 > before) {
            deletionRound2.add(s);
          } else if (after2 > after) {
            deletionRound2.add(s);
          }
        }
      }
    }
    finalWords.removeAll(deletionRound2);

    System.out.println("After Deltion round 2: " + finalWords.size());

    /*
     * Deletion round 3: deleting all words that contain an amount of a specific letter not
     * compatible with the occurrence in the bag.
     */
    ArrayList<String> deletionRound3 = new ArrayList<String>();
    HashMap<String, Integer> currentDistribution = bag.getCurrentBagDistribution();
    for (String s : finalWords) {
      if (!checkBagDistributionLegal(currentDistribution, s)) {
        deletionRound3.add(s);
      }
    }
    finalWords.removeAll(deletionRound3);
    System.out.println("Final Words length 3. : " + finalWords.size());

    ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
    for (String s : finalWords) {
      tiles.add(wordToTiles(s, fixLetter, column, row, horizontal));
    }
    return tiles;
  }

  public static void main(String[] args) {
    String s = "laura";
    System.out.println(s.length());
  }

  /**
   * Creates the tiles for a word.
   *
   * @author lengist
   * @param word the word that needs to be converted into tiles
   * @param fixLetter the Letter that is already placed on the GameBoard where the Ai wants to lay a
   *     word next to
   * @param column the column of the tile with letter fixLetter on the board
   * @param row the row of the tile with letter fixLetter on the board
   * @param horizontal is true, when the word needs to get laid horizontal and false if vertical
   * @return tiles a ArrayList containing all tiles for the word word
   */
  public static ArrayList<Tile> wordToTiles(
      String word, String fixLetter, int column, int row, boolean horizontal) {
    ArrayList<Tile> tiles = new ArrayList<Tile>();
    tiles = getCoordinatesRep(word, fixLetter, column, row, horizontal);
    return tiles;
  }

  /**
   * Method to calculate the coordinates for the tiles of a new word. First it estimates the place
   * of the fixLetter in word. With this information, the Coordinates for the newLetter get
   * calculated and saved as new Tile.
   *
   * @author lengist
   * @param word the word that needs to be created with tiles
   * @param fixLetter the letter that is already on the gameboard
   * @param columnFixLetter the column of the fixLetter
   * @param rowFixLetter the row of the fixLetter
   * @param horizontal a boolean variable for the alignment of the word on the board. If it is true,
   *     the word will be laid horizontal. If not, vertical.
   * @return tiles, a ArrayList including all tiles for the word word
   */
  public static ArrayList<Tile> getCoordinatesRep(
      String word, String fixLetter, int columnFixLetter, int rowFixLetter, boolean horizontal) {
    int fixPosition = 0;
    ArrayList<Integer> list = new ArrayList<Integer>();
    ArrayList<Tile> tiles = new ArrayList<Tile>();
    int row = 0;
    int column = 0;
    int value = 0;
    for (int i = 0; i < word.length(); i++) {
      if (word.charAt(i) == fixLetter.charAt(0)) {
        fixPosition = i;
      }
    }
    if (horizontal) {
      for (int i = 0; i < fixPosition; i++) {
        value = getPointForLetter(String.valueOf(word.charAt(i)));
        // System.out.println(word.charAt(i));
        row = rowFixLetter;
        column = columnFixLetter - (fixPosition - i);
        Tile t = new Tile(String.valueOf(word.charAt(i)), value, row, column);
        tiles.add(t);
      }
      Tile t1 = new Tile(fixLetter, getPointForLetter(fixLetter), rowFixLetter, columnFixLetter);
      tiles.add(t1);
      for (int i = fixPosition + 1; i < word.length(); i++) {
        value = getPointForLetter(String.valueOf(word.charAt(i)));
        row = rowFixLetter;
        column = columnFixLetter + (i - fixPosition);
        Tile t = new Tile(String.valueOf(word.charAt(i)), value, row, column);
        tiles.add(t);
      }
    } else {
      for (int i = 0; i < fixPosition; i++) {
        value = getPointForLetter(String.valueOf(word.charAt(i)));
        row = rowFixLetter - (fixPosition - i);
        column = columnFixLetter;
        Tile t = new Tile(String.valueOf(word.charAt(i)), value, row, column);
        tiles.add(t);
      }
      Tile t1 = new Tile(fixLetter, getPointForLetter(fixLetter), rowFixLetter, columnFixLetter);
      tiles.add(t1);
      for (int i = fixPosition + 1; i < word.length(); i++) {
        value = getPointForLetter(String.valueOf(word.charAt(i)));
        row = rowFixLetter + (i - fixPosition);
        column = columnFixLetter;
        Tile t = new Tile(String.valueOf(word.charAt(i)), value, row, column);
        tiles.add(t);
      }
    }
    return tiles;
  }

  /**
   * Returns if a word contains a letter more than one time.
   *
   * @author lengist
   * @param word the word which will be scanned
   * @return a boolean value whether it contains a letter for more than one time (true) oder not
   *     (false)
   */
  public static boolean isLetterExistingRepeatedly(String word) {
    for (int i = 1; i < word.length(); i++) {
      if (word.charAt(i) == word.charAt(i - 1)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Method to return the points for a letter saved in the database.
   *
   * @author lengist
   * @param letter the String of the letter for that the points need to be known
   * @return points the points saved in database for the letter.
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
   * This Method is gettig a String word and the current Bag-Occurence hashmap handed over and then
   * checks if there are enough letters in the Bag to create the word. For every Letter in the word
   * it substracts 1 from the value in the Hashmap and then checks if the value of this Letter is
   * still positive. If it is negative, the boolean will be changed to false, the loop breaks and
   * the hashmap will be resetted and false is given back
   *
   * @param currentDistribution is the current letter-occurence of the tiles in the bag
   * @param word is the word that is going to be checked
   * @author hraza
   */
  public static Boolean checkBagDistributionLegal(
      HashMap<String, Integer> currentDistribution, String word) {
    boolean b = true;
    int j = 0;
    for (int i = 0; i < word.length(); i++) {
      currentDistribution.put(
          Character.toString(word.charAt(i)),
          currentDistribution.get(Character.toString(word.charAt(i))) - 1);
      if (currentDistribution.get(Character.toString(word.charAt(i))) < 0) {
        b = false;
        j = i;
        break;
      } else {
        j = word.length() - 1;
      }
    }

    for (int i = 0; i <= j; i++) {
      currentDistribution.put(
          Character.toString(word.charAt(i)),
          currentDistribution.get(Character.toString(word.charAt(i))) + 1);
    }
    return b;
  }

  /**
   * Because we create the possible words as tile lists, because when we want
   *
   * @author trohwede
   * @param gameBoard takes the currentGameBoard
   * @param possibleWords revices the possible words as a ArrayList<Tile>
   * @return Points per Word
   */
  public static ArrayList<Integer> countScore(
      GameBoard gameBoard, ArrayList<ArrayList<Tile>> possibleWords) {
    ArrayList<Integer> scoreList = new ArrayList<>();

    for (ArrayList<Tile> word : possibleWords) {
      int score = 0;
      int scoreToBe = 0;
      boolean tw = false;
      boolean dw = false;
      for (Tile tile : word) {
        switch (gameBoard.getSpecialsAt(tile.getRow(), tile.getColumn())) {
          case "DL":
            scoreToBe += tile.getValue() * 2;
            break;
          case "TL":
            scoreToBe += tile.getValue() * 3;
            break;
          case "DW":
            dw = true;
            scoreToBe += tile.getValue();
            break;
          case "TW":
            tw = true;
            scoreToBe += tile.getValue();
            break;
          default:
            scoreToBe += tile.getValue();
        }
        Data.getGameSession().getGameBoard().setSpecialAt(tile.getRow(), tile.getColumn(), "  ");
      }

      if (dw) {
        score = scoreToBe * 2;
      } else if (tw) {
        score = scoreToBe * 3;
      } else {
        score += scoreToBe;
      }
      scoreList.add(score);
    }
    return scoreList;
  }

  /**
   * The idea of the fuction is to find out how much space it has to play with the given coordinates
   * so that it creates only one word and dosnt touch any other tiles.
   *
   * @author trohwede
   * @param row coordinates row
   * @param column coordinates column
   * @param g gameBoard used
   */
  public void getSpotsFree2(int row, int column, GameBoard g) {
    Tile[][] gameBoard = g.getGameBoard();

    counterUp = 0;
    counterDown = 0;
    counterLeft = 0;
    counterRight = 0;

    int count = 0;
    // up

    while (row - 2 - count >= 0) {
      if (column == 0
          && gameBoard[row - 1 - count][column + 1] == null
          && gameBoard[row - 1 - count][column] == null
          && gameBoard[row - 2 - count][column] == null) {
        counterUp++;
        count++;
      } else if (column == 0) {
        break;
      }
      if (column == 14
          && gameBoard[row - 1 - count][column - 1] == null
          && gameBoard[row - 1 - count][column] == null
          && gameBoard[row - 2 - count][column] == null) {
        counterUp++;
        count++;
      } else if (column == 14) {
        break;
      }
      if (column != 0
          && column != 14
          && gameBoard[row - 1 - count][column - 1] == null
          && gameBoard[row - 1 - count][column + 1] == null
          && gameBoard[row - 1 - count][column] == null
          && gameBoard[row - 2 - count][column] == null) {
        counterUp++;
        count++;
      } else if (column != 0 && column != 14) {
        break;
      }
    }
    if (row - 2 - count < 0) {
      if (gameBoard[0][column] == null) {
        counterUp++;
      }
    }
    if (row != 14 && gameBoard[row + 1][column] != null) {
      counterUp = 0;
    }

    count = 0;
    // down
    while (row + 2 + count < 15) {
      if (column == 0
          && gameBoard[row + 1 + count][column + 1] == null
          && gameBoard[row + 1 + count][column] == null
          && gameBoard[row + 2 + count][column] == null) {
        counterDown++;
        count++;
      } else if (column == 0) {
        break;
      }
      if (column == 14
          && gameBoard[row + 1 + count][column - 1] == null
          && gameBoard[row + 1 + count][column] == null
          && gameBoard[row + 2 + count][column] == null) {
        counterDown++;
        count++;
      } else if (column == 14) {
        break;
      }
      if (column != 0
          && column != 14
          && gameBoard[row + 1 + count][column - 1] == null
          && gameBoard[row + 1 + count][column + 1] == null
          && gameBoard[row + 1 + count][column] == null
          && gameBoard[row + 2 + count][column] == null) {
        counterDown++;
        count++;
      } else if (column != 0 && column != 14) {
        break;
      }
    }
    if (row + 2 + count == 15) {
      if (gameBoard[14][column] == null) {
        counterDown++;
      }
    }
    if (row != 0 && gameBoard[row - 1][column] != null) {
      counterDown = 0;
    }

    count = 0;

    // right
    while (column + 2 + count < 15) {
      if (row == 0
          && gameBoard[row + 1][column + 1 + count] == null
          && gameBoard[row][column + 1 + count] == null
          && gameBoard[row][column + 2 + count] == null) {
        counterRight++;
        count++;
      } else if (row == 0) {
        break;
      }
      if (row == 14
          && gameBoard[row - 1][column + 1 + count] == null
          && gameBoard[row][column + 1 + count] == null
          && gameBoard[row][column + 2 + count] == null) {
        counterRight++;
        count++;
      } else if (row == 14) {
        break;
      }
      if (row != 0
          && row != 14
          && gameBoard[row - 1][column + 1 + count] == null
          && gameBoard[row + 1][column + 1 + count] == null
          && gameBoard[row][column + 1 + count] == null
          && gameBoard[row][column + 2 + count] == null) {
        counterRight++;
        count++;
      } else if (row != 0 && row != 14) {
        break;
      }
    }
    if (column + 2 + count == 15) {
      if (gameBoard[row][14] == null) {
        counterRight++;
      }
    }
    if (column != 0 && gameBoard[row][column - 1] != null) {
      counterRight = 0;
    }

    count = 0;

    // left
    while (column - 2 - count >= 0) {
      if (row == 0
          && gameBoard[row + 1][column - 1 - count] == null
          && gameBoard[row][column - 1 - count] == null
          && gameBoard[row][column - 2 - count] == null) {
        counterLeft++;
        count++;
      } else if (row == 0) {
        break;
      }
      if (row == 14
          && gameBoard[row - 1][column - 1 - count] == null
          && gameBoard[row][column - 1 - count] == null
          && gameBoard[row][column - 2 - count] == null) {
        counterLeft++;
        count++;
      } else if (row == 14) {
        break;
      }

      if (row != 0
          && row != 14
          && gameBoard[row - 1][column - 1 - count] == null
          && gameBoard[row + 1][column - 1 - count] == null
          && gameBoard[row][column - 1 - count] == null
          && gameBoard[row][column - 2 - count] == null) {
        counterLeft++;
        count++;
      } else if (row != 0 && row != 14) {
        break;
      }
    }

    if (column - 2 - count < 0) {
      if (gameBoard[row][0] == null) {
        counterLeft++;
      }
    }

    if (column != 14 && gameBoard[row][column + 1] != null) {
      counterLeft = 0;
    }
  }

  /**
   * This Method is looking for free valid Spots around the given position and initializes the
   * counters.
   *
   * @param x is the column-position from where it starts countin on the current gameBoard
   * @param y is the row-position from where it starts countin on the current gameBoard
   * @author hraza
   */
  public void getSpotsFree(int x, int y, GameBoard g) {
    int counterRight = 0;
    int counterLeft = 0;
    int counterUp = 0;
    int counterDown = 0;

    System.out.println("Finding free Spots at: " + y + " : " + x);

    // Checking the right Side of the Position on the Board
    while (y < 14
        && y > 0
        && x + 2 + counterRight <= 14
        && g.isSpotFreeOld(y, x + 1 + counterRight)
        && g.isSpotFreeOld(y, x + 2 + counterRight)
        && g.isSpotFreeOld(y - 1, x + 1 + counterRight)
        && g.isSpotFreeOld(y + 1, x + 1 + counterRight)) {
      counterRight++;
    }
    while (y < 14
        && y > 0
        && x + 2 + counterRight == 15
        && g.isSpotFreeOld(y, x + 1 + counterRight)
        && g.isSpotFreeOld(y - 1, x + 1 + counterRight)
        && g.isSpotFreeOld(y + 1, x + 1 + counterRight)) {
      counterRight++;
    }
    while (y == 0
        && x + 2 + counterRight <= 14
        && g.isSpotFreeOld(y, x + 1 + counterRight)
        && g.isSpotFreeOld(y, x + 2 + counterRight)
        && g.isSpotFreeOld(y + 1, x + 1 + counterRight)) {
      counterRight++;
    }

    while (y == 0
        && x + 2 + counterRight == 15
        && g.isSpotFreeOld(y, x + 1 + counterRight)
        && g.isSpotFreeOld(y + 1, x + 1 + counterRight)) {
      counterRight++;
    }
    while (y == 14
        && x + 2 + counterRight <= 14
        && g.isSpotFreeOld(y, x + 1 + counterRight)
        && g.isSpotFreeOld(y, x + 2 + counterRight)
        && g.isSpotFreeOld(y - 1, x + 1 + counterRight)) {
      counterRight++;
    }
    while (y == 14
        && x + 2 + counterRight == 15
        && g.isSpotFreeOld(y, x + 1 + counterRight)
        && g.isSpotFreeOld(y - 1, x + 1 + counterRight)) {
      counterRight++;
    }
    if (x + 2 <= 14 && g.isSpotFree(y, x + 1) && !g.isSpotFree(y, x + 2)) {
      counterRight = -1;
    }

    if (x + 2 == 15
        && y > 0
        && y < 14
        && g.isSpotFree(y, x + 1)
        && (!g.isSpotFree(y - 1, x + 1) || !g.isSpotFree(y + 1, x + 1))) {
      counterRight = -1;
    }
    if (x + 2 == 15 && y == 0 && g.isSpotFree(y, x + 1) && !g.isSpotFree(y + 1, x + 1)) {
      counterRight = -1;
    }
    if (x + 2 == 15 && y == 14 && g.isSpotFree(y, x + 1) && !g.isSpotFree(y - 1, x + 1)) {
      counterRight = -1;
    }
    if (x - 1 >= 0 && !g.isSpotFree(y, x - 1)) {
      counterRight = -1;
    }

    // Checking the left Side of the Position on the Board
    while (y < 14
        && y > 0
        && x - 2 - counterLeft >= 0
        && g.isSpotFreeOld(y, x - 1 - counterLeft)
        && g.isSpotFreeOld(y, x - 2 - counterLeft)
        && g.isSpotFreeOld(y - 1, x - 1 - counterLeft)
        && g.isSpotFreeOld(y + 1, x - 1 - counterLeft)) {
      counterLeft++;
      // System.out.println("y="+ y +"; counterLeft ="+counterLeft);
    }
    while (y < 14
        && y > 0
        && x - 2 - counterLeft == -1
        && g.isSpotFreeOld(y, x - 1 - counterLeft)
        && g.isSpotFreeOld(y - 1, x - 1 - counterLeft)
        && g.isSpotFreeOld(y + 1, x - 1 - counterLeft)) {
      counterLeft++;
      // System.out.println("y=" + y + "; counterLeft =" + counterLeft);
    }
    while (y == 0
        && x - 2 - counterLeft >= 0
        && g.isSpotFreeOld(y, x - 1 - counterLeft)
        && g.isSpotFreeOld(y, x - 2 - counterLeft)
        && g.isSpotFreeOld(y + 1, x - 1 - counterLeft)) {
      counterLeft++;
    }
    while (y == 0
        && x - 2 - counterLeft == -1
        && g.isSpotFreeOld(y, x - 1 - counterLeft)
        && g.isSpotFreeOld(y + 1, x - 1 - counterLeft)) {
      counterLeft++;
    }
    while (y == 14
        && x - 2 - counterLeft >= 0
        && g.isSpotFreeOld(y, x - 1 - counterLeft)
        && g.isSpotFreeOld(y, x - 2 - counterLeft)
        && g.isSpotFreeOld(y - 1, x - 1 - counterLeft)) {
      counterLeft++;
    }
    while (y == 14
        && x - 2 - counterLeft == -1
        && g.isSpotFreeOld(y, x - 1 - counterLeft)
        && g.isSpotFreeOld(y - 1, x - 1 - counterLeft)) {
      counterLeft++;
    }
    if (x - 2 >= 0 && g.isSpotFree(y, x - 1) && !g.isSpotFree(y, x - 2)) {
      counterLeft = -1;
    }

    if (x - 2 == -1
        && y > 0
        && y < 14
        && g.isSpotFree(y, x - 1)
        && (!g.isSpotFree(y - 1, x - 1) || !g.isSpotFree(y + 1, x - 1))) {
      counterLeft = -1;
    }
    if (x - 2 == -1 && y == 0 && g.isSpotFree(y, x - 1) && !g.isSpotFree(y + 1, x - 1)) {
      counterLeft = -1;
    }
    if (x - 2 == -1 && y == 14 && g.isSpotFree(y, x - 1) && !g.isSpotFree(y - 1, x - 1)) {
      counterLeft = -1;
    }
    if (x + 1 <= 14 && !g.isSpotFree(y, x + 1)) {
      counterLeft = -1;
    }

    // Checking for free Spots under the Position x,y
    while (y + 2 + counterDown <= 14
        && x > 0
        && x < 14
        && g.isSpotFreeOld(y + 1 + counterDown, x)
        && g.isSpotFreeOld(y + 2 + counterDown, x)
        && g.isSpotFreeOld(y + 1 + counterDown, x + 1)
        && g.isSpotFreeOld(y + 1 + counterDown, x - 1)) {
      counterDown++;
    }
    while (y + 2 + counterDown == 15
        && x > 0
        && x < 14
        && g.isSpotFreeOld(y + 1 + counterDown, x)
        && g.isSpotFreeOld(y + 1 + counterDown, x + 1)
        && g.isSpotFreeOld(y + 1 + counterDown, x - 1)) {
      counterDown++;
    }

    while (y + 2 + counterDown <= 14
        && x == 0
        && g.isSpotFreeOld(y + 1 + counterDown, x)
        && g.isSpotFreeOld(y + 2 + counterDown, x)
        && g.isSpotFreeOld(y + 1 + counterDown, x + 1)) {
      counterDown++;
    }
    while (y + 2 + counterDown == 15
        && x == 0
        && g.isSpotFreeOld(y + 1 + counterDown, x)
        && g.isSpotFreeOld(y + 1 + counterDown, x + 1)) {
      counterDown++;
    }
    while (y + 2 + counterDown <= 14
        && x == 14
        && g.isSpotFreeOld(y + 1 + counterDown, x)
        && g.isSpotFreeOld(y + 2 + counterDown, x)
        && g.isSpotFreeOld(y + 1 + counterDown, x - 1)) {
      counterDown++;
    }
    while (y + 2 + counterDown == 15
        && x == 14
        && g.isSpotFreeOld(y + 1 + counterDown, x)
        && g.isSpotFreeOld(y + 1 + counterDown, x - 1)) {
      counterDown++;
    }
    if (y + 2 <= 14 && g.isSpotFree(y + 1, x) && !g.isSpotFree(y + 2, x)) {
      counterDown = -1;
    }

    if (y + 2 == 15
        && x > 0
        && x < 14
        && g.isSpotFree(y + 1, x)
        && (!g.isSpotFree(y + 1, x + 1) || !g.isSpotFree(y + 1, x - 1))) {
      counterDown = -1;
    }
    if (y + 2 == 15 && x == 0 && g.isSpotFree(y + 1, x) && !g.isSpotFree(y + 1, x + 1)) {
      counterDown = -1;
    }
    if (y + 2 == 15 && x == 14 && g.isSpotFree(y + 1, x) && !g.isSpotFree(y + 1, x - 1)) {
      counterDown = -1;
    }

    if (y - 1 >= 0 && !g.isSpotFree(y - 1, x)) {
      counterDown = -1;
    }
    // Checking for free Spots above the Position x,y
    while (y - 2 - counterUp >= 0
        && x > 0
        && x < 14
        && g.isSpotFreeOld(y - 1 - counterUp, x)
        && g.isSpotFreeOld(y - 2 - counterUp, x)
        && g.isSpotFreeOld(y - 1 - counterUp, x + 1)
        && g.isSpotFreeOld(y - 1 - counterUp, x - 1)) {
      counterUp++;
      // System.out.println("y=" + y + "; counterUp =" + counterUp);
    }
    while (x > 0
        && x < 14
        && y - 2 - counterUp == -1
        && g.isSpotFreeOld(y - 1 - counterUp, x)
        && g.isSpotFreeOld(y - 1 - counterUp, x + 1)
        && g.isSpotFreeOld(y - 1 - counterUp, x - 1)) {
      counterUp++;
      // System.out.println("y=" + y + "; counterUp =" + counterUp);
    }
    while (y - 2 - counterUp >= 0
        && x == 0
        && g.isSpotFreeOld(y - 1 - counterUp, x)
        && g.isSpotFreeOld(y - 2 - counterUp, x)
        && g.isSpotFreeOld(y - 1 - counterUp, x + 1)) {
      counterUp++;
    }
    while (y - 2 - counterUp == -1
        && x == 0
        && g.isSpotFreeOld(y - 1 - counterUp, x)
        && g.isSpotFreeOld(y - 1 - counterUp, x + 1)) {
      counterUp++;
    }
    while (y - 2 - counterUp >= 0
        && x == 14
        && g.isSpotFreeOld(y - 1 - counterUp, x)
        && g.isSpotFreeOld(y - 2 - counterUp, x)
        && g.isSpotFreeOld(y - 1 - counterUp, x - 1)) {
      counterUp++;
    }
    while (y - 2 - counterUp == -1
        && x == 14
        && g.isSpotFreeOld(y - 1 - counterUp, x)
        && g.isSpotFreeOld(y - 1 - counterUp, x - 1)) {
      counterUp++;
    }

    if (y - 2 >= 0 && g.isSpotFree(y - 1, x) && !g.isSpotFree(y - 2, x)) {
      counterUp = -1;
    }

    if (y - 2 == -1
        && x > 0
        && x < 14
        && g.isSpotFree(y - 1, x)
        && (!g.isSpotFree(y - 1, x - 1) || !g.isSpotFree(y - 1, x + 1))) {
      counterUp = -1;
    }
    if (y - 2 == -1 && x == 0 && g.isSpotFree(y - 1, x) && !g.isSpotFree(y - 1, x + 1)) {
      counterUp = -1;
    }
    if (y - 2 == -1 && x == 14 && g.isSpotFree(y - 1, x) && !g.isSpotFree(y - 1, x - 1)) {
      counterUp = -1;
    }

    if (y + 1 <= 14 && !g.isSpotFree(y + 1, x)) {
      counterUp = -1;
    }

    /*
     * this.counterDown = counterDown; this.counterUp = counterUp; this.counterRight = counterRight;
     * this.counterLeft = counterLeft;
     *
     * if (counterLeft <= 0 && counterRight >= 0) { this.counterLeft = 0; this.counterRight =
     * counterRight; } if (counterLeft >= 0 && counterRight <= 0) { this.counterRight = 0;
     * this.counterLeft = counterLeft; }
     *
     * if (counterLeft <= 0 && counterRight <= 0) { this.counterRight = 0; this.counterLeft = 0; }
     *
     * if (counterUp <= 0 && counterDown >= 0) { this.counterUp = 0; this.counterDown = counterDown;
     * } if (counterUp >= 0 && counterDown <= 0) { this.counterDown = 0; this.counterUp = counterUp;
     * }
     *
     * if (counterUp <= 0 && counterDown <= 0) { this.counterDown = 0; this.counterUp = 0; }
     *
     * System.out.println("left: " + this.counterLeft); System.out.println("right: " +
     * this.counterRight); System.out.println("up: " + this.counterUp); System.out.println("down: "
     * + this.counterDown);
     */
  }

  /**
   * This methods makes the ai play. First we find a tile that is already in play. From this tile we
   * check out which coordinates in which way are free. We want to create words, that only create 1
   * new word on the game board. So it returns the integer value for each row we have space. With
   * word generator, we can give the tile a fixed letter and it will try to create as many words as
   * possible with the space and letters given. Then we calculate all the points for each word. And
   * then select the word that fit the difficulty threshold. If the spot found doesnt give a good
   * enough word, we check out the next tile. Then chosen word is then placed on the board and the
   * required tiles for that word are removed from the bag of tiles.
   *
   * @author trohwede
   */
  public void aiPlay() throws IOException {
    lettersFromDatabase = UseDatabase.getAllLetters();
    pointsPerLetterFromDatabase = UseDatabase.getAllPointsPerLetter();
    Database.disconnect();

    boolean foundMatchingThreshold = false;

    ArrayList<Tile> choosenWord = new ArrayList<>();
    System.out.println("Starting to find word");
    // go through game while threshhold is not reached
    int pointsForRound = 0;

    if (Data.getGameSession().getGameBoard().isFirstTile()) {
      getSpotsFree2(7, 7, Data.getGameSession().getGameBoard());
    }

    findacceptable:
    for (int row = 0; row < 15; row++) {
      for (int column = 0; column < 15; column++) {
        System.out.println("Checking " + row + " : " + column);

        // If tile is already used on the gameBoard or if FirstTile
        if (Data.getGameSession().getGameBoard().getPlayedTile(row, column) != null
            || Data.getGameSession().getGameBoard().isFirstTile()) {
          getSpotsFree2(row, column, Data.getGameSession().getGameBoard());

          ArrayList<ArrayList<Tile>> wordList = new ArrayList<>();

          System.out.println("row: " + row);
          System.out.println("column: " + column);
          System.out.println("Trying someting");

          if (Data.getGameSession().getGameBoard().isFirstTile()) {
            currentFixLetter = "B";
            wordList = wordGenerator("B", 5, 4, 7, 7, true);

          } else if (counterDown + counterUp > counterLeft + counterRight
              && counterDown + counterUp + counterRight + counterLeft > 0) {
            currentFixLetter =
                Data.getGameSession().getGameBoard().getPlayedTile(row, column).getLetter();
            currentFixX = column;
            currentFixY = row;
            wordList =
                wordGenerator(
                    Data.getGameSession().getGameBoard().getPlayedTile(row, column).getLetter(),
                    counterUp,
                    counterDown,
                    column,
                    row,
                    false);
            if (wordList.isEmpty()) {
              break;
            }
          } else if (counterDown + counterUp + counterRight + counterLeft > 0) {
            currentFixLetter =
                Data.getGameSession().getGameBoard().getPlayedTile(row, column).getLetter();
            currentFixX = column;
            currentFixY = row;
            wordList =
                wordGenerator(
                    Data.getGameSession().getGameBoard().getPlayedTile(row, column).getLetter(),
                    counterLeft,
                    counterRight,
                    column,
                    row,
                    true);

            if (wordList.isEmpty()) {
              break;
            }
          }
          if (wordList.size() > 0) {
            System.out.println("left: " + counterLeft);
            System.out.println("right: " + counterRight);
            System.out.println("up: " + counterUp);
            System.out.println("down: " + counterDown);

            System.out.println("Checked all");

            ArrayList<ArrayList<Tile>> wordListtest = new ArrayList<>();

            // for (int i = 0; i < 10; i++) {
            // wordListtest.add(wordList.get(i));
            // }

            ArrayList<Integer> points = countScore(Data.getGameSession().getGameBoard(), wordList);

            System.out.println("Points size :" + points.size());
            System.out.println(
                "How many points does the first word give:" + points.get(0).toString());
            // System.out.println(aiThreshold);
            ArrayList<Integer> randomSelector = new ArrayList<>();

            for (int k = 0; k < points.size(); k++) {
              randomSelector.add(k);
            }
            Collections.shuffle(randomSelector);

            for (int i = 0; i < points.size(); i++) {
              if (points.get(randomSelector.get(i)) >= aiThreshold
                  && points.get(randomSelector.get(i)) <= aiThreshold + 7) {
                choosenWord = wordList.get(randomSelector.get(i));
                pointsForRound = points.get(randomSelector.get(i));
                foundMatchingThreshold = true;

                System.out.println("DID BREAK : " + points.get(randomSelector.get(i)));
                break findacceptable;
              }
            }
          }
        }
      }
    }

    System.out.println("chosen word length : " + choosenWord.size());
    // for (Tile t : choosenWord) {
    // System.out.print(t.getLetter());
    // }
    System.out.println();

    // because ai uses tiles from the bag, the correct distubution needs to be set.
    HashMap<String, Integer> currentDistru =
        Data.getGameSession().getBag().getCurrentBagDistribution();

    if (foundMatchingThreshold) {
      System.out.println("chosen word : ");
      for (Tile tile : choosenWord) {
        System.out.print(tile.getLetter());
        System.out.println("Row: " + tile.getRow() + " column: " + tile.getColumn());

        Data.getGameSession().getGameBoard().placeTileTest(tile, tile.getRow(), tile.getColumn());
        currentDistru.put(tile.getLetter(), currentDistru.get(tile.getLetter()) - 1);
      }
      System.out.println("placed");
    } else {
      Data.getGameSession().setSkippedTurn(Data.getGameSession().getSkippedTurn() + 1);
    }

    Data.getGameSession()
        .getCurrentPlayer()
        .setPoints(Data.getGameSession().getCurrentPlayer().getPoints() + pointsForRound);
    System.out.println("erster");
    Data.getGameSession().getBag().setBagWithDistribution(currentDistru);
    System.out.println("zweiter");
    Data.getGameSession().getGameBoard().finishTurn();
    System.out.println("dritter");

    /*
     * System.out.println( Data.getGameSession() .getGameBoard()
     * .getTile(choosenWord.get(0).getRow(), choosenWord.get(0).getColumn()).getLetter());
     */

    Data.getGameSession().finishTurn();
    System.out.println("will hier jetzt wieder schließen.");
    Database.disconnect();
  }
}
