package com.scrab5.ui;

import com.scrab5.core.game.GameBoard;
import com.scrab5.core.game.GameSession;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
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

  private ImageView rackPlace1;
  private ImageView rackPlace2;
  private ImageView rackPlace3;
  private ImageView rackPlace4;
  private ImageView rackPlace5;
  private ImageView rackPlace6;
  private ImageView rackPlace7;

  private ArrayList<String> unavailableTiles = new ArrayList<String>();
  private ArrayList<String> choosenTiles = new ArrayList<String>();

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub

  }


  /**
   * @param event
   * @throws IOException
   * @author Aaron
   */
  @FXML
  private void fieldClicked(MouseEvent event) throws IOException {

    ImageView iv = ((ImageView) event.getSource());

    String cordinate = iv.getId();

    GameBoard current = GameSession.getGameBoard();

    boolean isFree =
        current.isSpotFree(rowTransformation(cordinate), columnTransformation(cordinate));

    // check if the field clicked is a letter to remove
    if (iv.getImage().getUrl().contains("letter_Tiles") && !placeTaken(iv)) {

      // check which ragPlace to replace and turn back to rack
      if (rackPlace1 != null
          && iv.getImage().getUrl().equalsIgnoreCase(rackPlace1.getImage().getUrl())
          && rackPlace1.getOpacity() == 0) {
        rackPlace1.setOpacity(1);
      } else if (rackPlace2 != null
          && iv.getImage().getUrl().equalsIgnoreCase(rackPlace2.getImage().getUrl())
          && rackPlace2.getOpacity() == 0) {
        rackPlace2.setOpacity(1);
      } else if (rackPlace3 != null
          && iv.getImage().getUrl().equalsIgnoreCase(rackPlace3.getImage().getUrl())
          && rackPlace3.getOpacity() == 0) {
        rackPlace3.setOpacity(1);
      } else if (rackPlace4 != null
          && iv.getImage().getUrl().equalsIgnoreCase(rackPlace4.getImage().getUrl())
          && rackPlace4.getOpacity() == 0) {
        rackPlace4.setOpacity(1);
      } else if (rackPlace5 != null
          && iv.getImage().getUrl().equalsIgnoreCase(rackPlace5.getImage().getUrl())
          && rackPlace5.getOpacity() == 0) {
        rackPlace5.setOpacity(1);
      } else if (rackPlace6 != null
          && iv.getImage().getUrl().equalsIgnoreCase(rackPlace6.getImage().getUrl())
          && rackPlace6.getOpacity() == 0) {
        rackPlace6.setOpacity(1);
      } else if (rackPlace7 != null
          && iv.getImage().getUrl().equalsIgnoreCase(rackPlace7.getImage().getUrl())
          && rackPlace7.getOpacity() == 0) {
        rackPlace7.setOpacity(1);
      }
      choosenTiles.remove(iv.getId());
      iv.setImage(markedTile);
      clickedTile = null;
      clickedLetter = null;
    } else {
      // check if no tiles has been clicked
      if (!tileClicked && !placeTaken(iv)) {
        clickedTile = (ImageView) event.getSource();
        if (!letterClicked) {
          lighten(event);
          tileClicked = true;
        } else if (!iv.getImage().getUrl().contains("letter_Tiles")) {
          markedTile = clickedTile.getImage();
          clickedTile.setImage(clickedLetter.getImage());
          choosenTiles.add(clickedTile.getId());
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
   * @param event
   * @throws IOException
   * @author Aaron
   */
  @FXML
  private void ragPlace1clicked(MouseEvent event) throws IOException {
    ImageView iv = (ImageView) event.getSource();
    rackPlace1 = iv;
    // no other letter is clicked
    if (!letterClicked && iv.getOpacity() != 0) {

      // no destination tile chosen
      if (!tileClicked) {
        clickedLetter = rackPlace1;
        rackPlace1.setOpacity(0.9);
        letterClicked = true;

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        markedTile = clickedTile.getImage();
        tileClicked = false;
        clickedTile.setImage(rackPlace1.getImage());
        choosenTiles.add(clickedTile.getId());
        rackPlace1.setOpacity(0);
      }

    } else if (letterClicked && (rackPlace1 == (ImageView) event.getSource())) {
      letterClicked = false;
      rackPlace1.setOpacity(1);
    }
  }

  /**
   * @param event
   * @throws IOException
   * @author Aaron
   */
  @FXML
  private void ragPlace2clicked(MouseEvent event) throws IOException {
    ImageView iv = (ImageView) event.getSource();
    rackPlace2 = iv;
    // no other letter is clicked
    if (!letterClicked && iv.getOpacity() != 0) {

      // no destination tile chosen
      if (!tileClicked) {
        clickedLetter = rackPlace2;
        rackPlace2.setOpacity(0.9);
        letterClicked = true;

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        markedTile = clickedTile.getImage();
        tileClicked = false;
        clickedTile.setImage(rackPlace2.getImage());
        choosenTiles.add(clickedTile.getId());
        rackPlace2.setOpacity(0);
      }

    } else if (letterClicked && (rackPlace2 == (ImageView) event.getSource())) {
      letterClicked = false;
      rackPlace2.setOpacity(1);
    }
  }

  /**
   * @param event
   * @throws IOException
   * @author Aaron
   */
  @FXML
  private void ragPlace3clicked(MouseEvent event) throws IOException {
    ImageView iv = (ImageView) event.getSource();
    rackPlace3 = iv;
    // no other letter is rackPlace3
    if (!letterClicked && iv.getOpacity() != 0) {

      // no destination tile chosen
      if (!tileClicked) {
        clickedLetter = rackPlace3;
        rackPlace3.setOpacity(0.9);
        letterClicked = true;

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        markedTile = clickedTile.getImage();
        tileClicked = false;
        clickedTile.setImage(rackPlace3.getImage());
        choosenTiles.add(clickedTile.getId());
        rackPlace3.setOpacity(0);
      }

    } else if (letterClicked && (rackPlace3 == (ImageView) event.getSource())) {
      letterClicked = false;
      rackPlace3.setOpacity(1);
    }
  }

  /**
   * @param event
   * @throws IOException
   * @author Aaron
   */
  @FXML
  private void ragPlace4clicked(MouseEvent event) throws IOException {
    ImageView iv = (ImageView) event.getSource();
    rackPlace4 = iv;
    // no other letter is clicked
    if (!letterClicked && iv.getOpacity() != 0) {

      // no destination tile chosen
      if (!tileClicked) {
        clickedLetter = rackPlace4;
        rackPlace4.setOpacity(0.9);
        letterClicked = true;

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        markedTile = clickedTile.getImage();
        tileClicked = false;
        clickedTile.setImage(rackPlace4.getImage());
        choosenTiles.add(clickedTile.getId());
        rackPlace4.setOpacity(0);
      }

    } else if (letterClicked && (rackPlace4 == (ImageView) event.getSource())) {
      letterClicked = false;
      rackPlace4.setOpacity(1);
    }
  }

  /**
   * @param event
   * @throws IOException
   * @author Aaron
   */
  @FXML
  private void ragPlace5clicked(MouseEvent event) throws IOException {
    ImageView iv = (ImageView) event.getSource();
    rackPlace5 = iv;
    // no other letter is clicked
    if (!letterClicked && iv.getOpacity() != 0) {

      // no destination tile chosen
      if (!tileClicked) {
        clickedLetter = rackPlace5;
        rackPlace5.setOpacity(0.9);
        letterClicked = true;

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        markedTile = clickedTile.getImage();
        tileClicked = false;
        clickedTile.setImage(rackPlace5.getImage());
        choosenTiles.add(clickedTile.getId());
        rackPlace5.setOpacity(0);
      }

    } else if (letterClicked && (rackPlace5 == (ImageView) event.getSource())) {
      letterClicked = false;
      rackPlace5.setOpacity(1);
    }
  }

  /**
   * @param event
   * @throws IOException
   * @author Aaron
   */
  @FXML
  private void ragPlace6clicked(MouseEvent event) throws IOException {
    ImageView iv = (ImageView) event.getSource();
    rackPlace6 = iv;
    // no other letter is clicked
    if (!letterClicked && iv.getOpacity() != 0) {

      // no destination tile chosen
      if (!tileClicked) {
        clickedLetter = rackPlace6;
        rackPlace6.setOpacity(0.9);
        letterClicked = true;

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        markedTile = clickedTile.getImage();
        tileClicked = false;
        clickedTile.setImage(rackPlace6.getImage());
        choosenTiles.add(clickedTile.getId());
        rackPlace6.setOpacity(0);
      }

    } else if (letterClicked && (rackPlace6 == (ImageView) event.getSource())) {
      letterClicked = false;
      rackPlace6.setOpacity(1);
    }
  }

  /**
   * @param event
   * @throws IOException
   * @author Aaron
   */
  @FXML
  private void ragPlace7clicked(MouseEvent event) throws IOException {
    ImageView iv = (ImageView) event.getSource();
    rackPlace7 = iv;
    // no other letter is clicked
    if (!letterClicked && iv.getOpacity() != 0) {

      // no destination tile chosen
      if (!tileClicked) {
        clickedLetter = rackPlace7;
        rackPlace7.setOpacity(0.9);
        letterClicked = true;

        // destination chosen
      } else {
        markedTile = clickedTile.getImage();
        tileClicked = false;
        clickedTile.setImage(rackPlace7.getImage());
        choosenTiles.add(clickedTile.getId());
        rackPlace7.setOpacity(0);
      }

    } else if (letterClicked && (rackPlace7 == (ImageView) event.getSource())) {
      letterClicked = false;
      rackPlace7.setOpacity(1);
    }
  }

  /**
   * @param event
   * @throws IOException
   * @author Aaron
   */
  @FXML
  private void playClicked(MouseEvent event) throws IOException {

    // add choosenTiles to Placed Tiles, so they are not available for the next anymore
    Iterator<String> it = choosenTiles.iterator();
    while (it.hasNext()) {
      String s = it.next();
      unavailableTiles.add(s);
    }

    choosenTiles.clear();

    // reset Opacity on the Rag Board if not null
    if (rackPlace1 != null) {
      rackPlace1.setOpacity(1);
    }
    if (rackPlace2 != null) {
      rackPlace2.setOpacity(1);
    }
    if (rackPlace3 != null) {
      rackPlace3.setOpacity(1);
    }
    if (rackPlace4 != null) {
      rackPlace4.setOpacity(1);
    }
    if (rackPlace5 != null) {
      rackPlace5.setOpacity(1);
    }
    if (rackPlace6 != null) {
      rackPlace6.setOpacity(1);
    }
    if (rackPlace7 != null) {
      rackPlace7.setOpacity(1);
    }

    // delete the ragplaces for new tiles
    rackPlace1 = null;
    rackPlace2 = null;
    rackPlace3 = null;
    rackPlace4 = null;
    rackPlace5 = null;
    rackPlace6 = null;
    rackPlace7 = null;
  }

  private boolean placeTaken(ImageView iv) {
    Iterator<String> it = unavailableTiles.iterator();
    while (it.hasNext()) {
      String s = it.next();
      if (s.equals(iv.getId())) {
        return true;
      } else {
        continue;
      }
    }
    return false;
  }

  private int rowTransformation(String s) {

    // check if column number
    char xCord = s.charAt(0);
    int x = ((int) xCord) - 96;
    return x;
  }

  private int columnTransformation(String s) {

    char yCord = s.charAt(1);
    int y = ((int) yCord) - 48;

    // if length of s is two, there is only the row and column number of length one -> nothing to
    // check
    if (s.length() > 2) {

      // if length is > 2 and the 3. char is a number, the column number has the length of two
      if (47 < ((int) s.charAt(2)) && ((int) s.charAt(2)) < 58) {
        char yCord2 = s.charAt(2);
        int y2 = ((int) yCord2) - 48;
        y *= 10;
        y += y2;
      }
    }
    return y;
  }
}
