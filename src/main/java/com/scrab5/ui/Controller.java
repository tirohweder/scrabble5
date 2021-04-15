package com.scrab5.ui;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public abstract class Controller {
  /**
   * @author marku
   * 
   *         Method that changes opacity of an image to 1. Image is usally a copy of a button image
   *         but with a white filter on it. It is supposed to lighten up a button when hovering over
   *         it with a mouse
   */
  @FXML
  private void lighten(MouseEvent event) {
    ImageView iv = ((ImageView) event.getSource());
    iv.setOpacity(1);
  }

  /**
   * @author marku
   * 
   *         Method that changes opacity of an image to 0. Image is usally a copy of a button image
   *         but with a white filter on it. It is supposed to darken a button when leaving the
   *         button area with the mouse
   */
  @FXML
  private void darken(MouseEvent event) {
    ImageView iv = ((ImageView) event.getSource());
    iv.setOpacity(0);
  }

  public void attach() {

  }

  public void detach() {

  }
}
