package com.scrab5.ui;

import java.net.URL;
import java.util.ArrayList;
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

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    int[] occurrencies = UseDatabase.getAllOccurrences();
    int[] pointsDistribution = UseDatabase.getAllPointsPerLetter();

    ArrayList<TextField> al = this.createListO();
    ArrayList<TextField> alP = this.createListP();


    for (int i = 0; i < 27; i++) {
      al.get(i).setText(occurrencies[i] + "");
      alP.get(i).setText(pointsDistribution[i] + "");
    }
  }



  @FXML
  private void confirm(MouseEvent event) {
    playSound("ButtonClicked.mp3");
  }

  private ArrayList<TextField> createListO() {
    ArrayList<TextField> al = new ArrayList<TextField>();
    al.add(aO);
    al.add(bO);
    al.add(cO);
    al.add(dO);
    al.add(eO);
    al.add(fO);
    al.add(gO);
    al.add(hO);
    al.add(iO);
    al.add(jO);
    al.add(kO);
    al.add(lO);
    al.add(mO);
    al.add(nO);
    al.add(oO);
    al.add(pO);
    al.add(qO);
    al.add(rO);
    al.add(sO);
    al.add(tO);
    al.add(uO);
    al.add(vO);
    al.add(wO);
    al.add(xO);
    al.add(yO);
    al.add(zO);
    al.add(spaceO);

    return al;
  }

  private ArrayList<TextField> createListP() {
    ArrayList<TextField> al = new ArrayList<TextField>();
    al.add(aP);
    al.add(bP);
    al.add(cP);
    al.add(dP);
    al.add(eP);
    al.add(fP);
    al.add(gP);
    al.add(hP);
    al.add(iP);
    al.add(jP);
    al.add(kP);
    al.add(lP);
    al.add(mP);
    al.add(nP);
    al.add(oP);
    al.add(pP);
    al.add(qP);
    al.add(rP);
    al.add(sP);
    al.add(tP);
    al.add(uP);
    al.add(vP);
    al.add(wP);
    al.add(xP);
    al.add(yP);
    al.add(zP);
    al.add(spaceP);

    return al;
  }


}
