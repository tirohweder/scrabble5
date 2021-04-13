/**
 * Class to implement the client sided client-server-communication. Starts the ClientThread Class.
 * Provides methods for the client to communicate with the server.
 * 
 * @author nitterhe
 */
package com.scrab5.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import com.scrab5.network.messages.ChatMessage;
import com.scrab5.network.messages.DisconnectMessage;
import com.scrab5.network.messages.GetServerDataMessage;
import com.scrab5.network.messages.Message;
import com.scrab5.network.messages.MessageType;

public class Client {

  public final int clientPort = 2345;
  public final int serverPort = 1234;
  private String ip;
  private final String username;
  private ClientThread clientThread;
  private ArrayList<ServerData> serverList;
  private Server hostedServer;

  /**
   * Implements a new Client with the given username. Constructs an empty list for all servers in
   * the network and saves the users IP4Address.
   * 
   * @author nitterhe
   * @param username This is the client's name other clients will see in a MultiPlayerLobby.
   * @throws xyz
   */
  public Client(String username) {
    this.username = username;
    serverList = new ArrayList<ServerData>();
    try {
      this.ip = InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {
      // Exception handling required
    }
  }

  /**
   * Hosts a server. In application: starts a MultiPlayerLobby.
   * 
   * @author nitterhe
   * @throws xyz
   */
  public void hostServer() {
    if (hostedServer == null) {
      hostedServer = new Server(this.username);
      connectToServer(ip);
    } else {
      // Exception handling required
    }
  }

  /**
   * Searches for Servers in the local network and adds them to the serverList.
   * 
   * @author from stackoverflow -
   *         https://stackoverflow.com/questions/24082077/java-find-server-in-network
   * @return
   */
  // UI must refresh the shown servers from the serverList with a Thread due to the long time the
  // method needs
  public void searchServers() {
    for (int j = 1; j < 255; j++) {
      for (int k = 1; k < 255; k++) {
        final String ip4 = "192.168." + j + "." + k;
        new Thread(new Runnable() {
          public void run() {
            try {
              InetAddress serverCheck = InetAddress.getByName(ip4);
              if (!serverCheck.isReachable(10000))
                return;

              Socket getServerDataSocket = new Socket(ip4, serverPort);
              ObjectOutputStream out =
                  new ObjectOutputStream(getServerDataSocket.getOutputStream());
              ObjectInputStream in = new ObjectInputStream(getServerDataSocket.getInputStream());
              out.writeObject(new GetServerDataMessage(username));
              out.flush();
              out.reset();
              Message m = (Message) in.readObject();
              if (m.getType() == MessageType.DEFAULT) {
                ServerData serverdata =
                    new ServerData(m.getSender() + "'s Server", ip4, serverPort);
                addServerToServerList(serverdata);
              }
              getServerDataSocket.shutdownInput();
              getServerDataSocket.shutdownOutput();
              getServerDataSocket.close();
            } catch (Exception e) {
              // requires Exception handling
            }
          }
        });
      }
    }
  }

  /**
   * Adds an available server to the serverList.
   * 
   * @author nitterhe
   * @param serverdata The necessary data needed to open a socket with the server.
   */
  private synchronized void addServerToServerList(ServerData serverdata) {
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
   * Connects the ClientThread to the given ServerThread by starting the ClientThread.
   * 
   * @author nitterhe
   * @param serverdata Includes the IP4Address and
   */
  public void connectToServer(ServerData serverdata) {
    if (clientThread == null) {
      clientThread = new ClientThread(this);
      clientThread.connectToServer(serverdata);
    }
  }

  /**
   * Connects the ClientThread to the given IP4 Address by calling the method above with a new
   * ServerData object.
   * 
   * @author nitterhe
   * @param ip4 The ip4 which should be connected to.
   */
  public void connectToServer(String ip4) {
    connectToServer(new ServerData(null, ip4, serverPort));
  }

  /**
   * Disconnects the ClientThread from the ServerThread.
   * 
   * @author nitterhe
   */
  public void disconnectFromServer() {
    if (clientThread.isAlive()) {
      clientThread.sendMessageToServer(new DisconnectMessage(clientThread.sender, this.getIp()));
      clientThread = null;
      hostedServer = null;
    }
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
   * Returns the client's username.
   * 
   * @author nitterhe
   * @return username of the client
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Returns the client's IP4Address.
   * 
   * @author nitterhe
   * @return ip - the IP4Address as a String
   */
  public String getIp() {
    return this.ip;
  }

  /**
   * Inner class to save server information in one object. Including server host, ip4 and port.
   * 
   * @author nitterhe
   */
  public class ServerData {
    private String servername;
    private String ip4;
    private int port;

    /**
     * Constructor for ServerData object. The serverList saves server information as ServerData
     * objects.
     * 
     * @param servername - The name of the server
     * @param ip4 - The IP4Address of the server
     * @param port - The port of the server
     */
    public ServerData(String servername, String ip4, int port) {
      this.servername = servername;
      this.ip4 = ip4;
      this.port = port;
    }

    /**
     * Returns the servername.
     * 
     * @author nitterhe
     * @return servername - the name of the server
     */
    public String getServername() {
      return this.servername;
    }

    /**
     * Returns the IP4Address of the server.
     * 
     * @author nitterhe
     * @return ip4 - the IP4Address as a String
     */
    public String getIP4Address() {
      return this.ip4;
    }

    /**
     * Returns the server's port.
     * 
     * @author nitterhe
     * @return port - the server's port
     */
    public int getPort() {
      return this.port;
    }
  }
}
