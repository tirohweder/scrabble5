package com.scrab5.ui;

import com.scrab5.util.database.Database;
import com.scrab5.util.database.FillDatabase;
import com.scrab5.util.database.UseDatabase;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The LetterCustomizationController class controls the components of the
 * "LetterCustomization.fxml"..
 *
 * @author mherre
 */
public class LetterCustomizationController extends Controller implements Initializable {

  @FXML
  private TextField occA;
  @FXML
  private TextField occB;
  @FXML
  private TextField occC;
  @FXML
  private TextField occD;
  @FXML
  private TextField occE;
  @FXML
  private TextField occF;
  @FXML
  private TextField occG;
  @FXML
  private TextField occH;
  @FXML
  private TextField occI;
  @FXML
  private TextField occJ;
  @FXML
  private TextField occK;
  @FXML
  private TextField occL;
  @FXML
  private TextField occM;
  @FXML
  private TextField occN;
  @FXML
  private TextField occO;
  @FXML
  private TextField occP;
  @FXML
  private TextField occQ;
  @FXML
  private TextField occR;
  @FXML
  private TextField occS;
  @FXML
  private TextField occT;
  @FXML
  private TextField occU;
  @FXML
  private TextField occV;
  @FXML
  private TextField occW;
  @FXML
  private TextField occX;
  @FXML
  private TextField occY;
  @FXML
  private TextField occZ;
  @FXML
  private TextField spaceO;
  @FXML
  private TextField poiA;
  @FXML
  private TextField poiB;
  @FXML
  private TextField poiC;
  @FXML
  private TextField poiD;
  @FXML
  private TextField poiE;
  @FXML
  private TextField poiF;
  @FXML
  private TextField poiG;
  @FXML
  private TextField poiH;
  @FXML
  private TextField poiI;
  @FXML
  private TextField poiJ;
  @FXML
  private TextField poiK;
  @FXML
  private TextField poiL;
  @FXML
  private TextField poiM;
  @FXML
  private TextField poiN;
  @FXML
  private TextField poiO;
  @FXML
  private TextField poiP;
  @FXML
  private TextField poiQ;
  @FXML
  private TextField poiR;
  @FXML
  private TextField poiS;
  @FXML
  private TextField poiT;
  @FXML
  private TextField poiU;
  @FXML
  private TextField poiV;
  @FXML
  private TextField poiW;
  @FXML
  private TextField poiX;
  @FXML
  private TextField poiY;
  @FXML
  private TextField poiZ;
  @FXML
  private TextField spaceP;

  private ArrayList<TextField> al;
  private ArrayList<TextField> alP;

  /**
   * Call certain methods as soon as the Controller is loaded. Sets the displayed occurrences and
   * points depending on if the letter's occurrences/points have already been edited, if not then
   * the standard occurrences and point distribution is loaded from the database.
   *
   * @author mherre
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    FillDatabase.fillLetters();
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

      ArrayList<Integer> listO;
      ArrayList<Integer> listP;
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

      ArrayList<Integer> listO;
      ArrayList<Integer> listP;
      listO = new ArrayList<>();
      listP = new ArrayList<>();

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
   * Method that puts all <code>TextField</code> containing the occurrences into one <code>ArrayList
   * </code>, so working with them is easier.
   *
   * @author mherre
   * @return al the ArrayList containing the occurrences
   */
  private ArrayList<TextField> createListO() {
    ArrayList<TextField> al = new ArrayList<>();
    al.add(occA);
    al.add(occB);
    al.add(occC);
    al.add(occD);
    al.add(occE);
    al.add(occF);
    al.add(occG);
    al.add(occH);
    al.add(occI);
    al.add(occJ);
    al.add(occK);
    al.add(occL);
    al.add(occM);
    al.add(occN);
    al.add(occO);
    al.add(occP);
    al.add(occQ);
    al.add(occR);
    al.add(occS);
    al.add(occT);
    al.add(occU);
    al.add(occV);
    al.add(occW);
    al.add(occX);
    al.add(occY);
    al.add(occZ);
    al.add(spaceO);

    return al;
  }

  /**
   * Method that puts all <code>TextField</code> containing the points into one <code>ArrayList
   * </code>, so working with them is easier.
   *
   * @author mherre
   * @return al the ArrayList containing the amount of points
   */
  private ArrayList<TextField> createListP() {
    ArrayList<TextField> al = new ArrayList<>();
    al.add(poiA);
    al.add(poiB);
    al.add(poiC);
    al.add(poiD);
    al.add(poiE);
    al.add(poiF);
    al.add(poiG);
    al.add(poiH);
    al.add(poiI);
    al.add(poiJ);
    al.add(poiK);
    al.add(poiL);
    al.add(poiM);
    al.add(poiN);
    al.add(poiO);
    al.add(poiP);
    al.add(poiQ);
    al.add(poiR);
    al.add(poiS);
    al.add(poiT);
    al.add(poiU);
    al.add(poiV);
    al.add(poiW);
    al.add(poiX);
    al.add(poiY);
    al.add(poiZ);
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
      if (!al.get(i).getText().matches("[0-9]{0,4}")
          || !alP.get(i).getText().matches("[0-9]{0,4}")) {
        String message =
            "Please make sure your values only consist of numbers and do not exceed 9.999!";
        PopUpMessage pum = new PopUpMessage(message, PopUpMessageType.ERROR);
        pum.show();
        return false;
      }
    }
    return true;
  }
}
