/**
 * Thread for the client sided client-server communication. Exchanges messages with the server and
 * executes methods based on the incoming messages.
 *
 * @author nitterhe
 */
package com.scrab5.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import com.scrab5.network.NetworkError.NetworkErrorType;
import com.scrab5.network.messages.ChatMessage;
import com.scrab5.network.messages.ConnectMessage;
import com.scrab5.network.messages.LobbyUpdateMessage;
import com.scrab5.network.messages.Message;
import com.scrab5.ui.MultiplayerLobbyController;
import com.scrab5.ui.PopUpMessage;
import com.scrab5.ui.PopUpMessageType;
import javafx.application.Platform;

public class ClientThread extends Threads implements Serializable {
  private static final long serialVersionUID = 1L;

  transient private Client client;
  transient private ObjectOutputStream toServer;
  transient private ObjectInputStream fromServer;
  transient private Socket socketToServer;
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
    sendMessageToServer(new ConnectMessage(this.sender, this.client.getClientData()));
    try {
      Message message;
      while (this.running) {
        message = (Message) this.fromServer.readObject();
        switch (message.getType()) {

          case SENDSERVERDATA:

            break;
          case DISCONNECT:
            // switch layer to lobby overview
            // MultiplayerLobbyController.lobbyClosed();
            MultiplayerLobbyController.lobbyClosed();
            this.closeConnection();
            break;
          case CHAT:
            ChatMessage chatMessage = (ChatMessage) message;
            String text = chatMessage.getText();
            System.out.println(text);
            // needs implementation
            break;
          case CONNECT:
            // this is used since sending messages between client and server is faster than the
            // thread switching to the new Scene
            synchronized (this) {
              try {
                wait(300);
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
            MultiplayerLobbyController.lobbyClosed();
            this.closeConnection();
            new NetworkError(NetworkErrorType.NAMEINUSE);
            break;
          case LOBBYUPDATE:
            LobbyUpdateMessage lum = (LobbyUpdateMessage) message;
            if (this.client.getCurrentServer() == null) {
              this.client.initializeCurrentServer(
                  new Server(lum.getSender(), lum.getClientMaximum(), true));
            }
            this.client.updateCurrentServer(lum);
          default:
            break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      new NetworkError(NetworkErrorType.CLIENTRUN);
      e.printStackTrace();
    }

    try {
      this.socketToServer.close();
      try {
        Platform.runLater(new Runnable() {
          public void run() {
            try {
              PopUpMessage npm = new PopUpMessage("The connection has been closed.",
                  PopUpMessageType.NOTIFICATION);
              npm.show();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      new NetworkError(NetworkErrorType.CLOSECONNECTION);
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
      }
    } catch (Exception e) {
      e.printStackTrace();
      new NetworkError(NetworkErrorType.CONNECTION);
      MultiplayerLobbyController.lobbyClosed();
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
      new NetworkError(NetworkErrorType.COMMUNICATION);
    }
  }

  /**
   * Sets the Thread attribute running = false. Closes the current connection and streams to the
   * server.
   * 
   * @author nitterhe @mherre :^)
   */
  protected void closeConnection() {
    this.stopThread();
  }
}
