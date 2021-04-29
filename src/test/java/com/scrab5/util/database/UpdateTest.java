package com.scrab5.util.database;

class UpdateTest {

  /**
   * Tests the function to change the name in the table Player.
   * 
   * @author lengist
   */
  /*
   * @Test void testUpdatePlayer() { CreateDatabase db = new CreateDatabase();
   * FillDatabase.createPlayer("Laura", "Bild"); /* update the name:
   */
  /*
   * FillDatabase.updatePlayer("Name", "Laura", "Maria", 0, 0.0); Statement stm; try {
   * System.out.println("\n---------------CHANGE-IN-PLAYER-FROM-Laura-To-Maria---------"); stm =
   * Database.connection.createStatement(); ResultSet rs = stm.executeQuery("SELECT * FROM Player");
   * while (rs.next()) { System.out.println("Name: " + rs.getString("Name") + ", ");
   * System.out.println("Picture: " + rs.getString("Picture") + ", ");
   * System.out.println("Total points: " + rs.getString("TotalPoints") + ", ");
   * System.out.println("Personal Highscore: " + rs.getString("PersonalHighscore") + ", ");
   * System.out.println("Laid words: " + rs.getString("LaidWords") + ", ");
   * System.out.println("Points per word rate: " + rs.getString("PointsPerWordRate") + ", ");
   * System.out.println("Longest word: " + rs.getString("LongestWord") + ", ");
   * System.out.println("Total played games: " + rs.getString("TotalPlayedGames") + ", ");
   * System.out.println("Total wins: " + rs.getString("TotalWins") + ", ");
   * System.out.println("Win rate: " + rs.getString("WinRate") + ", ");
   * System.out.println("Favorite dictionary: " + rs.getString("FaveDic") + ", ");
   * System.out.println("Music: " + rs.getString("Music") + ", ");
   * System.out.println("Sound Effect: " + rs.getString("SoundEffect") + ", "); }
   * System.out.println("---------------CHANGE-IN-PLAYER-FINISHED------------"); } catch
   * (SQLException e) { e.printStackTrace(); } }
   * 
   * /*
   * 
   * @Test void testUpdateServer() { fail("Not yet implemented"); }
   */

  /**
   * Tests the function to change the corresponding points to a given letter in the table letters.
   * 
   * @author lengist
   */
  /*
   * @Test void testUpdateLetters() { CreateDatabase db = new CreateDatabase();
   * FillDatabase.insertLetters("L", 4); /* updating:
   */
  /*
   * FillDatabase.updateLetters("L", 2); Statement stm; try {
   * System.out.println("\n---------------CHANGE-IN-LETTERS-FROM-4-To-2---------"); stm =
   * Database.connection.createStatement(); ResultSet rs =
   * stm.executeQuery("SELECT * FROM Letters"); while (rs.next()) { System.out.println("Letter: " +
   * rs.getString("Letter") + ", "); System.out.println("Points: " + rs.getInt("Points") + ", "); }
   * System.out.println("---------------CHANGE-IN-LETTERS-FINISHED------------"); } catch
   * (SQLException e) { e.printStackTrace(); } }
   */
}
