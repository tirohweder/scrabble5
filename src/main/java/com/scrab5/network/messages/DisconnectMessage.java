// @author nitterhe

package com.scrab5.network.messages;

public class DisconnectMessage extends Message {
  private static final long serialVersionUID = 1L;

  private String senderIp;

  public DisconnectMessage(String sender, String senderIp) {
    super(sender);
    this.type = MessageType.DISCONNECT;
    this.senderIp = senderIp;
  }

  public String getSenderIp() {
    return this.senderIp;
  }
}
