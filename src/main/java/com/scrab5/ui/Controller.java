package com.scrab5.ui;

import com.scrab5.util.database.Database;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * The Controller class contains some basic methods which are used in several other Controller
 * classes.
 *
 * @author mherre
 */
public abstract class Controller {

  private ImageView iv;

  /**
   * Event method that is called when the mouse is hovering over the <code>ImageView</code> object
   * <code>iv</code> or released after it has been clicked. Changes the opacity of it to 1.
   *
   * <p>
   * <code>iv</code> is usually a button image file with a white filter. The same button image
   * without the white filter is underlying, this gives the impression that it is an actual button.
   *
   * @author mherre
   * @param event the event that is created from the hovering and releasing
   */
  @FXML
  private void lighten(MouseEvent event) {
    iv = ((ImageView) event.getSource());
    iv.setOpacity(1);
  }

  /**
   * Event method that is called when the mouse is exiting the <code>ImageView</code> object <code>
   * iv</code> or when it is pressed. Changes the opacity of it to 0.
   *
   * <p>
   * <code>iv</code> is usually a button image file with a white filter. The same button image
   * without the white filter is underlying, this gives the impression that it is an actual button.
   *
   * @author mherre
   * @param event the event that is created from exiting and pressing
   */
  @FXML
  private void darken(MouseEvent event) {
    iv = ((ImageView) event.getSource());
    iv.setOpacity(0);
  }

  /**
   * Event method that is called when some sort of closing button in the UI is clicked. Closes the
   * current stage. Mainly used for pop ups.
   *
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void close(MouseEvent event) {
    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    s.close();
  }

  /**
   * Event method that is called when some sort of closing button in the UI is clicked. Closes the
   * current stage. Mainly used for the main stage.
   *
   * @author mherre
   * @author nitterhe
   * @author lengist
   * @param event the event that is created from the mouse-click
   */
  @FXML
  protected void closeGame(MouseEvent event) {
    Database.disconnect();

    if (Data.getHostedServer() != null) {
      Data.getHostedServer().shutDownServer();
    }

    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    s.close();
  }

  /**
   * Method that plays a sound file and adjusts the volume to the volume that has been set by the
   * user in the "Settings.fxml" scene.
   *
   * @author mherre
   */
  protected void playSound() {
    Media sound = new Media(Objects
        .requireNonNull(
            Controller.class.getResource("/com/scrab5/ui/sound_effects/" + "ButtonClicked.mp3"))
        .toExternalForm());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.setVolume(Data.getSFXVolume());
    mediaPlayer.play();
  }
}
