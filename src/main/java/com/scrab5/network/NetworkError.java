/**
 * Class for c reating MessageDialogs with error notifications.
 * 
 * @author nitterhe
 */
package com.scrab5.network;

import com.scrab5.ui.PopUpMessage;
import com.scrab5.ui.PopUpMessageType;

public class NetworkError extends Error {
  private static final long serialVersionUID = 1L;

  private NetworkErrorType errorType;

  /**
   * Constructor for the NetworkError. Sets ErrorType and handles the Error by popping up a
   * MessageDialog.
   * 
   * @author nitterhe
   * @param errorType
   */
  public NetworkError(NetworkErrorType errorType) {
    this.errorType = errorType;
    this.handleError();
  }

  /**
   * Handles the error by displaying error messages based on the errorType.
   * 
   * @author nitterhe
   */
  public void handleError() {
    String dialog = "";

    switch (this.errorType) {

      case CONNECTION:
        dialog = "Could not connect to the Server, try refreshing the server list";
        break;
      case COMMUNICATION:
        dialog =
            "Could not reach server. Server could have been closed, try again or try to reconnect";
        break;
      case IP:
        dialog = "Could not identify IP";
        break;
      case SEARCHSERVERS:
        dialog = "Could not search for servers in the local network";
        break;
      case CLIENTRUN:
        dialog = "Could not read incoming message from the server";
        break;
      case CLOSECONNECTION:
        dialog = "Closing the connection failed, maybe sockets already have been closed";
        break;
      case SERVERCREATION:
        dialog = "Could not host server";
        break;
      default:
        break;
    }
    if (!dialog.isEmpty()) {
      try {
        PopUpMessage npm = new PopUpMessage(dialog, PopUpMessageType.ERROR);
        npm.show();
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
    CONNECTION, COMMUNICATION, IP, SEARCHSERVERS, CLIENTRUN, CLOSECONNECTION, SERVERCREATION
  }
}
