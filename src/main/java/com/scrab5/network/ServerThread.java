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
import com.scrab5.network.NetworkError.NetworkErrorType;
import com.scrab5.network.messages.ChatMessage;
import com.scrab5.network.messages.ConnectMessage;
import com.scrab5.network.messages.DisconnectMessage;
import com.scrab5.network.messages.Message;
import com.scrab5.network.messages.SendServerDataMessage;

public class ServerThread extends Threads {

  private Socket socketToClient;
  private Server server;
  private ObjectOutputStream toClient;
  private ObjectInputStream fromClient;
  private Client connectedClient;

  /**
   * Construtor for the ServerThread. Sets up the socket for 1 client and opens streams and handles
   * the server side communication.
   * 
   * @author nitterhe
   * @param server - the server object this Thread belongs to
   * @param socketToClient - the clients's socket the ServerThread connects to.
   */
  public ServerThread(Server server, Socket socketToClient) {
    this.server = server;
    this.socketToClient = socketToClient;
    try {
      toClient = new ObjectOutputStream(socketToClient.getOutputStream());
      fromClient = new ObjectInputStream(socketToClient.getInputStream());
    } catch (Exception e) {
      // requires exception handling
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
            sendMessageToClient(
                new SendServerDataMessage(this.server.getHost(), this.server.getClientCounter(),
                    this.server.getClientMaximum(), this.server.getStatus()));
            this.closeConnection();
            break;
          case CONNECT:
            ConnectMessage connect = (ConnectMessage) message;
            try {
              addClient(connect.getClientData());
            } catch (Exception e) {
              sendMessageToClient(
                  new ConnectMessage(this.server.getHost(), connect.getClientData()));
            }
            this.server.sendUpdateMessage();
            break;
          case DISCONNECT:
            DisconnectMessage disconnect = (DisconnectMessage) message;
            if (disconnect.getSender().equals(server.getHost())) {
              server.shutDownServer();
            } else {
              deleteClient(disconnect.getSender());
            }
            this.stopThread();
            this.server.sendUpdateMessage();
            break;
          case CHAT:
            ChatMessage chat = (ChatMessage) message;
            server.sendMessageToAllClients(chat);
            break;
          default:
            break;
        }
      }
    } catch (Exception e) {
      new NetworkError(NetworkErrorType.COMMUNICATION);
    }
  }

  /**
   * Adds the given client to the servers client list with username as key. Declared private and in
   * this class so that no client can join the server without setting up a connection. Reduces
   * failures.
   * 
   * @author nitterhe
   * @param client
   */
  private void addClient(ClientData clientData) throws Exception {
    if (null == server.getClients().get(clientData.getUsername())) {
      server.getClients().put(clientData.getUsername(), clientData);
      server.getConnections().put(clientData, this);
      server.updateClientCount();
      this.server.sendUpdateMessage();
    } else {
      throw new Exception();
    }
  }

  /**
   * Adds the given client to the servers client list. Declared private and in this class so that no
   * external class can manipulate the client list. Reduces failures.
   * 
   * @author nitterhe
   * @param sender
   */
  private void deleteClient(String sender) {
    ClientData client = server.getClients().get(sender);
    if (null != client) {
      this.server.getConnections().remove(client);
      this.server.getClients().remove(client.getUsername());
      this.server.updateClientCount();
      this.closeConnection();
    }
    // maybe else, idk doesnt feel necessary
  }

  /**
   * Returns the client this ServerThread is connected to.
   * 
   * @author nitterhe
   * @return connectedClient - the connected client
   */
  public Client getClient() {
    return this.connectedClient;
  }

  /**
   * Sends the given message to the connected client.
   * 
   * @author nitterhe
   * @param message - the message to send
   */
  public void sendMessageToClient(Message message) {
    try {
      this.toClient.writeObject(message);
      this.toClient.flush();
      this.toClient.reset();
    } catch (Exception e) {
      // requires Exception handling
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
      new NetworkError(NetworkErrorType.CLOSECONNECTION);
    }

  }
}
