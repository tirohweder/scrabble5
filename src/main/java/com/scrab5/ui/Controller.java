package com.scrab5.ui;

import com.scrab5.util.database.Database;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * The Controller class contains some basic methods to create buttons using ImageView objects
 * 
 * @author mherre
 *
 */

public abstract class Controller {

  private ImageView iv;
  private MediaPlayer mediaPlayer;
  private Media sound;

  /**
   * 
   * Method that changes opacity of an image to 1. Image is usally a copy of a button image but with
   * a white filter on it. It is supposed to lighten up a button when hovering over it with a mouse
   * 
   * @author mherre
   *
   */
  @FXML
  private void lighten(MouseEvent event) {
    ImageView iv = ((ImageView) event.getSource());
    iv.setOpacity(1);
  }

  /**
   * Method that changes opacity of an image to 0. Image is usally a copy of a button image but with
   * a white filter on it. It is supposed to darken a button when leaving the button area with the
   * mouse
   * 
   * @author mherre
   * 
   */
  @FXML
  private void darken(MouseEvent event) {
    iv = ((ImageView) event.getSource());
    iv.setOpacity(0);
  }

  @FXML
  private void close(MouseEvent event) {
    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    s.close();
  }

  @FXML
  private void closeGame(MouseEvent event) {
    Database.disconnect();
    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();

    if (Data.getPlayerServer() != null)
      Data.getPlayerServer().shutDownServer();
    if (Data.getPlayerClient() != null)
      Data.getPlayerClient().stopClientThread();

    s.close();
  }

  protected void playSound(String file) {
    sound = new Media(
        Controller.class.getResource("/com/scrab5/ui/sound_effects/" + file).toExternalForm());
    mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.setVolume(Data.getSFXVolume());
    mediaPlayer.play();
  }


}
