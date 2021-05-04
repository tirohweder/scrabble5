package com.scrab5.ui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public abstract class LobbyController extends Controller {
  @FXML
  protected ImageView kick2, kick3, kick4;
  @FXML
  protected Label player1, player2, player3, player4;
  @FXML
  protected Label ready1, ready2, ready3, ready4;

  private boolean isReady1 = false;



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
    if (!isReady1) {
      this.ready1.setText("Ready");
      this.isReady1 = true;

    } else {
      this.ready1.setText("Not Ready");
      this.isReady1 = false;
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
