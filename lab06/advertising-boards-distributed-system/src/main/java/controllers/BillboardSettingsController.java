package controllers;

import java.io.IOException;
import java.time.Duration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BillboardSettingsController {
	
	@FXML
    private TextField rmiRegistryHostTextField;

    @FXML
    private TextField rmiRegistryPortTextField;

    @FXML
    private TextField managerNameTextField;

    @FXML
    private TextField billboardPortTextField;

    @FXML
    private TextField billboardNameTextField;

    @FXML
    private TextField bufforSizeTextField;

    @FXML
    private TextField displayIntervalTextField;

    @FXML
    private Button proceedButton;

    @FXML
    void proceedButtonOnAction(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/Billboard.fxml"));

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

		BillboardController controller = loader.getController();

		controller.setBillboardData(
				rmiRegistryHostTextField.getText(),
				Integer.parseInt(rmiRegistryPortTextField.getText()),
				managerNameTextField.getText(),
				Integer.parseInt(billboardPortTextField.getText()),
				billboardNameTextField.getText(),
				Integer.parseInt(bufforSizeTextField.getText()),
				Duration.ofSeconds(Long.parseLong(displayIntervalTextField.getText()))
				);
		
		stage.setTitle(billboardNameTextField.getText());

		stage.show();
    }
    
    @FXML
	public void initialize() {
    	rmiRegistryHostTextField.setText("127.0.0.1");
		rmiRegistryPortTextField.setText("1500");
		managerNameTextField.setText("Manager-Server");
		billboardPortTextField.setText("4500");
		billboardNameTextField.setText("Billboard");
		bufforSizeTextField.setText("10");
		displayIntervalTextField.setText("5");
		
	}

}
