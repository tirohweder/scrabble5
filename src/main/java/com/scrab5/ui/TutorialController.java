package com.scrab5.ui;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * The TutorialController class handels everything that happens during the "Play Tutorial" mode.
 * Displays next and prebvious explanations. Controller for Tutorial.fxml. Images created using
 * photoshop.
 *
 * @author apilgrim
 */

public class TutorialController extends Controller implements Initializable {

  @FXML
  private ImageView tutorial;

  private int page = 1;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

  }


  /**
   * Is called when the "Next" button is clicked, so the next explanation will be displayed.
   * 
   * @author apilgrim
   * @param event - MouseEvent created, when the "Next" button is clicked
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void nextClicked(MouseEvent event) throws IOException {
    page++;
    if (page < 50) {
      tutorial.setImage(
          new Image("/com/scrab5/ui/tutorial_Images/Tutorial_" + Integer.toString(page) + ".png"));
    } else {
      App.setRoot("SingleplayerLobby");
    }
  }


  /**
   * Is called when the "Back" button is clicked, so the previous explanation will be displayed.
   * 
   * @author apilgrim
   * @param event - MouseEvent created, when the "Back" button is clicked
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void backClicked(MouseEvent event) throws IOException {
    page--;
    if (page > 0) {
      tutorial.setImage(
          new Image("/com/scrab5/ui/tutorial_Images/Tutorial_" + Integer.toString(page) + ".png"));
    } else {
      App.setRoot("MainMenu");
    }
  }


  /**
   * Is called when the "Back" button is clicked, so the previous explanation will be displayed.
   * 
   * @author apilgrim
   * @param event - MouseEvent created, when the "Back" button is clicked
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void backToMenu(MouseEvent event) throws IOException {
    PopUpMessage pum = new PopUpMessage("Back to main menu?", PopUpMessageType.CONFIRMATION);
    pum.show();

    if (Data.isConfirmed()) {
      App.setRoot("MainMenu");
    }
  }
}
