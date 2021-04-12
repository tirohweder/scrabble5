package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/*
 * @author apilgrim
 */

public class SingleplayerController implements Initializable {

  private boolean tileClicked = false, letterClicked = false;
  private ImageView clickedTile;
  private ImageView clickedLetter;
  private Image markedTile;
  private ImageView ragPlace1;
  private ImageView ragPlace2;
  private ImageView ragPlace3;
  private ImageView ragPlace4;
  private ImageView ragPlace5;
  private ImageView ragPlace6;
  private ImageView ragPlace7;

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

      // check which ragPlace to replace and turn back to rag
      if (ragPlace1 != null
          && iv.getImage().getUrl().equalsIgnoreCase(ragPlace1.getImage().getUrl())
          && ragPlace1.getOpacity() == 0) {
        ragPlace1.setOpacity(1);
      } else if (ragPlace2 != null
          && iv.getImage().getUrl().equalsIgnoreCase(ragPlace2.getImage().getUrl())
          && ragPlace2.getOpacity() == 0) {
        ragPlace2.setOpacity(1);
      } else if (ragPlace3 != null
          && iv.getImage().getUrl().equalsIgnoreCase(ragPlace3.getImage().getUrl())
          && ragPlace3.getOpacity() == 0) {
        ragPlace3.setOpacity(1);
      } else if (ragPlace4 != null
          && iv.getImage().getUrl().equalsIgnoreCase(ragPlace4.getImage().getUrl())
          && ragPlace4.getOpacity() == 0) {
        ragPlace4.setOpacity(1);
      } else if (ragPlace5 != null
          && iv.getImage().getUrl().equalsIgnoreCase(ragPlace5.getImage().getUrl())
          && ragPlace5.getOpacity() == 0) {
        ragPlace5.setOpacity(1);
      } else if (ragPlace6 != null
          && iv.getImage().getUrl().equalsIgnoreCase(ragPlace6.getImage().getUrl())
          && ragPlace6.getOpacity() == 0) {
        ragPlace6.setOpacity(1);
      } else if (ragPlace7 != null
          && iv.getImage().getUrl().equalsIgnoreCase(ragPlace7.getImage().getUrl())
          && ragPlace7.getOpacity() == 0) {
        ragPlace7.setOpacity(1);
      }

      iv.setImage(markedTile);
      clickedTile = null;
      clickedLetter = null;
    } else {
      // check if no tiles has been clicked
      if (!tileClicked) {
        clickedTile = (ImageView) event.getSource();
        if (!letterClicked) {
          lighten(event);
          tileClicked = true;
        } else {
          markedTile = clickedTile.getImage();
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
  private void ragPlace1clicked(MouseEvent event) throws IOException {
    ImageView iv = (ImageView) event.getSource();
    ragPlace1 = iv;
    // no other letter is clicked
    if (!letterClicked && iv.getOpacity() != 0) {

      // no destination tile chosen
      if (!tileClicked) {
        clickedLetter = ragPlace1;
        ragPlace1.setOpacity(0.9);
        letterClicked = true;

        // destination chosen
      } else {
        markedTile = clickedTile.getImage();
        tileClicked = false;
        clickedTile.setImage(ragPlace1.getImage());
        ragPlace1.setOpacity(0);
      }

    } else if (letterClicked && (ragPlace1 == (ImageView) event.getSource())) {
      letterClicked = false;
      ragPlace1.setOpacity(1);
    }
  }

  /**
   * @author Aaron
   * @param event
   * @throws IOException
   */
  @FXML
  private void ragPlace2clicked(MouseEvent event) throws IOException {
    ImageView iv = (ImageView) event.getSource();
    ragPlace2 = iv;
    // no other letter is clicked
    if (!letterClicked && iv.getOpacity() != 0) {

      // no destination tile chosen
      if (!tileClicked) {
        clickedLetter = ragPlace2;
        ragPlace2.setOpacity(0.9);
        letterClicked = true;

        // destination chosen
      } else {
        markedTile = clickedTile.getImage();
        tileClicked = false;
        clickedTile.setImage(ragPlace2.getImage());
        ragPlace2.setOpacity(0);
      }

    } else if (letterClicked && (ragPlace2 == (ImageView) event.getSource())) {
      letterClicked = false;
      ragPlace2.setOpacity(1);
    }
  }

  /**
   * @author Aaron
   * @param event
   * @throws IOException
   */
  @FXML
  private void ragPlace3clicked(MouseEvent event) throws IOException {
    ImageView iv = (ImageView) event.getSource();
    ragPlace3 = iv;
    // no other letter is ragPlace3
    if (!letterClicked && iv.getOpacity() != 0) {

      // no destination tile chosen
      if (!tileClicked) {
        clickedLetter = ragPlace3;
        ragPlace3.setOpacity(0.9);
        letterClicked = true;

        // destination chosen
      } else {
        markedTile = clickedTile.getImage();
        tileClicked = false;
        clickedTile.setImage(ragPlace3.getImage());
        ragPlace3.setOpacity(0);
      }

    } else if (letterClicked && (ragPlace3 == (ImageView) event.getSource())) {
      letterClicked = false;
      ragPlace3.setOpacity(1);
    }
  }

  /**
   * @author Aaron
   * @param event
   * @throws IOException
   */
  @FXML
  private void ragPlace4clicked(MouseEvent event) throws IOException {
    ImageView iv = (ImageView) event.getSource();
    ragPlace4 = iv;
    // no other letter is clicked
    if (!letterClicked && iv.getOpacity() != 0) {

      // no destination tile chosen
      if (!tileClicked) {
        clickedLetter = ragPlace4;
        ragPlace4.setOpacity(0.9);
        letterClicked = true;

        // destination chosen
      } else {
        markedTile = clickedTile.getImage();
        tileClicked = false;
        clickedTile.setImage(ragPlace4.getImage());
        ragPlace4.setOpacity(0);
      }

    } else if (letterClicked && (ragPlace4 == (ImageView) event.getSource())) {
      letterClicked = false;
      ragPlace4.setOpacity(1);
    }
  }

  /**
   * @author Aaron
   * @param event
   * @throws IOException
   */
  @FXML
  private void ragPlace5clicked(MouseEvent event) throws IOException {
    ImageView iv = (ImageView) event.getSource();
    ragPlace5 = iv;
    // no other letter is clicked
    if (!letterClicked && iv.getOpacity() != 0) {

      // no destination tile chosen
      if (!tileClicked) {
        clickedLetter = ragPlace5;
        ragPlace5.setOpacity(0.9);
        letterClicked = true;

        // destination chosen
      } else {
        markedTile = clickedTile.getImage();
        tileClicked = false;
        clickedTile.setImage(ragPlace5.getImage());
        ragPlace5.setOpacity(0);
      }

    } else if (letterClicked && (ragPlace5 == (ImageView) event.getSource())) {
      letterClicked = false;
      ragPlace5.setOpacity(1);
    }
  }

  /**
   * @author Aaron
   * @param event
   * @throws IOException
   */
  @FXML
  private void ragPlace6clicked(MouseEvent event) throws IOException {
    ImageView iv = (ImageView) event.getSource();
    ragPlace6 = iv;
    // no other letter is clicked
    if (!letterClicked && iv.getOpacity() != 0) {

      // no destination tile chosen
      if (!tileClicked) {
        clickedLetter = ragPlace6;
        ragPlace6.setOpacity(0.9);
        letterClicked = true;

        // destination chosen
      } else {
        markedTile = clickedTile.getImage();
        tileClicked = false;
        clickedTile.setImage(ragPlace6.getImage());
        ragPlace6.setOpacity(0);
      }

    } else if (letterClicked && (ragPlace6 == (ImageView) event.getSource())) {
      letterClicked = false;
      ragPlace6.setOpacity(1);
    }
  }

  /**
   * @author Aaron
   * @param event
   * @throws IOException
   */
  @FXML
  private void ragPlace7clicked(MouseEvent event) throws IOException {
    ImageView iv = (ImageView) event.getSource();
    ragPlace7 = iv;
    // no other letter is clicked
    if (!letterClicked && iv.getOpacity() != 0) {

      // no destination tile chosen
      if (!tileClicked) {
        clickedLetter = ragPlace7;
        ragPlace7.setOpacity(0.9);
        letterClicked = true;

        // destination chosen
      } else {
        markedTile = clickedTile.getImage();
        tileClicked = false;
        clickedTile.setImage(ragPlace7.getImage());
        ragPlace7.setOpacity(0);
      }

    } else if (letterClicked && (ragPlace7 == (ImageView) event.getSource())) {
      letterClicked = false;
      ragPlace7.setOpacity(1);
    }
  }

  /**
   * @author Aaron
   * @param event
   * @throws IOException
   */
  @FXML
  private void playClicked(MouseEvent event) throws IOException {
    ragPlace1 = null;
    ragPlace2 = null;
    ragPlace3 = null;
    ragPlace4 = null;
    ragPlace5 = null;
    ragPlace6 = null;
    ragPlace7 = null;
  }
}
