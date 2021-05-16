package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import com.scrab5.core.game.GameSession;
import com.scrab5.core.player.Player;
import com.scrab5.network.Client;
import com.scrab5.network.ClientData;
import com.scrab5.network.Server;
import com.scrab5.network.ServerStatistics;
import com.scrab5.network.ServerStatistics.ClientStatistic;
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
 * The MultiplayerLobbyController class is supposed to control the of the MultiplayerLobby screen
 *
 * @author mherre
 */
public class MultiplayerLobbyController extends LobbyController implements Initializable {

  @FXML
  private Label player1, player2, player3, player4;
  @FXML
  private Label ready1, ready2, ready3, ready4;
  @FXML
  private Label playerNameStats1, playerNameStats2, playerNameStats3, playerNameStats4;
  @FXML
  private Label played1, played2, played3, played4;
  @FXML
  private Label won1, won2, won3, won4;
  @FXML
  private Label score1, score2, score3, score4;
  @FXML
  private ImageView customizeButton;
  @FXML
  private TextArea chatBox;
  @FXML
  private TextField messageTextField;

  private boolean isReady = false;
  private int aiPlayerAmount = 0;
  private static StringBuffer chatHistory = new StringBuffer();
  private LinkedList<Client> AIs;

  /**
   * @author mherre
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.isClickable();
    this.setUpInit();

    if (Data.getPlayerClient().getUsername()
        .equals(Data.getPlayerClient().getCurrentServer().getHost())) {
    } else {
      this.customizeButton.setY(-34);
      this.customizeButton.setOpacity(1.0);

    }

    this.refreshUI();

  }

  /**
   * Called when the lobby was closed by the host.
   *
   * @throws IOException
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
   * This method is called when the "Back"-button is clicked. It sets the scene to
   * "MultiplayerOverview"
   *
   * @param event
   * @throws IOException
   * @author mherre @author nitterhe
   */
  @FXML
  protected void back(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    Data.getPlayerClient().disconnectFromServer();
  }

  @FXML
  private void ready(MouseEvent event) throws IOException {

    playSound("ButtonClicked.mp3");

    this.isReady = !isReady;
    Data.getPlayerClient().setReady(this.isReady);
  }

  @FXML
  private void enterChatMessage(MouseEvent event) {

    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        messageTextField.selectAll();
        Data.getPlayerClient()
            .sendChatMessage(Data.getCurrentUser() + ": " + messageTextField.getText() + "\n");
      }
    });

  }


  protected void addPlayer(MouseEvent event) throws IOException {

    // AIs.add(new Client("Der Zerstörinator"+(AIs.size()+1)));

    playSound("ButtonClicked.mp3");
    this.aiPlayerAmount++;

    for (int i = 1; i < Data.getPlayerCountMultiplayer() - 1; i++) {
      if (freeSpaces[i]) {
        switch (i) {
          case 1:
            this.player3.setText("CPU 3");
            this.ready3.setText("Ready");
            this.difficulty3.setOpacity(1.0);
            this.kick3.setOpacity(1.0);
            this.diffSelection3.setOpacity(1.0);
            this.diffButton2.setOpacity(1.0);
            // isReady[i + 1] = true;
            break;
          case 2:
            this.player4.setText("CPU 4");
            this.ready4.setText("Ready");
            this.difficulty4.setOpacity(1.0);
            this.kick4.setOpacity(1.0);
            this.diffSelection4.setOpacity(1.0);
            this.diffButton3.setOpacity(1.0);
            // isReady[i + 1] = true;
            break;
          default:
            break;
        }
        this.freeSpaces[i] = false;
        break;
      }
    }
    this.isClickable();
  }


  @FXML
  protected void dontShow(MouseEvent event) throws IOException {
    voteSelection1.hide();
    voteSelection2.hide();
    voteSelection3.hide();
    voteSelection4.hide();
    diffBox1.hide();
    diffBox2.hide();
    diffBox3.hide();
  }

  @FXML
  protected void kickPlayer2(MouseEvent event) {
    // TODO Auto-generated method stub

  }

  @FXML
  protected void kickPlayer3(MouseEvent event) {

    if (kick3.getOpacity() == 1.0) { // AI oder echter Spieler?
      playSound("ButtonClicked.mp3");
      this.player3.setText("");
      this.ready3.setText("");
      this.difficulty3.setOpacity(0);
      this.diffSelection3.setOpacity(0);
      this.diffButton2.setOpacity(0);
      this.kick3.setOpacity(0);
      this.playerAmount--;
      this.freeSpaces[1] = true;
      this.isClickable();
    }
  }

  @FXML
  protected void kickPlayer4(MouseEvent event) {

    if (kick4.getOpacity() == 1.0) { // AI oder echter Spieler?
      playSound("ButtonClicked.mp3");
      this.player4.setText("");
      this.ready4.setText("");
      this.difficulty4.setOpacity(0);
      this.diffSelection4.setOpacity(0);
      this.diffButton3.setOpacity(0);
      this.kick4.setOpacity(0);
      this.playerAmount--;
      this.freeSpaces[2] = true;
      this.isClickable();
    }

  }

  protected void setUpInit() {
    this.isDictionarySelected = true;
    this.player1.setText(Data.getCurrentUser());
    this.ready1.setText("Not Ready");
    for (int i = 1; i <= 4; i++) {
      this.voteSelection1.getItems().add(i);
      this.voteSelection2.getItems().add(i);
      this.voteSelection3.getItems().add(i);
      this.voteSelection4.getItems().add(i);
    }
    this.voteSelection1.getSelectionModel().select(0);
    this.voteSelection2.getSelectionModel().select(1);
    this.voteSelection3.getSelectionModel().select(2);
    this.voteSelection4.getSelectionModel().select(3);

    this.diffBox2.getItems().add("Easy");
    this.diffBox2.getItems().add("Difficult");
    this.diffBox3.getItems().add("Easy");
    this.diffBox3.getItems().add("Difficult");

    this.diffBox2.getSelectionModel().select(0);
    this.diffBox3.getSelectionModel().select(0);
  }

  protected boolean isClickable() {
    if (playerAmount >= Data.getPlayerCountMultiplayer() - 1) {
      this.addPlayerButton.setY(-44);
      this.addPlayerButton.setOpacity(1);
      return false;

    } else {
      this.addPlayerButton.setY(0);
      this.addPlayerButton.setOpacity(0);
      return true;
    }
  }

  @Override
  protected void startGame(MouseEvent event) throws IOException, SQLException {
    // TODO Auto-generated method stub

    ArrayList<Player> playerList = new ArrayList<Player>();
    for (String clientName : Data.getPlayerClient().getCurrentServer().getClients().keySet()) {

      int vote1 = 0;

      playerList.add(new Player(clientName));

    }

    if (Data.getHasBeenEdited()) {
      ArrayList<Integer> pointsDito = Data.getPointsDistribution();
      ArrayList<Integer> occurrencyDisto = Data.getOccurrencyDistribution();

      Data.setGameSession(new GameSession(playerList, pointsDito, occurrencyDisto, true));

    } else {
      Data.setGameSession(new GameSession(playerList, true));
      System.out.println("Online GameSession created");
    }

    App.setRoot("Multiplayer");
  }


  /**
   * @author nitterhe
   */
  public void refreshUI() {

    Thread t = new Thread(new Runnable() {

      @Override
      public void run() {



        while (Data.getPlayerClient().getClientThread().isAlive()) {

          Server UIServer = Data.getPlayerClient().getCurrentServer();
          ServerStatistics sd = UIServer.getServerStatistics();
          Iterator<ClientData> iterator = UIServer.getClients().values().iterator();

          playerAmount = UIServer.getClientCounter() + aiPlayerAmount;

          Platform.runLater(new Runnable() {

            @Override
            public void run() {

              chatBox.setText(chatHistory.toString());

              boolean start = true;
              ClientData client;
              if (iterator.hasNext()) {
                client = iterator.next();
                player1.setText(client.getUsername());
                ready1.setText(client.isReady() ? "Ready" : "Not Ready");
                if (!client.isReady())
                  start = false;
              } else {
                player1.setText("");
                ready1.setText("");
              }

              if (iterator.hasNext()) {
                client = iterator.next();
                player2.setText(client.getUsername());
                ready2.setText(client.isReady() ? "Ready" : "Not Ready");
                if (!client.isReady())
                  start = false;
              } else {
                player2.setText("");
                ready2.setText("");
              }

              if (iterator.hasNext()) {
                client = iterator.next();
                player3.setText(client.getUsername());
                ready3.setText(client.isReady() ? "Ready" : "Not Ready");
                if (!client.isReady())
                  start = false;
              } else {
                player3.setText("");
                ready3.setText("");
              }

              if (iterator.hasNext()) {
                client = iterator.next();
                player4.setText(client.getUsername());
                ready4.setText(client.isReady() ? "Ready" : "Not Ready");
                if (!client.isReady())
                  start = false;
              } else {
                player4.setText("");
                ready4.setText("");
              }

              if (start && Data.getPlayerClient().getCurrentServer().getClients().size() > 1) {
                startButton.setOpacity(1.0);
                startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

                  @Override
                  public void handle(MouseEvent event) {
                    try {
                      startGame(event);
                    } catch (IOException | SQLException e) {
                      e.printStackTrace();
                    }
                  }

                });
              }

              ClientStatistic help;
              if (null != (help = sd.get(1))) {
                playerNameStats1.setText(help.getClientName());
                played1.setText("" + help.getGamesPlayed());
                won1.setText("" + help.getGamesWon());
              }
              if (null != (help = sd.get(2))) {
                playerNameStats2.setText(help.getClientName());
                played2.setText("" + help.getGamesPlayed());
                won2.setText("" + help.getGamesWon());
              }
              if (null != (help = sd.get(3))) {
                playerNameStats3.setText(help.getClientName());
                played3.setText("" + help.getGamesPlayed());
                won3.setText("" + help.getGamesWon());
              }
              if (null != (help = sd.get(4))) {
                playerNameStats4.setText(help.getClientName());
                played4.setText("" + help.getGamesPlayed());
                won4.setText("" + help.getGamesWon());
              }

            }
          });
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


  public static void displayChatMessage(String text) {

  }

  public static StringBuffer getChatHistory() {
    return chatHistory;
  }
}
