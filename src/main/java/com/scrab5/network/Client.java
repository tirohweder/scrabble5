// @author nitterhe

package com.scrab5.network;

import java.net.InetAddress;
import java.net.Socket;

public class Client {

  private InetAddress ip;
  private String username;
  private Socket clientSocket;

  private Client() {

  }

  public String getUsername() {
    return this.username;
  }

}
