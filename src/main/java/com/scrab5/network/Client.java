// @author nitterhe

package com.scrab5.network;

import java.net.InetAddress;
import java.net.Socket;

public class Client {

  private InetAddress ip;
  private String username;
  private Socket clientSocket;
  private boolean running;

  private Client(String username, InetAddress ip, int port) {
    this.username = username;
    this.running = true;
    try {
      this.clientSocket = new Socket(ip, port);
    } catch (Exception e) {
      // Exception handling
    }
  }

  public void createClient() {

  }

  public String getUsername() {
    return this.username;
  }

}
