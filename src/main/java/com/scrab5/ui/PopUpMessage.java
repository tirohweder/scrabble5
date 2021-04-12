package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUpMessage extends Controller implements Initializable {

  Stage s = new Stage();

  public PopUpMessage(String message, PopUpMessageType messageType) throws IOException {

    switch (messageType) {
      case ERROR:
        s.setScene(new Scene(loadFXML("ErrorMessage"), 400, 400));
        break;
      default:
        break;
    }
    s.setResizable(false);
    s.setAlwaysOnTop(true);
    s.initModality(Modality.APPLICATION_MODAL);

  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub

  }

  public void show() {
    s.showAndWait();
  }



  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }



}
