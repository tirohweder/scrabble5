// @author nitterhe

package com.scrab5.network.messages;

public class ChatMessage extends Message {
  private static final long serialVersionUID = 1L;

  private String text;

  public ChatMessage(String sender, String text) {
    super(sender);
    this.type = MessageType.CHAT;
    this.text = text;
  }

  public String getText() {
    return this.text;
  }
}
