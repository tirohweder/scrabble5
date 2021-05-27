package com.scrab5.network;

import com.scrab5.network.NetworkError.NetworkErrorType;
import com.scrab5.network.messages.ChatMessage;
import com.scrab5.network.messages.DisconnectMessage;
import com.scrab5.network.messages.GetServerDataMessage;
import com.scrab5.network.messages.LobbyUpdateMessage;
import com.scrab5.network.messages.MakeTurnMessage;
import com.scrab5.network.messages.Message;
import com.scrab5.network.messages.MessageType;
import com.scrab5.network.messages.SendReadyMessage;
import com.scrab5.network.messages.SendServerDataMessage;
import com.scrab5.ui.Data;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class to implement the client sided client-server-communication. Starts the ClientThread Class.
 * Provides methods for the client to communicate with the server. Serializable so the whole Client
 * object can be sent to the Server. Also fields implementing objects of other classes are
 * Serializable therefore.
 *
 * @author nitterhe
 */

public class Client implements Serializable {

  private static final long serialVersionUID = 1L;

  public final int serverPort = 8080;
  private String ip;
  private String username;
  private ClientThread clientThread;
  private ArrayList<ServerData> serverList;
  private Server currentServer;
  private Server hostedServer;
  private boolean isReady;
  private boolean starting;

  /**
   * Implements a new Client with the given username. Constructs an empty list for all servers in
   * the network and saves the users IP4Address. CurrentServer is only used for UI communication.
   * Server constantly changes every connected client's currentServer attribute (i.e. ChatMessages,
   * new Clients joined ....).
   *
   * @param username - This is the client's name other clients will see in a MultiPlayerLobby.
   * @author nitterhe
   */
  public Client(String username) {
    this.username = username;
    serverList = new ArrayList<ServerData>();
    this.hostedServer = null; // needs connection to database
    this.isReady = false;
    this.starting = false;
    try {
      this.ip = InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {
      new NetworkError(NetworkErrorType.IP);
    }
  }

  /**
   * Hosts a server and accepts clients. Automatically joins the server. In application: starts a
   * MultiPlayerLobby. Use Case 3.1.
   *
   * @param clientMaximum - the maximum number of clients allowed to connect to the server
   * @throws Exception - an Exception that is thrown to the Controller that the server could not be
   *         hosted
   * @author nitterhe
   */
  public void hostServer(int clientMaximum) throws Exception {
    if (hostedServer == null) {
      hostedServer = new Server(this.username, clientMaximum, false);
    } else {
      hostedServer.setClientMaximum(clientMaximum);
      hostedServer.loadServerStatistics();
      hostedServer.openServerSocket();
    }
    hostedServer.acceptClients();
    try {
      connectToServer(hostedServer.getIp4());
    } catch (Exception e) {
      hostedServer.shutDownServer();
      throw e;
    }
  }


  /**
   * Searches for Servers in the local network and adds them to the serverList.
   *
   * @author from stackoverflow -
   *         https://stackoverflow.com/questions/24082077/java-find-server-in-network
   */
  public void searchServers() {
    this.serverList.clear();
    Thread t1 = new Thread(new Runnable() {
      public void run() {
        for (int j = 0; j < 256 && Data.getIsSearching(); j++) {
          for (int k = 0; k < 256 && Data.getIsSearching(); k++) {
            final String ip4 = "192.168." + j + "." + k;
            final String ip42 = "168.254." + j + "." + k;
            Thread t = new Thread(new Runnable() {
              public void run() {
                try {
                  InetAddress serverCheck = InetAddress.getByName(ip4);
                  InetAddress serverCheck2 = InetAddress.getByName(ip42);
                  if (serverCheck.isReachable(1000)) {
                    Socket getServerDataSocket = new Socket(ip4, serverPort);
                    getServerDataSocket.setSoTimeout(10000);

                    ObjectOutputStream out =
                        new ObjectOutputStream(getServerDataSocket.getOutputStream());
                    ObjectInputStream in =
                        new ObjectInputStream(getServerDataSocket.getInputStream());

                    out.writeObject(new GetServerDataMessage(username));
                    out.flush();
                    out.reset();

                    Message m = (Message) in.readObject();
                    if (m.getType() == MessageType.SENDSERVERDATA) {
                      SendServerDataMessage ssdMessage = (SendServerDataMessage) m;
                      addServerToServerList(new ServerData(ssdMessage.getSender(), ip4,
                          ssdMessage.getPort(), ssdMessage.getClientCounter(),
                          ssdMessage.getClientMaximum(), ssdMessage.getStatus()));
                    }
                    getServerDataSocket.shutdownInput();
                    getServerDataSocket.shutdownOutput();
                    getServerDataSocket.close();
                  }
                  if (serverCheck2.isReachable(1000)) {
                    Socket getServerDataSocket = new Socket(ip42, serverPort);
                    getServerDataSocket.setSoTimeout(10000);

                    ObjectOutputStream out =
                        new ObjectOutputStream(getServerDataSocket.getOutputStream());
                    ObjectInputStream in =
                        new ObjectInputStream(getServerDataSocket.getInputStream());

                    out.writeObject(new GetServerDataMessage(username));
                    out.flush();
                    out.reset();

                    Message m = (Message) in.readObject();
                    if (m.getType() == MessageType.SENDSERVERDATA) {
                      SendServerDataMessage ssdMessage = (SendServerDataMessage) m;
                      addServerToServerList(new ServerData(ssdMessage.getSender(), ip42,
                          ssdMessage.getPort(), ssdMessage.getClientCounter(),
                          ssdMessage.getClientMaximum(), ssdMessage.getStatus()));
                    }
                    getServerDataSocket.shutdownInput();
                    getServerDataSocket.shutdownOutput();
                    getServerDataSocket.close();
                  }
                } catch (Exception e) {
                  // e.printStackTrace();
                  // does nothing here
                }
              }
            });
            t.start();


            // otherwise too many Threads start at the same time
            synchronized (this) {
              try {
                this.wait(1);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }
          }
        }
      }
    });
    t1.start();
  }

  /**
   * Adds an available server to the serverList.
   *
   * @param serverdata The necessary data needed to open a socket with the server.
   * @author nitterhe
   */
  private synchronized void addServerToServerList(ServerData serverdata) {
    boolean add = true;
    for (ServerData sd : this.serverList) {
      if (sd.getServerHost().equals(serverdata.getServerHost())) {
        add = false;
      }
      break;
    }
    if (add) {
      serverList.add(serverdata);
    }
  }

  /**
   * Returns the current serverList.
   *
   * @return serverList
   * @author nitterhe
   */
  public synchronized ArrayList<ServerData> getServerList() {
    return this.serverList;
  }

  /**
   * Connects the ClientThread to the given ServerThread by starting the ClientThread. For the
   * currentServer a new Server instance is created that is updated by the server via messages. Use
   * Case 3.2.
   *
   * @param serverdata Includes the IP4Address and
   * @author nitterhe
   */
  public void connectToServer(ServerData serverdata) {
    Data.resetChatHistroy();
    this.isReady = false;
    this.clientThread = new ClientThread(this);
    this.clientThread.connectToServer(serverdata);
  }

  /**
   * Connects the ClientThread to the given IP4 Address by calling the method above with a new
   * ServerData object.
   *
   * @param ip - The ip4 which should be connected to.
   * @author nitterhe
   */
  public void connectToServer(String ip) throws Exception {
    connectToServer(new ServerData(null, ip.trim(), serverPort, 0, 0, false));
  }

  /**
   * Disconnects the ClientThread from the ServerThread. Server shuts down if the client is the
   * server's host. Use Case 3.4.
   *
   * @return boolean - used in the MulitplayerLobbyController to display a disconnect notification
   * @author nitterhe
   */
  public boolean disconnectFromServer() {
    if (this.clientThread.running) {
      this.isReady = false;
      clientThread.sendMessageToServer(new DisconnectMessage(clientThread.sender));
      return true;
    }
    return false;
  }

  /**
   * Stops the client by calling the closeConnection method of the client's thread.
   *
   * @author nitterhe
   */
  public void stopClientThread() {
    if (this.clientThread.running) {
      this.clientThread.closeConnection();
    }
  }

  public ClientData getClientData() {
    return new ClientData(this.username, this.ip, this.clientThread, isReady);
  }

  /**
   * Sends ChatMessage to the server. Server will send it to all clients in the lobby.
   *
   * @param text The message text.
   * @author nitterhe
   */
  public void sendChatMessage(String text) {
    this.clientThread.sendMessageToServer(new ChatMessage(clientThread.sender, text));
  }

  /**
   * Returns the client's username as a String.
   *
   * @return username - username of the client
   * @author nitterhe
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Returns the client's IP4Address as a String.
   *
   * @return ip - the IP4Address of the client
   * @author nitterhe
   */
  public String getIp() {
    return this.ip;
  }

  /**
   * Returns this client's ClientThread.
   *
   * @return clientThread - this client's ClientThread.
   * @author nitterhe
   */
  public ClientThread getClientThread() {
    return this.clientThread;
  }

  /**
   * Returns the client's server as a Server object. Null if no server was hosted by the client.
   *
   * @return hostedServer - the client's server
   * @author nitterhe
   */
  public Server getHostedServer() {
    return this.hostedServer;
  }

  /**
   * Returns the client's ready to play status.
   *
   * @return isReady - the client's ready status
   * @author nitterhe
   */
  public boolean isReady() {
    return this.isReady;
  }

  /**
   * Sets the client's ready status.
   *
   * @param ready - a boolean with the client's ready satus
   * @author nitterhe
   */
  public void setReady(boolean ready, ArrayList<Integer> order) {
    this.isReady = ready;
    this.clientThread.sendMessageToServer(new SendReadyMessage(this.username, ready, order));
  }

  /**
   * Used to first set an empty sever instance that is filled by the conencted server and the
   * changes are displayed by the UI.
   *
   * @param currentServer - the server that is now the new currentServer
   * @author nitterhe
   */
  public void initializeCurrentServer(Server currentServer) {
    this.currentServer = currentServer;
  }

  /**
   * Updates the current Server instance and refreshes the UI.
   *
   * @param lum - the message from the server with the updated values
   * @author nitterhe
   */
  public void updateCurrentServer(LobbyUpdateMessage lum) {
    this.getCurrentServer().setGameStart(lum.getGameStart());
    this.getCurrentServer().setClients(lum.getClients());
    this.getCurrentServer().getClientCounter();
    this.getCurrentServer().setServerStatistics(lum.getServerStatistics());
    this.getCurrentServer().setIp4(lum.getIp4());
    Data.updatePlayerClient(this);
  }

  /**
   * Returns the client's current server that the client is connected to. Used for controlling what
   * the UI shall show. CurrentServer is constantly updated by the server.
   *
   * @return currentServer - the server the client is connected to
   * @author nitterhe
   */
  public Server getCurrentServer() {
    return this.currentServer;
  }

  /**
   * Changes the username. Used when the client edits their username in the playerprofile.
   *
   * @param username - the new username
   * @author nitterhe
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Method to send the turn made by the client to the server.
   * 
   * @author nitterhe
   */
  public void makeTurn() {
    MakeTurnMessage mtm = new MakeTurnMessage(this.username, Data.getGameSession());
    this.clientThread.sendMessageToServer(mtm);
  }

  /**
   * Method to set the value of the starting value. This is used to set App.setroot() once when the
   * game starts.
   * 
   * @return starting - the starting variable
   * @author nitterhe
   */
  public boolean getStarting() {
    return this.starting;
  }

  /**
   * Method to set the value of the starting variable.
   * 
   * @param starting - the starting variable
   * @author nitterhe
   */
  public void setStarting(boolean starting) {
    this.starting = starting;
  }

  public boolean threadIsRunning() {
    return this.clientThread.running;
  }
}
