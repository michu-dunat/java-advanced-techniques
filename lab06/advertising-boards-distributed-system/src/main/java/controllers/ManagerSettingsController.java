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

public class ManagerSettingsController {

	@FXML
	private Button proceedButton;

	@FXML
	private TextField rmiRegistryPortTextField;

	@FXML
	private TextField managerPortTextField;

	@FXML
	private TextField managerNameTextField;

	@FXML
	void proceedButtonOnAction(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Manager.fxml"));

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

		ManagerController controller = loader.getController();

		controller.setManagerData(Integer.parseInt(rmiRegistryPortTextField.getText()),
				Integer.parseInt(managerPortTextField.getText()), managerNameTextField.getText());

		stage.setTitle(managerNameTextField.getText());

		stage.show();
	}

	@FXML
	public void initialize() {
		rmiRegistryPortTextField.setText("1500");
		managerPortTextField.setText("1600");
		managerNameTextField.setText("Manager-Server");

	}

}
