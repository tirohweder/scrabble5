package com.scrab5.ui;

import com.scrab5.core.game.Rack;
import com.scrab5.core.game.Tile;
import com.scrab5.core.player.Player;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
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

/**
 * The InGameController class handels everything that happens during playing the game. Plus is
 * needed for the EndGame-/ Singleplayer-/ Multiplayer-/ ExchangeController
 *
 * @author apilgrim
 */
public abstract class InGameController implements Initializable {

  private static final double LABEL_X_CORD = 30.0;
  private static final double LABEL_Y_CORD = 23.0;
  private static final double LABEL_X_CORD_BACK = 46.0;
  private static final double LABEL_Y_CORD_BACK = 44.0;
  private final ArrayList<Player> players = Data.getGameSession().getListOfPlayers();
  private final int playerAmount = players.size();
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
  Boolean[] rackChanges = new Boolean[7];
  private boolean tileClicked = false;
  private boolean letterClicked = false;
  private ImageView clickedTile;
  private ImageView clickedLetter;
  private Label clickedLabel;
  private Image markedTile;
  private boolean endPossible = false;
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
  @FXML
  private Label bagSize;
  @FXML
  private Label rounds;
  private int rackClicked;
  private ArrayList<ImageView> changes = new ArrayList<>();
  private ArrayList<Integer> jokerPlacedAt = new ArrayList<>();
  private ArrayList<Integer> tilePlacedOrder = new ArrayList<>();

  private boolean chooseJoker = false;
  private boolean turn = true;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    initGameboard();
  }

  // init section

  /**
   * initPlayers() initializes the points, names and active/ passive profiles of the ingame (thru
   * opacity/ text setting of Labels and ImageViews).
   *
   * @author apilgrim
   */
  protected void initPlayers() {

    if (0 < playerAmount) {
      pointsPlayer1
          .setText(Integer.toString(Data.getGameSession().getListOfPlayers().get(0).getPoints()));
      player1.setText(players.get(0).getName());
      if (players.get(0).getName()
          .equalsIgnoreCase(Data.getGameSession().getCurrentPlayer().getName())) {
        playerProfile1Passive.setOpacity(0);
      }
    }
    if (1 < playerAmount) {
      player2.setText(players.get(1).getName());
      player2.setOpacity(1);
      pointsPlayer2
          .setText(Integer.toString(Data.getGameSession().getListOfPlayers().get(1).getPoints()));
      pointsPlayer2.setOpacity(1);
      playerProfile2Active.setOpacity(1);
      playerProfile2Passive.setOpacity(1);
      if (players.get(1).getName()
          .equalsIgnoreCase(Data.getGameSession().getCurrentPlayer().getName())) {
        playerProfile1Passive.setOpacity(0);
      }
    }
    if (2 < playerAmount) {
      player3.setText(players.get(2).getName());
      player3.setOpacity(1);
      pointsPlayer3
          .setText(Integer.toString(Data.getGameSession().getListOfPlayers().get(2).getPoints()));
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

  /**
   * initButtons() initializes the undo/ skip/play / end Game Buttons and Labels of the in game
   * (thru opacity/ text setting of Labels and ImageViews). Checks also the turn and displays a
   * message when it's your turn.
   *
   * @author apilgrim
   */
  protected void initButtons() throws IOException {

    if (changes.size() != 0) {
      undoLabel.setOpacity(1);
      undoButton.setOpacity(1);
    } else {
      undoLabel.setOpacity(0);
      undoButton.setOpacity(0);
    }

    rounds.setText("Rounds played:  "
        + Data.getGameSession().getRoundNumber() / Data.getGameSession().getListOfPlayers().size());

    if (turn && Data.getGameSession().getCurrentPlayer().getName()
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
    } else {
      endGame.setOpacity(0);
      endPossible = false;
    }
  }

  /**
   * initRack() initializes the ui rack representation in the ingame (thru opacity/ text setting of
   * Labels and ImageViews rackplace1-7 and pointsRack1-7). Checks which rack is yours and gets all
   * informations for each tile from core.game Rack.java.
   *
   * @author apilgrim
   */
  protected void initRack() {
    Rack myRack = null;

    for (Player p : Data.getGameSession().getListOfPlayers()) {
      String s = p.getName();
      if (s.equalsIgnoreCase(Data.getCurrentUser())) {
        myRack = p.getRack();
      }
    }

    for (int i = 0; i < 7; i++) {
      switch (i) {
        case 0:
          assert myRack != null;
          if (myRack.getTileAt(i) != null) {
            setNewTile(rackPlace1, pointsRack1, myRack.getTileAt(i).getLetter(),
                myRack.getTileAt(i).getValue());
          } else {
            rackPlace1.setOpacity(0);
            pointsRack1.setOpacity(0);
          }
          break;
        case 1:
          if (myRack.getTileAt(i) != null) {
            setNewTile(rackPlace2, pointsRack2, myRack.getTileAt(i).getLetter(),
                myRack.getTileAt(i).getValue());
          } else {
            rackPlace2.setOpacity(0);
            pointsRack2.setOpacity(0);
          }
          break;
        case 2:
          if (myRack.getTileAt(i) != null) {
            setNewTile(rackPlace3, pointsRack3, myRack.getTileAt(i).getLetter(),
                myRack.getTileAt(i).getValue());
          } else {
            rackPlace3.setOpacity(0);
            pointsRack3.setOpacity(0);
          }
          break;
        case 3:
          if (myRack.getTileAt(i) != null) {
            setNewTile(rackPlace4, pointsRack4, myRack.getTileAt(i).getLetter(),
                myRack.getTileAt(i).getValue());
          } else {
            rackPlace4.setOpacity(0);
            pointsRack4.setOpacity(0);
          }
          break;
        case 4:
          if (myRack.getTileAt(i) != null) {
            setNewTile(rackPlace5, pointsRack5, myRack.getTileAt(i).getLetter(),
                myRack.getTileAt(i).getValue());
          } else {
            rackPlace5.setOpacity(0);
            pointsRack5.setOpacity(0);
          }
          break;
        case 5:
          if (myRack.getTileAt(i) != null) {
            setNewTile(rackPlace6, pointsRack6, myRack.getTileAt(i).getLetter(),
                myRack.getTileAt(i).getValue());
          } else {
            rackPlace6.setOpacity(0);
            pointsRack6.setOpacity(0);
          }
          break;
        case 6:
          if (myRack.getTileAt(i) != null) {
            setNewTile(rackPlace7, pointsRack7, myRack.getTileAt(i).getLetter(),
                myRack.getTileAt(i).getValue());
          } else {
            rackPlace7.setOpacity(0);
            pointsRack7.setOpacity(0);
          }
          break;
        default:
          break;
      }
    }
  }

  /**
   * Initializes the gameboard in the in game (thru opacity/ text setting of Labels and ImageViews).
   * Therefore it checks every field in the core.game gameboard, if a letter is placed and if so,
   * set there an image file with the letter plus the points. Is called after every turn of a
   * player, to check what is new on the board.
   *
   * @author apilgrim
   */
  protected void initGameboard() {

    double layoutX = 263.0;
    double layoutY = 53.0;

    bagSize.setText("Bag size: " + Data.getGameSession().getBag().getSize());

    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        if (Data.getGameSession().getGameBoard().getPlayedTile(i, j) != null) {
          ImageView letterImage = new ImageView();
          String letter = Data.getGameSession().getGameBoard().getPlayedTile(i, j).getLetter();

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
          String points =
              Integer.toString(Data.getGameSession().getGameBoard().getPlayedTile(i, j).getValue());
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
   * This method is called when a field on the board is clicked and if it's the players turn. It
   * checks 1. if no other tile is clicked and the place isn't taken, then it is marked thru the
   * opacity or if a letter is clicked as well, the Letter is placed on the gameboard. 3. last if
   * the same tile has been clicked before it is unclicked an reseted from the clickedTile.
   *
   * @author apilgirm
   * @param event - MouseEvent created when a field is clicked
   * @throws IOException when you are not the current player or the tile is not correct placed
   */
  @FXML
  private void fieldClicked(MouseEvent event) throws IOException {
    playSound();

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
                + "\nRemember to place youre letter tiles in the "
                + "same row OR column (per round)!");
          }
        } else if (clickedTile == event.getSource()) {
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
   * Method which is called when the rack Place One is clicked and checks (if it is your turn) 1. if
   * another letter is clicked/marked 2. if not, it checks if a destination tile is already marked
   * on the field and therefore is replaced with this letter (Image set plus opacity on 0 to remove
   * from rack), otherwise it is marked and is locked in the clicked Letter attribute. Or 3. if
   * another letter has been clicked with no destination, the clickedLetter is switched to the new
   * one. Least, if it has been selected before, it is unclicked.
   *
   * @author apilgirm
   * @param event - MouseEvent created when the rackPlace 1 is clicked
   * @throws IOException - thrown when an Error occurs handling the PopUp Message
   */
  @FXML
  private void rackPlace1clicked(MouseEvent event) throws IOException {
    playSound();
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
          placeLetter(clickedLetter, pointsRack1);
          rackPlace1.setOpacity(0);
          clickedTile = null;
          tileClicked = false;
        } else {
          newPum("Sorry, the tile can't be placed here"
              + "\nRemember to place youre letter tiles in the same row OR column "
              + "(per round)!");
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
   * This method is called when rackPlace2 is clicked. It checks different options to handle the
   * clickedLetter in rackPlace2 like in rackPlace1Clicked and handles it the same way.
   *
   * @author apilgirm
   * @param event - MouseEvent created when the rackPlace2 is clicked
   * @throws IOException - thrown when an Error occurs handling the PopUp Message
   */
  @FXML
  private void rackPlace2clicked(MouseEvent event) throws IOException {
    playSound();
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
          placeLetter(clickedLetter, pointsRack2);
          rackPlace2.setOpacity(0);
          clickedTile = null;
          tileClicked = false;
        } else {
          newPum("Sorry, the tile can't be placed here."
              + "\nRemember to place youre letter tiles in the same row"
              + " OR column (per round)!");
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
   * This method is called when rackPlace3 is clicked. It checks different options to handle the
   * clickedLetter in rackPlace3 like in rackPlace1Clicked and handles it the same way.
   *
   * @author apilgirm
   * @param event - MouseEvent created when the rackPlace3 is clicked
   * @throws IOException - thrown when an Error occurs handling the PopUp Message
   */
  @FXML
  private void rackPlace3clicked(MouseEvent event) throws IOException {
    playSound();
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
          placeLetter(clickedLetter, pointsRack3);
          rackPlace3.setOpacity(0);
          clickedTile = null;
          tileClicked = false;
        } else {
          newPum("Sorry, the tile can't be placed here."
              + "\nRemember to place youre letter tiles in the same row "
              + "OR column (per round)!");
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
   * This method is called when rackPlace4 is clicked. It checks different options to handle the
   * clickedLetter in rackPlace4 like in rackPlace1Clicked and handles it the same way.
   *
   * @author apilgirm
   * @param event - MouseEvent created when the rackPlace4 is clicked
   * @throws IOException - thrown when an Error occurs handling the PopUp Message
   */
  @FXML
  private void rackPlace4clicked(MouseEvent event) throws IOException {
    playSound();
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
          placeLetter(clickedLetter, pointsRack4);
          rackPlace4.setOpacity(0);
          clickedTile = null;
          tileClicked = false;
        } else {
          newPum("Sorry, the tile can't be placed here."
              + "\nRemember to place youre letter tiles in the same row OR "
              + "column (per round)!");
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
   * This method is called when rackPlace5 is clicked. It checks different options to handle the
   * clickedLetter in rackPlace5 like in rackPlace1Clicked and handles it the same way.
   *
   * @author apilgirm
   * @param event - MouseEvent created when the rackPlace5 is clicked
   * @throws IOException - thrown when an Error occurs handling the PopUp Message
   */
  @FXML
  private void rackPlace5clicked(MouseEvent event) throws IOException {
    playSound();
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
          placeLetter(clickedLetter, pointsRack5);
          rackPlace5.setOpacity(0);
          clickedTile = null;
          tileClicked = false;
        } else {
          newPum("Sorry, the tile can't be placed here."
              + "\nRemember to place youre letter tiles in the same r"
              + "ow OR column (per round)!");
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
   * This method is called when rackPlace6 is clicked. It checks different options to handle the
   * clickedLetter in rackPlace6 like in rackPlace1Clicked and handles it the same way.
   *
   * @author apilgirm
   * @param event - MouseEvent created when the rackPlace6 is clicked
   * @throws IOException - thrown when an Error occurs handling the PopUp Message
   */
  @FXML
  private void rackPlace6clicked(MouseEvent event) throws IOException {
    playSound();

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
          placeLetter(clickedLetter, pointsRack6);
          rackPlace6.setOpacity(0);
          clickedTile = null;
          tileClicked = false;
        } else {
          newPum("Sorry, the tile can't be placed here."
              + "\nRemember to place youre letter tiles in the same row OR col"
              + "umn (per round)!");
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
   * This method is called when rackPlace7 is clicked. It checks different options to handle the
   * clickedLetter in rackPlace7 like in rackPlace1Clicked and handles it the same way.
   *
   * @author apilgirm
   * @param event - MouseEvent created when the rackPlace7 is clicked
   * @throws IOException - thrown when an Error occurs handling the PopUp Message
   */
  @FXML
  private void rackPlace7clicked(MouseEvent event) throws IOException {
    playSound();

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
          newPum("Sorry, the tile can't be placed here."
              + "\nRemember to place youre letter tiles in the same row OR column (p"
              + "er round)!");
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
   * Method which is called when the "play/skip" button is clicked. Checks if it's your turn and if
   * the first tile is placed in the center. Next it checks, if something has been laid, the word
   * laid is correct (checking in core.game). If not a PopUpMessage is created. Otherwise the points
   * are calculated and the letters placed are removed from the rack. Then the placed letters are
   * returned to the rack and are reinitialized. All clicked attributes are reset and the turn is
   * finished by calling the finishTurn method of core.game. At least it is checked, if the bag and
   * rack still contains enough letters to continue.
   *
   * @author apilgirm
   * @param event - MouseEvent created when the "play/ skip" button is clicked
   * @throws IOException - thrown when an Error occurs handling the PopUp Message
   */
  @FXML
  private void playClicked(MouseEvent event) throws IOException {
    playSound();

    ArrayList<Tile> currentChanges = Data.getGameSession().getGameBoard().getCurrentChanges();

    Iterator<Tile> it = currentChanges.iterator();
    Tile check;
    boolean notPlaceable = false;

    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {
      while (it.hasNext()) {
        check = it.next();

        if (check.getColumn() == 7 && check.getRow() == 7 && currentChanges.size() < 2) {
          notPlaceable = true;
        }
      }

      if (Data.getGameSession().getGameBoard().getCurrentChanges().size() > 0) {
        if (!notPlaceable) {
          if (Data.getGameSession().getGameBoard().checkWordsLegit()) {

            // increase corrected placed word attirbute of the player laid the word
            Data.getGameSession().setSkippedTurn(0);
            Data.getGameSession().getCurrentPlayer()
                .setCorrectWords(Data.getGameSession().getCurrentPlayer().getCorrectWords()
                    + (Data.getGameSession().getGameBoard().getWords().size()
                        - Data.getGameSession().getLastWordsSize()));

            int points = Data.getGameSession().getGameBoard().countScore();

            String message = "Congrats you scored: " + points;
            Data.getGameSession().getCurrentPlayer()
                .setPoints(Data.getGameSession().getCurrentPlayer().getPoints() + points);
            PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.ERROR);
            pum.show();

            Data.getGameSession().checkBagAndRack(Data.getGameSession().getCurrentPlayer());

            // reset Opacity on the Rag Board if not null and remove tiles from rack
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

            // returns the laid letter imageView attributes back to the rack
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

            // reset the clicked attributes
            letterClicked = false;
            tileClicked = false;
            clickedLetter = null;
            clickedTile = null;
            turn = true;
            changes.clear();

            // re initialize rack and the gamboard
            initRack();
            initPlayers();

            Data.getGameSession().checkBagAndRack(Data.getGameSession().getCurrentPlayer());

            // finish turn
            Data.getGameSession().finishTurn();

          } else {
            // wrong word
            String message = "The word placed isnt legit!";
            PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.NOTIFICATION);
            pum.show();
          }
        } else {
          // first word min length handling
          newPum("The first placed word must\nhave a minimum length of two!");
        }
      } else {
        // skip turn
        turn = true;
        Data.getGameSession().checkBagAndRack(Data.getGameSession().getCurrentPlayer());
        Data.getGameSession().setSkippedTurn(Data.getGameSession().getSkippedTurn() + 1);
        Data.getGameSession().finishTurn();
      }
    } else {
      PopUpMessage pum =
          new PopUpMessage("Sorry, you're not the current Player", PopUpMessageType.NOTIFICATION);
      pum.show();
    }
  }

  /**
   * This method is called when "shuffle" button is clicked. It checks which letters is still in the
   * rack and add it to the ArrayList order. It checks which is the rack of the current user and
   * then the shuffleRack method in core.game.Rack.java is called. After this the ui rack is
   * reinitialized.
   *
   * @author apilgirm
   * @param event - MouseEvent created when the "shuffle" - button is clicked
   */
  @FXML
  private void shuffleClicked(MouseEvent event) {
    playSound();

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

    ArrayList<Player> players = Data.getGameSession().getListOfPlayers();

    for (Player p : players) {
      String s = p.getName();
      if (s.equals(Data.getCurrentUser())) {
        p.getRack().shuffleRack(order);
        initRack();
      }
    }
  }

  /**
   * This method is called when "exchange" button is clicked. It checks which letters is selected
   * from the rack to get exchanged. Handled in the ExchangeController called thru the
   * Exchange.fxml. If not enough tiles are left, letters in your turn already laid or its not your
   * turn, a PopUpMessage is displayed with an error.
   *
   * @author apilgirm
   * @param event - MouseEvent created when the "exchange" - button is clicked
   * @throws IOException - thrown when an Error occurs handling the PopUp Message or the fxml file
   *         doesnt exists
   */
  @FXML
  private void exchangeClicked(MouseEvent event) throws IOException {
    playSound();
    if (Data.getGameSession().getGameBoard().isAllowedToPlay()) {
      if ((Data.getGameSession().getGameBoard().getCurrentChanges().size() == 0)) {
        if (Data.getGameSession().getBag().getSize() > 7) {
          App.setRoot("Exchange");
        } else {
          newPum("Sorry there are not enough tiles left in the bag");
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

  /**
   * This method is called when a letter from the JokerPane is choosen for the laid joker.
   *
   * @author apilgirm
   * @param event - MouseEvent created when the "exchange" - button is clicked
   * @throws IOException - thrown when an Error occurs handling the PopUp Message
   */
  @FXML
  private void jokerLetterChoosen(MouseEvent event) throws IOException {
    playSound();
    ImageView iv = (ImageView) event.getSource();

    if (chooseJoker) {
      String letterJoker = iv.getId();
      setNewTile(clickedLetter, clickedLabel, letterJoker, 0);

      if (Data.getGameSession().getGameBoard().placeTile(new Tile(letterJoker, 0),
          rowTransformation(clickedTile.getId()), columnTransformation(clickedTile.getId()))) {
        chooseJoker = false;
        changes.add(clickedTile);
        placeLetter(clickedLetter, clickedLabel);
        jokerPlacedAt.add(changes.size() - 1);
      } else {
        newPum("Sorry, the tile can't be placed here."
            + "\nRemember to place youre letter tiles in the same row OR column (per round)!");
      }

      jokerPane.setOpacity(0);
    }
  }

  /**
   * This method is called when "give up" - button is clicked. If confirmed, the end game screen is
   * displayed.
   *
   * @author apilgirm
   * @param event - MouseEvent created when the "give up" - button is clicked
   * @throws IOException - thrown when an Error occurs handling the PopUp Message or the fxml file
   *         exists
   */
  @FXML
  private void giveUpClicked(MouseEvent event) throws IOException {
    if (!((Data.getGameSession().getRoundNumber()
        / Data.getGameSession().getListOfPlayers().size()) < 1)) {
      playSound();
      PopUpMessage pum = new PopUpMessage("Really!?", PopUpMessageType.CONFIRMATION);
      pum.show();
      if (Data.isConfirmed()) {
        Iterator<Player> it = Data.getGameSession().getListOfPlayers().iterator();
        Player p;
        while (it.hasNext()) {
          p = it.next();
          if (p.getName().equals(Data.getCurrentUser())) {
            p.setGivenUp(true);
          }
        }
        if (Data.getGameSession().isOnline()) {
          // this is sent so all players know who gave up
          Data.getPlayerClient().makeTurn();
          Data.getPlayerClient().endGame();
        } else {
          Data.getGameSession().setShouldEnd(true);
          Data.getGameSession().endGame();
          App.setRoot("EndGameSingleplayer");
        }
        changes.clear();
        Data.getGameSession().setSkippedTurn(0);
      }
    } else {
      newPum("You can't give up in the first round!");
    }
  }

  /**
   * This method is called when "undo" - button is clicked. The last laid letter will be returned to
   * the rack.
   *
   * @author apilgirm
   * @param event - MouseEvent created when the "undi" - button is clicked
   */
  @FXML
  private void undoClicked(MouseEvent event) {
    if (undoButton.getOpacity() == 1) {
      playSound();
      if (changes.size() > 0) {
        backToRack(changes.get(changes.size() - 1),
            tilePlacedOrder.get(tilePlacedOrder.size() - 1));
      }
    }
  }

  // help methods

  /**
   * This method is called when the rack is initialized. It gets the image file from letter_images
   * and lay the correct points as label on it. So creating the UI representation of the rackplaces.
   *
   * @author apilgirm
   * @param rackPlace - ImageView of the rackplace being initialized, point - Label representing the
   *        points of the rackplace being initialized, letter - String the letter for the rackplace,
   *        points - Integer the value of the letter
   */
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
    if (!Integer.toString(points).equals("0") | !letter.contains("placeHolder")) {
      point.setText(Integer.toString(points));
      point.setOpacity(1);
    } else {
      point.setOpacity(0);
    }
  }

  // Joker handling

  /**
   * This method is called when the clicked Letter is a joker. Opens the jokerPane to choose a
   * letter for the joker.
   *
   * @author apilgirm
   * @throws IOException - when new PopUpMessage is created and throws an error
   */
  private void setJoker() throws IOException {
    jokerPane.setOpacity(1);
    chooseJoker = true;
    newPum("Choose a letter on the left\n the Joker stands for!");
  }

  /**
   * Help method to unclick all rackplaces faster. Sets opacity of all rackplaces on 1.
   *
   * @author apilgirm
   */
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

  /**
   * Help method to unclick the clickedLetter and reset the attributes for the clicked letter.
   *
   * @author apilgirm
   * @param rackPlace - ImageView which is unclicked
   */
  private void unclickLetter(ImageView rackPlace) {
    letterClicked = false;
    clickedLetter = null;
    clickedLabel = null;
    rackPlace.setOpacity(1);
  }

  /**
   * Help method to switch the clickedLetter and switch the attributes for the clicked letter.
   *
   * @author apilgirm
   * @param rackPlace - ImageView which is unclicked, points - Label representing the points for the
   *        letter which needs to be switched
   */
  private void switchClickedLetter(ImageView rackPlace, Label points) {
    clickedLetter = rackPlace;
    clickedLabel = points;
    unclickAll();
    rackPlace.setOpacity(0.8);
  }

  /**
   * This method is called when the "undo" button is clicked and brings back the letter to the ui
   * rack. It changes the Image on the Board back to the marked Tile (black square) and brings the
   * Letter from the Board back to the rack thru the opacity and resets the clicked attributes
   * (Letter/ Tile) for source and destination. Checks the two options if the letter is a joker to
   * return or a normal letter by checking arraylists with the order when the letter has been laid.
   *
   * @author apilgirm
   * @param iv field which contained a letter returned, int place - which rackplace is returned
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
    iv.setOpacity(0);
    clickedTile = null;
    tileClicked = false;
    letterClicked = false;
    changes.remove(changes.size() - 1);
  }

  /**
   * This method is called when a destination Tile is clicked on the GameBoard and a Letter Tile is
   * selected. It changes the Image on the Board and "deletes" the Letter from the Board thru the
   * opacity and resets the boolean clicked attributes (Letter/ Tile) for source and destination,
   *
   * @author apilgirm
   * @param iv field which contained a letter returned, int place - which rackplace is returned
   */
  private void placeLetter(ImageView iv, Label l) {

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
   * Help method to transform the id of the field into the row int representation for the gameboard
   * placement.
   *
   * @author apilgrim
   * @param placeId - String representation of the coordinate from every tile on the board read from
   *        the fxml document as ID
   * @return x - Integer representation of the x coordinate for the tile, placed on the Gameboard
   */
  private int rowTransformation(String placeId) {

    // check if column number
    char xcord = placeId.charAt(0);
    return ((int) xcord) - 97;
  }

  /**
   * Help method to transform the id of the field into the column int representation for the
   * gameboard placement.
   *
   * @author apilgrim
   * @param placeId - String representation of the coordinate from every tile on the board read from
   *        the fxml document as ID
   * @return x - Integer representation of the y coordinate for the tile, placed on the Gameboard
   */
  private int columnTransformation(String placeId) {

    char ycord = placeId.charAt(1);
    int y = ((int) ycord) - 48;

    // if length of the String s is two, there is only the row and column number of length one ->
    // nothing to
    // check
    if (placeId.length() > 2) {

      // if length is > 2 and the 3. char is a number, the column number has the length of two
      if (47 < ((int) placeId.charAt(2)) && ((int) placeId.charAt(2)) < 58) {
        char ycord2 = placeId.charAt(2);
        int y2 = ((int) ycord2) - 48;
        y *= 10;
        y += y2;
      }
    }
    return y - 1;
  }

  /**
   * Method that plays a sound file and adjusts the volume to the volume that has been set by the
   * user in the "Settings.fxml" scene.
   *
   * @author mherre
   */
  protected void playSound() {
    Media sound = new Media(Objects
        .requireNonNull(
            Controller.class.getResource("/com/scrab5/ui/sound_effects/" + "ButtonClicked.mp3"))
        .toExternalForm());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.setVolume(Data.getSFXVolume());
    mediaPlayer.play();
  }

  /**
   * Method to create a new popupmessage.
   *
   * @author apilgrim
   * @param message for the PopUpMessage
   */
  private void newPum(String message) throws IOException {
    PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.NOTIFICATION);
    pum.show();
  }

  /**
   * Method to remove the tile from the rack at the position i.
   *
   * @author apilgrim
   * @param pos of the tile
   */
  protected void rackRemoveTile(int pos) {
    ArrayList<Player> players = Data.getGameSession().getListOfPlayers();

    for (Player p : players) {
      String s = p.getName();
      if (s.equals(Data.getCurrentUser())) {
        p.getRack().removeTileFromRack(pos);
        p.getRack().fill(Data.getGameSession().getBag());
      }
    }
  }

  /**
   * method to set the opacity on 1 and let it looks like the field/ button is entered but checks
   * first that it isnt a letter already placed or the marked field with the square.
   *
   * @author apilgirm
   * @param event - MouseEvent created when image is entered or released after clicking.
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

  /**
   * Method to check if the first turn is from the ai.
   *
   * @author apilgirm
   * @throws IOException - when handling went wrong
   */
  protected void aiTurn() throws IOException {
    Data.getGameSession().isAiFirstTurn();
  }

  /**
   * method to set the opacity on zero and let it looks like the field/ button is excited but checks
   * first that it isnt a letter already placed or the marked field with the square.
   *
   * @author apilgirm
   * @param event - MouseEvent created when image is excited or pressed.
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
   * Method to check if the end is possible after 6 skipped turns. If end game is clicked the game
   * is ended and the end game screen is displayed.
   *
   * @author apilgirm
   * @throws IOException - when file fxml is not found.
   */
  @FXML
  private void endGame(MouseEvent event) throws IOException {
    playSound();
    if (endPossible) {
      if (Data.getGameSession().isOnline()) {
        Data.getPlayerClient().endGame();
      } else {
        Data.getGameSession().setShouldEnd(true);
        Data.getGameSession().endGame();
        App.setRoot("EndGameSingleplayer");
      }
    }
  }

  /**
   * Method checking whos players turn it is. Setting the opacity of the profile images in the top
   * container.
   *
   * @author apilgrim
   */
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
   * Modified method from @mherre to end a game when exit the game. Checks if online or offline and
   * ends the game if exit is confirmed.
   *
   * @author mherre @author apilgrim
   * @param event MouseEvent - created when "Exit" button is clicked
   * @throws IOException thrown when fxml file doesnt exist or PopUpMessage occured an error
   */
  @FXML
  private void closeGame(MouseEvent event) throws IOException {
    playSound();
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
