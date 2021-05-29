package com.scrab5.ui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The PopUpMessage class is used to create various pop up messages.
 *
 * @author mherre
 */
public class PopUpMessage {

  private Stage popUp;

  /**
   * Creates a new pop up message from a <code>String</code> and a {@link
   * com.scrab5.ui.PopUpMessageType PopUpMessageType}. Creates a new <code>Stage</code> and sets up
   * the <code>PopUpMessageController</code> in {@link com.scrab5.ui.Data Data}, so {@link
   * com.scrab5.ui.PopUpMessageController PopUpMessageController} works correctly.
   *
   * @author mherre
   * @param message the String that will be displayed in the UI
   * @param type the PopUpMessageType containg the type of message
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *     exist
   */
  public PopUpMessage(String message, PopUpMessageType type) throws IOException {

    this.popUp = new Stage();
    Data.setMessage(message);

    switch (type) {
      case INPUT:
        Data.setMessageType(PopUpMessageType.INPUT);
        break;
      case ERROR:
        Data.setMessageType(PopUpMessageType.ERROR);
        break;
      case CONFIRMATION:
        Data.setMessageType(PopUpMessageType.CONFIRMATION);
        break;
      case NOTIFICATION:
        Data.setMessageType(PopUpMessageType.NOTIFICATION);
        break;
      default:
    }

    this.popUp.setScene(new Scene(loadFXML(), 500, 200));
    this.popUp.initModality(Modality.APPLICATION_MODAL);
    this.popUp.setResizable(false);
  }

  /**
   * Shows the pop up in the UI. Calls {@link javafx.stage.Stage#showAndWait()}.
   *
   * @author mherre
   */
  public void show() {
    this.popUp.showAndWait();
  }

  /**
   * Loads the given fxml and returns it as parent.
   *
   * @return Parent
   * @throws IOException tried to read a local file that was no longer available
   * @author trohwede
   */
  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  private Parent loadFXML() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("PopUpMessage" + ".fxml"));
    return fxmlLoader.load();
  }
}
