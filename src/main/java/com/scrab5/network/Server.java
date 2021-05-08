/**
 * Class to implement the server sided client-server-communication. Starts the ServerThread Class.
 * Provides methods for the server to communicate with the clients. Also provides methods to get
 * server information.
 * 
 * @author nitterhe
 */
package com.scrab5.network;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import com.scrab5.network.NetworkError.NetworkErrorType;
import com.scrab5.network.messages.LobbyUpdateMessage;
import com.scrab5.network.messages.Message;


public class Server implements Serializable {
  private static final long serialVersionUID = 1L;

  public final int clientPort = 50000;
  public final int serverPort = 60000;

  private final String host;
  private String ip;
  private static ServerSocket serverSocket;
  private boolean gameStart;
  private static int clientCounter;
  private static int clientMaximum;
  private ServerStatistics serverStatistics;

  private HashMap<String, ClientData> clients;
  private HashMap<ClientData, ServerThread> connections;

  /**
   * Construtor for the Server. Sets up the server settings by initializing the Collections and
   * creating the server socket. ServerSocket only opens if this is not a Server instance that is
   * used for communication with the UI. The boolean UIServerInstance states if this Server is saved
   * as a currentServer in the Client class.
   * 
   * @author nitterhe
   * @param host - the name of the server host
   * @param clientMaximum - the maximum amount of clients allowed to connect to the server
   * @param UIServerInstance - boolean to handle if sockets must be opened
   */
  public Server(String host, int clientMaximum, boolean UIServerInstance) {
    this.clients = new HashMap<String, ClientData>();
    this.connections = new HashMap<ClientData, ServerThread>();
    this.gameStart = false;
    this.host = host;
    Server.clientMaximum = clientMaximum;
    clientCounter = 0;
    this.serverStatistics = new ServerStatistics();
    if (!UIServerInstance) {
      this.openServerSocket();
    }
  }

  public void openServerSocket() {
    try {
      this.ip = InetAddress.getLocalHost().getHostAddress();
      serverSocket = new ServerSocket(this.serverPort);
    } catch (Exception e) {
      e.printStackTrace();
      new NetworkError(NetworkErrorType.SERVERCREATION);
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
    while (!this.gameStart && Server.clientCounter < Server.clientMaximum
        && !serverSocket.isClosed()) {
      try {
        Socket newClient = serverSocket.accept();
        ServerThread clientConnection = new ServerThread(this, newClient);
        clientConnection.start();
      } catch (SocketException e) {
        // does nothing so closing the server socket does not result in a SocketException error
      } catch (Exception e) {
        // no error message?
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
    this.sendUpdateMessage();
  }

  /**
   * Ends the game. Clients can join again.
   * 
   * @author nitterhe
   */
  // must be called by Game logic after game ends
  public void endGame(String winner) {
    this.gameStart = false;
    boolean win = false;
    for (String client : this.clients.keySet()) {
      win = client.equals(winner);
      this.serverStatistics.gamePlayed(client, win);
    }
    this.sendUpdateMessage();
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
      new NetworkError(NetworkErrorType.COMMUNICATION);
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
    return Server.clientMaximum;
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
  public void updateClientCount() {
    clientCounter = clients.size();
    this.sendUpdateMessage();
  }

  /**
   * 
   */
  public void sendUpdateMessage() {
    this.sendMessageToAllClients(new LobbyUpdateMessage(this.getHost(), this.getStatus(),
        this.getClients(), this.getClientMaximum(), this.getServerStatistics()));
  }

  /**
   * Returns all connected clients as a HashMap. Keys are the usernames as Strings and values are
   * the clients as ClientData objects.
   * 
   * @author nitterhe
   * @return clients - HashMap with the Strings as keys and Clients as values
   */
  public HashMap<String, ClientData> getClients() {
    return clients;
  }

  /**
   * Returns all connections as a HashMap. Keys are the Client objects (from the client list) and
   * values are the belonging ServerThread objects.
   * 
   * @author nitterhe
   * @return connections - HashMap with Clients as keys and ServerThreads as values
   */
  public HashMap<ClientData, ServerThread> getConnections() {
    return connections;
  }

  /**
   * Returns this server's statistics as a ServerStatistics object.
   * 
   * @author nitterhe
   * @return serverStatistics - this server's ServerStatistics object
   */
  public ServerStatistics getServerStatistics() {
    return this.serverStatistics;
  }

  /**
   * Sets the serverStatistics to the given ServerStatistics instance. Only used for the
   * currentServer in the Client class to display the statistics correctly at every client.
   * 
   * @author nitterhe
   * @param serverStatistics - the new ServerStatistics object
   */
  public void setServerStatistics(ServerStatistics serverStatistics) {
    this.serverStatistics = serverStatistics;
  }

  /**
   * Shuts down the server by closing all connections to the clients and closing the server socket.
   * 
   * @author nitterhe
   */
  public void shutDownServer() {
    for (ServerThread serverThread : connections.values()) {
      serverThread.closeConnection();
    }
    this.clients.clear();
    this.connections.clear();
    this.updateClientCount();
    this.gameStart = false;
    try {
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // save server to database
  }

  /**
   * Updates the attribute gameStart with the given parameter.
   * 
   * @author nitterhe
   * @param gameStart - the new value for gameStart
   */
  public void setGameStart(boolean gameStart) {
    this.gameStart = gameStart;
  }

  /**
   * Overrides the client list.
   * 
   * @author nitterhe
   * @param clients - the new HashMap of the clients
   */
  public void setClients(HashMap<String, ClientData> clients) {
    this.clients = clients;
  }


  public void setClientMaximum(int clientMaximum) {
    Server.clientMaximum = clientMaximum;
  }
}
