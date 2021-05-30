package com.scrab5.ui;

import com.scrab5.util.database.CreateDatabase;
import com.scrab5.util.database.Database;
import com.scrab5.util.database.FillDatabase;
import com.scrab5.util.database.UseDatabase;
import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The App class contains some methods changing the scene seen and sets the app up for the first
 * time it is started.
 *
 * @author trohwede
 * @author mherre
 */
public class App extends Application {

  private static Scene scene;
  private static Stage mainStage;
  private static MediaPlayer mediaplayer;

  /**
   * Sets the given fxml as root.
   *
   * @param fxml the name of the xml you want to set as root
   * @throws IOException tried to read a local file that was no longer available
   * @author trohwede
   */
  public static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFXML(fxml));
  }

  /**
   * Changes the current shown scene depending on the predecessor scene.
   *
   * @author mherre
   * @param fxml the String values the name of the .fxml scene
   * @param predescessor the String values the name of the scene which was seen before
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *     exist
   */
  public static void setRoot(String fxml, String predescessor) throws IOException {

    if ("Profile".equals(predescessor)) {
      switch (fxml) {
        case "AccountCreation":
          AccountCreationController.setPredecessor("Profile");
          break;
        case "RealLogin":
          RealLoginController.setPredecessor("Profile");
          break;
        default:
          break;
      }
    }
    scene.setRoot(loadFXML(fxml));
  }

  /**
   * Loads the given fxml and returns it as parent.
   *
   * @author trohwede
   * @param fxml loads the fxml
   * @return Parent
   * @throws IOException tried to read a local file that was no longer available
   */
  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }

  /**
   * App main.
   *
   * @author trohwede
   * @param args not needed
   */
  public static void main(String[] args) {
    launch();
  }

  /**
   * This method is called when the sound volume of the music needs to be adjusted (e.g.
   * "Settings.fxml").
   *
   * @author mherre
   * @param volume the double contains the value to which the music volume is set
   */
  public static void setMusicVolume(double volume) {
    mediaplayer.setVolume(volume);
  }

  /**
   * Returns the main <code>Stage</code>.
   *
   * @author mherre
   * @return mainStage the stage which contains the main stage
   */
  public static Stage getMainStage() {
    return mainStage;
  }

  /**
   * Returns the current loaded scene.
   *
   * @author mherre
   * @return scene the Scene which contains the current set scene
   */
  public static Scene getScene() {
    return scene;
  }

  /**
   * This method is called when ever the application is started. First it checks whether a database
   * already exists, if not a new database is created.
   *
   * <p>Then it sets up the app screen (icons, mediaplayer for sound) and shows the first scene. The
   * close operation has also been changed, so the app closes properly:
   *
   * <p>https://www.codota.com/code/java/methods/javafx.stage.Stage/setOnCloseRequest.
   *
   * @param stage the main stage where the app is presented
   * @author mherre
   */
  public void start(Stage stage) throws IOException {

    mainStage = stage;

    if (!Database.databaseExistance()) {
      Database db = new Database();
      CreateDatabase cdb = new CreateDatabase();
    } else {
      Database.reconnect();
    }
    FillDatabase.fillLetters();
    if (UseDatabase.tablePlayerIsEmpty()) {
      scene = new Scene(loadFXML("Login"), 1360, 768);
    } else {
      scene = new Scene(loadFXML("RealLogin"), 1360, 768);
    }

    this.setIcons(stage);
    this.setMediaPlayer();

    stage.setOnCloseRequest(
        new EventHandler<>() {
          @Override
          public void handle(WindowEvent event) {

            if (Data.getHostedServer() != null) {
              Data.getHostedServer().shutDownServer();
            } else if (Data.getPlayerClient() != null) {
              if (Data.getPlayerClient().threadIsRunning()) {
                Data.getPlayerClient().disconnectFromServer();
              }
            }
            Platform.exit();
            System.exit(0);
          }
        });

    stage.setScene(scene);
    stage.setTitle("Scrabble - Group 5");
    stage.setResizable(false);
    stage.show();
  }

  /**
   * This method is used to create a <code>MediaPlayer</code> which runs while the app is running.
   * It plays the background music.
   *
   * @author mherre
   */
  private void setMediaPlayer() {
    Media sound =
        new Media(
            Objects.requireNonNull(
                    Controller.class.getResource(
                        "/com/scrab5/ui/sound_effects/BackgroundMusic.mp3"))
                .toExternalForm());
    mediaplayer = new MediaPlayer(sound);
    mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaplayer.play();
  }

  /**
   * This method is used to set the task bar icon. The icon is either a 32x32 image or a 16x16
   * image, this depends on the resolution of the users desktop.
   *
   * @param stage the main stage where the app is presented
   * @author mherre
   */
  private void setIcons(Stage stage) {
    Image icon1 =
        new Image(
            Objects.requireNonNull(App.class.getResource("/com/scrab5/ui/images/TaskbarIcon32.png"))
                .toExternalForm());
    Image icon2 =
        new Image(
            Objects.requireNonNull(App.class.getResource("/com/scrab5/ui/images/TaskbarIcon16.png"))
                .toExternalForm());
    stage.getIcons().add(icon1);
    stage.getIcons().add(icon2);
  }
}
