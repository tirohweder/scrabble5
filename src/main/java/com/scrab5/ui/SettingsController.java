package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.scrab5.util.database.PlayerProfileDatabase;
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
    String user = Data.getCurrentUser();
    this.sliderMusic.setValue(PlayerProfileDatabase.getMusicVolume(user));
    this.sliderSFX.setValue(PlayerProfileDatabase.getSoundEffectVolume(user));
    this.setupListeners();
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


  private void setupListeners() {

    sliderMusic.valueProperty().addListener(new ChangeListener<Number>() {

      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {
        App.setMusicVolume((double) newValue / 100);
        PlayerProfileDatabase.setMusicVolume(Data.getCurrentUser(), (double) newValue);
      }
    });

    sliderSFX.valueProperty().addListener((new ChangeListener<Number>() {

      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {
        Data.setSFXVolume((double) newValue / 100);
        PlayerProfileDatabase.setSoundEffectVolume(Data.getCurrentUser(), (double) newValue);
      }
    }));
  }



}
