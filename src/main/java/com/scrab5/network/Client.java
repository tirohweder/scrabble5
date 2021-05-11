/**
 * Class to implement the client sided client-server-communication. Starts the ClientThread Class.
 * Provides methods for the client to communicate with the server. Serializable so the whole Client
 * object can be sent to the Server. Also fields implementing objects of other classes are
 * Serializable therefore.
 * 
 * @author nitterhe
 */
package com.scrab5.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import com.scrab5.network.NetworkError.NetworkErrorType;
import com.scrab5.network.messages.ChatMessage;
import com.scrab5.network.messages.DisconnectMessage;
import com.scrab5.network.messages.GetServerDataMessage;
import com.scrab5.network.messages.LobbyUpdateMessage;
import com.scrab5.network.messages.Message;
import com.scrab5.network.messages.MessageType;
import com.scrab5.network.messages.SendServerDataMessage;
import com.scrab5.ui.Data;

public class Client implements Serializable {
  private static final long serialVersionUID = 1L;

  public final int clientPort = 50000;
  public final int serverPort = 60000;
  private String ip;
  private String username;
  private ClientThread clientThread;
  private ArrayList<ServerData> serverList;
  private Server currentServer;
  private Server hostedServer;

  /**
   * Implements a new Client with the given username. Constructs an empty list for all servers in
   * the network and saves the users IP4Address. CurrentServer is only used for UI communication.
   * Server constantly changes every connected client's currentServer attribute (i.e. ChatMessages,
   * new Clients joined ....).
   * 
   * @author nitterhe
   * @param username This is the client's name other clients will see in a MultiPlayerLobby.
   * @throws xyz
   */
  public Client(String username) {
    this.username = username;
    serverList = new ArrayList<ServerData>();
    this.hostedServer = null; // needs connection to database
    try {
      this.ip = InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {
      new NetworkError(NetworkErrorType.IP);
    }
  }

  /**
   * Hosts a server and accepts clients. Automatically joins the server. In application: starts a
   * MultiPlayerLobby.
   * 
   * @author nitterhe
   * @param clientMaximum - the maximum number of clients allowed to connect to the server
   * @throws xyz
   */
  public void hostServer(int clientMaximum) {
    if (hostedServer == null) {
      hostedServer = new Server(this.username, clientMaximum, false);
    } else {
      hostedServer.setClientMaximum(clientMaximum);
      hostedServer.openServerSocket();
    }
    hostedServer.acceptClients();
    connectToServer(ip);

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
            Thread t = new Thread(new Runnable() {
              public void run() {
                try {
                  InetAddress serverCheck = InetAddress.getByName(ip4);
                  if (serverCheck.isReachable(1000)) {
                    Socket getServerDataSocket = new Socket(ip4, serverPort);
                    ObjectOutputStream out =
                        new ObjectOutputStream(getServerDataSocket.getOutputStream());
                    ObjectInputStream in =
                        new ObjectInputStream(getServerDataSocket.getInputStream());
                    out.writeObject(new GetServerDataMessage(username));
                    out.flush();
                    out.reset();
                    Message m;
                    for (int i = 0; i < 10; i++) {
                      m = (Message) in.readObject();
                      if (m.getType() == MessageType.SENDSERVERDATA) {
                        SendServerDataMessage ssdMessage = (SendServerDataMessage) m;
                        ServerData serverdata = new ServerData(ssdMessage.getSender(), ip4,
                            serverPort, ssdMessage.getClientCounter(),
                            ssdMessage.getClientMaximum(), ssdMessage.getStatus());
                        addServerToServerList(serverdata);
                        i = 20;
                      }
                    }
                    getServerDataSocket.shutdownInput();
                    getServerDataSocket.shutdownOutput();
                    getServerDataSocket.close();
                  }
                } catch (ConnectException ce) {
                  // does nothing since this error occurs when the pinged IP is not a server
                } catch (Exception e) {
                  System.out.println(e.getMessage());
                  // does nothing since too many device's firewalls block pings, therefore, too many
                  // exceptions
                }
              }
            });
            t.start();
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
   * @author nitterhe
   * @param serverdata The necessary data needed to open a socket with the server.
   */
  private synchronized void addServerToServerList(ServerData serverdata) {
    if (!serverList.contains(serverdata))
      serverList.add(serverdata);
  }

  /**
   * Returns the current serverList.
   * 
   * @author nitterhe
   * @return serverList
   */
  public synchronized ArrayList<ServerData> getServerList() {
    return this.serverList;
  }

  /**
   * Connects the ClientThread to the given ServerThread by starting the ClientThread. For the
   * currentServer a new Server instance is created that is updated by the server via messages.
   * 
   * @author nitterhe
   * @param serverdata Includes the IP4Address and
   */
  public void connectToServer(ServerData serverdata) {
    this.clientThread = new ClientThread(this);
    this.clientThread.connectToServer(serverdata);
  }

  /**
   * Connects the ClientThread to the given IP4 Address by calling the method above with a new
   * ServerData object.
   * 
   * @author nitterhe
   * @param ip4 The ip4 which should be connected to.
   */
  public void connectToServer(String ip4) {
    connectToServer(new ServerData(null, ip4, serverPort, 0, 0, false));
  }

  /**
   * Disconnects the ClientThread from the ServerThread. Server shuts down if the client is the
   * server's host.
   * 
   * @author nitterhe
   * @return boolean - used in the MulitplayerLobbyController to display a disconnect notification
   */
  public boolean disconnectFromServer() {
    if (this.clientThread.running) {
      // save hostedServer to database
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
    if (this.clientThread.running)
      this.clientThread.closeConnection();
  }

  public ClientData getClientData() {
    return new ClientData(this.username, this.ip, this.clientThread);
  }

  /**
   * Sends ChatMessage to the server. Server will send it to all clients in the lobby.
   * 
   * @author nitterhe
   * @param text The message text.
   */
  public void sendChatMessage(String text) {
    this.clientThread.sendMessageToServer(new ChatMessage(clientThread.sender, text));
  }

  /**
   * Returns the client's username as a String.
   * 
   * @author nitterhe
   * @return username - username of the client
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Returns the client's IP4Address as a String.
   * 
   * @author nitterhe
   * @return ip - the IP4Address of the client
   */
  public String getIp() {
    return this.ip;
  }

  /**
   * Returns this client's ClientThread.
   * 
   * @author nitterhe
   * @return clientThread - this client's ClientThread.
   */
  public ClientThread getClientThread() {
    return this.clientThread;
  }

  /**
   * Returns the client's server as a Server object. Null if no server was hosted by the client.
   * 
   * @author nitterhe
   * @return hostedServer - the client's server
   */
  public Server getHostedServer() {
    return this.hostedServer;
  }

  /**
   * Used to first set an empty sever instance that is filled by the conencted server and the
   * changes are displayed by the UI.
   * 
   * @author nitterhe
   * @param currentServer - the server that is now the new currentServer
   */
  public void initializeCurrentServer(Server currentServer) {
    this.currentServer = currentServer;
  }

  /**
   * Updates the current Server instance and refreshes the UI
   * 
   * @author nitterhe
   * @param lum - the message from the server with the updated values
   */
  public void updateCurrentServer(LobbyUpdateMessage lum) {
    this.getCurrentServer().setGameStart(lum.getGameStart());
    this.getCurrentServer().setClients(lum.getClients());
    this.getCurrentServer().updateClientCount();
    this.getCurrentServer().setServerStatistics(lum.getServerStatistics());
    // needs to refresh UI
  }

  /**
   * Returns the client's current server that the client is connected to. Used for controlling what
   * the UI shall show. CurrentServer is constantly updated by the server.
   * 
   * @author nitterhe
   * @return currentServer - the server the client is connected to
   */
  public Server getCurrentServer() {
    return this.currentServer;
  }

  /**
   * Changes the username. Used when the client edits their username in the playerprofile.
   * 
   * @author nitterhe
   * @param username - the new username
   */
  public void setUsername(String username) {
    this.username = username;
  }
}
