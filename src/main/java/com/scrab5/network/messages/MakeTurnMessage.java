/**
 * Class for clients to send the turn done to the server => every client.
 *
 * @author nitterhe
 */
package com.scrab5.network.messages;

import com.scrab5.core.game.GameSession;

public class MakeTurnMessage extends Message {

  private static final long serialVersionUID = 1L;

  private GameSession gameSession;

  /**
   * Constructor to send the turn made to the server / from the server to all clients.
   *
   * @author nitterhe
   * @param sender - the username of the client that made the turn
   */
  public MakeTurnMessage(String sender, GameSession gameSession) {
    super(sender);
    this.type = MessageType.MAKETURN;
    this.gameSession = gameSession;
  }

  /**
   * Returns the gameSession that is sent to all clients.
   *
   * @author nitterhe
   * @return gameSession - the GameSession object
   */
  public GameSession getGameSession() {
    return this.gameSession;
  }
}
