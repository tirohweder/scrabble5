package com.scrab5.ui;

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
 * The PopUpMessageController controls the components of the "PopUpMessage.fxml".
 * 
 * @author mherre
 */

public class PopUpMessageController extends Controller implements Initializable {

  @FXML
  private Text message;
  @FXML
  private ImageView messageIcon;
  @FXML
  private ImageView okayButton, okayButtonClicked;
  @FXML
  private ImageView confirmButton, confirmButtonClicked;
  @FXML
  private TextField textfield;

  /**
   * The initialize method is called once the scene of this controller is loaded. Depending on what
   * kind of Pop-Up got opened the different cases come into play.
   * <p>
   * In case of an "INPUT" PopUp the nickname of the current user gets placed in the textfield,
   * furthermore the opacity of the textfield gets set to "1".
   * </p>
   * In case of an "ERROR" PopUp the original icon of the PopUp message gets changed to a warning
   * symbol.
   * <p>
   * In case of a "CONFIRMATION" PopUp the "Okay"-button gets changed to a "Cancel"-button and the
   * icon to a questionmark. A "Confirm"-button is also added.
   * </p>
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
   * Event method that is called when the "Okay"- or "Cancel"-button in the UI is clicked. In case
   * the pop up is from the type "INPUT", <code>currentUser</code> in {@link com.scrab5.ui.Data
   * Data} gets updated. In any case: The pop up will close afterwards.
   * 
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void okay(MouseEvent event) {
    playSound("ButtonClicked.mp3");

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
   * Event method that is called when the "Confirm"-button in the UI is clicked. Saves the action in
   * {@link com.scrab5.ui.Data Data} and the pop up closes.
   * 
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void confirm(MouseEvent event) {
    playSound("ButtonClicked.mp3");
    Data.setConfirmed(true);
    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    s.close();
  }
}
