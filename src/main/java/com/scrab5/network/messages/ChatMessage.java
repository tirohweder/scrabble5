/**
 * Class for messages used to chat with other clients via the server.
 * 
 * @author nitterhe
 */
package com.scrab5.network.messages;

public class ChatMessage extends Message {
  private static final long serialVersionUID = 1L;

  private String text;

  /**
   * Constructor to create ChatMessages.
   * 
   * @author nitterhe
   * @param sender - the username of the sending client
   * @param text - the text of the message as a String
   */
  public ChatMessage(String sender, String text) {
    super(sender);
    this.type = MessageType.CHAT;
    this.text = text;
  }

  /**
   * Returns the text of the ChatMessage.
   * 
   * @author nitterhe
   * @return text - the text of the ChatMessage
   */
  public String getText() {
    return this.text;
  }
}
