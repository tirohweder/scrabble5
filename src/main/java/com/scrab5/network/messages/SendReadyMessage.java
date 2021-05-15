/**
 * Class for clients to inform the server (and other clients) that the player is ready.
 * 
 * @author nitterhe
 */
package com.scrab5.network.messages;

public class SendReadyMessage extends Message {
  private static final long serialVersionUID = 1L;

  private boolean ready;

  /**
   * Constructor for creating LobbyUpdateMessages
   * 
   * @author nitterhe
   * @param sender - the LobbyUpdateMessage sender (always server's host)
   */
  public SendReadyMessage(String sender, boolean ready) {
    super(sender);
    this.type = MessageType.SENDREADY;
    this.ready = ready;
  }

  /**
   * Returns the client's ready status
   * 
   * @author nitterhe
   * @return ready - the clients's ready status
   */
  public boolean getReady() {
    return this.ready;
  }
}
