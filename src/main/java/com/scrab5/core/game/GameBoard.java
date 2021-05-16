package com.scrab5.core.game;

import com.scrab5.util.textParser.DictionaryScanner;
import java.io.Serializable;
import java.util.ArrayList;

public class GameBoard implements Serializable {

  private static final long serialVersionUID = 1L;

  public ArrayList<Tile> getCurrentChanges() {
    return currentChanges;
  }

  public void setCurrentChanges(ArrayList<Tile> currentChanges) {
    this.currentChanges = currentChanges;
  }

  private String[][] gameBoardSpecial = new String[][]{
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
      {"TW", "  ", "  ", "DL", "  ", "  ", "  ", "TW", "  ", "  ", "  ", "DL", "  ", "  ", "TW"}};

  private Tile[][] gameBoard = new Tile[15][15];
  private Tile[][] gameBoardCurrent = new Tile[15][15];


  private ArrayList<Tile> currentChanges = new ArrayList<>();
  private ArrayList<String> gameBoardWords = new ArrayList<>();
  private boolean firstTile = true;


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


  public boolean removeTile(int row, int column) {
    if (gameBoard[row][column] != null) {
      return false;
    }
    if (!isSpotFree(row, column) && currentChanges.size() > 0) {
      Tile t = gameBoardCurrent[row][column];
      gameBoardCurrent[row][column] = null;
      currentChanges.remove(t);
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

    return (row == row1 + 1 && column == column1) || (row == row1 - 1 && column == column1)
        || (row == row1 && column == column1 + 1) || (row == row1 && column == column1 - 1);
  }

  /**
   * Checks if the given coordinates would be legal to play a tile there
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

    //
    if ((row1 == row2 + 1 && column1 == column2) || (row1 == row2 - 1 && column1 == column2)) {

      for (int i = 0; i < currentChanges.size(); i++) {
        if (((row == currentChanges.get(i).getRow() - 1) && column == column1)
            || ((row == currentChanges.get(i).getRow() + 1) && column == column1)) {
          return true;
        }
      }
      return false;


    } else if ((row1 == row2 && column1 == column2 + 1)
        || (row1 == row2 && column1 == column2 - 1)) {

      for (int i = 0; i < currentChanges.size(); i++) {
        if (((column == currentChanges.get(i).getColumn() - 1) && row == row1)
            || ((column == currentChanges.get(i).getColumn() + 1) && row == row1)) {
          return true;
        }
      }
      return false;
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

    if (firstTile) {
      return (row == 7 && column == 7);
    } else {
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
  }


  /**
   * Will count the score of all the placed tiles of one turn. The copied the code from getting all
   * the Words. So I go through first rows then columns, add points for everyword, and if its a
   * tripple or double letter word i can multiply the word like this
   *
   * @return the Score that was achived during the players turn
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
          if (gameBoardSpecial[i][j] == "TL") {
            scoreToBe += changedWords[i][j].getValue() * 3;
          } else if (gameBoardSpecial[i][j] == "DL") {
            scoreToBe += changedWords[i][j].getValue() * 2;
          } else if (gameBoardSpecial[i][j] == "TW") {
            scoreToBe += changedWords[i][j].getValue();
            tws = true;
          } else if (gameBoardSpecial[i][j] == "DW") {
            scoreToBe += changedWords[i][j].getValue();
            dws = true;
          } else {
            scoreToBe += changedWords[i][j].getValue();
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

    for (int j = 0; j < 15; j++) {
      for (int i = 0; i < 15; i++) {
        if (changedWords[i][j] != null) { // Tile is empty
          word.append(getTile(i, j).getLetter());
          if (gameBoardSpecial[i][j] == "TL") {
            scoreToBe += changedWords[i][j].getValue() * 3;
          } else if (gameBoardSpecial[i][j] == "DL") {
            scoreToBe += changedWords[i][j].getValue() * 2;
          } else if (gameBoardSpecial[i][j] == "TW") {
            scoreToBe += changedWords[i][j].getValue();
            tws = true;
          } else if (gameBoardSpecial[i][j] == "DW") {
            scoreToBe += changedWords[i][j].getValue();
            dws = true;
          } else {
            scoreToBe += changedWords[i][j].getValue();
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

    if (word.length() > 1) {
      if (tws) {
        scoreToBe *= 3;
      } else if (dws) {
        scoreToBe *= 2;
      }
      score += scoreToBe;
    }

    if (currentChanges.size() == 7) {
      score += 50;
    }
    return score;
  }


  public Tile[][] getTouchedWords() {
    Tile[][] touchedTiles = new Tile[15][15];

    for (int i = 0; i < currentChanges.size(); i++) {
      boolean drunter = false;
      boolean drueber = false;
      boolean rechts = false;
      boolean links = false;

      int row = currentChanges.get(i).getRow();
      int column = currentChanges.get(i).getColumn();

      touchedTiles[row][column] = currentChanges.get(i);

      int count = 1;

      // System.out.println("Current Tile: " + currentChanges.get(i).getLetter());
      if ((row - count >= 0) && gameBoard[row - count][column] != null) {
        drueber = true;
        // System.out.println("Drüber: " + gameBoard[row - count][column].getLetter());
      }

      if ((row + count < 15) && gameBoard[row + count][column] != null) {
        drunter = true;
        // System.out.println("Drunter: " + gameBoard[row + count][column].getLetter());
      }

      if ((column + count < 15) && gameBoard[row][column + count] != null) {
        rechts = true;
        // System.out.println("Rechts: " + gameBoard[row][column + count].getLetter());
      }

      if ((column - count >= 0) && gameBoard[row][column - count] != null) {
        links = true;
        // System.out.println("Links: " + gameBoard[row][column - count].getLetter());
      }

      while ((row - count >= 0) && row - count >= 0 && drueber) {
        if (gameBoard[row - count][column] != null) {
          touchedTiles[row - count][column] = gameBoard[row - count][column];
          // System.out.println("druebersucces");
          count++;
        } else {
          drueber = false;
        }
      }

      count = 1;

      while ((row + count < 15) && row + count <= 14 && drunter) {
        if (gameBoard[row + count][column] != null) {
          touchedTiles[row + count][column] = gameBoard[row + count][column];
          // System.out.println("druntersucces");
          count++;
        } else {
          drunter = false;
        }
      }

      count = 1;

      while ((column + count < 15) && column + count <= 14 && rechts) {
        if (gameBoard[row][column + count] != null) {
          touchedTiles[row][column + count] = gameBoard[row][column + count];
          // System.out.println("rechts succes");
          count++;
        } else {
          rechts = false;
        }
      }

      count = 1;

      while ((column - count >= 0) && column - count >= 0 && links) {
        if (gameBoard[row][column - count] != null) {
          touchedTiles[row][column - count] = gameBoard[row][column - count];
          // System.out.println("links succes");
          count++;
        } else {
          links = false;
        }
      }


    }

    return touchedTiles;
  }


  /**
   * Returns the Value of Tile at the given coordinates of the GameBoard
   *
   * @param row
   * @param column
   * @return
   */
  public Tile getTile(int row, int column) {
    if (gameBoardCurrent[row][column] != null) {
      return gameBoardCurrent[row][column];
    }
    return null;
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
    return listOfWords;
  }

  /**
   * Checks if all the words of the Board are legit
   *
   * @return Returns if the words are legit
   * @author trohwede
   */
  public boolean checkWordsLegit() {
    ArrayList<String> gameWords = getWords();
    for (int i = 0; i < gameWords.size(); i++) {
      if (!DictionaryScanner.scan((gameWords.get(i)))) {
        return false;
      }
    }
    return true;
  }


  /**
   * Clears the board of all Tiles and sets them to null
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
  }
}

