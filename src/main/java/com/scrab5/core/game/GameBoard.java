package com.scrab5.core.game;

public class GameBoard {
  /*
   TW: Tripple Word
   DW: Double Word
   TL: Tripple Letter
   DL: Double Letter
  */
  private String[][] gameBoard_bonus =
      new String[][] {
        {"TW", "  ", "  ", "DL", "  ", "  ", "  ", "TW", "  ", "  ", "  ", "DL", "  ", "  ", "TW"},
        {"  ", "DW", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "DW", "  "},
        {"  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  "},
        {"DL", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ", "DL"},
        {"  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  "},
        {"  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  "},
        {"  ", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "  "},
        {"TW", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ", "TW"},
        {"  ", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "  "},
        {"  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  "},
        {"  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  "},
        {"DL", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ", "DL"},
        {"  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  "},
        {"  ", "DW", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "DW", "  "},
        {"TW", "  ", "  ", "DL", "  ", "  ", "  ", "TW", "  ", "  ", "  ", "DL", "  ", "  ", "TW"}
      };

  // creates gameboard where every tile is stored
  private Tile[][] gameBoard = new Tile[15][15];

  // created gameboard of tiles placed this turn
  private Tile[][] game_Board_current = new Tile[15][15];

  public GameBoard() {}

  public void placeTile(int row, int column) {}

  // checks wether a spot is free or already taken.
  public boolean is_spot_free(int row, int column) {
    return gameBoard[row][column] == null;
  }

  // removes all Tiles from the board
  public void clearBoard() {
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        gameBoard[i][j] = null;
      }
    }
  }

  public boolean placeTile(Tile t, int row, int column) {
    if (is_spot_free(row, column)) {
      gameBoard[row][column] = t;
      return true;
    } else {
      return false;
    }
  }

  public boolean isBoardLegal() {
    return true;
  }

  public int countScore() {
    return 1;
  }
}
