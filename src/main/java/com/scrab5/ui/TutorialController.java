package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/*
 * @author apilgrim
 */

public class TutorialController extends Controller implements Initializable {
  
  @FXML
  private ImageView tutorial;
  private int number = 1;

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
  private void nextClicked(MouseEvent event) throws IOException {
    number++;
    if(number<11) {
      tutorial.setImage(new Image("/com/scrab5/ui/tutorial_Images/Tutorial_"+Integer.toString(number)+".png"));
    }else {
      App.setRoot("SingleplayerLobby");
    }
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
    number--;
    if(number>0) {
      tutorial.setImage(new Image("/com/scrab5/ui/tutorial_Images/Tutorial_"+Integer.toString(number)+".png"));
    }else {
      App.setRoot("MainMenu");
    }
  }



  /**
   * @author apilgrim
   * @param event
   * 
   *        Is called when "Back to Main Menu" - button is clicked, returns to the Main Menu
   */
  @FXML
  private void close(MouseEvent event) {
    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    s.close();
  }

}
