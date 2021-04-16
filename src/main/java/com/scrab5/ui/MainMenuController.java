package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/*
 * @author apilgrim
 */

public class MainMenuController extends Controller implements Initializable {

  @FXML
  private ImageView multiplayerButton;
  @FXML
  private ImageView singleplayerButton;
  @FXML
  private ImageView tutorialButton;

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
    // App.setRoot("AccountCreation");

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

  /**
   * @author apilgrim
   * @param event
   * 
   *        Is called when "Exit" - button is clicked, closes the application and ends the programm
   */
  @FXML
  private void close(MouseEvent event) {
    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    s.close();
  }

}
