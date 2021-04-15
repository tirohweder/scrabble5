package com.scrab5.ui;

public class Data {

  private static String currentUser;
  private static String inputFieldText;

  private static String popUpMessage;
  private static PopUpMessageType messageType;
  private static boolean confirmed;

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

  public static void setMessageType(PopUpMessageType popUpType) {
    messageType = popUpType;
  }

  public static PopUpMessageType getMessageType() {
    return messageType;
  }

  public static void setConfirmed(boolean cf) {
    confirmed = cf;
  }

  public static boolean isConfirmed() {
    return confirmed;
  }

  public static void setInputFieldText(String ipft) {
    inputFieldText = ipft;
  }

  public static String getInputFieldText() {
    return inputFieldText;
  }
}
