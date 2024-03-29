package com.scrab5.ui;

import com.scrab5.core.game.GameSession;
import com.scrab5.network.Client;
import com.scrab5.network.Server;
import com.scrab5.network.ServerData;
import java.util.ArrayList;

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
  private static boolean okayClicked = false;
  private static Client playerClient;
  private static Server playerServer;
  private static ArrayList<ServerData> serverList;
  private static int playerCountMultiplayer;
  private static boolean isSearching = false;
  private static String selectedDictionary;
  private static ArrayList<Integer> occurrencyDistribution;
  private static ArrayList<Integer> pointsDistribution;
  private static boolean hasBeenEdited = false;
  private static double sfxVolume = 1.0;
  private static StringBuffer chatHistory = new StringBuffer();
  private static GameSession gameSession;
  private static boolean endScreen = false;

  /**
   * Returns the current GameSession the player is currently playing in.
   *
   * @author nitterhe, mherre
   * @return gameSession - the current GameSession object
   */
  public static synchronized GameSession getGameSession() {
    return gameSession;
  }

  /**
   * Sets the current GameSession the player is currently playing in.
   *
   * @author nitterhe, mherre
   * @param gs - the current GameSession object
   */
  public static synchronized void setGameSession(GameSession gs) {
    gameSession = gs;
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
   * Saves which user is currently logged in.
   *
   * @param username the string containing the username which will be set
   * @author mherre
   */
  public static void setCurrentUser(String username) {
    currentUser = username;
  }

  /**
   * Returns the name of the dictionary which is currently selected.
   *
   * @return selectedDictionary the string containing the name of the dictionary
   * @author mherre
   */
  public static String getSelectedDictionary() {
    return selectedDictionary;
  }

  /**
   * Sets the name of the dictionary which is currently selected.
   *
   * @param dictionary the string containing the dictionary which will be set
   * @author mherre
   */
  public static void setSelectedDictionary(String dictionary) {
    selectedDictionary = dictionary;
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
   * Saves the text message that gets displayed on the currently opened {@link
   * com.scrab5.ui.PopUpMessage PopUpMessage}.
   *
   * @param message the string that gets set
   * @author mherre
   */
  public static void setMessage(String message) {
    popUpMessage = message;
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
   * Saves what kind of {@link com.scrab5.ui.PopUpMessage PopUpMessage} is currently opened.
   *
   * @param popUpType the PopUpMessageTyp that gets set
   * @author mherre
   */
  public static void setMessageType(PopUpMessageType popUpType) {
    messageType = popUpType;
  }

  /**
   * Returns the value of <code>confirmed</code> and thus gives information if the "Confirm" button
   * of a {@link com.scrab5.ui.PopUpMessage PopUpMessage} was clicked.
   *
   * @return confirmed the boolean representing if it was clicked or not
   * @author mherre
   */
  public static boolean isConfirmed() {
    return confirmed;
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
   * Returns the value of <code>okayClicked</code> and thus gives information if the "Okay" button
   * of a {@link com.scrab5.ui.PopUpMessage PopUpMessage} was clicked.
   *
   * @return okayClicked the boolean representing if it was clicked or not
   * @author mherre
   */
  public static boolean isOkayClicked() {
    return okayClicked;
  }

  /**
   * Saves if "Okay" of a {@link com.scrab5.ui.PopUpMessage PopUpMessage} has been clicked or not.
   *
   * @param ok the boolean that gets set
   * @author mherre
   */
  public static void setOkayClicked(boolean ok) {
    okayClicked = ok;
  }

  /**
   * Returns the text of the from a {@link com.scrab5.ui.PopUpMessage PopUpMessage}.
   *
   * @return inputFieldText the String containing the text of the textfield
   * @author mherre
   */
  public static String getInputFieldText() {
    return inputFieldText;
  }

  /**
   * Saves the text of the from a {@link com.scrab5.ui.PopUpMessage PopUpMessage}.
   *
   * @param ipft the String that gets set
   * @author mherre
   */
  public static void setInputFieldText(String ipft) {
    inputFieldText = ipft;
  }

  /**
   * Returns the Client object belonging to the player.
   *
   * @author nitterhe, mherre
   * @return playerClient - the Client object of the player for communication with the server
   */
  public static Client getPlayerClient() {
    return playerClient;
  }

  /**
   * Sets the Client object belonging to the player.
   *
   * @author nitterhe, mherre
   * @param client - the Client object of the player for communication with the server
   */
  public static void setPlayerClient(Client client) {
    if (playerClient == null) {
      playerClient = client;
    }
  }

  /**
   * Updates the Client object belonging to the player.
   *
   * @author nitterhe, mherre
   * @param client - the Client object of the player for communication with the server
   */
  public static void updatePlayerClient(Client client) {
    playerClient = client;
  }

  /**
   * Just set if you host a server, not join a server. Returns the hosted Server.
   *
   * @author nitterhe
   * @return playerServer - the hosted Server
   */
  public static Server getHostedServer() {
    return playerServer;
  }

  /**
   * Just set if you host a server, not join a server. Sets the hosted Server.
   *
   * @author nitterhe
   * @param server - the hosted Server
   */
  public static void setHostedServer(Server server) {
    if (playerServer == null) {
      playerServer = server;
    }
  }

  /**
   * Returns a list of all servers in the local network.
   *
   * @author nitterhe
   * @return serverList - all servers in the local network that were found
   */
  public static ArrayList<ServerData> getServerList() {
    return serverList;
  }

  /**
   * Sets the list of servers found in the local network..
   *
   * @author nitterhe
   * @param slist - the list of Servers that were found
   */
  public static void setServerList(ArrayList<ServerData> slist) {
    serverList = slist;
  }

  /**
   * Returns if the client is currently searching for servers.
   *
   * @author nitterhe
   * @return isSearching - boolean if client is currently searching for servers in the local network
   */
  public static boolean getIsSearching() {
    return isSearching;
  }

  /**
   * Sets the value of is searching. True is the client is currently searching for servers.
   *
   * @author nitterhe
   * @param searching - the boolean if currently searching
   */
  public static void setIsSearching(boolean searching) {
    isSearching = searching;
  }

  /**
   * Returns the current set sound effects volume.
   *
   * @author mherre
   * @return sfxVolume the double containing the volume
   */
  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  public static double getSFXVolume() {
    return sfxVolume;
  }

  /**
   * Sets the sound effects volume of the game.
   *
   * @author mherre
   * @param newVolume the double containing the new sound volume
   */
  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  public static void setSFXVolume(double newVolume) {
    sfxVolume = newVolume;
  }

  /**
   * Returns the customized occurrences.
   *
   * @author mherre
   * @return occurrencyDistribution the ArrayList containing the occurrences
   */
  public static ArrayList<Integer> getOccurrencyDistribution() {
    return occurrencyDistribution;
  }

  /**
   * Saves the customized letter occurrences.
   *
   * @author mherre
   * @param al the ArrayList that containing the whole customized occurrences
   */
  public static void setOccurrencyDistribution(ArrayList<Integer> al) {
    occurrencyDistribution = al;
  }

  /**
   * Returns the customized points Distribution.
   *
   * @author mherre
   * @return pointsDistribution the ArrayList containing the points Distribution
   */
  public static ArrayList<Integer> getPointsDistribution() {
    return pointsDistribution;
  }

  /**
   * Saves the customized letter points distribution.
   *
   * @author mherre
   * @param al the ArrayList that containing the whole customized points distribution
   */
  public static void setPointsDistribution(ArrayList<Integer> al) {
    pointsDistribution = al;
  }

  /**
   * Returns the value if the letters have been customized in any way.
   *
   * @author mherre
   * @return hasBeenEdited the boolean containing the value if it has been edited
   */
  public static boolean getHasBeenEdited() {
    return hasBeenEdited;
  }

  /**
   * Saves the information if the letters have been customized in any way.
   *
   * @author mherre
   * @param b the boolean containing the value if it has been customized
   */
  public static void setHasBeenEdited(boolean b) {
    hasBeenEdited = b;
  }

  /**
   * Returns the current amount of players in a multiplayer lobby.
   *
   * @author mherre
   * @return playerCountMultiplayer the integer containing the current amount of players
   */
  public static int getPlayerCountMultiplayer() {
    return playerCountMultiplayer;
  }

  /**
   * Sets the current amount of players in a multiplayer lobby.
   *
   * @author mherre
   * @param i the integer containing the current amount of players
   */
  public static void setPlayerCountMultiplayer(int i) {
    playerCountMultiplayer = i;
  }

  /**
   * Returns the chat history displayed in {@link com.scrab5.ui.MultiplayerLobbyController
   * MultiplayerLobby}.
   *
   * @author mherre
   * @return chatHistory the StringBuffer containing the chat history
   */
  public static StringBuffer getChatHistory() {
    return chatHistory;
  }

  /**
   * Resets the chat displayed in the UI to an empty <code>StringBuffer</code>.
   *
   * @author mherre
   */
  public static void resetChatHistroy() {
    chatHistory = new StringBuffer();
  }

  /**
   * Returns a boolean that is true if the EndGame screen has been called.
   *
   * @author nitterhe
   */
  public static boolean getEndScreen() {
    return endScreen;
  }

  /**
   * Sets a boolean that is true if the EndGame screen has been called.
   *
   * @author nitterhe
   */
  public static void setEndScreen(boolean end) {
    endScreen = end;
  }
}
