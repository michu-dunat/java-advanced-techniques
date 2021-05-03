package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MainWindowController {

    @Autowired
    private ApplicationContext springContext;

    @FXML
    private Button manageClientInstallationDataAndPriceListButton;

    @FXML
    private Button viewReceivablesPaymentsMakePaymentButton;


    @FXML
    void manageClientInstallationDataAndPriceListButtonOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientInstallationPriceList.fxml"));
        loader.setControllerFactory(springContext::getBean);

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

        stage.show();

    }

    @FXML
    void viewReceivablesPaymentsMakePaymentButtonOnAction(ActionEvent event) {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccruedReceivablesPayments.fxml"));
        loader.setControllerFactory(springContext::getBean);

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

        stage.show();
    }

    @FXML
    private Button startSimulationButton;

    @FXML
    void startSimulationButtonOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogsView.fxml"));
        Parent root = null;
        try {
            Thread.sleep(2000);
            root = loader.load();
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
