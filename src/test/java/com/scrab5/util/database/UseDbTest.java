package com.scrab5.util.database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import org.junit.Test;
import javafx.collections.ObservableList;

public class UseDbTest {

  /**
   * Tests the function to return all the letters with their corresponding points that are saved in
   * the table Server in the database.
   * 
   * @author lengist
   */
  @Test
  public void vieLettersTest() throws IOException {
    FillDatabase.insertLetters("A", 4);
    FillDatabase.insertLetters("B", 1);
    FillDatabase.insertLetters("C", 2);
    ResultSet rs = UseDatabase.viewLetters();
    try {
      while (rs.next()) {
        System.out.println(rs.getString(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Tests the function to check if the table Player is empty.
   * 
   * @author lengist
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
   * Tests the function to return all players from the table Player.
   * 
   * @author lengist
   */
  @Test
  public void getAllPlayerRSTest() throws IOException {
    FillDatabase.createPlayer("Alpha", null);
    FillDatabase.createPlayer("Beta", null);

    ResultSet rs = UseDatabase.getAllPlayerRS();
    try {
      while (rs.next()) {
        System.out.println(rs.getString(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Tests the function to return all players from the table player as ObservableList.
   * 
   * @author lengist
   */
  public void getAllPlayerTest() throws IOException {
    FillDatabase.createPlayer("Gamma", null);
    FillDatabase.createPlayer("Delta", null);
    ObservableList<String> list = UseDatabase.getAllPlayer();
    Iterator<String> it = list.iterator();
    System.out.println(list);
    while (it.hasNext()) {
      System.out.println(it.next());
    }
    /* not yet implemented */
  }

}
