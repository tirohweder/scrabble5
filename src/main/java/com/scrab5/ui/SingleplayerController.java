package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import com.scrab5.core.game.GameBoard;
import com.scrab5.core.game.GameSession;
import com.scrab5.core.player.Player;
import com.scrab5.util.database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
  private Label clickedLabel;

  private Image markedTile;
  private static final double LABEL_X_CORD = 30.0;
  private static final double LABEL_Y_CORD = 23.0;
  private static final double LABEL_X_CORD_BACK = 46.0;
  private static final double LABEL_Y_CORD_BACK = 44.0;

  public ArrayList<Player> playersList = new ArrayList<>();



  /**
   * @author apilgrim
   * 
   *         first representation of the rack places which later will be connected to the game core
   *         and linked to the bag of tiles
   */
  @FXML
  private ImageView rackPlace1;
  @FXML
  private ImageView rackPlace2;
  @FXML
  private ImageView rackPlace3;
  @FXML
  private ImageView rackPlace4;
  @FXML
  private ImageView rackPlace5;
  @FXML
  private ImageView rackPlace6;
  @FXML
  private ImageView rackPlace7;

  @FXML
  private ImageView playerProfile1;
  @FXML
  private ImageView playerProfile2;
  @FXML
  private ImageView playerProfile3;
  @FXML
  private ImageView playerProfile4;

  @FXML
  private ImageView exchangeScreen;



  @FXML
  private Label pointsRack1;
  @FXML
  private Label pointsRack2;
  @FXML
  private Label pointsRack3;
  @FXML
  private Label pointsRack4;
  @FXML
  private Label pointsRack5;
  @FXML
  private Label pointsRack6;
  @FXML
  private Label pointsRack7;

  @FXML
  private Label player1;
  @FXML
  private Label player2;
  @FXML
  private Label player3;
  @FXML
  private Label player4;

  @FXML
  private Label pointsPlayer1;
  @FXML
  private Label pointsPlayer2;
  @FXML
  private Label pointsPlayer3;
  @FXML
  private Label pointsPLayer4;



  private ArrayList<String> unavailableTiles = new ArrayList<String>();
  private ArrayList<String> choosenTiles = new ArrayList<String>();

  private ArrayList<Player> players = GameSession.getListOfPlayers();


  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    player1.setText("Aaron");
    player2.setText("Peter");
    player2.setOpacity(1);
    player3.setText("Peter");
    player3.setOpacity(1);
    player4.setText("Peter");
    player4.setOpacity(1);

    initRack();


  }

  public void test() {
    playersList.add(new Player("peter"));
    playersList.add(new Player("karl"));
    playersList.add(new Player("marta"));
    playersList.add(new Player("Aaron"));
  }

  private void initPlayers() {
    Iterator<Player> it = players.iterator();

    if (it.hasNext()) {
      player1.setText(it.next().getName());
      player1.setOpacity(1);
      pointsPlayer1.setText("");
      it.next();
      if (it.hasNext()) {

      }
    }
  }

  private void initRack() {
    // String currentUser = Data.getCurrentUser();
    // Rack myRack = null;
    // int rackPlace;
    //
    // Iterator<Player> it = players.iterator();
    // while (it.hasNext()) {
    // String s = it.next().getName();
    // if (s.equals(currentUser)) {
    // myRack = it.next().getRack();
    // } else {
    // continue;
    // }
    // }
    //
    // for (int i = 0; i < 7; i++) {
    // rackPlace = myRack.getTileAt(i).getRackPlace();
    //
    // switch (rackPlace) {
    // case 1:
    // setNewTile(rackPlace1, pointsRack1, myRack.getTileAt(i).getLetter(),
    // myRack.getTileAt(i).getValue());
    // break;
    // case 2:
    // setNewTile(rackPlace2, pointsRack1, myRack.getTileAt(i).getLetter(),
    // myRack.getTileAt(i).getValue());
    // break;
    // case 3:
    // setNewTile(rackPlace3, pointsRack1, myRack.getTileAt(i).getLetter(),
    // myRack.getTileAt(i).getValue());
    // break;
    // case 4:
    // setNewTile(rackPlace4, pointsRack1, myRack.getTileAt(i).getLetter(),
    // myRack.getTileAt(i).getValue());
    // break;
    // case 5:
    // setNewTile(rackPlace5, pointsRack1, myRack.getTileAt(i).getLetter(),
    // myRack.getTileAt(i).getValue());
    // break;
    // case 6:
    // setNewTile(rackPlace6, pointsRack1, myRack.getTileAt(i).getLetter(),
    // myRack.getTileAt(i).getValue());
    // break;
    // case 7:
    // setNewTile(rackPlace7, pointsRack1, myRack.getTileAt(i).getLetter(),
    // myRack.getTileAt(i).getValue());
    // break;
    // default:
    // break;
    // }
    // }
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
    if (iv.getImage().getUrl().contains("letter_Images") && !placeTaken(iv)) {
      backToRack(iv);
    } else {
      // check if no tiles has been clicked
      if (!tileClicked && !placeTaken(iv)) {
        clickedTile = (ImageView) event.getSource();
        if (!letterClicked) {
          lighten(event);
          tileClicked = true;
        } else if (!iv.getImage().getUrl().contains("letter_Images")) {
          placeLetter(clickedLetter, clickedLabel);
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
    if (clickedTile != iv && !iv.getImage().getUrl().contains("letter_Images")) {
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
  private void rackPlace1clicked(MouseEvent event) throws IOException {
    rackPlace1 = (ImageView) event.getSource();
    // no other letter is clicked
    if (!letterClicked && rackPlace1.getOpacity() != 0) {

      clickedLetter = rackPlace1;
      letterClicked = true;
      clickedLabel = pointsRack1;

      // no destination tile chosen
      if (!tileClicked) {
        rackPlace1.setOpacity(0.9);

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        placeLetter(clickedLetter, pointsRack1);
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
  private void rackPlace2clicked(MouseEvent event) throws IOException {
    rackPlace2 = (ImageView) event.getSource();
    // no other letter is clicked
    if (!letterClicked && rackPlace2.getOpacity() != 0) {

      clickedLetter = rackPlace2;
      letterClicked = true;
      clickedLabel = pointsRack2;

      // no destination tile chosen
      if (!tileClicked) {
        rackPlace2.setOpacity(0.9);

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        placeLetter(clickedLetter, pointsRack2);
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
  private void rackPlace3clicked(MouseEvent event) throws IOException {
    rackPlace3 = (ImageView) event.getSource();
    // no other letter is clicked
    if (!letterClicked && rackPlace3.getOpacity() != 0) {

      clickedLetter = rackPlace3;
      letterClicked = true;
      clickedLabel = pointsRack3;

      // no destination tile chosen
      if (!tileClicked) {
        rackPlace3.setOpacity(0.9);

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        placeLetter(clickedLetter, pointsRack3);
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
  private void rackPlace4clicked(MouseEvent event) throws IOException {
    rackPlace4 = (ImageView) event.getSource();
    // no other letter is clicked
    if (!letterClicked && rackPlace4.getOpacity() != 0) {

      clickedLetter = rackPlace4;
      letterClicked = true;
      clickedLabel = pointsRack4;

      // no destination tile chosen
      if (!tileClicked) {
        rackPlace4.setOpacity(0.9);

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        placeLetter(clickedLetter, pointsRack4);
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
  private void rackPlace5clicked(MouseEvent event) throws IOException {
    rackPlace5 = (ImageView) event.getSource();
    // no other letter is clicked
    if (!letterClicked && rackPlace5.getOpacity() != 0) {

      clickedLetter = rackPlace5;
      letterClicked = true;
      clickedLabel = pointsRack5;

      // no destination tile chosen
      if (!tileClicked) {
        rackPlace5.setOpacity(0.9);

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        placeLetter(clickedLetter, pointsRack5);
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
  private void rackPlace6clicked(MouseEvent event) throws IOException {
    rackPlace6 = (ImageView) event.getSource();
    // no other letter is clicked
    if (!letterClicked && rackPlace6.getOpacity() != 0) {

      clickedLetter = rackPlace6;
      letterClicked = true;
      clickedLabel = pointsRack6;

      // no destination tile chosen
      if (!tileClicked) {
        rackPlace6.setOpacity(0.9);

        // destination chosen
      } else if (!placeTaken(clickedTile)) {
        placeLetter(clickedLetter, pointsRack6);
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
  private void rackPlace7clicked(MouseEvent event) throws IOException {
    rackPlace7 = (ImageView) event.getSource();
    // no other letter is clicked
    if (!letterClicked && rackPlace7.getOpacity() != 0) {

      clickedLetter = rackPlace7;
      letterClicked = true;
      clickedLabel = pointsRack7;

      // if no destination tile is chosen the letter is highlighted and the letterClicked is set on
      // true
      if (!tileClicked) {
        rackPlace7.setOpacity(0.9);

        // desination chosen
      } else if (!placeTaken(clickedTile)) {
        placeLetter(clickedLetter, pointsRack7);
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
    ImageView iv = (ImageView) event.getSource();
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
      pointsRack1.setLayoutX(rackPlace1.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack1.setLayoutY(rackPlace1.getLayoutY() + LABEL_Y_CORD_BACK);
      iv.setImage(markedTile);
    } else if (rackPlace2 != null
        && iv.getImage().getUrl().equalsIgnoreCase(rackPlace2.getImage().getUrl())
        && rackPlace2.getOpacity() == 0) {
      rackPlace2.setOpacity(1);
      pointsRack2.setLayoutX(rackPlace2.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack2.setLayoutY(rackPlace2.getLayoutY() + LABEL_Y_CORD_BACK);
      iv.setImage(markedTile);
    } else if (rackPlace3 != null
        && iv.getImage().getUrl().equalsIgnoreCase(rackPlace3.getImage().getUrl())
        && rackPlace3.getOpacity() == 0) {
      rackPlace3.setOpacity(1);
      pointsRack3.setLayoutX(rackPlace3.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack3.setLayoutY(rackPlace3.getLayoutY() + LABEL_Y_CORD_BACK);
      iv.setImage(markedTile);
    } else if (rackPlace4 != null
        && iv.getImage().getUrl().equalsIgnoreCase(rackPlace4.getImage().getUrl())
        && rackPlace4.getOpacity() == 0) {
      rackPlace4.setOpacity(1);
      pointsRack4.setLayoutX(rackPlace4.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack4.setLayoutY(rackPlace4.getLayoutY() + LABEL_Y_CORD_BACK);
      iv.setImage(markedTile);
    } else if (rackPlace5 != null
        && iv.getImage().getUrl().equalsIgnoreCase(rackPlace5.getImage().getUrl())
        && rackPlace5.getOpacity() == 0) {
      rackPlace5.setOpacity(1);
      pointsRack5.setLayoutX(rackPlace5.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack5.setLayoutY(rackPlace5.getLayoutY() + LABEL_Y_CORD_BACK);
      iv.setImage(markedTile);
    } else if (rackPlace6 != null
        && iv.getImage().getUrl().equalsIgnoreCase(rackPlace6.getImage().getUrl())
        && rackPlace6.getOpacity() == 0) {
      rackPlace6.setOpacity(1);
      pointsRack6.setLayoutX(rackPlace6.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack6.setLayoutY(rackPlace6.getLayoutY() + LABEL_Y_CORD_BACK);
      iv.setImage(markedTile);
    } else if (rackPlace7 != null
        && iv.getImage().getUrl().equalsIgnoreCase(rackPlace7.getImage().getUrl())
        && rackPlace7.getOpacity() == 0) {
      rackPlace7.setOpacity(1);
      pointsRack7.setLayoutX(rackPlace7.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack7.setLayoutY(rackPlace7.getLayoutY() + LABEL_Y_CORD_BACK);
      iv.setImage(markedTile);
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
  private void placeLetter(ImageView iv, Label l) {

    markedTile = clickedTile.getImage();
    clickedTile.setImage(iv.getImage());
    l.setLayoutX(clickedTile.getLayoutX() + LABEL_X_CORD);
    l.setLayoutY(clickedTile.getLayoutY() + LABEL_Y_CORD);
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
    return y - 1;
  }

  @FXML
  private void shuffleClicked(MouseEvent event) {
    ArrayList<Player> players = GameSession.getListOfPlayers();
    String currentUser = Data.getCurrentUser();

    Iterator<Player> it = players.iterator();
    while (it.hasNext()) {
      String s = it.next().getName();
      if (s.equals(currentUser)) {
        it.next().getRack().shuffleRack();
        initRack();
      } else {
        continue;
      }
    }

  }

  @FXML
  private void exchangeClicked(MouseEvent event) {

    exchangeScreen.setFitHeight(768);
    exchangeScreen.setFitWidth(1360);
    exchangeScreen.setLayoutX(0);
    exchangeScreen.setLayoutY(0);
    Image exchange = new Image(
        this.getClass().getResource("/com/scrab5/ui/board_Images/exchangeTiles.png").toString());
    exchangeScreen.setImage(exchange);
  }

  private void setNewTile(ImageView rackPlace, Label point, String letter, int points) {
    Image letterImage = new Image(this.getClass()
        .getResource("/com/scrab5/ui/letter_Images/tile" + letter.toUpperCase() + ".png")
        .toString());
    rackPlace.setImage(letterImage);
    rackPlace.setOpacity(1);
    point.setText(Integer.toString(points));
    point.setOpacity(1);
  }

  @FXML
  private void closeGame(MouseEvent event) {
    Database.disconnect();
    Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();

    if (Data.getHostedServer() != null)
      Data.getHostedServer().shutDownServer();
    if (Data.getPlayerClient() != null)
      Data.getPlayerClient().disconnectFromServer();

    s.close();
  }
}

