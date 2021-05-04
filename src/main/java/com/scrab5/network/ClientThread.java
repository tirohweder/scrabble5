/**
 * Thread for the client sided client-server communication. Exchanges messages with the server and
 * executes methods based on the incoming messages.
 *
 * @author nitterhe
 */
package com.scrab5.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import com.scrab5.network.Client.ServerData;
import com.scrab5.network.messages.ChatMessage;
import com.scrab5.network.messages.ConnectMessage;
import com.scrab5.network.messages.DisconnectMessage;
import com.scrab5.network.messages.Message;

public class ClientThread extends Threads {

  private Client client;
  private ObjectOutputStream toServer;
  private ObjectInputStream fromServer;
  private Socket socketToServer;
  public final String sender;

  /**
   * Creates a Client Thread. Thread is started when the Client connects to a server.
   * 
   * @author nitterhe
   * @param client - references to the client the thread belongs to
   */
  public ClientThread(Client client) {
    this.client = client;
    sender = client.getUsername();
  }

  /**
   * Runs the thread. Receives messages from the server and handles actions.
   * 
   * @author nitterhe
   */
  public void run() {
    this.running = true;
    System.out.println("c");
    try {
      Message message;
      while (running) {
        message = (Message) this.fromServer.readObject();
        switch (message.getType()) {

          case DISCONNECT:
            this.closeConnection();
          case CHAT:
            ChatMessage chatMessage = (ChatMessage) message;
            String text = chatMessage.getText();
            System.out.println(text);
            // needs implementation
          default:
            break;
        }
        wait(50);
      }
    } catch (Exception e) {
      // requires Exception handling
    }
  }

  /**
   * Checks if server is still reachable then connects to the server. Opens streams and then starts
   * the thread => run() starts.
   * 
   * @author nitterhe
   * @param serverdata - object with given serverdata to connect to
   */
  public void connectToServer(ServerData serverdata) {
    try {
      if (InetAddress.getByName(serverdata.getIP4Address()).isReachable(10000)) {
        this.socketToServer = new Socket(serverdata.getIP4Address(), serverdata.getPort());
        this.toServer = new ObjectOutputStream(socketToServer.getOutputStream());
        this.fromServer = new ObjectInputStream(socketToServer.getInputStream());
        this.start();
        sendMessageToServer(new ConnectMessage(this.client.getUsername(), this.client));
      } else {
        // requires Exception handling
      }
    } catch (Exception e) {
      // requires Exception handling
    }
  }

  /**
   * Sends a message to the connected server.
   * 
   * @author nitterhe
   * @param message - the Message object to send to the server
   */
  public void sendMessageToServer(Message message) {
    try {
      this.toServer.writeObject(message);
      this.toServer.flush();
      this.toServer.reset();
    } catch (Exception e) {
      // requires Exception handling
    }
  }

  /**
   * Sets the Thread attribute running = false. Closes the current connection and streams to the
   * server.
   * 
   * @author nitterhe
   */
  void closeConnection() {
    sendMessageToServer(new DisconnectMessage(sender));
    // popup: you have been disconnected
    this.stopThread();
    try {
      this.socketToServer.close();
    } catch (Exception e) {
      // requires Exception handling
    }

  }
}
