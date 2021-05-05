package com.scrab5.ui;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import com.scrab5.core.game.*;
import com.scrab5.core.player.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/*
 * @author apilgrim
 */

public class SingleplayerController implements Initializable {

  /**
   * boolean which is set when a tile from the Board/ a letter from the rack is clicked (selected)
   */
  private boolean tileClicked = false, letterClicked = false;

  /**
   * a copy of the tile from the Board/ a letter from the rack which are currently selected
   */
  private ImageView clickedTile;
  private ImageView clickedLetter;

  private Image markedTile;
  
  public ArrayList<Player> playersList = new ArrayList<>();
  
  

  /**
   * @author apilgrim
   * 
   *         first representation of the rack places which later will be connected to the game core
   *         and linked to the bag of tiles
   */
  private ImageView rackPlace1;
  private ImageView rackPlace2;
  private ImageView rackPlace3;
  private ImageView rackPlace4;
  private ImageView rackPlace5;
  private ImageView rackPlace6;
  private ImageView rackPlace7;
  
  Player owner;

  private ArrayList<String> unavailableTiles = new ArrayList<String>();
  private ArrayList<String> choosenTiles = new ArrayList<String>();
  
  private ArrayList<TextField> players = new ArrayList<TextField>();
  

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub

  }
  
  public void test() {
    playersList.add(new Player("peter"));
    playersList.add(new Player("karl"));
    playersList.add(new Player("marta"));
    playersList.add(new Player("Aaron"));
  }
  
  private void initPlayers() {
    Iterator<Player> it = playersList.iterator();
    Iterator<TextField> pIt = players.iterator();
    while (it.hasNext()) {
      players.add(new TextField());
      pIt.next().setText(it.next().getName());
      pIt.next().localToScene(50, 700);
      pIt.next().setFocusTraversable(false);
    }
  }


  /**
   * 
   * @author apilgirm
   * @param event - MouseEvent
   * @throws IOException
   * 
   *         this method is called when a tile on the board is clicked. It checks 1. if the field
   *         already contained a letter which than is removed (backToRack) 2. else, if no other tile
   *         is clicked and the place isn't taken, then it is marked thru the opacity or if a letter
   *         is clicked as well, the Letter is placed. 3. last if another tile has been clicked
   *         before it is unclicked an reseted from the clickedTile
   * 
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
      backToRack(iv);
    } else {
      // check if no tiles has been clicked
      if (!tileClicked && !placeTaken(iv)) {
        clickedTile = (ImageView) event.getSource();
        if (!letterClicked) {
          lighten(event);
          tileClicked = true;
        } else if (!iv.getImage().getUrl().contains("letter_Tiles")) {
          placeLetter(clickedLetter);
        }
      } else if (tileClicked && (clickedTile == (ImageView) event.getSource())) {
        darken(event);
        tileClicked = false;
        clickedTile = null;
      }
    }
  }

  /**
   * @author apilgirm
   * @param event - MouseEvent
   * 
   *        method to set the opacity and let it looks like the field/ button is entered
   */
  @FXML
  private void lighten(MouseEvent event) {
    ImageView iv = ((ImageView) event.getSource());
    iv.setOpacity(1);
  }

  /**
   * @author apilgirm
   * 
   *         method to set the opacity on zero and let it looks like the field/ button is excited
   *         but checks first that it isnt a letter already placed or the marked field with the
   *         square
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
   * @author apilgirm
   * @param event
   * @throws IOException
   * 
   *         method which is called when the rack Place One is clicked and checks 1. if another
   *         letter is clicked/marked 2. if not, it checks if a destination tile is already marked
   *         on the field and therefore is replaced with this letter, otherwise it is marked and is
   *         locked in the clicked Letter attribute. Or 3. least if it was already the marked letter
   *         in the rack it is unmarked and unclicked
   */
  @FXML
  private void ragPlace1clicked(MouseEvent event) throws IOException {
    rackPlace1 = (ImageView) event.getSource();
    // no other letter is clicked
    if (!letterClicked && rackPlace1.getOpacity() != 0) {

      clickedLetter = rackPlace1;
      letterClicked = true;

      // no destination tile chosen
      if (!tileClicked) {
        rackPlace1.setOpacity(0.9);

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        placeLetter(clickedLetter);
        rackPlace1.setOpacity(0);
      }

    } else if (letterClicked && rackPlace1.getOpacity() == 0.9) {
      unclickLetter(rackPlace1);
    }
  }

  /**
   * @author apilgirm
   * @param event
   * @throws IOException
   * 
   *         method to check different options to handle the clicked Letter in rack Place 2 like in
   *         rackPlace1Clicked
   */
  @FXML
  private void ragPlace2clicked(MouseEvent event) throws IOException {
    rackPlace2 = (ImageView) event.getSource();
    // no other letter is clicked
    if (!letterClicked && rackPlace2.getOpacity() != 0) {

      clickedLetter = rackPlace2;
      letterClicked = true;

      // no destination tile chosen
      if (!tileClicked) {
        rackPlace2.setOpacity(0.9);

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        placeLetter(clickedLetter);
        rackPlace2.setOpacity(0);
      }

    } else if (letterClicked && rackPlace2.getOpacity() == 0.9) {
      unclickLetter(rackPlace2);
    }
  }

  /**
   * @author apilgirm
   * @param event
   * @throws IOException
   * 
   *         method to check different options to handle the clicked Letter in rack Place 3 like in
   *         rackPlace1Clicked
   */
  @FXML
  private void ragPlace3clicked(MouseEvent event) throws IOException {
    rackPlace3 = (ImageView) event.getSource();
    // no other letter is clicked
    if (!letterClicked && rackPlace3.getOpacity() != 0) {

      clickedLetter = rackPlace3;
      letterClicked = true;

      // no destination tile chosen
      if (!tileClicked) {
        rackPlace3.setOpacity(0.9);

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        placeLetter(clickedLetter);
        rackPlace3.setOpacity(0);
      }

    } else if (letterClicked && rackPlace3.getOpacity() == 0.9) {
      unclickLetter(rackPlace3);
    }
  }

  /**
   * @author apilgirm
   * @param event
   * @throws IOException
   * 
   *         method to check different options to handle the clicked Letter in rack Place 4 like in
   *         rackPlace1Clicked
   */
  @FXML
  private void ragPlace4clicked(MouseEvent event) throws IOException {
    rackPlace4 = (ImageView) event.getSource();
    // no other letter is clicked
    if (!letterClicked && rackPlace4.getOpacity() != 0) {

      clickedLetter = rackPlace4;
      letterClicked = true;

      // no destination tile chosen
      if (!tileClicked) {
        rackPlace4.setOpacity(0.9);

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        placeLetter(clickedLetter);
        rackPlace4.setOpacity(0);
      }

    } else if (letterClicked && rackPlace4.getOpacity() == 0.9) {
      unclickLetter(rackPlace4);
    }
  }

  /**
   * @author apilgirm
   * @param event
   * @throws IOException
   * 
   *         method to check different options to handle the clicked Letter in rack Place 5 like in
   *         rackPlace1Clicked
   */
  @FXML
  private void ragPlace5clicked(MouseEvent event) throws IOException {
    rackPlace5 = (ImageView) event.getSource();
    // no other letter is clicked
    if (!letterClicked && rackPlace5.getOpacity() != 0) {

      clickedLetter = rackPlace5;
      letterClicked = true;

      // no destination tile chosen
      if (!tileClicked) {
        rackPlace5.setOpacity(0.9);

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        placeLetter(clickedLetter);
        rackPlace5.setOpacity(0);
      }

    } else if (letterClicked && rackPlace5.getOpacity() == 0.9) {
      unclickLetter(rackPlace5);
    }
  }

  /**
   * @author apilgirm
   * @param event
   * @throws IOException
   * 
   *         method to check different options to handle the clicked Letter in rack Place 6 like in
   *         rackPlace1Clicked
   */
  @FXML
  private void ragPlace6clicked(MouseEvent event) throws IOException {
    rackPlace6 = (ImageView) event.getSource();
    // no other letter is clicked
    if (!letterClicked && rackPlace6.getOpacity() != 0) {

      clickedLetter = rackPlace6;
      letterClicked = true;

      // no destination tile chosen
      if (!tileClicked) {
        rackPlace6.setOpacity(0.9);

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        placeLetter(clickedLetter);
        rackPlace6.setOpacity(0);
      }

    } else if (letterClicked && rackPlace6.getOpacity() == 0.9) {
      unclickLetter(rackPlace6);
    }
  }

  /**
   * @author apilgirm
   * @param event
   * @throws IOException
   * 
   *         method to check different options to handle the clicked Letter in rack Place 7 like in
   *         rackPlace1Clicked
   * @author Aaron
   */
  @FXML
  private void ragPlace7clicked(MouseEvent event) throws IOException {
    rackPlace7 = (ImageView) event.getSource();
    // no other letter is clicked
    if (!letterClicked && rackPlace7.getOpacity() != 0) {

      clickedLetter = rackPlace7;
      letterClicked = true;

      // if no destination tile is chosen the letter is highlighted and the letterClicked is set on
      // true
      if (!tileClicked) {
        rackPlace7.setOpacity(0.9);

        // desination chosen
      } else if (!placeTaken(clickedTile)) {
        placeLetter(clickedLetter);
        rackPlace7.setOpacity(0);
      }

    } else if (letterClicked && rackPlace7.getOpacity() == 0.9) {
      unclickLetter(rackPlace7);
    }
  }

  /**
   * @author apilgirm
   * @param event
   * @throws IOException
   * 
   *         method to refill rack where letters have been placed and to permanently lock
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

  /**
   * @author apilgrim
   * @param iv - ImageView
   * 
   *        reset the opacity of the clickedLetter in the Rack and resets him from being clicked
   */

  private void unclickLetter(ImageView rackPlace) {
    letterClicked = false;
    clickedLetter = null;
    rackPlace.setOpacity(1);
  }

  /**
   * @author apilgrim
   * @param iv - ImageView
   * 
   *        This method is called when a destination Tile is clicked on the GameBoard which already
   *        contains a letter tile (is chosen but not permanently logged) and brings back the letter
   *        to the rack. It changes the Image on the Board back to the marked Tile (black square)
   *        and brings the Letter from the Board back to the rack thru the opacity and resets the
   *        clicked attributes (Letter/ Tile) for source and destination
   */
  private void backToRack(ImageView iv) {

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

  }

  /**
   * @author apilgrim
   * @param iv - ImageView
   * 
   *        This method is called when a destination Tile is clicked on the GameBoard and a Letter
   *        Tile is selected. It changes the Image on the Board and "deletes" the Letter from the
   *        Board thru the opacity and resets the boolean clicked attributes (Letter/ Tile) for
   *        source and destination
   */
  private void placeLetter(ImageView iv) {

    markedTile = clickedTile.getImage();
    clickedTile.setImage(iv.getImage());
    choosenTiles.add(clickedTile.getId());
    clickedLetter.setOpacity(0);
    letterClicked = false;
    tileClicked = false;
  }

  /**
   * @author apilgrim
   * @param iv - ImageView
   * @return boolean
   * 
   *         This method checks if a tile is already permanently taken by another Letter. Only for
   *         demonstration/ test purpose. Later checked in the core.game with @is_spot_free
   */
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


  /**
   * @author apilgrim
   * @param placeID - String representation of the coordinate from every tile on the board read from
   *        the fxml document as ID
   * @return x - Integer representation of the x coordinate for the tile, placed on the Gameboard
   */
  private int rowTransformation(String placeID) {

    // check if column number
    char xCord = placeID.charAt(0);
    int x = ((int) xCord) - 97;
    return x;
  }


  /**
   * @author apilgrim
   * @param placeID - String representation of the coordinate from every tile on the board read from
   *        the fxml document as ID
   * @return y - Integer representation of the y coordinate for the tile, placed on the Gameboard
   */
  private int columnTransformation(String placeID) {

    char yCord = placeID.charAt(1);
    int y = ((int) yCord) - 48;

    // if length of the String s is two, there is only the row and column number of length one ->
    // nothing to
    // check
    if (placeID.length() > 2) {

      // if length is > 2 and the 3. char is a number, the column number has the length of two
      if (47 < ((int) placeID.charAt(2)) && ((int) placeID.charAt(2)) < 58) {
        char yCord2 = placeID.charAt(2);
        int y2 = ((int) yCord2) - 48;
        y *= 10;
        y += y2;
      }
    }
    return y;
  }
  
  
  private void setNewTile(String letter, String points) {
    Image i = new Image(this.getClass().getResource("/com/scrab5/ui/images/Singleplayer.png").toString());
    System.out.println("Done");
  }
}
