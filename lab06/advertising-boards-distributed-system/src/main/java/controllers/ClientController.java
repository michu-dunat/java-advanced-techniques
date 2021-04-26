package controllers;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;

import bilboards.IClient;
import bilboards.IManager;
import bilboards.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.OrderTableRecord;

public class ClientController implements IClient {

	@FXML
	private TextArea advertisementContentTextArea;

	@FXML
	private TextField advertisementDurationTextField;

	@FXML
	private TableView<OrderTableRecord> advertisementsTableView;

	@FXML
	private Button placeAdvertisementButton;

	@FXML
	private Button cancelAdvertisementButton;

	@FXML
	private TableColumn<OrderTableRecord, Integer> recordIdColumn = new TableColumn("ID");

	@FXML
	private TableColumn<OrderTableRecord, String> contentColumn = new TableColumn("Content");

	@FXML
	private TableColumn<OrderTableRecord, Duration> durationColumn = new TableColumn("Duration");

	private ObservableList<OrderTableRecord> advertisements = FXCollections.observableArrayList();
	private String rmiRegistryHost;
	private int rmiRegistryPort;
	private String managerName;
	private int clientPort;
	private String clientName;
	private IClient clientInterface;
	private Registry registry;
	private int orderId;

	public void setClientData(String rmiRegistryHost, int rmiRegistryPort, String managerName, int clientPort,
			String clientName) {

		this.rmiRegistryHost = rmiRegistryHost;
		this.rmiRegistryPort = rmiRegistryPort;
		this.managerName = managerName;
		this.clientPort = clientPort;
		this.clientName = clientName;

		try {
			registry = LocateRegistry.getRegistry(this.rmiRegistryHost, this.rmiRegistryPort);
			clientInterface = (IClient) UnicastRemoteObject.exportObject(this, this.clientPort);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void initialize() {
		System.setProperty("java.security.policy", "./java.policy");
        System.setSecurityManager(new SecurityManager());
		
		recordIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
		contentColumn.setCellValueFactory(new PropertyValueFactory<>("advertText"));
		durationColumn.setCellValueFactory(new PropertyValueFactory<>("displayPeriod"));

		advertisementsTableView.getColumns().add(recordIdColumn);
		advertisementsTableView.getColumns().add(contentColumn);
		advertisementsTableView.getColumns().add(durationColumn);

		advertisementsTableView.setItems(advertisements);

	}

	@FXML
	void placeAdvertisementButtonOnAction(ActionEvent event) {
		Order order = new Order();
		order.advertText = advertisementContentTextArea.getText();
		order.client = clientInterface;
		try {
			order.displayPeriod = Duration.ofSeconds(Long.parseLong(advertisementDurationTextField.getText()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		IManager managerInterface;

		try {
			managerInterface = (IManager) registry.lookup(managerName);
			if (managerInterface.placeOrder(order)) {
				OrderTableRecord orderTableRecord = new OrderTableRecord(Integer.valueOf(orderId), order.advertText,
						order.displayPeriod);
				advertisements.add(orderTableRecord);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Error");
				alert.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void cancelAdvertisementButtonOnAction(ActionEvent event) {
		OrderTableRecord selectedItem = advertisementsTableView.getSelectionModel().getSelectedItem();
		IManager managerInterface;
		try {
			managerInterface = (IManager) registry.lookup(managerName);
			if (managerInterface.withdrawOrder(selectedItem.getOrderId().intValue())) {
				advertisements.remove(selectedItem);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Error");
				alert.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setOrderId(int orderId) throws RemoteException {
		this.orderId = orderId;
	}

}
