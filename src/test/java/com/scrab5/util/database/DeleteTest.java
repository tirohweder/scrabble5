package com.scrab5.util.database;

class DeleteTest {

  /**
   * Tests the deletion of the whole table "Player".
   * 
   * @author lengist
   */
  /*
   * @Test void testDeleteTable() { CreateDatabase cdb = new CreateDatabase();
   * FillDatabase.createPlayer("Laura", null); FillDatabase.deleteTable("Player"); Statement stm;
   * try { System.out.println("\n-----------------DELETE-TABLE-PLAYER-----------"); stm =
   * Database.connection.createStatement(); ResultSet rs = stm.executeQuery("SELECT * FROM Player");
   * if (!rs.next()) { System.out.println("Table Player is empty"); }
   * System.out.println("-----------------DELETED-TABLE-PLAYER-----------"); } catch (SQLException
   * e) { e.printStackTrace(); } }
   * 
   * /** Tests the deletion of a certain player in the table Player.
   * 
   * @author lengist
   */
  /*
   * @Test void testDeletePlayer() { CreateDatabase cdb = new CreateDatabase();
   * FillDatabase.createPlayer("Laura", null); FillDatabase.deletePlayer("Laura");
   * 
   * Statement stm; try { System.out.println("\n-----------------DELETE-PLAYER-Laura-----------");
   * stm = Database.connection.createStatement(); ResultSet rs =
   * stm.executeQuery("SELECT * FROM Player"); if (!rs.next()) {
   * System.out.println("Player doesn't exist"); }
   * System.out.println("-----------------DELETED-PLAYER-Laura-----------"); stm.close(); } catch
   * (SQLException e) { e.printStackTrace(); } }
   * 
   * /*
   * 
   * @Test void testDeleteServer() { fail("Not yet implemented"); }
   */

}
