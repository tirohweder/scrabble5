package com.scrab5.ui;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;
import com.scrab5.network.Client;
import com.scrab5.network.NetworkError;
import com.scrab5.network.NetworkError.NetworkErrorType;
import com.scrab5.network.ServerData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * The MultiPlayerOverviewController class controls the components of the
 * "MultiplayerOverview.fxml".
 * 
 * @author mherre
 */
public class MultiplayerOverviewController extends Controller implements Initializable {

  @FXML
  private Label userPlaying, dicDisplaying;
  @FXML
  private ImageView playerNumber, arrowRight, arrowLeft;
  @FXML
  private ComboBox<String> dictionarySelection;
  @FXML
  private TextField serverName;
  @FXML
  private ImageView dropDownButton;
  @FXML
  private ImageView joinButton0, joinButton1, joinButton2, joinButton3, joinButton4, joinButton5,
      joinButton6, joinButton7;
  @FXML
  private Label serverName0, serverName1, serverName2, serverName3, serverName4, serverName5,
      serverName6, serverName7;
  @FXML
  private Label playerCount0, playerCount1, playerCount2, playerCount3, playerCount4, playerCount5,
      playerCount6, playerCount7;
  @FXML
  private Label status0, status1, status2, status3, status4, status5, status6, status7;

  private boolean isDictionarySelected = false;
  private int playerCount = 2;

  /**
   * Call certain methods as soon as the Controller is loaded. Sets up the labels in the UI and
   * starts searching for servers.
   * 
   * @author mherre
   * @author nitterhe
   */
  public void initialize(URL arg0, ResourceBundle arg1) {
    this.serverName.setFocusTraversable(false);
    this.userPlaying.setText(Data.getCurrentUser());
    this.setUpDicitionaryBox();

    if (Data.getPlayerClient() == null) {
      Data.setPlayerClient(new Client(Data.getCurrentUser()));
    } else if (!Data.getPlayerClient().getUsername().equals(Data.getCurrentUser())) {
      Data.getPlayerClient().setUsername(Data.getCurrentUser());
    }
    this.searchServers();
  }

  /**
   * Event method that is called when the "Start"-button in the UI is clicked. If the server
   * settings are set up properly a new server gets created and scene gets changed to
   * "MultiplayerLobby.fxml".
   * 
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void start(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");

    // if (serverName.getText().isEmpty()) {
    // String message = "You must enter a server name to continue";
    // PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.ERROR);
    // pum.show();
    // } else {
    // App.setRoot("MultiplayerLobby");
    // }
    if (this.isDictionarySelected) {

      Data.setIsSearching(false);
      Data.setPlayerCountMultiplayer(playerCount);
      try {
        this.setupServer(playerCount);
        App.setRoot("MultiplayerLobby");
      } catch (Exception e) {
        new NetworkError(NetworkErrorType.SERVERCREATION);
      }

    } else {

      String message = "To start the game please select a dictionary!";
      PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.ERROR);
      pum.show();

    }
  }

  /**
   * Event method that is called when the "Back"-button in the UI is clicked. Scene gets changed to
   * the predecessor "MainMenu.fxml" scene.
   * 
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void back(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    Data.setIsSearching(false);
    App.setRoot("MainMenu");
  }

  /**
   * Event method that is called when the "Right Arrow"-button in the UIis clicked. The displayed
   * number gets increased by one. Changes "Right Arrow"-button to a grayed out image if
   * <code>playerCount</code> equals 4, this can't be clicked anymore.
   * 
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void nextNumber(MouseEvent event) {
    playerCount++;
    playSound("ButtonClicked.mp3");

    if (playerCount > 4) {
      playerCount--;

    } else if (playerCount == 4) {
      this.changeNumberImage();
      this.arrowRight.setLayoutY(458);
      this.arrowRight.setOpacity(1);

    } else if (playerCount > 2 && this.arrowLeft.getOpacity() == 1) {
      this.changeNumberImage();
      this.arrowLeft.setLayoutY(500);
      this.arrowLeft.setOpacity(0);

    } else {
      this.changeNumberImage();

    }
  }

  /**
   * Event method that is called when the "Left Arrow"-button in the UIis clicked. The displayed
   * number gets decreased by one. Changes "Left Arrow"-button to a grayed out image if
   * <code>playerCount</code> equals 2, this can't be clicked anymore.
   * 
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void previousNumber(MouseEvent event) {
    playerCount--;
    playSound("ButtonClicked.mp3");

    if (playerCount < 2) {
      playerCount++;

    } else if (playerCount == 2) {
      this.changeNumberImage();
      this.arrowLeft.setLayoutY(458);
      this.arrowLeft.setOpacity(1);

    } else if (playerCount < 4 && this.arrowRight.getOpacity() == 1) {
      this.changeNumberImage();
      this.arrowRight.setLayoutY(500);
      this.arrowRight.setOpacity(0);

    } else {
      this.changeNumberImage();

    }
  }

  /**
   * Event method that is called when the "Arrow Down"-button in the UI is clicked. Opens the
   * <code>ComboBox dictionarySelection</code> displaying all available dictionaries.
   * 
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void clickComboBox(MouseEvent event) {
    playSound("ButtonClicked.mp3");
    dictionarySelection.show();

  }

  /**
   * Event method that is called when the "Refresh"-button in the UI is clicked. Starts searching
   * for new servers.
   * 
   * @author mherre
   * @author nitterhe
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void refresh(MouseEvent event) {
    playSound("ButtonClicked.mp3");
    Data.setIsSearching(false);
    this.searchServers();
  }

  /**
   * Event method that is called when the "Find Game"-button in the UI is clicked. Puts the player
   * into a random selected lobby that is displayed in the server list if there is at least one.
   * Otherwise an error message is created.
   * 
   * @author mherre
   * @author nitterhe
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void findGame(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    if (!Data.getServerList().isEmpty()) {
      String ip4 = Data.getServerList().get((int) (Data.getServerList().size() * Math.random()))
          .getIP4Address();
      if (joinServer(ip4)) {
        App.setRoot("MultiplayerLobby");
      }
    } else {
      new NetworkError(NetworkErrorType.NOSERVERFOUND);
    }
  }

  /**
   * Event method that is called when <code>dictionarySelection</code> in the UI is clicked. Makes
   * dictionarySelection only openable when the dropDownButton is clicked.
   * 
   * @author mherre
   * @param event the event that is created from the mouse-click
   */
  @FXML
  private void dontShow(MouseEvent event) {
    dictionarySelection.hide();
  }

  /**
   * Event method that is called when the "Join"-button in the UI is clicked. Only joins if server 0
   * is actually shown.
   * 
   * @author nitterhe
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void join0(MouseEvent event) throws IOException {
    if (this.joinButton0.getOpacity() == 1.0)
      this.joinGame(0);
  }

  /**
   * Event method that is called when the "Join"-button in the UI is clicked. Only joins if server 1
   * is actually shown.
   * 
   * @author nitterhe
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void join1(MouseEvent event) throws IOException {
    if (this.joinButton1.getOpacity() == 1.0)
      this.joinGame(1);
  }

  /**
   * Event method that is called when the "Join"-button in the UI is clicked. Only joins if server 2
   * is actually shown.
   * 
   * @author nitterhe
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void join2(MouseEvent event) throws IOException {
    if (this.joinButton2.getOpacity() == 1.0)
      this.joinGame(2);
  }

  /**
   * Event method that is called when the "Join"-button in the UI is clicked. Only joins if server 3
   * is actually shown.
   * 
   * @author nitterhe
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void join3(MouseEvent event) throws IOException {
    if (this.joinButton3.getOpacity() == 1.0)
      this.joinGame(3);
  }

  /**
   * Event method that is called when the "Join"-button in the UI is clicked. Only joins if server 4
   * is actually shown.
   * 
   * @author nitterhe
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void join4(MouseEvent event) throws IOException {
    if (this.joinButton4.getOpacity() == 1.0)
      this.joinGame(4);
  }

  /**
   * Event method that is called when the "Join"-button in the UI is clicked. Only joins if server 5
   * is actually shown.
   * 
   * @author nitterhe
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void join5(MouseEvent event) throws IOException {
    if (this.joinButton5.getOpacity() == 1.0)
      this.joinGame(5);
  }

  /**
   * Event method that is called when the "Join"-button in the UI is clicked. Only joins if server 6
   * is actually shown.
   * 
   * @author nitterhe
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void join6(MouseEvent event) throws IOException {
    if (this.joinButton6.getOpacity() == 1.0)
      this.joinGame(6);
  }

  /**
   * Event method that is called when the "Join"-button in the UI is clicked. Only joins if server 7
   * is actually shown.
   * 
   * @author nitterhe
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void join7(MouseEvent event) throws IOException {
    if (this.joinButton7.getOpacity() == 1.0)
      this.joinGame(7);
  }

  /**
   * Event method that is called when the mouse is hovering over the <code>ImageView</code> object
   * <code>iv</code> or released after it has been clicked. Changes the <code>image</code> to
   * "/com/scrab5/ui/images/SB06_JoinButtonClicked.png".
   * 
   * @author mherre
   * @param event the event that is created from the hovering and releasing
   */
  @FXML
  private void lightenJoinIcon(MouseEvent event) {
    ImageView iv = (ImageView) event.getSource();
    iv.setImage(new Image("/com/scrab5/ui/images/SB06_JoinButtonClicked.png"));
  }

  /**
   * Event method that is called when the mouse is exiting the <code>ImageView</code> object
   * <code>iv</code> or when it is pressed. Changes the <code>image</code> to
   * "/com/scrab5/ui/images/SB06_JoinButton.png".
   * 
   * @author mherre
   * @param event the event that is created from the hovering and releasing
   */
  @FXML
  private void darkenJoinIcon(MouseEvent event) {
    ImageView iv = (ImageView) event.getSource();
    iv.setImage(new Image("/com/scrab5/ui/images/SB06_JoinButton.png"));
  }

  /**
   * Event method that is called when an item in <code>dictionarySelection</code> has been selected.
   * 
   * @author mherre
   * @param event the event that is created when an item has been selected
   */
  @FXML
  private void setSelectedDictionary(ActionEvent event) {
    String selected = (String) this.dictionarySelection.getValue();
    this.dicDisplaying.setText(selected.substring(0, selected.length() - 4));
    isDictionarySelected = true;
  }

  /**
   * Changes the image of the current <code>playerNumber</code> to the fitting
   * <code>playerCount</code>.
   * 
   * @author mherre
   */
  private void changeNumberImage() {
    Image img = null;
    switch (playerCount) {
      case 2:
        img = new Image("/com/scrab5/ui/images/SB04_2.png", 20, 28, false, false);
        break;
      case 3:
        img = new Image("/com/scrab5/ui/images/SB04_3.png", 21, 28, false, false);
        break;
      case 4:
        img = new Image("/com/scrab5/ui/images/SB04_4.png", 23, 28, false, false);
      default:
        break;
    }
    playerNumber.setImage(img);
  }

  /**
   * Method that is called once a player clicked a "Join"-button. If the lobby isn't full yet, then
   * the player joins the lobby and the scene gets changed to "MultiplayerLobby.fxml".
   * 
   * @author nitterhe
   * @author mherre
   * @param number the integer containing the number which "Join"-button has been clicked
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  private void joinGame(int number) throws IOException {
    if (Data.getServerList().size() >= number)
      playSound("ButtonClicked.mp3");
    if (joinServer(Data.getServerList().get(number).getIP4Address()))
      App.setRoot("MultiplayerLobby");
  }

  /**
   * KOOOOOOOOOOOMENTARE NIKLAAAAS; KOOOOOOOOOOOOOOOOOOOOOMENTAAAAAAAAAAAAAAAAAAAAAAARE
   * 
   * @author nitterhe
   * @param playerCount
   */
  private void setupServer(int playerCount) throws Exception {
    Data.getPlayerClient().hostServer(playerCount);
    Data.setHostedServer(Data.getPlayerClient().getHostedServer());
  }

  /**
   * This method is called to setup the <code>ComboBox dictionarySelection</code>, so all inserted
   * dictionaries are getting displayed. The dictionaries must be in the same directory as the .jar.
   * <p>
   * https://stackabuse.com/java-list-files-in-a-directory/
   * </p>
   * 
   * @author mherre
   */
  private void setUpDicitionaryBox() {

    File dir = new File(System.getProperty("user.dir"));
    FilenameFilter filter = new FilenameFilter() {

      public boolean accept(File dir, String name) {
        return name.endsWith(".txt");
      }

    };

    String[] fileNames = dir.list(filter);

    if (fileNames == null) {
      System.out.println("JCOMBO BOX Directory is INCORRECT or does not exist!");
    } else {
      for (int i = 0; i < fileNames.length; i++) {
        String filename = fileNames[i];
        this.dictionarySelection.getItems().add(filename);
      }
    }
  }

  /**
   * "Searches ofr local servers". Actually just refreshes the serverlist from the Client object
   * every 2 seconds. i = 35 since the searchServers method from the class Client takes between 66
   * and 68 seconds. Therefore, after 70 seconds this method can be sure no more servers are in the
   * local network.
   * 
   * @author nitterhe
   */
  private void searchServers() {
    this.clearServerDisplay();
    if (!Data.getIsSearching()) {
      Data.setIsSearching(true);
      Data.getPlayerClient().searchServers();

      Runnable r = new Runnable() {

        public synchronized void run() {
          for (int i = 0; i < 35 && Data.getIsSearching(); i++) {
            Data.setServerList(Data.getPlayerClient().getServerList());
            if (!Data.getServerList().isEmpty()) {
              int j = 0;
              while (j < Data.getServerList().size()) {
                displayServer(Data.getServerList().get(j), j);
                j++;
              }
            }
            try {
              wait(2000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          Data.setIsSearching(false);
        }
      };
      new Thread(r).start();
    }
  }


  /**
   * Joins a server with the given IPAddress.
   * 
   * @author nitterhe
   * @param IPAddress - the IPAddress of the server that should be joined as a String
   */
  private boolean joinServer(String IPAddress) {
    try {
      if (InetAddress.getByName(IPAddress).isReachable(5000)) {
        Data.getPlayerClient().connectToServer(IPAddress);
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * This method is called in {@link com.scrab5.ui.MultiplayerOverviewController#initialize
   * initialize} and starts a <code>Thread</code> that is refreshing the server list at all time. If
   * a new server is found the labels containing the server information are getting displayed, the
   * "Join"-button is shown as well.
   * 
   * @author mherre
   * @author nitterhe
   * @param serverdata contains the information needed to display everything correctly in the server
   *        list
   * @param position the integer containing the position of the server in the server list
   */
  private void displayServer(ServerData serverdata, int position) {
    Platform.runLater(new Runnable() {

      public void run() {

        switch (position) {
          case 0:
            serverName0.setText(serverdata.getServerHost() + "'s Lobby");
            playerCount0
                .setText(serverdata.getClientCounter() + "/" + serverdata.getClientMaximum());
            status0.setText(serverdata.getServerStatus() ? "Playing" : "Waiting");
            joinButton0.setOpacity(1.0);
            break;
          case 1:
            serverName1.setText(serverdata.getServerHost() + "'s Lobby");
            playerCount1
                .setText(serverdata.getClientCounter() + "/" + serverdata.getClientMaximum());
            status1.setText(serverdata.getServerStatus() ? "Playing" : "Waiting");
            joinButton1.setOpacity(1.0);
            break;
          case 2:
            serverName2.setText(serverdata.getServerHost() + "'s Lobby");
            playerCount2
                .setText(serverdata.getClientCounter() + "/" + serverdata.getClientMaximum());
            status2.setText(serverdata.getServerStatus() ? "Playing" : "Waiting");
            joinButton2.setOpacity(1.0);
            break;
          case 3:
            serverName3.setText(serverdata.getServerHost() + "'s Lobby");
            playerCount3
                .setText(serverdata.getClientCounter() + "/" + serverdata.getClientMaximum());
            status3.setText(serverdata.getServerStatus() ? "Playing" : "Waiting");
            joinButton3.setOpacity(1.0);
            break;
          case 4:
            serverName4.setText(serverdata.getServerHost() + "'s Lobby");
            playerCount4
                .setText(serverdata.getClientCounter() + "/" + serverdata.getClientMaximum());
            status4.setText(serverdata.getServerStatus() ? "Playing" : "Waiting");
            joinButton4.setOpacity(1.0);
            break;
          case 5:
            serverName5.setText(serverdata.getServerHost() + "'s Lobby");
            playerCount5
                .setText(serverdata.getClientCounter() + "/" + serverdata.getClientMaximum());
            status5.setText(serverdata.getServerStatus() ? "Playing" : "Waiting");
            joinButton5.setOpacity(1.0);
            break;
          case 6:
            serverName6.setText(serverdata.getServerHost() + "'s Lobby");
            playerCount6
                .setText(serverdata.getClientCounter() + "/" + serverdata.getClientMaximum());
            status6.setText(serverdata.getServerStatus() ? "Playing" : "Waiting");
            joinButton6.setOpacity(1.0);
            break;
          case 7:
            serverName7.setText(serverdata.getServerHost() + "'s Lobby");
            playerCount7
                .setText(serverdata.getClientCounter() + "/" + serverdata.getClientMaximum());
            status7.setText(serverdata.getServerStatus() ? "Playing" : "Waiting");
            joinButton7.setOpacity(1.0);
            break;
          default:
            break;
        }
      }
    });
  }


  /**
   * Resets all 8 server displays if there is at least 1 server displayed.
   * 
   * @author nitterhe
   */
  private void clearServerDisplay() {

    this.serverName0.setText("");
    this.playerCount0.setText("");
    this.status0.setText("");
    this.joinButton0.setOpacity(0);

    this.serverName0.setText("");
    this.playerCount0.setText("");
    this.status0.setText("");
    this.joinButton0.setOpacity(0);

    this.serverName1.setText("");
    this.playerCount1.setText("");
    this.status1.setText("");
    this.joinButton1.setOpacity(0);

    this.serverName1.setText("");
    this.playerCount1.setText("");
    this.status1.setText("");
    this.joinButton1.setOpacity(0);

    this.serverName2.setText("");
    this.playerCount2.setText("");
    this.status2.setText("");
    this.joinButton2.setOpacity(0);

    this.serverName2.setText("");
    this.playerCount2.setText("");
    this.status2.setText("");
    this.joinButton2.setOpacity(0);

    this.serverName3.setText("");
    this.playerCount3.setText("");
    this.status3.setText("");
    this.joinButton3.setOpacity(0);

    this.serverName3.setText("");
    this.playerCount3.setText("");
    this.status3.setText("");
    this.joinButton3.setOpacity(0);

    this.serverName4.setText("");
    this.playerCount4.setText("");
    this.status4.setText("");
    this.joinButton4.setOpacity(0);

    this.serverName4.setText("");
    this.playerCount4.setText("");
    this.status4.setText("");
    this.joinButton4.setOpacity(0);

    this.serverName5.setText("");
    this.playerCount5.setText("");
    this.status5.setText("");
    this.joinButton5.setOpacity(0);

    this.serverName5.setText("");
    this.playerCount5.setText("");
    this.status5.setText("");
    this.joinButton5.setOpacity(0);

    this.serverName6.setText("");
    this.playerCount6.setText("");
    this.status6.setText("");
    this.joinButton6.setOpacity(0);

    this.serverName6.setText("");
    this.playerCount6.setText("");
    this.status6.setText("");
    this.joinButton6.setOpacity(0);

    this.serverName7.setText("");
    this.playerCount7.setText("");
    this.status7.setText("");
    this.joinButton7.setOpacity(0);
  }
}
