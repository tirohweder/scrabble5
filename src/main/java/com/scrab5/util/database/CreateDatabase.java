package com.scrab5.util.database;

import java.sql.SQLException;
import java.sql.Statement;


public class CreateDatabase extends Database {

  /* requests methods to create tables */
  private void createTable() {
    createTablePlayer();
    createTableServer();
  }

  /* removes table if a re-start is intended */
  private void removeTable(String name) {
    try (Statement stm = connection.createStatement()) {
      String sql = "DROP TABLE IF EXISTS " + name;
      stm.executeUpdate(sql);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /* Generation of player table */
  private void createTablePlayer() {
    removeTable("Player");
    try (Statement stm = connection.createStatement()) {
      String sql = "CREATE TABLE Player ";
      stm.executeUpdate(sql);
      System.out.println("Table for player generated!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /* Generation of server table */
  private void createTableServer() {
    removeTable("Server");
    try (Statement stm = connection.createStatement()) {
      String sql = "";
      stm.executeUpdate(sql);
      System.out.println("Table for server generated!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /* for testing */
  /*
   * public static void main(String[] args) { CreateDatabase cdb = new CreateDatabase();
   * cdb.createTables(); cdb.disconnect(); }
   */



}
