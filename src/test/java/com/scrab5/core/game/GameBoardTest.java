package com.scrab5.core.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.scrab5.util.parser.DictionaryParser;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class GameBoardTest {

  private final GameBoard gameBoardTest = new GameBoard();


  @Test
  void placeTileTest() {
    gameBoardTest.placeTileTest(new Tile("T", 3), 6, 5);

    Tile test = gameBoardTest.getTile(6, 5);
    assertEquals("T", test.getLetter());
    assertEquals(6, test.getRow());
    assertEquals(5, test.getColumn());
    assertNull(test.getRackPlace());
    assertEquals(test, gameBoardTest.getCurrentChanges().get(0));

  }

  @Test
  void isSpotFree() {
    gameBoardTest.placeTileTest(new Tile("T", 3), 0, 0);
    assertFalse(gameBoardTest.isSpotFree(0, 0));
    assertTrue(gameBoardTest.isSpotFree(1, 0));
  }


  @Test
  void removeTile() {
    Tile x = new Tile("T", 3);
    gameBoardTest.placeTileTest(x, 0, 1);
    gameBoardTest.placeTileTest(new Tile("T", 3), 0, 0);
    gameBoardTest.removeTile(0, 0);
    assertNull(gameBoardTest.getTile(0, 0));
    assertEquals(x, gameBoardTest.getCurrentChanges().get(0));

    gameBoardTest.placeTileTest(new Tile("T", 3), 0, 0);
    gameBoardTest.finishTurn();

    assertFalse(gameBoardTest.removeTile(0, 0));

  }

  @Test
  void isSpotNext() {
    gameBoardTest.placeTileTest(new Tile("T", 3), 2, 4);
    assertEquals(true, gameBoardTest.isSpotNext(3, 4));
    assertTrue(gameBoardTest.isSpotNext(1, 4));
    assertTrue(gameBoardTest.isSpotNext(2, 5));
    assertTrue(gameBoardTest.isSpotNext(2, 3));

    assertFalse(gameBoardTest.isSpotNext(3, 5));

    gameBoardTest.placeTileTest(new Tile("T", 3), 3, 4);
    gameBoardTest.placeTileTest(new Tile("T", 3), 4, 4);
    gameBoardTest.placeTileTest(new Tile("T", 3), 5, 4);

    gameBoardTest.finishTurn();

    gameBoardTest.placeTileTest(new Tile("T", 3), 1, 4);
    //gameBoardTest.placeTileTest(new Tile("T", 3), 6, 4);

    assertTrue(gameBoardTest.isSpotNext(6, 4));
    assertTrue(gameBoardTest.isSpotNext(0, 4));
    assertTrue(gameBoardTest.isSpotNext(1, 3));
    assertTrue(gameBoardTest.isSpotNext(1, 5));

    assertFalse(gameBoardTest.isSpotNext(1, 6));
    assertFalse(gameBoardTest.isSpotNext(1, 2));
    assertFalse(gameBoardTest.isSpotNext(8, 4));
    assertFalse(gameBoardTest.isSpotNext(5, 3));


  }

  @Test
  void isSpotInLine() {
    gameBoardTest.placeTileTest(new Tile("T", 3), 7, 2);
    gameBoardTest.placeTileTest(new Tile("T", 3), 8, 2);

    assertTrue(gameBoardTest.isSpotInLine(9, 2));
    assertTrue(gameBoardTest.isSpotInLine(6, 2));

    assertFalse(gameBoardTest.isSpotInLine(8, 1));

    gameBoardTest.placeTileTest(new Tile("T", 3), 6, 2);

    assertTrue(gameBoardTest.isSpotInLine(5, 2));
    assertFalse(gameBoardTest.isSpotInLine(4, 2));

    gameBoardTest.clearBoard();

    gameBoardTest.placeTileTest(new Tile("T", 3), 8, 2);
    gameBoardTest.placeTileTest(new Tile("T", 3), 8, 3);

    assertTrue(gameBoardTest.isSpotInLine(8, 1));
    assertTrue(gameBoardTest.isSpotInLine(8, 4));

    assertFalse(gameBoardTest.isSpotInLine(9, 2));

    gameBoardTest.placeTileTest(new Tile("T", 3), 8, 4);
    assertTrue(gameBoardTest.isSpotInLine(8, 5));
    assertFalse(gameBoardTest.isSpotInLine(8, 6));

    gameBoardTest.finishTurn();

    gameBoardTest.placeTileTest(new Tile("T", 3), 8, 1);
    gameBoardTest.placeTileTest(new Tile("T", 3), 8, 5);

    assertTrue(gameBoardTest.isSpotInLine(8, 0));
    assertTrue(gameBoardTest.isSpotInLine(8, 5));

    assertFalse(gameBoardTest.isSpotInLine(8, 7));
    gameBoardTest.placeTileTest(new Tile("T", 3), 8, 5);
    assertTrue(gameBoardTest.isSpotInLine(8, 6));
  }

  @Test
  void isTileLegal() {

    assertTrue(gameBoardTest.isTileLegal(7, 7));
    assertFalse(gameBoardTest.isTileLegal(8, 7));

    gameBoardTest.placeTileTest(new Tile("T", 3), 7, 7);
    assertTrue(gameBoardTest.isTileLegal(7, 8));
    assertTrue(gameBoardTest.isTileLegal(7, 6));
    assertTrue(gameBoardTest.isTileLegal(6, 7));
    assertTrue(gameBoardTest.isTileLegal(8, 7));

    assertFalse(gameBoardTest.isTileLegal(10, 10));

    gameBoardTest.placeTileTest(new Tile("T", 3), 7, 6);
    assertTrue(gameBoardTest.isTileLegal(7, 5));
    assertTrue(gameBoardTest.isTileLegal(7, 8));

    assertFalse(gameBoardTest.isTileLegal(8, 9));

  }


  @Test
  void countScore() {
    Tile t1 = new Tile("1", 3);
    Tile t2 = new Tile("2", 3);
    Tile t3 = new Tile("3", 3);
    Tile t4 = new Tile("4", 3);
    Tile t5 = new Tile("5", 3);

    gameBoardTest.placeTileTest(t1, 7, 7);
    gameBoardTest.placeTileTest(t2, 7, 8);
    gameBoardTest.placeTileTest(t3, 7, 9);
    gameBoardTest.placeTileTest(t4, 5, 6);
    gameBoardTest.placeTileTest(t5, 5, 7);

    gameBoardTest.finishTurn();

    Tile t6 = new Tile("6", 3);
    Tile t7 = new Tile("7", 3);
    Tile t8 = new Tile("8", 3);
    gameBoardTest.placeTileTest(t5, 5, 8);
    gameBoardTest.placeTileTest(t7, 6, 8);
    gameBoardTest.placeTileTest(t8, 8, 8);

    assertEquals(27, gameBoardTest.countScore());

    gameBoardTest.clearBoard();

    gameBoardTest.placeTileTest(t1, 13, 0);
    gameBoardTest.placeTileTest(t2, 13, 1);
    gameBoardTest.placeTileTest(t3, 13, 2);
    gameBoardTest.placeTileTest(t4, 13, 3);
    gameBoardTest.placeTileTest(t5, 13, 4);
    gameBoardTest.placeTileTest(t6, 13, 5);

    assertEquals(48, gameBoardTest.countScore());

    gameBoardTest.clearBoard();

    gameBoardTest.placeTileTest(t7, 0, 0);
    gameBoardTest.placeTileTest(t8, 0, 1);
    assertEquals(18, gameBoardTest.countScore());

    gameBoardTest.clearBoard();

    gameBoardTest.placeTileTest(t1, 0, 14);
    gameBoardTest.placeTileTest(t2, 1, 14);
    gameBoardTest.placeTileTest(t3, 2, 14);
    gameBoardTest.placeTileTest(t4, 3, 14);
    gameBoardTest.placeTileTest(t5, 4, 14);
    gameBoardTest.placeTileTest(t6, 5, 14);

    assertEquals(63, gameBoardTest.countScore());
    gameBoardTest.placeTileTest(t7, 6, 14);

    assertEquals(122, gameBoardTest.countScore());

  }


  @Test
  void getTouchedWords() {
    Tile t1 = new Tile("1", 3);
    Tile t2 = new Tile("2", 3);
    Tile t3 = new Tile("3", 3);

    Tile t4 = new Tile("4", 3);
    Tile t5 = new Tile("5", 3);

    gameBoardTest.placeTileTest(t1, 7, 7);
    gameBoardTest.placeTileTest(t2, 7, 8);
    gameBoardTest.placeTileTest(t3, 7, 9);

    gameBoardTest.placeTileTest(t4, 5, 6);
    gameBoardTest.placeTileTest(t5, 5, 7);

    gameBoardTest.finishTurn();

    Tile t6 = new Tile("6", 3);
    Tile t7 = new Tile("7", 3);
    Tile t8 = new Tile("8", 3);
    gameBoardTest.placeTileTest(t6, 5, 8);
    gameBoardTest.placeTileTest(t7, 6, 8);
    gameBoardTest.placeTileTest(t8, 8, 8);

    Tile[][] test = new Tile[15][15];
    test[5][6] = t4;
    test[5][7] = t5;
    test[5][8] = t6;

    test[6][8] = t7;
    test[7][8] = t2;
    test[8][8] = t8;
    Tile[][] result = gameBoardTest.getTouchedWords();
    assertEquals(test[5][6].getLetter(), result[5][6].getLetter());
    assertEquals(test[5][7].getLetter(), result[5][7].getLetter());
    assertEquals(test[5][8].getLetter(), result[5][8].getLetter());

    assertEquals(test[6][8].getLetter(), result[6][8].getLetter());
    assertEquals(test[7][8].getLetter(), result[7][8].getLetter());
    assertEquals(test[8][8].getLetter(), result[8][8].getLetter());

    // assertEquals("456", gameBoardTest.getWords().get(0).toString());
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

    gameBoardTest.placeTileTest(new Tile("T", 3), 0, 0);
    gameBoardTest.placeTileTest(new Tile("W", 3), 0, 1);
    gameBoardTest.placeTileTest(new Tile("X", 3), 0, 2);
    gameBoardTest.placeTileTest(new Tile("L", 3), 1, 0);

    gameBoardTest.placeTileTest(new Tile("9", 3), 13, 0);
    gameBoardTest.placeTileTest(new Tile("8", 3), 14, 0);

    gameBoardTest.placeTileTest(new Tile("2", 3), 0, 13);
    gameBoardTest.placeTileTest(new Tile("e", 3), 0, 14);

    gameBoardTest.placeTileTest(new Tile("1", 3), 3, 0);
    gameBoardTest.placeTileTest(new Tile("2", 3), 4, 0);
    gameBoardTest.placeTileTest(new Tile("3", 3), 5, 0);

    gameBoardTest.placeTileTest(new Tile("k", 3), 14, 10);
    gameBoardTest.placeTileTest(new Tile("a", 3), 14, 11);
    gameBoardTest.placeTileTest(new Tile("b", 3), 14, 12);
    gameBoardTest.placeTileTest(new Tile("b", 3), 14, 13);

    gameBoardTest.placeTileTest(new Tile("x", 3), 7, 7);
    gameBoardTest.placeTileTest(new Tile("r", 3), 7, 8);
    gameBoardTest.placeTileTest(new Tile("t", 3), 7, 9);
    gameBoardTest.placeTileTest(new Tile("2", 3), 8, 7);
    gameBoardTest.placeTileTest(new Tile("3", 3), 9, 7);

    gameBoardTest.placeTileTest(new Tile("0", 3), 14, 14);
    gameBoardTest.placeTileTest(new Tile("1", 3), 13, 14);

    gameBoardTest.placeTileTest(new Tile("w", 3), 5, 14);
    gameBoardTest.placeTileTest(new Tile("w", 3), 6, 14);

    assertEquals(resultArray, gameBoardTest.getWords());

  }

  @Test
  void checkWordsLegit() {
    DictionaryParser.setCurrentDictionary("Built-In Standard Dictionary");
    gameBoardTest.placeTileTest(new Tile("H", 3), 0, 0);
    gameBoardTest.placeTileTest(new Tile("E", 3), 0, 1);
    gameBoardTest.placeTileTest(new Tile("L", 3), 0, 2);
    gameBoardTest.placeTileTest(new Tile("L", 3), 0, 3);

    assertTrue(gameBoardTest.checkWordsLegit());

    gameBoardTest.placeTileTest(new Tile("W", 3), 2, 0);
    gameBoardTest.placeTileTest(new Tile("E", 3), 2, 1);
    gameBoardTest.placeTileTest(new Tile("L", 3), 2, 2);
    gameBoardTest.placeTileTest(new Tile("L", 3), 2, 3);

    assertTrue(gameBoardTest.checkWordsLegit());

    gameBoardTest.placeTileTest(new Tile("X", 3), 4, 0);
    gameBoardTest.placeTileTest(new Tile("X", 3), 4, 1);
    gameBoardTest.placeTileTest(new Tile("X", 3), 4, 2);
    gameBoardTest.placeTileTest(new Tile("X", 3), 4, 3);

    assertFalse(gameBoardTest.checkWordsLegit());


  }


  @Test
  void clearBoard() {
    gameBoardTest.placeTileTest(new Tile("X", 3), 4, 0);
    gameBoardTest.clearBoard();

    assertNull(gameBoardTest.getTile(4, 0));
  }


}
