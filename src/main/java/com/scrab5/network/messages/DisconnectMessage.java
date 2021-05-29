package com.scrab5.network.messages;

/**
 * Class for messages used to disconnect the client from the server.
 *
 * @author nitterhe
 */
public class DisconnectMessage extends Message {
  private static final long serialVersionUID = 1L;

  /**
   * Constructor to create DisconnectMessages.
   *
   * @author nitterhe
   * @param sender - the username of the sending client
   */
  public DisconnectMessage(String sender) {
    super(sender);
    this.type = MessageType.DISCONNECT;
  }
}
