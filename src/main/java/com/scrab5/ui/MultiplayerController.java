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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;


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

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    synchronized (this) {
      try {
        wait(300);
      } catch (Exception e) {
        // do nothing. this is just so dictionaries and database can be loaded and prepared at every
        // client
      }
    }

    aiTurn();

    refreshUI();

  }

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


  @FXML
  private void chatInsertClicked(MouseEvent event) {


  }

  /**
   * Use Case 3.3 within.
   * 
   * @author nitterhe @author apilgrem
   */
  private void refreshUI() {

    Thread t = new Thread(new Runnable() {

      @Override
      public void run() {

        while (Data.getGameSession().isRunning()) {

          Platform.runLater(new Runnable() {

            @Override
            public void run() {

              textArea.setText(Data.getChatHistory().toString());

              initPlayers();
              initRack();
              try {
                initGameboard();
              } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
      }
    });
    t.start();
  }

}
