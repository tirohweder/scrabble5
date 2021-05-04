package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * The SingleplayerLobbyController class is supposed to control the components of
 * SinglePlayerLobby.fxml
 * 
 * @author mherre
 */

public class SingleplayerLobbyController extends LobbyController implements Initializable {

  @FXML
  private ImageView addPlayerButton;
  private int playerAmount = 1;
  private boolean[] freeSpaces = {true, true, true};

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.player1.setText(Data.getCurrentUser());
    this.ready1.setText("Not Ready");

  }

  @FXML
  protected void back(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    App.setRoot("MainMenu");

  }

  @FXML
  protected void addPlayer(MouseEvent event) {

    playSound("ButtonClicked.mp3");
    this.playerAmount++;

    for (int i = 0; i < freeSpaces.length; i++) {
      if (freeSpaces[i]) {
        switch (i) {
          case 0:
            this.player2.setText("CPU 2");
            this.ready2.setText("Ready");
            this.kick2.setOpacity(1.0);
            break;
          case 1:
            this.player3.setText("CPU 3");
            this.ready3.setText("Ready");
            this.kick3.setOpacity(1.0);
            break;
          case 2:
            this.player4.setText("CPU 4");
            this.ready4.setText("Ready");
            this.kick4.setOpacity(1.0);
            break;
          default:
            break;
        }
        this.freeSpaces[i] = false;
        break;
      }
    }
    if (playerAmount >= 4) {
      this.addPlayerButton.setY(400);
      this.addPlayerButton.setOpacity(1);
    }
  }

  @FXML
  protected void kickPlayer2(MouseEvent event) {

    this.player2.setText("");
    this.ready2.setText("");
    this.kick2.setOpacity(0);
    this.playerAmount--;
    this.freeSpaces[0] = true;
  }

  @FXML
  protected void kickPlayer3(MouseEvent event) {
    this.player3.setText("");
    this.ready3.setText("");
    this.kick3.setOpacity(0);
    this.playerAmount--;
    this.freeSpaces[1] = true;


  }

  @FXML
  protected void kickPlayer4(MouseEvent event) {

    this.player4.setText("");
    this.ready4.setText("");
    this.kick4.setOpacity(0);
    this.playerAmount--;
    this.freeSpaces[2] = true;

  }

}
