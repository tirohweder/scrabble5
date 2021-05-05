package com.scrab5.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileImage {

  private ImageView letterImage;

  public ImageView createTileImage(String letter) {

    System.out.println();
    Image i = new Image(this.getClass().getResource("/com/scrab5/ui/images/Singleplayer.png").toString());
    File f = new File(this.getClass().getResource("/com/scrab5/ui/images/Singleplayer.png").toString());
//    letterImage.setImage(picture);
//    letterImage.setFitHeight(0);
//    letterImage.setFitWidth(0);

    return letterImage;
  }

}