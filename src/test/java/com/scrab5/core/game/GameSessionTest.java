package com.scrab5.core.game;

import com.scrab5.core.player.Player;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class GameSessionTest {


  @Test
  void createGameSession() {

  }

  @Test
  void initializeBag() {

  }

  @Test
  void testInitializeBag() throws SQLException {
    Player p1 = new Player("Player1");
    Player p2 = new Player("Player2");

    ArrayList<Player> test = new ArrayList<>();

    test.add(0, p1);
    test.add(0, p2);
    GameSession testGameSession = new GameSession(test);
    

  }

  @Test
  void finishTurn() {
  }

  @Test
  void endGame() {
  }

  @Test
  void giveUp() {
  }
}