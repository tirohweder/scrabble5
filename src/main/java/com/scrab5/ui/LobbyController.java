package com.scrab5.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The LobbyController class contains some basic methods which are needed by the MultiplayerLobby-
 * and SingleplayerLobbyController.
 *
 * @author mherre
 */
public abstract class LobbyController extends Controller {

  @FXML
  protected ImageView kick2;
  @FXML
  protected ImageView kick3;
  @FXML
  protected ImageView kick4;
  @FXML
  protected ImageView addPlayerButton;
  @FXML
  protected ImageView startButton;
  @FXML
  protected ImageView darkBackground;
  @FXML
  protected ImageView diffSelection2;
  @FXML
  protected ImageView diffSelection3;
  @FXML
  protected ImageView diffSelection4;
  @FXML
  protected ImageView diffButton1;
  @FXML
  protected ImageView diffButton2;
  @FXML
  protected ImageView diffButton3;
  @FXML
  protected Label player1;
  @FXML
  protected Label player2;
  @FXML
  protected Label player3;
  @FXML
  protected Label player4;
  @FXML
  protected Label ready1;
  @FXML
  protected Label ready2;
  @FXML
  protected Label ready3;
  @FXML
  protected Label ready4;
  @FXML
  protected Label vote1;
  @FXML
  protected Label vote2;
  @FXML
  protected Label vote3;
  @FXML
  protected Label vote4;
  @FXML
  protected Label difficulty2;
  @FXML
  protected Label difficulty3;
  @FXML
  protected Label difficulty4;
  @FXML
  protected ComboBox<Integer> voteSelection1;
  @FXML
  protected ComboBox<Integer> voteSelection2;
  @FXML
  protected ComboBox<Integer> voteSelection3;
  @FXML
  protected ComboBox<Integer> voteSelection4;
  @FXML
  protected ComboBox<String> diffBox1;
  @FXML
  protected ComboBox<String> diffBox2;
  @FXML
  protected ComboBox<String> diffBox3;

  protected int playerAmount = 1;
  protected boolean[] isReady = {false, true, true, true};
  protected boolean[] freeSpaces = {false, true, true, true};
  protected boolean isDictionarySelected = false;

  /**
   * Loads the fxml as LetterCustomization.
   *
   * @author trohwede
   * @return parent of letterCustomization
   * @throws IOException when App.class.getResource doesnt exist.
   */
  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  private static Parent loadFXML() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("LetterCustomization" + ".fxml"));
    return fxmlLoader.load();
  }

  /**
   * Event method that is called when the "Customize"-button in the UI is clicked. Grays out the
   * background and creates a new <code>Stage</code> that loads "LetterCustomization.fxml" (for
   * further information check out the class {@link com.scrab5.ui.LetterCustomizationController
   * LetterCustomizationController}.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>loadFXML(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void customize(MouseEvent event) throws IOException {

    playSound("ButtonClicked.mp3");
    this.darkBackground.setFitWidth(1360);
    this.darkBackground.setFitHeight(768);
    this.darkBackground.setOpacity(1);

    Stage customScreen = new Stage();
    customScreen.setScene(new Scene(loadFXML(), 622, 650));
    customScreen.initModality(Modality.APPLICATION_MODAL);
    customScreen.initStyle(StageStyle.UNDECORATED);
    customScreen.showAndWait();

    playSound("ButtonClicked.mp3");
    this.darkBackground.setFitWidth(10);
    this.darkBackground.setFitHeight(10);
    this.darkBackground.setOpacity(0);
  }

  /**
   * Event method that is called when the first drop down button for the player votes in the UI is
   * clicked. Opens the associated <code>ComboBox</code>.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void clickComboBox1(MouseEvent event) {
    playSound("ButtonClicked.mp3");
    this.voteSelection1.show();
  }

  /**
   * Event method that is called when the second drop down button for the player votes in the UI is
   * clicked. Opens the associated <code>ComboBox</code>.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void clickComboBox2(MouseEvent event) {
    playSound("ButtonClicked.mp3");
    this.voteSelection2.show();
  }

  /**
   * Event method that is called when the third drop down button for the player votes in the UI is
   * clicked. Opens the associated <code>ComboBox</code>.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void clickComboBox3(MouseEvent event) {
    playSound("ButtonClicked.mp3");
    this.voteSelection3.show();
  }

  /**
   * Event method that is called when the fourth drop down button for the player votes in the UI is
   * clicked. Opens the associated <code>ComboBox</code>.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void clickComboBox4(MouseEvent event) {
    playSound("ButtonClicked.mp3");
    this.voteSelection4.show();
  }

  /**
   * Event method that is called when the first drop down button for the difficulty selection in the
   * UI is clicked. Opens the associated <code>ComboBox</code>.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void clickDiffBox1(MouseEvent event) {
    playSound("ButtonClicked.mp3");
    this.diffBox1.show();
  }

  /**
   * Event method that is called when the second drop down button for the difficulty selection in
   * the UI is clicked. Opens the associated <code>ComboBox</code>.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void clickDiffBox2(MouseEvent event) {
    playSound("ButtonClicked.mp3");
    this.diffBox2.show();
  }

  /**
   * Event method that is called when the third drop down button for the difficulty selection in the
   * UI is clicked. Opens the associated <code>ComboBox</code>.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void clickDiffBox3(MouseEvent event) {
    playSound("ButtonClicked.mp3");
    this.diffBox3.show();
  }

  /**
   * Event method that is called when an item (difficulty) in the first difficulty selection for the
   * AI has been selected. Refreshes the <code>Label</code> that displays the difficulty.
   *
   * @author mherre
   * @param event the event that is created when an item in the associated <code>ComboBox</code> is
   *        selected
   */
  @FXML
  private void setDifficulty1(ActionEvent event) {
    ComboBox<String> temp = (ComboBox<String>) event.getSource();
    this.difficulty2.setText(temp.getValue() + "");
  }

  /**
   * Event method that is called when an item (difficulty) in the second difficulty selection for
   * the AI has been selected. Refreshes the <code>Label</code> that displays the difficulty.
   *
   * @author mherre
   * @param event the event that is created when an item in the associated <code>ComboBox</code> is
   *        selected
   */
  @FXML
  private void setDifficulty2(ActionEvent event) {
    ComboBox<String> temp = (ComboBox<String>) event.getSource();
    this.difficulty3.setText(temp.getValue() + "");
  }

  /**
   * Event method that is called when an item (difficulty) in the third difficulty selection for the
   * AI has been selected. Refreshes the <code>Label</code> that displays the difficulty.
   *
   * @author mherre
   * @param event the event that is created when an item in the associated <code>ComboBox</code> is
   *        selected
   */
  @FXML
  private void setDifficulty3(ActionEvent event) {
    ComboBox<String> temp = (ComboBox<String>) event.getSource();
    this.difficulty4.setText(temp.getValue() + "");
  }

  /**
   * Event method that is called when an item (votes) in the first vote selection for the players/AI
   * has been selected. Refreshes the <code>Label</code> that displays the votes.
   *
   * @author mherre
   * @param event the event that is created when an item in the associated <code>ComboBox</code> is
   *        selected
   */
  @FXML
  private void setPlayerVote1(ActionEvent event) {
    ComboBox<Integer> temp = (ComboBox<Integer>) event.getSource();
    this.vote1.setText(temp.getValue() + "");
  }

  /**
   * Event method that is called when an item (votes) in the second vote selection for the
   * players/AI has been selected. Refreshes the <code>Label</code> that displays the votes.
   *
   * @author mherre
   * @param event the event that is created when an item in the associated <code>ComboBox</code> is
   *        selected
   */
  @FXML
  private void setPlayerVote2(ActionEvent event) {
    ComboBox<Integer> temp = (ComboBox<Integer>) event.getSource();
    this.vote2.setText(temp.getValue() + "");
  }

  /**
   * Event method that is called when an item (votes) in the third vote selection for the players/AI
   * has been selected. Refreshes the <code>Label</code> that displays the votes.
   *
   * @author mherre
   * @param event the event that is created when an item in the associated <code>ComboBox</code> is
   *        selected
   */
  @FXML
  private void setPlayerVote3(ActionEvent event) {
    ComboBox<Integer> temp = (ComboBox<Integer>) event.getSource();
    this.vote3.setText(temp.getValue() + "");
  }

  /**
   * Event method that is called when an item (votes) in the fourth vote selection for the
   * players/AI has been selected. Refreshes the <code>Label</code> that displays the votes.
   *
   * @author mherre
   * @param event the event that is created when an item in the associated <code>ComboBox</code> is
   *        selected
   */
  @FXML
  private void setPlayerVote4(ActionEvent event) {
    ComboBox<Integer> temp = (ComboBox<Integer>) event.getSource();
    this.vote4.setText(temp.getValue() + "");
  }

  /**
   * Event method that is called when the mouse is hovering over the <code>ImageView</code> object
   * <code>iv</code> or released after it has been clicked. Changes the <code>image</code> to
   * "/com/scrab5/ui/images/SB06_KickIconClicked.png".
   *
   * @author mherre
   * @param event the event that is created from the hovering and releasing
   */
  @FXML
  private void lightenKickIcon(MouseEvent event) {
    ImageView iv = (ImageView) event.getSource();
    iv.setImage(new Image("/com/scrab5/ui/images/SB05_KickIconClicked.png"));
  }

  /**
   * Event method that is called when the mouse is exiting the <code>ImageView</code> object <code>
   * iv</code> or when it is pressed. Changes the <code>image</code> to
   * "/com/scrab5/ui/images/SB05_KickIcon.png".
   *
   * @author mherre
   * @param event the event that is created from the exiting and pressing
   */
  @FXML
  private void darkenKickIcon(MouseEvent event) {
    ImageView iv = (ImageView) event.getSource();
    iv.setImage(new Image("/com/scrab5/ui/images/SB05_KickIcon.png"));
  }

  /**
   * Event method that is called when the mouse is hovering over the <code>ImageView</code> object
   * <code>iv</code> or released after it has been clicked. Changes the <code>image</code> to
   * "/com/scrab5/ui/images/SB05_PlayerVoteButtonClicked.png".
   *
   * @author mherre
   * @param event the event that is created from the hovering and releasing
   */
  @FXML
  private void lightenArrow(MouseEvent event) {
    ImageView iv = (ImageView) event.getSource();
    iv.setImage(new Image("/com/scrab5/ui/images/SB05_PlayerVoteButtonClicked.png"));
  }

  /**
   * Event method that is called when the mouse is exiting the <code>ImageView</code> object <code>
   * iv</code> or when it is pressed. Changes the <code>image</code> to
   * "/com/scrab5/ui/images/SB05_PlayerVoteButton.png".
   *
   * @author mherre
   * @param event the event that is created from the exiting and pressing
   */
  @FXML
  private void darkenArrow(MouseEvent event) {
    ImageView iv = (ImageView) event.getSource();
    iv.setImage(new Image("/com/scrab5/ui/images/SB05_PlayerVoteButton.png"));
  }

  /**
   * Event method that is called when the mouse is hovering over the <code>ImageView</code> object
   * <code>iv</code> or released after it has been clicked. Changes the <code>image</code> to
   * "/com/scrab5/ui/images/SB05_StartButtonClicked.png".
   *
   * @author mherre
   * @param event the event that is created from the hovering and releasing
   */
  @FXML
  private void lightenStartButton(MouseEvent event) {
    ImageView iv = (ImageView) event.getSource();
    iv.setImage(new Image("/com/scrab5/ui/images/SB05_StartButtonClicked.png"));
  }

  /**
   * Event method that is called when the mouse is exiting the <code>ImageView</code> object <code>
   * iv</code> or when it is pressed. Changes the <code>image</code> to
   * "/com/scrab5/ui/images/SB05_StartButton.png".
   *
   * @author mherre
   * @param event the event that is created from the exiting and pressing
   */
  @FXML
  private void darkenStartButton(MouseEvent event) {
    ImageView iv = (ImageView) event.getSource();
    iv.setImage(new Image("/com/scrab5/ui/images/SB05_StartButton.png"));
  }

  /**
   * Method that checks if everyone in the lobby clicked ready / is ready.
   *
   * @author mherre
   * @return the boolean valuing if everyone is ready
   */
  protected boolean isEveryoneReady() {
    for (boolean b : isReady) {
      if (!b) {
        return false;
      }
    }
    return true;
  }

  /**
   * Method that is called in the initialize methods of Multiplayer- and
   * SingleplayerLobbyController. Sets up all ComboBoxes (Player votes, difficulty) and two Labels
   * (name displaying, ready status).
   *
   * @author mherre
   */
  protected void setUpInit() {
    this.player1.setText(Data.getCurrentUser());
    this.ready1.setText("Not Ready");
    for (int i = 1; i <= 4; i++) {
      this.voteSelection1.getItems().add(i);
      this.voteSelection2.getItems().add(i);
      this.voteSelection3.getItems().add(i);
      this.voteSelection4.getItems().add(i);
    }
    this.voteSelection1.getSelectionModel().select(0);
    this.voteSelection2.getSelectionModel().select(1);
    this.voteSelection3.getSelectionModel().select(2);
    this.voteSelection4.getSelectionModel().select(3);

    this.diffBox1.getItems().add("Easy");
    this.diffBox1.getItems().add("Difficult");
    this.diffBox2.getItems().add("Easy");
    this.diffBox2.getItems().add("Difficult");
    this.diffBox3.getItems().add("Easy");
    this.diffBox3.getItems().add("Difficult");

    this.diffBox1.getSelectionModel().select(0);
    this.diffBox2.getSelectionModel().select(0);
    this.diffBox3.getSelectionModel().select(0);
  }

  /**
   * Method that is called to get the cast votes of each player. The votes are returned in form of
   * an <code>ArrayList</code>. The first item in the list is the amount of votes for the first
   * player, the second item for the second player, ...
   *
   * @author mherre
   * @return al the ArrayList that cotains the votes
   */
  protected ArrayList<Integer> getPlayerVotes() {

    ArrayList<Integer> al = new ArrayList<>();

    for (int i = 0; i < 4; i++) {

      if (i == 0) {
        al.add(voteSelection1.getValue());

      } else if (i == 1) {
        al.add(voteSelection2.getValue());

      } else if (i == 2) {
        al.add(voteSelection3.getValue());

      } else {
        al.add(voteSelection4.getValue());
      }
    }
    return al;
  }

  /**
   * Method that creates an <code>ArrayList</code> which contains the amount of tiles of each
   * letter. The maximum amount of tiles that a bag can have, is 100.
   *
   * @author mherre
   * @param al the ArrayList that contains the selected occurrences
   * @return finalBag the ArrayList that contains the amount of tiles for each letter
   */
  protected ArrayList<Integer> createTileBag(ArrayList<Integer> al) {

    ArrayList<Integer> finalBag = new ArrayList<>();
    int numberOfTiles = 0;

    for (Integer integer : al) {
      numberOfTiles += integer;
    }

    for (Integer integer : al) {
      double letterAmount = Math.ceil(integer * 100 / numberOfTiles);
      finalBag.add((int) letterAmount);
    }

    return finalBag;
  }

  protected abstract boolean isClickable();

  @FXML
  protected abstract void startGame(MouseEvent event) throws IOException, SQLException;

  @FXML
  protected abstract void back(MouseEvent event) throws IOException;

  @FXML
  protected abstract void addPlayer(MouseEvent event) throws IOException;

  @FXML
  protected abstract void dontShow(MouseEvent event) throws IOException;

  @FXML
  protected abstract void kickPlayer2(MouseEvent event);

  @FXML
  protected abstract void kickPlayer3(MouseEvent event);

  @FXML
  protected abstract void kickPlayer4(MouseEvent event);
}
