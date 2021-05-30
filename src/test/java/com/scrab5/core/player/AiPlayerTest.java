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

/**
 * All tests for AiPlayer internal methods are placed here.
 *
 * @author trohwede
 * @author lengist
 * @author hraza
 */
class AiPlayerTest {

  AiPlayer test = new AiPlayer("Hans", 0);
  CreateDatabase cd = new CreateDatabase();

  /**
   * Test for checkBagDistribution, this test is checking if the Method is working
   *
   * @author hraza
   */
  @Test
  void checkBagDistributionLegalTest() {
    HashMap<String, Integer> bagDistribution = new HashMap<>();
    bagDistribution.put("H", 1);
    bagDistribution.put("A", 1);
    bagDistribution.put("L", 0);
    bagDistribution.put("O", 1);
    assertEquals(false, AiPlayer.checkBagDistributionLegal(bagDistribution, "HALLO", "L"));
    bagDistribution.put("L", 1);
    assertEquals(false, AiPlayer.checkBagDistributionLegal(bagDistribution, "WALLO", "L"));
    bagDistribution.put("L", 3);
    assertEquals(true, AiPlayer.checkBagDistributionLegal(bagDistribution, "HALLLLO", "L"));
    assertEquals(false, AiPlayer.checkBagDistributionLegal(bagDistribution, "HALLLLLO", "L"));
    assertEquals(true, AiPlayer.checkBagDistributionLegal(bagDistribution, "WALLLO", "W"));
    assertEquals(false, AiPlayer.checkBagDistributionLegal(bagDistribution, "WWALLLO", "W"));
  }

  /**
   * Tests if the countScore for the AI works. Main class needs to be modified because, gameSession
   * is needed for this to work.
   *
   * @author trohwede
   */
  @Test
  @Disabled
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

    assertEquals(testResult, AiPlayer.countScore(possibleWords));

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
    assertEquals(scoreList, AiPlayer.countScore(tilesList));
  }

  @Test
  void wordToTilesTest() {
    cd.createTest();
    FillDatabase.fillLetters();
    Constants.lettersFromDatabase = UseDatabase.getAllLetters();
    Constants.pointsPerLetterFromDatabase = UseDatabase.getAllPointsPerLetter();
    ArrayList<Tile> tiles = AiPlayer.wordToTiles("HELLO", "H", 7, 7, true);
    assertEquals(7, tiles.get(0).getColumn());
    assertEquals(7, tiles.get(0).getRow());
    /* E */
    assertEquals(8, tiles.get(1).getColumn());
    assertEquals(7, tiles.get(1).getRow());
    /* L */
    assertEquals(9, tiles.get(2).getColumn());
    assertEquals(7, tiles.get(2).getRow());
    /* L */
    assertEquals(10, tiles.get(3).getColumn());
    assertEquals(7, tiles.get(3).getRow());
    /* O */
    assertEquals(11, tiles.get(4).getColumn());
    assertEquals(7, tiles.get(4).getRow());

    ArrayList<Tile> tiles2 = AiPlayer.wordToTiles("HELLO", "H", 7, 7, false);
    assertEquals(7, tiles2.get(0).getColumn());
    assertEquals(7, tiles2.get(0).getRow());
    /* E */
    assertEquals(7, tiles2.get(1).getColumn());
    assertEquals(8, tiles2.get(1).getRow());
    /* L */
    assertEquals(7, tiles2.get(2).getColumn());
    assertEquals(9, tiles2.get(2).getRow());
    /* L */
    assertEquals(7, tiles2.get(3).getColumn());
    assertEquals(10, tiles2.get(3).getRow());
    /* O */
    assertEquals(7, tiles2.get(4).getColumn());
    assertEquals(11, tiles2.get(4).getRow());

    ArrayList<Tile> tiles3 = AiPlayer.wordToTiles("DELETE", "D", 7, 7, true);
    assertEquals(7, tiles3.get(0).getColumn());
    assertEquals(7, tiles3.get(0).getRow());
    /* E */
    assertEquals(8, tiles3.get(1).getColumn());
    assertEquals(7, tiles3.get(1).getRow());
    /* L */
    assertEquals(9, tiles3.get(2).getColumn());
    assertEquals(7, tiles3.get(2).getRow());
    /* E */
    assertEquals(10, tiles3.get(3).getColumn());
    assertEquals(7, tiles3.get(3).getRow());
    /* T */
    assertEquals(11, tiles3.get(4).getColumn());
    assertEquals(7, tiles3.get(4).getRow());
    /* 5 */
    assertEquals(12, tiles3.get(5).getColumn());
    assertEquals(7, tiles3.get(5).getRow());

    ArrayList<Tile> tiles4 = AiPlayer.wordToTiles("HELLO", "O", 7, 9, true);
    assertEquals("H", tiles4.get(0).getLetter());
    assertEquals(3, tiles4.get(0).getColumn());
    assertEquals(9, tiles4.get(0).getRow());
    /* E */
    assertEquals("E", tiles4.get(1).getLetter());
    assertEquals(4, tiles4.get(1).getColumn());
    assertEquals(9, tiles4.get(1).getRow());
    /* L */
    assertEquals("L", tiles4.get(2).getLetter());
    assertEquals(5, tiles4.get(2).getColumn());
    assertEquals(9, tiles4.get(2).getRow());
    /* L */
    assertEquals("L", tiles4.get(3).getLetter());
    assertEquals(6, tiles4.get(3).getColumn());
    assertEquals(9, tiles4.get(3).getRow());
    /* O */
    assertEquals("O", tiles4.get(4).getLetter());
    assertEquals(7, tiles4.get(4).getColumn());
    assertEquals(9, tiles4.get(4).getRow());
  }

  /**
   * Test for getSpotsFree2. Goal was to try all basic settings plus some more special ones.
   *
   * @author hraza, trohwede
   */
  @Test
  void getSpotsFree2() {
    GameBoard g2 = new GameBoard();
    g2.placeTileForce(new Tile("A ", 1), 7, 3);
    g2.placeTileForce(new Tile("A ", 1), 7, 4);
    g2.placeTileForce(new Tile("A ", 1), 8, 3);
    g2.finishTurn();
    test.getSpotsFree2(7, 3, g2);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(0, test.counterUp);

    GameBoard g3 = new GameBoard();
    g3.placeTileForce(new Tile("A ", 1), 5, 6);
    g3.placeTileForce(new Tile("A ", 1), 5, 7);
    g3.placeTileForce(new Tile("A ", 1), 7, 6);
    g3.finishTurn();
    test.getSpotsFree2(5, 6, g3);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
    assertEquals(5, test.counterUp);

    GameBoard g4 = new GameBoard();
    g4.placeTileForce(new Tile("A ", 1), 7, 6);
    g4.placeTileForce(new Tile("A ", 1), 7, 7);
    g4.placeTileForce(new Tile("A ", 1), 7, 8);
    g4.placeTileForce(new Tile("A ", 1), 7, 9);
    g4.placeTileForce(new Tile("A ", 1), 7, 10);
    g4.placeTileForce(new Tile("A ", 1), 6, 10);
    g4.placeTileForce(new Tile("A ", 1), 8, 10);
    g4.placeTileForce(new Tile("A ", 1), 8, 7);
    g4.placeTileForce(new Tile("A ", 1), 9, 7);

    g4.finishTurn();
    test.getSpotsFree2(7, 8, g4);
    assertEquals(7, test.counterUp);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
    GameBoard g5 = new GameBoard();
    g5.placeTileForce(new Tile("A ", 1), 4, 0);
    g5.placeTileForce(new Tile("A ", 1), 3, 1);

    g5.finishTurn();
    test.getSpotsFree2(4, 0, g5);
    assertEquals(0, test.counterUp);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(10, test.counterDown);

    // GameBoard 6 = testing Borders
    GameBoard g6 = new GameBoard();

    g6.placeTileForce(new Tile("A ", 1), 0, 0);
    g6.finishTurn();
    test.getSpotsFree2(0, 0, g6);
    assertEquals(0, test.counterUp);
    assertEquals(0, test.counterLeft);
    assertEquals(14, test.counterRight);
    assertEquals(14, test.counterDown);

    g6.placeTileForce(new Tile("A ", 1), 14, 0);
    g6.finishTurn();
    test.getSpotsFree2(14, 0, g6);
    assertEquals(12, test.counterUp);
    assertEquals(0, test.counterLeft);
    assertEquals(14, test.counterRight);
    assertEquals(0, test.counterDown);

    g6.placeTileForce(new Tile("A ", 1), 0, 14);
    g6.finishTurn();
    test.getSpotsFree2(0, 14, g6);
    assertEquals(0, test.counterUp);
    assertEquals(12, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(14, test.counterDown);

    g6.placeTileForce(new Tile("A ", 1), 14, 14);
    g6.finishTurn();
    test.getSpotsFree2(14, 14, g6);
    assertEquals(12, test.counterUp);
    assertEquals(12, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);

    g6.placeTileForce(new Tile("A ", 1), 6, 4);
    g6.placeTileForce(new Tile("A ", 1), 7, 4);
    g6.placeTileForce(new Tile("A ", 1), 6, 5);
    g6.placeTileForce(new Tile("A ", 1), 6, 6);
    g6.finishTurn();
    test.getSpotsFree2(6, 5, g6);
    assertEquals(6, test.counterUp);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);

    GameBoard g7 = new GameBoard();

    g7.placeTileForce(new Tile("A ", 1), 6, 2);
    g7.placeTileForce(new Tile("A ", 1), 7, 2);
    g7.placeTileForce(new Tile("A ", 1), 5, 0);

    g7.placeTileForce(new Tile("A ", 1), 4, 2);
    g7.placeTileForce(new Tile("A ", 1), 5, 3);
    g7.placeTileForce(new Tile("A ", 1), 6, 4);
    g7.finishTurn();
    test.getSpotsFree2(6, 2, g7);
    assertEquals(0, test.counterUp);
    assertEquals(1, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);

    GameBoard g8 = new GameBoard();

    g8.placeTileForce(new Tile("A ", 1), 2, 6);
    g8.placeTileForce(new Tile("A ", 1), 2, 5);
    g8.placeTileForce(new Tile("A ", 1), 2, 7);

    g8.placeTileForce(new Tile("A ", 1), 4, 6);

    g8.finishTurn();
    test.getSpotsFree2(2, 6, g8);
    assertEquals(2, test.counterUp);
    assertEquals(0, test.counterLeft);
    assertEquals(0, test.counterRight);
    assertEquals(0, test.counterDown);
  }

  /*

  @hraza was supposed to start working on the AiPlayer by himself. After 3 weeks of time,
  we had no working code and no test classes. In those 3 weeks we were not told that he had major
  problems with working on the AI or were asked any questions. (We were told EasyAi would be
  finished after those 3 weeks.)
  But instead nothing was working, and there was no clear plan on how to create an working Ai.
  To make sure we can finish the project on time we needed to change team assignments.
  Because we need working code to play the game.

  After those 3 weeks we added @lengist, @trohwede to help with the AiPlayer. While @lengist was
  creating working methods we need for the Ai, @hraza still did not produce any working code.

  We split the Ai into different tasks. GetSpotsFree was one that was given to @hraza.
  The Function was supposed to do what getSpotsFree2 is doing. After we were told the method is
  finished. It still produced test errors. After having several days to fix the method.
  It was still failing tests, so we needed to create an alternative that would solve the problem.
  (We gave enough time for him to fix the method but still nothing worked.) We offered to help him,
  but in the end we were the ones writing the code and not just helping him. Also because we
  were so delayed because we thought the Ai would be much further along, we needed to be faster than
  normally. Where we could have properly spend more time explaining basic java concepts and basic
  git commands. (He was still unable to solve git conflicts and once told us he couldn't push code
  for 2 days because he couldn't solve the conflict. This was about 40 days into the project.)
  As no one from the group has worked with git extensively before, we all had to learn how git
  functions.

  We asked our tutor if it would be okay to start taking over the part assigned to @hraza.
  Because we wanted to make sure, that we can finish the project we have all worked on very hard.

  The date before the due date, we even noticed he didnt understand the rules of scrabble and
  could not have programmed the Ai like he told us he would. (He was asking why he cant place tiles
  randomly on the board as long it is connected to an old tile. In retrospect this makes sense after
  he programmed an countScore method for the Ai, that firstly ignored "Double Letter,
  Tripple Word, etc.". It also didnt account for the option that you are able to customize points.
  Which we talked about extensively in our meetings.

  He did put effort into the design artefacts. In the beginning he worked on the system sequence
  diagrams and architecture diagram with @lengist and finished his use case.
  For the final submission he designed the sequence diagrams on his own.

  @author lengist, nitterhe, mherre, trohwede, apilgirm

  P.S.: We are keeping the code here to show that he did something.

  */

  /*
   * @Test void getSpotsFreeTest() { GameBoard g2 = new GameBoard(); g2.placeTileTest(new
   * Tile("A ", 1), 7, 3); g2.placeTileTest(new Tile("A ", 1), 7, 4); g2.placeTileTest(new
   * Tile("A ", 1), 8, 3); g2.finishTurn(); test.getSpotsFree(3, 7, g2); assertEquals(0,
   * test.counterLeft); assertEquals(0, test.counterRight); assertEquals(0, test.counterDown);
   * assertEquals(0, test.counterUp);
   *
   * GameBoard g3 = new GameBoard(); g3.placeTileTest(new Tile("A ", 1), 5, 6); g3.placeTileTest(new
   * Tile("A ", 1), 5, 7); g3.placeTileTest(new Tile("A ", 1), 7, 6); g3.finishTurn();
   * test.getSpotsFree(6, 5, g3); assertEquals(0, test.counterLeft); assertEquals(0,
   * test.counterRight); assertEquals(0, test.counterDown); assertEquals(5, test.counterUp);
   *
   * GameBoard g4 = new GameBoard(); g4.placeTileTest(new Tile("A ", 1), 7, 6); g4.placeTileTest(new
   * Tile("A ", 1), 7, 7); g4.placeTileTest(new Tile("A ", 1), 7, 8); g4.placeTileTest(new
   * Tile("A ", 1), 7, 9); g4.placeTileTest(new Tile("A ", 1), 7, 10);
   *
   * g4.placeTileTest(new Tile("A ", 1), 6, 10); g4.placeTileTest(new Tile("A ", 1), 8, 10);
   *
   * g4.placeTileTest(new Tile("A ", 1), 8, 7); g4.placeTileTest(new Tile("A ", 1), 9, 7);
   *
   * g4.finishTurn(); test.getSpotsFree(8, 7, g4); assertEquals(0, test.counterLeft);
   * assertEquals(0, test.counterRight); assertEquals(0, test.counterDown); assertEquals(7,
   * test.counterUp);
   *
   * test.getSpotsFree(10, 8, g4); assertEquals(0, test.counterLeft); assertEquals(4,
   * test.counterRight); assertEquals(0, test.counterDown); assertEquals(0, test.counterUp);
   *
   * test.getSpotsFree(10, 7, g4); assertEquals(0, test.counterLeft); assertEquals(0,
   * test.counterRight); assertEquals(0, test.counterDown); assertEquals(0, test.counterUp);
   *
   * test.getSpotsFree(10, 6, g4); assertEquals(0, test.counterLeft); assertEquals(4,
   * test.counterRight); assertEquals(0, test.counterDown); assertEquals(0, test.counterUp);
   *
   * test.getSpotsFree(9, 7, g4); assertEquals(0, test.counterLeft); assertEquals(0,
   * test.counterRight); assertEquals(0, test.counterDown); assertEquals(0, test.counterUp);
   *
   * test.getSpotsFree(7, 7, g4); assertEquals(0, test.counterLeft); assertEquals(0,
   * test.counterRight); assertEquals(0, test.counterDown); assertEquals(0, test.counterUp);
   *
   * test.getSpotsFree(6, 7, g4); assertEquals(0, test.counterLeft); assertEquals(0,
   * test.counterRight); assertEquals(0, test.counterDown); assertEquals(7, test.counterUp);
   *
   * test.getSpotsFree(7, 8, g4); assertEquals(0, test.counterLeft); assertEquals(0,
   * test.counterRight); assertEquals(0, test.counterDown); assertEquals(0, test.counterUp);
   *
   * test.getSpotsFree(7, 9, g4); assertEquals(7, test.counterLeft); assertEquals(2,
   * test.counterRight); assertEquals(0, test.counterDown); assertEquals(0, test.counterUp);
   * GameBoard g7 = new GameBoard();
   *
   * g7.placeTileTest(new Tile("A ", 1), 6, 2); g7.placeTileTest(new Tile("A ", 1), 7, 2);
   * g7.placeTileTest(new Tile("A ", 1), 5, 0);
   *
   * g7.placeTileTest(new Tile("A ", 1), 4, 2); g7.placeTileTest(new Tile("A ", 1), 5, 3);
   * g7.placeTileTest(new Tile("A ", 1), 6, 4); g7.finishTurn(); test.getSpotsFree(2, 6, g7);
   * assertEquals(0, test.counterUp); assertEquals(1, test.counterLeft); assertEquals(0,
   * test.counterRight); assertEquals(0, test.counterDown); }
   */
}
