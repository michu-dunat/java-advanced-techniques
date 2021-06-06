package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {

    @FXML
    private Button openChatButton;

    @FXML
    private TextField hostTextField;

    @FXML
    private TextField listeningPortTextField;

    @FXML
    private TextField sendingPortTextField;

    @FXML
    void openChatButtonOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChatWindow.fxml"));

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

        ChatController controller = loader.getController();

        controller.setChatHostAndPorts(
                hostTextField.getText(),
                Integer.parseInt(sendingPortTextField.getText()),
                Integer.parseInt(listeningPortTextField.getText())
        );

        stage.show();
    }

    @FXML
    public void initialize() {
        hostTextField.setText("127.0.0.1");
        sendingPortTextField.setText("Java9");
        listeningPortTextField.setText("Java9");

    }

}
