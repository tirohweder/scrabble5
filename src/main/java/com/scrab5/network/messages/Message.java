package com.scrab5.network.messages;

import java.io.Serializable;

/**
 * Class for the Message objects that are exchanged between client and server. All ***Message
 * Objects extend this class.
 *
 * @author nitterhe
 */
public class Message implements Serializable {
  private static final long serialVersionUID = 1L;
  private final String sender;
  MessageType type;

  /**
   * Construcor for default messages. Every message always includes a sender and a message type
   *
   * @author nitterhe
   * @param sender - the username of the sending client
   */
  public Message(String sender) {
    this.type = MessageType.DEFAULT;
    this.sender = sender;
  }

  /**
   * Returns this message object's type.
   *
   * @author nitterhe
   * @return type - this message object's type
   */
  public MessageType getType() {
    return this.type;
  }

  /**
   * Returns this message's sender.
   *
   * @author nitterhe
   * @return sender - this message's sender
   */
  public String getSender() {
    return this.sender;
  }
}
