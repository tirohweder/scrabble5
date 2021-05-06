package com.scrab5.ui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class LobbyController extends Controller {
  @FXML
  protected ImageView kick2, kick3, kick4, addPlayerButton, darkBackground;
  @FXML
  protected Label player1, player2, player3, player4;
  @FXML
  protected Label ready1, ready2, ready3, ready4;
  @FXML
  protected Label vote1, vote2, vote3, vote4;
  @FXML
  protected ComboBox<Integer> voteSelection1, voteSelection2, voteSelection3, voteSelection4;

  protected int playerAmount = 1;
  protected boolean isReady[] = {false, true, true, true};
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

        if (this.playerAmount >= 2) {
          App.setRoot("SinglePlayer");

        } else {
          String message = "Please add at least one another player in order to play the game!";
          PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.ERROR);
          pum.show();
          this.ready1.setText("Not Ready");
          this.isReady[0] = false;

        }

      } else {
        String message = "You must select a dictionary in order to play the game!";
        PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.ERROR);
        pum.show();
        this.ready1.setText("Not Ready");
        this.isReady[0] = false;

      }

    } else {
      this.ready1.setText("Not Ready");
      this.isReady[0] = false;

    }
  }

  @FXML
  private void customize(MouseEvent event) throws IOException {

    playSound("ButtonClicked.mp3");
    this.darkBackground.setFitWidth(1360);
    this.darkBackground.setFitHeight(768);
    this.darkBackground.setOpacity(1);

    Stage customScreen = new Stage();
    customScreen.setScene(new Scene(loadFXML("LetterCustomization"), 622, 650));
    customScreen.initModality(Modality.APPLICATION_MODAL);
    customScreen.initStyle(StageStyle.UNDECORATED);
    customScreen.showAndWait();

    playSound("ButtonClicked.mp3");
    this.darkBackground.setFitWidth(10);
    this.darkBackground.setFitHeight(10);
    this.darkBackground.setOpacity(0);
  }

  @FXML
  private void clickComboBox1(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    this.voteSelection1.show();
  }

  @FXML
  private void clickComboBox2(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    this.voteSelection2.show();
  }

  @FXML
  private void clickComboBox3(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    this.voteSelection3.show();
  }

  @FXML
  private void clickComboBox4(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    this.voteSelection4.show();
  }

  @FXML
  private void setPlayerVote(ActionEvent event) {
    ComboBox<Integer> temp = (ComboBox<Integer>) event.getSource();
    this.vote1.setText(temp.getValue() + "");
  }

  protected void setUpInit() {
    this.player1.setText(Data.getCurrentUser());
    this.ready1.setText("Not Ready");

    for (int i = 1; i <= 4; i++) {
      voteSelection1.getItems().add(i);
      voteSelection2.getItems().add(i);
      voteSelection3.getItems().add(i);
      voteSelection4.getItems().add(i);
    }
  }

  /**
   * @author trohwede
   * @param fxml
   * @return
   * @throws IOException
   */
  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }


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
