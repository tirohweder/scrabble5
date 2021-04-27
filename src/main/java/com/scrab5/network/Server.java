/**
 * Class to implement the server sided client-server-communication. Starts the ServerThread Class.
 * Provides methods for the server to communicate with the clients. Also provides methods to get
 * server information.
 * 
 * @author nitterhe
 */
package com.scrab5.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import com.scrab5.network.messages.Message;


public class Server {

  public final int clientPort = 50000;
  public final int serverPort = 60000;

  private final String host;
  private String ip;
  private static ServerSocket serverSocket;
  private boolean gameStart;
  private static int clientCounter = 0;
  private final int clientMaximum;

  private HashMap<String, Client> clients;
  private HashMap<Client, ServerThread> connections;

  /**
   * Construtor for the Server. Sets up the server settings by initializing the Collections and
   * creating the server socket.
   * 
   * @author nitterhe
   * @param host - the name of the server host
   * @param clientMaximum - the maximum amount of clients allowed to connect to the server
   */
  public Server(String host, int clientMaximum) {
    this.clients = new HashMap<String, Client>();
    this.connections = new HashMap<Client, ServerThread>();
    this.gameStart = false;
    this.host = host;
    this.clientMaximum = clientMaximum;
    try {
      this.ip = InetAddress.getLocalHost().getHostAddress();
      serverSocket = new ServerSocket(this.serverPort);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("error at Server constructor, seversocket failed to setup");
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
   * Method run by the Thread in the method acceptClients() above. Usually throws 1 Exception since
   * closing the sockets while the Thread is running ends in a SocketException. Could be changed
   * with a flag but there is no need since this Exception does not effect anything.
   * 
   * @author nitterhe
   */
  private synchronized void accept() {
    int i = 0;
    while (!gameStart && clientCounter < clientMaximum && !serverSocket.isClosed() && i < 5) {
      try {
        i++;
        Socket newClient = serverSocket.accept();
        ServerThread clientConnection = new ServerThread(this, newClient);
        clientConnection.start();
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error at acceptClients()");
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
   * Returns the server's status (true = in game/ false = waiting for clients)
   * 
   * @author nitterhe
   * @return gameStart - the server's status
   */
  public boolean getStatus() {
    return this.gameStart;
  }

  /**
   * Returns the ServerSocket of the server as a ServerSocket object.
   * 
   * @author nitterhe
   * @return serverSocket - the ServerSocket of the server
   */
  public ServerSocket getServerSocket() {
    return serverSocket;
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
   * Returns the maximum amount of clients allowed to connect.
   * 
   * @author nitterhe
   * @return clientMaximum- number of connected clients
   */
  public int getClientMaximum() {
    return this.clientMaximum;
  }

  /**
   * Returns the number of connected clients as an int.
   * 
   * @author nitterhe
   * @return client count - number of connected clients
   */
  public int getClientCounter() {
    return clientCounter;
  }

  /**
   * Sets clientCounter to the current size of the clients ArrayList.
   * 
   * @author nitterhe
   */
  public synchronized void setClientCount() {
    clientCounter = clients.size();
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
   * Shuts down the server by closing all connections to the clients and closing the server socket.
   * 
   * @author nitterhe
   */
  // maybe need to clear HashMaps, but i do not think so, cause new Lobby creates new Server
  public void shutDownServer() {
    for (ServerThread serverThread : connections.values()) {
      serverThread.closeConnection();
    }
    try {
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // save server to database
  }
}
