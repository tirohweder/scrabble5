package com.scrab5.ui;

import com.scrab5.core.game.GameSession;
import com.scrab5.core.player.AiPlayer;
import com.scrab5.core.player.Player;
import com.scrab5.util.database.PlayerProfileDatabase;
import com.scrab5.util.database.UseDatabase;
import com.scrab5.util.parser.DictionaryParser;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * The SingleplayerLobbyController class controls the components of the "SinglePlayerLobby.fxml".
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

  /**
   * Call certain methods as soon as the Controller is loaded. Calls
   * {@link com.scrab5.ui.LobbyController#setUpInit() setUpInit()} and set ups the
   * <code>ComboBox</code> for the dictionary selection.
   *
   * @author mherre
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setUpInit();
    this.setUpDicitionaryBox();
  }

  /**
   * Event method that is called when the "Back"-button in the UI is clicked. The scene gets changed
   * to "MainMenu.fxml".
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  protected void back(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    App.setRoot("MainMenu");
  }

  /**
   * Event method that is called when the "Ready"-button in the UI is clicked.
   *
   * <p>
   * 1. Checks first if the user is already ready. If not, then further conditions (s. 2.) are
   * checked. In both cases the <code>Label</code> that displays the ready status gets updated. 2.
   * If every player is ready and a dictionary has been selected by the user, then further
   * conditions (s. 3.) are checked. If not, then an error message is created and ready status reset
   * to "Not Ready".
   *
   * <p>
   * 3. If there is at least one more player in the lobby, then the "Start"-button appears. If not,
   * then an error message is created and ready status reset to "Not Ready".
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
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

  /**
   * Event method that is called when the "Add Player"-button in the UI is clicked. Adds an AI
   * player to the first free position in the lobby. Calls
   * {@link com.scrab5.ui.SingleplayerController#isClickable isClickable()}.
   *
   * <p>
   * Every AI player has a "Kick"-button and a ComboBox to select a difficulty (hard or easy). Every
   * necessary component gets displayed in this method.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
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

  /**
   * Event method that is called when the "Kick"-button of the second player in the UI is clicked.
   * Removes every visual component of an added player and calls
   * {@link com.scrab5.ui.SingleplayerController#isClickable isClickable()}.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
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

  /**
   * Event method that is called when the "Kick"-button of the third player in the UI is clicked.
   * Removes every visual component of an added player and calls
   * {@link com.scrab5.ui.SingleplayerController#isClickable isClickable()}.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
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

  /**
   * Event method that is called when the "Kick"-button of the fourth player in the UI is clicked.
   * Removes every visual component of an added player and calls
   * {@link com.scrab5.ui.SingleplayerController#isClickable isClickable()}.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
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
   * Method that is called when a <code>ComboBox</code> in the UI clicked. Makes every <code>
   * ComboBox</code> in this class only openable when a corresponding "Arrow Down"-button is
   * clicked.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
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
   * Event method that is called when the "Arrow Down"-button of the dictionary selection in the UI
   * is clicked.for the player votes in the UI is clicked. Opens the associated <code>ComboBox
   * </code>.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void clickComboBox(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    dictionarySelection.show();
  }

  /**
   * Event method that is called when an dictionary has been selected in <code>dictionarySelection
   * </code>. Updates the <code>Label</code> displaying the current selected dictionary in the UI.
   * And calls methods from {@link com.scrab5.util.parser.DictionaryParser DictionaryParser}.
   *
   * @author mherre
   * @author lengist
   * @param event the ActionEvent that is created when an item has been selected in the
   *        corresponding <code>ComboBox</code>
   */
  @FXML
  private void setSelectedDictionary(ActionEvent event) {
    String selected = (String) this.dictionarySelection.getValue();
    this.dicDisplaying.setText(selected.substring(0, selected.length() - 4));
    DictionaryParser.setCurrentDictionary(selected);
    DictionaryParser.parseFile(selected);
    Data.setSelectedDictionary(selected);
    PlayerProfileDatabase.setFavoriteDictionary(Data.getCurrentUser(), selected);
    isDictionarySelected = true;
  }

  /**
   * Event method that is called when the "Start"-button in the UI is clicked.
   *
   * <p>
   * First, an <code>ArrayList</code> that contains all players of the lobby is created. Second, if
   * the letters have been customized then the letters are loaded from the class
   * {@link com.scrab5.ui.Data Data}, if not then they are loaded and converted from
   * {@link com.scrab5.util.database.UseDatabase UseDatabase}. In both cases
   * {@link com.scrab5.ui.SingleplayerLobbyController#createTileBag(ArrayList) createTileBag(...)}
   * is called and a new {@link com.scrab5.core.game.GameSession GameSession} is created.
   *
   * <p>
   * Third, the scene gets changed to "SinglePlayer.fxml".
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   * @throws SQLException if the database hasn't been correctly initialized
   */
  @FXML
  protected void startGame(MouseEvent event) throws IOException, SQLException {

    if (Data.getHasBeenEdited()) {
      ArrayList<Integer> tiles = createTileBag(Data.getOccurrencyDistribution());
      Data.setGameSession(new GameSession(setPlayerOrder(getPlayerVotes()), tiles,
          Data.getPointsDistribution(), false));

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
      Data.setGameSession(new GameSession(setPlayerOrder(getPlayerVotes()), tiles, points, false));
    }
    // Database.disconnect();
    App.setRoot("SinglePlayer");
  }

  /**
   * This method is needed to create a <code>GameSession</code>. It sets the correct play order of
   * the players and creates the AI players as well as the player itself.
   *
   * <p>
   * 1. A new <code>ArrayList temp</code> is created which contains the amount of votes as well as
   * the position of the corresponding player. 2. The votes get multiplied by 1000, this way we can
   * easily randomize the position of two players in case they have the same amount of votes.
   *
   * <p>
   * 3. <code>temp</code> gets sorted by a Bubble Sort algorithm. 4. The players are created and
   * added to <code>playerList</code> which then is returned.
   *
   * @author mherre
   * @param playerVotes the <code>ArrayList</code> which contains the votes of the postion 1 - 4
   * @return playerList the <code>ArrayList</code> which contains the correct order of newly created
   *         players
   */
  private ArrayList<Player> setPlayerOrder(ArrayList<Integer> playerVotes) {

    ArrayList<int[]> temp = new ArrayList<int[]>();
    for (int i = 0; i < playerVotes.size(); i++) {
      if (!freeSpaces[i]) {
        temp.add(new int[] {playerVotes.get(i), i});
      }
    }

    for (int i = 0; i < temp.size(); i++) {
      Random r = new Random();
      int low = temp.get(i)[0] * 1000;
      int high = temp.get(i)[0] * 1000 + 999;

      temp.get(i)[0] = r.nextInt(high - low) + low;
    }

    boolean unsorted = true;
    while (unsorted) {
      unsorted = false;
      for (int i = 0; i < temp.size() - 1; i++) {
        if (temp.get(i + 1)[0] < temp.get(i)[0]) {
          int[] help = temp.get(i);
          temp.set(i, temp.get(i + 1));
          temp.set(i + 1, help);
          unsorted = true;
        }
      }
    }

    ArrayList<Player> playerList = new ArrayList<Player>();
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i)[1] == 0) {
        Player user = new Player(Data.getCurrentUser());
        playerList.add(user);
      } else {
        switch (temp.get(i)[1]) {
          case 1:
            playerList.add(new AiPlayer("CPU" + (temp.get(i)[1] + 1),
                diffBox1.getSelectionModel().getSelectedIndex()));
            break;
          case 2:
            playerList.add(new AiPlayer("CPU" + (temp.get(i)[1] + 1),
                diffBox2.getSelectionModel().getSelectedIndex()));
            break;
          default:
            playerList.add(new AiPlayer("CPU" + (temp.get(i)[1] + 1),
                diffBox3.getSelectionModel().getSelectedIndex()));
            break;
        }
      }
    }

    return playerList;
  }

  /**
   * Checks if the lobby contains the maximum amount of players. If so, the "Add Player"-button gets
   * grayed out. If not, then the "Add Player"-button either stays the same or it isn't grayed out
   * any more.
   *
   * @author mherre
   */
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
   * Method that puts all .txt files that are in the same directory as the .jar in the <code>
   * ComboBox</code> dictionarySelection.
   *
   * <p>
   * https://stackabuse.com/java-list-files-in-a-directory/
   *
   * @author mherre
   */
  private void setUpDicitionaryBox() {

    File dir = new File(System.getProperty("user.dir"));
    FilenameFilter filter = new FilenameFilter() {

      public boolean accept(File dir, String name) {
        if (name.endsWith("Parsed.txt")) {
          return false;
        } else {
          return name.endsWith(".txt");
        }
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
