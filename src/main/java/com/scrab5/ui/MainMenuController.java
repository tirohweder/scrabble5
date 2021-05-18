package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/*
 * @author apilgrim
 */

public class MainMenuController extends Controller implements Initializable {

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub
  }


  /**
   * @author apilgrim
   * @param event
   * @throws IOException
   * 
   *         Is called when "Multiplayer" - button is clicked, switches to the "Multiplayer Lobby"
   *         screen
   */
  @FXML
  private void multiplayerClicked(MouseEvent event) throws IOException {
    App.setRoot("MultiplayerOverview");
  }

  /**
   * @author apilgrim
   * @param event
   * @throws IOException
   * 
   *         Is called when "Singleplayer" - button is clicked, switches to the "Singleplayer Lobby"
   *         screen
   */
  @FXML
  private void singleplayerClicked(MouseEvent event) throws IOException {
    App.setRoot("SingleplayerLobby");

  }

  /**
   * @author apilgrim
   * @param event
   * @throws IOException
   * 
   *         Is called when "Play Tutorial" - button is clicked, switches to the "Tutorial" screen
   */
  @FXML
  private void tutorialClicked(MouseEvent event) throws IOException {
     App.setRoot("Tutorial");

  }

  /**
   * @author apilgrim
   * @param event
   * @throws IOException
   * 
   *         Is called when "Settings" - button is clicked, switches to the settings screen
   */
  @FXML
  private void settingsClicked(MouseEvent event) throws IOException {
    App.setRoot("Settings");

  }


  /**
   * @author apilgrim
   * @param event
   * @throws IOException
   * 
   *         Is called when "Stats" - button is clicked, switches to the game "stats" Screen
   */
  @FXML
  private void statsClicked(MouseEvent event) throws IOException {
    App.setRoot("Profile");

  }
}
