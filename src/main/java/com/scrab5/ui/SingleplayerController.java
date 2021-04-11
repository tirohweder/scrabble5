package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/*
 * @author apilgrim
 */

public class SingleplayerController implements Initializable {

  private boolean tileClicked = false, letterClicked = false;
  private ImageView clickedTile;
  private ImageView clickedLetter;
  private ImageView markedTile;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub

  }


  /**
   * @author Aaron
   * @param event
   * @throws IOException
   */
  @FXML
  private void fieldClicked(MouseEvent event) throws IOException {

    ImageView iv = ((ImageView) event.getSource());

    // check if the field clicked is a letter to remove
    if (iv.getImage().getUrl().contains("letter_Tiles")) {
      iv.setOpacity(0);
      iv.setImage(markedTile.getImage());
      clickedLetter.setOpacity(1);
      clickedLetter = null;
    } else {
      // check if no tiles has been clicked
      if (!tileClicked) {
        clickedTile = (ImageView) event.getSource();
        if (!letterClicked) {
          lighten(event);
          tileClicked = true;
        } else {
          markedTile = clickedTile;
          clickedTile.setImage(clickedLetter.getImage());;
          clickedLetter.setOpacity(0);
          letterClicked = false;
        }
      } else if (tileClicked && (clickedTile == (ImageView) event.getSource())) {
        darken(event);
        tileClicked = false;
        clickedTile = null;
      }
    }
  }

  /**
   * @author Aaron
   */
  @FXML
  private void lighten(MouseEvent event) {
    ImageView iv = ((ImageView) event.getSource());
    iv.setOpacity(1);
  }

  /**
   * @author Aaron
   */
  @FXML
  private void darken(MouseEvent event) {

    ImageView iv = ((ImageView) event.getSource());
    // check that Tile is not clicked and isnt a letter
    if (clickedTile != iv && !iv.getImage().getUrl().contains("letter_Tiles")) {
      iv.setOpacity(0);
    }
  }

  /**
   * @author Aaron
   * @param event
   * @throws IOException
   */
  @FXML
  private void letterClicked(MouseEvent event) throws IOException {
    ImageView iv = (ImageView) event.getSource();
    // no other letter is clicked
    if (!letterClicked && iv.getOpacity() != 0) {
      clickedLetter = (ImageView) event.getSource();

      // no destination tile chosen
      if (!tileClicked) {
        clickedLetter.setOpacity(0.9);
        letterClicked = true;

        // destination chosen
      } else {
        markedTile = clickedTile;
        tileClicked = false;
        clickedTile.setImage(clickedLetter.getImage());
        clickedLetter.setOpacity(0);
      }

    } else if (letterClicked && (clickedLetter == (ImageView) event.getSource())) {
      letterClicked = false;
      clickedLetter.setOpacity(1);
      clickedLetter = null;
    }
  }
}

// /**
// * @author Aaron
// * @param event
// * @throws IOException
// */
// @FXML
// private void letterDragged(MouseEvent event) throws IOException {
// ImageView iv = ((ImageView) event.getSource());
// iv.setOnDragDetected(new EventHandler<MouseEvent>() {
//
// public void handle(MouseEvent dragEvent) {
// Dragboard letterBoard = iv.startDragAndDrop(TransferMode.ANY);
//
// ClipboardContent content = new ClipboardContent();
// content.putImage(iv.getImage());
//
// letterBoard.setContent(content);
// };
// });
//
// }
//
// /**
// * @author Aaron
// * @param event
// * @throws IOException
// */
// @FXML
// private void letterDroppable(MouseEvent event) throws IOException {
// ImageView iv = ((ImageView) event.getSource());
//
// iv.setOnDragOver(new EventHandler<DragEvent>() {
//
// public void handle(DragEvent dropEvent) {
//
// dropEvent.acceptTransferModes(TransferMode.ANY);;
// };
// });
//
// }
//
// /**
// * @author Aaron
// * @param event
// * @throws IOException
// */
// @FXML
// private void letterDropped(MouseEvent event) throws IOException {
// ImageView iv = ((ImageView) event.getSource());
//
// iv.setOnDragDropped(new EventHandler<DragEvent>() {
//
// public void handle(DragEvent event) {
//
// Dragboard d = event.getDragboard();
// iv.setImage(d.getImage());
// };
// });
//
// }
//
//
//
// }
