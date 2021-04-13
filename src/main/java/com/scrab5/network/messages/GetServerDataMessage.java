// @author nitterhe

package com.scrab5.network.messages;

public class GetServerDataMessage extends Message {
  private static final long serialVersionUID = 1L;

  public GetServerDataMessage(String sender) {
    super(sender);
    this.type = MessageType.GETSERVERDATA;
  }
}
