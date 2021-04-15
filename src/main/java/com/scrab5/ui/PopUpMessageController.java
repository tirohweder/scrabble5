package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PopUpMessageController extends Controller implements Initializable {

  @FXML
  private Text message;
  @FXML
  private ImageView messageIcon;
  @FXML
  private ImageView okayButton;
  @FXML
  private ImageView okayButtonClicked;
  @FXML
  private ImageView confirmButton;
  @FXML
  private ImageView confirmButtonClicked;
  @FXML
  private TextField textfield;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Image img = null;

    switch (Data.getMessageType()) {

      case INPUT:
        this.textfield.setText(Data.getCurrentUser());
        this.textfield.setOpacity(1);
        confirmButtonClicked.setOnMouseEntered(null);
        break;

      case ERROR:
        img = new Image("/com/scrab5/ui/images/Error_WarningIcon.png");
        this.messageIcon.setImage(img);
        confirmButtonClicked.setOnMouseEntered(null);
        break;

      case NOTIFICATION:
        confirmButtonClicked.setOnMouseEntered(null);
        break;

      case CONFIRMATION:
        img = new Image("/com/scrab5/ui/images/Confirmation_QuestionmarkIcon.png");
        this.messageIcon.setImage(img);
        img = new Image("/com/scrab5/ui/images/Confirmation_CancelButton.png");
        this.okayButton.setImage(img);
        img = new Image("/com/scrab5/ui/images/Confirmation_CancelButtonClicked.png");
        this.okayButtonClicked.setImage(img);
        this.confirmButton.setOpacity(1);
        break;

      default:
        break;
    }

    this.message.setText(Data.getMessage());

  }

  @FXML
  private void okay(MouseEvent event) {

    switch (Data.getMessageType()) {
      case INPUT:
        Data.setCurrentUser(textfield.getText());
      default:
        break;
    }

    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    s.close();
  }

  @FXML
  private void confirm(MouseEvent event) throws IOException {
    Data.setConfirmed(true);
    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    s.close();
  }


}
