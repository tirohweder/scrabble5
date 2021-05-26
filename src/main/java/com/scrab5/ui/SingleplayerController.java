package com.scrab5.ui;

import com.scrab5.core.player.Player;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;

public class SingleplayerController extends InGameController implements Initializable {

  Player current;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    aiTurn();
    refreshUI();
    current = Data.getGameSession().getCurrentPlayer();

  }

  int counter = 0;

  private void refreshUI() {

    Thread t = new Thread(new Runnable() {

      @Override
      public void run() {

        while (!Data.getGameSession().isShouldEnd()) {

          Platform.runLater(new Runnable() {

            @Override
            public void run() {
              initRack();
              initPlayers();

              if (Data.getGameSession().getCurrentPlayer() != current) {
                try {
                  initGameboard();
                } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
                current = Data.getGameSession().getCurrentPlayer();
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

}
