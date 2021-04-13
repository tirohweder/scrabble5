package com.scrab5.ui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUpMessage {

  private Stage popUp;

  public PopUpMessage(String message, PopUpMessageType type) throws IOException {

    this.popUp = new Stage();
    Data.setMessage(message);

    switch (type) {
      case ERROR:
        this.popUp.setScene(new Scene(loadFXML("ErrorMessage"), 500, 200));
        break;
      case CONFIRMATION:
        this.popUp.setScene(new Scene(loadFXML("ConfirmationMessage"), 500, 200));
        break;
      case NOTIFICATION:
        this.popUp.setScene(new Scene(loadFXML("NotificationMessage"), 500, 200));
        break;
      default:
    }

    this.popUp.initModality(Modality.APPLICATION_MODAL);
    this.popUp.setResizable(false);
  }

  public void show() {
    this.popUp.showAndWait();
  }

  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }


}
