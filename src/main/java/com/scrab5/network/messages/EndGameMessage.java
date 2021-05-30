package com.scrab5.network.messages;

/**
 * Class for messages sent when a player ends the game.
 *
 * @author nitterhe
 */
public class EndGameMessage extends Message {
  private static final long serialVersionUID = 1L;

  /**
   * Constructor to create EndGameMessage.
   *
   * @author nitterhe
   * @param sender - the username of the connecting client
   */
  public EndGameMessage(String sender) {
    super(sender);
    this.type = MessageType.ENDGAME;
  }
}
