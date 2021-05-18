package com.scrab5.ui;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.scrab5.util.database.Database;
import com.scrab5.util.database.PlayerProfileDatabase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

/**
 * The SettingsController class controls the components of the "Settings.fxml".
 * 
 * @author mherre
 */
public class SettingsController extends Controller implements Initializable {

  @FXML
  private Slider sliderSFX, sliderMusic;

  /**
   * Call certain methods as soon as the Controller is loaded.
   * 
   * @author mherre
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Database.reconnect();
    String user = Data.getCurrentUser();
    this.sliderMusic.setValue(PlayerProfileDatabase.getMusicVolume(user));
    this.sliderSFX.setValue(PlayerProfileDatabase.getSoundEffectVolume(user));
    this.setupListeners();
    Database.disconnect();
  }

  /**
   * Event method that is called when the "Back"-button in the UI is clicked. Changes the scene to
   * "MainMenu.fxml".
   * 
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void back(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    App.setRoot("MainMenu");
  }

  /**
   * Event method that is called when the "Add Dictionary"-button in the UI is clicked. Opens a
   * <code>FileChooser</code> instance where the user can select a .txt file. The selected file will
   * be copied in the directory of the .jar.
   * 
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
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

  /**
   * Event method that is called when the "Edit Dictionary"-button in the UI is clicked. Opens a
   * <code>FileChooser</code> instance where the user can select a .txt file. The selected file will
   * be opened so the user can edit it.
   * 
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
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

  /**
   * Setups two listeners for <code>sliderMusic</code> and <code>sliderSFX</code>. If the listeners
   * are called then the volume changes to the value the user selected with the sliders. The values
   * are stored in <code>PlayerProfileDatabase</code>.
   * 
   * @author mherre
   */
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
   * Copies a file <code>source</code> into the directory <code>dest</code>.
   * <p>
   * https://stackoverflow.com/questions/16433915/how-to-copy-file-from-one-location-to-another-location
   * </p>
   * 
   * @author mherre
   * @param source the file that will be copied
   * @param dest the directory where <code>source</code> will copied to
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
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
