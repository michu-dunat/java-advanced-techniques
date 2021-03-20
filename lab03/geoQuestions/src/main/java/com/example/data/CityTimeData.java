package com.example.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CityTimeData {
	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CityTimeData {\n");
		sb.append("    data: ").append(this.data).append("\n");
		sb.append("}");
		return sb.toString();
	}
}
