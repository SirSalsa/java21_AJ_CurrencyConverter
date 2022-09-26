package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import helpers.ApiHelpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConvertController implements Initializable {

	@FXML
	private Label errorLabel;
	@FXML
	private TextField ogCurrencyTextField;
	@FXML
	private TextField amountTextField;
	@FXML
	private TextField newCurrencyTextField;
	@FXML
	private Button submitButton;
	@FXML
	private Label resultLabel;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}
	
	public void returnToMainScene(ActionEvent event) throws IOException {
		Parent mainParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene mainScene = new Scene(mainParent);

		// This line gets the Stage information
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(mainScene);
		window.show();
	}

	public void submit(ActionEvent event) {

		// Checking for correct formatting of input
		try {
			errorLabel.setText("");
			String currency1 = ogCurrencyTextField.getText().toUpperCase();
			String currency2 = newCurrencyTextField.getText().toUpperCase();
			double amount = Double.parseDouble(amountTextField.getText());

			if (currency1.length() > 3 || currency2.length() > 3) {
				errorLabel.setText("Please write only three letters for currencies!");
			} else if (currency1.isBlank() && currency2.isBlank()) {
				errorLabel.setText("Cannot leave text fields empty!");
			} else {
				// Performs API call, returns result
				resultLabel.setText(ApiHelpers.callConvertApi(currency1, currency2, amount));
			}
		}
		// Checks for non-numeric inputs in amount field
		catch (NumberFormatException e) {
			errorLabel.setText("Cannot leave number field empty!");
			System.out.println(e);
			if (amountTextField.getText().contains(",")) {
				errorLabel.setText("Only use decimal points with decimal numbers!");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
