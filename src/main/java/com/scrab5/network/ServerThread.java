// @author nitterhe

package com.scrab5.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.scrab5.network.messages.ChatMessage;
import com.scrab5.network.messages.ConnectMessage;
import com.scrab5.network.messages.DisconnectMessage;
import com.scrab5.network.messages.Message;

public class ServerThread extends Threads {

  private Socket socketToClient;
  private Server server;
  private ObjectOutputStream toClient;
  private ObjectInputStream fromClient;
  private Client connectedClient;

  // sets up Socket and ServerThread for 1 client and handles the server side communication
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

  public void run() {
    this.running = true;
    try {
      Message message;
      while (running) {
        message = (Message) this.fromClient.readObject();
        switch (message.getType()) {

          case GETSERVERDATA:
            Message reply = new Message(server.host);
            sendMessageToClient(reply);
            break;
          case CONNECT:
            ConnectMessage connect = (ConnectMessage) message;
            addPlayer(connect.getClient());
            break;
          case DISCONNECT:
            DisconnectMessage disconnect = (DisconnectMessage) message;
            if (disconnect.getSender().equals(server.host)) {
              server.shutDownServer();
            } else {
              deletePlayer(disconnect.getSenderIp());
            }
          case CHAT:
            ChatMessage chat = (ChatMessage) message;
            sendMessageToAllClients(chat);
            break;

          default:
            break;
        }
      }
    } catch (Exception e) {
      // requires Exception handling
    }
  }

  private void addPlayer(Client player) {
    if (null == server.getPlayers().get(player.getIp())) {
      server.getPlayers().put(player.getIp(), player);
      server.getConnections().put(player, this);
    }

  }

  private void deletePlayer(String senderIp) {
    Client player = server.getPlayers().get(senderIp);
    if (null != player) {
      server.getConnections().remove(player);
      server.getPlayers().remove(player.getIp());
      this.closeConnection();
    }
  }

  public Client getClient() {
    return this.connectedClient;
  }

  public void sendMessageToClient(Message message) {
    try {
      this.toClient.writeObject(message);
      this.toClient.flush();
      this.toClient.reset();
    } catch (Exception e) {
      // requires Exception handling
    }
  }

  public void sendMessageToAllClients(Message message) {
    try {
      for (ServerThread toClient : this.server.getConnections().values()) {
        toClient.sendMessageToClient(message);
      }
    } catch (Exception e) {
      // requires Exception handling
    }
  }

  public synchronized void closeConnection() {
    sendMessageToClient(new DisconnectMessage(server.host, server.getIp()));
    this.running = false;
    try {
      this.socketToClient.shutdownInput();
      this.socketToClient.shutdownOutput();
      this.socketToClient.close();
    } catch (Exception e) {
      // requires Exception handling
    }
  }
}
