package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

/**
 * The MainMenuController class controls the buttons of the "MainMenu.fxml".
 *
 * @author apilgrim
 */
public class MainMenuController extends Controller implements Initializable {

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {}

  /**
   * Is called when "Multiplayer" - button is clicked, switches to the "Multiplayer Lobby" screen.
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "Multiplayer" button is clicked
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void multiplayerClicked(MouseEvent event) throws IOException {
    playSound();
    App.setRoot("MultiplayerOverview");
  }

  /**
   * Is called when "Singleplayer" - button is clicked, switches to the "Singleplayer Lobby" screen.
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "Singleplayer" button is clicked
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void singleplayerClicked(MouseEvent event) throws IOException {
    playSound();
    App.setRoot("SingleplayerLobby");
  }

  /**
   * Is called when "Play Tutorial" - button is clicked, switches to the "Tutorial" screen.
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "Play Tutorial" button is clicked
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void tutorialClicked(MouseEvent event) throws IOException {
    playSound();
    App.setRoot("Tutorial");
  }

  /**
   * Is called when "Settings" - button is clicked, switches to the settings screen.
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "Settings" button is clicked
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void settingsClicked(MouseEvent event) throws IOException {
    playSound();
    App.setRoot("Settings");
  }

  /**
   * Is called when "stats" - button is clicked, switches to the game "PlayerProfile" Screen.
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "Stats" button is clicked
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void statsClicked(MouseEvent event) throws IOException {
    playSound();
    App.setRoot("Profile");
  }
}
