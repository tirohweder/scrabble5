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

public class SingleplayerController implements Initializable {

  private boolean clicked = false;
  private ImageView clickedTile;

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
    if (!clicked) {
      lighten(event);
      clickedTile = (ImageView) event.getSource();
      clicked = true;
    } else if (clicked && (clickedTile == (ImageView) event.getSource())) {
      darken(event);
      clicked = false;
      clickedTile = null;
    }
  }

  /**
   * @author Aaron
   */
  @FXML
  private void lighten(MouseEvent event) {
    ImageView iv = ((ImageView) event.getSource());
    iv.setOpacity(1);
  }

  /**
   * @author Aaron
   */
  @FXML
  private void darken(MouseEvent event) {

    if (clickedTile != (ImageView) event.getSource()) {
      ImageView iv = ((ImageView) event.getSource());
      iv.setOpacity(0);
    }
  }

}
