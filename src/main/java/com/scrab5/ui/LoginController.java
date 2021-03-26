package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/*
 * @author Markus
 */

public class LoginController extends Controller implements Initializable {

  @FXML
  private ImageView quitButton;
  @FXML
  private ImageView createButton;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub

  }


  /*
   * @author Markus
   */
  @FXML
  private void close(MouseEvent event) {
    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    s.close();
  }

  /*
   * @author Markus
   */
  @FXML
  private void create(MouseEvent event) throws IOException {
    App.setRoot("AccountCreation");

  }

}
