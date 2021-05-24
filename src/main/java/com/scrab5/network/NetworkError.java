package com.scrab5.network;

import com.scrab5.ui.PopUpMessage;
import com.scrab5.ui.PopUpMessageType;
import java.io.IOException;
import javafx.application.Platform;

/**
 * Class for c reating MessageDialogs with error notifications.
 * 
 * @author nitterhe
 */
public class NetworkError extends Error {
  private static final long serialVersionUID = 1L;

  private NetworkErrorType errorType;
  protected String dialog;

  /**
   * Constructor for the NetworkError. Sets ErrorType and handles the Error by popping up a
   * MessageDialog.
   * 
   * @author nitterhe
   * @param errorType - the type of error, influences the message shown
   */
  public NetworkError(NetworkErrorType errorType) {
    this.errorType = errorType;
    dialog = "";
    this.handleError();
  }

  /**
   * Handles the error by displaying error messages based on the errorType.
   * 
   * @author nitterhe
   */
  public void handleError() {

    switch (this.errorType) {

      case CONNECTION:
        this.dialog = "Could not connect to the Server, try refreshing the server list.";
        break;
      case COMMUNICATION:
        this.dialog =
            "Could not reach server. Server could have been closed, try again or try to reconnect.";
        break;
      case IP:
        this.dialog = "Could not identify IP.";
        break;
      case SEARCHSERVERS:
        this.dialog = "Could not search for servers in the local network.";
        break;
      case CLIENTRUN:
        this.dialog = "Could not read incoming message from the server.";
        break;
      case SERVERRUN:
        this.dialog = "Could not read incoming message from a client.";
        break;
      case CLOSECONNECTION:
        this.dialog = "Closing the connection failed, maybe sockets already have been closed.";
        break;
      case SERVERCREATION:
        this.dialog = "Could not host server.";
        break;
      case NAMEINUSE:
        this.dialog =
            "Name is already taken on this server. Please edit your username in the settings.";
        break;
      case NOSERVERFOUND:
        this.dialog = "No servers in your local network found.";
        break;
      case TIMER:
        this.dialog = "10 minute Timer run out. Server shut down.";
        break;
      case SERVERFULL:
        this.dialog = "Server is already full.";
        break;
      default:
        break;
    }
    if (!this.dialog.isEmpty()) {
      try {
        Platform.runLater(new Runnable() {
          public void run() {
            try {
              PopUpMessage npm = new PopUpMessage(NetworkError.this.dialog, PopUpMessageType.ERROR);
              npm.show();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Returns the type of this NetworkError.
   * 
   * @author nitterhe
   * @return errorType - the type of the NetworkError.
   */
  public NetworkErrorType getErrorType() {
    return this.errorType;
  }

  /**
   * An enum to gather the possible errorTypes.
   * 
   * @author Niklas
   */
  public enum NetworkErrorType {
    CONNECTION, COMMUNICATION, IP, SEARCHSERVERS, CLIENTRUN, SERVERRUN, CLOSECONNECTION, SERVERCREATION, NAMEINUSE, NOSERVERFOUND, TIMER, SERVERFULL
  }
}
