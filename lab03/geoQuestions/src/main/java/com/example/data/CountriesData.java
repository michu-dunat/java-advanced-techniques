package com.example.data;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountriesData {
	
	private String code;
	private String[] currencyCodes;
	private String name;
	private String wikiDataId;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String[] getCurrencyCodes() {
		return currencyCodes;
	}
	public void setCurrencyCodes(String[] currencyCodes) {
		this.currencyCodes = currencyCodes;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWikiDataId() {
		return wikiDataId;
	}
	public void setWikiDataId(String wikiDataId) {
		this.wikiDataId = wikiDataId;
	}
	
	@Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class CountriesData {\n");
	    sb.append("    code: ").append(this.code).append("\n");
	    sb.append("    currencyCodes: ").append(this.currencyCodes.toString()).append("\n");
	    sb.append("    name: ").append(this.name).append("\n");
	    sb.append("    wikiDataId: ").append(this.wikiDataId).append("\n");
	    sb.append("}");
	    return sb.toString();
	  }
	
	
	
	
	
	

}
