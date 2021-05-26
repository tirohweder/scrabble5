package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;

public class SingleplayerController extends InGameController implements Initializable {

  private int roundNumber;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    aiTurn();
    refreshUI();
    roundNumber = Data.getGameSession().getRoundNumber();

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

              if (Data.getGameSession().getRoundNumber() != roundNumber) {
                try {
                  initGameboard();
                } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
                roundNumber = Data.getGameSession().getRoundNumber();
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
