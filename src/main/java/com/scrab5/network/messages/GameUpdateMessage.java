/**
 * Class for clients to send the turn done to the server => every client.
 * 
 * @author nitterhe
 */
package com.scrab5.network.messages;

public class GameUpdateMessage extends Message {
  private static final long serialVersionUID = 1L;

  /**
   * Constructor for GameUpdateMessages that are sent from the server to the clients.
   * 
   * @author nitterhe
   * @param sender - the sender of the message (always server)
   */
  public GameUpdateMessage(String sender) {
    super(sender);
    this.type = MessageType.GAMEUPDATE;
  }
}
