package com.scrab5.network.messages;

/**
 * Class for messages used when someone scores a triple word or bingo. Plays a sound.
 *
 * @author nitterhe
 */
public class PlaySoundMessage extends Message {
  private static final long serialVersionUID = 1L;

  private final boolean tripleOrBingo;

  /**
   * Constructor to create PlaySoundMessages.
   *
   * @author nitterhe
   * @param sender - the username of the sending client
   * @param tob - the boolean what sound to play (false = triple/ true = bingo)
   */
  public PlaySoundMessage(String sender, boolean tob) {
    super(sender);
    this.type = MessageType.PLAYSOUND;
    this.tripleOrBingo = tob;
  }

  /**
   * Returns a boolean with false means triple and true means bingo.
   *
   * @author nitterhe
   * @return TripleOrBingo - a boolean saying what sound to play.
   */
  public boolean getTripleOrBingo() {
    return this.tripleOrBingo;
  }
}
