package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/*
 * @author apilgrim
 */

public class MainMenuController extends Controller implements Initializable {

  @FXML
  private ImageView multiplayerButton;
  @FXML
  private ImageView singleplayerButton;
  @FXML
  private ImageView tutorialButton;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub

  }


  /**
   * @author Aaron
   * @param event
   * @throws IOException
   */
  @FXML
  private void multiplayerClicked(MouseEvent event) throws IOException {
    App.setRoot("AccountCreation");
  }

  /**
   * @author Aaron
   * @param event
   * @throws IOException
   */
  @FXML
  private void singleplayerClicked(MouseEvent event) throws IOException {
    App.setRoot("AccountCreation");

  }

  /**
   * @author Aaron
   * @param event
   * @throws IOException
   */
  @FXML
  private void tutorialClicked(MouseEvent event) throws IOException {
    App.setRoot("AccountCreation");

  }

}
