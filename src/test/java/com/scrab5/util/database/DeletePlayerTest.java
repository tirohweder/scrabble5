package com.scrab5.util.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

class DeletePlayerTest {

  /**
   * @author lengist
   * 
   *         Tests the deletion of a certain player in the table Player.
   */
  @Test
  void testDeletion() {
    Database db = new Database();

    FillDatabase.createPlayer("Laura", "Bild");
    FillDatabase.deletePlayer("Laura");
    Statement stm;
    try {
      System.out.println("\n-----------------DELETE-PLAYER-Laura-----------");
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Player");
      if (!rs.next()) {
        System.out.println("Player doesn't exist");
      }
      System.out.println("-----------------DELETED-PLAYER-Laura-----------");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
   * @author lengist
   * 
   *         Tests the function to check if the table Player is empty.
   */
  @Test
  public void tablePlayerIsEmptyTest() {
    CreateDatabase db = new CreateDatabase();
    if (FillDatabase.tablePlayerIsEmpty()) {
      System.out.println("The table player is empty");
    } else {
      System.out.println("The table player is not empty");
    }
  }


}
