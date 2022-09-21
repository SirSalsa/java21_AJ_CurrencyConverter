package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController implements Initializable {


	public void gotoConvertScene(ActionEvent event) throws IOException {
		System.out.println("Convert");
		Parent tableViewParent = FXMLLoader.load(getClass().getResource("ConvertScene.fxml"));
		Scene tableViewScene = new Scene(tableViewParent);

		// This line gets the Stage information
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(tableViewScene);
		window.show();
	}
	
	public void gotoHistoricalValuesScene(ActionEvent event) throws IOException {
		System.out.println("Historical");
		Parent tableViewParent = FXMLLoader.load(getClass().getResource("HistoricalValues.fxml"));
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
