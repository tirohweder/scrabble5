package com.scrab5.core.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.scrab5.core.game.GameBoard;
import com.scrab5.core.game.Tile;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class AiPlayerTest {

  @Test
  void checkBagDistributionLegalTest() {
    HashMap<String, Integer> bagDistribution = new HashMap<String, Integer>();
    bagDistribution.put("H", 1);
    bagDistribution.put("A", 1);
    bagDistribution.put("L", 1);
    bagDistribution.put("O", 1);
    assertEquals(false, AiPlayer.checkBagDistributionLegal(bagDistribution, "HALLO"));
    bagDistribution.put("L", 2);
    assertEquals(true, AiPlayer.checkBagDistributionLegal(bagDistribution, "HALLO"));
    bagDistribution.put("L", 3);
    assertEquals(true, AiPlayer.checkBagDistributionLegal(bagDistribution, "HALLO"));
    assertEquals(true, AiPlayer.checkBagDistributionLegal(bagDistribution, "HALLLO"));
  }

  @Test
  void getSpotsFreeTest() {
    Tile middle = new Tile("A", 1);
    Tile middle2 = new Tile("A", 1);
    GameBoard g = new GameBoard();
    g.placeTileTest(middle, 6, 6);
    g.placeTileTest(middle2, 8, 6);
    AiPlayer test = new AiPlayer("test", 0);

    test.getSpotsfree(6, 6, g);
    assertEquals(6, test.counterLeft);
    assertEquals(8, test.counterRight);
    assertEquals(1, test.counterDown);
    assertEquals(6, test.counterUp);

    test.getSpotsfree(0, 0, g);
    assertEquals(0, test.counterLeft);
    assertEquals(14, test.counterRight);
    assertEquals(14, test.counterDown);
    assertEquals(0, test.counterUp);

    test.getSpotsfree(14, 14, g);
    assertEquals(14, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(14, test.counterUp);

    test.getSpotsfree(0, 14, g);
    assertEquals(0, test.counterLeft);
    assertEquals(14, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(14, test.counterUp);

    test.getSpotsfree(14, 0, g);
    assertEquals(14, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(14, test.counterDown);
    assertEquals(0, test.counterUp);

    g.placeTileTest(new Tile("T", 3), 3, 7);
    test.getSpotsfree(6, 6, g);
    assertEquals(6, test.counterLeft);
    assertEquals(8, test.counterRight);
    assertEquals(1, test.counterDown);
    assertEquals(2, test.counterUp);

    g.removeTile(3, 7);

    g.placeTileTest(new Tile("T", 3), 3, 5);
    test.getSpotsfree(6, 6, g);
    assertEquals(6, test.counterLeft);
    assertEquals(8, test.counterRight);
    assertEquals(1, test.counterDown);
    assertEquals(2, test.counterUp);

    g.placeTileTest(new Tile("T", 3), 5, 9);
    test.getSpotsfree(6, 6, g);
    assertEquals(6, test.counterLeft);
    assertEquals(2, test.counterRight);
    assertEquals(1, test.counterDown);
    assertEquals(2, test.counterUp);
  }

  @Test
  void aiPlay() {}
}
