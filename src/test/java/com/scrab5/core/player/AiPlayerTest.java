package com.scrab5.core.player;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.scrab5.core.game.BagOfTiles;
import com.scrab5.core.game.GameBoard;
import com.scrab5.core.game.Tile;

class AiPlayerTest {

   
  @Test
  void sortPosMinTest() {
    
  }
  
  
  @Test
  void sortPosMaxTest() {
    
  }
  
  @Test 
  void getSpotsFree() {
    Tile middle = new Tile("A",1);
    Tile middle2= new Tile("A",1);
    GameBoard g= new GameBoard();
    g.placeTileTest(middle, 6, 6);
    g.placeTileTest(middle2, 8, 6);
    AiPlayer test = new AiPlayer("test");
    
    test.getSpotsfree(6, 6, g);
    assertEquals(6,test.getCounterLeft());
    assertEquals(8,test.getCounterRight());
    assertEquals(1,test.getCounterDown());
    assertEquals(6,test.getCounterUp());

    test.getSpotsfree(0, 0, g);
    assertEquals(0,test.getCounterLeft());
    assertEquals(14,test.getCounterRight());
    assertEquals(14,test.getCounterDown());
    assertEquals(0,test.getCounterUp());
    
    test.getSpotsfree(14, 14, g);
    assertEquals(14,test.getCounterLeft());
    assertEquals(0,test.getCounterRight());
    assertEquals(0,test.getCounterDown());
    assertEquals(14,test.getCounterUp());
    
    test.getSpotsfree(0, 14, g);
    assertEquals(0,test.getCounterLeft());
    assertEquals(14,test.getCounterRight());
    assertEquals(0,test.getCounterDown());
    assertEquals(14,test.getCounterUp());
    
    test.getSpotsfree(14, 0, g);
    assertEquals(14,test.getCounterLeft());
    assertEquals(0,test.getCounterRight());
    assertEquals(14,test.getCounterDown());
    assertEquals(0,test.getCounterUp());   
  }

}
