package com.scrab5.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class AccountCreationController extends Controller implements Initializable {

	@FXML
	private TextField nickname;

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

	/**
	 * @author marku
	 * @param event
	 * @throws IOException
	 * 
	 * 
	 */
	@FXML
	private void enterPressed(KeyEvent event) throws IOException {
		switch (event.getCode()) {
		case ENTER:
			if (nickname.getText().length() <= 12) {
				System.out.println(nickname.getText());
			} else {
				System.out.println("Too Long");
			}
		}
	}

}
