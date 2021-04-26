package controllers;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import bilboards.IBillboard;
import bilboards.IManager;
import bilboards.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Advertisement;
import models.BillboardTableRecord;

public class ManagerController implements IManager {

	@FXML
	private TableView<BillboardTableRecord> blllboardsTable;

	@FXML
	private TableColumn<BillboardTableRecord, Integer> recordIdColumn = new TableColumn("ID");

	@FXML
	private TableColumn<BillboardTableRecord, Integer> bufforSizeColumn = new TableColumn("Buffor size");

	@FXML
	private TableColumn<BillboardTableRecord, Integer> freeSlotsColumn = new TableColumn("Free slots");

	private ObservableList<BillboardTableRecord> billboards = FXCollections.observableArrayList();
	private int rmiRegistryPort;
	private int managerPort;
	private String managerName;
	private Registry registry;
	private IManager managerInterface;
	private HashMap<Integer, IBillboard> idAndBillboardMap;
	private HashMap<Integer, Advertisement> idAndAdvertisementsMap;
	private int billboardIdCounter;
	private int advertisementIdCounter;

	@FXML
	public void initialize() {
		System.setProperty("java.security.policy", "./java.policy");
        System.setSecurityManager(new SecurityManager());
		
		recordIdColumn.setCellValueFactory(new PropertyValueFactory<>("billboardId"));
		bufforSizeColumn.setCellValueFactory(new PropertyValueFactory<>("bufforSize"));
		freeSlotsColumn.setCellValueFactory(new PropertyValueFactory<>("freeSlots"));

		blllboardsTable.getColumns().add(recordIdColumn);
		blllboardsTable.getColumns().add(bufforSizeColumn);
		blllboardsTable.getColumns().add(freeSlotsColumn);

		blllboardsTable.setItems(billboards);

		idAndBillboardMap = new HashMap<>();
		idAndAdvertisementsMap = new HashMap<>();

		billboardIdCounter = 0;
		advertisementIdCounter = 0;

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				int billboardId, freeSlots;
				
				while (true) {
					synchronized (idAndBillboardMap) {
						for (int i = 0; i < billboards.size(); ++i) {
							billboardId = billboards.get(i).getBillboardId();
							
							try {
								freeSlots = idAndBillboardMap.get(billboardId).getCapacity()[1];
								billboards.get(i).setFreeSlots(freeSlots);
								blllboardsTable.refresh();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

	public void setManagerData(int rmiRegistryPort, int managerPort, String managerName) {

		this.rmiRegistryPort = rmiRegistryPort;
		this.managerPort = managerPort;
		this.managerName = managerName;

		try {
			registry = LocateRegistry.createRegistry(this.rmiRegistryPort);
			managerInterface = (IManager) UnicastRemoteObject.exportObject(this, this.managerPort);
			registry.bind(this.managerName, managerInterface);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int bindBillboard(IBillboard billboard) throws RemoteException {
		synchronized (idAndBillboardMap) {
			idAndBillboardMap.put(billboardIdCounter, billboard);
			
			BillboardTableRecord billboardTableRecord = new BillboardTableRecord(billboardIdCounter,
					billboard.getCapacity()[0], billboard.getCapacity()[1]);
			billboards.add(billboardTableRecord);
			
			billboardIdCounter++;
		}
		
		billboard.start();
		
		return billboardIdCounter - 1;
	}

	public boolean unbindBillboard(int billboardId) throws RemoteException {
		/*
		 * boolean isUnbound = false; synchronized (idAndBillboardMap) {
		 * BillboardTableRecord billboardTableRecord = null; for (int i = 0; i <
		 * billboards.size(); i++) { if (billboardId ==
		 * billboards.get(i).getBillboardId()) { billboardTableRecord =
		 * billboards.get(i); } } idAndBillboardMap.remove(billboardId).stop();
		 * billboards.remove(billboardTableRecord); isUnbound = true; } return
		 * isUnbound;
		 */
		return true;
	}

	public boolean placeOrder(Order order) throws RemoteException {
		if (order == null || order.advertText == null || order.displayPeriod == null || order.client == null)
			return false;

		order.client.setOrderId(advertisementIdCounter);
		
		boolean isAddedAtAll = false;

		for (int i = 0; i < billboards.size(); ++i) {
			if (billboards.get(i).getFreeSlots() > 0) {
				IBillboard billboard = idAndBillboardMap.get(billboards.get(i).getBillboardId());
				billboard.addAdvertisement(order.advertText, order.displayPeriod, advertisementIdCounter);
				isAddedAtAll = true;

				Advertisement ad = new Advertisement(order, advertisementIdCounter, billboards.get(i).getBillboardId());
				idAndAdvertisementsMap.put(advertisementIdCounter, ad);
			}
		}
		
		if (!isAddedAtAll)
			return false;

		advertisementIdCounter++;

		return true;
	}

	public boolean withdrawOrder(int orderId) throws RemoteException {
		Advertisement advertisementToBeWithdrawed = idAndAdvertisementsMap.get(orderId);

		if (advertisementToBeWithdrawed == null)
			return false;

		for (IBillboard billboard : idAndBillboardMap.values()) {
			billboard.removeAdvertisement(orderId);
		}

		idAndAdvertisementsMap.remove(orderId);

		return true;
	}

}
