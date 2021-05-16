package com.scrab5.ui;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;


public class MultiplayerController extends InGameController implements Initializable {
  
  @FXML
  ImageView chatImage;
  @FXML
  TextFlow chatTextField;
  @FXML
  TextField chatInsert;
  
  private boolean chatOpen = false;
  @FXML
  private void chatClicked(MouseEvent event) {
    if(chatOpen) {
      chatImage.setOpacity(0);
      chatTextField.setOpacity(0);
      chatInsert.setOpacity(0);
    }else {
      chatImage.setOpacity(1);
      chatTextField.setOpacity(1);
      chatInsert.setOpacity(1);
    }
  }
  
  @FXML
  private void sentClicked(MouseEvent event) {
    if(chatOpen) {
      if(chatInsert.getText().length()>0) {
        System.out.println("Done");
      }
    }
  }
  
  
  @FXML
  private void chatInsertClicked(MouseEvent event) {
  }
  
}
  