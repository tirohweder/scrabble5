package com.scrab5.core.game;

import com.scrab5.ui.Data;
import com.scrab5.util.constants.Constants;
import com.scrab5.util.parser.DictionaryScanner;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * GameBoard, represents the physical gameBoard where everyone plays on. Including all functions
 * that change or edit the gameBoard.Here we can calculate the points, check what new words haven
 * been played and check if the words are still legit.
 *
 * @author trohwede
 */
public class GameBoard implements Serializable {

  private static final long serialVersionUID = 1L;
  private final Tile[][] gameBoard = new Tile[15][15];
  private final Tile[][] gameBoardCurrent = new Tile[15][15];
  private String[][] gameBoardSpecial;
  private ArrayList<Tile> currentChanges = new ArrayList<>();
  private boolean firstTile;

  /**
   * GameBoard constructor. Sets first tile to true and resets gameBoardSpecial.
   *
   * @author trohwede
   */
  public GameBoard() {
    firstTile = true;
    gameBoardSpecial =
        new String[][] {
          {
            "TW", "  ", "  ", "DL", "  ", "  ", "  ", "TW", "  ", "  ", "  ", "DL", "  ", "  ", "TW"
          },
          {
            "  ", "DW", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "DW", "  "
          },
          {
            "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  "
          },
          {
            "DL", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ", "DL"
          },
          {
            "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  "
          },
          {
            "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  "
          },
          {
            "  ", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "  "
          },
          {
            "TW", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ", "TW"
          },
          {
            "  ", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DL", "  ", "  "
          },
          {
            "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  "
          },
          {
            "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  ", "  ", "DW", "  ", "  ", "  ", "  "
          },
          {
            "DL", "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  ", "DL"
          },
          {
            "  ", "  ", "DW", "  ", "  ", "  ", "DL", "  ", "DL", "  ", "  ", "  ", "DW", "  ", "  "
          },
          {
            "  ", "DW", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "TL", "  ", "  ", "  ", "DW", "  "
          },
          {"TW", "  ", "  ", "DL", "  ", "  ", "  ", "TW", "  ", "  ", "  ", "DL", "  ", "  ", "TW"}
        };
  }

  public Tile[][] getGameBoard() {
    return gameBoard;
  }

  public boolean isFirstTile() {
    return firstTile;
  }

  public ArrayList<Tile> getCurrentChanges() {
    return currentChanges;
  }

  public void setSpecialAt(int row, int column, String string) {
    this.gameBoardSpecial[row][column] = string;
  }

  public String getSpecialsAt(int row, int column) {
    return gameBoardSpecial[row][column];
  }

  /**
   * Places a tile, but only if it follows the rules.
   *
   * @author trohwede
   * @param t Tile you want to place.
   * @param row coordinates of the to be checked spot
   * @param column coordinates of the to be checked spot
   * @return if your placement was successful
   */
  public boolean placeTile(Tile t, int row, int column) {

    if (isTileLegal(row, column)) {
      gameBoardCurrent[row][column] = t;
      t.setRow(row);
      t.setColumn(column);
      t.setRackPlace(null);
      currentChanges.add(t);
      firstTile = false;

      return true;
    } else {
      return false;
    }
  }

  /**
   * Placing a tile without checking if its correct. Needed for the Ai, and makes testing easier.
   *
   * @author trohwede
   * @param t Tile you want to place
   * @param row coordinates of the to be checked spot
   * @param column coordinates of the to be checked spot
   */
  public void placeTileForce(Tile t, int row, int column) {
    if (isSpotFree(row, column)) {
      gameBoardCurrent[row][column] = t;
      t.setRow(row);
      t.setColumn(column);
      t.setRackPlace(null);
      currentChanges.add(t);
      firstTile = false;
    }
  }

  /**
   * Returns the tile at the given coordinates, only tiles that have already been confirmed.
   *
   * @author trohwede
   * @param row coordinates of the to be checked spot
   * @param column coordinates of the to be checked spot
   * @return Tile that has already been played.
   */
  public Tile getPlayedTile(int row, int column) {
    return gameBoard[row][column];
  }

  /**
   * Checks if the currentPlayer is the same as the currentPerson clicking.
   *
   * @author trohwede
   * @return if player is allowed to play
   */
  public boolean isAllowedToPlay() {
    return (Data.getCurrentUser().equals(Data.getGameSession().getCurrentPlayer().getName()));
  }

  /**
   * Checks if it is possible to remove a tile. First is has to check if the tile is already
   * confirmed. It also has to check if it the tile in the middle.
   *
   * @author trohwede
   * @param row coordinates of the to be checked spot
   * @param column coordinates of the to be checked spot
   * @return if it is possible to remove a tile
   */
  public boolean removeTile(int row, int column) {
    if (gameBoard[row][column] != null) {
      return false;
    }
    if (!isSpotFree(row, column) && currentChanges.size() > 0) {
      Tile t = gameBoardCurrent[row][column];
      gameBoardCurrent[row][column] = null;
      currentChanges.remove(t);
      if (row == 7 && column == 7) {
        firstTile = true;
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * If the turn is finished this method, will set add the new tiles placed this round to the
   * gameBoard that has all confirmed changes. Also currentChanges have to be reset.
   *
   * @author trohwede
   */
  public void finishTurn() {
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        if (gameBoardCurrent[i][j] != null) {
          gameBoard[i][j] = gameBoardCurrent[i][j];
        }
      }
    }
    currentChanges.clear();
  }

  /**
   * Will check if at the given coordinates there is already a tile placed on gameBoardCurrent.
   *
   * @author trohwede
   * @param row coordinates of the to be checked spot
   * @param column coordinates of the to be checked spot
   * @return if at the given coordinates there is already a tile placed on the gameBoardCurrent
   */
  public boolean isSpotFree(int row, int column) {
    return gameBoardCurrent[row][column] == null;
  }

  /**
   * Will check if at the given coordinates there is already a tile placed on gameBoardCurrent.
   *
   * @author trohwede
   * @param row coordinates of the to be checked spot
   * @param column coordinates of the to be checked spot
   * @return if at the given coordinates there is already a tile placed on the gameBoardCurrent
   */
  public boolean isSpotFreeOld(int row, int column) {
    return gameBoard[row][column] == null;
  }

  /**
   * Checks if the given coordinates, are in the same row and column. And if not next to each other
   * if between them other tiles have already been placed
   *
   * @author trohwede
   * @param row coordinates of the to be checked spot
   * @param column coordinates of the to be checked spot
   * @return if the given coordinates are in the same row or column and if they are connected.
   */
  public boolean isSpotNext(int row, int column) {
    int row1 = currentChanges.get(0).getRow();
    int column1 = currentChanges.get(0).getColumn();

    // quick check if its just next to each other without an old tile connecting
    if ((row == row1 + 1 && column == column1)
        || (row == row1 - 1 && column == column1)
        || (row == row1 && column == column1 + 1)
        || (row == row1 && column == column1 - 1)) {
      return true;
    }

    // needs to check if other "old" tiles fill the void between the 2 placed tiles.
    // same row
    if (row1 == row) {
      if (column1 < column) {
        for (int i = 1; i < column - column1; i++) {
          if (gameBoard[row][column1 + i] == null) {
            return false;
          }
        }
      } else {
        for (int i = 1; i < column1 - column; i++) {
          if (gameBoard[row][column + i] == null) {
            return false;
          }
        }
      }
      // same column
    } else if (column1 == column) {
      if (row1 < row) {
        for (int i = 1; i < row - row1; i++) {
          if (gameBoard[row1 + i][column1] == null) {
            return false;
          }
        }
      } else {
        for (int i = 1; i < row1 - row; i++) {
          if (gameBoard[row + i][column1] == null) {
            return false;
          }
        }
      }
    } else {
      return false;
    }
    return true;
  }

  /**
   * Checks if the given coordinates would be connected to the previously played tiles.
   *
   * @author trohwede
   * @param row coordinates of the to be checked spot
   * @param column coordinates of the to be checked spot
   * @return if the given coordinates are connected to the previously played tiles.
   */
  public boolean isConnectedToOldTiles(int row, int column) {
    return ((row + 1 < 15) && gameBoard[row + 1][column] != null)
        || ((row - 1 >= 0) && gameBoard[row - 1][column] != null)
        || ((column + 1 < 15) && gameBoard[row][column + 1] != null)
        || ((column - 1 >= 0) && gameBoard[row][column - 1] != null);
  }

  /**
   * Checks if its the given coordinates are in line with the first two tiles placed.
   *
   * @author trohwede
   * @param row coordinates of the to be checked spot
   * @param column coordinates of the to be checked spot
   * @return if the given coordinates are in line with the first two tiles placed.
   */
  public boolean isSpotInLine(int row, int column) {
    int row1 = currentChanges.get(0).getRow();
    int column1 = currentChanges.get(0).getColumn();
    int row2 = currentChanges.get(1).getRow();
    int column2 = currentChanges.get(1).getColumn();

    if (column1 == column2 && column1 == column) { // given coordinates are vertical
      if (row < row1) {
        for (int i = 1; i < Integer.min(row1, row2) - row; i++) {
          if (gameBoardCurrent[Integer.min(row1, row2) - i][column] == null) {
            return false;
          }
        }
      } else if (row > row1) {
        for (int i = 1; i < row - Integer.max(row1, row2); i++) {
          if (gameBoardCurrent[Integer.max(row1, row2) + i][column] == null) {
            return false;
          }
        }
      }

    } else if (row1 == row2 && row1 == row) { // given coordinates are horizontal
      if (column < column1) {
        for (int i = 1; i < Integer.min(column1, column2) - column; i++) {
          if (gameBoardCurrent[row][Integer.min(column1, column2) - i] == null) {
            return false;
          }
        }
      } else if (column > column1) {
        for (int i = 1; i < column - Integer.max(column1, column2); i++) {
          if (gameBoardCurrent[row][Integer.max(column1, column2) + i] == null) {
            return false;
          }
        }
      }
    } else { // if not horizontal or vertical its not a legal move.
      return false;
    }
    return true;
  }

  /**
   * Checks if at the given coordinates its legal to play the next tile.
   *
   * @author trohwede
   * @param row coordinates row - of the to be checked tile.
   * @param column coordinates column - of the to be checked tile.
   * @return boolean if at the given coordinates its possible to play the next tile.
   */
  public boolean isTileLegal(int row, int column) {

    // if first tile is not played yet, needs to be placed on 7,7
    if (firstTile) {
      return (row == 7 && column == 7);

    } else {
      // if its the first tile played need to be next to one tile on the old gameBoard
      if (currentChanges.size() == 0) {
        return isConnectedToOldTiles(row, column);
        // the second tile can should create a horizontal line or a vertical line
      } else if (currentChanges.size() == 1) {
        if (!(isSpotFree(row, column))) {
          return false;
        }
        return isSpotNext(row, column);
        // if 3 or more tiles, it needs to be in the horizontal or vertical line created by the 1.
        // and 2. tile played
      } else {
        if (!(isSpotFree(row, column))) {
          return false;
        }
        return isSpotInLine(row, column);
      }
    }
  }

  /**
   * We check use the function getTouchedWords() to get the tiles that are required to be counted
   * toward the score. The the same way I check for words created to check if they are legit. I go
   * through the board add up a score. If the word ends, the word score, will be doubled or tripled.
   *
   * @author trohwede
   * @return the score that was achieved during the players turn
   */
  public int countScore() {
    int score = 0;
    boolean tws = false;
    boolean dws = false;

    StringBuilder word = new StringBuilder();
    Tile[][] changedWords = getTouchedWords();

    int scoreToBe = 0;

    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        if (changedWords[i][j] != null) {
          word.append(getTile(i, j).getLetter());
          switch (gameBoardSpecial[i][j]) {
            case "TL":
              scoreToBe += changedWords[i][j].getValue() * 3;
              break;
            case "DL":
              scoreToBe += changedWords[i][j].getValue() * 2;
              break;
            case "TW":
              scoreToBe += changedWords[i][j].getValue();
              tws = true;
              if (Data.getGameSession().isOnline()) {
                Data.getPlayerClient().playSound(false);
              } else {
                Data.getGameSession().playSound(false);
              }
              break;
            case "DW":
              scoreToBe += changedWords[i][j].getValue();
              dws = true;
              break;
            default:
              scoreToBe += changedWords[i][j].getValue();
              break;
          }

        } else { // Tile is empty
          if (word.length() > 1) {
            if (tws) {
              scoreToBe *= 3;
            } else if (dws) {
              scoreToBe *= 2;
            }
            score += scoreToBe;
          }
          scoreToBe = 0;
          word.setLength(0);
        }
      } // End of second for loop

      if (word.length() > 1) {
        if (tws) {
          scoreToBe *= 3;
        } else if (dws) {
          scoreToBe *= 2;
        }
        score += scoreToBe;
      }

      scoreToBe = 0;
      word.setLength(0);
    }

    scoreToBe = 0;
    word.setLength(0);

    for (int j = 0; j < 15; j++) {
      for (int i = 0; i < 15; i++) {
        if (changedWords[i][j] != null) { // Tile is empty
          word.append(getTile(i, j).getLetter());
          switch (gameBoardSpecial[i][j]) {
            case "TL":
              scoreToBe += changedWords[i][j].getValue() * 3;
              break;
            case "DL":
              scoreToBe += changedWords[i][j].getValue() * 2;
              break;
            case "TW":
              scoreToBe += changedWords[i][j].getValue();
              tws = true;
              break;
            case "DW":
              scoreToBe += changedWords[i][j].getValue();
              dws = true;
              break;
            default:
              scoreToBe += changedWords[i][j].getValue();
              break;
          }
          // needs to remove gameBoardSpecial here, so it cant be used more than once.
          gameBoardSpecial[i][j] = "  ";

        } else { // Tile is empty
          if (word.length() > 1) {
            if (tws) {
              scoreToBe *= 3;
            } else if (dws) {
              scoreToBe *= 2;
            }
            score += scoreToBe;
          }
          scoreToBe = 0;
          word.setLength(0);
        }
      } // End of second for loop

      if (word.length() > 1) {
        if (tws) {
          scoreToBe *= 3;
        } else if (dws) {
          scoreToBe *= 2;
        }
        score += scoreToBe;
      }
      scoreToBe = 0;
      word.setLength(0);
    }

    // if 7 tiles have been played by the player receive bingo points
    if (currentChanges.size() == 7) {
      score += 50;

      // bingo sound
      if (Data.getGameSession().isOnline()) {
        Data.getPlayerClient().playSound(true);
      } else {
        Data.getGameSession().playSound(true);
      }
    }
    return score;
  }

  /**
   * The goal of the function is to find all the words that will have to be checked. If there is a
   * Tile that was already played in a previous round, touches a currentChange, the code tries to
   * find more Tiles in the same direction. If the next tile would be null, it stops.
   *
   * @author trohwede
   * @return returns a 2d Array of Tiles. That only contain all the newly created words.
   */
  public Tile[][] getTouchedWords() {
    Tile[][] touchedTiles = new Tile[15][15];

    for (Tile currentChange : currentChanges) {
      boolean below = false;
      boolean top = false;
      boolean right = false;
      boolean left = false;

      int row = currentChange.getRow();
      int column = currentChange.getColumn();

      touchedTiles[row][column] = currentChange;
      // if there are tiles that are left,right,above or below the currentChanges tiles.
      int count = 1;

      if ((row - count >= 0) && gameBoard[row - count][column] != null) {
        top = true;
      }

      if ((row + count < 15) && gameBoard[row + count][column] != null) {
        below = true;
      }

      if ((column + count < 15) && gameBoard[row][column + count] != null) {
        right = true;
      }

      if ((column - count >= 0) && gameBoard[row][column - count] != null) {
        left = true;
      }

      while (row - count >= 0 && top) {
        if (gameBoard[row - count][column] != null) {
          touchedTiles[row - count][column] = gameBoard[row - count][column];
          count++;
        } else {
          top = false;
        }
      }

      count = 1;

      // Searches further into the rows or columns to find more tiles that create the choose word.
      while (row + count <= 14 && below) {
        if (gameBoard[row + count][column] != null) {
          touchedTiles[row + count][column] = gameBoard[row + count][column];
          count++;
        } else {
          below = false;
        }
      }

      count = 1;

      while (column + count <= 14 && right) {
        if (gameBoard[row][column + count] != null) {
          touchedTiles[row][column + count] = gameBoard[row][column + count];
          count++;
        } else {
          right = false;
        }
      }

      count = 1;

      while (column - count >= 0 && left) {
        if (gameBoard[row][column - count] != null) {
          touchedTiles[row][column - count] = gameBoard[row][column - count];
          count++;
        } else {
          left = false;
        }
      }
    }

    return touchedTiles;
  }

  /**
   * Returns the Tile of gameBoardCurrent, at the given coordinates.
   *
   * @author trohwede
   * @param row row coordinates.
   * @param column column coordinates.
   * @return the Tile at the given coordinates.
   */
  public Tile getTile(int row, int column) {
    if (gameBoardCurrent[row][column] != null) {
      return gameBoardCurrent[row][column];
    }
    return null;
  }

  /**
   * Returns a list of all the words placed on the gameBoard. First it finds all horizontal words,
   * if there are more than 2 words next to each other it counts as an word. After that it checks
   * all words horizontally.
   *
   * @author trohwede
   * @return ArrayList including every word on the board
   */
  public ArrayList<String> getWords() {
    ArrayList<String> listOfWords = new ArrayList<>();
    StringBuilder word = new StringBuilder();

    // double for loop to go through the gameBoard left to right and top to bottom
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        // adds all consecutive tiles that arent empty to a string.
        if (gameBoardCurrent[i][j] != null) {
          word.append(getTile(i, j).getLetter());
        } else {
          // if the string is longer than 1, it counts as a word and is added to the word list.
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

    // double for loop to go through the gameBoard top to bottom and left to right. Basic copy from
    // above just changed j/i
    for (int j = 0; j < 15; j++) {
      for (int i = 0; i < 15; i++) {
        if (gameBoardCurrent[i][j] != null) {
          word.append(getTile(i, j).getLetter());
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
   * Checks if all words generated by getWords() are found in the dictionary.
   *
   * @author trohwede
   * @return boolean if all words are found in the dictionary.
   */
  public boolean checkWordsLegit() {
    ArrayList<String> gameWords = getWords();
    for (String gameWord : gameWords) {
      if (!DictionaryScanner.scan(gameWord)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Clears the board of all Tiles and sets them to null. Also resets currentChanges, firstTile, and
   * gameBoardSpecial.
   *
   * @author trohwede
   */
  public void clearBoard() {
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        gameBoardCurrent[i][j] = null;
      }
    }
    currentChanges.clear();
    firstTile = true;
    gameBoardSpecial = Constants.GAME_BOARD_SPECIAL_BASIC;
  }

  /**
   * Method just for testing because the "normal" scanner scans the selected dictionary, but in the
   * juint test no user selects a dictionary file. So the test the function if a word is legit, the
   * scanTest needs to be called. Checks if all words generated by getWords() are found in the
   * dictionary.
   *
   * @author trohwede
   * @return boolean if all words are found in the dictionary.
   */
  public boolean checkWordsLegitTest() {
    ArrayList<String> gameWords = getWords();
    for (String gameWord : gameWords) {
      if (!DictionaryScanner.scanTest(gameWord)) {
        return false;
      }
    }
    return true;
  }
}
