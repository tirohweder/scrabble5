package com.scrab5.core.player;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.scrab5.core.game.GameBoard;
import com.scrab5.core.game.Tile;

class AiPlayerTest {

  
  @Test
  void sortPosMinTest() {
    Tile first = new Tile("A",1);
    Tile middle = new Tile("A",1);
    GameBoard g= new GameBoard();
    g.placeTile(first, 8, 8);
    g.placeTile(middle, 6, 6);
    g.placeTile(middle, 6, 6);
    AiPlayer test = new AiPlayer("test");
    test.getSpotsfree(0, 2, g);
    assertEquals(6,test.getCounterLeft());
    assertEquals(8,test.getCounterRight());
    assertEquals(8,test.getCounterDown());
    assertEquals(6,test.getCounterUp());
  }
  
  
  @Test
  void sortPosMaxTest() {
    
  }
  
  @Test 
  void getSpotsFree() {
    
  }
  

}
