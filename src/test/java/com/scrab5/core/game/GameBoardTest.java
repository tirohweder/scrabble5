package com.scrab5.core.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.scrab5.util.database.DictionaryParser;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class GameBoardTest {

  private GameBoard gameBoardTest = new GameBoard();


  @Test
  void isSpotFree() {
    gameBoardTest.placeTile(new Tile("T", 3), 0, 0);
    assertEquals(gameBoardTest.isSpotFree(0, 0), false);
    assertEquals(gameBoardTest.isSpotFree(1, 0), true);
  }

  @Test
  void isSpotNextFree() {

  }

  @Test
  void isSpotInLine() {
  }

  @Test
  void isTileLegal() {
  }

  @Test
  void clearBoard() {
  }

  @Test
  void testPlaceTile() {
  }

  @Test
  void countScore() {
  }

  @Test
  void didTouchOther() {

  }


  @Test
  void getTile() {
    gameBoardTest.placeTile(new Tile("T", 3), 0, 0);
    assertEquals(gameBoardTest.getTile(0, 0), "T");
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
    DictionaryParser.setFileName("testScan.txt");
    DictionaryParser.createSearchableFile("words.txt");

    gameBoardTest.placeTile(new Tile("H", 3), 0, 0);
    gameBoardTest.placeTile(new Tile("E", 3), 0, 1);
    gameBoardTest.placeTile(new Tile("L", 3), 0, 2);
    gameBoardTest.placeTile(new Tile("L", 3), 0, 3);

    //ArrayList<String> test = gameBoardTest.getWords();
    // System.out.println(test.get(0));
    System.out.println(gameBoardTest.checkWordsLegit());

    assertEquals(true, gameBoardTest.checkWordsLegit());

    gameBoardTest.placeTile(new Tile("X", 3), 4, 0);
    gameBoardTest.placeTile(new Tile("X", 3), 4, 1);
    gameBoardTest.placeTile(new Tile("X", 3), 4, 2);
    gameBoardTest.placeTile(new Tile("X", 3), 4, 3);

    //test = gameBoardTest.getWords();
    //System.out.println(test.get(1));

    assertEquals(false, gameBoardTest.checkWordsLegit());

  }


}