// @author nitterhe

package com.scrab5.network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class Server {

  // private boolean running;
  public final int serverPort = 1234;
  private ServerSocket serverSocket;
  private boolean gameStart;
  private HashMap<String, Client> players;
  private HashMap<Client, ServerThread> connections;


  // 1. sets up server 2. initializes ServerCollections 3. creates ServerSocket
  public Server() {
    this.players = new HashMap<String, Client>();
    this.connections = new HashMap<Client, ServerThread>();
    this.gameStart = false;
    try {
      serverSocket = new ServerSocket(this.serverPort);
    } catch (Exception e) {
      // requires Exception handling
    }
    this.acceptClients();
  }

  // allows maximum of 4 players to connect until gameStart == true
  private void acceptClients() {
    while (!gameStart && this.getPlayerCount() < 4) {
      try {
        Socket newClient = serverSocket.accept();
        ServerThread clientConnection = new ServerThread(this, newClient);
        clientConnection.start();
        /*
         * Client c = clientConnection.getClient(); players.put(c);
         * connections.put(c,clientConnection);connections.add(clientConnection);
         */
      } catch (Exception e) {
        // requires exception handling
      }
    }
  }

  public void startGame() {
    this.gameStart = true;
  }

  public void endGame() {
    this.gameStart = false;
    this.acceptClients();
  }

  public int getPlayerCount() {
    return players.size();
  }

  private void addPlayer() {

  }

  private void deletePlayer() {

  }
}
