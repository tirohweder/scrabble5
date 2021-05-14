/**
 * Class for messages used to send the main data from the server to the client. Reply for
 * GETSERVERDATAMESSAGEs.
 * 
 * @author nitterhe
 */
package com.scrab5.network.messages;

public class SendServerDataMessage extends Message {
  private static final long serialVersionUID = 1L;

  private int port;
  private int clientCounter;
  private int clientMaximum;
  private boolean status;

  /**
   * Constructor to create GetServerDataMessages.
   * 
   * @author nitterhe
   * @param sender - the username of the connecting client
   * @param clientCounter - the number of clients connected to the server
   * @param clientMaxmimum - the maximum amount of clients allowed to connect to the server
   * @param status - the server's status (true = in game/ false = waiting for clients)
   */
  public SendServerDataMessage(String sender, int port, int clientCounter, int clientMaximum,
      boolean status) {
    super(sender);
    this.type = MessageType.SENDSERVERDATA;
    this.port = port;
    this.clientCounter = clientCounter;
    this.clientMaximum = clientMaximum;
    this.status = status;
  }

  /**
   * Returns the port that the server is waiting on for messages.
   * 
   * @author nitterhe
   * @return port - the server's port for this client
   */
  public int getPort() {
    return this.port;
  }

  /**
   * Returns the clientCounter of the server.
   * 
   * @author nitterhe
   * @return clientCounter - the number of clients connected to the server
   */
  public int getClientCounter() {
    return this.clientCounter;
  }

  /**
   * Returns the maximum amount of clients allowed to connect to the server.
   * 
   * @author nitterhe
   * @return clientMaximum - the maximum amount of clients allowed to connect to the server
   */
  public int getClientMaximum() {
    return this.clientMaximum;
  }

  /**
   * Returns the server's status (true = in game/ false = waiting for clients)
   * 
   * @author nitterhe
   * @return boolean - the status of the server
   */
  public boolean getStatus() {
    return this.status;
  }
}
