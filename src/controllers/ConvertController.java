package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
				callApi(currency1, currency2, amount);
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

	// Makes the convert API call with given variables
	private void callApi(String currency1, String currency2, double amount) {
		JSONParser parser = new JSONParser();

		try {

			// Creating URL with variables
			URL urlAdress = new URL("https://api.currencyscoop.com/v1/convert?api_key=64e1bf3a3d7ee7d878c70bec407ec1a1"
					+ "&from=" + currency1 + "&to=" + currency2 + "&amount=" + amount);

			URLConnection yc = urlAdress.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

			// Fetching response and storing it as JSON object
			Object obj = parser.parse(in.readLine());
			JSONObject jsObject = (JSONObject) obj;

			String jsonString = jsObject.entrySet().toArray()[1].toString();

			// Removing "response" from JSON by finding first {
			int startIndex = jsonString.indexOf("{");
			String finalString = jsonString.substring(startIndex, jsonString.length());

			// Returning string to JSONObject so specific components can be called
			JSONObject ja = (JSONObject) parser.parse(finalString);
			String value = ja.get("value").toString();
			String result = (value.substring(0, value.indexOf(".") + 3)) + " " + ja.get("to");
			resultLabel.setText(result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void returnToMainScene(ActionEvent event) throws IOException {
		Parent tableViewParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene tableViewScene = new Scene(tableViewParent);

		// This line gets the Stage information
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(tableViewScene);
		window.show();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}
}
