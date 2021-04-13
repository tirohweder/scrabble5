// @author nitterhe

package com.scrab5.network;

import javax.swing.JOptionPane;

public class NetworkError extends Error {
  private static final long serialVersionUID = 1L;

  private NetworkErrorType errorType;

  public NetworkError(NetworkErrorType errorType) {
    this.errorType = errorType;
  }

  public void handleError() {
    String dialog = "";

    switch (this.getType()) {

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

  public NetworkErrorType getType() {
    return this.errorType;
  }

  public enum NetworkErrorType {
    CONNECTION, COMMUNICATION
  }
}

