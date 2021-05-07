package com.scrab5.ui;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import javafx.stage.FileChooser;

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

  @FXML
  private void addDictionary(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");

    FileChooser fc = new FileChooser();
    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Dictionaries", "*.txt"));
    File source = fc.showOpenDialog(App.getMainStage());

    if (source != null) {
      String fileName = source.getName();
      File destination = new File(System.getProperty("user.dir") + "\\" + fileName);
      this.copyFile(source, destination);
    }

  }



  @FXML
  private void editDictionaries(MouseEvent event) throws IOException {
    FileChooser fc = new FileChooser();
    fc.setInitialDirectory(new File(System.getProperty("user.dir")));
    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Dictionaries", "*.txt"));
    File source = fc.showOpenDialog(App.getMainStage());
    if (source != null) {
      Desktop.getDesktop().open(source);
    }
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

  /**
   * https://stackoverflow.com/questions/16433915/how-to-copy-file-from-one-location-to-another-location
   * 
   * @param source
   * @param dest
   * @throws IOException
   */
  private void copyFile(File source, File dest) throws IOException {
    FileInputStream is = null;
    FileOutputStream os = null;
    try {
      is = new FileInputStream(source);
      os = new FileOutputStream(dest);
      byte[] buffer = new byte[1024];
      int length;
      while ((length = is.read(buffer)) > 0) {
        os.write(buffer, 0, length);
      }
    } finally {
      is.close();
      os.close();
    }
  }



}
