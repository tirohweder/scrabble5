/**
 * Class for messages used to connect the client with the server.
 * 
 * @author nitterhe
 */
package com.scrab5.network.messages;

import com.scrab5.network.ClientData;

public class ConnectMessage extends Message {
  private static final long serialVersionUID = 1L;

  private final ClientData clientData;

  /**
   * Constructor to create ConnectionMessages.
   * 
   * @author nitterhe
   * @param sender - the username of the connecting client
   * @param clientData - the ClientData instance of the connecting client
   */
  public ConnectMessage(String sender, ClientData clientData) {
    super(sender);
    this.type = MessageType.CONNECT;
    this.clientData = clientData;
  }

  /**
   * Returns the client object that is connecting to the server.
   * 
   * @author nitterhe
   * @return client - object of the connecting client
   */
  public ClientData getClientData() {
    return this.clientData;
  }
}
