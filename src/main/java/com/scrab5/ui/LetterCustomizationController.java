package com.scrab5.ui;

import java.net.URL;
import java.util.ResourceBundle;
import com.scrab5.util.database.UseDatabase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LetterCustomizationController extends Controller implements Initializable {

  @FXML
  private TextField aO, bO, cO, dO, eO, fO, gO, hO, iO, jO, kO, lO, mO, nO, oO, pO, qO, rO, sO, tO,
      uO, vO, wO, xO, yO, zO, spaceO;
  @FXML
  private TextField aP, bP, cP, dP, eP, fP, gP, hP, iP, jP, kP, lP, mP, nP, oP, pP, qP, rP, sP, tP,
      uP, vP, wP, xP, yP, zP, spaceP;

  private TextField[] letterOccurrency = {aO, bO, cO, dO, eO, fO, gO, hO, iO, jO, kO, lO, mO, nO,
      oO, pO, qO, rO, sO, tO, uO, vO, wO, xO, yO, zO, spaceO};
  private TextField[] letterPointDistribution = {aP, bP, cP, dP, eP, fP, gP, hP, iP, jP, kP, lP, mP,
      nP, oP, pP, qP, rP, sP, tP, uP, vP, wP, xP, yP, zP, spaceP};

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    int[] occurrencies = UseDatabase.getAllOccurrences();
    int[] pointsDistribution = UseDatabase.getAllPointsPerLetter();

    for (int i = 0; i < letterOccurrency.length; i++) {
      this.letterOccurrency[i].setText("");
    }
  }



  @FXML
  private void confirm(MouseEvent event) {
    playSound("ButtonClicked.mp3");
    this.aO.setText("TETSTSTSTS");

  }



}
