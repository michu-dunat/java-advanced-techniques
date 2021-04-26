package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientSettingsController {

	@FXML
	private TextField rmiRegistryHostTextField;

	@FXML
	private TextField rmiRegistryPortTextField;

	@FXML
	private TextField managerNameTextField;

	@FXML
	private TextField clientPortTextField;

	@FXML
	private TextField clientNameTextField;

	@FXML
	private Button proceedButton;

	@FXML
	void proceedButtonOnAction(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client.fxml"));

		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();

		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		stage.setScene(scene);

		ClientController controller = loader.getController();

		controller.setClientData(
				rmiRegistryHostTextField.getText(),
				Integer.parseInt(rmiRegistryPortTextField.getText()),
				managerNameTextField.getText(),
				Integer.parseInt(clientPortTextField.getText()),
				clientNameTextField.getText()
				);
		
		stage.setTitle(clientNameTextField.getText());

		stage.show();
	}

	@FXML
	public void initialize() {
		rmiRegistryHostTextField.setText("127.0.0.1");
		rmiRegistryPortTextField.setText("1500");
		managerNameTextField.setText("Manager-Server");
		clientPortTextField.setText("3500");
		clientNameTextField.setText("Client");
	}

}
