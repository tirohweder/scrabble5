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
import com.scrab5.network.messages.Message;


public class Server {

  public final int clientPort = 54321;
  public final int serverPort = 61234;
  private final String host;
  private String ip;
  private ServerSocket serverSocket;
  private boolean gameStart;
  private HashMap<String, Client> clients;
  private HashMap<Client, ServerThread> connections;

  /**
   * Construtor for the Server. Sets up the server settings by initializing the Collections and
   * creating the server socket.
   * 
   * @author nitterhe
   * @param host - the name of the server host
   */
  public Server(String host) {
    this.clients = new HashMap<String, Client>();
    this.connections = new HashMap<Client, ServerThread>();
    this.gameStart = false;
    this.host = host;
    try {
      this.ip = InetAddress.getLocalHost().getHostAddress();
      serverSocket = new ServerSocket(this.serverPort);
    } catch (Exception e) {
      // requires Exception handling
    }
  }


  /**
   * Allows a maximum of 4 clients to connect to the server while the game session has not been
   * started.
   * 
   * @author nitterhe
   */
  public void acceptClients() {
    Runnable r = new Runnable() {
      public void run() {
        accept();
      }
    };
    new Thread(r).start();
  }

  /**
   * Method run by the Thread in the method acceptClients() above.
   * 
   * @author nitterhe
   */
  private void accept() {
    while (!gameStart && this.getClientCount() < 4) {
      try {
        Socket newClient = serverSocket.accept();
        ServerThread clientConnection = new ServerThread(this, newClient);
        clientConnection.start();
      } catch (Exception e) {
        System.out.println("Error");
        // requires exception handling
      }
    }
  }

  /**
   * Starts the game. No more clients can join the lobby.
   * 
   * @author nitterhe
   */
  // must be called by Game logic when game board is set up
  public void startGame() {
    this.gameStart = true;
  }

  /**
   * Ends the game. Clients can join again.
   * 
   * @author nitterhe
   */
  // must be called by Game logic after game ends
  public void endGame() {
    this.gameStart = false;
    this.acceptClients();
  }

  /**
   * Sends a message to all clients via the ServerThreads.
   * 
   * @author nitterhe
   * @param message - the message to send
   */
  public void sendMessageToAllClients(Message message) {
    try {
      for (ServerThread toClient : connections.values()) {
        toClient.sendMessageToClient(message);
      }
    } catch (Exception e) {
      // requires Exception handling
    }
  }

  /**
   * Returns the IP4Address of the server as a String.
   * 
   * @author nitterhe
   * @return ip - the IP4Address of the server
   */
  public String getIp() {
    return this.ip;
  }

  /**
   * Returns the ServerSocket of the server as a ServerSocket object.
   * 
   * @author nitterhe
   * @return serverSocket - the ServerSocket of the server
   */
  public ServerSocket getServerSocket() {
    return this.serverSocket;
  }

  /**
   * Returns the server's host as a String.
   * 
   * @author nitterhe
   * @return host - the server's host.
   */
  public String getHost() {
    return this.host;
  }

  /**
   * Returns the number of connected clients as an int.
   * 
   * @author nitterhe
   * @return client count - number of connected clients
   */
  public int getClientCount() {
    return clients.size();
  }

  /**
   * Returns all connected clients as a HashMap. Keys are the usernames as Strings and values are
   * the clients as Client objects.
   * 
   * @author nitterhe
   * @return clients - HashMap with the Strings as keys and Clients as values
   */
  public HashMap<String, Client> getClients() {
    return clients;
  }

  /**
   * Returns all connections as a HashMap. Keys are the Client objects (from the client list) and
   * values are the belonging ServerThread objects.
   * 
   * @author nitterhe
   * @return connections - HashMap with Clients as keys and ServerThreads as values
   */
  public HashMap<Client, ServerThread> getConnections() {
    return connections;
  }

  /**
   * Shuts down the server by closing all connections to the clients.
   * 
   * @author nitterhe
   */
  // maybe need to clear HashMaps, but i do not think so, cause new Lobby creates new Server
  public void shutDownServer() {
    for (ServerThread serverThread : connections.values()) {
      serverThread.closeConnection();
    }
  }
}
