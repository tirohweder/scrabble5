/**
 * Class for clients to send the turn done to the server => every client.
 * 
 * @author nitterhe
 */
package com.scrab5.network.messages;

public class MakeTurnMessage extends Message {
  private static final long serialVersionUID = 1L;



  /**
   * Constructor to send the turn made to the server / from the server to all clients.
   * 
   * @author nitterhe
   * @param sender - the username of the client that made the turn
   */
  public MakeTurnMessage(String sender) {
    super(sender);
    this.type = MessageType.MAKETURN;
  }
}
