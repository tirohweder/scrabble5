package com.scrab5.ui;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * The SingleplayerLobbyController class is supposed to control the components of
 * SinglePlayerLobby.fxml
 * 
 * @author mherre
 */

public class SingleplayerLobbyController extends LobbyController implements Initializable {

  @FXML
  private ComboBox<String> dictionarySelection;
  @FXML
  private ImageView dropDownButton;
  @FXML
  private Label dicDisplaying;
  private boolean[] freeSpaces = {true, true, true};

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.player1.setText(Data.getCurrentUser());
    this.ready1.setText("Not Ready");
    this.setUpDicitionaryBox();

  }

  @FXML
  protected void back(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    App.setRoot("MainMenu");

  }

  @FXML
  protected void addPlayer(MouseEvent event) {

    playSound("ButtonClicked.mp3");
    this.playerAmount++;

    for (int i = 0; i < freeSpaces.length; i++) {
      if (freeSpaces[i]) {
        switch (i) {
          case 0:
            this.player2.setText("CPU 2");
            this.ready2.setText("Ready");
            this.kick2.setOpacity(1.0);
            isReady[i + 1] = true;
            break;
          case 1:
            this.player3.setText("CPU 3");
            this.ready3.setText("Ready");
            this.kick3.setOpacity(1.0);
            isReady[i + 1] = true;
            break;
          case 2:
            this.player4.setText("CPU 4");
            this.ready4.setText("Ready");
            this.kick4.setOpacity(1.0);
            isReady[i + 1] = true;
            break;
          default:
            break;
        }
        this.freeSpaces[i] = false;
        break;
      }
    }
    this.isClickable();
  }

  @FXML
  protected void kickPlayer2(MouseEvent event) {

    playSound("ButtonClicked.mp3");
    this.player2.setText("");
    this.ready2.setText("");
    this.kick2.setOpacity(0);
    this.playerAmount--;
    this.freeSpaces[0] = true;
    this.isClickable();
  }

  @FXML
  protected void kickPlayer3(MouseEvent event) {

    playSound("ButtonClicked.mp3");
    this.player3.setText("");
    this.ready3.setText("");
    this.kick3.setOpacity(0);
    this.playerAmount--;
    this.freeSpaces[1] = true;
    this.isClickable();


  }

  @FXML
  protected void kickPlayer4(MouseEvent event) {

    playSound("ButtonClicked.mp3");
    this.player4.setText("");
    this.ready4.setText("");
    this.kick4.setOpacity(0);
    this.playerAmount--;
    this.freeSpaces[2] = true;
    this.isClickable();

  }

  @FXML
  private void lightenKickIcon(MouseEvent event) {
    ImageView iv = (ImageView) event.getSource();
    iv.setImage(new Image("/com/scrab5/ui/images/SB05_KickIconClicked.png"));
  }

  @FXML
  private void darkenKickIcon(MouseEvent event) {
    ImageView iv = (ImageView) event.getSource();
    iv.setImage(new Image("/com/scrab5/ui/images/SB05_KickIcon.png"));
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

  @FXML
  private void setSelectedDictionary(ActionEvent event) {
    String selected = (String) this.dictionarySelection.getValue();
    Data.setCurrentUser(selected);
    this.dicDisplaying.setText(selected.substring(0, selected.length() - 4));
    isDictionarySelected = true;
  }

  private boolean isClickable() {

    if (playerAmount >= 4) {
      this.addPlayerButton.setY(-44);
      this.addPlayerButton.setOpacity(1);
      return false;

    } else {
      this.addPlayerButton.setY(0);
      this.addPlayerButton.setOpacity(0);
      return true;
    }
  }

  private void setUpDicitionaryBox() {

    File dir = new File(System.getProperty("user.dir"));
    FilenameFilter filter = new FilenameFilter() {

      public boolean accept(File dir, String name) {
        return name.endsWith(".txt");
      }

    };

    String[] fileNames = dir.list(filter);

    if (fileNames == null) {
      System.out.println("JCOMBO BOX Directory is INCORRECT or does not exist!");
    } else {
      for (int i = 0; i < fileNames.length; i++) {
        String filename = fileNames[i];
        this.dictionarySelection.getItems().add(filename);
      }
    }
  }

}
