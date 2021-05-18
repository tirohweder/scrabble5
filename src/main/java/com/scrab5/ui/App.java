package com.scrab5.ui;

import java.io.IOException;
import com.scrab5.util.database.CreateDatabase;
import com.scrab5.util.database.Database;
import com.scrab5.util.database.FillDatabase;
import com.scrab5.util.database.UseDatabase;
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
 * The App class contains some methods changing the scene seen and the sets the app up for the first
 * time it gets started.
 * 
 * @author trohwede
 * @author mherre
 */
public class App extends Application {

  private static Scene scene;
  private static Stage mainStage;
  private Database db;
  private Media sound;
  private static MediaPlayer mediaplayer;


  /**
   * This method is called when ever the app gets startet. First it checks whether a database
   * already exists if not a new database gets created.
   * 
   * Then it sets up the app screen and shows the first scene
   * 
   * https://www.codota.com/code/java/methods/javafx.stage.Stage/setOnCloseRequest
   * 
   * TODO: Update Comments
   * 
   * @author mherre
   * @param stage
   */
  public void start(Stage stage) throws IOException {

    mainStage = stage;

    if (!Database.databaseExistance()) {
      db = new Database();
      CreateDatabase cdb = new CreateDatabase();
      FillDatabase.fillLetters();
    } else {
      Database.reconnect();
    }
    Database.disconnect();
    Database.reconnect();

    if (UseDatabase.tablePlayerIsEmpty()) {
      scene = new Scene(loadFXML("Login"), 1360, 768);
    } else {
      scene = new Scene(loadFXML("RealLogin"), 1360, 768);
    }

    this.setIcons(stage);
    this.setMediaPlayer();

    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

      @Override
      public void handle(WindowEvent event) {

        if (Data.getHostedServer() != null) {
          Data.getHostedServer().shutDownServer();
        }

        Platform.exit();
        System.exit(0);
      }
    });
    stage.setScene(scene);
    stage.setTitle("Scrabble - Group 5");
    stage.setResizable(false);
    stage.show();
    Database.disconnect();
    System.out.println("hier sind wir jetzt!");
  }

  /**
   * 
   * @author mherre
   */
  private void setMediaPlayer() {
    sound = new Media(Controller.class
        .getResource("/com/scrab5/ui/sound_effects/BackgroundMusic.mp3").toExternalForm());
    mediaplayer = new MediaPlayer(sound);
    mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaplayer.play();
  }

  /**
   * 
   * 
   * @author mherre
   * @param stage
   */
  private void setIcons(Stage stage) {
    Image icon1 = new Image(
        App.class.getResource("/com/scrab5/ui/images/TaskbarIcon32.png").toExternalForm());
    Image icon2 = new Image(
        App.class.getResource("/com/scrab5/ui/images/TaskbarIcon16.png").toExternalForm());
    stage.getIcons().add(icon1);
    stage.getIcons().add(icon2);
  }

  /**
   * @author trohwede
   * @param fxml
   * @throws IOException
   */
  public static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFXML(fxml));
  }

  /**
   * Changes the current shown scene depending on the predescessor scene
   * 
   * @author mherre
   * @param fxml
   * @param predescessor
   * @throws IOException
   */
  public static void setRoot(String fxml, String predescessor) throws IOException {

    switch (predescessor) {

      case "Profile":
        switch (fxml) {
          case "AccountCreation":
            AccountCreationController.setPredecessor("Profile");
            break;
          case "RealLogin":
            RealLoginController.setPredecessor("Profile");
          default:
            break;
        }

      default:
        break;

    }
    scene.setRoot(loadFXML(fxml));
  }

  /**
   * @author trohwede
   * @param fxml
   * @return
   * @throws IOException
   */
  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }

  /**
   * @author trohwede
   * @param args
   */
  public static void main(String[] args) {
    launch();
  }

  public static void setMusicVolume(double volume) {
    mediaplayer.setVolume(volume);
  }

  public static Stage getMainStage() {
    return mainStage;
  }

  public static Scene getScene() {
    return scene;
  }

}
