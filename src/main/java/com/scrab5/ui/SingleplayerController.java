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

public class SingleplayerController extends Controller implements Initializable {

  private boolean clicked = false;

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
  private void fieldClicked(MouseEvent event) throws IOException {
    ImageView iv = ((ImageView) event.getSource());
    iv.setOpacity(1);
    clicked = true;
  }

  public boolean getClicked() {
    return clicked;
  }

}
