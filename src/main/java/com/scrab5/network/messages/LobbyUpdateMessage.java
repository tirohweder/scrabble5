package com.scrab5.network.messages;

import com.scrab5.network.ClientData;
import com.scrab5.network.ServerStatistics;
import java.util.LinkedHashMap;

/**
 * Class for messages update every client's lobby.
 *
 * @author nitterhe
 */
public class LobbyUpdateMessage extends Message {
  private static final long serialVersionUID = 1L;

  private final boolean gameStart;
  private final String ip4;
  private final LinkedHashMap<String, ClientData> clients;
  private final int clientMaximum;
  private final ServerStatistics serverStatistics;

  /**
   * Constructor for creating LobbyUpdateMessages.
   *
   * @author nitterhe
   * @param sender - the LobbyUpdateMessage sender (always server's host)
   */
  public LobbyUpdateMessage(
      String sender,
      String ip4,
      boolean gameStart,
      LinkedHashMap<String, ClientData> clients,
      int clientMaximum,
      ServerStatistics serverStatistics) {
    super(sender);
    this.type = MessageType.LOBBYUPDATE;
    this.ip4 = ip4;
    this.gameStart = gameStart;
    this.clients = clients;
    this.clientMaximum = clientMaximum;
    this.serverStatistics = serverStatistics;
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
   * Returns the server host's IP4Address.
   *
   * @return ip4 - the IP4Address as a string.
   * @author nitterhe
   */
  public String getIp4() {
    return this.ip4;
  }

  /**
   * Returns the server's client overview.
   *
   * @author nitterhe
   * @return client - object of the connecting client
   */
  public LinkedHashMap<String, ClientData> getClients() {
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

  /**
   * Returns the server's statistics overview.
   *
   * @author nitterhe
   * @return serverStatistics - this server's ServerStatistics object
   */
  public ServerStatistics getServerStatistics() {
    return this.serverStatistics;
  }
}
