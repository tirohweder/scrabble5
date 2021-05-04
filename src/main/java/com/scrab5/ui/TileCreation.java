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
    
    try {
      System.out.println("Test combain");
      combainImages(letter, "TestBild");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
//    
//    TileImage ti = new TileImage();
//    iv = ti.createTileImage(letter);
//    Text point = new Text(points);
//    StackPane pane = new StackPane();
//    
//    pane.getChildren().add(iv);
//    pane.getChildren().add(point);
    
  }
  
  private void combainImages(String letter, String combainedImages) throws IOException {
    BufferedImage a = ImageIO.read(new File("@letter_Tiles/Tile%20_M_%20%20.png")); //imageList holds the path to all images
    BufferedImage b = ImageIO.read(new File("@letter_Tiles/Tile%20_M_%20%20.png"));
    BufferedImage c = new BufferedImage(a.getWidth(), a.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = c.getGraphics();
    g.drawImage(a, 0, 0, null);
    g.drawImage(b, 0, 0, null);
    ImageIO.write(c, "PNG", new File(combainedImages));

}
  
  public static void main(String[] args) {
    System.out.println("Ausgabe main");
    TileCreation tc = new TileCreation("A", "5");
  }

}
