package com.scrab5.network.messages;

/**
 * Class for messages used when Exceptions occure. The server shall resend
 *
 * @author nitterhe
 */
public class ResendMessage extends Message {
  private static final long serialVersionUID = 1L;

  private int tryNumber;

  /**
   * Constructor to create ResendMessages.
   *
   * @author nitterhe
   * @param sender - the username of the connecting client
   */
  public ResendMessage(String sender, int tryNumber) {
    super(sender);
    this.type = MessageType.RESEND;
    this.tryNumber = tryNumber;
  }

  /**
   * Returns the number of trys that have been made before.
   *
   * @author nitterhe
   * @return tryNumber - the number of trys before
   */
  public int getTryNumber() {
    return this.tryNumber;
  }
}
