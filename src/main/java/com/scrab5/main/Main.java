package com.scrab5.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
  public void start(Stage primaryStage) {
    try {



      AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/Sample.fxml"));
      Scene scene = new Scene(root, 1360, 768);
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);

      primaryStage.setTitle("Scrabble - Group 5");
      primaryStage.setResizable(false);
      // primaryStage.initStyle(StageStyle.TRANSPARENT);


      primaryStage.show();


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

}
