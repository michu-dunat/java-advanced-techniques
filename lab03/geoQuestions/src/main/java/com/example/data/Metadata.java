package com.example.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata {
	private long currentOffset;
	private long totalCount;

	public long getCurrentOffset() {
		return currentOffset;
	}

	public void setCurrentOffset(long currentOffset) {
		this.currentOffset = currentOffset;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Metadata {\n");
		sb.append("    currenctOffset: ").append(this.currentOffset).append("\n");
		sb.append("    totalCount: ").append(this.totalCount).append("\n");
		sb.append("}");
		return sb.toString();
	}
}
