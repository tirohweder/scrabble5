package com.scrab5.core.game;

import java.util.ArrayList;
import java.util.Iterator;

public class GameBoard {

  /*
   TW: Tripple Word
   DW: Double Word
   TL: Tripple Letter
   DL: Double Letter
  */
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
   * @author trohwede
   */
  public GameBoard() {
  }

  /**
   * @return
   * @author trohwede
   */
  public void placeTile(int row, int column) {
  }

  /**
   * @return
   * @author trohwede
   */
  public boolean isSpotFree(int row, int column) {
    return gameBoard[row][column] == null;
  }

  public boolean isSpotNext(int row, int column) {
    int row1 = currentChanges.get(0).getRow();
    int column1 = currentChanges.get(0).getColumn();
    return ((row == row1 + 1 && column == column1) || (row == row1 - 1 && column == column1) || (
        column == column1 + 1 && row == row1) || (column == column1 - 1 && row == row1));
  }

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
   * @return
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
   * @return
   * @author trohwede
   */
  public boolean isBoardLegal() {
    return true;
  }

  /**
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
