package com.scrab5.core.game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AiPositionTest {

  @Test
  public void getIndexOfMostPoints() {
    AiPosition test = new AiPosition(6,6,"T");
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
    AiPosition test = new AiPosition(6,6,"T");
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
    AiPosition test = new AiPosition(6,6,"T");
    test.add("QQQQ", 1, 1, true);
    assertEquals(40, test.getPoints(0));
  }

}
