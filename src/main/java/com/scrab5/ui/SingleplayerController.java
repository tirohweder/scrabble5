package com.scrab5.ui;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;

/**
 * The SingleplayerController class handels everything that happens during the "Singleplayer" in
 * game. Everything in common with the multiplayer mode is once implemented in the abstract
 * InGameController class and is used by the differnet specific in game controllers. The
 * singleplayer controller refreshs the UI (rack and gameboard) and checks if the game isn't
 * finished with a thread checking several conditions in the InGameController. Controller for
 * SinglePlayer.fxml.
 *
 * @author apilgrim
 */
public class SingleplayerController extends InGameController implements Initializable {

  int counter = 0;
  private int roundNumber;

  /**
   * Initializes the rack and player when game is started. Called method aiTurn() checks if the
   * first turn is by the AI player. After this the thread refreshing the board/ rack/ player
   * attributes/ buttons is called when the roundNumber changed. Which means, that some information
   * or the rack definetly have changed.
   * 
   * @author apilgrim
   * @param arg0 URL, arg1 Resourcebundle
   */
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    roundNumber = Data.getGameSession().getRoundNumber();
    try {
      aiTurn();
    } catch (IOException e) {
      e.printStackTrace();
    }
    refreshUI();
    initGameboard();
  }

  /**
   * Thread started when the singleplayer game is started. Refreshs the UI elements by calling
   * methods from InGameController which are checking, if something did change.
   * 
   * @author apilgrim
   * 
   */
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
              try {
                initButtons();
              } catch (IOException e) {
                e.printStackTrace();
              }

              if (Data.getGameSession().getRoundNumber() != roundNumber) {
                initGameboard();
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
