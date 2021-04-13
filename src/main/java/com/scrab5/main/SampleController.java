package com.scrab5.main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SampleController implements Initializable {

  @FXML
  private ImageView quitButton;
  @FXML
  private ImageView createButton;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub

  }

  @FXML
  private void lighten(MouseEvent event) {
    String id = ((ImageView) event.getSource()).getId();

    if (id.equals("quitButton")) {
      quitButton.setOpacity(1);

    } else if (id.equals("createButton")) {
      createButton.setOpacity(1);
    }
  }

  @FXML
  private void close(MouseEvent event) {
    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    s.close();
  }

  @FXML
  private void darken(MouseEvent event) {
    ImageView iv = ((ImageView) event.getSource());
    iv.setOpacity(0);
  }


  @FXML
  private void create(MouseEvent event) {
    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    s.close();
  }

}
