package com.scrab5.ui;

import com.scrab5.core.game.Rack;
import com.scrab5.core.game.Tile;
import com.scrab5.core.player.Player;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/*
 * @author apilgrim
 */

public abstract class InGameController implements Initializable {


  private boolean tileClicked = false;
  private boolean letterClicked = false;
  private ImageView clickedTile;
  private ImageView clickedLetter;
  private Label clickedLabel;
  private Media sound;
  private MediaPlayer mediaPlayer;

  private Image markedTile;

  private static final double LABEL_X_CORD = 30.0;
  private static final double LABEL_Y_CORD = 23.0;
  private static final double LABEL_X_CORD_BACK = 46.0;
  private static final double LABEL_Y_CORD_BACK = 44.0;

  private boolean endPossible = false;

  private String letterJoker;

  @FXML
  protected ImageView rackPlace1;
  @FXML
  protected ImageView rackPlace2;
  @FXML
  protected ImageView rackPlace3;
  @FXML
  protected ImageView rackPlace4;
  @FXML
  protected ImageView rackPlace5;
  @FXML
  protected ImageView rackPlace6;
  @FXML
  protected ImageView rackPlace7;

  @FXML
  private ImageView playerProfile1Passive;
  @FXML
  private ImageView playerProfile2Passive;
  @FXML
  private ImageView playerProfile3Passive;
  @FXML
  private ImageView playerProfile4Passive;

  @FXML
  private ImageView playerProfile2Active;
  @FXML
  private ImageView playerProfile3Active;
  @FXML
  private ImageView playerProfile4Active;

  @FXML
  private ImageView endGame;

  @FXML
  private ImageView undoButton;

  @FXML
  private Label undoLabel;

  @FXML
  private AnchorPane mainPane;

  @FXML
  private AnchorPane jokerPane;


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
  private Label pointsPlayer4;

  @FXML
  private Label skipPlay;

  private int rackClicked;

  Boolean[] rackChanges = new Boolean[7];

  private ArrayList<ImageView> changes = new ArrayList<ImageView>();

  private ArrayList<Player> players = Data.getGameSession().getListOfPlayers();
  private int playerAmount = players.size();

  private ArrayList<Integer> jokerPlacedAt = new ArrayList<>();
  private ArrayList<Integer> tilePlacedOrder = new ArrayList<>();

  private boolean chooseJoker = false;
  private boolean turn = true;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {}

  // init section

  protected void initPlayers() throws IOException {

    if (changes.size() != 0) {
      undoLabel.setOpacity(1);
      undoButton.setOpacity(1);
    } else {
      undoLabel.setOpacity(0);
      undoButton.setOpacity(0);
    }

    if (turn
        && Data.getGameSession()
            .getCurrentPlayer()
            .getName()
            .equalsIgnoreCase(Data.getCurrentUser())) {
      turn = false;
      newPum("IT'S YOUR TURN");
    }

    if (Data.getGameSession().getGameBoard().getCurrentChanges().size() == 0) {
      skipPlay.setText("Skip");
    } else {
      skipPlay.setText("Play");
    }

    if (Data.getGameSession().getSkippedTurn() >= 6) {
      endGame.setOpacity(1);
      endPossible = true;
    }

    if (0 < playerAmount) {
      pointsPlayer1.setText(
          Integer.toString(Data.getGameSession().getListOfPlayers().get(0).getPoints()));
      player1.setText(players.get(0).getName());
      if (players
          .get(0)
          .getName()
          .equalsIgnoreCase(Data.getGameSession().getCurrentPlayer().getName())) {
        playerProfile1Passive.setOpacity(0);
      }
    }
    if (1 < playerAmount) {
      player2.setText(players.get(1).getName());
      player2.setOpacity(1);
      pointsPlayer2.setText(
          Integer.toString(Data.getGameSession().getListOfPlayers().get(1).getPoints()));
      pointsPlayer2.setOpacity(1);
      playerProfile2Active.setOpacity(1);
      playerProfile2Passive.setOpacity(1);
      if (players
          .get(1)
          .getName()
          .equalsIgnoreCase(Data.getGameSession().getCurrentPlayer().getName())) {
        playerProfile1Passive.setOpacity(0);
      }
    }
    if (2 < playerAmount) {
      player3.setText(players.get(2).getName());
      player3.setOpacity(1);
      pointsPlayer3.setText(
          Integer.toString(Data.getGameSession().getListOfPlayers().get(2).getPoints()));
      pointsPlayer3.setOpacity(1);
      playerProfile3Active.setOpacity(1);
      playerProfile3Passive.setOpacity(1);
      if (players.get(2).getName()
          .equalsIgnoreCase(Data.getGameSession().getCurrentPlayer().getName())) {
        playerProfile1Passive.setOpacity(0);
      }
    }
    if (3 < playerAmount) {
      player4.setText(players.get(3).getName());
      player4.setOpacity(1);
      pointsPlayer4
          .setText(Integer.toString(Data.getGameSession().getListOfPlayers().get(3).getPoints()));
      pointsPlayer4.setOpacity(1);
      playerProfile4Active.setOpacity(1);
      playerProfile4Passive.setOpacity(1);
      if (players.get(3).getName()
          .equalsIgnoreCase(Data.getGameSession().getCurrentPlayer().getName())) {
        playerProfile1Passive.setOpacity(0);
      }
    }

    nextPlayer();
  }

  protected void initRack() {
    Rack myRack = null;

    for (Player p : Data.getGameSession().getListOfPlayers()) {
      String s = p.getName();
      if (s.equalsIgnoreCase(Data.getCurrentUser())) {
        myRack = p.getRack();
      } else {
        continue;
      }
    }

    for (int i = 0; i < 7; i++) {
      if (myRack.getTileAt(i) != null) {
        switch (i) {

          case 0:
            setNewTile(rackPlace1, pointsRack1, "joker", myRack.getTileAt(i).getValue());
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

    double layoutX = 263.0;
    double layoutY = 53.0;

    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        if (Data.getGameSession().getGameBoard().getPlayedTile(i, j) != null) {
          ImageView letterImage = new ImageView();
          String letter = Data.getGameSession().getGameBoard().getPlayedTile(i, j).getLetter();
          String points =
              Integer.toString(Data.getGameSession().getGameBoard().getPlayedTile(i, j).getValue());
          letterImage.setFitWidth(42.0);
          letterImage.setFitHeight(42.0);
          if (letter.equalsIgnoreCase("*")) {
            letterImage.setImage(new Image("/com/scrab5/ui/letter_Images/placeHolder.png"));
          } else {
            letterImage.setImage(
                new Image("/com/scrab5/ui/letter_Images/tile" + letter.toUpperCase() + ".png"));
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


  // Tile acctions

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
    playSound("ButtonClicked.mp3");

    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {

      ImageView iv = ((ImageView) event.getSource());

      // check if the field clicked is a letter to remove
      if (!iv.getImage().getUrl().contains("letter_Images")) {

        // check if no tiles has been clicked
        if (!tileClicked) {
          clickedTile = (ImageView) event.getSource();
          if (!letterClicked) {
            lighten(event);
            tileClicked = true;
          } else if (clickedLetter.getImage().getUrl().contains("placeHolder")) {
            setJoker();
          } else if (Data.getGameSession().getGameBoard().placeTile(
              Data.getGameSession().getCurrentPlayer().getRack().getTileAt(rackClicked),
              rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))
              && rackClicked < 7) {
            changes.add(clickedTile);
            placeLetter(clickedLetter, clickedLabel);
          } else {
            newPum("Sorry, the tile can't be placed here"
                + "\nRemember to place youre letter tiles in the same row OR column (per round)!");
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
    playSound("ButtonClicked.mp3");
    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {
      rackPlace1 = (ImageView) event.getSource();
      rackClicked = 0;

      // no other letter is clicked
      if (!letterClicked && rackPlace1.getOpacity() != 0) {

        clickedLetter = rackPlace1;
        letterClicked = true;
        clickedLabel = pointsRack1;

        // no destination tile chosen
        if (!tileClicked) {
          rackPlace1.setOpacity(0.8);

          // destination chosen
        } else if (clickedLetter.getImage().getUrl().contains("placeHolder")) {
          setJoker();
        } else if (Data.getGameSession().getGameBoard().placeTile(
            Data.getGameSession().getCurrentPlayer().getRack().getTileAt(0),
            rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))) {
          changes.add(clickedTile);
          chooseJoker = false;
          jokerPane.setOpacity(0);
          placeLetter(clickedLetter, pointsRack1);
          rackPlace1.setOpacity(0);
          clickedTile = null;
          tileClicked = false;
        } else {
          newPum("Sorry, the tile can't be placed here"
              + "\nRemember to place youre letter tiles in the same row OR column (per round)!");
        }

      } else {
        if (clickedLetter != null) {
          if (clickedLetter == rackPlace1) {
            unclickLetter(rackPlace1);
          } else if (rackPlace1.getOpacity() != 0) {
            switchClickedLetter(rackPlace1, pointsRack1);
          }
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
    playSound("ButtonClicked.mp3");
    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {
      rackPlace2 = (ImageView) event.getSource();
      rackClicked = 1;

      // no other letter is clicked
      if (!letterClicked && rackPlace2.getOpacity() != 0) {

        clickedLetter = rackPlace2;
        letterClicked = true;
        clickedLabel = pointsRack2;

        // no destination tile chosen
        if (!tileClicked) {
          rackPlace2.setOpacity(0.8);

          // destination chosen
        } else if (clickedLetter.getImage().getUrl().contains("placeHolder")) {
          setJoker();
        } else if (Data.getGameSession().getGameBoard().placeTile(
            Data.getGameSession().getCurrentPlayer().getRack().getTileAt(1),
            rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))) {
          changes.add(clickedTile);
          chooseJoker = false;
          jokerPane.setOpacity(0);
          placeLetter(clickedLetter, pointsRack2);
          rackPlace2.setOpacity(0);
          clickedTile = null;
          tileClicked = false;
        } else {
          newPum(
              "Sorry, the tile can't be placed here.\nRemember to place youre letter tiles in the same row OR column (per round)!");
        }

      } else {
        if (clickedLetter != null) {
          if (clickedLetter == rackPlace2) {
            unclickLetter(rackPlace2);
          } else if (rackPlace2.getOpacity() != 0) {
            switchClickedLetter(rackPlace2, pointsRack2);
          }
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
    playSound("ButtonClicked.mp3");
    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {
      rackPlace3 = (ImageView) event.getSource();
      rackClicked = 2;

      // no other letter is clicked
      if (!letterClicked && rackPlace3.getOpacity() != 0) {

        clickedLetter = rackPlace3;
        letterClicked = true;
        clickedLabel = pointsRack3;

        // no destination tile chosen
        if (!tileClicked) {
          rackPlace3.setOpacity(0.8);

          // destination chosen
        } else if (clickedLetter.getImage().getUrl().contains("placeHolder")) {
          setJoker();
        } else if (Data.getGameSession().getGameBoard().placeTile(
            Data.getGameSession().getCurrentPlayer().getRack().getTileAt(2),
            rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))) {

          changes.add(clickedTile);
          chooseJoker = false;
          jokerPane.setOpacity(0);
          placeLetter(clickedLetter, pointsRack3);
          rackPlace3.setOpacity(0);
          clickedTile = null;
          tileClicked = false;
        } else {
          newPum(
              "Sorry, the tile can't be placed here.\nRemember to place youre letter tiles in the same row OR column (per round)!");
        }
      } else {
        if (clickedLetter != null) {
          if (clickedLetter == rackPlace3) {
            unclickLetter(rackPlace3);
          } else if (rackPlace3.getOpacity() != 0) {
            switchClickedLetter(rackPlace3, pointsRack3);
          }
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
    playSound("ButtonClicked.mp3");
    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {
      rackPlace4 = (ImageView) event.getSource();
      rackClicked = 3;

      // no other letter is clicked
      if (!letterClicked && rackPlace4.getOpacity() != 0) {

        clickedLetter = rackPlace4;
        letterClicked = true;
        clickedLabel = pointsRack4;

        // no destination tile chosen
        if (!tileClicked) {
          rackPlace4.setOpacity(0.8);

          // destination chosen
        } else if (clickedLetter.getImage().getUrl().contains("placeHolder")) {
          setJoker();
        } else if (Data.getGameSession().getGameBoard().placeTile(
            Data.getGameSession().getCurrentPlayer().getRack().getTileAt(3),
            rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))) {
          changes.add(clickedTile);
          chooseJoker = false;
          jokerPane.setOpacity(0);
          placeLetter(clickedLetter, pointsRack4);
          rackPlace4.setOpacity(0);
          clickedTile = null;
          tileClicked = false;
        } else {
          newPum(
              "Sorry, the tile can't be placed here.\nRemember to place youre letter tiles in the same row OR column (per round)!");
        }

      } else {
        if (clickedLetter != null) {
          if (clickedLetter == rackPlace4) {
            unclickLetter(rackPlace4);
          } else if (rackPlace4.getOpacity() != 0) {
            switchClickedLetter(rackPlace4, pointsRack4);
          }
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
    playSound("ButtonClicked.mp3");
    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {
      rackPlace5 = (ImageView) event.getSource();
      rackClicked = 4;

      // no other letter is clicked
      if (!letterClicked && rackPlace5.getOpacity() != 0) {

        clickedLetter = rackPlace5;
        letterClicked = true;
        clickedLabel = pointsRack5;

        // no destination tile chosen
        if (!tileClicked) {
          rackPlace5.setOpacity(0.8);

          // destination chosen
        } else if (clickedLetter.getImage().getUrl().contains("placeHolder")) {
          setJoker();
        } else if (Data.getGameSession().getGameBoard().placeTile(
            Data.getGameSession().getCurrentPlayer().getRack().getTileAt(4),
            rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))) {
          changes.add(clickedTile);
          chooseJoker = false;
          jokerPane.setOpacity(0);
          placeLetter(clickedLetter, pointsRack5);
          rackPlace5.setOpacity(0);
          clickedTile = null;
          tileClicked = false;
        } else {
          newPum(
              "Sorry, the tile can't be placed here.\nRemember to place youre letter tiles in the same row OR column (per round)!");
        }

      } else {
        if (clickedLetter != null) {
          if (clickedLetter == rackPlace5) {
            unclickLetter(rackPlace5);
          } else if (rackPlace5.getOpacity() != 0) {
            switchClickedLetter(rackPlace5, pointsRack5);
          }
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
    playSound("ButtonClicked.mp3");

    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {
      rackPlace6 = (ImageView) event.getSource();
      rackClicked = 5;

      // no other letter is clicked
      if (!letterClicked && rackPlace6.getOpacity() != 0) {

        clickedLetter = rackPlace6;
        letterClicked = true;
        clickedLabel = pointsRack6;

        // no destination tile chosen
        if (!tileClicked) {
          rackPlace6.setOpacity(0.8);

          // destination chosen
        } else if (clickedLetter.getImage().getUrl().contains("placeHolder")) {
          setJoker();
        } else if (Data.getGameSession().getGameBoard().placeTile(
            Data.getGameSession().getCurrentPlayer().getRack().getTileAt(5),
            rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))) {
          changes.add(clickedTile);
          chooseJoker = false;
          jokerPane.setOpacity(0);
          placeLetter(clickedLetter, pointsRack6);
          rackPlace6.setOpacity(0);
          clickedTile = null;
          tileClicked = false;
        } else {
          newPum(
              "Sorry, the tile can't be placed here.\nRemember to place youre letter tiles in the same row OR column (per round)!");
        }

      } else {
        if (clickedLetter != null) {
          if (clickedLetter == rackPlace6) {
            unclickLetter(rackPlace6);
          } else if (rackPlace6.getOpacity() != 0) {
            switchClickedLetter(rackPlace6, pointsRack6);
          }
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
    playSound("ButtonClicked.mp3");

    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {

      rackPlace7 = (ImageView) event.getSource();
      rackClicked = 6;

      // no other letter is clicked
      if (!letterClicked && rackPlace7.getOpacity() != 0) {

        clickedLetter = rackPlace7;
        letterClicked = true;
        clickedLabel = pointsRack7;

        // if no destination tile is chosen the letter is highlighted and the letterClicked is set
        // on
        // true
        if (!tileClicked) {
          rackPlace7.setOpacity(0.8);

          // desination chosen
        } else if (clickedLetter.getImage().getUrl().contains("placeHolder")) {
          setJoker();
        } else if (Data.getGameSession().getGameBoard().placeTile(
            Data.getGameSession().getCurrentPlayer().getRack().getTileAt(6),
            rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))) {
          changes.add(clickedTile);
          chooseJoker = false;
          jokerPane.setOpacity(0);
          placeLetter(clickedLetter, pointsRack7);
          rackPlace7.setOpacity(0);
          clickedTile = null;
          tileClicked = false;
        } else {
          newPum(
              "Sorry, the tile can't be placed here.\nRemember to place youre letter tiles in the same row OR column (per round)!");
        }

      } else {
        if (clickedLetter != null) {
          if (clickedLetter == rackPlace7) {
            unclickLetter(rackPlace7);
          } else if (rackPlace7.getOpacity() != 0) {
            switchClickedLetter(rackPlace7, pointsRack7);
          }
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
    playSound("ButtonClicked.mp3");
    ImageView iv = (ImageView) event.getSource();

    ArrayList<Tile> currentChanges = Data.getGameSession().getGameBoard().getCurrentChanges();

    Iterator<Tile> it = currentChanges.iterator();
    Tile check;
    boolean notPlaceable = false;;

    while (it.hasNext()) {
      check = it.next();

      if (check.getColumn() == 7 && check.getRow() == 7 && currentChanges.size() < 2) {
        notPlaceable = true;
      }
    }

    if (Data.getGameSession().getGameBoard().getCurrentChanges().size() > 0) {
      if (!notPlaceable) {
        if (Data.getGameSession().getGameBoard().checkWordsLegit()) {

          Data.getGameSession().setSkippedTurn(0);

          int points = Data.getGameSession().getGameBoard().countScore();

          String message = "Congrats you scored: " + points;
          Data.getGameSession().getCurrentPlayer().setPoints(points);
          PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.ERROR);
          pum.show();

          Player current = Data.getGameSession().getCurrentPlayer();

          Data.getGameSession().getGameBoard().finishTurn();
          Data.getGameSession().finishTurn();

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
          letterClicked = false;
          tileClicked = false;
          clickedLetter = null;
          clickedTile = null;
          turn = true;
          changes.clear();

          Data.getGameSession().checkBagAndRack(current);

        } else {
          String message = "The word placed isnt legit!";
          PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.NOTIFICATION);
          pum.show();
        }
      } else {
        newPum("The first placed word must\nhave a minimum length of two!");
      }
    } else {
      Data.getGameSession().setSkippedTurn(Data.getGameSession().getSkippedTurn() + 1);

      Data.getGameSession().getGameBoard().finishTurn();
      Data.getGameSession().finishTurn();
    }


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
    clickedLabel = null;
    rackPlace.setOpacity(1);
  }

  private void switchClickedLetter(ImageView rackPlace, Label points) {
    clickedLetter = rackPlace;
    clickedLabel = points;
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
  private void backToRack(ImageView iv, int place) {

    if (jokerPlacedAt.size() > 0
        && jokerPlacedAt.get(jokerPlacedAt.size() - 1) == (changes.size() - 1)) {
      if (place == 0) {
        rackPlace1.setOpacity(1);
        Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()),
            columnTransformation(iv.getId()));
      } else if (place == 1) {
        rackPlace2.setOpacity(1);
        Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()),
            columnTransformation(iv.getId()));
      } else if (place == 2) {
        rackPlace3.setOpacity(1);
        Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()),
            columnTransformation(iv.getId()));
      } else if (place == 3) {
        rackPlace4.setOpacity(1);
        Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()),
            columnTransformation(iv.getId()));
      } else if (place == 4) {
        rackPlace5.setOpacity(1);
        Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()),
            columnTransformation(iv.getId()));
      } else if (place == 5) {
        rackPlace6.setOpacity(1);
        Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()),
            columnTransformation(iv.getId()));
      } else if (place == 6) {
        rackPlace7.setOpacity(1);
        Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()),
            columnTransformation(iv.getId()));
      }
      jokerPlacedAt.remove(jokerPlacedAt.size() - 1);
    } else {
      System.out.println("hallo2" + tilePlacedOrder.get(tilePlacedOrder.size() - 1));
      if (place == 0) {
        rackPlace1.setOpacity(1);
        pointsRack1.setLayoutX(rackPlace1.getLayoutX() + LABEL_X_CORD_BACK);
        pointsRack1.setLayoutY(rackPlace1.getLayoutY() + LABEL_Y_CORD_BACK);
        iv.setImage(markedTile);
        Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()),
            columnTransformation(iv.getId()));
      } else if (place == 1) {
        rackPlace2.setOpacity(1);
        pointsRack2.setLayoutX(rackPlace2.getLayoutX() + LABEL_X_CORD_BACK);
        pointsRack2.setLayoutY(rackPlace2.getLayoutY() + LABEL_Y_CORD_BACK);
        iv.setImage(markedTile);
        Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()),
            columnTransformation(iv.getId()));
      } else if (place == 2) {
        rackPlace3.setOpacity(1);
        pointsRack3.setLayoutX(rackPlace3.getLayoutX() + LABEL_X_CORD_BACK);
        pointsRack3.setLayoutY(rackPlace3.getLayoutY() + LABEL_Y_CORD_BACK);
        iv.setImage(markedTile);
        Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()),
            columnTransformation(iv.getId()));
      } else if (place == 3) {
        rackPlace4.setOpacity(1);
        pointsRack4.setLayoutX(rackPlace4.getLayoutX() + LABEL_X_CORD_BACK);
        pointsRack4.setLayoutY(rackPlace4.getLayoutY() + LABEL_Y_CORD_BACK);
        iv.setImage(markedTile);
        Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()),
            columnTransformation(iv.getId()));
      } else if (place == 4) {
        rackPlace5.setOpacity(1);
        pointsRack5.setLayoutX(rackPlace5.getLayoutX() + LABEL_X_CORD_BACK);
        pointsRack5.setLayoutY(rackPlace5.getLayoutY() + LABEL_Y_CORD_BACK);
        iv.setImage(markedTile);
        Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()),
            columnTransformation(iv.getId()));
      } else if (place == 5) {
        rackPlace6.setOpacity(1);
        pointsRack6.setLayoutX(rackPlace6.getLayoutX() + LABEL_X_CORD_BACK);
        pointsRack6.setLayoutY(rackPlace6.getLayoutY() + LABEL_Y_CORD_BACK);
        iv.setImage(markedTile);
        Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()),
            columnTransformation(iv.getId()));
      } else if (place == 6) {
        rackPlace7.setOpacity(1);
        pointsRack7.setLayoutX(rackPlace7.getLayoutX() + LABEL_X_CORD_BACK);
        pointsRack7.setLayoutY(rackPlace7.getLayoutY() + LABEL_Y_CORD_BACK);
        iv.setImage(markedTile);
        Data.getGameSession().getGameBoard().removeTile(rowTransformation(iv.getId()),
            columnTransformation(iv.getId()));
      }
    }

    tilePlacedOrder.remove(tilePlacedOrder.size() - 1);
    iv.setImage(markedTile);
    clickedTile = null;
    tileClicked = false;
    letterClicked = false;
    changes.remove(changes.size() - 1);

  }

  /**
   * @param iv - ImageView This method is called when a destination Tile is clicked on the GameBoard
   *        and a Letter Tile is selected. It changes the Image on the Board and "deletes" the
   *        Letter from the Board thru the opacity and resets the boolean clicked attributes
   *        (Letter/ Tile) for source and destination
   * @author apilgrim
   */
  private void placeLetter(ImageView iv, Label l) {

    System.out.println();
    tilePlacedOrder.add(rackClicked);

    markedTile = clickedTile.getImage();
    clickedTile.setImage(iv.getImage());
    l.setLayoutX(clickedTile.getLayoutX() + LABEL_X_CORD);
    l.setLayoutY(clickedTile.getLayoutY() + LABEL_Y_CORD);
    clickedLetter.setOpacity(0);
    clickedLetter = null;
    clickedLabel = null;
    letterClicked = false;
    tileClicked = false;
    clickedTile = null;
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
    playSound("ButtonClicked.mp3");
    ArrayList<Player> players = Data.getGameSession().getListOfPlayers();
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
      if (s.equals(Data.getCurrentUser())) {
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
    playSound("ButtonClicked.mp3");
    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {
      if ((Data.getGameSession().getGameBoard().getCurrentChanges().size() == 0)) {
        if (Data.getGameSession().getBag().getSize() > 7) {
          App.setRoot("Exchange");
        } else {
          newPum("Sorry there are not enough tile left in the bag");
        }
      } else {
        newPum(
            "You can't place tiles and exchange in the same turn!\nRemove all before exchanging");
      }
    } else {
      PopUpMessage pum =
          new PopUpMessage("Sorry, you're not the current Player", PopUpMessageType.NOTIFICATION);
      pum.show();
    }
  }

  protected void setNewTile(ImageView rackPlace, Label point, String letter, int points) {
    if (letter.equals("space") | letter.equals("*") | letter.equals("joker")) {
      letter = "placeHolder";
    } else {
      letter = "tile" + letter.toUpperCase();
    }
    Image letterImage = new Image("/com/scrab5/ui/letter_Images/" + letter + ".png");
    rackPlace.setImage(letterImage);
    if (!rackPlace.getImage().getUrl().equals(letterImage.getUrl())) {
      rackPlace.setOpacity(1);
    }
    if (!letter.equals("placeHolder")) {
      point.setText(Integer.toString(points));
      point.setOpacity(1);
    } else {
      point.setOpacity(0);
    }
  }

  // Joker handling

  private void setJoker() throws IOException {
    jokerPane.setOpacity(1);
    chooseJoker = true;
    newPum("Choose a letter on the left\n the Joker stands for!");
  }


  @FXML
  private void jokerLetterChoosen(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    ImageView iv = (ImageView) event.getSource();

    if (chooseJoker) {
      letterJoker = iv.getId();
      setNewTile(clickedLetter, clickedLabel, letterJoker, 0);
      if (clickedLetter != null)
        System.out.println(clickedLetter.getImage().getUrl());
      // TODO
      System.out.println(letterJoker);
      if (Data.getGameSession().getGameBoard().placeTile(new Tile(letterJoker, 0),
          rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))) {
        chooseJoker = false;
        placeLetter(clickedLetter, clickedLabel);
        changes.add(clickedTile);
        jokerPlacedAt.add(changes.size() - 1);
      } else {
        System.out.println(Data.getGameSession().getGameBoard().getTile(7, 7).getLetter());
        newPum("Sorry, the tile can't be placed here."
            + "\nRemember to place youre letter tiles in the same row OR column (per round)!");
      }

      jokerPane.setOpacity(0);
    }
  }



  // Buttons

  @FXML
  private void giveUpClicked(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    PopUpMessage pum = new PopUpMessage("Really!?", PopUpMessageType.CONFIRMATION);
    pum.show();
    if (Data.isConfirmed()) {
      Data.getGameSession().setShouldEnd(true);
      Data.getGameSession().endGame();
      if (Data.getGameSession().isOnline()) {
        App.setRoot("EndGameMultiplayer");
      } else {
        App.setRoot("EndGameSingleplayer");
      }
    }
  }

  @FXML
  private void undoClicked(MouseEvent event) throws IOException {
    if (undoButton.getOpacity() == 1) {
      playSound("ButtonClicked.mp3");
      if (changes.size() > 0) {
        backToRack(changes.get(changes.size() - 1),
            tilePlacedOrder.get(tilePlacedOrder.size() - 1));
      }
    }
  }



  // help methods

  /**
   * Method that plays a sound file and adjusts the volume to the volume that has been set by the
   * user in the "Settings.fxml" scene.
   * 
   * @author mherre
   * @param file the String containing the file name
   */
  protected void playSound(String file) {
    sound = new Media(
        Controller.class.getResource("/com/scrab5/ui/sound_effects/" + file).toExternalForm());
    mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.setVolume(Data.getSFXVolume());
    mediaPlayer.play();
  }

  private void newPum(String message) throws IOException {
    PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.NOTIFICATION);
    pum.show();
  }

  protected void rackRemoveTile(int pos) {
    ArrayList<Player> players = Data.getGameSession().getListOfPlayers();

    Iterator<Player> it = players.iterator();
    while (it.hasNext()) {
      Player p = it.next();
      String s = p.getName();
      if (s.equals(Data.getCurrentUser())) {
        p.getRack().removeTileFromRack(pos);
        p.getRack().fill(Data.getGameSession().getBag());
      } else {
        continue;
      }
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
    if (iv.getImage().getUrl().contains("endButton")) {
      if (endPossible) {
        iv.setOpacity(1);
      }
    } else if (iv.getImage().getUrl().contains("undo")) {
      if (undoButton.getOpacity() == 1) {
        iv.setOpacity(1);
      } else {
        darken(event);
      }
    } else {
      iv.setOpacity(1);
    }
  }

  protected void aiTurn() {
    Data.getGameSession().isAiFirstTurn();
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

  @FXML
  private void endGame(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    if (endPossible) {
      Data.getGameSession().setShouldEnd(true);
      Data.getGameSession().endGame();
      if (Data.getGameSession().isOnline()) {
        App.setRoot("EndGameMultiplayer");
      } else {
        App.setRoot("EndGameSingleplayer");
      }
    }
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

  /**
   * @param event
   * @throws IOException
   * @author mherre
   */
  @FXML
  private void closeGame(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    PopUpMessage pum =
        new PopUpMessage("Really!?\nEvery progress is lost and there is no way back....",
            PopUpMessageType.CONFIRMATION);
    pum.show();
    if (Data.isConfirmed()) {
      Data.getGameSession().endGame();
      if (Data.getGameSession().isOnline()) {
        Data.getPlayerClient().disconnectFromServer();
      }
      Data.getGameSession().setShouldEnd(true);
      App.setRoot("MainMenu");
    }
  }



}

