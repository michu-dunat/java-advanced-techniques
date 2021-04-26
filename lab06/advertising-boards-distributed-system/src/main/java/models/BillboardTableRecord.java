package models;

public class BillboardTableRecord {

	private Integer billboardId;
	private Integer bufforSize;
	private Integer freeSlots;

	public BillboardTableRecord(Integer billboardId, Integer bufforSize, Integer freeSlots) {
		super();
		this.billboardId = billboardId;
		this.bufforSize = bufforSize;
		this.freeSlots = freeSlots;
	}

	public Integer getBillboardId() {
		return billboardId;
	}

	public void setBillboardId(Integer billboardId) {
		this.billboardId = billboardId;
	}

	public Integer getFreeSlots() {
		return freeSlots;
	}

	public void setFreeSlots(Integer freeSlots) {
		this.freeSlots = freeSlots;
	}

	public Integer getBufforSize() {
		return bufforSize;
	}

	public void setBufforSize(Integer bufforSize) {
		this.bufforSize = bufforSize;
	}

}
