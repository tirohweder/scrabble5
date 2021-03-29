// @author nitterhe

package com.scrab5.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.scrab5.network.Client.ServerData;
import com.scrab5.network.messages.ConnectMessage;
import com.scrab5.network.messages.DisconnectMessage;
import com.scrab5.network.messages.Message;

public class ClientThread extends Threads {

  private Client client;
  private ObjectOutputStream toServer;
  private ObjectInputStream fromServer;
  private Socket socketToServer;
  public final String sender;

  public ClientThread(Client client) {
    this.client = client;
    sender = client.getUsername();
  }

  public void run() {
    this.running = true;
    try {
      Message message;
      while (running) {
        message = (Message) this.fromServer.readObject();
        switch (message.getType()) {

          case DISCONNECT:
            this.closeConnection();
          case CHAT:
            // needs implementation
          default:
            break;
        }
      }
    } catch (Exception e) {
      // requires Exception handling
    }
  }

  public boolean connectToServer(ServerData serverdata) {
    boolean success = false;
    try {
      this.socketToServer = new Socket(serverdata.ip4, serverdata.port);
      this.toServer = new ObjectOutputStream(socketToServer.getOutputStream());
      this.fromServer = new ObjectInputStream(socketToServer.getInputStream());
      sendMessageToServer(new ConnectMessage(this.client.getUsername(), this.client));
      this.start();
      success = true;
    } catch (Exception e) {
      // requires Exception handling
      success = false;
    }
    return success;
  }

  public void sendMessageToServer(Message message) {
    try {
      this.toServer.writeObject(message);
      this.toServer.flush();
      this.toServer.reset();
    } catch (Exception e) {
      // requires Exception handling
    }
  }

  public void closeConnection() {
    sendMessageToServer(new DisconnectMessage(sender, client.getIp()));
    running = false;
    try {
      this.socketToServer.shutdownInput();
      this.socketToServer.shutdownOutput();
      this.socketToServer.close();
    } catch (Exception e) {
      // requires Exception handling
    }
  }


}
