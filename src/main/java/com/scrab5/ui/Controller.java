package com.scrab5.ui;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public abstract class Controller {
  /*
   * @author Markus
   */
  @FXML
  private void lighten(MouseEvent event) {
    ImageView iv = ((ImageView) event.getSource());
    iv.setOpacity(1);
  }

  /*
   * @author Markus
   */
  @FXML
  private void darken(MouseEvent event) {
    ImageView iv = ((ImageView) event.getSource());
    iv.setOpacity(0);
  }
}
