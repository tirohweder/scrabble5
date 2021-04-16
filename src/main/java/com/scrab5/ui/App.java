package com.scrab5.ui;

import java.io.IOException;
import com.scrab5.util.database.CreateDatabase;
import com.scrab5.util.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The App class contains some methods changing the scene seen and the sets the app up for the first
 * time it gets started.
 * 
 * @author trohwede
 * @author mherre
 */
public class App extends Application {

  private static Scene scene;
  private Database db;

  /**
   * This method is called when ever the app gets startet. First it checks whether a database
   * already exists if not a new database gets created.
   * 
   * Then it sets up the app screen and shows the first scene
   * 
   * @author mherre
   * @param stage
   */
  public void start(Stage stage) throws IOException {

    if (!Database.databaseExistance()) {
      db = new Database();
      CreateDatabase cdb = new CreateDatabase();
    } else {
      Database.reconnect();
    }

    scene = new Scene(loadFXML("Login"), 1360, 768);
    stage.setScene(scene);
    stage.setTitle("Scrabble - Group 5");
    stage.setResizable(false);
    stage.show();
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
        AccountCreationController.setPredecessor("Profile");
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



}
