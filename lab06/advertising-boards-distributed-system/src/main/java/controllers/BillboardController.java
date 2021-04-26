package controllers;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import bilboards.IBillboard;
import bilboards.IManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import models.OrderTableRecord;

public class BillboardController implements IBillboard {

	@FXML
	private TextArea billboardTextArea;

	private String rmiRegistryHost;
	private int rmiRegistryPort;
	private String managerName;
	private int billboardPort;
	private String billboardName;
	private int capacity;
	private Duration displayInterval;
	private Registry registry;
	private IBillboard billboardInterface;
	private int billboardId;
	private Thread displayThread;
	private boolean isThreadFinish;

	private HashMap<Integer, OrderTableRecord> orderIdAndAdvertisements;
	private ArrayList<OrderTableRecord> displayQueue;
	private String defaultMessage = "PLACE YOUR ADVERTISEMENT HERE!!!";

	public void setBillboardData(String rmiRegistryHost, int rmiRegistryPort, String managerName, int billboardPort,
			String billboardName, int capacity, Duration displayInterval) {

		this.rmiRegistryHost = rmiRegistryHost;
		this.rmiRegistryPort = rmiRegistryPort;
		this.managerName = managerName;
		this.billboardPort = billboardPort;
		this.billboardName = billboardName;
		this.capacity = capacity;
		this.displayInterval = displayInterval;

		try {
			registry = LocateRegistry.getRegistry(this.rmiRegistryHost, this.rmiRegistryPort);
			billboardInterface = (IBillboard) UnicastRemoteObject.exportObject(this, this.billboardPort);
			IManager managerInterface = (IManager) registry.lookup(this.managerName);
			billboardId = managerInterface.bindBillboard(billboardInterface);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void initialize() {
		System.setProperty("java.security.policy", "./java.policy");
        System.setSecurityManager(new SecurityManager());
		
		isThreadFinish = false;
		orderIdAndAdvertisements = new HashMap<>();
		displayQueue = new ArrayList<>();
		billboardTextArea.setEditable(false);

	}

	public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException {
		if (orderIdAndAdvertisements.size() < capacity) {
			OrderTableRecord newAdvertisement = new OrderTableRecord(orderId, advertText, displayPeriod);
			
			synchronized (orderIdAndAdvertisements) {
				orderIdAndAdvertisements.put(Integer.valueOf(orderId), newAdvertisement);
				displayQueue.add(newAdvertisement);
			}
			
			return true;
		}
		
		return false;
	}

	public boolean removeAdvertisement(int orderId) throws RemoteException {
		synchronized (orderIdAndAdvertisements) {
			OrderTableRecord advertisementToBeRemoved = orderIdAndAdvertisements.remove(Integer.valueOf(orderId));
			
			if (advertisementToBeRemoved != null) {
				displayQueue.remove(advertisementToBeRemoved);
				return true;
			}
		}
		
		return false;
	}

	public int[] getCapacity() throws RemoteException {
		return new int[] { capacity, capacity - orderIdAndAdvertisements.size() };
	}

	public void setDisplayInterval(Duration displayInterval) throws RemoteException {
		this.displayInterval = displayInterval;
	}

	public boolean start() throws RemoteException {
		if (displayThread == null) {
			isThreadFinish = false;

			displayThread = new Thread(new Runnable() {

				@Override
				public void run() {

					while (isThreadFinish != true) {
						Duration duration = Duration.ofMillis(50);

						if (orderIdAndAdvertisements.size() > 0) {

							synchronized (orderIdAndAdvertisements) {
								OrderTableRecord advertisementToBeDisplayed = displayQueue.get(0);
								billboardTextArea.setText(advertisementToBeDisplayed.getAdvertText());
								displayQueue.remove(0);

								if (advertisementToBeDisplayed.getDisplayPeriod().compareTo(displayInterval) <= 0) {
									duration = advertisementToBeDisplayed.getDisplayPeriod();
									orderIdAndAdvertisements.remove(advertisementToBeDisplayed.getOrderId());
								} else {
									displayQueue.add(advertisementToBeDisplayed);
									duration = displayInterval;
									advertisementToBeDisplayed.setDisplayPeriod(advertisementToBeDisplayed.getDisplayPeriod().minus(displayInterval));
								}
							}
						} else {
							billboardTextArea.setText(defaultMessage);
						}

						try {
							Thread.sleep(duration.toMillis());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			
			displayThread.start();
			return true;
		}
		
		return false;
	}

	public boolean stop() throws RemoteException {
		isThreadFinish = true;
		displayThread = null;
		return true;
	}

}
