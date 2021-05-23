package com.scrab5.core.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class AiPositionTest {

  AiPosition test = new AiPosition(6, 6, "T");

  @Test
  public void getIndexOfMostPoints() {
    AiPosition test = new AiPosition(6, 6, "T");
    test.add("QQQQQQ", 1, 1, true);
    test.add("QQ", 1, 1, true);
    test.add("QQQ", 1, 1, true);
    test.add("QQ", 1, 1, true);
    test.add("QQQQQQQQQ", 1, 1, true);
    test.add("QQQQ", 1, 1, true);
    test.add("Q", 1, 1, true);
    test.add("QQQQQ", 1, 1, true);
    assertEquals(4, test.getIndexOfMostPoints());
  }

  @Test
  public void getIndexOfSmallestPoints() {
    AiPosition test = new AiPosition(6, 6, "T");
    test.add("QQQQQQ", 1, 1, true);
    test.add("QQ", 1, 1, true);
    test.add("QQQ", 1, 1, true);
    test.add("QQ", 1, 1, true);
    test.add("QQQQQQQQQ", 1, 1, true);
    test.add("QQQQ", 1, 1, true);
    test.add("Q", 1, 1, true);
    test.add("QQQQQ", 1, 1, true);
    assertEquals(6, test.getIndexOfSmallestPoints());
  }

  @Test
  public void calculatePointsTest() {
    AiPosition test = new AiPosition(6, 6, "T");
    test.add("QQQQ", 1, 1, true);
    assertEquals(40, test.getPoints(0));
  }


  @Test
  void countScore() {

    ArrayList<Tile> testList = new ArrayList<>();
    testList.add(new Tile("W", 4, 7, 7));
    testList.add(new Tile("E", 1, 7, 8));
    testList.add(new Tile("L", 1, 7, 9));
    testList.add(new Tile("L", 1, 7, 10));

    ArrayList<Tile> testList2 = new ArrayList<>();
    testList2.add(new Tile("H", 4, 11, 7));
    testList2.add(new Tile("I", 1, 12, 7));
    testList2.add(new Tile("P", 3, 13, 7));
    testList2.add(new Tile("P", 3, 14, 7));

    ArrayList<ArrayList<Tile>> possibleWords = new ArrayList<>();
    possibleWords.add(testList);
    possibleWords.add(testList2);

    ArrayList<Integer> testResult = new ArrayList<>();
    testResult.add(14);
    testResult.add(45);

    assertEquals(testResult, test.countScore(new GameBoard(), possibleWords));


  }
}
