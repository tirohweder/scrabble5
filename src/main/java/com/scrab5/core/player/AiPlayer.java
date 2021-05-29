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
import java.util.Random;

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
  private static int fakeRackSize = 7;
  private final int aiThreshold;
  int counterUp;
  int counterDown;
  int counterRight;
  int counterLeft;
  Random random = new Random();
  private int aiSkippedTurns = 0;

  /**
   * Constructor for AiPlayer. Needs difficulty set.
   *
   * @param name name of the ai.
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
   * the possible words. The different steps are explained within the method.
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
    System.out.println("Bag size: " + Data.getGameSession().getBag().getSize());

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
      System.out.print(letter + " : ");
    }
    System.out.println();

    String[] possibleLetters = new String[possibleLetters1.size()];
    for (int i = 0; i < possibleLetters.length; i++) {
      possibleLetters[i] = possibleLetters1.get(i);
    }

    int maximumLength = before + 1 + after;
    ArrayList<String> finalWords = new ArrayList<String>();

    // TODO

    System.out.println("FAKE RACK SIZE: " + fakeRackSize);

    if (fakeRackSize < 7) {
      finalWords = DictionaryScanner.getWordsIncluding(fixLetter, fakeRackSize);
    } else if (maximumLength <= 8) {
      finalWords = DictionaryScanner.getWordsIncluding(fixLetter, maximumLength);
    } else {
      finalWords = DictionaryScanner.getWordsIncluding(fixLetter, 8);
    }
    System.out.println("1. Final Words length: " + finalWords.size());

    /* Deletion round 1: deleting all words that include a letter that is not in the bag. */
    StringBuilder sb = new StringBuilder();
    for (String s : possibleLetters) {
      sb.append(s);
    }
    sb.append(fixLetter);
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
      if (!checkBagDistributionLegal(currentDistribution, s, fixLetter)) {
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
      HashMap<String, Integer> currentDistribution, String word, String fixLetter) {
    boolean b = true;
    int j = 0;

    StringBuilder testb = new StringBuilder(word);

    for (int i = 0; i < word.length(); i++) {
      if (word.charAt(i) == fixLetter.charAt(0)) {
        testb.deleteCharAt(i);
        break;
      }
    }

    word = testb.toString();
    for (int i = 0; i < word.length(); i++) {
      if (!currentDistribution.containsKey(String.valueOf(word.charAt(i)))) {

        return false;
      }
    }
    for (int i = 0; i < word.length(); i++) {
      currentDistribution.replace(
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
      currentDistribution.replace(
          Character.toString(word.charAt(i)),
          currentDistribution.get(Character.toString(word.charAt(i))) + 1);
    }
    return b;
  }

  /**
   * Because we create the possible words as tile lists, because when we want.
   *
   * @author trohwede
   * @param possibleWords all possible words as a ArrayList of tiles.
   * @return Points per Word
   */
  public static ArrayList<Integer> countScore(ArrayList<ArrayList<Tile>> possibleWords) {

    ArrayList<Integer> scoreList = new ArrayList<>();

    boolean tw;
    boolean dw;
    for (ArrayList<Tile> word : possibleWords) {
      tw = false;
      dw = false;
      int score = 0;
      int scoreToBe = 0;

      for (Tile tile : word) {
        switch (Data.getGameSession()
            .getGameBoard()
            .getSpecialsAt(tile.getRow(), tile.getColumn())) {
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
      }

      if (dw) {
        score = scoreToBe * 2;
      } else if (tw) {
        score = scoreToBe * 3;
      } else {
        score += scoreToBe;
      }

      if (word.size() == 8) {
        score += 50;
      } else if (word.size() == 7
          && Data.getGameSession().getGameBoard().getSpecialsAt(7, 7).equals("DW")) {
        score += 50;
      }
      scoreList.add(score);
    }
    return scoreList;
  }

  /**
   * The idea of the function is to find out how much space it has to play with the given
   * coordinates so that it creates only one word and dosnt touch any other tiles.
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

    // ----------------------- UP -------------------------- //
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
      if (column == 0 && gameBoard[0][0] == null && gameBoard[0][1] == null) {
        counterUp++;
      } else if (column == 14 && gameBoard[0][14] == null && gameBoard[0][13] == null) {
        counterUp++;
      } else if (column != 0
          && column != 14
          && gameBoard[0][column] == null
          && gameBoard[0][column - 1] == null
          && gameBoard[0][column + 1] == null) {
        counterUp++;
      }
    }
    if (row != 14 && gameBoard[row + 1][column] != null) {
      counterUp = 0;
    }

    count = 0;
    // ----------------------- DOWN -------------------------- //
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
      if (column == 0 && gameBoard[14][0] == null && gameBoard[14][1] == null) {
        counterDown++;
      } else if (column == 14 && gameBoard[14][14] == null && gameBoard[14][13] == null) {
        counterDown++;
      } else if (column != 14
          && column != 0
          && gameBoard[14][column] == null
          && gameBoard[14][column - 1] == null
          && gameBoard[14][column + 1] == null) {
        counterDown++;
      }
    }
    if (row != 0 && gameBoard[row - 1][column] != null) {
      counterDown = 0;
    }

    count = 0;

    // ----------------------- RIGHT -------------------------- //
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
      if (row == 0 && gameBoard[0][14] == null && gameBoard[1][14] == null) {
        counterRight++;
      } else if (row == 14 && gameBoard[14][14] == null && gameBoard[13][14] == null) {
        counterRight++;
      } else if (row != 0
          && row != 14
          && gameBoard[row][14] == null
          && gameBoard[row - 1][14] == null
          && gameBoard[row + 1][14] == null) {
        counterRight++;
      }
    }
    if (column != 0 && gameBoard[row][column - 1] != null) {
      counterRight = 0;
    }

    count = 0;

    // ----------------------- Left -------------------------- //
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
      if (row == 0 && gameBoard[0][0] == null && gameBoard[1][0] == null) {
        counterLeft++;
      } else if (row == 14 && gameBoard[14][0] == null && gameBoard[13][0] == null) {
        counterLeft++;
      } else if (row != 0
          && column != 14
          && gameBoard[row][0] == null
          && gameBoard[row - 1][0] == null
          && gameBoard[row + 1][0] == null) {
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
    /*
     * int counterRight = 0; int counterLeft = 0; int counterUp = 0; int counterDown = 0;
     *
     * System.out.println("Finding free Spots at: " + y + " : " + x);
     *
     * // Checking the right Side of the Position on the Board while (y < 14 && y > 0 && x + 2 +
     * counterRight <= 14 && g.isSpotFreeOld(y, x + 1 + counterRight) && g.isSpotFreeOld(y, x + 2 +
     * counterRight) && g.isSpotFreeOld(y - 1, x + 1 + counterRight) && g.isSpotFreeOld(y + 1, x + 1
     * + counterRight)) { counterRight++; } while (y < 14 && y > 0 && x + 2 + counterRight == 15 &&
     * g.isSpotFreeOld(y, x + 1 + counterRight) && g.isSpotFreeOld(y - 1, x + 1 + counterRight) &&
     * g.isSpotFreeOld(y + 1, x + 1 + counterRight)) { counterRight++; } while (y == 0 && x + 2 +
     * counterRight <= 14 && g.isSpotFreeOld(y, x + 1 + counterRight) && g.isSpotFreeOld(y, x + 2 +
     * counterRight) && g.isSpotFreeOld(y + 1, x + 1 + counterRight)) { counterRight++; }
     *
     * while (y == 0 && x + 2 + counterRight == 15 && g.isSpotFreeOld(y, x + 1 + counterRight) &&
     * g.isSpotFreeOld(y + 1, x + 1 + counterRight)) { counterRight++; } while (y == 14 && x + 2 +
     * counterRight <= 14 && g.isSpotFreeOld(y, x + 1 + counterRight) && g.isSpotFreeOld(y, x + 2 +
     * counterRight) && g.isSpotFreeOld(y - 1, x + 1 + counterRight)) { counterRight++; } while (y
     * == 14 && x + 2 + counterRight == 15 && g.isSpotFreeOld(y, x + 1 + counterRight) &&
     * g.isSpotFreeOld(y - 1, x + 1 + counterRight)) { counterRight++; } if (x + 2 <= 14 &&
     * g.isSpotFree(y, x + 1) && !g.isSpotFree(y, x + 2)) { counterRight = -1; }
     *
     * if (x + 2 == 15 && y > 0 && y < 14 && g.isSpotFree(y, x + 1) && (!g.isSpotFree(y - 1, x + 1)
     * || !g.isSpotFree(y + 1, x + 1))) { counterRight = -1; } if (x + 2 == 15 && y == 0 &&
     * g.isSpotFree(y, x + 1) && !g.isSpotFree(y + 1, x + 1)) { counterRight = -1; } if (x + 2 == 15
     * && y == 14 && g.isSpotFree(y, x + 1) && !g.isSpotFree(y - 1, x + 1)) { counterRight = -1; }
     * if (x - 1 >= 0 && !g.isSpotFree(y, x - 1)) { counterRight = -1; }
     *
     * // Checking the left Side of the Position on the Board while (y < 14 && y > 0 && x - 2 -
     * counterLeft >= 0 && g.isSpotFreeOld(y, x - 1 - counterLeft) && g.isSpotFreeOld(y, x - 2 -
     * counterLeft) && g.isSpotFreeOld(y - 1, x - 1 - counterLeft) && g.isSpotFreeOld(y + 1, x - 1 -
     * counterLeft)) { counterLeft++; // System.out.println("y="+ y +"; counterLeft ="+counterLeft);
     * } while (y < 14 && y > 0 && x - 2 - counterLeft == -1 && g.isSpotFreeOld(y, x - 1 -
     * counterLeft) && g.isSpotFreeOld(y - 1, x - 1 - counterLeft) && g.isSpotFreeOld(y + 1, x - 1 -
     * counterLeft)) { counterLeft++; // System.out.println("y=" + y + "; counterLeft =" +
     * counterLeft); } while (y == 0 && x - 2 - counterLeft >= 0 && g.isSpotFreeOld(y, x - 1 -
     * counterLeft) && g.isSpotFreeOld(y, x - 2 - counterLeft) && g.isSpotFreeOld(y + 1, x - 1 -
     * counterLeft)) { counterLeft++; } while (y == 0 && x - 2 - counterLeft == -1 &&
     * g.isSpotFreeOld(y, x - 1 - counterLeft) && g.isSpotFreeOld(y + 1, x - 1 - counterLeft)) {
     * counterLeft++; } while (y == 14 && x - 2 - counterLeft >= 0 && g.isSpotFreeOld(y, x - 1 -
     * counterLeft) && g.isSpotFreeOld(y, x - 2 - counterLeft) && g.isSpotFreeOld(y - 1, x - 1 -
     * counterLeft)) { counterLeft++; } while (y == 14 && x - 2 - counterLeft == -1 &&
     * g.isSpotFreeOld(y, x - 1 - counterLeft) && g.isSpotFreeOld(y - 1, x - 1 - counterLeft)) {
     * counterLeft++; } if (x - 2 >= 0 && g.isSpotFree(y, x - 1) && !g.isSpotFree(y, x - 2)) {
     * counterLeft = -1; }
     *
     * if (x - 2 == -1 && y > 0 && y < 14 && g.isSpotFree(y, x - 1) && (!g.isSpotFree(y - 1, x - 1)
     * || !g.isSpotFree(y + 1, x - 1))) { counterLeft = -1; } if (x - 2 == -1 && y == 0 &&
     * g.isSpotFree(y, x - 1) && !g.isSpotFree(y + 1, x - 1)) { counterLeft = -1; } if (x - 2 == -1
     * && y == 14 && g.isSpotFree(y, x - 1) && !g.isSpotFree(y - 1, x - 1)) { counterLeft = -1; } if
     * (x + 1 <= 14 && !g.isSpotFree(y, x + 1)) { counterLeft = -1; }
     *
     * // Checking for free Spots under the Position x,y while (y + 2 + counterDown <= 14 && x > 0
     * && x < 14 && g.isSpotFreeOld(y + 1 + counterDown, x) && g.isSpotFreeOld(y + 2 + counterDown,
     * x) && g.isSpotFreeOld(y + 1 + counterDown, x + 1) && g.isSpotFreeOld(y + 1 + counterDown, x -
     * 1)) { counterDown++; } while (y + 2 + counterDown == 15 && x > 0 && x < 14 &&
     * g.isSpotFreeOld(y + 1 + counterDown, x) && g.isSpotFreeOld(y + 1 + counterDown, x + 1) &&
     * g.isSpotFreeOld(y + 1 + counterDown, x - 1)) { counterDown++; }
     *
     * while (y + 2 + counterDown <= 14 && x == 0 && g.isSpotFreeOld(y + 1 + counterDown, x) &&
     * g.isSpotFreeOld(y + 2 + counterDown, x) && g.isSpotFreeOld(y + 1 + counterDown, x + 1)) {
     * counterDown++; } while (y + 2 + counterDown == 15 && x == 0 && g.isSpotFreeOld(y + 1 +
     * counterDown, x) && g.isSpotFreeOld(y + 1 + counterDown, x + 1)) { counterDown++; } while (y +
     * 2 + counterDown <= 14 && x == 14 && g.isSpotFreeOld(y + 1 + counterDown, x) &&
     * g.isSpotFreeOld(y + 2 + counterDown, x) && g.isSpotFreeOld(y + 1 + counterDown, x - 1)) {
     * counterDown++; } while (y + 2 + counterDown == 15 && x == 14 && g.isSpotFreeOld(y + 1 +
     * counterDown, x) && g.isSpotFreeOld(y + 1 + counterDown, x - 1)) { counterDown++; } if (y + 2
     * <= 14 && g.isSpotFree(y + 1, x) && !g.isSpotFree(y + 2, x)) { counterDown = -1; }
     *
     * if (y + 2 == 15 && x > 0 && x < 14 && g.isSpotFree(y + 1, x) && (!g.isSpotFree(y + 1, x + 1)
     * || !g.isSpotFree(y + 1, x - 1))) { counterDown = -1; } if (y + 2 == 15 && x == 0 &&
     * g.isSpotFree(y + 1, x) && !g.isSpotFree(y + 1, x + 1)) { counterDown = -1; } if (y + 2 == 15
     * && x == 14 && g.isSpotFree(y + 1, x) && !g.isSpotFree(y + 1, x - 1)) { counterDown = -1; }
     *
     * if (y - 1 >= 0 && !g.isSpotFree(y - 1, x)) { counterDown = -1; } // Checking for free Spots
     * above the Position x,y while (y - 2 - counterUp >= 0 && x > 0 && x < 14 && g.isSpotFreeOld(y
     * - 1 - counterUp, x) && g.isSpotFreeOld(y - 2 - counterUp, x) && g.isSpotFreeOld(y - 1 -
     * counterUp, x + 1) && g.isSpotFreeOld(y - 1 - counterUp, x - 1)) { counterUp++; //
     * System.out.println("y=" + y + "; counterUp =" + counterUp); } while (x > 0 && x < 14 && y - 2
     * - counterUp == -1 && g.isSpotFreeOld(y - 1 - counterUp, x) && g.isSpotFreeOld(y - 1 -
     * counterUp, x + 1) && g.isSpotFreeOld(y - 1 - counterUp, x - 1)) { counterUp++; //
     * System.out.println("y=" + y + "; counterUp =" + counterUp); } while (y - 2 - counterUp >= 0
     * && x == 0 && g.isSpotFreeOld(y - 1 - counterUp, x) && g.isSpotFreeOld(y - 2 - counterUp, x)
     * && g.isSpotFreeOld(y - 1 - counterUp, x + 1)) { counterUp++; } while (y - 2 - counterUp == -1
     * && x == 0 && g.isSpotFreeOld(y - 1 - counterUp, x) && g.isSpotFreeOld(y - 1 - counterUp, x +
     * 1)) { counterUp++; } while (y - 2 - counterUp >= 0 && x == 14 && g.isSpotFreeOld(y - 1 -
     * counterUp, x) && g.isSpotFreeOld(y - 2 - counterUp, x) && g.isSpotFreeOld(y - 1 - counterUp,
     * x - 1)) { counterUp++; } while (y - 2 - counterUp == -1 && x == 14 && g.isSpotFreeOld(y - 1 -
     * counterUp, x) && g.isSpotFreeOld(y - 1 - counterUp, x - 1)) { counterUp++; }
     *
     * if (y - 2 >= 0 && g.isSpotFree(y - 1, x) && !g.isSpotFree(y - 2, x)) { counterUp = -1; }
     *
     * if (y - 2 == -1 && x > 0 && x < 14 && g.isSpotFree(y - 1, x) && (!g.isSpotFree(y - 1, x - 1)
     * || !g.isSpotFree(y - 1, x + 1))) { counterUp = -1; } if (y - 2 == -1 && x == 0 &&
     * g.isSpotFree(y - 1, x) && !g.isSpotFree(y - 1, x + 1)) { counterUp = -1; } if (y - 2 == -1 &&
     * x == 14 && g.isSpotFree(y - 1, x) && !g.isSpotFree(y - 1, x - 1)) { counterUp = -1; }
     *
     * if (y + 1 <= 14 && !g.isSpotFree(y + 1, x)) { counterUp = -1; }
     */
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
   * check out which coordinates in which way are free.(getspotsfree2) We want to create words, that
   * only create 1 new word on the game board. So it returns the integer value for each row we have
   * space. With word generator, we can give the tile a fixed letter and it will try to create as
   * many words as possible with the space and letters given. Then we calculate all the points for
   * each word. And then select the word that fit the difficulty threshold. If the spot found doesnt
   * give a good enough word, we check out the next tile. Then chosen word is then placed on the
   * board and the required tiles for that word are removed from the bag of tiles.
   *
   * @author trohwede
   */
  public void aiPlay() throws IOException {
    lettersFromDatabase = UseDatabase.getAllLetters();
    pointsPerLetterFromDatabase = UseDatabase.getAllPointsPerLetter();
    Database.disconnect();

    // needs to remove random letter from the ai rack, so the chances for pulling a tile
    // remain the same for human player remains the same

    for (int i = 0; i < Data.getGameSession().getListOfPlayers().size(); i++) {
      if (Data.getGameSession().getListOfPlayers().get(i) instanceof AiPlayer) {
        Data.getGameSession()
            .getBag()
            .addRackToBag(Data.getGameSession().getListOfPlayers().get(i).getRack());

        if (Data.getGameSession().getCurrentPlayer()
            == Data.getGameSession().getListOfPlayers().get(i)) {
          fakeRackSize = Data.getGameSession().getCurrentPlayer().getRack().getRackSize();
        }

        Data.getGameSession().getListOfPlayers().get(i).getRack().clearRack();
      }
    }

    boolean foundMatchingThreshold = false;

    ArrayList<Tile> choosenWord = new ArrayList<>();

    // go through game while threshhold is not reached
    int pointsForRound = 0;

    if (Data.getGameSession().getGameBoard().isFirstTile()) {
      getSpotsFree2(7, 7, Data.getGameSession().getGameBoard());
    }

    // to randomise where words are selected, we need random arrays.
    ArrayList<Integer> rowRand = new ArrayList<>();
    ArrayList<Integer> columnRand = new ArrayList<>();
    for (int i = 0; i < 15; i++) {
      rowRand.add(i);
      columnRand.add(i);
    }
    Collections.shuffle(rowRand);
    Collections.shuffle(columnRand);

    int row;
    int column;

    findacceptable:
    for (int i = 0; i < 15; i++) {
      row = rowRand.get(i);
      for (int j = 0; j < 15; j++) {
        column = columnRand.get(j);
        System.out.println("Checking " + row + " : " + column);

        // If tile is already used on the gameBoard or if FirstTile
        if (Data.getGameSession().getGameBoard().getPlayedTile(row, column) != null
            || Data.getGameSession().getGameBoard().isFirstTile()) {
          getSpotsFree2(row, column, Data.getGameSession().getGameBoard());

          ArrayList<ArrayList<Tile>> wordList = new ArrayList<>();

          if (Data.getGameSession().getGameBoard().isFirstTile()) {

            currentFixLetter = Data.getGameSession().getBag().randomLetterFromBag();
            boolean horizontal = random.nextBoolean();
            wordList = wordGenerator(currentFixLetter, 5, 4, 7, 7, horizontal);

            // if there is more space to go vertical go vertical
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
            // else if there is space go horizontal
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

            // because wordList is in alphabetical order we check random entrys if they can fulfill
            // requirements
            ArrayList<Integer> points = countScore(wordList);
            ArrayList<Integer> randomSelector = new ArrayList<>();
            for (int k = 0; k < points.size(); k++) {
              randomSelector.add(k);
            }
            Collections.shuffle(randomSelector);

            // checks if the points for the word fulfill requirements
            for (int l = 0; l < points.size(); l++) {

              // we use the gaussian method to have random borders for playing words
              // also reduces threshold needed for each consecutive skipped turn

              // TODO rack size reduce points
              int aiThresholdLow =
                  (int) Math.round(random.nextGaussian() * 2 + aiThreshold - (aiSkippedTurns * 2));
              int aiThresholdHigh =
                  (int)
                      Math.round(
                          random.nextGaussian() * 3 + aiThreshold + 10 + (aiSkippedTurns * 2));

              if (points.get(randomSelector.get(l)) >= aiThresholdLow
                  && points.get(randomSelector.get(l)) <= aiThresholdHigh) {
                choosenWord = wordList.get(randomSelector.get(l));
                pointsForRound = points.get(randomSelector.get(l));
                foundMatchingThreshold = true;

                break findacceptable;
              }
            }
          }
        }
      }
    }

    // because ai uses tiles from the bag, the correct distribution needs to be set.
    HashMap<String, Integer> currentBagDistribution =
        Data.getGameSession().getBag().getCurrentBagDistribution();

    if (foundMatchingThreshold) {
      for (Tile tile : choosenWord) {
        // needed because other wise removes 1 more letter from the bag for the tile that was
        // already placed
        if (Data.getGameSession().getGameBoard().isSpotFreeOld(tile.getRow(), tile.getColumn())) {
          currentBagDistribution.put(
              tile.getLetter(), currentBagDistribution.get(tile.getLetter()) - 1);
        }
        Data.getGameSession().getGameBoard().placeTileForce(tile, tile.getRow(), tile.getColumn());

        // Plays sound if triple word
        if (Data.getGameSession()
            .getGameBoard()
            .getSpecialsAt(tile.getRow(), tile.getColumn())
            .equals("TW")) {
          if (Data.getGameSession().isOnline()) {
            Data.getPlayerClient().playSound(false);
          } else {
            Data.getGameSession().playSound(false);
          }
        }
        // if special tiles are used needs to reset them.
        Data.getGameSession().getGameBoard().setSpecialAt(tile.getRow(), tile.getColumn(), "  ");
      }

      // if ai plays it will reset skipped turns
      Data.getGameSession().setSkippedTurn(0);
      aiSkippedTurns = 0;

      // play sound bingo if 7 tiles are played at once by the ai. Because it always only uses 1
      // tile to create a word it need length 8

      if (choosenWord.size() == 7) {
        if (Data.getGameSession().isOnline()) {
          Data.getPlayerClient().playSound(false);
        } else {
          Data.getGameSession().playSound(false);
        }
      } else if (choosenWord.size() == 8) { // if ai plays first turn, needs to check if it just
        // uses 7,7 from an old tile or
        // if it played it new
        for (Tile tile : choosenWord) {
          if (tile.getRow() == 7
              && tile.getColumn() == 7
              && Data.getGameSession().getGameBoard().getPlayedTile(7, 7) == null) {
            if (Data.getGameSession().isOnline()) {
              Data.getPlayerClient().playSound(true);
            } else {
              Data.getGameSession().playSound(true);
            }
          }
        }
      }

    } else {
      // if ai skips a turn it needs to increase skipped turns
      aiSkippedTurns++;
      Data.getGameSession().setSkippedTurn(Data.getGameSession().getSkippedTurn() + 1);
    }

    // sets new Distribution, adds points to the ai, and finishes turn
    Data.getGameSession()
        .getCurrentPlayer()
        .setPoints(Data.getGameSession().getCurrentPlayer().getPoints() + pointsForRound);
    Data.getGameSession().getBag().setBagWithDistribution(currentBagDistribution);
    Data.getGameSession().finishTurn();

    Database.disconnect();
  }
}
