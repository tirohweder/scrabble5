package com.scrab5.util.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ConnectTest {

  /**
   * Tests the connection to the database file.
   * 
   * @author lengist
   */
  @Test
  void testDatabase() {
    Database db = new Database();
    db.disconnect();
  }


  /**
   * Tests the reconnection after disconnecting the database file.
   * 
   * @author lengist
   */
  @Test
  void testReconnect() {
    Database db = new Database();
    db.disconnect();
    db.reconnect();
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
