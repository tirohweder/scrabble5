package com.scrab5.core.game;

import java.util.ArrayList;
import java.util.Iterator;

public class GameBoard {

  private String[][] gameBoard_bonus =
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

  /**
   * Constructor dont know if i need it
   *
   * @author trohwede
   */
  public GameBoard() {
  }


  /**
   * Place Tile, will place a Tile on the gameBoard
   *
   * @param row
   * @param column
   * @author trohwede
   */
  public void placeTile(int row, int column) {
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
    return gameBoard[row][column] == null;
  }


  /**
   * Checks if a Spot is above, below, right or left of the first Tile placed.
   *
   * @param row
   * @param column
   * @return
   * @author trohwede
   */
  public boolean isSpotNext(int row, int column) {
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
      return isSpotNext(row, column);
    } else {
      if (!(isSpotFree(row, column))) {
        return false;
      }
      return isSpotInLine(row, column);
    }
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
      gameBoard[row][column] = t;
      return true;
    } else {
      return false;
    }
  }

  /**
   * Checks wether the board as it is right now is legal.
   *
   * @return
   * @author trohwede
   */
  public boolean isBoardLegal() {
    return true;
  }

  /**
   * Will count the score of all the layed tiles.
   *
   * @return
   * @author trohwede
   */
  public int countScore() {
    for (int i = 0; i < currentChanges.size(); i++) {

      int row = currentChanges.get(i).getRow();
      int column = currentChanges.get(i).getColumn();


    }

    return 1;
  }
}
