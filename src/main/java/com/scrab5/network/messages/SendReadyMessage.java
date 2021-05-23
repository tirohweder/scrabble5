package com.scrab5.network.messages;

import java.util.ArrayList;

/**
 * Class for clients to inform the server (and other clients) that the player is ready.
 * 
 * @author nitterhe
 */
public class SendReadyMessage extends Message {
  private static final long serialVersionUID = 1L;

  private boolean ready;
  private ArrayList<Integer> order;

  /**
   * Constructor for creating LobbyUpdateMessages.
   * 
   * @author nitterhe
   * @param sender - the LobbyUpdateMessage sender (always server's host)
   */
  public SendReadyMessage(String sender, boolean ready, ArrayList<Integer> order) {
    super(sender);
    this.type = MessageType.SENDREADY;
    this.ready = ready;
    this.order = order;
  }

  /**
   * Returns the client's ready status.
   * 
   * @author nitterhe
   * @return ready - the clients's ready status
   */
  public boolean getReady() {
    return this.ready;
  }

  /**
   * Returns the suggested order from the client.
   * 
   * @author nitterhe
   * @return order - the suggested order from the client in the order the clients are shown in the
   *         lobby
   */
  public ArrayList<Integer> getOrder() {
    return this.order;
  }
}
