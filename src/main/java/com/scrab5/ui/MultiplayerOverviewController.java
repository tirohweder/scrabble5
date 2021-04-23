package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MultiplayerOverviewController extends Controller implements Initializable {

  @FXML
  private Label userPlaying;
  @FXML
  private ImageView playerNumber;
  @FXML
  private ImageView arrowRight;
  @FXML
  private ImageView arrowLeft;
  @FXML
  private ComboBox<String> dictionarySelection;
  @FXML
  private ImageView dropDownButton;

  private int playerCount = 2;

  /**
   * Is called right after the scene is loaded. Sets the displayed username to the username of the
   * current selected user / logged in user.
   * 
   * @author mherre
   */
  public void initialize(URL arg0, ResourceBundle arg1) {
    this.userPlaying.setText(Data.getCurrentUser());
  }

  @FXML
  private void start(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    App.setRoot("MultiplayerLobby");
  }

  /**
   * 
   * Event method that is called when the "Back"-button is clicked. Scene gets changed to the
   * predecessor "Main Menu" scene
   * 
   * @author mherre
   * @param event
   * @throws IOException
   */
  @FXML
  private void back(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    App.setRoot("MainMenu");
  }

  /**
   * Event method that is called when the "Right Arrow"-button is clicked. The "Number of Players"
   * number gets increased by one.Changes "Right Arrow"-button to a greyed out image of it which
   * can't be clicked.
   * 
   * @author mherre
   * @param event
   * @throws IOException
   */
  @FXML
  private void nextNumber(MouseEvent event) throws IOException {
    playerCount++;
    playSound("ButtonClicked.mp3");

    if (playerCount > 4) {
      playerCount--;

    } else if (playerCount == 4) {
      this.changeNumberImage();
      this.arrowRight.setLayoutY(458);
      this.arrowRight.setOpacity(1);

    } else if (playerCount > 2 && this.arrowLeft.getOpacity() == 1) {
      this.changeNumberImage();
      this.arrowLeft.setLayoutY(500);
      this.arrowLeft.setOpacity(0);

    } else {
      this.changeNumberImage();

    }
  }

  /**
   * Event method that is called when the "Left Arrow"-button is clicked. The "Number of Players"
   * number gets decreased by one. Changes "Left Arrow"-button to a greyed out image of it which
   * can't be clicked.
   * 
   * @author mherre
   * @param event
   * @throws IOException
   * 
   */
  @FXML
  private void previousNumber(MouseEvent event) throws IOException {
    playerCount--;
    playSound("ButtonClicked.mp3");

    if (playerCount < 2) {
      playerCount++;

    } else if (playerCount == 2) {
      this.changeNumberImage();
      this.arrowLeft.setLayoutY(458);
      this.arrowLeft.setOpacity(1);

    } else if (playerCount < 4 && this.arrowRight.getOpacity() == 1) {
      this.changeNumberImage();
      this.arrowRight.setLayoutY(500);
      this.arrowRight.setOpacity(0);

    } else {
      this.changeNumberImage();

    }
  }

  /**
   *
   * Is called when the "dropDownButton" Button is clicked. Opens the combo box displaying all
   * selectable dictionaries.
   * 
   * @author mherre
   * @param event
   * @throws IOException
   * 
   * 
   */
  @FXML
  private void clickComboBox(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    dictionarySelection.show();

  }

  /**
   * 
   * 
   * @author mherre
   * @param event
   */
  @FXML
  private void refresh(MouseEvent event) {
    playSound("ButtonClicked.mp3");

  }

  @FXML
  private void findGame(MouseEvent event) {
    playSound("ButtonClicked.mp3");

  }

  /**
   * 
   * Makes dictionarySelection only openable when the dropDownButton is clicked, not when the
   * combobox (dictionarySelection) is clicked.
   * 
   * @author mherre
   * @param event
   * @throws IOException
   * 
   */
  @FXML
  private void dontShow(MouseEvent event) throws IOException {
    dictionarySelection.hide();
  }

  /**
   * 
   * Changes the image of the current playerCount.
   * 
   * @author mherre
   */
  private void changeNumberImage() {
    Image img = null;
    switch (playerCount) {
      case 2:
        img = new Image("/com/scrab5/ui/images/SB04_2.png", 20, 28, false, false);
        break;
      case 3:
        img = new Image("/com/scrab5/ui/images/SB04_3.png", 21, 28, false, false);
        break;
      case 4:
        img = new Image("/com/scrab5/ui/images/SB04_4.png", 23, 28, false, false);
      default:
        break;
    }
    playerNumber.setImage(img);
  }

}
