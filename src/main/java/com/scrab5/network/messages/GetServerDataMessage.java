package com.scrab5.network.messages;

/**
 * Class for messages used to get the main data from the server. Used when searching for servers in
 * the local network.
 *
 * @author nitterhe
 */
public class GetServerDataMessage extends Message {
  private static final long serialVersionUID = 1L;

  /**
   * Constructor to create GetServerDataMessages.
   *
   * @author nitterhe
   * @param sender - the username of the connecting client
   */
  public GetServerDataMessage(String sender) {
    super(sender);
    this.type = MessageType.GETSERVERDATA;
  }
}
