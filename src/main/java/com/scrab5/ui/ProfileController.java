package com.scrab5.ui;

import com.scrab5.util.database.Database;
import com.scrab5.util.database.FillDatabase;
import com.scrab5.util.database.PlayerProfileDatabase;
import com.scrab5.util.database.UseDatabase;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * The ProfileController class controls the components of the "Profile.fxml".
 *
 * @author mherre
 */
public class ProfileController extends Controller implements Initializable {

  @FXML private Label nicknameLabel;
  @FXML private Label totalPoints;
  @FXML private Label averagePointsGame;
  @FXML private Label mostPoints;
  @FXML private Label laidWords;
  @FXML private Label averagePointsWord;
  @FXML private Label totalGames;
  @FXML private Label totalWins;
  @FXML private Label winPercentage;
  @FXML private Label favDic;

  /**
   * Call certain methods as soon as the Controller is loaded. Calls {@link #setupStats()} and
   * displays the name of the current user
   *
   * @author mherre
   */
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    this.nicknameLabel.setText(Data.getCurrentUser());
    this.setupStats();
  }

  /**
   * Event method that is called when the "Delete Profile"-button in the UI is clicked. Opens a
   * Pop-Up and depending on the users action the profile gets deleted and scene changed or not.
   *
   * @author mherre
   * @author lengist
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *     exist
   */
  @FXML
  private void deleteProfile(MouseEvent event) throws IOException {
    Database.reconnect();

    playSound();

    String message = "Are you sure you want to delete this profile? (You can't undo this)";
    PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.CONFIRMATION);
    pum.show();

    if (Data.isConfirmed()) {
      FillDatabase.deletePlayer(Data.getCurrentUser());

      if (UseDatabase.tablePlayerIsEmpty()) {
        App.setRoot("AccountCreation");

      } else {
        App.setRoot("RealLogin");
      }
    }
    Database.disconnect();
  }

  /**
   * Event method that is called when the "Edit Name"-button in the UI is clicked. Opens a Pop-Up
   * message with a textfield displaying the user's current nickname. In case the edited nickname
   * fullfills the username criteria then the name will be updated in the database.
   *
   * @author mherre
   * @author lengist
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *     exist
   */
  @FXML
  private void editName(MouseEvent event) throws IOException {
    Database.reconnect();
    playSound();

    String message = "Enter your changes and click 'Okay'";
    PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.INPUT);
    pum.show();

    if (this.isUsernameValid(Data.getInputFieldText())) {
      PlayerProfileDatabase.setName(Data.getCurrentUser(), Data.getInputFieldText());
      Data.setCurrentUser(Data.getInputFieldText());
      App.setRoot("Profile");
    }
    Database.disconnect();
  }

  /**
   * Event method that is called when the "Change Profile"-button in the UI is clicked. Changes the
   * scene to "RealLogin.fxml".
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *     exist
   */
  @FXML
  private void changeProfile(MouseEvent event) throws IOException {
    playSound();
    App.setRoot("RealLogin", "Profile");
  }

  /**
   * Event method that is called when "Create New Profile"-button in the UI is clicked. Changes the
   * scene to "AccountCreation.fxml".
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *     exist
   */
  @FXML
  private void createNewPlayerProfile(MouseEvent event) throws IOException {
    playSound();
    App.setRoot("AccountCreation", "Profile");
  }

  /**
   * Event method that is called when the "Back"-button in the UI is clicked. Changes the scene to
   * "MainMenu.fxml".
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *     exist
   */
  @FXML
  private void back(MouseEvent event) throws IOException {
    playSound();
    App.setRoot("MainMenu");
  }

  /**
   * Checks whether a given username fulfills the criteria (consists only of numbers, letters and
   * underscores). In case it does, it returns true. In case it doesn't it returns false and shows a
   * PopUp message stating the username doesn't fullfill the criteria.
   *
   * @author mherre
   * @param username the string containing the username that's tested
   * @return the boolean describing if the username is valid
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *     exist
   */
  public boolean isUsernameValid(String username) throws IOException {

    String regex = "[a-zA-Z0-9_]{1,12}";
    String message;
    PopUpMessage pum;

    if (username.matches(regex)) {
      if (!(UseDatabase.playerExists(username) && !(username.equals(Data.getCurrentUser())))) {
        return true;

      } else {
        message = "This username already exists. Please choose a different name!";
        pum = new PopUpMessage(message, PopUpMessageType.ERROR);
        pum.show();
        return false;
      }
    } else {
      message =
          "Please make sure your nickname consists only of letters, numbers, "
              + "underscores and is only 12 signs long";
      pum = new PopUpMessage(message, PopUpMessageType.ERROR);
      pum.show();
      return false;
    }
  }

  /**
   * Gathers all necessary data from the database and sets the text of various labels that are
   * displaying the statistics in the UI.
   *
   * @author mherre
   * @author lengist
   */
  private synchronized void setupStats() {

    String name = Data.getCurrentUser();

    String favoriteDictionary = PlayerProfileDatabase.getFavoriteDictionary(name);
    if (!favoriteDictionary.equals("")) {
      this.favDic.setText(favoriteDictionary.substring(0, favoriteDictionary.length() - 4));
    } else {
      this.favDic.setText("-");
    }

    int totalPoints = PlayerProfileDatabase.getTotalPoints(name);
    this.totalPoints.setText(String.valueOf(totalPoints));

    int personalHighscore = PlayerProfileDatabase.getPersonalHighscore(name);
    this.mostPoints.setText(String.valueOf(personalHighscore));

    int laidWords = PlayerProfileDatabase.getLaidWords(name);
    this.laidWords.setText(String.valueOf(laidWords));

    int pointsPerWordRate = PlayerProfileDatabase.getPointsPerWordRate(name);
    this.averagePointsWord.setText(String.valueOf(pointsPerWordRate));

    int totalPlayedGames = PlayerProfileDatabase.getTotalPlayedGames(name);
    this.totalGames.setText(String.valueOf(totalPlayedGames));

    int totalWins = PlayerProfileDatabase.getTotalWins(name);
    this.totalWins.setText(String.valueOf(totalWins));

    double winRate = PlayerProfileDatabase.getWinRate(name);
    this.winPercentage.setText(String.valueOf(winRate * 100.0));

    if (totalPoints == 0 || totalPlayedGames == 0) {
      this.averagePointsGame.setText("0");
    } else {
      int averagePointsPerGame = totalPoints / totalPlayedGames;
      this.averagePointsGame.setText(String.valueOf(averagePointsPerGame));
    }
  }
}
