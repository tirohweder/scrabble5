package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.scrab5.core.game.GameSession;
import com.scrab5.util.database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ExchangeController extends InGameController implements Initializable {
  
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    initPlayers();
    
    initRack();


  }
  
  @FXML
  private void backClicked(MouseEvent event) throws IOException {
    App.setRoot("SinglePlayer");
  }
  
  @FXML
  private void exchangeConfirmed(MouseEvent event) throws IOException {
    exchangeTiles();
    Data.getGameSession().finishTurn();
    App.setRoot("SinglePlayer");
  }
  
  private void exchangeTiles() {
    for(int i = 0; i<rackChanges.length; i++) {
      if(rackChanges[i] != null && rackChanges[i] == true) {
        Data.getGameSession().getBag().add(Data.getGameSession().getCurrentPlayer().getRack().getTileAt(i));
        Data.getGameSession().getCurrentPlayer().getRack().removeTileFromRack(i);
        
        Data.getGameSession().getCurrentPlayer().getRack().fill(Data.getGameSession().getBag());
      }
    }
  }
  
  @FXML
  private void rackPlace1clicked(MouseEvent event) {
    if(rackChanges[0] != null && rackChanges[0]) {
      rackChanges[0] = false;
      rackPlace1.setOpacity(1);
    }else {
      rackChanges[0] = true;
      rackPlace1.setOpacity(0.8);
    }
  }
  
  @FXML
  private void rackPlace2clicked(MouseEvent event) {
    if(rackChanges[1] != null && rackChanges[1]) {
      rackChanges[1] = false;
      rackPlace2.setOpacity(1);
    }else {
      rackChanges[1] = true;
      rackPlace2.setOpacity(0.8);
    }
  }
  
  @FXML
  private void rackPlace3clicked(MouseEvent event) {
    if(rackChanges[2] != null && rackChanges[2]) {
      rackChanges[2] = false;
      rackPlace3.setOpacity(1);
    }else {
      rackChanges[2] = true;
      rackPlace3.setOpacity(0.8);
    }
  }
  
  @FXML
  private void rackPlace4clicked(MouseEvent event) {
    if(rackChanges[3] != null && rackChanges[3]) {
      rackChanges[3] = false;
      rackPlace4.setOpacity(1);
    }else {
      rackChanges[3] = true;
      rackPlace4.setOpacity(0.8);
    }
  }
  
  @FXML
  private void rackPlace5clicked(MouseEvent event) {
    if(rackChanges[4] != null && rackChanges[4]) {
      rackChanges[4] = false;
      rackPlace5.setOpacity(1);
    }else {
      rackChanges[4] = true;
      rackPlace5.setOpacity(0.8);
    }
  }
  
  @FXML
  private void rackPlace6clicked(MouseEvent event) {
    if(rackChanges[5] != null && rackChanges[5]) {
      rackChanges[5] = false;
      rackPlace6.setOpacity(1);
    }else {
      rackChanges[5] = true;
      rackPlace6.setOpacity(0.8);
    }
  }
  
  @FXML
  private void rackPlace7clicked(MouseEvent event) {
    if(rackChanges[6] != null && rackChanges[6]) {
      rackChanges[6] = false;
      rackPlace7.setOpacity(1);
    }else {
      rackChanges[6] = true;
      rackPlace7.setOpacity(0.8);
    }
  }
  
  

}
