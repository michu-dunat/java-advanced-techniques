package models;

import java.time.Duration;

public class OrderTableRecord {
	private Integer orderId;
	private String advertText;
	private Duration displayPeriod;

	public OrderTableRecord(Integer orderId, String advertText, Duration displayPeriod) {
		super();
		this.orderId = orderId;
		this.advertText = advertText;
		this.displayPeriod = displayPeriod;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getAdvertText() {
		return advertText;
	}

	public void setAdvertText(String advertText) {
		this.advertText = advertText;
	}

	public Duration getDisplayPeriod() {
		return displayPeriod;
	}

	public void setDisplayPeriod(Duration displayPeriod) {
		this.displayPeriod = displayPeriod;
	}

	@Override
	public String toString() {
		return "OrderTableRow [orderId=" + orderId + ", advertText=" + advertText + ", displayPeriod=" + displayPeriod
				+ "]";
	}

}

