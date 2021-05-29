package com.scrab5.network;

import java.io.Serializable;

/**
 * Class to save server information in one object. Including server host, ip4 and port.
 *
 * @author nitterhe
 */
public class ServerData implements Serializable {
  private static final long serialVersionUID = 1L;

  private final String serverHost;
  private final String ip4;
  private final int port;
  private final int clientMaximum;
  private int clientCounter;
  private boolean status;

  /**
   * Constructor for ServerData object. The serverList saves server information as ServerData
   * objects.
   *
   * @author nitterhe
   * @param serverHost - The name of the server host
   * @param ip4 - The IP4Address of the server
   * @param port - The port of the server
   * @param clientCounter - the number of clients connected to the server
   * @param clientMaximum - the maximum amount of clients allowed to connect to the server
   * @param status - the server's status (true = in game/ false = waiting for clients)
   */
  public ServerData(
      String serverHost,
      String ip4,
      int port,
      int clientCounter,
      int clientMaximum,
      boolean status) {
    this.serverHost = serverHost;
    this.ip4 = ip4;
    this.port = port;
    this.clientCounter = clientCounter;
    this.clientMaximum = clientMaximum;
    this.status = status;
  }

  /**
   * Returns the server's host as a String.
   *
   * @author nitterhe
   * @return servername - the name of the host
   */
  public String getServerHost() {
    return this.serverHost;
  }

  /**
   * Returns the IP4Address of the server as a String.
   *
   * @author nitterhe
   * @return ip4 - the IP4Address as a String
   */
  public String getIp4Address() {
    return this.ip4;
  }

  /**
   * Returns the server's port as an integer.
   *
   * @author nitterhe
   * @return port - the server's port
   */
  public int getPort() {
    return this.port;
  }

  /**
   * Returns the server's counter of connected clients.
   *
   * @author nitterhe
   * @return clientCounter - the server's number of connected clients
   */
  public int getClientCounter() {
    return this.clientCounter;
  }

  /**
   * Returns the maximum of players allowed to connect to the server.
   *
   * @author nitterhe
   * @return clientMaximum - the maximum of players allowed to connect to the server
   */
  public int getClientMaximum() {
    return this.clientMaximum;
  }

  /**
   * Returns the server's current status as a boolean (true = in game/ false = waiting for clients).
   *
   * @author nitterhe
   * @return status - the server's current status
   */
  public boolean getServerStatus() {
    return this.status;
  }
}
