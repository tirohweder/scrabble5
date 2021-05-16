/**
 * Thread for the server sided client-server communication. Exchanges messages with the client and
 * executes methods based on the incoming messages.
 *
 * @author nitterhe
 */
package com.scrab5.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import com.scrab5.network.NetworkError.NetworkErrorType;
import com.scrab5.network.messages.ChatMessage;
import com.scrab5.network.messages.ConnectMessage;
import com.scrab5.network.messages.DisconnectMessage;
import com.scrab5.network.messages.MakeTurnMessage;
import com.scrab5.network.messages.Message;
import com.scrab5.network.messages.SendReadyMessage;
import com.scrab5.network.messages.SendServerDataMessage;
import com.scrab5.ui.Data;
import com.scrab5.util.database.FillDatabase;

public class ServerThread extends Threads {

  private Socket socketToClient;
  private Server server;
  private ObjectOutputStream toClient;
  private ObjectInputStream fromClient;
  private ClientData connectedClient;

  /**
   * Construtor for the ServerThread. Sets up the socket for 1 client and opens streams and handles
   * the server side communication.
   *
   * @param server - the server object this Thread belongs to
   * @param socketToClient - the clients's socket the ServerThread connects to.
   * @author nitterhe
   */
  public ServerThread(Server server, Socket socketToClient) {
    this.server = server;
    this.socketToClient = socketToClient;
    try {
      toClient = new ObjectOutputStream(socketToClient.getOutputStream());
      fromClient = new ObjectInputStream(socketToClient.getInputStream());
    } catch (Exception e) {
      System.out.println("Server could not implement streams");
    }
  }

  /**
   * Runs the thread. Receives messages from the client and handles actions.
   *
   * @author nitterhe
   */
  public void run() {
    this.running = true;

    try {
      Message message;
      while (this.running) {
        message = (Message) this.fromClient.readObject();
        switch (message.getType()) {

          case GETSERVERDATA:
            sendMessageToClient(new SendServerDataMessage(this.server.getHost(),
                this.socketToClient.getLocalPort(), this.server.getClientCounter(),
                this.server.getClientMaximum(), this.server.getStatus()));
            this.stopThread();
            this.socketToClient.close();
            break;
          case CONNECT:
            ConnectMessage connect = (ConnectMessage) message;
            this.connectedClient = connect.getClientData();
            try {
              addClient(connect.getClientData());
            } catch (Exception e) {
              sendMessageToClient(
                  new ConnectMessage(this.server.getHost(), connect.getClientData()));
            }
            break;
          case DISCONNECT:
            DisconnectMessage disconnect = (DisconnectMessage) message;
            if (disconnect.getSender().equals(server.getHost())) {
              server.shutDownServer();
            } else {
              deleteClient(disconnect.getSender());
              closeConnection();
            }
            break;
          case CHAT:
            ChatMessage chat = (ChatMessage) message;
            server.sendMessageToAllClients(chat);
            break;
          case SENDREADY:
            SendReadyMessage srm = (SendReadyMessage) message;
            server.setClientReady(srm.getSender(), srm.getReady());
            break;
          case MAKETURN:
            MakeTurnMessage mtm = (MakeTurnMessage) message;
            Data.setGameSession(mtm.getGameSession());
            this.server.resetTimer();
            server.sendMessageToAllClients(mtm);
            break;
          default:
            break;
        }
        this.server.sendUpdateMessage();
      }

      /**
       * This is in close connection, but also here to ensure that sockets on ports are really
       * closed and no conflicts shoot when hosting the App the next time.
       */
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
      new NetworkError(NetworkErrorType.SERVERRUN);
    }
  }

  /**
   * Adds the given client to the servers client list with username as key. Declared private and in
   * this class so that no client can join the server without setting up a connection. Reduces
   * failures.
   *
   * @param clientData - the clientData object of the lient that just connected to the server
   * @throws Exception - an Exception that is thrown when a similar client with the same name is
   *         already on the server / was on the server
   * @author nitterher
   */
  private void addClient(ClientData clientData) throws Exception {
    if (null == server.getClients().get(clientData.getUsername())) {
      if (server.getServerStatistics().addClient(clientData.getUsername(), clientData.getIp())) {
        FillDatabase.createServerRow(this.server.getHost(), clientData.getUsername(),
            clientData.getIp());
      }
      server.getClients().put(clientData.getUsername(), clientData);
      server.getConnections().put(clientData, this);
      server.updateClientCount();
    } else {
      throw new Exception();
    }
  }

  /**
   * Adds the given client to the servers client list. Declared private and in this class so that no
   * external class can manipulate the client list. Reduces failures.
   *
   * @param sender - the disconnect message's sender
   * @author nitterhe
   */
  private void deleteClient(String sender) {
    ClientData client = server.getClients().get(sender);
    if (null != client) {
      this.server.getConnections().remove(client);
      this.server.getClients().remove(client.getUsername());
      this.server.updateClientCount();
    }
  }

  /**
   * Returns the client this ServerThread is connected to.
   *
   * @return connectedClient - the connected client
   * @author nitterhe
   */
  public ClientData getClient() {
    return this.connectedClient;
  }

  /**
   * Sends the given message to the connected client.
   *
   * @param message - the message to send
   * @author nitterhe
   */
  public synchronized void sendMessageToClient(Message message) {
    try {
      this.toClient.writeObject(message);
      this.toClient.flush();
      this.toClient.reset();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Sends a DisconnectMessage to the connected client and closes the streams.
   *
   * @author nitterhe
   */
  protected synchronized void closeConnection() {
    sendMessageToClient(new DisconnectMessage(server.getHost()));
    this.stopThread();
    try {
      this.socketToClient.close();
    } catch (Exception e) {
      // new NetworkError(NetworkErrorType.CLOSECONNECTION);
    }
  }
}
