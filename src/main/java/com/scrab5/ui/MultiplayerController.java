package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;

/**
 * The MultiplayerController class handles everything that happens during the "Multiplayer" in game.
 * Everything in common with the multiplayer mode is once implemented in the abstract
 * InGameController class and is used by the different specific in game controllers. The multiplayer
 * controller refreshes the UI (rack, gameboard, players/points, buttons and chat) and checks if the
 * game isn't finished with a thread checking several conditions in the InGameController. Controller
 * for MultiPlayer.fxml.
 *
 * @author apilgrim
 */
public class MultiplayerController extends InGameController implements Initializable {

  @FXML
  ImageView chatImage;
  @FXML
  TextFlow chatTextField;
  @FXML
  TextField chatInsert;
  @FXML
  TextArea textArea;

  private boolean chatOpen = false;
  private int roundNumber;

  /**
   * Initializes the rack and player when a game is started. Called method aiTurn() checks if the
   * first turn is by the AI player. After this the thread refreshing the board/ rack/ player
   * attributes/ buttons and chat is called when the roundNumber changed. Which means, that some
   * information or the rack definitely have changed.
   *
   * @author apilgrim
   * @param arg0 URL, arg1 Resourcebundle
   */
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    synchronized (this) {
      try {
        wait(200);
      } catch (Exception e) {
        // do nothing. this is just so dictionaries and database can be loaded and prepared at every
        // client
      }
    }
    initRack();
    initPlayers();
    roundNumber = Data.getGameSession().getRoundNumber();
    try {
      aiTurn();
    } catch (IOException e) {
      e.printStackTrace();
    }
    initGameboard();
    refreshUi();
  }

  /**
   * Is called when "chat" - button is clicked, opens the chat in the game.
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "chat" button is clicked
   */
  @FXML
  private void chatClicked(MouseEvent event) {
    if (chatOpen) {
      chatImage.setOpacity(0);
      chatTextField.setOpacity(0);
      chatInsert.setOpacity(0);
      chatOpen = false;
    } else {
      chatImage.setOpacity(1);
      chatTextField.setOpacity(1);
      chatInsert.setOpacity(1);
      chatOpen = true;
    }
  }

  /**
   * Is called when sent - button is clicked. Everything tipped will be sent to any other player and
   * displayed in the chatBox.
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the sent button is clicked
   */
  @FXML
  private void sentClicked(MouseEvent event) {
    if (chatOpen) {
      Platform.runLater(new Runnable() {

        @Override
        public void run() {
          chatInsert.selectAll();
          Data.getPlayerClient()
              .sendChatMessage(Data.getCurrentUser() + ": " + chatInsert.getText() + "\n");
        }
      });
    }
  }

  /**
   * Event method that is called when the "Enter"-key on the keyboard is clicked. It sends the
   * entered message to every client and refreshes the chat UI.
   *
   * @param event the event that is created from the mouse-click
   * @author mherre
   */
  @FXML
  private void enterPressedChatMessage(KeyEvent event) throws IOException {
    if (event.getCode() == KeyCode.ENTER) {
      Platform.runLater(new Runnable() {

        @Override
        public void run() {
          chatInsert.selectAll();
          Data.getPlayerClient()
              .sendChatMessage(Data.getCurrentUser() + ": " + chatInsert.getText() + "\n");
        }
      });
    }
  }

  @FXML
  private void chatInsertClicked(MouseEvent event) {}

  /**
   * Use Case 3.3 within. Thread started when the singleplayer game is started. Refreshes the UI
   * elements by calling methods from InGameController which are checking, if something on the board
   * or on the rack/ points changed. Connected to network.This implementation is chosen to provide 3
   * frames per second.
   *
   * @author apilgrim @author nitterhe
   */
  private void refreshUi() {

    Thread t = new Thread(new Runnable() {

      @Override
      public void run() {

        while (Data.getPlayerClient().threadIsRunning()) {

          if (Data.getGameSession().isShouldEnd()) {
            Data.getPlayerClient().endGame();
            Data.getGameSession().setShouldEnd(false);
            try {
              App.setRoot("EndGameMultiplayer");
            } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          Platform.runLater(new Runnable() {

            @Override
            public void run() {

              textArea.setText(Data.getChatHistory().toString());
              try {
                initButtons();
              } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
              if (Data.getGameSession().getRoundNumber() != roundNumber) {
                initPlayers();
                initRack();
                initGameboard();
                roundNumber = Data.getGameSession().getRoundNumber();
              }
            }
          });
          synchronized (this) {
            try {
              this.wait(300);
            } catch (InterruptedException e) {
              // e.printStackTrace();
            }
          }
        }
        // check for bag and rack empty to end the game and display statistics
        if (Data.getGameSession().isShouldEnd()) {
          if (Data.getGameSession().isRunning()) {
            Data.getGameSession().endGame();
            try {
              App.setRoot("EndGameMultiplayer");
            } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
        }
      }
    });
    t.start();
  }
}
