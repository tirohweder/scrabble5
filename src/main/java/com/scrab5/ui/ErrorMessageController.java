package com.scrab5.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ErrorMessageController extends Controller implements Initializable {

  @FXML
  private Text message;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // TODO Auto-generated method stub
    this.message.setText(Data.getMessage());

  }

  @FXML
  private void okay(MouseEvent event) {
    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    s.close();
  }



}
