package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import com.scrab5.core.game.Rack;
import com.scrab5.core.player.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/*
 * @author apilgrim
 */

public abstract class InGameController implements Initializable {

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


  String currentUser = Data.getCurrentUser();
  String currentPlayer = Data.getGameSession().getCurrentPlayer().getName();


  /**
   * @author apilgrim
   *         <p>
   *         first representation of the rack places which later will be connected to the game core
   *         and linked to the bag of tiles
   */
  @FXML
  ImageView rackPlace1;
  @FXML
  ImageView rackPlace2;
  @FXML
  ImageView rackPlace3;
  @FXML
  ImageView rackPlace4;
  @FXML
  ImageView rackPlace5;
  @FXML
  ImageView rackPlace6;
  @FXML
  ImageView rackPlace7;

  @FXML
  ImageView playerProfile1Passive;
  @FXML
  ImageView playerProfile2Passive;
  @FXML
  ImageView playerProfile3Passive;
  @FXML
  ImageView playerProfile4Passive;

  @FXML
  private ImageView playerProfile2Active;
  @FXML
  private ImageView playerProfile3Active;
  @FXML
  private ImageView playerProfile4Active;

  @FXML
  private AnchorPane mainPane;


  @FXML
  Label pointsRack1;
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

  private int rackClicked;

  Boolean[] rackChanges = new Boolean[7];


  private ArrayList<String> choosenTiles = new ArrayList<String>();

  private ArrayList<Player> players = Data.getGameSession().getListOfPlayers();
  private int playerAmount = players.size();

  private boolean exchangeable = false;


  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    initRack();
    initPlayers();
    initGameboard();

  }

  protected void initPlayers() {

    if (0 < playerAmount) {
      player1.setText(players.get(0).getName());
      if (players.get(0).getName().equalsIgnoreCase(currentPlayer)) {
        playerProfile1Passive.setOpacity(0);
      }
    }
    if (1 < playerAmount) {
      player2.setText(players.get(1).getName());
      player2.setOpacity(1);
      playerProfile2Active.setOpacity(1);
      playerProfile2Passive.setOpacity(1);
      if (players.get(1).getName().equalsIgnoreCase(currentPlayer)) {
        playerProfile1Passive.setOpacity(0);
      }
    }
    if (2 < playerAmount) {
      player3.setText(players.get(2).getName());
      player3.setOpacity(1);
      playerProfile3Active.setOpacity(1);
      playerProfile3Passive.setOpacity(1);
      if (players.get(2).getName().equalsIgnoreCase(currentPlayer)) {
        playerProfile1Passive.setOpacity(0);
      }
    }
    if (3 < playerAmount) {
      player4.setText(players.get(3).getName());
      player4.setOpacity(1);
      playerProfile4Active.setOpacity(1);
      playerProfile4Passive.setOpacity(1);
      if (players.get(3).getName().equalsIgnoreCase(currentPlayer)) {
        playerProfile1Passive.setOpacity(0);
      }
    }

    nextPlayer();

  }

  protected void initRack() {
    String currentUser = Data.getCurrentUser();
    Rack myRack = null;
    int rackPlace;

    Iterator<Player> it = Data.getGameSession().getListOfPlayers().iterator();
    while (it.hasNext()) {
      Player p = it.next();
      String s = p.getName();
      System.out.println(s);
      System.out.println(currentUser);
      System.out.println(s.equalsIgnoreCase(currentUser));
      if (s.equalsIgnoreCase(currentUser)) {
        p.getRack().fill(Data.getGameSession().getBag());
        myRack = p.getRack();
        System.out.println(myRack.getTileAt(0).getLetter());
      } else {
        continue;
      }
    }

    for (int i = 0; i < 7; i++) {
      if (!myRack.getTileAt(i).getLetter().equals("space")) {
        switch (i) {

          case 0:
            setNewTile(rackPlace1, pointsRack1, myRack.getTileAt(i).getLetter(),
                myRack.getTileAt(i).getValue());
            break;
          case 1:
            setNewTile(rackPlace2, pointsRack2, myRack.getTileAt(i).getLetter(),
                myRack.getTileAt(i).getValue());
            break;
          case 2:
            setNewTile(rackPlace3, pointsRack3, myRack.getTileAt(i).getLetter(),
                myRack.getTileAt(i).getValue());
            break;
          case 3:
            setNewTile(rackPlace4, pointsRack4, myRack.getTileAt(i).getLetter(),
                myRack.getTileAt(i).getValue());
            break;
          case 4:
            setNewTile(rackPlace5, pointsRack5, myRack.getTileAt(i).getLetter(),
                myRack.getTileAt(i).getValue());
            break;
          case 5:
            setNewTile(rackPlace6, pointsRack6, myRack.getTileAt(i).getLetter(),
                myRack.getTileAt(i).getValue());
            break;
          case 6:
            setNewTile(rackPlace7, pointsRack7, myRack.getTileAt(i).getLetter(),
                myRack.getTileAt(i).getValue());
            break;
          default:
            break;
        }
      }
    }

  }

  protected void initGameboard() {


    double layoutX = 263.0, layoutY = 53.0;

    

     for(int i = 0; i < 15; i++) {
     for(int j = 0; j < 15; j++) {
       if(Data.getGameSession().getGameBoard().getTile(i, j) != null) {
         System.out.println("OIUHFOÖIEROÖI2");
         ImageView letterImage = new ImageView();
         String letter = Data.getGameSession().getGameBoard().getTile(i, j).getLetter();
         String points = Integer.toString(Data.getGameSession().getGameBoard().getTile(i,
         j).getValue());
         letterImage.setFitWidth(42.0);
         letterImage.setFitHeight(42.0);
         if(letter.equalsIgnoreCase("*")) {
         letterImage.setImage(new Image("/com/scrab5/ui/letter_Images/placeHolder.png"));
         }else {
         letterImage.setImage(new Image("/com/scrab5/ui/letter_Images/tile"+letter.toUpperCase() +
         ".png"));
         }
         letterImage.setLayoutX(layoutX);
         letterImage.setLayoutY(layoutY);
         Label point = new Label(points);
        
         point.setLayoutX(letterImage.getLayoutX() + LABEL_X_CORD);
         point.setLayoutY(letterImage.getLayoutY() + LABEL_Y_CORD);
         
         mainPane.getChildren().add(mainPane.getChildren().size(), letterImage);
         mainPane.getChildren().add(mainPane.getChildren().size(), point);
         }
     layoutX += 42.0;
     }
     layoutY += 42.0;
     layoutX = 263.0;
     }
    
  }

  /**
   * @param event - MouseEvent
   * @throws IOException this method is called when a tile on the board is clicked. It checks 1. if
   *         the field already contained a letter which than is removed (backToRack) 2. else, if no
   *         other tile is clicked and the place isn't taken, then it is marked thru the opacity or
   *         if a letter is clicked as well, the Letter is placed. 3. last if another tile has been
   *         clicked before it is unclicked an reseted from the clickedTile
   * @author apilgirm
   */
  @FXML
  private void fieldClicked(MouseEvent event) throws IOException {

    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {

      ImageView iv = ((ImageView) event.getSource());

      // check if the field clicked is a letter to remove
      if (isChoosen(iv) && exchangeable && iv.getImage().getUrl().contains("letter_Images")) {
        System.out.println("HUHFHHAKSKANFÖO");
        backToRack(iv);
      } else {
        // check if no tiles has been clicked
        if (!tileClicked) {
          clickedTile = (ImageView) event.getSource();
          if (!letterClicked) {
            lighten(event);
            tileClicked = true;
          } else if (Data.getGameSession().getGameBoard().placeTile(
              Data.getGameSession().getCurrentPlayer().getRack().getTileAt(rackClicked),
              rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))
              && rackClicked < 7) {
            placeLetter(clickedLetter, clickedLabel);
            choosenTiles.add(iv.getId());
            exchangeable = true;
          }
        } else if (tileClicked && (clickedTile == (ImageView) event.getSource())) {
          darken(event);
          tileClicked = false;
          clickedTile = null;
        }
      }
    } else {
      PopUpMessage pum =
          new PopUpMessage("Sorry, you're not the current Player", PopUpMessageType.NOTIFICATION);
      pum.show();
    }
  }

  /**
   * @param event - MouseEvent
   *        <p>
   *        method to set the opacity and let it looks like the field/ button is entered
   * @author apilgirm
   */
  @FXML
  private void lighten(MouseEvent event) {
    ImageView iv = ((ImageView) event.getSource());
    iv.setOpacity(1);
  }

  /**
   * @author apilgirm
   *         <p>
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
   * @param event
   * @throws IOException method which is called when the rack Place One is clicked and checks 1. if
   *         another letter is clicked/marked 2. if not, it checks if a destination tile is already
   *         marked on the field and therefore is replaced with this letter, otherwise it is marked
   *         and is locked in the clicked Letter attribute. Or 3. least if it was already the marked
   *         letter in the rack it is unmarked and unclicked
   * @author apilgirm
   */
  @FXML
  private void rackPlace1clicked(MouseEvent event) throws IOException {
    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {
      rackPlace1 = (ImageView) event.getSource();
      // no other letter is clicked
      if (!letterClicked && rackPlace1.getOpacity() != 0) {

        clickedLetter = rackPlace1;
        rackClicked = 0;
        letterClicked = true;
        clickedLabel = pointsRack1;

        // no destination tile chosen
        if (!tileClicked) {
          rackPlace1.setOpacity(0.8);

          // destination chosen
        } else if (Data.getGameSession().getGameBoard().placeTile(
            Data.getGameSession().getCurrentPlayer().getRack().getTileAt(0),
            rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))) {
          placeLetter(clickedLetter, pointsRack1);
          rackPlace1.setOpacity(0);
          choosenTiles.add(clickedTile.getId());
          clickedTile = null;
          tileClicked = false;
          exchangeable = true;
        }

      } else {
        if (clickedLetter == rackPlace1) {
          unclickLetter(rackPlace1);
        } else {
          switchClickedLetter(rackPlace1);
        }
      }
    } else {
      PopUpMessage pum =
          new PopUpMessage("Sorry, you're not the current Player", PopUpMessageType.NOTIFICATION);
      pum.show();
    }


  }

  /**
   * @param event
   * @throws IOException method to check different options to handle the clicked Letter in rack
   *         Place 2 like in rackPlace1Clicked
   * @author apilgirm
   */
  @FXML
  private void rackPlace2clicked(MouseEvent event) throws IOException {
    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {
      rackPlace2 = (ImageView) event.getSource();
      // no other letter is clicked
      if (!letterClicked && rackPlace2.getOpacity() != 0) {

        clickedLetter = rackPlace2;
        letterClicked = true;
        rackClicked = 1;
        clickedLabel = pointsRack2;

        // no destination tile chosen
        if (!tileClicked) {
          rackPlace2.setOpacity(0.8);

          // destination chosen
        } else if (Data.getGameSession().getGameBoard().placeTile(
            Data.getGameSession().getCurrentPlayer().getRack().getTileAt(1),
            rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))) {
          placeLetter(clickedLetter, pointsRack2);
          rackPlace2.setOpacity(0);
          choosenTiles.add(clickedTile.getId());
          clickedTile = null;
          tileClicked = false;
          exchangeable = true;
        }

      } else {
        if (clickedLetter == rackPlace2) {
          unclickLetter(rackPlace2);
        } else {
          switchClickedLetter(rackPlace2);
        }
      }
    } else {
      PopUpMessage pum =
          new PopUpMessage("Sorry, you're not the current Player", PopUpMessageType.NOTIFICATION);
      pum.show();
    }


  }

  /**
   * @param event
   * @throws IOException method to check different options to handle the clicked Letter in rack
   *         Place 3 like in rackPlace1Clicked
   * @author apilgirm
   */
  @FXML
  private void rackPlace3clicked(MouseEvent event) throws IOException {
    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {
      rackPlace3 = (ImageView) event.getSource();
      // no other letter is clicked
      if (!letterClicked && rackPlace3.getOpacity() != 0) {

        clickedLetter = rackPlace3;
        letterClicked = true;
        rackClicked = 2;
        clickedLabel = pointsRack3;

        // no destination tile chosen
        if (!tileClicked) {
          rackPlace3.setOpacity(0.8);

          // destination chosen
        } else if (Data.getGameSession().getGameBoard().placeTile(
            Data.getGameSession().getCurrentPlayer().getRack().getTileAt(2),
            rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))) {
          placeLetter(clickedLetter, pointsRack3);
          rackPlace3.setOpacity(0);
          choosenTiles.add(clickedTile.getId());
          clickedTile = null;
          tileClicked = false;
          exchangeable = true;
        }
      } else {
        if (clickedLetter == rackPlace3) {
          unclickLetter(rackPlace3);
        } else {
          switchClickedLetter(rackPlace3);
        }
      }
    } else {
      PopUpMessage pum =
          new PopUpMessage("Sorry, you're not the current Player", PopUpMessageType.NOTIFICATION);
      pum.show();
    }


  }

  /**
   * @param event
   * @throws IOException method to check different options to handle the clicked Letter in rack
   *         Place 4 like in rackPlace1Clicked
   * @author apilgirm
   */
  @FXML
  private void rackPlace4clicked(MouseEvent event) throws IOException {
    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {

      rackPlace4 = (ImageView) event.getSource();
      // no other letter is clicked
      if (!letterClicked && rackPlace4.getOpacity() != 0) {

        clickedLetter = rackPlace4;
        letterClicked = true;
        rackClicked = 3;
        clickedLabel = pointsRack4;

        // no destination tile chosen
        if (!tileClicked) {
          rackPlace4.setOpacity(0.8);

          // destination chosen
        } else if (Data.getGameSession().getGameBoard().placeTile(
            Data.getGameSession().getCurrentPlayer().getRack().getTileAt(3),
            rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))) {
          placeLetter(clickedLetter, pointsRack4);
          rackPlace4.setOpacity(0);
          choosenTiles.add(clickedTile.getId());
          clickedTile = null;
          tileClicked = false;
          exchangeable = true;
        }

      } else {
        if (clickedLetter == rackPlace4) {
          unclickLetter(rackPlace4);
        } else {
          switchClickedLetter(rackPlace4);
        }
      }
    } else {
      PopUpMessage pum =
          new PopUpMessage("Sorry, you're not the current Player", PopUpMessageType.NOTIFICATION);
      pum.show();
    }

  }

  /**
   * @param event
   * @throws IOException method to check different options to handle the clicked Letter in rack
   *         Place 5 like in rackPlace1Clicked
   * @author apilgirm
   */
  @FXML
  private void rackPlace5clicked(MouseEvent event) throws IOException {
    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {
      rackPlace5 = (ImageView) event.getSource();
      // no other letter is clicked
      if (!letterClicked && rackPlace5.getOpacity() != 0) {

        clickedLetter = rackPlace5;
        letterClicked = true;
        rackClicked = 4;
        clickedLabel = pointsRack5;

        // no destination tile chosen
        if (!tileClicked) {
          rackPlace5.setOpacity(0.8);

          // destination chosen
        } else if (Data.getGameSession().getGameBoard().placeTile(
            Data.getGameSession().getCurrentPlayer().getRack().getTileAt(4),
            rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))) {

          placeLetter(clickedLetter, pointsRack5);
          rackPlace5.setOpacity(0);
          choosenTiles.add(clickedTile.getId());
          clickedTile = null;
          tileClicked = false;
          exchangeable = true;
        }

      } else {
        if (clickedLetter == rackPlace5) {
          unclickLetter(rackPlace5);
        } else {
          switchClickedLetter(rackPlace5);
        }
      }
    } else {
      PopUpMessage pum =
          new PopUpMessage("Sorry, you're not the current Player", PopUpMessageType.NOTIFICATION);
      pum.show();
    }


  }

  /**
   * @param event
   * @throws IOException method to check different options to handle the clicked Letter in rack
   *         Place 6 like in rackPlace1Clicked
   * @author apilgirm
   */
  @FXML
  private void rackPlace6clicked(MouseEvent event) throws IOException {

    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {

      rackPlace6 = (ImageView) event.getSource();
      // no other letter is clicked
      if (!letterClicked && rackPlace6.getOpacity() != 0) {

        clickedLetter = rackPlace6;
        letterClicked = true;
        rackClicked = 5;
        clickedLabel = pointsRack6;

        // no destination tile chosen
        if (!tileClicked) {
          rackPlace6.setOpacity(0.8);

          // destination chosen
        } else if (Data.getGameSession().getGameBoard().placeTile(
            Data.getGameSession().getCurrentPlayer().getRack().getTileAt(5),
            rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))) {
          placeLetter(clickedLetter, pointsRack6);
          rackPlace6.setOpacity(0);
          choosenTiles.add(clickedTile.getId());
          clickedTile = null;
          tileClicked = false;
          exchangeable = true;
        }

      } else {
        if (clickedLetter == rackPlace6) {
          unclickLetter(rackPlace6);
        } else {
          switchClickedLetter(rackPlace6);
        }
      }
    } else {
      PopUpMessage pum =
          new PopUpMessage("Sorry, you're not the current Player", PopUpMessageType.NOTIFICATION);
      pum.show();
    }

  }

  /**
   * @param event
   * @throws IOException method to check different options to handle the clicked Letter in rack
   *         Place 7 like in rackPlace1Clicked
   * @author apilgirm
   * @author Aaron
   */
  @FXML
  private void rackPlace7clicked(MouseEvent event) throws IOException {

    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {

      rackPlace7 = (ImageView) event.getSource();
      // no other letter is clicked
      if (!letterClicked && rackPlace7.getOpacity() != 0) {

        clickedLetter = rackPlace7;
        letterClicked = true;
        rackClicked = 6;
        clickedLabel = pointsRack7;

        // if no destination tile is chosen the letter is highlighted and the letterClicked is set
        // on
        // true
        if (!tileClicked) {
          rackPlace7.setOpacity(0.8);

          // desination chosen
        } else if (Data.getGameSession().getGameBoard().placeTile(
            Data.getGameSession().getCurrentPlayer().getRack().getTileAt(6),
            rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))) {
          placeLetter(clickedLetter, pointsRack7);
          rackPlace7.setOpacity(0);
          choosenTiles.add(clickedTile.getId());
          clickedTile = null;
          tileClicked = false;
          exchangeable = true;
        }

      } else {
        if (clickedLetter == rackPlace7) {
          unclickLetter(rackPlace7);
        } else {
          switchClickedLetter(rackPlace7);
        }
      }
    } else {
      PopUpMessage pum =
          new PopUpMessage("Sorry, you're not the current Player", PopUpMessageType.NOTIFICATION);
      pum.show();
    }

  }


  /**
   * @param event
   * @throws IOException method to refill rack where letters have been placed and to permanently
   *         lock
   * @author apilgirm, (small part trohwede)
   */
  @FXML
  private void playClicked(MouseEvent event) throws IOException {
    ImageView iv = (ImageView) event.getSource();

    // TODO
    // if (Data.getGameSession().getGameBoard().checkWordsLegit()) {

    String message = "Congrats you scored: " + Data.getGameSession().getGameBoard().countScore();
    PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.ERROR);
    pum.show();
    Data.getGameSession().getGameBoard().finishTurn();
    Data.getGameSession().finishTurn();

    // } else {
    // String message = "The word placed isnt legit!";
    // PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.NOTIFICATION);
    // }

    // TODO

    // reset Opacity on the Rag Board if not null
    if (rackPlace1 != null && rackPlace1.getOpacity() == 0) {
      rackPlace1.setOpacity(1);
      rackRemoveTile(0);
    }
    if (rackPlace2 != null && rackPlace2.getOpacity() == 0) {
      rackPlace2.setOpacity(1);
      rackRemoveTile(1);
    }
    if (rackPlace3 != null && rackPlace3.getOpacity() == 0) {
      rackPlace3.setOpacity(1);
      rackRemoveTile(2);
    }
    if (rackPlace4 != null && rackPlace4.getOpacity() == 0) {
      rackPlace4.setOpacity(1);
      rackRemoveTile(3);
    }
    if (rackPlace5 != null && rackPlace5.getOpacity() == 0) {
      rackPlace5.setOpacity(1);
      rackRemoveTile(4);
    }
    if (rackPlace6 != null && rackPlace6.getOpacity() == 0) {
      rackPlace6.setOpacity(1);
      rackRemoveTile(5);
    }
    if (rackPlace7 != null && rackPlace7.getOpacity() == 0) {
      rackPlace7.setOpacity(1);
      rackRemoveTile(6);
    }

    if (pointsRack1 != null && (pointsRack1.getLayoutY() < 740)) {
      Label point = new Label(pointsRack1.getText());
      point.setLayoutX(pointsRack1.getLayoutX());
      point.setLayoutY(pointsRack1.getLayoutY());
      mainPane.getChildren().add(mainPane.getChildren().size(), point);
      pointsRack1.setLayoutX(rackPlace1.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack1.setLayoutY(rackPlace1.getLayoutY() + LABEL_Y_CORD_BACK);
    }
    if (pointsRack2 != null && (pointsRack2.getLayoutY() < 740)) {
      Label point = new Label(pointsRack2.getText());
      point.setLayoutX(pointsRack2.getLayoutX());
      point.setLayoutY(pointsRack2.getLayoutY());
      mainPane.getChildren().add(mainPane.getChildren().size(), point);
      pointsRack2.setLayoutX(rackPlace2.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack2.setLayoutY(rackPlace2.getLayoutY() + LABEL_Y_CORD_BACK);
    }
    if (pointsRack3 != null && (pointsRack3.getLayoutY() < 740)) {
      Label point = new Label(pointsRack3.getText());
      point.setLayoutX(pointsRack3.getLayoutX());
      point.setLayoutY(pointsRack3.getLayoutY());
      mainPane.getChildren().add(mainPane.getChildren().size(), point);
      pointsRack3.setLayoutX(rackPlace3.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack3.setLayoutY(rackPlace3.getLayoutY() + LABEL_Y_CORD_BACK);
    }
    if (pointsRack4 != null && (pointsRack4.getLayoutY() < 740)) {
      Label point = new Label(pointsRack4.getText());
      point.setLayoutX(pointsRack4.getLayoutX());
      point.setLayoutY(pointsRack4.getLayoutY());
      mainPane.getChildren().add(mainPane.getChildren().size(), point);
      pointsRack4.setLayoutX(rackPlace4.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack4.setLayoutY(rackPlace4.getLayoutY() + LABEL_Y_CORD_BACK);
    }
    if (pointsRack5 != null && (pointsRack5.getLayoutY() < 740)) {
      Label point = new Label(pointsRack5.getText());
      point.setLayoutX(pointsRack5.getLayoutX());
      point.setLayoutY(pointsRack5.getLayoutY());
      mainPane.getChildren().add(mainPane.getChildren().size(), point);
      pointsRack5.setLayoutX(rackPlace5.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack5.setLayoutY(rackPlace5.getLayoutY() + LABEL_Y_CORD_BACK);
    }
    if (pointsRack6 != null && (pointsRack6.getLayoutY() < 740)) {
      Label point = new Label(pointsRack6.getText());
      point.setLayoutX(pointsRack6.getLayoutX());
      point.setLayoutY(pointsRack6.getLayoutY());
      mainPane.getChildren().add(mainPane.getChildren().size(), point);
      pointsRack6.setLayoutX(rackPlace6.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack6.setLayoutY(rackPlace6.getLayoutY() + LABEL_Y_CORD_BACK);
    }
    if (pointsRack7 != null && (pointsRack7.getLayoutY() < 740)) {
      Label point = new Label(pointsRack7.getText());
      point.setLayoutX(pointsRack7.getLayoutX());
      point.setLayoutY(pointsRack7.getLayoutY());
      mainPane.getChildren().add(mainPane.getChildren().size(), point);
      pointsRack7.setLayoutX(rackPlace7.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack7.setLayoutY(rackPlace7.getLayoutY() + LABEL_Y_CORD_BACK);
    }

    initRack();
    initPlayers();
    exchangeable = false;
    choosenTiles.clear();
    letterClicked = false;
    tileClicked = false;
    clickedLetter = null;
    clickedTile = null;

  }

  /**
   * @param - ImageView
   *        <p>
   *        reset the opacity of the clickedLetter in the Rack and resets him from being clicked
   * @author apilgrim
   */

  private void unclickLetter(ImageView rackPlace) {
    letterClicked = false;
    clickedLetter = null;
    rackPlace.setOpacity(1);
  }

  private void switchClickedLetter(ImageView rackPlace) {
    clickedLetter = rackPlace;
    unclickAll();
    rackPlace.setOpacity(0.8);
  }


  /**
   * @param iv - ImageView
   *        <p>
   *        This method is called when a destination Tile is clicked on the GameBoard which already
   *        contains a letter tile (is chosen but not permanently logged) and brings back the letter
   *        to the rack. It changes the Image on the Board back to the marked Tile (black square)
   *        and brings the Letter from the Board back to the rack thru the opacity and resets the
   *        clicked attributes (Letter/ Tile) for source and destination
   * @author apilgrim
   */
  private void backToRack(ImageView iv) {

    // check which ragPlace to replace and turn back to rack
    if (rackPlace1 != null
        && iv.getImage().getUrl().equalsIgnoreCase(rackPlace1.getImage().getUrl())
        && rackPlace1.getOpacity() == 0 && isChoosen(iv)) {
      rackPlace1.setOpacity(1);
      pointsRack1.setLayoutX(rackPlace1.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack1.setLayoutY(rackPlace1.getLayoutY() + LABEL_Y_CORD_BACK);
      iv.setImage(markedTile);
      removeChoosen(iv.getId());
      Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()), columnTransformation(iv.getId()));
    } else if (rackPlace2 != null
        && iv.getImage().getUrl().equalsIgnoreCase(rackPlace2.getImage().getUrl())
        && rackPlace2.getOpacity() == 0 && isChoosen(iv)) {
      rackPlace2.setOpacity(1);
      pointsRack2.setLayoutX(rackPlace2.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack2.setLayoutY(rackPlace2.getLayoutY() + LABEL_Y_CORD_BACK);
      iv.setImage(markedTile);
      removeChoosen(iv.getId());
      Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()), columnTransformation(iv.getId()));
    } else if (rackPlace3 != null
        && iv.getImage().getUrl().equalsIgnoreCase(rackPlace3.getImage().getUrl())
        && rackPlace3.getOpacity() == 0 && isChoosen(iv)) {
      rackPlace3.setOpacity(1);
      pointsRack3.setLayoutX(rackPlace3.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack3.setLayoutY(rackPlace3.getLayoutY() + LABEL_Y_CORD_BACK);
      iv.setImage(markedTile);
      removeChoosen(iv.getId());
      Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()), columnTransformation(iv.getId()));
    } else if (rackPlace4 != null
        && iv.getImage().getUrl().equalsIgnoreCase(rackPlace4.getImage().getUrl())
        && rackPlace4.getOpacity() == 0 && isChoosen(iv)) {
      rackPlace4.setOpacity(1);
      pointsRack4.setLayoutX(rackPlace4.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack4.setLayoutY(rackPlace4.getLayoutY() + LABEL_Y_CORD_BACK);
      iv.setImage(markedTile);
      removeChoosen(iv.getId());
      Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()), columnTransformation(iv.getId()));
    } else if (rackPlace5 != null
        && iv.getImage().getUrl().equalsIgnoreCase(rackPlace5.getImage().getUrl())
        && rackPlace5.getOpacity() == 0 && isChoosen(iv)) {
      rackPlace5.setOpacity(1);
      pointsRack5.setLayoutX(rackPlace5.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack5.setLayoutY(rackPlace5.getLayoutY() + LABEL_Y_CORD_BACK);
      iv.setImage(markedTile);
      removeChoosen(iv.getId());
      Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()), columnTransformation(iv.getId()));
    } else if (rackPlace6 != null
        && iv.getImage().getUrl().equalsIgnoreCase(rackPlace6.getImage().getUrl())
        && rackPlace6.getOpacity() == 0 && isChoosen(iv)) {
      rackPlace6.setOpacity(1);
      pointsRack6.setLayoutX(rackPlace6.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack6.setLayoutY(rackPlace6.getLayoutY() + LABEL_Y_CORD_BACK);
      iv.setImage(markedTile);
      removeChoosen(iv.getId());
      Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()), columnTransformation(iv.getId()));
    } else if (rackPlace7 != null
        && iv.getImage().getUrl().equalsIgnoreCase(rackPlace7.getImage().getUrl())
        && rackPlace7.getOpacity() == 0 && isChoosen(iv)) {
      rackPlace7.setOpacity(1);
      pointsRack7.setLayoutX(rackPlace7.getLayoutX() + LABEL_X_CORD_BACK);
      pointsRack7.setLayoutY(rackPlace7.getLayoutY() + LABEL_Y_CORD_BACK);
      iv.setImage(markedTile);
      removeChoosen(iv.getId());
      Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()), columnTransformation(iv.getId()));
    }
    iv.setImage(markedTile);
    // TODO
    clickedTile = null;
    tileClicked = false;
    letterClicked = false;
  }

  /**
   * @param iv - ImageView
   *        <p>
   *        This method is called when a destination Tile is clicked on the GameBoard and a Letter
   *        Tile is selected. It changes the Image on the Board and "deletes" the Letter from the
   *        Board thru the opacity and resets the boolean clicked attributes (Letter/ Tile) for
   *        source and destination
   * @author apilgrim
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
   * @param placeID - String representation of the coordinate from every tile on the board read from
   *        the fxml document as ID
   * @return x - Integer representation of the x coordinate for the tile, placed on the Gameboard
   * @author apilgrim
   */
  private int rowTransformation(String placeID) {

    // check if column number
    char xCord = placeID.charAt(0);
    int x = ((int) xCord) - 97;
    return x;
  }


  /**
   * @param placeID - String representation of the coordinate from every tile on the board read from
   *        the fxml document as ID
   * @return y - Integer representation of the y coordinate for the tile, placed on the Gameboard
   * @author apilgrim
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
    ArrayList<Player> players = Data.getGameSession().getListOfPlayers();
    currentUser = Data.getCurrentUser();
    ArrayList<Integer> order = new ArrayList<>();

    if (rackPlace1.getOpacity() != 0) {
      order.add(0);
    }
    if (rackPlace2.getOpacity() != 0) {
      order.add(1);
    }
    if (rackPlace3.getOpacity() != 0) {
      order.add(2);
    }
    if (rackPlace4.getOpacity() != 0) {
      order.add(3);
    }
    if (rackPlace5.getOpacity() != 0) {
      order.add(4);
    }
    if (rackPlace6.getOpacity() != 0) {
      order.add(5);
    }
    if (rackPlace7.getOpacity() != 0) {
      order.add(6);
    }

    Iterator<Player> it = players.iterator();
    while (it.hasNext()) {
      Player p = it.next();
      String s = p.getName();
      if (s.equals(currentUser)) {
        p.getRack().shuffleRack(order);
        initRack();
      } else {
        continue;
      }
    }

  }

  private void unclickAll() {

    if (rackPlace1.getOpacity() != 0) {
      rackPlace1.setOpacity(1);
    }
    if (rackPlace2.getOpacity() != 0) {
      rackPlace2.setOpacity(1);
    }
    if (rackPlace3.getOpacity() != 0) {
      rackPlace3.setOpacity(1);
    }
    if (rackPlace4.getOpacity() != 0) {
      rackPlace4.setOpacity(1);
    }
    if (rackPlace5.getOpacity() != 0) {
      rackPlace5.setOpacity(1);
    }
    if (rackPlace6.getOpacity() != 0) {
      rackPlace6.setOpacity(1);
    }
    if (rackPlace7.getOpacity() != 0) {
      rackPlace7.setOpacity(1);
    }

  }

  @FXML
  private void exchangeClicked(MouseEvent event) throws IOException {
    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {
      App.setRoot("Exchange");
    } else {
      PopUpMessage pum =
          new PopUpMessage("Sorry, you're not the current Player", PopUpMessageType.NOTIFICATION);
      pum.show();
    }
  }

  protected void setNewTile(ImageView rackPlace, Label point, String letter, int points) {
    if (letter.equals("*")) {
      letter = "placeHolder";
    } else {
      letter = "tile" + letter.toUpperCase();
    }


    Image letterImage = new Image("/com/scrab5/ui/letter_Images/" + letter + ".png");
    rackPlace.setImage(letterImage);
    if (!rackPlace.getImage().getUrl().equals(letterImage.getUrl())) {
      rackPlace.setOpacity(1);
    }
    point.setText(Integer.toString(points));
    point.setOpacity(1);
  }

  private void nextPlayer() {
    if (Data.getGameSession().getCurrentPlayer().getName().equalsIgnoreCase(player1.getText())) {
      playerProfile1Passive.setOpacity(0);
    } else {
      playerProfile1Passive.setOpacity(1);
    }
    if (Data.getGameSession().getCurrentPlayer().getName().equalsIgnoreCase(player2.getText())) {
      playerProfile2Passive.setOpacity(0);
    } else if (1 < playerAmount) {
      playerProfile2Passive.setOpacity(1);
    }
    if (Data.getGameSession().getCurrentPlayer().getName().equalsIgnoreCase(player3.getText())) {
      playerProfile3Passive.setOpacity(0);
    } else if (2 < playerAmount) {
      playerProfile3Passive.setOpacity(1);
    }
    if (Data.getGameSession().getCurrentPlayer().getName().equalsIgnoreCase(player4.getText())) {
      playerProfile4Passive.setOpacity(0);
    } else if (3 < playerAmount) {
      playerProfile4Passive.setOpacity(1);
    }
  }


  protected void rackRemoveTile(int pos) {
    ArrayList<Player> players = Data.getGameSession().getListOfPlayers();
    currentUser = Data.getCurrentUser();

    Iterator<Player> it = players.iterator();
    while (it.hasNext()) {
      Player p = it.next();
      String s = p.getName();
      if (s.equals(currentUser)) {
        p.getRack().removeTileFromRack(pos);
        p.getRack().fill(Data.getGameSession().getBag());
      } else {
        continue;
      }
    }
  }

  private boolean isChoosen(ImageView iv) {

    for (int i = 0; i < choosenTiles.size(); i++) {
      if (choosenTiles.get(i).equalsIgnoreCase(iv.getId())) {
        return true;
      }
    }
    return false;
  }

  private void removeChoosen(String choosen) {

    for (int i = 0; i < choosenTiles.size(); i++) {
      if (choosenTiles.get(i).equalsIgnoreCase(choosen)) {
        choosenTiles.remove(i);
      }
    }

  }

  /**
   * @param event
   * @author mherre
   * @throws IOException
   */
  @FXML
  private void closeGame(MouseEvent event) throws IOException {
    if (Data.getPlayerClient() != null) {
      Data.getPlayerClient().disconnectFromServer();
    }
  }

  
}

