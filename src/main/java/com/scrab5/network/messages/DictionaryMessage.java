package com.scrab5.network.messages;

import java.io.File;

/**
 * Class for messages used to send the used dictionary from the server to the clients.
 *
 * @author nitterhe
 */
public class DictionaryMessage extends Message {
  private static final long serialVersionUID = 1L;

  private String dictionaryName;
  private File dictionary;

  /**
   * Constructor to create DictionaryMessages.
   *
   * @author nitterhe
   * @param sender - the username of the connecting client
   * @param dictionaryName - the name of the dictionary
   * @param dictionary - the dictionary
   */
  public DictionaryMessage(String sender, String dictionaryName, File dictionary) {
    super(sender);
    this.type = MessageType.DICTIONARY;
    this.dictionaryName = dictionaryName;
    this.dictionary = dictionary;
  }

  /**
   * Returns the name of the dictionary.
   *
   * @author nitterhe
   * @return client - object of the connecting client
   */
  public String getDictionaryName() {
    return this.dictionaryName;
  }

  /**
   * Returns the dictionary.
   * 
   * @author nitterhe
   * @return dictionary - the dictionary as a String
   */
  public File getDictionary() {
    return this.dictionary;
  }
}
