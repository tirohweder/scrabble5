package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

/*
 * @author apilgrim
 */

public class TutorialController extends Controller implements Initializable {


  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub

  }


  /**
   * @author apilgrim
   * @param event
   * @throws IOException
   * 
   *         is called when "got it" button is clicked as soon as an explanation is understood, next
   *         explanation will be displayed
   */
  @FXML
  private void gotItClicked(MouseEvent event) throws IOException {
    // App.setRoot("nextExplanation");
  }

  /**
   * @author apilgrim
   * @param event
   * @throws IOException
   * 
   *         is called when "back" button is clicked and previous explanation is displayed
   */
  @FXML
  private void backClicked(MouseEvent event) throws IOException {
    // App.setRoot("nextExplanation");
  }



  /**
   * @author apilgrim
   * @param event
   * 
   *        Is called when "Back to Main Menu" - button is clicked, returns to the Main Menu
   * @throws IOException
   */
  @FXML
  private void toMainClicked(MouseEvent event) throws IOException {
    App.setRoot("MainMenu");
  }

}
