package com.scrab5.core.player;

import com.scrab5.core.game.BagOfTiles;
import com.scrab5.core.game.GameBoard;
import com.scrab5.core.game.Tile;
import com.scrab5.ui.Data;
import com.scrab5.util.database.UseDatabase;
import com.scrab5.util.parser.DictionaryScanner;
import java.util.ArrayList;
import java.util.HashMap;

public class AiPlayer extends Player {

  static String[] lettersFromDatabase;
  static int[] pointsPerLetterFromDatabase;
  static int currentFixX;
  static int currentFixY;
  static String currentFixLetter;
  int counterUp;
  int counterDown;
  int counterRight;
  int counterLeft;
  int aiThreshold;

  /**
   * @param name
   * @author hraza
   */
  // public AiPlayer(String name) {
  //   super(name);
  // }

  /**
   * Constructor for Tile
   *
   * @param name
   * @author trohwede
   */
  public AiPlayer(String name, int difficulty) {
    super(name, false);

    if (difficulty == 0) {
      this.aiThreshold = Data.easyAiThreshold;
    } else {
      this.aiThreshold = Data.hardAiThreshold;
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
   * @param fixLetter the Letter that is already placed on the GameBoard where the Ai wants to lay a
   *     word next to
   * @param before the amount of tiles that are free before this letter
   * @param after the amount of tiles that are free after this letter
   * @param x the x-coordinate of the tile with letter fixLetter on the board
   * @param y the y-coordinate of the tile with letter fixLetter on the board
   * @param horizontal is true, when the word needs to get laid horizontal and false if vertical.
   *     This parameter is needed later on
   * @author lengist
   */
  public static ArrayList<ArrayList<Tile>> wordGenerator(
      String fixLetter, int before, int after, int x, int y, boolean horizontal) {
    System.out.println();
    System.out.println("fixLetter: " + fixLetter);
    System.out.println("x: " + x);
    System.out.println("y: " + y);
    System.out.println();
    System.out.println("Saved fixLetter: " + currentFixLetter);
    System.out.println("saved x: " + currentFixX);
    System.out.println("saved y: " + currentFixY);
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

    System.out.println("After Deltion round 2: " + finalWords.size());

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
      // System.out.println("test word: " + s);
      tiles.add(wordToTiles(s, fixLetter, currentFixX, currentFixY, horizontal));
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
   * @param x the x-coordinate of the tile with letter fixLetter on the board
   * @param y the y-coordinate of the tile with letter fixLetter on the board
   * @param horizontal is true, when the word needs to get laid horizontal and false if vertical
   * @return tiles a ArrayList containing all tiles for the word word
   */
  public static ArrayList<Tile> wordToTiles(
      String word, String fixLetter, int x, int y, boolean horizontal) {
    ArrayList<Tile> tiles = new ArrayList<Tile>();
    tiles = getCoordinatesRep(word, fixLetter, x, y, horizontal);
    /*if (!isLetterExistingRepeatedly(word)) {
    /*for (int i = 0; i < word.length(); i++) {

      value = getPointForLetter(String.valueOf(word.charAt(i)));
      coordinates =
          getCoordinates(word, fixLetter, String.valueOf(word.charAt(i)),
              x, y, horizontal);
      row = coordinates.get(1);
      column = coordinates.get(0);
      Tile t = new Tile(String.valueOf(word.charAt(i)), value, row, column);
      tiles.add(t);
    }*/
    /*} else {
    tiles = getCoordinatesRep(word, fixLetter, x, y, horizontal);
    /*for (int i = 0; i < word.length(); i++) {

      value = getPointForLetter(String.valueOf(word.charAt(i)));
      coordinates =
          getCoordinates(word, fixLetter, String.valueOf(word.charAt(i)),
              x, y, horizontal);
      row = coordinates.get(1);
      column = coordinates.get(0);
      Tile t = new Tile(String.valueOf(word.charAt(i)), value, row, column);
      tiles.add(t);
    }

    for (int i = 1; i < word.length(); i++) {
      if (word.charAt(i) == word.charAt(i - 1)) {
        if (!horizontal) {
          tiles.get(i - 1).setRow(tiles.get(i).getRow() - 1);
        } else {
          tiles.get(i - 1).setColumn(tiles.get(i).getColumn() - 1);
        }
      }
    } */
    // }
    return tiles;
  }

  public static ArrayList<Tile> getCoordinatesRep(
      String word, String fixLetter, int xfixLetter, int yfixLetter, boolean horizontal) {
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
        System.out.println(word.charAt(i));
        row = yfixLetter;
        column = xfixLetter - (fixPosition - i);
        Tile t = new Tile(String.valueOf(word.charAt(i)), value, row, column);
        tiles.add(t);
      }
      Tile t1 = new Tile(fixLetter, getPointForLetter(fixLetter), yfixLetter, xfixLetter);
      tiles.add(t1);
      for (int i = fixPosition + 1; i < word.length(); i++) {
        value = getPointForLetter(String.valueOf(word.charAt(i)));
        row = yfixLetter;
        column = xfixLetter + (i - fixPosition);
        Tile t = new Tile(String.valueOf(word.charAt(i)), value, row, column);
        tiles.add(t);
      }
    } else {
      for (int i = 0; i < fixPosition; i++) {
        value = getPointForLetter(String.valueOf(word.charAt(i)));
        row = yfixLetter - (fixPosition - i);
        column = xfixLetter;
        Tile t = new Tile(String.valueOf(word.charAt(i)), value, row, column);
        tiles.add(t);
      }
      Tile t1 = new Tile(fixLetter, getPointForLetter(fixLetter), yfixLetter, xfixLetter);
      tiles.add(t1);
      for (int i = fixPosition + 1; i < word.length(); i++) {
        value = getPointForLetter(String.valueOf(word.charAt(i)));
        row = yfixLetter + (i - fixPosition);
        column = xfixLetter;
        Tile t = new Tile(String.valueOf(word.charAt(i)), value, row, column);
        tiles.add(t);
      }
    }

    return tiles;
  }

  /**
   * Method to calculate the coordinates for the tiles of a new word. First it estimates the place
   * of the fixLetter in word and then the place of the newLetter in word. With this information,
   * the Coordinates for the newLetter get calculated.
   *
   * @param word the word that contains the fixLetter and newLetter to calculate the exact positions
   *     of the given letters
   * @param fixLetter the letter that is already n the gameboard
   * @param newLetter a letter from the word different to fixLetter. This is the letter the
   *     coordinates need to be calculated for.
   * @param xfixLetter the x-Coordinate of the fixLetter
   * @param yfixLetter the y-Coordinate of the fixLetter
   * @param horizontal a boolean variable for the alignment of the word on the board. If it is true,
   *     the word will be laid horizontal. If not, vertical.
   * @return coordinates, a ArrayList including the x- and y-Coordinate of the newLetter.
   * @author lengist
   */
  public static ArrayList<Integer> getCoordinates(
      String word,
      String fixLetter,
      String newLetter,
      int xfixLetter,
      int yfixLetter,
      boolean horizontal) {
    int placeFixLetter = 0;
    int xnew = 0;
    int ynew = 0;

    for (int i = 0; i < word.length(); i++) {
      if (word.charAt(i) == fixLetter.charAt(0)) {
        placeFixLetter = i;
      }
    }
    for (int i = 0; i < word.length(); i++) {
      if (word.charAt(i) == newLetter.charAt(0)) {
        if (horizontal) {
          ynew = yfixLetter;
          if (i < placeFixLetter) {
            xnew = xfixLetter - (placeFixLetter - i);
          } else {
            xnew = xfixLetter + (i - placeFixLetter);
          }
        } else {
          xnew = xfixLetter;
          if (i < placeFixLetter) {
            ynew = yfixLetter - (placeFixLetter - i);
          } else {
            ynew = yfixLetter + (i - placeFixLetter);
          }
        }
      }
    }
    ArrayList<Integer> coordinates = new ArrayList<Integer>();
    coordinates.add(xnew);
    coordinates.add(ynew);
    return coordinates;
  }

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
   * @param letter the String of the letter for that the points need to be known
   * @return points the points saved in database for the letter.
   * @author lengist
   */
  public static int getPointForLetter(String letter) {

    int points = 0;
    // System.out.println("Letter length" + lettersFromDatabase.length);
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

    // System.out.println("Word: " + word + " is : " + b);
    return b;
  }

  /**
   * Cout
   *
   * @param gameBoard takes the currentGameBoard
   * @param possibleWords revices the possible words as a ArrayList<Tile>
   * @return Points per Word
   * @author trohwede
   */
  public static ArrayList<Integer> countScore(
      GameBoard gameBoard, ArrayList<ArrayList<Tile>> possibleWords) {
    ArrayList<Integer> scoreList = new ArrayList<>();

    System.out.println("Counting score " + possibleWords.size());
    System.out.println("Possible " + possibleWords.get(0).size());

    for (int i = 0; i < possibleWords.get(0).size(); i++) {
      System.out.println(possibleWords.get(0).get(i).getLetter());
      System.out.println("VALUE of Tile: " + possibleWords.get(0).get(i).getValue());
    }

    for (ArrayList<Tile> word : possibleWords) {

      int score = 0;
      int scoreToBe = 0;
      boolean tw = false;
      boolean dw = false;
      for (Tile tile : word) {
        // System.out.println(tile.getLetter());
        if (gameBoard.getPlayedTile(tile.getRow(), tile.getColumn()) == null) { // ) {
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
        }
      }
      if (dw) {
        score = scoreToBe * 2;
      } else if (tw) {
        score = scoreToBe * 3;
      } else {
        score += scoreToBe;
      }

      // System.out.println("score: " + score);
      scoreList.add(score);
    }

    System.out.println(scoreList.get(0).toString());

    return scoreList;
  }

  /**
   * This Method is looking for free valid Spots around the given position and initializes the
   * counters.
   *
   * @param x is the column-position from where it starts countin on the current gameBoard
   * @param y is the row-position from where it starts countin on the current gameBoard
   * @author hraza
   */
  public void getSpotsfree(int x, int y, GameBoard g) {
    int counterRight = 0;
    int counterLeft = 0;
    int counterUp = 0;
    int counterDown = 0;

    System.out.println("Finding free Spots");

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
      System.out.println("y=" + y + "; counterLeft =" + counterLeft);
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
    // Checking for free Spots above the Position x,y
    while (y - 2 - counterUp >= 0
        && x > 0
        && x < 14
        && g.isSpotFreeOld(y - 1 - counterUp, x)
        && g.isSpotFreeOld(y - 2 - counterUp, x)
        && g.isSpotFreeOld(y - 1 - counterUp, x + 1)
        && g.isSpotFreeOld(y - 1 - counterUp, x - 1)) {
      counterUp++;
      System.out.println("y=" + y + "; counterUp =" + counterUp);
    }
    while (x > 0
        && x < 14
        && y - 2 - counterUp == -1
        && g.isSpotFreeOld(y - 1 - counterUp, x)
        && g.isSpotFreeOld(y - 1 - counterUp, x + 1)
        && g.isSpotFreeOld(y - 1 - counterUp, x - 1)) {
      counterUp++;
      System.out.println("y=" + y + "; counterUp =" + counterUp);
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
    this.counterDown = counterDown;
    this.counterUp = counterUp;
    this.counterRight = counterRight;
    this.counterLeft = counterLeft;

    if (counterLeft == 0 | counterRight == 0) {
      this.counterLeft = 0;
      this.counterRight = 0;
    } else if (counterUp == 0 | counterDown == 0) {
      this.counterDown = 0;
      this.counterUp = 0;
    }
  }

  public void aiPlay() {
    lettersFromDatabase = UseDatabase.getAllLetters();
    pointsPerLetterFromDatabase = UseDatabase.getAllPointsPerLetter();

    boolean foundMatchingThreshold = false;

    ArrayList<Tile> choosenWord = new ArrayList<>();
    System.out.println("Starting to find word");
    // go through game while threshhold is not reached
    int pointsForRound = 0;

    if (Data.getGameSession().getGameBoard().isFirstTile()) {
      getSpotsfree(7, 7, Data.getGameSession().getGameBoard());
    }

    findacceptable:
    for (int row = 0; row < 15; row++) {
      for (int column = 0; column < 15; column++) {
        System.out.println("Checking " + row + " : " + column);

        // If tile is already used on the gameBoard or if FirstTile
        if (Data.getGameSession().getGameBoard().getPlayedTile(row, column) != null
            || Data.getGameSession().getGameBoard().isFirstTile()) {
          getSpotsfree(row, column, Data.getGameSession().getGameBoard());

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
                    counterDown,
                    counterUp,
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
                    counterRight,
                    counterLeft,
                    column,
                    row,
                    true);

            if (wordList.isEmpty()) {
              break;
            }
          }

          System.out.println("Checked all");

          ArrayList<ArrayList<Tile>> wordListtest = new ArrayList<>();

          // for (int i = 0; i < 10; i++) {
          // wordListtest.add(wordList.get(i));
          // }

          ArrayList<Integer> points = countScore(Data.getGameSession().getGameBoard(), wordList);

          System.out.println("Points size :" + points.size());
          System.out.println(
              "How many points does the first word give:" + points.get(0).toString());
          System.out.println(aiThreshold);

          for (int k = 0; k < points.size(); k++) {
            // System.out.println("Points: " + points + " word: " + wordList.get(k));
            // System.out.print(points.get(k) + " : ");

            if (points.get(k) >= aiThreshold) {
              choosenWord = wordList.get(k);
              pointsForRound = points.get(k);
              foundMatchingThreshold = true;
              System.out.println("DID BREAK");
              break findacceptable;
            }
          }
        }
      }
    }

    System.out.println("chosen word: ");
    for (Tile t : choosenWord) {
      System.out.print(t.getLetter());
    }
    System.out.println();

    // because ai uses tiles from the bag, the correct distubution needs to be set.
    HashMap<String, Integer> currentDistru =
        Data.getGameSession().getBag().getCurrentBagDistribution();

    if (foundMatchingThreshold) {
      System.out.println("chosen word 720: ");
      for (Tile tile : choosenWord) {
        System.out.println(tile.getLetter());
        System.out.println("y: " + tile.getRow());
        System.out.println("x: " + tile.getColumn());

        Data.getGameSession().getGameBoard().placeTileTest(tile, tile.getRow(), tile.getColumn());
        currentDistru.put(tile.getLetter(), currentDistru.get(tile.getLetter()) - 1);
      }
      System.out.println("placed");
    }

    Data.getGameSession()
        .getCurrentPlayer()
        .setPoints(Data.getGameSession().getCurrentPlayer().getPoints() + pointsForRound);
    Data.getGameSession().getBag().setBagWithDistribution(currentDistru);
    Data.getGameSession().getGameBoard().finishTurn();

    /*System.out.println(
    Data.getGameSession()
        .getGameBoard()
        .getTile(choosenWord.get(0).getRow(), choosenWord.get(0).getColumn()).getLetter());*/

    Data.getGameSession().finishTurn();
  }
}
