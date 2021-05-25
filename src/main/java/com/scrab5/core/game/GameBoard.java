package com.scrab5.core.game;

import com.scrab5.ui.Data;
import com.scrab5.util.parser.DictionaryScanner;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * GameBoard, represents the physical gameboard where everyone plays on. Including all functions
 * that change or edit the gameboard.
 *
 * @author trohwede
 */
public class GameBoard implements Serializable {

  private static final long serialVersionUID = 1L;
  private final Tile[][] gameBoard = new Tile[15][15];
  private final Tile[][] gameBoardCurrent = new Tile[15][15];
  private String[][] gameBoardSpecial =
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
  private ArrayList<Tile> currentChanges = new ArrayList<>();
  private boolean firstTile = true;

  public GameBoard() {
    firstTile = true;
  }

  public boolean isFirstTile() {
    return firstTile;
  }

  public void setFirstTile(boolean firstTile) {
    this.firstTile = firstTile;
  }

  public ArrayList<Tile> getCurrentChanges() {
    return currentChanges;
  }

  public void setCurrentChanges(ArrayList<Tile> currentChanges) {
    this.currentChanges = currentChanges;
  }

  public String[][] getGameBoardSpecial() {
    return gameBoardSpecial;
  }

  public void setGameBoardSpecial(String[][] gameBoardSpecial) {
    this.gameBoardSpecial = gameBoardSpecial;
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
   * @param t Tile you want to place.
   * @param row coordinates of the to be checked spot
   * @param column coordinates of the to be checked spot
   * @return if your placement was successful
   * @author trohwede
   */
  public boolean placeTile(Tile t, int row, int column) {

    if (isTileLegal(row, column)) {
      gameBoardCurrent[row][column] = t;
      t.setRow(row);
      t.setColumn(column);
      t.setRackPlace(null);
      currentChanges.add(t);
      System.out.println(currentChanges.size());
      firstTile = false;

      return true;
    } else {
      return false;
    }
  }

  /**
   * Placing a tile in the test doesn't have to follow rules, this makes testing a lot easier.
   *
   * @param t Tile you want to place
   * @param row coordinates of the to be checked spot
   * @param column coordinates of the to be checked spot
   * @return if your placement was successful
   * @author trohwede
   */
  public boolean placeTileTest(Tile t, int row, int column) {
    if (isSpotFree(row, column)) {
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
   * Returns the tile at the given coordinates, only tiles that have already been confirmed.
   *
   * @param row coordinates of the to be checked spot
   * @param column coordinates of the to be checked spot
   * @return Tile that has already been played.
   * @author trohwede
   */
  public Tile getPlayedTile(int row, int column) {
    return gameBoard[row][column];
  }

  /**
   * Checks if the currentPlayer is the same as the currentPerson clicking.
   *
   * @return if player is allowed to play
   * @author trohwede
   */
  public boolean isAllowedToPlay() {
    return (Data.getCurrentUser().equals(Data.getGameSession().getCurrentPlayer().getName()));
  }

  /**
   * Checks if it is possible to remove a tile. First is has to check if the tile is already
   * confirmed. It also has to check if it the tile in the middle.
   *
   * @param row coordinates of the to be checked spot
   * @param column coordinates of the to be checked spot
   * @return if it is possible to remove a tile
   * @author trohwede
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
   * gameBoard that has all confirmed changes. Also currentChanges have to be resettet.
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
   * @param row coordinates of the to be checked spot
   * @param column coordinates of the to be checked spot
   * @return if at the given coordinates there is already a tile placed on the gameBoardCurrent
   * @author trohwede
   */
  public boolean isSpotFree(int row, int column) {
    return gameBoardCurrent[row][column] == null;
  }

  /**
   * Checks if the given coordinates, are in the same row and column. And if not next to each other
   * if between them other tiles have already been placed
   *
   * @param row coordinates of the to be checked spot
   * @param column coordinates of the to be checked spot
   * @return if the given coordinates are in the same row or column and if they are connected.
   * @author trohwede
   */
  public boolean isSpotNext(int row, int column) {
    System.out.println("Start");
    int row1 = currentChanges.get(0).getRow();
    int column1 = currentChanges.get(0).getColumn();

    if ((row == row1 + 1 && column == column1)
        || (row == row1 - 1 && column == column1)
        || (row == row1 && column == column1 + 1)
        || (row == row1 && column == column1 - 1)) {
      return true;
    } else {
      System.out.println("Nicht nebeinander");
    }

    // gleiche reihe
    if (row1 == row) {
      System.out.println("Gleiche Reihe");
      if (column1 < column) {
        System.out.println("Neue Tile is right");
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
      // gleiche column
    } else if (column1 == column) {
      System.out.println("Gleiche Spalte");
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
   * @param row coordinates of the to be checked spot
   * @param column coordinates of the to be checked spot
   * @return if the given coordinates are in line with the first two tiles placed.
   * @author trohwede
   */
  public boolean isSpotInLine(int row, int column) {
    int row1 = currentChanges.get(0).getRow();
    int column1 = currentChanges.get(0).getColumn();
    int row2 = currentChanges.get(1).getRow();
    int column2 = currentChanges.get(1).getColumn();

    if (column1 == column2 && column1 == column) {
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

    } else if (row1 == row2 && row1 == row) {
      System.out.println("Same Row");
      if (column < column1) {
        System.out.println("Neuer buchstabe ist left");
        for (int i = 1; i < Integer.min(column1, column2) - column; i++) {
          if (gameBoardCurrent[row][Integer.min(column1, column2) - i] == null) {
            return false;
          }
        }
      } else if (column > column1) {
        System.out.println("Neuer buchstabe ist right");
        for (int i = 1; i < column - Integer.max(column1, column2); i++) {
          if (gameBoardCurrent[row][Integer.max(column1, column2) + i] == null) {
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
   * Checks if at the given coordinates its legal to play the next tile.
   *
   * @param row coordinates row - of the to be checked tile.
   * @param column coordinates column - of the to be checked tile.
   * @return boolean if at the given coordinates its possible to play the next tile.
   * @author trohwede
   */
  public boolean isTileLegal(int row, int column) {

    if (firstTile) {
      System.out.println("First Tile should be Placed");
      return (row == 7 && column == 7);

    } else {
      if (currentChanges.size() == 0) {
        return isConnectedToOldTiles(row, column);
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
  }

  /**
   * We check use the function getTouchedWords() to get the tiles that are required to be counted
   * toward the score. The the same way I check for words created to check if they are legit. I go
   * through the board add up a score. If the word ends, the word score, will be doubled or tripled.
   *
   * @return the score that was achieved during the players turn
   * @author trohwede
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
        if (changedWords[i][j] != null) { // Tile is empty
          word.append(getTile(i, j).getLetter());
          System.out.print(getTile(i, j).getLetter() + " tile: ");
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
      System.out.println(score);
    }

    if (currentChanges.size() == 7) {
      score += 50;
    }
    return score;
  }

  /**
   * The goal of the function is to find all the words that will have to be checked. If there is a
   * Tile that was already played in a previous round, touches a currentChange, the code tries to
   * find more Tiles in the same direction. If the next tile would be null, it stops.
   *
   * @return returns a 2d Array of Tiles. That only contain all the newly created words.
   * @author trohwede
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

      int count = 1;

      // System.out.println("Current Tile: " + currentChanges.get(i).getLetter());
      if ((row - count >= 0) && gameBoard[row - count][column] != null) {
        top = true;
        // System.out.println("Drüber: " + gameBoard[row - count][column].getLetter());
      }

      if ((row + count < 15) && gameBoard[row + count][column] != null) {
        below = true;
        // System.out.println("below: " + gameBoard[row + count][column].getLetter());
      }

      if ((column + count < 15) && gameBoard[row][column + count] != null) {
        right = true;
        // System.out.println("right: " + gameBoard[row][column + count].getLetter());
      }

      if ((column - count >= 0) && gameBoard[row][column - count] != null) {
        left = true;
        // System.out.println("left: " + gameBoard[row][column - count].getLetter());
      }

      while (row - count >= 0 && top) {
        if (gameBoard[row - count][column] != null) {
          touchedTiles[row - count][column] = gameBoard[row - count][column];
          // System.out.println("topsucces");
          count++;
        } else {
          top = false;
        }
      }

      count = 1;

      while (row + count <= 14 && below) {
        if (gameBoard[row + count][column] != null) {
          touchedTiles[row + count][column] = gameBoard[row + count][column];
          // System.out.println("belowsucces");
          count++;
        } else {
          below = false;
        }
      }

      count = 1;

      while (column + count <= 14 && right) {
        if (gameBoard[row][column + count] != null) {
          touchedTiles[row][column + count] = gameBoard[row][column + count];
          // System.out.println("right succes");
          count++;
        } else {
          right = false;
        }
      }

      count = 1;

      while (column - count >= 0 && left) {
        if (gameBoard[row][column - count] != null) {
          touchedTiles[row][column - count] = gameBoard[row][column - count];
          // System.out.println("left succes");
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
   * @param row row coordinates.
   * @param column column coordinates.
   * @return the Tile at the given coordinates.
   * @author trohwede
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
   * @return ArrayList including every word on the board
   * @author trohwede
   */
  public ArrayList<String> getWords() {
    ArrayList<String> listOfWords = new ArrayList<>();
    StringBuilder word = new StringBuilder();

    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
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
      word.setLength(0);
    }

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

    for (String word1 : listOfWords) {
      System.out.println(word1);
    }
    return listOfWords;
  }

  /**
   * Checks if all the words generated are actually in the dictionary.
   *
   * @return boolean if all words are found in the dictionary.
   * @author trohwede
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
   * Clears the board of all Tiles and sets them to null.
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
}
