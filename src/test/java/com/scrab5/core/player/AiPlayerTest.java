package com.scrab5.core.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.scrab5.core.game.GameBoard;
import com.scrab5.core.game.Tile;
import org.junit.jupiter.api.Test;

class AiPlayerTest {


  @Test
  void sortPosMinTest() {

  }


  @Test
  void sortPosMaxTest() {

  }

  @Test
  void getSpotsFree() {
    Tile middle = new Tile("A", 1);
    Tile middle2 = new Tile("A", 1);
    GameBoard g = new GameBoard();
    g.placeTileTest(middle, 6, 6);
    g.placeTileTest(middle2, 8, 6);
    AiPlayer test = new AiPlayer("test");

    test.getSpotsfree(6, 6, g);
    assertEquals(6, test.getCounterLeft());
    assertEquals(8, test.getCounterRight());
    assertEquals(1, test.getCounterDown());
    assertEquals(6, test.getCounterUp());

    test.getSpotsfree(0, 0, g);
    assertEquals(0, test.getCounterLeft());
    assertEquals(14, test.getCounterRight());
    assertEquals(14, test.getCounterDown());
    assertEquals(0, test.getCounterUp());

    test.getSpotsfree(14, 14, g);
    assertEquals(14, test.getCounterLeft());
    assertEquals(0, test.getCounterRight());
    assertEquals(0, test.getCounterDown());
    assertEquals(14, test.getCounterUp());

    test.getSpotsfree(0, 14, g);
    assertEquals(0, test.getCounterLeft());
    assertEquals(14, test.getCounterRight());
    assertEquals(0, test.getCounterDown());
    assertEquals(14, test.getCounterUp());

    test.getSpotsfree(14, 0, g);
    assertEquals(14, test.getCounterLeft());
    assertEquals(0, test.getCounterRight());
    assertEquals(14, test.getCounterDown());
    assertEquals(0, test.getCounterUp());

    g.placeTileTest(new Tile("T", 3), 3, 7);
    test.getSpotsfree(6, 6, g);
    assertEquals(6, test.getCounterLeft());
    assertEquals(8, test.getCounterRight());
    assertEquals(1, test.getCounterDown());
    assertEquals(2, test.getCounterUp());

    g.removeTile(3, 7);

    g.placeTileTest(new Tile("T", 3), 3, 5);
    test.getSpotsfree(6, 6, g);
    assertEquals(6, test.getCounterLeft());
    assertEquals(8, test.getCounterRight());
    assertEquals(1, test.getCounterDown());
    assertEquals(2, test.getCounterUp());

    g.placeTileTest(new Tile("T", 3), 5, 9);
    test.getSpotsfree(6, 6, g);
    assertEquals(6, test.getCounterLeft());
    assertEquals(2, test.getCounterRight());
    assertEquals(1, test.getCounterDown());
    assertEquals(2, test.getCounterUp());

  }

}
