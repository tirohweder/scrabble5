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
   * Is called right after the scene is loaded. Sets the displayed username to the username of the
   * current selected user / logged in user.
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
      this.setupServer(playerCount);
      App.setRoot("MultiplayerLobby");

    } else {
      String message = "To start the game please select a dictionary!";
      PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.ERROR);
      pum.show();

    }
  }

  /**
   * 
   * Event method that is called when the "Back"-button is clicked. Scene gets changed to the
   * predecessor "Main Menu" scene
   * 
   * @author mherre @author nitterhe
   * @param event
   * @throws IOException
   */
  @FXML
  private void back(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    Data.setIsSearching(false);
    App.setRoot("MainMenu");
  }

  /**
   * Event method that is called when the "Right Arrow"-button is clicked. The "Number of Players"
   * number gets increased by one.Changes "Right Arrow"-button to a greyed out image of it which
   * can't be clicked.
   * 
   * @author mherre
   * @param event
   * @throws IOException
   */
  @FXML
  private void nextNumber(MouseEvent event) throws IOException {
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
   * Event method that is called when the "Left Arrow"-button is clicked. The "Number of Players"
   * number gets decreased by one. Changes "Left Arrow"-button to a greyed out image of it which
   * can't be clicked.
   * 
   * @author mherre
   * @param event
   * @throws IOException
   * 
   */
  @FXML
  private void previousNumber(MouseEvent event) throws IOException {
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
   *
   * Is called when the "dropDownButton" Button is clicked. Opens the combo box displaying all
   * selectable dictionaries.
   * 
   * @author mherre
   * @param event
   * @throws IOException
   * 
   * 
   */
  @FXML
  private void clickComboBox(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    dictionarySelection.show();

  }

  /**
   * 
   * 
   * @author mherre
   * @param event
   */
  @FXML
  private void refresh(MouseEvent event) {
    playSound("ButtonClicked.mp3");
    Data.setIsSearching(false);
    this.searchServers();
  }

  @FXML
  private void findGame(MouseEvent event) throws IOException {
    playSound("ButtonClicked.mp3");
    String ip4 = Data.getServerList().get((int) (Data.getServerList().size() * Math.random()))
        .getIP4Address();
    if (joinServer(ip4)) {
      App.setRoot("MultiplayerLobby");
    } else {
      new NetworkError(NetworkErrorType.CONNECTION);
    }
  }

  /**
   * 
   * Makes dictionarySelection only openable when the dropDownButton is clicked, not when the
   * combobox (dictionarySelection) is clicked.
   * 
   * @author mherre
   * @param event
   * @throws IOException
   * 
   */
  @FXML
  private void dontShow(MouseEvent event) throws IOException {
    dictionarySelection.hide();
  }

  /**
   * 
   * Changes the image of the current playerCount.
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
   * Method for when server 0 is clicked. Only joins if this server is actually shown.
   * 
   * @author nitterhe
   * @param event - the mouse click even in the LobbyOverview
   * @throws IOException -
   */
  @FXML
  private void join0(MouseEvent event) throws IOException {
    if (this.joinButton0.getOpacity() == 1.0)
      this.joinGame(0);
  }

  /**
   * Method for when server 1 is clicked. Only joins if this server is actually shown.
   * 
   * @author nitterhe
   * @param event - the mouse click even in the LobbyOverview
   * @throws IOException -
   */
  @FXML
  private void join1(MouseEvent event) throws IOException {
    if (this.joinButton1.getOpacity() == 1.0)
      this.joinGame(1);
  }

  /**
   * Method for when server 2 is clicked. Only joins if this server is actually shown.
   * 
   * @author nitterhe
   * @param event - the mouse click even in the LobbyOverview
   * @throws IOException -
   */
  @FXML
  private void join2(MouseEvent event) throws IOException {
    if (this.joinButton2.getOpacity() == 1.0)
      this.joinGame(2);
  }

  /**
   * Method for when server 3 is clicked. Only joins if this server is actually shown.
   * 
   * @author nitterhe
   * @param event - the mouse click even in the LobbyOverview
   * @throws IOException -
   */
  @FXML
  private void join3(MouseEvent event) throws IOException {
    if (this.joinButton3.getOpacity() == 1.0)
      this.joinGame(3);
  }

  /**
   * Method for when server 4 is clicked. Only joins if this server is actually shown.
   * 
   * @author nitterhe
   * @param event - the mouse click even in the LobbyOverview
   * @throws IOException -
   */
  @FXML
  private void join4(MouseEvent event) throws IOException {
    if (this.joinButton4.getOpacity() == 1.0)
      this.joinGame(4);
  }

  /**
   * Method for when server 5 is clicked. Only joins if this server is actually shown.
   * 
   * @author nitterhe
   * @param event - the mouse click even in the LobbyOverview
   * @throws IOException -
   */
  @FXML
  private void join5(MouseEvent event) throws IOException {
    if (this.joinButton5.getOpacity() == 1.0)
      this.joinGame(5);
  }

  /**
   * Method for when server 6 is clicked. Only joins if this server is actually shown.
   * 
   * @author nitterhe
   * @param event - the mouse click even in the LobbyOverview
   * @throws IOException -
   */
  @FXML
  private void join6(MouseEvent event) throws IOException {
    if (this.joinButton6.getOpacity() == 1.0)
      this.joinGame(6);
  }


  /**
   * Method for when server 7 is clicked. Only joins if this server is actually shown.
   * 
   * @author nitterhe
   * @param event - the mouse click even in the LobbyOverview
   * @throws IOException -
   */
  @FXML
  private void join7(MouseEvent event) throws IOException {
    if (this.joinButton7.getOpacity() == 1.0)
      this.joinGame(7);
  }

  /**
   * 
   * 
   * @author nitterhe
   * @param number
   * @throws IOException
   */
  private void joinGame(int number) throws IOException {
    if (Data.getServerList().size() >= number)
      playSound("ButtonClicked.mp3");
    if (joinServer(Data.getServerList().get(number).getIP4Address()))
      App.setRoot("MultiplayerLobby");
  }

  @FXML
  private void lightenJoinIcon(MouseEvent event) {
    ImageView iv = (ImageView) event.getSource();
    iv.setImage(new Image("/com/scrab5/ui/images/SB06_JoinButtonClicked.png"));
  }

  @FXML
  private void darkenJoinIcon(MouseEvent event) {
    ImageView iv = (ImageView) event.getSource();
    iv.setImage(new Image("/com/scrab5/ui/images/SB06_JoinButton.png"));
  }

  /**
   * 
   * 
   * @author nitterhe
   * @param playerCount
   */
  private void setupServer(int playerCount) {
    Data.getPlayerClient().hostServer(playerCount);
    Data.setHostedServer(Data.getPlayerClient().getHostedServer());
  }

  @FXML
  private void setSelectedDictionary(ActionEvent event) {
    String selected = (String) this.dictionarySelection.getValue();
    this.dicDisplaying.setText(selected.substring(0, selected.length() - 4));
    isDictionarySelected = true;
  }

  /**
   * https://stackabuse.com/java-list-files-in-a-directory/
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
                System.out.println(Data.getServerList().get(j).getIP4Address());
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
   * 
   * @author marku
   * @author nitterhe
   * @param serverdata
   * @param position
   */
  private void displayServer(ServerData serverdata, int position) {
    Platform.runLater(new Runnable() {

      public void run() {

    switch (position) {
      case 0:
        this.serverName0.setText(serverdata.getServerHost() + "'s Lobby");

        this.playerCount0
            .setText(serverdata.getClientCounter() + "/" + serverdata.getClientMaximum());
        this.status0.setText(serverdata.getServerStatus() ? "Playing" : "Waiting");
        this.joinButton0.setOpacity(1.0);
        break;
      case 1:
        this.serverName1.setText(serverdata.getServerHost() + "'s Lobby");
        this.playerCount1
            .setText(serverdata.getClientCounter() + "/" + serverdata.getClientMaximum());
        this.status1.setText(serverdata.getServerStatus() ? "Playing" : "Waiting");
        this.joinButton1.setOpacity(1.0);
        break;
      case 2:
        this.serverName2.setText(serverdata.getServerHost() + "'s Lobby");
        this.playerCount2
            .setText(serverdata.getClientCounter() + "/" + serverdata.getClientMaximum());
        this.status2.setText(serverdata.getServerStatus() ? "Playing" : "Waiting");
        this.joinButton2.setOpacity(1.0);
        break;
      case 3:
        this.serverName3.setText(serverdata.getServerHost() + "'s Lobby");
        this.playerCount3
            .setText(serverdata.getClientCounter() + "/" + serverdata.getClientMaximum());
        this.status3.setText(serverdata.getServerStatus() ? "Playing" : "Waiting");
        this.joinButton3.setOpacity(1.0);
        break;
      case 4:
        this.serverName4.setText(serverdata.getServerHost() + "'s Lobby");
        this.playerCount4
            .setText(serverdata.getClientCounter() + "/" + serverdata.getClientMaximum());
        this.status4.setText(serverdata.getServerStatus() ? "Playing" : "Waiting");
        this.joinButton4.setOpacity(1.0);
        break;
      case 5:
        this.serverName5.setText(serverdata.getServerHost() + "'s Lobby");
        this.playerCount5
            .setText(serverdata.getClientCounter() + "/" + serverdata.getClientMaximum());
        this.status5.setText(serverdata.getServerStatus() ? "Playing" : "Waiting");
        this.joinButton5.setOpacity(1.0);
        break;
      case 6:
        this.serverName6.setText(serverdata.getServerHost() + "'s Lobby");
        this.playerCount6
            .setText(serverdata.getClientCounter() + "/" + serverdata.getClientMaximum());
        this.status6.setText(serverdata.getServerStatus() ? "Playing" : "Waiting");
        this.joinButton6.setOpacity(1.0);
        break;
      case 7:
        this.serverName7.setText(serverdata.getServerHost() + "'s Lobby");
        this.playerCount7
            .setText(serverdata.getClientCounter() + "/" + serverdata.getClientMaximum());
        this.status7.setText(serverdata.getServerStatus() ? "Playing" : "Waiting");
        this.joinButton7.setOpacity(1.0);
        break;
      default:
        break;
    }
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
