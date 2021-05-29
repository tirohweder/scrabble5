package com.scrab5.network.messages;

/**
 * Class for messages used to send the used dictionary from the server to the clients.
 *
 * @author nitterhe
 */
public class DictionaryMessage extends Message {
  private static final long serialVersionUID = 1L;

  private final String dictionaryName;
  private final String dictionary;

  /**
   * Constructor to create DictionaryMessages. The dictionary is represented by one string since a
   * String can contain until 2.1 billion characters but the largest dictionary in the world with
   * 188 languages has only 7.6 million entries. Therefore every entry would need around 270
   * characters for us to get a problem with String length. This is not the prettiest solution but
   * the fastest.
   *
   * @author nitterhe
   * @param sender - the username of the connecting client
   * @param dictionaryName - the name of the dictionary
   * @param dictionary - the dictionary
   */
  public DictionaryMessage(String sender, String dictionaryName, String dictionary) {
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
  public String getDictionary() {
    return this.dictionary;
  }
}
