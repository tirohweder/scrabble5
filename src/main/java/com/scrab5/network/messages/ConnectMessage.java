// @author nitterhe

package com.scrab5.network.messages;

import com.scrab5.network.Client;

public class ConnectMessage extends Message {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Client client;

  public ConnectMessage(String sender, Client client) {
    super(sender);
    this.type = MessageType.CONNECT;
    this.client = client;
  }

  public Client getClient() {
    return this.client;
  }
}
