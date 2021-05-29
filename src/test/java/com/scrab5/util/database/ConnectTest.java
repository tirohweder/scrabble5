package com.scrab5.util.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


/**
 * This class tests the methods of Database.java for the setting of a connection to the local
 * database file, the re-connection to a existing database and the function to check if a local
 * database already exists. The method connect() is tested by testDatabase(). Note: In the methods
 * with access to the database the connection gets established and disconnect individually for each
 * method. Because of that every test method needs to do so too.
 * 
 * @author lengist
 */
class ConnectTest {

  /**
   * Tests the connection to the database file.
   * 
   * @author lengist
   */
  @Test
  void testDatabase() {
    Database db = new Database();
    Database.disconnect();
  }


  /**
   * Tests the reconnection after disconnecting the database file.
   * 
   * @author lengist
   */
  @Test
  void testReconnect() {
    Database db = new Database();
    Database.disconnect();
    Database.reconnect();
  }


  /**
   * Tests if a database file exists. Returns true if so.
   * 
   * @author lengist
   */
  @Test
  void testDatabaseExistance() {
    assertEquals(true, Database.databaseExistance());
  }
}
