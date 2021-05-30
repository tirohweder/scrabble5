package com.scrab5.network.messages;

/**
 * Class for messages used when Exceptions occure. The server shall resend
 *
 * @author nitterhe
 */
public class ResendMessage extends Message {
  private static final long serialVersionUID = 1L;

  /**
   * Constructor to create ResendMessages.
   *
   * @author nitterhe
   * @param sender - the username of the connecting client
   */
  public ResendMessage(String sender) {
    super(sender);
    this.type = MessageType.RESEND;
  }
}
