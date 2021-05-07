/**
 * Class for messages update every client's lobby.
 * 
 * @author nitterhe
 */
package com.scrab5.network.messages;

import java.util.HashMap;
import com.scrab5.network.ClientData;

public class LobbyUpdateMessage extends Message {
  private static final long serialVersionUID = 1L;

  private boolean gameStart;
  private HashMap<String, ClientData> clients;
  private int clientMaximum;

  /**
   * Constructor for creating LobbyUpdateMessages
   * 
   * @author nitterhe
   * @param sender - the LobbyUpdateMessage sender (always server's host)
   */
  public LobbyUpdateMessage(String sender, boolean gameStart, HashMap<String, ClientData> clients,
      int clientMaximum) {
    super(sender);
    this.type = MessageType.LOBBYUPDATE;
    this.gameStart = gameStart;
    this.clients = clients;
    this.clientMaximum = clientMaximum;
  }

  /**
   * Returns the server's gameStart boolean.
   * 
   * @author nitterhe
   * @return gameStart - boolean if server is ingame (true=in game/false = waiting for clients)
   */
  public boolean getGameStart() {
    return this.gameStart;
  }

  /**
   * Returns the server's client overview.
   * 
   * @author nitterhe
   * @return client - object of the connecting client
   */
  public HashMap<String, ClientData> getClients() {
    return this.clients;
  }

  /**
   * Returns the server's maximum amount of clients allowed to connect.
   * 
   * @author nitterhe
   * @return clientMaximum - the maximum amount of clients allowed to connect to the server
   */
  public int getClientMaximum() {
    return this.clientMaximum;
  }
}
