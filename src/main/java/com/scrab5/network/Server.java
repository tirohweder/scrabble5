/**
 * Class to implement the server sided client-server-communication. Starts the ServerThread Class.
 * Provides methods for the server to communicate with the clients. Also provides methods to get
 * server information.
 * 
 * @author nitterhe
 */
package com.scrab5.network;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class Server {

  public final int clientPort = 2345;
  public final int serverPort = 1234;
  private final String host;
  private String ip;
  private ServerSocket serverSocket;
  private boolean gameStart;
  private HashMap<String, Client> players;
  private HashMap<Client, ServerThread> connections;

  /**
   * Construtor for the Server. Sets up the server settings by initializing the Collections and
   * creating the server socket.
   * 
   * @author nitterhe
   * @param host - the name of the server host
   */
  public Server(String host) {
    this.players = new HashMap<String, Client>();
    this.connections = new HashMap<Client, ServerThread>();
    this.gameStart = false;
    this.host = host;
    try {
      this.ip = InetAddress.getLocalHost().getHostAddress();
      serverSocket = new ServerSocket(this.serverPort);
      //could cause deadlock
      this.acceptClients();
    } catch (Exception e) {
      // requires Exception handling
    }
  }


  /**
   * Allows a maximum of 4 players to connect to the server while game has not been started.
   * 
   * @author nitterhe
   */
  private void acceptClients() {
    while (!gameStart && this.getPlayerCount() < 4) {
      try {
        Socket newClient = serverSocket.accept();
        ServerThread clientConnection = new ServerThread(this, newClient);
        clientConnection.start();
        /*
         * Client c = clientConnection.getClient(); players.put(c);
         * connections.put(c,clientConnection);connections.add(clientConnection);
         */
      } catch (Exception e) {
        // requires exception handling
      }
    }
  }

  /**
   * Starts the lobby. N
   */
  public void startGame() {
    this.gameStart = true;
  }

  public void endGame() {
    this.gameStart = false;
    this.acceptClients();
  }

  public String getIp() {
    return this.ip;
  }

  public String getHost() {
    return this.host;
  }

  public int getPlayerCount() {
    return players.size();
  }

  public HashMap<String, Client> getPlayers() {
    return players;
  }

  public HashMap<Client, ServerThread> getConnections() {
    return connections;
  }

  // maybe need to clear HashMaps, but i do not think so, cause new Lobby creates new Server
  public void shutDownServer() {
    for (ServerThread serverThread : connections.values()) {
      serverThread.closeConnection();

    }
  }
}
