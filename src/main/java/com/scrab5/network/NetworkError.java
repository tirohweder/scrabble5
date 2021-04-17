/**
 * Class for creating MessageDialogs with error notifications.
 * 
 * @author nitterhe
 */
package com.scrab5.network;

import javax.swing.JOptionPane;

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
        dialog = "Could not connect to the Server";
        break;
      case COMMUNICATION:
        dialog = "Could not reach server, try again";
        break;
      default:
        break;
    }
    if (!dialog.isEmpty()) {
      JOptionPane.showMessageDialog(null, dialog);
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
    CONNECTION, COMMUNICATION
  }
}

