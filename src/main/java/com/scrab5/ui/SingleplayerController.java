package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;

public class SingleplayerController extends InGameController implements Initializable {
  
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    refreshUI();

  }
  
  private void refreshUI() {

    Thread t = new Thread(new Runnable() {

      @Override
      public void run() {

        while (!Data.getGameSession().isShouldEnd()) {

          Platform.runLater(new Runnable() {

            @Override
            public void run() {

              initPlayers();
              initRack();
              initGameboard();

            }
          }
              );
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
  