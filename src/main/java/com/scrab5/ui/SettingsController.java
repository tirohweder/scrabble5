package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

/**
 * The SettingsController class is supposed to control the components of the Settings.fxml
 * 
 * @author mherre
 */
public class SettingsController extends Controller implements Initializable {

  @FXML
  private Slider sliderSFX, sliderMusic;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // TODO Auto-generated method stub
    App.setMusicVolume(0);

  }

  /**
   * Event method that is called when the "Back"-button is clicked. Scene gets changed to the
   * predecessor "MainMenu" scene
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

  @FXML
  private void setMusic() {


  }



}
