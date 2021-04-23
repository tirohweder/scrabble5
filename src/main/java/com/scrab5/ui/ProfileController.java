package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.scrab5.util.database.FillDatabase;
import com.scrab5.util.database.PlayerProfileDatabase;
import com.scrab5.util.database.UseDatabase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * The ProfileController class is supposed to control the components of the scene "Profile"
 * 
 * @author mherre
 *
 */
public class ProfileController extends Controller implements Initializable {

  @FXML
  private Label nicknameLabel;
  @FXML
  private Label totalPoints;
  @FXML
  private Label averagePointsGame;
  @FXML
  private Label mostPoints;
  @FXML
  private Label laidWords;
  @FXML
  private Label averagePointsWord;
  @FXML
  private Label longestWord;
  @FXML
  private Label totalGames;
  @FXML
  private Label totalWins;
  @FXML
  private Label winPercentage;
  @FXML
  private Label favDic;

  /**
   * Sets the current username on the Label and shows stats
   * 
   * @author mherre
   */
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    this.nicknameLabel.setText(Data.getCurrentUser());
    this.setupStats();

  }

  /**
   * Method is called when the "Delete Profile" - button is clicked. Opens a Pop-Up asking the user
   * if he really want to delete his profile. In case he clicks "Confirm" the profile gets removed
   * from the database.
   * 
   * @author mherre
   * @author lengist
   * @param event
   * @throws IOException
   */
  @FXML
  private void deleteProfile(MouseEvent event) throws IOException {

    playSound("ButtonClicked.mp3");

    String message = "Are you sure you want to delete this profile? (You can't undo this)";
    PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.CONFIRMATION);
    pum.show();

    if (Data.isConfirmed()) {
      FillDatabase.deletePlayer(Data.getCurrentUser());
      System.out.println("Current: " + Data.getCurrentUser());

      if (UseDatabase.tablePlayerIsEmpty()) {
        App.setRoot("AccountCreation");
      } else {
        App.setRoot("RealLogin");
      }
    }
  }

  /**
   * Method that is called when the "Edit Name" - button is clicked. Opens a Pop-Up message with a
   * textfield displaying the user's current nickname. In case the edited nickname fullfills the
   * username criteria then name will be updated in the database.
   * 
   * @author mherre
   * @author lengist
   * @param event
   * @throws IOException
   */
  @FXML
  private void editName(MouseEvent event) throws IOException {

    playSound("ButtonClicked.mp3");

    String message = "Enter your changes and click 'Okay'";
    PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.INPUT);
    pum.show();

    if (this.isUsernameValid(Data.getInputFieldText())) {
      PlayerProfileDatabase.setName(Data.getCurrentUser(), Data.getInputFieldText());
      Data.setCurrentUser(Data.getInputFieldText());
      App.setRoot("Profile");
    }
  }

  @FXML
  private void changeProfile(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    App.setRoot("RealLogin", "Profile");
  }

  /**
   * Method that is called when the "Create New Profile" - button is clicked, changes the scene to
   * the "AccountCreation" screen
   * 
   * @author mherre
   * @param event
   * @throws IOException
   */
  @FXML
  private void createNewPlayerProfile(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    App.setRoot("AccountCreation", "Profile");
  }

  /**
   * Method that is called when the "Back"-button is clicked. Changes the scene to "MainMenu"
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
   * Checks whether a given username fulfills the criteria (consists only of numbers, letters and
   * underscores). In case it does, it returns true. In case it doesn't it returns false and shows a
   * PopUp message stating the username doesn't fullfill the criteria
   * 
   * @author mherre
   * @param username
   * @return
   * @throws IOException
   */
  public boolean isUsernameValid(String username) throws IOException {

    String regex = "[a-zA-Z0-9_]{1,12}";
    String message;
    PopUpMessage pum;

    if (username.matches(regex)) {

      return true;

    } else {

      message =
          "Please make sure your nickname consists only of letters, numbers, underscores and is only 12 signs long";
      pum = new PopUpMessage(message, PopUpMessageType.ERROR);
      pum.show();

      return false;
    }
  }

  /**
   * Gather all data from the database to display it on the UI
   * 
   * @author mherre
   * @author lengist
   */
  private void setupStats() {

    String name, picture, longestWord, favoriteDictionary;
    int totalPoints, personalHighscore, pointsPerWordRate, totalPlayedGames, totalWins, laidWords;

    name = Data.getCurrentUser();
    picture = PlayerProfileDatabase.getPicture(name);

    totalPoints = PlayerProfileDatabase.getTotalPoints(name);
    personalHighscore = PlayerProfileDatabase.getPersonalHighscore(name);
    laidWords = PlayerProfileDatabase.getLaidWords(name);
    pointsPerWordRate = PlayerProfileDatabase.getPointsPerWordRate(name);

    longestWord = PlayerProfileDatabase.getLongestWord(name);

    totalPlayedGames = PlayerProfileDatabase.getTotalPlayedGames(name);
    totalWins = PlayerProfileDatabase.getTotalWins(name);

    double winRate = PlayerProfileDatabase.getWinRate(name);

    favoriteDictionary = PlayerProfileDatabase.getFavoriteDictionary(name);

    if (totalPoints == 0 || totalPlayedGames == 0) {
      this.averagePointsGame.setText("0");

    } else {
      int averagePointsPerGame = totalPoints / totalPlayedGames;
      this.averagePointsGame.setText(String.valueOf(averagePointsPerGame));

    }

    this.totalPoints.setText(String.valueOf(totalPoints));
    this.averagePointsGame.setText("0");
    this.mostPoints.setText(String.valueOf(personalHighscore));
    this.laidWords.setText(String.valueOf(laidWords));
    this.averagePointsWord.setText(String.valueOf(pointsPerWordRate));
    this.longestWord.setText(longestWord);
    this.totalGames.setText(String.valueOf(totalPlayedGames));
    this.totalWins.setText(String.valueOf(totalWins));
    this.winPercentage.setText(String.valueOf(winRate));
    this.favDic.setText(favoriteDictionary);

  }


}
