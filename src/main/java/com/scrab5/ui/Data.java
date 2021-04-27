package com.scrab5.ui;

import java.util.ArrayList;
import com.scrab5.network.Client;
import com.scrab5.network.Server;

/**
 * The Data class is supposed to save temporary information that is needed between controller
 * classes
 * 
 * @author mherre
 *
 */

public class Data {

  private static String currentUser;
  private static String inputFieldText;

  private static String popUpMessage;
  private static PopUpMessageType messageType;
  private static boolean confirmed;

  private static Client playerClient;
  private static Server playerServer;
  private static ArrayList<Client.ServerData> serverList;
  private static boolean isSearching = false;

  private static double sfxVolume = 1.0;

  /**
   * Saves which user is currently logged in.
   * 
   * @author mherre
   * @param username
   */
  public static void setCurrentUser(String username) {
    currentUser = username;
  }

  /**
   * Returns the user who is currently logged in
   * 
   * @author mherre
   * @return
   */
  public static String getCurrentUser() {
    return currentUser;
  }

  /**
   * Saves the text message that gets displayed on the currently opened PopUp message
   * 
   * @author mherre
   * @param message
   */
  public static void setMessage(String message) {
    popUpMessage = message;
  }

  /**
   * Returns the text message of the currently opened PopUp message
   * 
   * @author mherre
   * @return
   */
  public static String getMessage() {
    return popUpMessage;
  }

  /**
   * Saves the type of the currently opened PopUp message
   * 
   * @author mherre
   * @param popUpType
   */
  public static void setMessageType(PopUpMessageType popUpType) {
    messageType = popUpType;
  }

  /**
   * Returns what kind of PopUp message is opened right now
   * 
   * @author mherre
   * @return
   */
  public static PopUpMessageType getMessageType() {
    return messageType;
  }

  /**
   * Saves if a the "Confirm" button of a CONFIRMATION PopUp message was clicked
   * 
   * @author mherre
   * @param cf
   */
  public static void setConfirmed(boolean cf) {
    confirmed = cf;
  }

  /**
   * Returns the value if the "Confirm" button was clicked
   * 
   * @author mherre
   * @return
   */
  public static boolean isConfirmed() {
    return confirmed;
  }

  /**
   * Sets the text of the textfield of a INPUT PopUp message
   * 
   * @author mherre
   * @param ipft
   */
  public static void setInputFieldText(String ipft) {
    inputFieldText = ipft;
  }

  /**
   * Returns the text of the textfield of a INPUT PopUp message
   * 
   * @author mherre
   * @return
   */
  public static String getInputFieldText() {
    return inputFieldText;
  }

  public static Client getPlayerClient() {
    return playerClient;
  }

  public static void setPlayerClient(Client client) {
    if (playerClient == null) {
      playerClient = client;
    } else {
      System.out.println("ERROR");

    }
  }

  public static void setPlayerServer(Server server) {
    if (playerServer == null) {
      playerServer = server;
    } else {
      System.out.println("ErRoR");
    }
  }

  public static Server getPlayerServer() {
    return playerServer;
  }

  public static ArrayList<Client.ServerData> getServerList() {
    return serverList;
  }

  public static void setServerList(ArrayList<Client.ServerData> slist) {
    serverList = slist;
  }

  public static boolean getIsSearching() {
    return isSearching;
  }

  public static void setIsSearching(boolean searching) {
    isSearching = searching;
  }

  public static void setSFXVolume(double newVolume) {
    sfxVolume = newVolume;
  }

  public static double getSFXVolume() {
    return sfxVolume;
  }
}
