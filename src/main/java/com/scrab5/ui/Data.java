package com.scrab5.ui;

public class Data {

  private static String currentUser;
  private static String popUpMessage;

  public static void setCurrentUser(String username) {
    currentUser = username;
  }

  public static String getCurrentUser() {
    return currentUser;
  }

  public static void setMessage(String message) {
    popUpMessage = message;
  }

  public static String getMessage() {
    return popUpMessage;
  }
}
