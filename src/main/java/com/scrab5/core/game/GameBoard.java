package com.scrab5.core.game;

import com.scrab5.util.database.DictionaryScanner;
import java.util.ArrayList;
import java.util.Iterator;

public class GameBoard {

  private String[][] gameBoardSpecial =
      new String[][]{
          {"TW", "  ", "  ", "DL", "  ", "  ", "  ", "TW", "  ", "  ", "  ", "DL", "  ", "  ",
              "TW"},
          {"  ", "DW", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "DW",
              "  "},
          {"  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DW", "  ",
              "  "},
          {"DL", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ",
              "DL"},
          {"  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ",
              "  "},
          {"  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL",
              "  "},
          {"  ", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DL", "  ",
              "  "},
          {"TW", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ",
              "TW"},
          {"  ", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DL", "  ",
              "  "},
          {"  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL",
              "  "},
          {"  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ",
              "  "},
          {"DL", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ",
              "DL"},
          {"  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DW", "  ",
              "  "},
          {"  ", "DW", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "DW",
              "  "},
          {"TW", "  ", "  ", "DL", "  ", "  ", "  ", "TW", "  ", "  ", "  ", "DL", "  ", "  ", "TW"}
      };

  private Tile[][] gameBoard = new Tile[15][15];
  private Tile[][] gameBoardCurrent = new Tile[15][15];
  private ArrayList<Tile> currentChanges = new ArrayList<>();
  private ArrayList<String> gameBoardWords = new ArrayList<>();

  /**
   * Constructor dont know if i need it
   *
   * @author trohwede
   */
  public GameBoard() {
  }

  /**
   * Places a tile at specific location
   *
   * @param t
   * @param row
   * @param column
   * @return
   * @author trohwede
   */
  public boolean placeTile(Tile t, int row, int column) {
    if (isSpotFree(row, column)) {
      gameBoardCurrent[row][column] = t;
      return true;
    } else {
      return false;
    }
  }

  public boolean removeTile(int row, int column) {
    if (isSpotFree(row, column)) {
      gameBoardCurrent[row][column] = null;
      return true;
    } else {
      return false;
    }
  }

  /**
   * Will check if a position is already used by a Tile
   *
   * @param row
   * @param column
   * @return boolean
   * @author trohwede
   */
  public boolean isSpotFree(int row, int column) {
    return gameBoardCurrent[row][column] == null;
  }

  public void finishTurn() {
    gameBoard = gameBoardCurrent.clone();
  }


  /**
   * Checks if a Spot is above, below, right or left of the first Tile placed.
   *
   * @param row
   * @param column
   * @return
   * @author trohwede
   */
  public boolean isSpotNextFree(int row, int column) {
    int row1 = currentChanges.get(0).getRow();
    int column1 = currentChanges.get(0).getColumn();
    return ((row == row1 + 1 && column == column1) || (row == row1 - 1 && column == column1) || (
        column == column1 + 1 && row == row1) || (column == column1 - 1 && row == row1));
  }

  /**
   * Checks wether the Spot is in line with the other placed tiles this turn. And if the tile is
   * free in generall
   *
   * @param row
   * @param column
   * @return
   * @author trohwede
   */
  public boolean isSpotInLine(int row, int column) {
    int row1 = currentChanges.get(0).getRow();
    int column1 = currentChanges.get(0).getColumn();
    int row2 = currentChanges.get(1).getRow();
    int column2 = currentChanges.get(1).getColumn();

    if ((row1 == row2 + 1 && column1 == column2) || (row1 == row2 - 1 && column1 == column2)) {

      Iterator<Tile> iter = currentChanges.iterator();
      while (iter.hasNext()) {
        int iterRow = iter.next().getRow();
        if (iterRow == row + 1 || iterRow == row - 1) {
          return true;
        }
      }
    } else if ((column == column1 + 1 && row == row1) || (column == column1 - 1 && row == row1)) {
      Iterator<Tile> iter = currentChanges.iterator();
      while (iter.hasNext()) {
        int iterColumn = iter.next().getColumn();
        if (iterColumn == row + 1 || iterColumn == row - 1) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Checks wether a tile is legal or not
   *
   * @param row
   * @param column
   * @return
   * @author trohwede
   */
  public boolean isTileLegal(int row, int column) {
    if (currentChanges.size() == 0) {
      return isSpotFree(row, column);
    } else if (currentChanges.size() == 1) {
      if (!(isSpotFree(row, column))) {
        return false;
      }
      return isSpotNextFree(row, column);
    } else {
      if (!(isSpotFree(row, column))) {
        return false;
      }
      return isSpotInLine(row, column);
    }
  }


  /**
   * Will count the score of all the layed tiles.
   *
   * @return
   * @author trohwede
   */
  public int countScore() {
    int score = 0;
    boolean tws = false;
    boolean dws = false;

    //main word;
    for (int i = 0; i < currentChanges.size(); i++) {
      int row = currentChanges.get(i).getRow();
      int column = currentChanges.get(i).getColumn();

      if (gameBoardSpecial[row][column] == "TL") {
        score += currentChanges.get(i).getValue() * 3;
      } else if (gameBoardSpecial[row][column] == "DL") {
        score += currentChanges.get(i).getValue() * 2;
      } else if (gameBoardSpecial[row][column] == "TW") {
        tws = true;
      } else if (gameBoardSpecial[row][column] == "DW") {
        dws = true;
      }
    }
    if (tws) {
      score *= 3;
    } else if (dws) {
      score *= 2;
    }

    //other words

    return score;
  }

  /**
   * Checks if the word layed changed anything on the board
   *
   * @return
   * @author trohwede
   */
  public boolean didTouchOther() {
    for (int i = 0; i < currentChanges.size(); i++) {
      int row = currentChanges.get(i).getRow();
      int col = currentChanges.get(i).getColumn();

      if (gameBoard[row - 1][col] != null) {
        return true;
      } else if (gameBoard[row + 1][col] != null) {
        return true;
      } else if (gameBoard[row][col - 1] != null) {
        return true;
      } else if (gameBoard[row][col + 1] != null) {
        return true;
      } else {
        return false;
      }
    }
    return false;
  }

  /**
   * Returns the Value of Tile at the given coordinates of the GameBoard
   *
   * @param row
   * @param column
   * @return
   */
  public String getTile(int row, int column) {
    return gameBoardCurrent[row][column].getLetter();
  }

  /**
   * Returns a list of all the words placed on the gameBoard 1. add als words left to right then
   * from top to bottom
   *
   * @return List of all Words in the Game
   * @author trohwede
   */
  public ArrayList<String> getWords() {
    ArrayList<String> listOfWords = new ArrayList<>();
    StringBuilder word = new StringBuilder();

    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        if (gameBoardCurrent[i][j] != null) {
          word.append(getTile(i, j));
        } else {
          if (word.length() > 1) {
            listOfWords.add(word.toString());
          }
          word.setLength(0);
        }
      }
      if (word.length() > 1) {
        listOfWords.add(word.toString());
      }
      word.setLength(0);
    }
    if (word.length() > 1) {
      listOfWords.add(word.toString());
      word.setLength(0);
    }

    for (int j = 0; j < 15; j++) {
      for (int i = 0; i < 15; i++) {
        if (gameBoardCurrent[i][j] != null) {
          word.append(getTile(i, j));
        } else {
          if (word.length() > 1) {
            listOfWords.add(word.toString());
          }
          word.setLength(0);
        }
      }
      if (word.length() > 1) {
        listOfWords.add(word.toString());
      }
      word.setLength(0);
    }
    if (word.length() > 1) {
      listOfWords.add(word.toString());
    }
    return listOfWords;
  }

  /**
   * Checks if all the words of the Board are legit
   *
   * @return boolean
   * @author trohwede
   */
  public boolean checkWordsLegit() {
    ArrayList<String> gameWords = getWords();
    Iterator<String> iter = gameWords.iterator();
    System.out.println(gameWords.toString());

    return DictionaryScanner.scan((gameWords.get(0)));
    //while (iter.hasNext()) {
    // if (DictionaryScanner.scan(iter.next())) {
    //   return false;
    //  }
    //}
    //return true;

  }


  /**
   * Clears the board of all Tiles and sets them to null
   *
   * @author trohwede
   */
  public void clearBoard() {
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        gameBoard[i][j] = null;
      }
    }
  }
}
