package com.scrab5.ui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public abstract class LobbyController extends Controller {
  @FXML
  protected ImageView kick2, kick3, kick4, addPlayerButton;
  @FXML
  protected Label player1, player2, player3, player4;
  @FXML
  protected Label ready1, ready2, ready3, ready4;

  protected boolean isReady[] = {false, false, false, false};
  protected boolean isDictionarySelected = false;



  /**
   * Event method that is called when the "Ready"-button is clicked.
   * 
   * @author mherre
   * @param event
   * @throws IOException
   */
  @FXML
  private void ready(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    if (!isReady[0]) {
      this.ready1.setText("Ready");
      this.isReady[0] = true;

      if (this.isReady[1] && this.isReady[2] && this.isReady[3] && this.isDictionarySelected) {
        App.setRoot("SinglePlayer");

      } else {
        String message = "You must select a dictionary in order to play the game!";
        PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.ERROR);
        pum.show();

      }

    } else {
      this.ready1.setText("Not Ready");
      this.isReady[0] = false;

    }
  }

  @FXML
  private void customize(MouseEvent event) {

  }

  /**
   * Event method that is called when the "Back"-button is clicked. Scene gets changed to the
   * predecessor "MainMenu" scene
   * 
   * @author mherre
   * @param event
   * @throws IOException
   */
  @FXML
  abstract protected void back(MouseEvent event) throws IOException;

  @FXML
  abstract protected void addPlayer(MouseEvent event) throws IOException;

  @FXML
  abstract protected void kickPlayer2(MouseEvent event);

  @FXML
  abstract protected void kickPlayer3(MouseEvent event);

  @FXML
  abstract protected void kickPlayer4(MouseEvent event);
}
