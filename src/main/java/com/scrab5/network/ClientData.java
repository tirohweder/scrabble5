package com.scrab5.network;

import java.io.Serializable;

/**
 * Class to save server information in one object. Including server host, ip4 and port.
 * 
 * @author nitterhe
 */
public class ClientData implements Serializable {
  private static final long serialVersionUID = 1L;

  String username;
  String ip;
  ClientThread clientThread;

  /**
   * Constructor save the most important data of the client for the server. Created to be sent via
   * sockets so it has to be Serializable (same for fields, i.e. ClientThread)
   * 
   * @author nitterhe
   * @param username - the username of the client
   * @param ip - the ip of the client
   * @param clientThread - the client's thread for receiving messages
   */
  public ClientData(String username, String ip, ClientThread clientThread) {
    this.username = username;
    this.ip = ip;
    this.clientThread = clientThread;
  }

  /**
   * Returns the client's username.
   * 
   * @author nitterhe
   * @return username - the clients username
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Returns the client's IPAddress.
   * 
   * @author nitterhe
   * @return ip - the client's IPAddress
   */
  public String getIp() {
    return this.ip;
  }

  /**
   * Returns the client's clientThread.
   * 
   * @author nitterhe
   * @return clientThread - the client's Thread for communication
   */
  public ClientThread getClientThread() {
    return this.clientThread;
  }
}
