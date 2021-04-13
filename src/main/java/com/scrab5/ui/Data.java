package com.scrab5.ui;

public class Data {

  private static String currentUser;

  public static void setCurrentUser(String username) {
    currentUser = username;
  }

  public static String getCurrentUser() {
    return currentUser;
  }
}
