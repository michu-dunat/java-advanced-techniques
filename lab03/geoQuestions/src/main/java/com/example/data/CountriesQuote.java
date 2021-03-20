package com.example.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountriesQuote {
	private Metadata metadata;

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CountriesData {\n");
		sb.append("    metadata: ").append(this.metadata).append("\n");
		sb.append("}");
		return sb.toString();
	}

}