package com.scrab5.util.database;

import org.junit.Test;

public class UseDbTest {

  /**
   * @author lengist
   * 
   *         Tests the function to return all the letters with their corresponding points that are
   *         saved in the table Server in the database.
   */
  @Test
  public void vieLettersTest() {
    /* not yet implemented */
  }

  /**
   * @author lengist
   * 
   *         Tests the function to check if the table Player is empty.
   */
  @Test
  public void tablePlayerIsEmptyTest() {
    CreateDatabase db = new CreateDatabase();
    if (UseDatabase.tablePlayerIsEmpty()) {
      System.out.println("The table player is empty");
    } else {
      System.out.println("The table player is not empty");
    }
  }

  /**
   * @author lengist
   * 
   *         Tests the function to return all players from the table Player.
   */
  @Test
  public void getAllPlayerTes() {
    /* not yet implemented */
  }

}
