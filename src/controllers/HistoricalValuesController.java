package controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HistoricalValuesController implements Initializable {

	@FXML
	private Label errorLabel;
	@FXML
	private TextField baseCurrencyTextField;
	@FXML
	private TextField targetCurrencyTextField;
	@FXML
	public ComboBox<String> comboBox;
	@FXML
	private Button submitButton;
	@FXML
	private Label resultLabel;

	// Dropdown menu options
	ObservableList<String> list = FXCollections.observableArrayList("Weeks", "Months", "Years");

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// Adds the list items to combobox element
		comboBox.setItems(list);
	}

	public void returnToMainScene(ActionEvent event) throws IOException {
		Parent mainParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene mainScene = new Scene(mainParent);

		// This line gets the Stage information
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(mainScene);
		window.show();
	}

	// Submit button event
	public void submitHistoricalSearch(ActionEvent event) throws InvocationTargetException {
		// Resets errorlabel
		errorLabel.setText("");

		String currency1 = baseCurrencyTextField.getText().toUpperCase();
		String currency2 = targetCurrencyTextField.getText().toUpperCase();
		String dropDownInput = comboBox.getValue();

		// Checks that user has selected an interval
		try {
			if (dropDownInput == null) {
				errorLabel.setText("Please select a time interval!");
			}
			// Continues running if successful
			else {
				System.out.println(dropDownInput);
				callApi(currency1, currency2, dropDownInput);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void callApi(String currency1, String currency2, String timeFrame) {

		if (checkValidTextInput(currency1, currency2)) {
			// do stuff
		}
	}

	// Checks for invalid input in textfields
	private boolean checkValidTextInput(String currency1, String currency2) {
		// Number determines what type of error (if any) occurred
		boolean isValid = false;

		// Checks first for non-empty input, then for correct length
		if (!currency1.isBlank() && !currency2.isBlank()) {
			if (currency1.length() < 4 && currency2.length() < 4) {
				isValid = true;
			} else {
				errorLabel.setText("Please type in a currency with only 3 letters!");
			}
		} else {
			errorLabel.setText("Please type in a currency in both text fields!");
		}

		return isValid;
	}

	// Formats the result and sets color
	private void setResultText() {

	}
}
