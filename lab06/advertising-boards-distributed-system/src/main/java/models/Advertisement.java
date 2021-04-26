package models;

import bilboards.Order;

public class Advertisement {
	private Order order;
	private int orderId;
	private int billboardId;

	public Advertisement(Order order, int orderId, int billboardId) {
		super();
		this.order = order;
		this.orderId = orderId;
		this.billboardId = billboardId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getBillboardId() {
		return billboardId;
	}

	public void setBillboardId(int billboardId) {
		this.billboardId = billboardId;
	}

}
