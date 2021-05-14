package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import com.scrab5.network.ClientData;
import com.scrab5.network.Server;
import com.scrab5.util.database.PlayerProfileDatabase;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * The MultiplayerLobbyController class is supposed to control the of the MultiplayerLobby screen
 * 
 * @author mherre
 *
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

  private boolean isReady1 = false;

  /**
   * 
   * 
   * @author mherre
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.isClickable();
    setUpInit();

    refreshUI();

    String name = Data.getCurrentUser();
    this.playerNameStats1.setText(name);
    this.played1.setText(PlayerProfileDatabase.getTotalPlayedGames(name) + "");
    this.won1.setText(PlayerProfileDatabase.getTotalWins(name) + "");
    this.score1.setText(PlayerProfileDatabase.getTotalPoints(name) + "");
  }

  /**
   * Called when the lobby was closed by the host.
   * 
   * @author nitterhe
   * @throws IOException
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
   * @author mherre @author nitterhe
   * @param event
   * @throws IOException
   */
  @FXML
  protected void back(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    Data.getPlayerClient().disconnectFromServer();
  }

  @FXML
  private void ready(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    if (!isReady1) {
      this.ready1.setText("Ready");
      this.isReady1 = true;

    } else {
      this.ready1.setText("Not Ready");
      this.isReady1 = false;
      Data.getHostedServer().startGame();
    }
  }

  @FXML
  private void enterChatMessage(MouseEvent event) {

  }


  protected void addPlayer(MouseEvent event) throws IOException {

    playSound("ButtonClicked.mp3");
    this.playerAmount++;

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
            isReady[i + 1] = true;
            break;
          case 2:
            this.player4.setText("CPU 4");
            this.ready4.setText("Ready");
            this.difficulty4.setOpacity(1.0);
            this.kick4.setOpacity(1.0);
            this.diffSelection4.setOpacity(1.0);
            this.diffButton3.setOpacity(1.0);
            isReady[i + 1] = true;
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


  protected void kickPlayer2(MouseEvent event) {
    // TODO Auto-generated method stub

  }


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
  protected void startGame(MouseEvent event) throws IOException {
    // TODO Auto-generated method stub

  }

  public void refreshUI() {

    Thread t = new Thread(new Runnable() {

      @Override
      public void run() {
        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHH");

        while (Data.getPlayerClient().getClientThread().isAlive()) {
          Server UIServer = Data.getPlayerClient().getCurrentServer();
          Iterator<ClientData> iterator = UIServer.getClients().values().iterator();


          Platform.runLater(new Runnable() {

            @Override
            public void run() {
              if (iterator.hasNext()) {
                ClientData client1 = iterator.next();
                player1.setText(client1.getUsername());
              }

              if (iterator.hasNext()) {
                ClientData client2 = iterator.next();
                player2.setText(client2.getUsername());

              }
              if (iterator.hasNext()) {
                ClientData client3 = iterator.next();
                player3.setText(client3.getUsername());
              }
              if (iterator.hasNext()) {
                ClientData client4 = iterator.next();
                player4.setText(client4.getUsername());
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

    // 1. clients refresh
    // 2. leaderboard
    // 3. gameState

    t.start();
  }


  public static void displayChatMessage(String text) {

  }
}
