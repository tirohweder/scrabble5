/**
 * Class for saving the statistics of all players that have ever played on the server.
 *
 * @author nitterhe
 */
package com.scrab5.network;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class ServerStatistics implements Serializable {
  private static final long serialVersionUID = 1L;

  private LinkedHashMap<String, ClientStatistic> serverStatistics;

  /**
   * Constructor for creating a new ServerStatistics instance. Creates an empty HashMap.
   * 
   * @author nitterhe
   */
  public ServerStatistics() {
    this.serverStatistics = new LinkedHashMap<String, ClientStatistic>();
  }

  /**
   * Helping class to save every clients statistics in one object.
   * 
   * @author nitterhe
   * @return serverStatistics - HashMap with all clients and their personal statistics on this
   *         server.
   */
  public LinkedHashMap<String, ClientStatistic> getServerStatistics() {
    return this.serverStatistics;
  }

  /**
   * Adds the client to serverStatistics if the client is new. Double if statement is used to avoid
   * NullpointerExceptions.
   * 
   * @author nitterhe
   * @param clientname - the username of the client as a String
   * @throws Exception - an Exception that is thrown when a similar client with the same name was
   *         already on the server
   */
  public boolean addClient(String clientname, String IPAddress) throws Exception {
    if (serverStatistics.containsKey(clientname)) {
      if (this.serverStatistics.get(clientname).getIPAddress().equals(IPAddress))
        return false;
      throw new Exception();
    }
    this.serverStatistics.put(clientname, new ClientStatistic(clientname, IPAddress));
    System.out.println("player added");
    return true;
  }

  /**
   * Used when loading ServerStatistics from the database.
   * 
   * @author nitterhe
   * @param clientStatistic - the clientStatistic to load from the database
   */
  public void loadClient(String clientname, String IPAddress, int gamesPlayed, int gamesWon) {
    this.serverStatistics.put(clientname,
        new ClientStatistic(clientname, IPAddress, gamesPlayed, gamesWon));
  }

  /**
   * Increases the clients gamesPlayed and also gamesWon when the boolean winner is true.
   * 
   * @author nitterhe
   * @param clientname - the name of the client that has played
   * @param winner - the client that won the game
   */
  public void gamePlayed(String clientname, boolean winner) {
    this.serverStatistics.get(clientname).gamePlayed();
    if (winner) {
      this.serverStatistics.get(clientname).gameWon();
      this.sort();
    }
  }

  /**
   * Sorts the ServerStatistics ordered by gamesWon.
   * 
   * @author nitterhe
   */
  private void sort() {
    LinkedHashMap<String, ClientStatistic> help =
        new LinkedHashMap<String, ClientStatistic>(this.serverStatistics.size());
    Collection<ClientStatistic> clients = this.serverStatistics.values();
    Iterator<ClientStatistic> iterator;
    ClientStatistic maximum;
    ClientStatistic next;

    while (!this.serverStatistics.isEmpty()) {
      iterator = clients.iterator();
      maximum = iterator.next();
      while (iterator.hasNext()) {
        next = iterator.next();
        if (maximum.getGamesWon() < next.getGamesWon())
          maximum = next;
      }
      help.put(maximum.getClientName(), maximum);
      this.serverStatistics.remove(maximum.getClientName());
    }
    this.serverStatistics = help;
  }


  /**
   * Helping class to save every clients statistics in one object.
   * 
   * @author Niklas
   */
  public class ClientStatistic implements Serializable {
    private static final long serialVersionUID = 1L;

    private String clientname;
    private String IPAddress;
    private int gamesPlayed;
    private int gamesWon;

    /**
     * Constructor for creating a single client's statistic object.
     * 
     * @author nitterhe
     * @param clientname - the client's username
     * @param IPAddress - the client's IPAddress
     */
    public ClientStatistic(String clientname, String IPAddress) {
      this.clientname = clientname;
      this.IPAddress = IPAddress;
      this.gamesPlayed = 0;
      this.gamesWon = 0;
    }

    /**
     * Constructor for creating a single client's statistic object. Used when loading statistics
     * from the server.
     * 
     * @author nitterhe
     * @param clientname - the client's username
     * @param IPAddress - the client's IPAddress
     * @param gamesPlayed - the client's number of games played
     * @param gamesWon - the client's number of games won
     */
    public ClientStatistic(String clientname, String IPAddress, int gamesPlayed, int gamesWon) {
      this.clientname = clientname;
      this.IPAddress = IPAddress;
      this.gamesPlayed = gamesPlayed;
      this.gamesWon = gamesWon;
    }

    /**
     * Returns the client's username.
     * 
     * @author nitterhe
     * @return clientname - the client's username as a String
     */
    public String getClientName() {
      return this.clientname;
    }

    /**
     * Returns the client's IPAddress.
     * 
     * @author nitterhe
     * @return IPAddress - the client's IPAddress
     */
    public String getIPAddress() {
      return this.IPAddress;
    }

    /**
     * Returns the number of games played on the server by the client.
     * 
     * @author nitterhe
     * @return gamesPlayed - the number of games played by the client as an int
     */
    public int getGamesPlayed() {
      return this.gamesPlayed;
    }

    /**
     * Returns the number of games won on the server by the client.
     * 
     * @author nitterhe
     * @return gamesWon - the number og games won by the client as an int
     */
    public int getGamesWon() {
      return this.gamesWon;
    }

    /**
     * Method to be called when the client played a game on the gameserver. Increases gamesPlayed.
     * 
     * @author nitterhe
     */
    public void gamePlayed() {
      this.gamesPlayed++;
    }

    /**
     * Method to be called when the client won a game on the gameserver. Increases gamesWon.
     * 
     * @author nitterhe
     */
    public void gameWon() {
      this.gamesWon++;
    }
  }
}
