package com.scrab5.core.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class GameBoardTest {

  private GameBoard gameBoardTest = new GameBoard();


  @Test
  void placeTile() {
    gameBoardTest.placeTile(new Tile("T", 3), 6, 5);

    Tile test = gameBoardTest.getTile(6, 5);
    assertEquals("T", test.getLetter());
    assertEquals(6, test.getRow());
    assertEquals(5, test.getColumn());
    assertEquals(null, test.getRackPlace());
    assertEquals(test, gameBoardTest.getCurrentChanges().get(0));

  }

  @Test
  void isSpotFree() {
    gameBoardTest.placeTile(new Tile("T", 3), 0, 0);
    assertEquals(gameBoardTest.isSpotFree(0, 0), false);
    assertEquals(gameBoardTest.isSpotFree(1, 0), true);
  }


  @Test
  void removeTile() {
    Tile x = new Tile("T", 3);
    gameBoardTest.placeTile(x, 0, 1);
    gameBoardTest.placeTile(new Tile("T", 3), 0, 0);
    gameBoardTest.removeTile(0, 0);
    assertEquals(null, gameBoardTest.getTile(0, 0));
    assertEquals(x, gameBoardTest.getCurrentChanges().get(0));
  }

  @Test
  void isSpotNext() {
    gameBoardTest.placeTile(new Tile("T", 3), 2, 4);
    assertEquals(true, gameBoardTest.isSpotNext(3, 4));
    assertEquals(true, gameBoardTest.isSpotNext(1, 4));
    assertEquals(true, gameBoardTest.isSpotNext(2, 5));
    assertEquals(true, gameBoardTest.isSpotNext(2, 3));

    assertEquals(false, gameBoardTest.isSpotNext(3, 5));
  }

  @Test
  void isSpotInLine() {
    gameBoardTest.placeTile(new Tile("T", 3), 7, 2);
    gameBoardTest.placeTile(new Tile("T", 3), 8, 2);

    assertEquals(true, gameBoardTest.isSpotInLine(9, 2));
    assertEquals(true, gameBoardTest.isSpotInLine(6, 2));

    assertEquals(false, gameBoardTest.isSpotInLine(8, 1));

    gameBoardTest.placeTile(new Tile("T", 3), 6, 2);

    assertEquals(true, gameBoardTest.isSpotInLine(5, 2));
    assertEquals(false, gameBoardTest.isSpotInLine(4, 2));

    gameBoardTest.clearBoard();

    gameBoardTest.placeTile(new Tile("T", 3), 8, 2);
    gameBoardTest.placeTile(new Tile("T", 3), 8, 3);

    assertEquals(true, gameBoardTest.isSpotInLine(8, 1));
    assertEquals(true, gameBoardTest.isSpotInLine(8, 4));

    assertEquals(false, gameBoardTest.isSpotInLine(9, 2));

    gameBoardTest.placeTile(new Tile("T", 3), 8, 4);
    assertEquals(true, gameBoardTest.isSpotInLine(8, 5));
    assertEquals(false, gameBoardTest.isSpotInLine(8, 6));
  }

  @Test
  void isTileLegal() {

    assertEquals(true, gameBoardTest.isTileLegal(7, 7));
    assertEquals(false, gameBoardTest.isTileLegal(8, 7));

    gameBoardTest.placeTile(new Tile("T", 3), 7, 7);
    assertEquals(true, gameBoardTest.isTileLegal(7, 8));
    assertEquals(true, gameBoardTest.isTileLegal(7, 6));
    assertEquals(true, gameBoardTest.isTileLegal(6, 7));
    assertEquals(true, gameBoardTest.isTileLegal(8, 7));

    assertEquals(false, gameBoardTest.isTileLegal(10, 10));

    gameBoardTest.placeTile(new Tile("T", 3), 7, 6);
    assertEquals(true, gameBoardTest.isTileLegal(7, 5));
    assertEquals(true, gameBoardTest.isTileLegal(7, 8));

    assertEquals(false, gameBoardTest.isTileLegal(8, 9));

  }


  @Test
  void countScore() {
  }

  @Test
  void didTouchOther() {

  }

  @Test
  void getTouchedWords() {
    gameBoardTest.placeTile(new Tile("T", 3), 7, 7);
    gameBoardTest.placeTile(new Tile("W", 3), 7, 8);
    gameBoardTest.placeTile(new Tile("X", 3), 7, 9);

    gameBoardTest.finishTurn();

    gameBoardTest.placeTile(new Tile("X", 3), 6, 8);
    gameBoardTest.placeTile(new Tile("X", 3), 8, 8);

    System.out.println(gameBoardTest.getTouchedWords().toString());
  }

  @Test
  void getWords() {
    ArrayList<String> resultArray = new ArrayList<>();
    resultArray.add("TWX");
    resultArray.add("2e");
    resultArray.add("xrt");
    resultArray.add("kabb0");

    resultArray.add("TL");
    resultArray.add("123");
    resultArray.add("98");
    resultArray.add("x23");
    resultArray.add("ww");
    resultArray.add("10");

    gameBoardTest.placeTile(new Tile("T", 3), 0, 0);
    gameBoardTest.placeTile(new Tile("W", 3), 0, 1);
    gameBoardTest.placeTile(new Tile("X", 3), 0, 2);
    gameBoardTest.placeTile(new Tile("L", 3), 1, 0);

    gameBoardTest.placeTile(new Tile("9", 3), 13, 0);
    gameBoardTest.placeTile(new Tile("8", 3), 14, 0);

    gameBoardTest.placeTile(new Tile("2", 3), 0, 13);
    gameBoardTest.placeTile(new Tile("e", 3), 0, 14);

    gameBoardTest.placeTile(new Tile("1", 3), 3, 0);
    gameBoardTest.placeTile(new Tile("2", 3), 4, 0);
    gameBoardTest.placeTile(new Tile("3", 3), 5, 0);

    gameBoardTest.placeTile(new Tile("k", 3), 14, 10);
    gameBoardTest.placeTile(new Tile("a", 3), 14, 11);
    gameBoardTest.placeTile(new Tile("b", 3), 14, 12);
    gameBoardTest.placeTile(new Tile("b", 3), 14, 13);

    gameBoardTest.placeTile(new Tile("x", 3), 7, 7);
    gameBoardTest.placeTile(new Tile("r", 3), 7, 8);
    gameBoardTest.placeTile(new Tile("t", 3), 7, 9);
    gameBoardTest.placeTile(new Tile("2", 3), 8, 7);
    gameBoardTest.placeTile(new Tile("3", 3), 9, 7);

    gameBoardTest.placeTile(new Tile("0", 3), 14, 14);
    gameBoardTest.placeTile(new Tile("1", 3), 13, 14);

    gameBoardTest.placeTile(new Tile("w", 3), 5, 14);
    gameBoardTest.placeTile(new Tile("w", 3), 6, 14);

    assertEquals(resultArray, gameBoardTest.getWords());

  }

  @Test
  void checkWordsLegit() {
    gameBoardTest.placeTile(new Tile("H", 3), 0, 0);
    gameBoardTest.placeTile(new Tile("E", 3), 0, 1);
    gameBoardTest.placeTile(new Tile("L", 3), 0, 2);
    gameBoardTest.placeTile(new Tile("L", 3), 0, 3);

    assertEquals(true, gameBoardTest.checkWordsLegit());

    gameBoardTest.placeTile(new Tile("W", 3), 2, 0);
    gameBoardTest.placeTile(new Tile("E", 3), 2, 1);
    gameBoardTest.placeTile(new Tile("L", 3), 2, 2);
    gameBoardTest.placeTile(new Tile("L", 3), 2, 3);

    assertEquals(true, gameBoardTest.checkWordsLegit());

    gameBoardTest.placeTile(new Tile("X", 3), 4, 0);
    gameBoardTest.placeTile(new Tile("X", 3), 4, 1);
    gameBoardTest.placeTile(new Tile("X", 3), 4, 2);
    gameBoardTest.placeTile(new Tile("X", 3), 4, 3);

    assertEquals(false, gameBoardTest.checkWordsLegit());

  }


  @Test
  void clearBoard() {
    gameBoardTest.placeTile(new Tile("X", 3), 4, 0);
    gameBoardTest.clearBoard();

    assertEquals(null, gameBoardTest.getTile(4, 0));
  }


}