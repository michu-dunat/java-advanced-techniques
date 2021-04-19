package serviceloader.controllers;

public class TableRecord {
	private String recordId;
	private String categoryId;
	private String firstValue;
	private String secondValue;

	public TableRecord(String recordId, String categoryId, String firstValue, String secondValue) {
		super();
		this.recordId = recordId;
		this.categoryId = categoryId;
		this.firstValue = firstValue;
		this.secondValue = secondValue;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getFirstValue() {
		return firstValue;
	}

	public void setFirstValue(String firstValue) {
		this.firstValue = firstValue;
	}

	public String getSecondValue() {
		return secondValue;
	}

	public void setSecondValue(String secondValue) {
		this.secondValue = secondValue;
	}

	@Override
	public String toString() {
		return "TableRecord [recordId=" + recordId + ", categoryId=" + categoryId + ", firstValue=" + firstValue
				+ ", secondValue=" + secondValue + "]";
	}

}
