package com.scrab5.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class PopUpMessageController extends Controller implements Initializable {

  @FXML
  private Text message;
  @FXML
  private ImageView messageIcon;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Image img = null;

    switch (Data.getMessageType()) {
      case ERROR:
        img = new Image("/com/scrab5/ui/images/Error_WarningIcon.png");
        this.messageIcon.setImage(img);
        break;
      case CONFIRMATION:
        break;
      default:
    }

    this.message.setText(Data.getMessage());

  }

  @FXML
  private void okay(MouseEvent event) {
    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    s.close();
  }

}
