package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    this.sliderMusic.valueChangingProperty().addListener(new ChangeListener<Boolean>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean wasChanging,
          Boolean changing) {
        // TODO Auto-generated method stub


        String valueString = String.format("%1$.3f", sliderMusic.getValue());

        if (changing) {
          // startLog.getItems().add(
          // valueString
          // );
        } else {
          // endLog.getItems().add(
          // valueString
          // );
        }
      }
    });
  }



}
