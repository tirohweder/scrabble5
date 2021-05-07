/**
 * Class for saving the statistics of all players that have ever played on the server.
 *
 * @author nitterhe
 */
package com.scrab5.network;

import java.io.Serializable;
import java.util.HashMap;

public class ServerStatistics implements Serializable {
  private static final long serialVersionUID = 1L;

  private HashMap<String, ClientStatistic> serverStatistics;

  /**
   * Construcotr for creating a new ServerStatistics instance. Creates an empty HashMap.
   * 
   * @author nitterhe
   */
  public ServerStatistics() {
    this.serverStatistics = new HashMap<String, ClientStatistic>();
  }

  /**
   * Helping class to save every clients statistics in one object.
   * 
   * @author nitterhe
   * @return serverStatistics - HashMap with all clients and their personal statistics on this
   *         server.
   */
  public HashMap<String, ClientStatistic> getServerStatistics() {
    return this.serverStatistics;
  }

  /**
   * Helping class to save every clients statistics in one object.
   * 
   * @author Niklas
   */
  public class ClientStatistic implements Serializable {
    private static final long serialVersionUID = 1L;

    private String clientname;
    private int gamesPlayed;
    private int gamesWon;

    /**
     * Constructor for creating a single client's statistic object.
     * 
     * @author nitterhe
     * @param clientname
     */
    public ClientStatistic(String clientname) {
      this.clientname = clientname;
      this.gamesPlayed = 0;
      this.gamesWon = 0;
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
