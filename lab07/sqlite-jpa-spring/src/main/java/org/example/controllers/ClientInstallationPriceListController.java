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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.example.models.Client;
import org.example.models.Installation;
import org.example.models.Service;
import org.example.repositories.ClientRepository;
import org.example.repositories.InstallationRepository;
import org.example.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ClientInstallationPriceListController {

    @FXML
    private TableView<Client> clientTable;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private Button addClientButton;

    @FXML
    private Button deleteClientButton;

    @FXML
    private TableColumn<Client, Integer> clientIdColumn = new TableColumn<>("ID");

    @FXML
    private TableColumn<Client, String> firstNameColumn = new TableColumn<>("First name");

    @FXML
    private TableColumn<Client, String> lastNameColumn = new TableColumn<>("Last name");

    private ObservableList<Client> clients = FXCollections.observableArrayList();

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    InstallationRepository installationRepository;

    @FXML
    private TableView<Service> serviceTable;

    @FXML
    private Button addServiceButton;

    @FXML
    private TextField serviceDescriptionTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TableColumn<Service, Integer> serviceIdColumn = new TableColumn<>("ID");

    @FXML
    private TableColumn<Service, String> serviceTypeColumn = new TableColumn<>("Service type");

    @FXML
    private TableColumn<Service, Float> priceColumn = new TableColumn<>("Price");

    private ObservableList<Service> services = FXCollections.observableArrayList();

    @FXML
    private TableView<Installation> installationTable;

    @FXML
    private TableColumn<Installation, Integer> installationIdColumn = new TableColumn<>("ID");

    @FXML
    private TableColumn<Installation, String> addressColumn = new TableColumn<>("Address");

    @FXML
    private TableColumn<Installation, Integer> routerIdColumn = new TableColumn<>("Router ID");

    @FXML
    private TableColumn<Installation, Client> clientIdInstallationColumn = new TableColumn<>("Client ID");

    @FXML
    private TableColumn<Installation, Service> serviceIdInstallationColumn = new TableColumn<>("Service ID");

    @FXML
    private Button addInstallationButton;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField routerIdTextField;

    private ObservableList<Installation> installations = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setUpClientTable();
        setUpServiceTable();
        setUpInstallationTable();

    }

    @FXML
    void addInstallationButtonOnAction(ActionEvent event) {
        Installation installation = new Installation(addressTextField.getText(),
                Integer.parseInt(routerIdTextField.getText()),
                serviceTable.getSelectionModel().getSelectedItem(),
                clientTable.getSelectionModel().getSelectedItem());
        installationRepository.save(installation);
        refreshInstallations();
    }

    private void setUpInstallationTable() {
        installationIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Installation, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Installation, String> event) {
                Installation installation = event.getRowValue();
                installation.setAddress(event.getNewValue());
                installationRepository.save(installation);
            }
        });
        routerIdColumn.setCellValueFactory(new PropertyValueFactory<>("routerId"));
        routerIdColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        routerIdColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Installation, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Installation, Integer> event) {
                Installation installation = event.getRowValue();
                installation.setRouterId(event.getNewValue());
                installationRepository.save(installation);
            }
        });

        clientIdInstallationColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        serviceIdInstallationColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));

        installationTable.getColumns().add(installationIdColumn);
        installationTable.getColumns().add(addressColumn);
        installationTable.getColumns().add(routerIdColumn);
        installationTable.getColumns().add(clientIdInstallationColumn);
        installationTable.getColumns().add(serviceIdInstallationColumn);

        installationTable.setItems(installations);

        refreshInstallations();
    }

    private void refreshInstallations() {
        installations.clear();
        installations.addAll(installationRepository.findAll());
    }

    private void setUpServiceTable() {
        serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        serviceTypeColumn.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        serviceTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        serviceTypeColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Service, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Service, String> event) {
                Service service = event.getRowValue();
                service.setServiceType(event.getNewValue());
                serviceRepository.save(service);
            }
        });
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        priceColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Service, Float>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Service, Float> event) {
                Service service = event.getRowValue();
                service.setPrice(event.getNewValue());
                serviceRepository.save(service);
            }
        });

        serviceTable.getColumns().add(serviceIdColumn);
        serviceTable.getColumns().add(serviceTypeColumn);
        serviceTable.getColumns().add(priceColumn);

        serviceTable.setItems(services);
        refreshServices();
    }

    private void refreshServices() {
        services.clear();
        services.addAll(serviceRepository.findAll());
    }

    private void setUpClientTable() {
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, String> event) {
                Client client = event.getRowValue();
                client.setFirstName(event.getNewValue());
                clientRepository.save(client);
            }
        });
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, String> event) {
                Client client = event.getRowValue();
                client.setLastName(event.getNewValue());
                clientRepository.save(client);
            }
        });

        clientTable.getColumns().add(clientIdColumn);
        clientTable.getColumns().add(firstNameColumn);
        clientTable.getColumns().add(lastNameColumn);

        clientTable.setItems(clients);
        refreshClients();
    }

    private void refreshClients() {
        clients.clear();
        clients.addAll(clientRepository.findAll());
    }

    @FXML
    void addClientButtonOnAction(ActionEvent event) {
        Client client = new Client(firstNameTextField.getText(), lastNameTextField.getText());
        clientRepository.save(client);
        refreshClients();
    }

    @FXML
    void deleteClientButtonOnAction(ActionEvent event) {
        Client client = clientTable.getSelectionModel().getSelectedItem();
        clients.remove(client);
        clientRepository.delete(client);
        refreshClients();
    }

    @FXML
    void addServiceButtonOnAction(ActionEvent event) {
        Service service = new Service(serviceDescriptionTextField.getText(), Float.parseFloat(priceTextField.getText()));
        serviceRepository.save(service);
        refreshServices();
    }

    @Autowired
    private ApplicationContext springContext;

    @FXML
    private Button backButton;

    @FXML
    void backButtonOnAction(ActionEvent event) {
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

    @FXML
    private Button testButton;

    @FXML
    void testButtonOnAction(ActionEvent event) {
        Client client = new Client("Jan", "Kowalski");
        clientRepository.save(client);
        Client client1 = new Client("Paweł", "Piotrowski");
        clientRepository.save(client1);
        Client client2 = new Client("Natalia", "Cicha");
        clientRepository.save(client2);
        refreshClients();

        Service service = new Service("Internet 100", (float) 30.00);
        serviceRepository.save(service);
        Service service1 = new Service("Internet 200", (float) 35.00);
        serviceRepository.save(service1);
        Service service2 = new Service("Światłowód 750", (float) 60.00);
        serviceRepository.save(service2);
        Service service3 = new Service("Światłowód 1000", (float) 65.00);
        serviceRepository.save(service3);
        Service service4 = new Service("VPN", (float) 15.00);
        serviceRepository.save(service4);
        refreshServices();

        Installation installation = new Installation("Tychy",50, service, client);
        installationRepository.save(installation);
        Installation installation1 = new Installation("Wrocław",50, service1, client);
        installationRepository.save(installation1);
        Installation installation2 = new Installation("Rybnik",50, service2, client1);
        installationRepository.save(installation2);
        Installation installation3 = new Installation("Katowice",50, service3, client1);
        installationRepository.save(installation3);
        Installation installation4 = new Installation("Poznań",50, service4, client);
        installationRepository.save(installation4);
        refreshInstallations();

    }

}
