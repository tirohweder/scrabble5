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

/**
 * The PopUpMessageController class is supposed to control the components of all kind of
 * PopUpMessages
 * 
 * @author mherre
 */

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

  /**
   * The initialize method is called once the scene of this controller is loaded. Depending on what
   * kind of Pop-Up got opened the different cases come ino play.
   * 
   * In case of an "INPUT" PopUp the nickname of the current user gets placed in the textfield,
   * furthermore the opacity of the textfield gets set to "1".
   * 
   * In case of an "ERROR" PopUp the original icon of the PopUp message gets changed to a warning
   * symbol.
   * 
   * In case of a "CONFIRMATION" PopUp
   * 
   * 
   * @author mherre
   */
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

  /**
   * This method will be called if the "Okay"- or "Cancel"-Button is clicked (this can be the case
   * for all PopUpMessageTypes). In case the Pop-Up is from the type "INPUT", "currentUser" in
   * "Data" gets updated. In any case: The Pop-Up will close afterwards
   * 
   * @author mherre
   * @param event
   */
  @FXML
  private void okay(MouseEvent event) {

    switch (Data.getMessageType()) {
      case INPUT:
        Data.setInputFieldText(textfield.getText());
      default:
        break;
    }

    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    s.close();
  }

  /**
   * This method will only be called if the Pop-Up is from the PopUpMessageType "CONFIRMATION" and
   * the user presses the button "Confirm". If called the boolean "confirmed" in "Data" is set to
   * true and the Pop-Up gets closed.
   * 
   * @param event
   * @throws IOException
   */
  @FXML
  private void confirm(MouseEvent event) throws IOException {
    Data.setConfirmed(true);
    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    s.close();
  }


}
