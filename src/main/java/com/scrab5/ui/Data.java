package com.scrab5.ui;

import java.util.ArrayList;
import com.scrab5.core.game.GameSession;
import com.scrab5.network.Client;
import com.scrab5.network.Server;
import com.scrab5.network.ServerData;

/**
 * The Data class is supposed to save temporary information that is needed between controller
 * classes.
 *
 * @author mherre
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

  public static GameSession getGameSession() {
    return gameSession;
  }

  public static void setGameSession(GameSession gameSession) {
    Data.gameSession = gameSession;
  }

  private static GameSession gameSession;

  /**
   * Saves which user is currently logged in.
   *
   * @param username the string containing the username which will be set
   * @author mherre
   */
  public static void setCurrentUser(String username) {
    currentUser = username;
  }

  /**
   * Returns the name of the user who is currently logged in.
   *
   * @return currentUser the string containing the username
   * @author mherre
   */
  public static String getCurrentUser() {
    return currentUser;
  }

  /**
   * Saves the text message that gets displayed on the currently opened
   * {@link com.scrab5.ui.PopUpMessage PopUpMessage}.
   *
   * @param message the string that gets set
   * @author mherre
   */
  public static void setMessage(String message) {
    popUpMessage = message;
  }

  /**
   * Returns the text message of the currently opened {@link com.scrab5.ui.PopUpMessage
   * PopUpMessage}.
   *
   * @return popUpMessage the string containing the message
   * @author mherre
   */
  public static String getMessage() {
    return popUpMessage;
  }

  /**
   * Saves what kind of {@link com.scrab5.ui.PopUpMessage PopUpMessage} is currently opened.
   *
   * @param popUpType the PopUpMessageTyp that gets set
   * @author mherre
   */
  public static void setMessageType(PopUpMessageType popUpType) {
    messageType = popUpType;
  }

  /**
   * Returns what kind of {@link com.scrab5.ui.PopUpMessage PopUpMessage} is opened right now.
   *
   * @return messageType the PopUpMessageType that is currently saved
   * @author mherre
   */
  public static PopUpMessageType getMessageType() {
    return messageType;
  }

  /**
   * Saves if "Confirm" of a {@link com.scrab5.ui.PopUpMessage PopUpMessage} has been clicked or
   * not.
   *
   * @param cf the boolean that gets set
   * @author mherre
   */
  public static void setConfirmed(boolean cf) {
    confirmed = cf;
  }

  /**
   * Returns the value of <code>confirmed</code> and thus gives information if the "Confirm" button
   * of a {@link com.scrab5.ui.PopUpMessage PopUpMessage} was clicked.
   *
   * @return confirmed
   * @author mherre
   */
  public static boolean isConfirmed() {
    return confirmed;
  }

  /**
   * Saves the text of the {@link com.scrab5.ui.PopUpMessageController#textfield textfield} from a
   * {@link com.scrab5.ui.PopUpMessage PopUpMessage}.
   *
   * @param ipft the String that gets set
   * @author mherre
   */
  public static void setInputFieldText(String ipft) {
    inputFieldText = ipft;
  }

  /**
   * Returns the text of the {@link com.scrab5.ui.PopUpMessageController#textfield textfield} from a
   * {@link com.scrab5.ui.PopUpMessage PopUpMessage}.
   *
   * @return inputFieldText the String containing the text of the textfield
   * @author mherre
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
    }
  }

  public static void updatePlayerClient(Client client) {
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
   * @return
   * @author nitterhe
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
