/**
 * Class for messages used to connect the client with the server.
 * 
 * @author nitterhe
 */
package com.scrab5.network.messages;

import com.scrab5.network.Client;

public class ConnectMessage extends Message {

  private static final long serialVersionUID = 1L;
  private Client client;

  /**
   * Constructor to create ConnectionMessages.
   * 
   * @author nitterhe
   * @param sender - the username of the connecting client
   * @param client - the instance of the connecting client
   */
  public ConnectMessage(String sender, Client client) {
    super(sender);
    this.type = MessageType.CONNECT;
    this.client = client;
  }

  /**
   * Returns the client object that is connecting to the server.
   * 
   * @author nitterhe
   * @return client - object of the connecting client
   */
  public Client getClient() {
    return this.client;
  }
}
