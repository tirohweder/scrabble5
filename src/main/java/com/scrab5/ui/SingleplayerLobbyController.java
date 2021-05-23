package com.scrab5.ui;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import com.scrab5.core.game.GameSession;
import com.scrab5.core.player.AiPlayer;
import com.scrab5.core.player.Player;
import com.scrab5.util.database.PlayerProfileDatabase;
import com.scrab5.util.database.UseDatabase;
import com.scrab5.util.textParser.DictionaryParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setUpInit();
    this.setUpDicitionaryBox();
  }

  @FXML
  protected void back(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    App.setRoot("MainMenu");

  }

  /**
   * Event method that is called when the "Ready"-button is clicked.
   *
   * @param event
   * @throws IOException
   * @author mherre
   */
  @FXML
  private void ready(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");

    if (!isReady[0]) {
      this.ready1.setText("Ready");
      this.isReady[0] = true;

      if (isEveryoneReady() && this.isDictionarySelected) {

        if (this.playerAmount >= 2) {
          this.startButton.setOpacity(1.0);

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
      this.startButton.setOpacity(0.0);
      this.isReady[0] = false;

    }
  }

  @FXML
  protected void addPlayer(MouseEvent event) {

    playSound("ButtonClicked.mp3");
    this.playerAmount++;

    for (int i = 1; i < freeSpaces.length; i++) {
      if (freeSpaces[i]) {
        switch (i) {
          case 1:
            this.player2.setText("CPU 2");
            this.ready2.setText("Ready");
            this.difficulty2.setOpacity(1.0);
            this.kick2.setOpacity(1.0);
            this.diffSelection2.setOpacity(1.0);
            this.diffButton1.setOpacity(1.0);
            isReady[i] = true;
            break;
          case 2:
            this.player3.setText("CPU 3");
            this.ready3.setText("Ready");
            this.difficulty3.setOpacity(1.0);
            this.kick3.setOpacity(1.0);
            this.diffSelection3.setOpacity(1.0);
            this.diffButton2.setOpacity(1.0);
            isReady[i] = true;
            break;
          case 3:
            this.player4.setText("CPU 4");
            this.ready4.setText("Ready");
            this.difficulty4.setOpacity(1.0);
            this.kick4.setOpacity(1.0);
            this.diffSelection4.setOpacity(1.0);
            this.diffButton3.setOpacity(1.0);
            isReady[i] = true;
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
    if (kick2.getOpacity() == 1.0) {
      playSound("ButtonClicked.mp3");
      this.player2.setText("");
      this.ready2.setText("");
      this.difficulty2.setOpacity(0);
      this.diffSelection2.setOpacity(0);
      this.diffButton1.setOpacity(0);
      this.kick2.setOpacity(0);
      this.playerAmount--;
      this.freeSpaces[1] = true;
      this.isClickable();
    }
  }

  @FXML
  protected void kickPlayer3(MouseEvent event) {

    if (kick3.getOpacity() == 1.0) {
      playSound("ButtonClicked.mp3");
      this.player3.setText("");
      this.ready3.setText("");
      this.difficulty3.setOpacity(0);
      this.diffSelection3.setOpacity(0);
      this.diffButton2.setOpacity(0);
      this.kick3.setOpacity(0);
      this.playerAmount--;
      this.freeSpaces[2] = true;
      this.isClickable();
    }
  }

  @FXML
  protected void kickPlayer4(MouseEvent event) {

    if (kick4.getOpacity() == 1.0) {
      playSound("ButtonClicked.mp3");
      this.player4.setText("");
      this.ready4.setText("");
      this.difficulty4.setOpacity(0);
      this.diffSelection4.setOpacity(0);
      this.diffButton3.setOpacity(0);
      this.kick4.setOpacity(0);
      this.playerAmount--;
      this.freeSpaces[3] = true;
      this.isClickable();
    }
  }

  /**
   * Makes dictionarySelection only openable when the dropDownButton is clicked, not when the
   * combobox (dictionarySelection) is clicked.
   *
   * @param event
   * @throws IOException
   * @author mherre
   */
  @FXML
  protected void dontShow(MouseEvent event) {
    dictionarySelection.hide();
    voteSelection1.hide();
    voteSelection2.hide();
    voteSelection3.hide();
    voteSelection4.hide();
    diffBox1.hide();
    diffBox2.hide();
    diffBox3.hide();
  }

  /**
   * Is called when the "dropDownButton" Button is clicked. Opens the combo box displaying all
   * selectable dictionaries.
   *
   * @param event
   * @throws IOException
   * @author mherre
   */
  @FXML
  private void clickComboBox(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    dictionarySelection.show();
  }

  @FXML
  private void setSelectedDictionary(ActionEvent event) {
    String selected = (String) this.dictionarySelection.getValue();
    this.dicDisplaying.setText(selected.substring(0, selected.length() - 4));
    DictionaryParser.setCurrentDictionary(selected);
    DictionaryParser.parseFile(selected);
    PlayerProfileDatabase.setFavoriteDictionary(Data.getCurrentUser(), selected);
    System.out.println(selected);
    System.out.println("PP: " + PlayerProfileDatabase.getFavoriteDictionary(Data.getCurrentUser()));
    isDictionarySelected = true;
  }

  @FXML
  protected void startGame(MouseEvent event) throws IOException, SQLException {

    ArrayList<Player> playList = new ArrayList<Player>();
    playList.add(0, new Player(Data.getCurrentUser()));

    for (int i = 1; i < freeSpaces.length; i++) {
      if (!freeSpaces[i]) {
        playList.add(new AiPlayer("CPU" + (i + 1)));
      }
    }

    if (Data.getHasBeenEdited()) {
      ArrayList<Integer> tiles = createTileBag(Data.getOccurrencyDistribution());
      Data.setGameSession(new GameSession(playList, tiles, Data.getPointsDistribution(), false));

    } else {
      ArrayList<Integer> points = new ArrayList<Integer>();
      ArrayList<Integer> help1 = new ArrayList<Integer>();
      int[] help2 = UseDatabase.getAllOccurrences();
      int[] help3 = UseDatabase.getAllPointsPerLetter();

      for (int j = 0; j < help2.length; j++) {
        help1.add(help2[j]);
        points.add(help3[j]);
      }

      ArrayList<Integer> tiles = createTileBag(help1);
      Data.setGameSession(new GameSession(playList, tiles, points, false));
    }

    App.setRoot("SinglePlayer");
  }

  protected boolean isClickable() {

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

  /**
   * Method that creates an <code>ArrayList</code> which contains the amount of tiles of each
   * letter. The maximum amount of tiles that a bag can have, is 100.
   * 
   * @author mherre
   * @param al the ArrayList that contains the selected occurrences
   * @return finalBag the ArrayList that contains the amount of tiles for each letter
   */
  private ArrayList<Integer> createTileBag(ArrayList<Integer> al) {

    ArrayList<Integer> finalBag = new ArrayList<Integer>();
    int numberOfTiles = 0;

    for (int i = 0; i < al.size(); i++) {
      numberOfTiles += al.get(i);
    }

    for (int i = 0; i < al.size(); i++) {
      double letterAmount = Math.ceil(al.get(i) * 100 / numberOfTiles);
      finalBag.add((int) letterAmount);
    }

    return finalBag;
  }

  /**
   * https://stackabuse.com/java-list-files-in-a-directory/
   */
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
