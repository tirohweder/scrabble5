// @author nitterhe

package com.scrab5.network.messages;

import java.io.Serializable;

public class Message implements Serializable {

  MessageType type;
  private String sender;

  public Message(String sender) {
    this.type = MessageType.DEFAULT;
    this.sender = sender;
  }

  public MessageType getType() {
    return this.type;
  }

  public String getSender() {
    return this.sender;
  }
}
