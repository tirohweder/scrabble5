package com.scrab5.ui;

import java.util.ArrayList;
import com.scrab5.network.Client;
import com.scrab5.network.Server;
import com.scrab5.network.ServerData;

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
  private static ArrayList<ServerData> serverList;
  private static int playerCountMultiplayer;

  private static ArrayList<Integer> occurrencyDistribution;
  private static ArrayList<Integer> pointsDistribution;
  private static boolean hasBeenEdited = false;

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

  public synchronized static Client getPlayerClient() {
    return playerClient;
  }

  // used when joingin or leaving lobbies, not UI
  public synchronized static void setPlayerClient(Client client) {
    if (playerClient == null)
      playerClient = client;
  }

  // used for UI
  public synchronized static void updatePlayerClient(Client client) {
    playerClient = client;
  }

  public static void setHostedServer(Server server) {
    if (playerServer == null) {
      playerServer = server;
    }
  }

  /**
   * Just set if you host a server, not join a server.
   * 
   * @author nitterhe
   * @return
   */
  public static Server getHostedServer() {
    return playerServer;
  }

  public static ArrayList<ServerData> getServerList() {
    return serverList;
  }

  public static void setServerList(ArrayList<ServerData> slist) {
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

  public static void setOccurrencyDistribution(ArrayList<Integer> al) {
    occurrencyDistribution = al;
  }

  public static ArrayList<Integer> getOccurrencyDistribution() {
    return occurrencyDistribution;
  }

  public static void setPointsDistribution(ArrayList<Integer> al) {
    pointsDistribution = al;
  }

  public static ArrayList<Integer> getPointsDistribution() {
    return pointsDistribution;
  }

  public static void setHasBeenEdited(boolean b) {
    hasBeenEdited = b;
  }

  public static boolean getHasBeenEdited() {
    return hasBeenEdited;
  }

  public static void setPlayerCountMultiplayer(int i) {
    playerCountMultiplayer = i;
  }

  public static int getPlayerCountMultiplayer() {
    return playerCountMultiplayer;
  }



}
