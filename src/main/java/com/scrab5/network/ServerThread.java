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
      while (running) {
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
            addClient(connect.getClient());
            // send new client list to all clients
            break;
          case DISCONNECT:
            DisconnectMessage disconnect = (DisconnectMessage) message;
            if (disconnect.getSender().equals(server.getHost())) {
              server.shutDownServer();
            } else {
              deleteClient(disconnect.getSender());
            }
            // send an update message to the clients, so they know someone left the lobby
          case CHAT:
            ChatMessage chat = (ChatMessage) message;
            server.sendMessageToAllClients(chat);
            System.out.println(chat.getText());
            break;

          default:
            break;
        }
      }
    } catch (Exception e) {
      // requires Exception handling
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
  private void addClient(Client client) {
    if (null == server.getClients().get(client.getUsername())) {
      server.getClients().put(client.getUsername(), client);
      server.getConnections().put(client, this);
      server.setClientCount();
    } else {
      // requires Exception handling
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
    Client client = server.getClients().get(sender);
    if (null != client) {
      server.getConnections().remove(client);
      server.getClients().remove(client.getUsername());
      server.setClientCount();
      this.closeConnection();
    } else {
      // requires Exception handling
    }
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
  public synchronized void closeConnection() {
    sendMessageToClient(new DisconnectMessage(server.getHost()));
    try {
      this.socketToClient.close();
    } catch (Exception e) {
      // requires Exception handling
    }
    this.stopThread();
  }
}
