package com.scrab5.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class TileCreation {
  
  ImageView iv;
  
  public TileCreation(String letter, String points) {
    
    TileImage ti = new TileImage();
    iv = ti.createTileImage(letter);
//    Text point = new Text(points);
//    StackPane pane = new StackPane();
//    
//    pane.getChildren().add(iv);
//    pane.getChildren().add(point);
    
  }
  
  public static void main(String[] args) {
    System.out.println("Ausgabe main");
    TileCreation tc = new TileCreation("A", null);
  }

}
