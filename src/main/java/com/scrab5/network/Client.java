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
import com.scrab5.network.messages.SendServerDataMessage;

public class Client {

  public final int clientPort = 50000;
  public final int serverPort = 60000;
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
    this.hostedServer = null; // needs connection to database
    try {
      this.ip = InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {
      // Exception handling required
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
      hostedServer = new Server(this.username, clientMaximum);
      hostedServer.acceptClients();
      connectToServer(ip);
    } else {
      System.out.println("error at hostServer()");
      // Exception handling required
    }
  }

  /**
   * Searches for Servers in the local network and adds them to the serverList.
   * 
   * @author from stackoverflow -
   *         https://stackoverflow.com/questions/24082077/java-find-server-in-network
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
              Message m;
              for (int i = 0; i < 20; i++) {
                m = (Message) in.readObject();
                if (m.getType() == MessageType.SENDSERVERDATA) {
                  SendServerDataMessage ssdMessage = (SendServerDataMessage) m;
                  ServerData serverdata = new ServerData(ssdMessage.getSender() + "'s Server", ip4,
                      serverPort, ssdMessage.getClientCounter(), ssdMessage.getClientMaximum(),
                      ssdMessage.getStatus());
                  addServerToServerList(serverdata);
                  i = 20;
                } else {
                  wait(100);
                }
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
      clientThread.start();
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
    connectToServer(new ServerData(null, ip4, serverPort, 0, 0, false));
  }

  /**
   * Disconnects the ClientThread from the ServerThread. Does not execute the closeConnection method
   * of the ServerThread, because closeConnection would need to send a message to the server, but if
   * the server closes the connection the closeConnection method is executed. This would cause
   * closeConnection to send a message back to the server that already closed the connection and
   * closeConnection would throw an Exception.
   * 
   * @author nitterhe
   */
  public void disconnectFromServer() {
    if (clientThread.isAlive()) {
      clientThread.sendMessageToServer(new DisconnectMessage(clientThread.sender));
      clientThread = null;
      hostedServer = null;
    }
  }

  /**
   * Stops the client by simply switching the Thread attribute running to false
   * 
   * @author nitterhe
   */
  public void stopClientThread() {
    this.clientThread.stopThread();
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
   * Inner class to save server information in one object. Including server host, ip4 and port.
   * 
   * @author nitterhe
   */
  public class ServerData {
    private String servername;
    private String ip4;
    private int port;
    private int clientCounter;
    private int clientMaximum;
    private boolean status;

    /**
     * Constructor for ServerData object. The serverList saves server information as ServerData
     * objects.
     * 
     * @param servername - The name of the server
     * @param ip4 - The IP4Address of the server
     * @param port - The port of the server
     * @param clientCounter - the number of clients connected to the server
     * @param clientMaximum - the maximum amount of clients allowed to connect to the server
     * @param status - the server's status (true = in game/ false = waiting for clients)
     */
    public ServerData(String servername, String ip4, int port, int clientCounter, int clientMaximum,
        boolean status) {
      this.servername = servername;
      this.ip4 = ip4;
      this.port = port;
      this.clientCounter = clientCounter;
      this.clientMaximum = clientMaximum;
      this.status = status;
    }

    /**
     * Returns the servername as a String.
     * 
     * @author nitterhe
     * @return servername - the name of the server
     */
    public String getServername() {
      return this.servername;
    }

    /**
     * Returns the IP4Address of the server as a String.
     * 
     * @author nitterhe
     * @return ip4 - the IP4Address as a String
     */
    public String getIP4Address() {
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
     * Returns the server's current status as a boolean (true = in game/ false = waiting for
     * clients).
     * 
     * @author nitterhe
     * @return status - the server's current status
     */
    public boolean getServerStatus() {
      return this.status;
    }
  }
}
