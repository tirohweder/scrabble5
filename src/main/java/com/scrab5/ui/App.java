package com.scrab5.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

  private static Scene scene;

  @Override
  public void start(Stage stage) throws IOException {
    scene = new Scene(loadFXML("Login"), 1360, 768);
    stage.setScene(scene);
    stage.setTitle("Scrabble - Group 5");
    stage.setResizable(false);
    stage.show();
  }

  public static void setRoot(String fxml) throws IOException {


    scene.setRoot(loadFXML(fxml));

  }

  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }

  public static void main(String[] args) {
    launch();
  }


}
