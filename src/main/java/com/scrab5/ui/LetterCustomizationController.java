package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.scrab5.util.database.Database;
import com.scrab5.util.database.UseDatabase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The LetterCustomizationController class controls the components of the
 * "LetterCustomization.fxml".
 * 
 * @author mherre
 */
public class LetterCustomizationController extends Controller implements Initializable {

  @FXML
  private TextField aO, bO, cO, dO, eO, fO, gO, hO, iO, jO, kO, lO, mO, nO, oO, pO, qO, rO, sO, tO,
      uO, vO, wO, xO, yO, zO, spaceO;
  @FXML
  private TextField aP, bP, cP, dP, eP, fP, gP, hP, iP, jP, kP, lP, mP, nP, oP, pP, qP, rP, sP, tP,
      uP, vP, wP, xP, yP, zP, spaceP;

  private ArrayList<TextField> al, alP;

  /**
   * Call certain methods as soon as the Controller is loaded. Sets the displayed occurrences and
   * points depending on if the letter's occurrences/points have already been edited, if not then
   * the standard occurrences and point distribution is loaded from the database.
   * 
   * @author mherre
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    int[] occurrencies = UseDatabase.getAllOccurrences();
    int[] pointsDistribution = UseDatabase.getAllPointsPerLetter();

    al = this.createListO();
    alP = this.createListP();

    if (!Data.getHasBeenEdited()) {

      for (int i = 0; i < 27; i++) {
        al.get(i).setText(occurrencies[i] + "");
        alP.get(i).setText(pointsDistribution[i] + "");
      }

    } else {

      ArrayList<Integer> listO, listP;
      listO = Data.getOccurrencyDistribution();
      listP = Data.getPointsDistribution();
      UseDatabase.updateLetterCustomization(listP, listO);

      for (int i = 0; i < 27; i++) {
        al.get(i).setText(listO.get(i) + "");
        alP.get(i).setText(listP.get(i) + "");
      }
    }
    
    Database.disconnect();
  }

  /**
   * Event method that is called when the "Confirm"-button in the UI is clicked. Saves the changes
   * that were made by the user.
   * 
   * @author mherre
   * @param event the event that is created from the mouse-click
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  @FXML
  private void confirm(MouseEvent event) throws IOException {

    if (this.areValuesValid()) {

      ArrayList<Integer> listO, listP;
      listO = new ArrayList<Integer>();
      listP = new ArrayList<Integer>();

      for (int i = 0; i < 27; i++) {
        listO.add(Integer.parseInt(al.get(i).getText()));
        listP.add(Integer.parseInt(alP.get(i).getText()));
      }

      Data.setOccurrencyDistribution(listO);
      Data.setPointsDistribution(listP);
      Data.setHasBeenEdited(true);

      Stage s = (Stage) ((Node) (event.getSource())).getScene().getWindow();
      s.close();
    }

  }

  /**
   * Method that puts all <code>TextField</code> containing the occurrences into one
   * <code>ArrayList</code>, so working with them is easier.
   * 
   * @author mherre
   * @return al the ArrayList containing the occurrences
   */
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

  /**
   * Method that puts all <code>TextField</code> containing the points into one
   * <code>ArrayList</code>, so working with them is easier.
   * 
   * @author mherre
   * @return al the ArrayList containing the amount of points
   */
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

  /**
   * Method that checks if all entered values in the <code>TextFields</code>'s consist only of
   * numbers.
   * 
   * @author mherre
   * @return the boolean containing the value whether the <code>TextFields</code>'s fulfill the
   *         requirements
   * @throws IOException if the entered file name in <code>App.setRoot(String fxml)</code> doesn't
   *         exist
   */
  private boolean areValuesValid() throws IOException {

    for (int i = 0; i < 27; i++) {
      if (!al.get(i).getText().matches("[0-9]+") || !alP.get(i).getText().matches("[0-9]+")) {
        String message = "Please make sure your values only consist of numbers!";
        PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.ERROR);
        pum.show();
        return false;
      }
    }
    return true;
  }
}
