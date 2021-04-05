package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

/**
 * @author marku
 * 
 */
public class AccountCreationController extends Controller implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

	/**
	 * @author marku
	 * 
	 *         Event method that is called when the "Back"-button is clicked. Scene
	 *         gets changed to the predecessor "Login" scene
	 */
	@FXML
	private void back(MouseEvent event) throws IOException {
		App.setRoot("Login");
	}

}
