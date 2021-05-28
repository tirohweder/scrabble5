package com.scrab5.core.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.scrab5.core.game.GameBoard;
import com.scrab5.core.game.Tile;
import com.scrab5.util.constants.Constants;
import com.scrab5.util.database.CreateDatabase;
import com.scrab5.util.database.FillDatabase;
import com.scrab5.util.database.UseDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AiPlayerTest {

  AiPlayer test = new AiPlayer("Hans", 0);
  CreateDatabase cd = new CreateDatabase();

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
    assertEquals(false, AiPlayer.checkBagDistributionLegal(bagDistribution, "WE"));
  }

  @Test
  void getSpotsFreeTest() {
    /*
    GameBoard g2 = new GameBoard();
    g2.placeTileTest(new Tile("A ", 1), 7, 3);
    g2.placeTileTest(new Tile("A ", 1), 7, 4);
    g2.placeTileTest(new Tile("A ", 1), 8, 3);
    g2.finishTurn();
    test.getSpotsFree(3, 7, g2);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(0, test.counterUp);

    GameBoard g3 = new GameBoard();
    g3.placeTileTest(new Tile("A ", 1), 5, 6);
    g3.placeTileTest(new Tile("A ", 1), 5, 7);
    g3.placeTileTest(new Tile("A ", 1), 7, 6);
    g3.finishTurn();
    test.getSpotsFree(6, 5, g3);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(5, test.counterUp);

    GameBoard g4 = new GameBoard();
    g4.placeTileTest(new Tile("A ", 1), 7, 6);
    g4.placeTileTest(new Tile("A ", 1), 7, 7);
    g4.placeTileTest(new Tile("A ", 1), 7, 8);
    g4.placeTileTest(new Tile("A ", 1), 7, 9);
    g4.placeTileTest(new Tile("A ", 1), 7, 10);

    g4.placeTileTest(new Tile("A ", 1), 6, 10);
    g4.placeTileTest(new Tile("A ", 1), 8, 10);

    g4.placeTileTest(new Tile("A ", 1), 8, 7);
    g4.placeTileTest(new Tile("A ", 1), 9, 7);

    g4.finishTurn();
    test.getSpotsFree(8, 7, g4);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(7, test.counterUp);

    test.getSpotsFree(10, 8, g4);
    assertEquals(0, test.counterLeft);
    assertEquals(4, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(0, test.counterUp);

    test.getSpotsFree(10, 7, g4);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(0, test.counterUp);

    test.getSpotsFree(10, 6, g4);
    assertEquals(0, test.counterLeft);
    assertEquals(4, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(0, test.counterUp);

    test.getSpotsFree(9, 7, g4);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(0, test.counterUp);

    test.getSpotsFree(7, 7, g4);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(0, test.counterUp);

    test.getSpotsFree(6, 7, g4);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(7, test.counterUp);

    test.getSpotsFree(7, 8, g4);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(0, test.counterUp);

    test.getSpotsFree(7, 9, g4);
    assertEquals(7, test.counterLeft);
    assertEquals(2, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(0, test.counterUp);
    GameBoard g7 = new GameBoard();

    g7.placeTileTest(new Tile("A ", 1), 6, 2);
    g7.placeTileTest(new Tile("A ", 1), 7, 2);
    g7.placeTileTest(new Tile("A ", 1), 5, 0);

    g7.placeTileTest(new Tile("A ", 1), 4, 2);
    g7.placeTileTest(new Tile("A ", 1), 5, 3);
    g7.placeTileTest(new Tile("A ", 1), 6, 4);
    g7.finishTurn();
    test.getSpotsFree(2, 6, g7);
    assertEquals(0, test.counterUp);
    assertEquals(1, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);*/
  }

  /**
   * C
   *
   * @author trohwede
   */
  @Test
  void countScore() {
    /*
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

     assertEquals(testResult, AiPlayer.countScore(new GameBoard(), possibleWords));

     ArrayList<ArrayList<Tile>> tilesList = new ArrayList<ArrayList<Tile>>();
     Tile t1 = new Tile("H", 1, 7, 6);
     Tile t2 = new Tile("E", 1, 7, 7);
     Tile t3 = new Tile("L", 1, 7, 8);
     Tile t4 = new Tile("L", 1, 7, 9);
     Tile t5 = new Tile("O", 1, 7, 10);
     Tile w2 = new Tile("O", 1, 8, 6);
     Tile w3 = new Tile("W", 1, 9, 6);

     ArrayList<Tile> hello = new ArrayList<Tile>();
     hello.add(t1);
     hello.add(t2);
     hello.add(t3);
     hello.add(t4);
     hello.add(t5);

     ArrayList<Tile> how = new ArrayList<Tile>();
     how.add(t1);
     how.add(w2);
     how.add(w3);

     tilesList.add(hello);
     tilesList.add(how);
     ArrayList<Integer> scoreList = new ArrayList<Integer>();
     scoreList.add(10);
     scoreList.add(4);
     assertEquals(scoreList, AiPlayer.countScore(new GameBoard(), tilesList));

    */
  }

  @Test
  void aiPlay() {}

  @Disabled
  @Test
  void wordToTilesTest() {
    cd.createTest();
    FillDatabase.fillLetters();
    Constants.lettersFromDatabase = UseDatabase.getAllLetters();
    Constants.pointsPerLetterFromDatabase = UseDatabase.getAllPointsPerLetter();
    ArrayList<Tile> tiles = test.wordToTiles("HELLO", "H", 7, 7, true);
    assertEquals(7, tiles.get(0).getColumn());
    assertEquals(7, tiles.get(0).getRow());
    /*E*/
    assertEquals(8, tiles.get(1).getColumn());
    assertEquals(7, tiles.get(1).getRow());
    /*L*/
    assertEquals(9, tiles.get(2).getColumn());
    assertEquals(7, tiles.get(2).getRow());
    /*L*/
    assertEquals(10, tiles.get(3).getColumn());
    assertEquals(7, tiles.get(3).getRow());
    /*O*/
    assertEquals(11, tiles.get(4).getColumn());
    assertEquals(7, tiles.get(4).getRow());

    ArrayList<Tile> tiles2 = test.wordToTiles("HELLO", "H", 7, 7, false);
    assertEquals(7, tiles2.get(0).getColumn());
    assertEquals(7, tiles2.get(0).getRow());
    /*E*/
    assertEquals(7, tiles2.get(1).getColumn());
    assertEquals(8, tiles2.get(1).getRow());
    /*L*/
    assertEquals(7, tiles2.get(2).getColumn());
    assertEquals(9, tiles2.get(2).getRow());
    /*L*/
    assertEquals(7, tiles2.get(3).getColumn());
    assertEquals(10, tiles2.get(3).getRow());
    /*O*/
    assertEquals(7, tiles2.get(4).getColumn());
    assertEquals(11, tiles2.get(4).getRow());

    ArrayList<Tile> tiles3 = test.wordToTiles("DELETE", "D", 7, 7, true);
    assertEquals(7, tiles3.get(0).getColumn());
    assertEquals(7, tiles3.get(0).getRow());
    /*E*/
    assertEquals(8, tiles3.get(1).getColumn());
    assertEquals(7, tiles3.get(1).getRow());
    /*L*/
    assertEquals(9, tiles3.get(2).getColumn());
    assertEquals(7, tiles3.get(2).getRow());
    /*E*/
    assertEquals(10, tiles3.get(3).getColumn());
    assertEquals(7, tiles3.get(3).getRow());
    /*T*/
    assertEquals(11, tiles3.get(4).getColumn());
    assertEquals(7, tiles3.get(4).getRow());
    /*5*/
    assertEquals(12, tiles3.get(5).getColumn());
    assertEquals(7, tiles3.get(5).getRow());

    ArrayList<Tile> tiles4 = test.wordToTiles("HELLO", "O", 7, 9, true);
    assertEquals("H", tiles4.get(0).getLetter());
    assertEquals(3, tiles4.get(0).getColumn());
    assertEquals(9, tiles4.get(0).getRow());
    /*E*/
    assertEquals("E", tiles4.get(1).getLetter());
    assertEquals(4, tiles4.get(1).getColumn());
    assertEquals(9, tiles4.get(1).getRow());
    /*L*/
    assertEquals("L", tiles4.get(2).getLetter());
    assertEquals(5, tiles4.get(2).getColumn());
    assertEquals(9, tiles4.get(2).getRow());
    /*L*/
    assertEquals("L", tiles4.get(3).getLetter());
    assertEquals(6, tiles4.get(3).getColumn());
    assertEquals(9, tiles4.get(3).getRow());
    /*O*/
    assertEquals("O", tiles4.get(4).getLetter());
    assertEquals(7, tiles4.get(4).getColumn());
    assertEquals(9, tiles4.get(4).getRow());
  }

  @Test
  void getSpotsFree2() {
    GameBoard g2 = new GameBoard();
    g2.placeTileTest(new Tile("A ", 1), 7, 3);
    g2.placeTileTest(new Tile("A ", 1), 7, 4);
    g2.placeTileTest(new Tile("A ", 1), 8, 3);
    g2.finishTurn();
    test.getSpotsFree2(7, 3, g2);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(0, test.counterUp);

    GameBoard g3 = new GameBoard();
    g3.placeTileTest(new Tile("A ", 1), 5, 6);
    g3.placeTileTest(new Tile("A ", 1), 5, 7);
    g3.placeTileTest(new Tile("A ", 1), 7, 6);
    g3.finishTurn();
    test.getSpotsFree2(5, 6, g3);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(5, test.counterUp);

    GameBoard g4 = new GameBoard();
    g4.placeTileTest(new Tile("A ", 1), 7, 6);
    g4.placeTileTest(new Tile("A ", 1), 7, 7);
    g4.placeTileTest(new Tile("A ", 1), 7, 8);
    g4.placeTileTest(new Tile("A ", 1), 7, 9);
    g4.placeTileTest(new Tile("A ", 1), 7, 10);
    g4.placeTileTest(new Tile("A ", 1), 6, 10);
    g4.placeTileTest(new Tile("A ", 1), 8, 10);
    g4.placeTileTest(new Tile("A ", 1), 8, 7);
    g4.placeTileTest(new Tile("A ", 1), 9, 7);

    g4.finishTurn();
    test.getSpotsFree2(7, 8, g4);
    assertEquals(7, test.counterUp);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
    GameBoard g5 = new GameBoard();
    g5.placeTileTest(new Tile("A ", 1), 4, 0);
    g5.placeTileTest(new Tile("A ", 1), 3, 1);

    g5.finishTurn();
    test.getSpotsFree2(4, 0, g5);
    assertEquals(0, test.counterUp);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(10, test.counterDown);

    // GameBoard 6 = testing Borders
    GameBoard g6 = new GameBoard();

    g6.placeTileTest(new Tile("A ", 1), 0, 0);
    g6.finishTurn();
    test.getSpotsFree2(0, 0, g6);
    assertEquals(0, test.counterUp);
    assertEquals(0, test.counterLeft);
    assertEquals(14, test.counterRight);
    assertEquals(14, test.counterDown);

    g6.placeTileTest(new Tile("A ", 1), 14, 0);
    g6.finishTurn();
    test.getSpotsFree2(14, 0, g6);
    assertEquals(12, test.counterUp);
    assertEquals(0, test.counterLeft);
    assertEquals(14, test.counterRight);
    assertEquals(0, test.counterDown);

    g6.placeTileTest(new Tile("A ", 1), 0, 14);
    g6.finishTurn();
    test.getSpotsFree2(0, 14, g6);
    assertEquals(0, test.counterUp);
    assertEquals(12, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(14, test.counterDown);

    g6.placeTileTest(new Tile("A ", 1), 14, 14);
    g6.finishTurn();
    test.getSpotsFree2(14, 14, g6);
    assertEquals(12, test.counterUp);
    assertEquals(12, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);

    g6.placeTileTest(new Tile("A ", 1), 6, 4);
    g6.placeTileTest(new Tile("A ", 1), 7, 4);
    g6.placeTileTest(new Tile("A ", 1), 6, 5);
    g6.placeTileTest(new Tile("A ", 1), 6, 6);
    g6.finishTurn();
    test.getSpotsFree2(6, 5, g6);
    assertEquals(6, test.counterUp);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);

    GameBoard g7 = new GameBoard();

    g7.placeTileTest(new Tile("A ", 1), 6, 2);
    g7.placeTileTest(new Tile("A ", 1), 7, 2);
    g7.placeTileTest(new Tile("A ", 1), 5, 0);

    g7.placeTileTest(new Tile("A ", 1), 4, 2);
    g7.placeTileTest(new Tile("A ", 1), 5, 3);
    g7.placeTileTest(new Tile("A ", 1), 6, 4);
    g7.finishTurn();
    test.getSpotsFree2(6, 2, g7);
    assertEquals(0, test.counterUp);
    assertEquals(1, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);

    GameBoard g8 = new GameBoard();

    g8.placeTileTest(new Tile("A ", 1), 2, 6);
    g8.placeTileTest(new Tile("A ", 1), 2, 5);
    g8.placeTileTest(new Tile("A ", 1), 2, 7);

    g8.placeTileTest(new Tile("A ", 1), 4, 6);

    g8.finishTurn();
    test.getSpotsFree2(2, 6, g8);
    assertEquals(2, test.counterUp);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
  }
}
