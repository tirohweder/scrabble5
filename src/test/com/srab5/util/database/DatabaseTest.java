package com.scrab5.util.database;

import static org.junit.Assert.assertTrue;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.Test;

/**
 * Unit test for simple App.
 * 
 * @author lengist
 * 
 */
public class DatabaseTest {
  CreateDatabase d1, d2, d3;
  ResultSet rs1;
  
    @Before
    public void setUp() throws Exception {
      d1 = new CreateDatabase;
    }
    
    /*
     * Which tests do I need?:
     * - Tables created?
     * - fill Tables new with variables --> resultSet --> filled correctly?
     * - after flling tables --> ask for several information out of the table --> am I getting the right information?
     * - update tables and show resultset --> updaed correctly?
     * - delete Tables --> resultSet empty? --> delet
     * */
    
    public void testNewTablePlayer() {}
    public void testNewTableServer() {}
    public void testNewTableLetters() {}
    public void testInfoTablePlayer() {}
    public void testInfoTableServer() {}
    public void testInfoTableLetters() {}
    public void testUpdateTablePlayer() {}
    public void testUpdateTableServer() {}
    public void testUpdateTableLetters() {}
    public void testDeleteTablePlayer() {}
    public void testDeleteTableServer() {}
    public void testDeleteTableLetters() {}
    
    
    public void testFillTablePlayer() {
      d1.createTablePlayer();
      d1.createPlayer(Laura, null, 100, 100, 10, 5, Mama, 6, 3, 0.5, Deutsch);
      Statement stm = connection.createStatement();
      rs1 = stm.executeQuery("SELECT * FROM Player");
      while(rs.next()) {
        System.out.println("Name: " + rs.getString("Name") + ", ");
        System.out.println("Picture: " + rs.getString("Picture") + ", ");
        System.out.println("Total points: " + rs.getString("TotalPoints") + ", ");
        System.out.println("Personal highscore: " + rs.getString("PersonalHighscore") + ", ");
        System.out.println("Laid words: " + rs.getString("LaidWords") + ", ");
        System.out.println("Points per word rate: " + rs.getString("PointsPerWordRate") + ", ");
        System.out.println("Longest word: " + rs.getString("LongestWord") + ", ");
        System.out.println("Total played games: " + rs.getString("TotalPlayedGames") + ", ");
        System.out.println("Total wins: " + rs.getString("TotalWins") + ", ");
        System.out.println("win rate: " + rs.getString("WinRate") + ", ");
        System.out.println("Favorite dictionary: " + rs.getString("FaveDic") + ", ");
      }
      rs1.close();
      stm.close();
      connection.close();
    }
    
   
}}
    
}
