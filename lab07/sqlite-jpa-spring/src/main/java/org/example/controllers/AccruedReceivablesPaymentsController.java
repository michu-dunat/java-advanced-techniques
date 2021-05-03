package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import org.example.models.*;
import org.example.repositories.AccruedReceivableRepository;
import org.example.repositories.InstallationRepository;
import org.example.repositories.MadePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
public class AccruedReceivablesPaymentsController {

    @Autowired
    InstallationRepository installationRepository;
    @Autowired
    MadePaymentRepository madePaymentRepository;
    @Autowired
    AccruedReceivableRepository accruedReceivableRepository;
    boolean isThreadFinish = false;
    @FXML
    private TableView<Installation> installationTable;
    @FXML
    private final TableColumn<Installation, Integer> installationIdColumn = new TableColumn<>("ID");
    @FXML
    private final TableColumn<Installation, String> addressColumn = new TableColumn<>("Address");
    @FXML
    private final TableColumn<Installation, Integer> routerIdColumn = new TableColumn<>("Router ID");
    @FXML
    private final TableColumn<Installation, Client> clientIdInstallationColumn = new TableColumn<>("Client ID");
    @FXML
    private final TableColumn<Installation, Service> serviceIdInstallationColumn = new TableColumn<>("Service ID");
    @FXML
    private final TableColumn<MadePayment, Integer> paymentIdColumn = new TableColumn<>("ID");
    @FXML
    private final TableColumn<MadePayment, LocalDate> paymentDateColumn = new TableColumn<>("Payment date");
    @FXML
    private final TableColumn<MadePayment, Float> paymentAmountColumn = new TableColumn<>("Payment amount");
    @FXML
    private final TableColumn<MadePayment, Installation> installationIdPaymentColumn = new TableColumn<>("Installation ID");
    @FXML
    private final TableColumn<AccruedReceivable, Integer> accruedReceivableIdColumn = new TableColumn<>("ID");
    @FXML
    private final TableColumn<AccruedReceivable, LocalDate> dueDateColumn = new TableColumn<>("Due date");
    @FXML
    private final TableColumn<AccruedReceivable, Float> priceToPayColumn = new TableColumn<>("Price to pay");
    @FXML
    private final TableColumn<AccruedReceivable, Installation> installationIdAccruedReceivableColumn = new TableColumn<>("Installation ID");
    @FXML
    private TableView<AccruedReceivable> accruedReceivableTable;
    @FXML
    private TableView<MadePayment> paymentTable;
    @FXML
    private TextField paymentAmountTextField;
    @FXML
    private DatePicker paymentDayDatePicker;
    @FXML
    private Button makePaymentButton;
    private final ObservableList<Installation> installations = FXCollections.observableArrayList();
    private final ObservableList<MadePayment> payments = FXCollections.observableArrayList();
    private final ObservableList<AccruedReceivable> accruedReceivables = FXCollections.observableArrayList();

    @FXML
    void makePaymentButtonOnAction(ActionEvent event) {
        MadePayment madePayment = new MadePayment(paymentDayDatePicker.getValue(),
                Float.parseFloat(paymentAmountTextField.getText()),
                installationTable.getSelectionModel().getSelectedItem());
        madePaymentRepository.save(madePayment);
        refreshPaymentTable();
    }

    @FXML
    public void initialize() {
        setUpInstallationTable();
        setUpPaymentTable();
        setUpAccruedReceivableTable();

        isThreadFinish = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isThreadFinish) {
                    refreshAccruedReceivableTable();

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }

        ).start();
    }

    private void setUpPaymentTable() {
        paymentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        paymentDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDay"));
        paymentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        paymentAmountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        paymentAmountColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MadePayment, Float>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<MadePayment, Float> event) {
                MadePayment madePayment = event.getRowValue();
                madePayment.setPaymentAmount(event.getNewValue());
                madePaymentRepository.save(madePayment);
            }
        });
        installationIdPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("installationId"));


        paymentTable.getColumns().add(paymentIdColumn);
        paymentTable.getColumns().add(paymentDateColumn);
        paymentTable.getColumns().add(paymentAmountColumn);
        paymentTable.getColumns().add(installationIdPaymentColumn);

        paymentTable.setItems(payments);

        refreshPaymentTable();
    }

    private void refreshPaymentTable() {
        payments.clear();
        payments.addAll(madePaymentRepository.findAll());
    }

    private void setUpAccruedReceivableTable() {
        accruedReceivableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        priceToPayColumn.setCellValueFactory(new PropertyValueFactory<>("priceToPay"));
        installationIdAccruedReceivableColumn.setCellValueFactory(new PropertyValueFactory<>("installationId"));


        accruedReceivableTable.getColumns().add(accruedReceivableIdColumn);
        accruedReceivableTable.getColumns().add(dueDateColumn);
        accruedReceivableTable.getColumns().add(priceToPayColumn);
        accruedReceivableTable.getColumns().add(installationIdAccruedReceivableColumn);

        accruedReceivableTable.setItems(accruedReceivables);

        refreshAccruedReceivableTable();
    }

    private void refreshAccruedReceivableTable() {
        accruedReceivables.clear();
        accruedReceivables.addAll(accruedReceivableRepository.findAll());
    }

    private void setUpInstallationTable() {
        installationIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        routerIdColumn.setCellValueFactory(new PropertyValueFactory<>("routerId"));
        clientIdInstallationColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        serviceIdInstallationColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));

        installationTable.getColumns().add(installationIdColumn);
        installationTable.getColumns().add(addressColumn);
        installationTable.getColumns().add(routerIdColumn);
        installationTable.getColumns().add(clientIdInstallationColumn);
        installationTable.getColumns().add(serviceIdInstallationColumn);

        installationTable.setItems(installations);

        installations.clear();
        installations.addAll(installationRepository.findAll());
    }

    @Autowired
    private ApplicationContext springContext;

    @FXML
    private Button backButton;

    @FXML
    void backButtonOnAction(ActionEvent event) {
        isThreadFinish = true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
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

}