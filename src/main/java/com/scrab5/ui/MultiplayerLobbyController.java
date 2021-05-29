package com.scrab5.ui;

import com.scrab5.core.game.GameSession;
import com.scrab5.core.player.AiPlayer;
import com.scrab5.core.player.Player;
import com.scrab5.network.ClientData;
import com.scrab5.network.Server;
import com.scrab5.network.ServerStatistics;
import com.scrab5.network.ServerStatistics.ClientStatistic;
import com.scrab5.network.messages.MakeTurnMessage;
import com.scrab5.util.database.UseDatabase;
import com.scrab5.util.parser.DictionaryParser;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * The MultiplayerLobbyController class controls the components of the "MultiplayerLobby.fxml".
 *
 * @author mherre
 */
public class MultiplayerLobbyController extends LobbyController implements Initializable {

  private static LinkedHashMap<String, ArrayList<Integer>> votes = new LinkedHashMap<>();
  @FXML private Label player1;
  @FXML private Label player2;
  @FXML private Label player3;
  @FXML private Label player4;
  @FXML private Label ready1;
  @FXML private Label ready2;
  @FXML private Label ready3;
  @FXML private Label ready4;
  @FXML private Label playerNameStats1;
  @FXML private Label playerNameStats2;
  @FXML private Label playerNameStats3;
  @FXML private Label playerNameStats4;
  @FXML private Label played1;
  @FXML private Label played2;
  @FXML private Label played3;
  @FXML private Label played4;
  @FXML private Label won1;
  @FXML private Label won2;
  @FXML private Label won3;
  @FXML private Label won4;
  @FXML private Label score1;
  @FXML private Label score2;
  @FXML private Label score3;
  @FXML private Label score4;
  @FXML private Label ipAddress;
  @FXML private ImageView customizeButton;
  @FXML private TextArea chatBox;
  @FXML private TextField messageTextField;
  private boolean isReady = false;
  private boolean isHost;
  private int ai = 0;

  /**
   * Called when the lobby was closed by the host.
   *
   * @throws IOException - thrown when the layer could not be switched
   * @author nitterhe
   */
  public static void lobbyClosed() {
    try {
      App.setRoot("MultiplayerOverview");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a play order vote to all votes.
   *
   * @author nitterhe
   * @param clientname - the name of the client suggesting the player order
   * @param vote - the actual order
   */
  public static void addVote(String clientname, ArrayList<Integer> vote) {
    votes.putIfAbsent(clientname, vote);
  }

  /**
   * Call certain methods as soon as the Controller is loaded.
   *
   * @author mherre
   * @author nitterhe
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.setUpInit();
    votes = new LinkedHashMap<>();

    // this is a short delay so things in the background sent via the server are set up.
    synchronized (this) {
      try {
        wait(200);
      } catch (Exception e) {
        // do nothing. this is just so everything transfered via the server can load
      }
    }
    if (Data.getPlayerClient()
        .getUsername()
        .equals(Data.getPlayerClient().getCurrentServer().getHost())) {
      isHost = true;
    } else {
      isHost = false;
      this.customizeButton.setY(-34);
      this.customizeButton.setOpacity(1.0);
    }
    this.ipAddress.setText(Data.getPlayerClient().getCurrentServer().getIp4());
    this.refreshUi();
  }

  /**
   * Event method that is called when the "Back"-button in the UI is clicked. It sets the scene to
   * "MultiplayerOverview".
   *
   * @param event the event that is created from the mouse-click
   * @author mherre
   * @author nitterhe
   */
  @FXML
  protected void back(MouseEvent event) {
    playSound("ButtonClicked.mp3");
    Data.getPlayerClient().disconnectFromServer();
  }

  /**
   * Event method that is called when the "Ready"-button in the UI is clicked. It sets the ready
   * status in the UI to ready / not ready and also intern.
   *
   * @param event the event that is created from the mouse-click
   * @author mherre
   * @author nitterhe
   */
  @FXML
  private void ready(MouseEvent event) {
    playSound("ButtonClicked.mp3");
    this.isReady = !isReady;
    Data.getPlayerClient().setReady(this.isReady, this.getPlayerVotes());
  }

  /**
   * Event method that is called when the "Chat"-button in the UI is clicked. It sends the entered
   * message to every client and refreshes the chat UI.
   *
   * @param event the event that is created from the mouse-click
   * @author mherre
   * @author nitterhe
   */
  @FXML
  private void enterChatMessage(MouseEvent event) {

    Platform.runLater(
        new Runnable() {

          @Override
          public void run() {
            messageTextField.selectAll();
            Data.getPlayerClient()
                .sendChatMessage(Data.getCurrentUser() + ": " + messageTextField.getText() + "\n");
          }
        });
  }

  /**
   * Event method that is called when the "Add Player"-button in the UI is clicked. If the there are
   * free spaces in the lobby, then an AIPlayer is added.
   *
   * @author mherre
   * @author nitterhe
   * @param event the event that is created from the mouse-click
   */
  protected void addPlayer(MouseEvent event) {

    playSound("ButtonClicked.mp3");

    // adds a different AI when adding an AI
    if (Data.getHostedServer().getClientMaximum() > Data.getHostedServer().getClientCounter()) {
      switch (ai % 3) {
        case 0:
          Data.getHostedServer().addAi("Horst_AI");
          break;
        case 1:
          Data.getHostedServer().addAi("Max_AI");
          break;
        case 2:
          Data.getHostedServer().addAi("Berta_AI");
          break;
        default:
          break;
      }
      ai++;
    }
  }

  /**
   * Event method that is called when the "Kick"-button of the second player in the UI is clicked.
   * Removes every visual component of an added player and calls {@link
   * com.scrab5.ui.MultiplayerLobbyController#isClickable isClickable()}.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  protected void kickPlayer2(MouseEvent event) {

    if (kick2.getOpacity() == 1.0) {
      Data.getHostedServer().kickClient(this.player2.getText());
    }
    playSound("ButtonClicked.mp3");
  }

  /**
   * Event method that is called when the "Kick"-button of the third player in the UI is clicked.
   * Removes every visual component of an added player and calls {@link
   * com.scrab5.ui.MultiplayerLobbyController#isClickable isClickable()}.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  protected void kickPlayer3(MouseEvent event) {

    if (kick3.getOpacity() == 1.0) {
      Data.getHostedServer().kickClient(this.player3.getText());
    }
    playSound("ButtonClicked.mp3");
  }

  /**
   * Event method that is called when the "Kick"-button of the fourth player in the UI is clicked.
   * Removes every visual component of an added player and calls {@link
   * com.scrab5.ui.MultiplayerLobbyController#isClickable isClickable()}.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  protected void kickPlayer4(MouseEvent event) {

    if (kick4.getOpacity() == 1.0) {
      Data.getHostedServer().kickClient(this.player4.getText());
    }
    playSound("ButtonClicked.mp3");
  }

  /**
   * Method that is called when a <code>ComboBox</code> in the UI clicked. Makes every <code>
   * ComboBox</code> in this class only openable when a corresponding "Arrow Down"-button is
   * clicked.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  protected void dontShow(MouseEvent event) {
    voteSelection1.hide();
    voteSelection2.hide();
    voteSelection3.hide();
    voteSelection4.hide();
    diffBox1.hide();
    diffBox2.hide();
    diffBox3.hide();
  }

  /**
   * Starts the game in a multipalyer session. You will not understand the code but it works fine.
   *
   * @param event start game was clicked
   * @throws IOException wrong input recived
   * @throws SQLException if database is empty
   * @author trohwede, nitterhe
   */
  @Override
  protected void startGame(MouseEvent event) throws IOException, SQLException {

    // initialization if used Lists and arrays
    ArrayList<Player> gameSessionList = new ArrayList<>();
    ArrayList<Integer> voteResults = new ArrayList<>();
    Collection<ClientData> clientdata =
        Data.getPlayerClient().getCurrentServer().getClients().values();
    int[] difficulties =
        new int[] {
          this.diffBox1.getSelectionModel().getSelectedIndex(),
          this.diffBox2.getSelectionModel().getSelectedIndex(),
          this.diffBox3.getSelectionModel().getSelectedIndex()
        };
    LinkedList<Player> playerList = new LinkedList<>();

    Iterator<ClientData> it = clientdata.iterator();
    ClientData current;
    int counter = 0;
    // inizializes a list of all players with the names of the currently connected clients and added
    // AIs
    while (it.hasNext()) {
      current = it.next();
      if (current.getIp().equals("AI")) {
        playerList.add(
            playerList.size(), new AiPlayer(current.getUsername(), difficulties[counter]));
      } else {
        playerList.add(playerList.size(), new Player(current.getUsername()));
      }
    }

    // for the number of clients and AIs connected a new voteResult with the value 0 is added
    for (int i = 0; i < clientdata.size(); i++) {
      voteResults.add(0);
    }

    // for each vote that was handed in by the clients the order numbers for each client is added
    // up. (1. gets 1 point added 2. gets 2 ....)
    for (ArrayList<Integer> vote : votes.values()) {
      for (int j = 0; j < clientdata.size(); j++) {
        voteResults.add(j, vote.get(j) + voteResults.remove(j));
      }
    }

    int max;
    // now the maximum in the voteResults is calulated and added on top of the list. So the player
    // with the highest amount of votes is added first and therefore plays as the last player. Many
    // votes mean that this player was often last in the suggested orders.
    for (int i = 0; i < playerList.size(); i++) {
      max = 0;
      for (int k = 1; k < clientdata.size(); k++) {
        if (voteResults.get(max) < voteResults.get(k)) {
          max = k;
        }
      }
      gameSessionList.add(0, playerList.get(max));
      voteResults.set(max, 0);
    }

    // now with the correct order of players and the eventually edited letter distribution and value
    // distribution a new gameSession is created
    if (Data.getHasBeenEdited()) {
      ArrayList<Integer> tiles = createTileBag(Data.getOccurrencyDistribution());
      new GameSession(gameSessionList, tiles, Data.getPointsDistribution(), true);
    } else {
      int[] help2 = UseDatabase.getAllOccurrences();
      int[] help3 = UseDatabase.getAllPointsPerLetter();

      ArrayList<Integer> points = new ArrayList<>();
      ArrayList<Integer> help1 = new ArrayList<>();
      for (int j = 0; j < help2.length; j++) {
        help1.add(help2[j]);
        points.add(help3[j]);
      }
      ArrayList<Integer> tiles = createTileBag(help1);
      Data.setGameSession(new GameSession(gameSessionList, tiles, points, true));
    }

    // the game is started and sent to all connected clients
    Data.getHostedServer().startGame();
    Data.getHostedServer()
        .sendDictionary(
            Data.getSelectedDictionary(),
            DictionaryParser.getDictionary(Data.getSelectedDictionary()));
    Data.getPlayerClient()
        .getClientThread()
        .sendMessageToServer(new MakeTurnMessage(Data.getCurrentUser(), Data.getGameSession()));
  }

  /**
   * Refreshes the UI and therefore changes like player joined or chat Messages received. Use Case
   * 3.3 and 5 within.
   *
   * @author nitterhe, mherre
   */
  private void refreshUi() {

    Thread t =
        new Thread(
            new Runnable() {

              @Override
              public void run() {

                while (Data.getPlayerClient().getClientThread().isAlive()) {

                  // initialization of used collections
                  Server uiServer = Data.getPlayerClient().getCurrentServer();
                  ServerStatistics sd = uiServer.getServerStatistics();
                  Iterator<ClientData> iterator = uiServer.getClients().values().iterator();

                  playerAmount = uiServer.getClientCounter();

                  Platform.runLater(
                      new Runnable() {

                        @Override
                        public void run() {

                          // updates the chat
                          chatBox.setText(Data.getChatHistory().toString());

                          // switches the layer to in game is the game has started
                          if (Data.getPlayerClient().getStarting()) {
                            try {
                              Data.getPlayerClient().setStarting(false);
                              Data.getPlayerClient().setReady(false, null);
                              App.setRoot("MultiPlayer");
                            } catch (IOException e) {
                              e.printStackTrace();
                            }
                          }

                          boolean start = true;

                          // sets the texts on the labels for each player
                          ClientData client;
                          if (iterator.hasNext()) {
                            client = iterator.next();
                            player1.setText(client.getUsername());
                            ready1.setText(client.isReady() ? "Ready" : "Not Ready");
                            if (!client.isReady()) {
                              start = false;
                            }
                          } else {
                            player1.setText("");
                            ready1.setText("");
                          }

                          if (iterator.hasNext()) {
                            client = iterator.next();
                            player2.setText(client.getUsername());
                            ready2.setText(client.isReady() ? "Ready" : "Not Ready");
                            if (isHost) {
                              kick2.setOpacity(1.0);
                              if (client.getIp().equals("AI")) {
                                difficulty2.setOpacity(1.0);
                                diffSelection2.setOpacity(1.0);
                                diffButton1.setOpacity(1.0);
                              }
                            }
                            if (client.getIp().equals("AI")) {
                              difficulty2.setOpacity(1.0);
                              diffSelection2.setOpacity(1.0);
                              diffButton1.setOpacity(1.0);
                            }
                            if (!client.isReady()) {
                              start = false;
                            }
                          } else {
                            player2.setText("");
                            ready2.setText("");
                            difficulty2.setOpacity(0);
                            diffSelection2.setOpacity(0);
                            diffButton1.setOpacity(0);
                            kick2.setOpacity(0);
                          }

                          if (iterator.hasNext()) {
                            client = iterator.next();
                            player3.setText(client.getUsername());
                            ready3.setText(client.isReady() ? "Ready" : "Not Ready");
                            if (isHost) {
                              kick3.setOpacity(1.0);
                              if (client.getIp().equals("AI")) {
                                difficulty3.setOpacity(1.0);
                                diffSelection3.setOpacity(1.0);
                                diffButton2.setOpacity(1.0);
                              }
                            }
                            if (client.getIp().equals("AI")) {
                              difficulty3.setOpacity(1.0);
                              diffSelection3.setOpacity(1.0);
                              diffButton2.setOpacity(1.0);
                            }
                            if (!client.isReady()) {
                              start = false;
                            }
                          } else {
                            player3.setText("");
                            ready3.setText("");
                            difficulty3.setOpacity(0);
                            diffSelection3.setOpacity(0);
                            diffButton2.setOpacity(0);
                            kick3.setOpacity(0);
                          }

                          if (iterator.hasNext()) {
                            client = iterator.next();
                            player4.setText(client.getUsername());
                            ready4.setText(client.isReady() ? "Ready" : "Not Ready");
                            if (isHost) {
                              kick4.setOpacity(1.0);
                              if (client.getIp().equals("AI")) {
                                difficulty4.setOpacity(1.0);
                                diffSelection4.setOpacity(1.0);
                                diffButton3.setOpacity(1.0);
                              }
                            }
                            if (client.getIp().equals("AI")) {
                              difficulty4.setOpacity(1.0);
                              diffSelection4.setOpacity(1.0);
                              diffButton3.setOpacity(1.0);
                            }
                            if (!client.isReady()) {
                              start = false;
                            }
                          } else {
                            player4.setText("");
                            ready4.setText("");
                            difficulty4.setOpacity(0);
                            diffSelection4.setOpacity(0);
                            diffButton3.setOpacity(0);
                            kick4.setOpacity(0);
                          }

                          // sets the posibility to start the game for the host
                          if (start
                              && Data.getPlayerClient().getCurrentServer().getClients().size() > 1
                              && isHost) {
                            startButton.setOpacity(1.0);
                            startButton.setOnMouseClicked(
                                new EventHandler<>() {

                                  @Override
                                  public void handle(MouseEvent event) {
                                    try {
                                      startGame(event);
                                    } catch (IOException | SQLException e) {
                                      e.printStackTrace();
                                    }
                                  }
                                });
                          } else {
                            startButton.setOpacity(0);
                            startButton.setOnMouseClicked(null);
                          }

                          // loads and displays the lobby statistics
                          ClientStatistic help;
                          if (null != (help = sd.get(1))) {
                            playerNameStats1.setText(help.getClientName());
                            played1.setText("" + help.getGamesPlayed());
                            won1.setText("" + help.getGamesWon());
                            if (help.getGamesPlayed() > 0) {
                              score1.setText(
                                  100 * (help.getGamesWon() / help.getGamesPlayed()) + "");
                            } else {
                              score1.setText("0");
                            }
                          }
                          if (null != (help = sd.get(2))) {
                            playerNameStats2.setText(help.getClientName());
                            played2.setText("" + help.getGamesPlayed());
                            won2.setText("" + help.getGamesWon());
                            if (help.getGamesPlayed() > 0) {
                              score2.setText(
                                  100 * (help.getGamesWon() / help.getGamesPlayed()) + "");
                            } else {
                              score2.setText("0");
                            }
                          }
                          if (null != (help = sd.get(3))) {
                            playerNameStats3.setText(help.getClientName());
                            played3.setText("" + help.getGamesPlayed());
                            won3.setText("" + help.getGamesWon());
                            if (help.getGamesPlayed() > 0) {
                              score3.setText(
                                  100 * (help.getGamesWon() / help.getGamesPlayed()) + "");
                            } else {
                              score3.setText("0");
                            }
                          }
                          if (null != (help = sd.get(4))) {
                            playerNameStats4.setText(help.getClientName());
                            played4.setText("" + help.getGamesPlayed());
                            won4.setText("" + help.getGamesWon());
                            if (help.getGamesPlayed() > 0) {
                              score4.setText(
                                  100 * (help.getGamesWon() / help.getGamesPlayed()) + "");
                            } else {
                              score4.setText("0");
                            }
                          }
                        }
                      });

                  isClickable();
                  // delay for less lag
                  synchronized (this) {
                    try {
                      this.wait(200);
                    } catch (InterruptedException e) {
                      // e.printStackTrace();
                    }
                  }
                }
              }
            });
    t.start();
  }

  /**
   * Checks if the lobby contains the maximum amount of players. If so, the "Add Player"-button gets
   * grayed out. If not, then the "Add Player"-button either stays the same or it isn't grayed out
   * any more. Makes sure only the host can add players.
   *
   * @author mherre
   */
  protected boolean isClickable() {
    if (playerAmount == Data.getPlayerClient().getCurrentServer().getClientMaximum()
        || !this.isHost) {
      this.addPlayerButton.setY(-44);
      this.addPlayerButton.setOpacity(1);
      return false;

    } else {
      this.addPlayerButton.setY(0);
      this.addPlayerButton.setOpacity(0);
      return true;
    }
  }
}
