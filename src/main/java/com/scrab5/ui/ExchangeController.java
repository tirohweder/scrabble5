package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

/**
 * The ExchangeController class controls the exchange process of the "Exchange.fxml".
 *
 * @author apilgrim
 */
public class ExchangeController extends InGameController implements Initializable {

  private int counter = 0;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    initPlayers();
    initRack();
  }

  /**
   * Is called when "back" - button is clicked, switches back to the game.
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "back" button is clicked
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *     exist
   */
  @FXML
  private void backClicked(MouseEvent event) throws IOException {
    if (Data.getGameSession().isOnline()) {
      App.setRoot("MultiPlayer");
    } else {
      App.setRoot("SinglePlayer");
    }
  }

  /**
   * Is called when "exchange" - button is clicked, exchanges the selected tiles and finishes the
   * turn when at least one tile is selected.
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "exchange" button is clicked
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *     exist
   */
  @FXML
  private void exchangeConfirmed(MouseEvent event) throws IOException {
    exchangeTiles();
    if (Data.getGameSession().isOnline()) {
      App.setRoot("MultiPlayer");
    } else {
      App.setRoot("SinglePlayer");
    }
    if (counter != 0) {
      Data.getGameSession().finishTurn();
    }
  }

  /**
   * Is called when "exchange" - button is clicked, exchanges the selected tiles and returns them to
   * the bag. Than drag new ones from the bag and place them in the rack.
   *
   * @author apilgrim
   */
  private void exchangeTiles() {
    for (int i = 0; i < rackChanges.length; i++) {
      if (rackChanges[i] != null && rackChanges[i]) {
        counter++;
        Data.getGameSession()
            .getBag()
            .add(Data.getGameSession().getCurrentPlayer().getRack().getTileAt(i));
        Data.getGameSession().getCurrentPlayer().getRack().removeTileFromRack(i);

        Data.getGameSession().getCurrentPlayer().getRack().fill(Data.getGameSession().getBag());
      }
    }
  }

  /**
   * Selects/ unselects the rackplace1 (by adding to a list) to exchange when confirmed by clicking
   * "exchange".
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "rackplace1" is clicked
   */
  @FXML
  private void rackPlace1clicked(MouseEvent event) {
    if (rackChanges[0] != null && rackChanges[0]) {
      rackChanges[0] = false;
      rackPlace1.setOpacity(1);
    } else {
      rackChanges[0] = true;
      rackPlace1.setOpacity(0.8);
    }
  }

  /**
   * Selects/ unselects the rackplace2 (by adding to a list) to exchange when confirmed by clicking
   * "exchange".
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "rackplace2" is clicked
   */
  @FXML
  private void rackPlace2clicked(MouseEvent event) {
    if (rackChanges[1] != null && rackChanges[1]) {
      rackChanges[1] = false;
      rackPlace2.setOpacity(1);
    } else {
      rackChanges[1] = true;
      rackPlace2.setOpacity(0.8);
    }
  }

  /**
   * Selects/ unselects the rackplace3 (by adding to a list) to exchange when confirmed by clicking
   * "exchange".
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "rackplace3" is clicked
   */
  @FXML
  private void rackPlace3clicked(MouseEvent event) {
    if (rackChanges[2] != null && rackChanges[2]) {
      rackChanges[2] = false;
      rackPlace3.setOpacity(1);
    } else {
      rackChanges[2] = true;
      rackPlace3.setOpacity(0.8);
    }
  }

  /**
   * Selects/ unselects the rackplace4 (by adding to a list) to exchange when confirmed by clicking
   * "exchange".
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "rackplace4" is clicked
   */
  @FXML
  private void rackPlace4clicked(MouseEvent event) {
    if (rackChanges[3] != null && rackChanges[3]) {
      rackChanges[3] = false;
      rackPlace4.setOpacity(1);
    } else {
      rackChanges[3] = true;
      rackPlace4.setOpacity(0.8);
    }
  }

  /**
   * Selects/ unselects the rackplace5 (by adding to a list) to exchange when confirmed by clicking
   * "exchange".
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "rackplace5" is clicked
   */
  @FXML
  private void rackPlace5clicked(MouseEvent event) {
    if (rackChanges[4] != null && rackChanges[4]) {
      rackChanges[4] = false;
      rackPlace5.setOpacity(1);
    } else {
      rackChanges[4] = true;
      rackPlace5.setOpacity(0.8);
    }
  }

  /**
   * Selects/ unselects the rackplace6 (by adding to a list) to exchange when confirmed by clicking
   * "exchange".
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "rackplace6" is clicked
   */
  @FXML
  private void rackPlace6clicked(MouseEvent event) {
    if (rackChanges[5] != null && rackChanges[5]) {
      rackChanges[5] = false;
      rackPlace6.setOpacity(1);
    } else {
      rackChanges[5] = true;
      rackPlace6.setOpacity(0.8);
    }
  }

  /**
   * Selects/ unselects the rackplace7 (by adding to a list) to exchange when confirmed by clicking
   * "exchange".
   *
   * @author apilgrim
   * @param event - MouseEvent created, when the "rackplace7" is clicked
   */
  @FXML
  private void rackPlace7clicked(MouseEvent event) {
    if (rackChanges[6] != null && rackChanges[6]) {
      rackChanges[6] = false;
      rackPlace7.setOpacity(1);
    } else {
      rackChanges[6] = true;
      rackPlace7.setOpacity(0.8);
    }
  }
}
