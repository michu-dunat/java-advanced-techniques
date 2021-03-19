package com.example.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountriesQuote {
	//private CountriesData[] data;
	//private CountriesLinks[] links;
	private Metadata metadata;
	
	/*
	 * public CountriesData[] getData() { return data; }
	 * 
	 * public void setData(CountriesData[] data) { this.data = data; }
	 */
	
	/*
	 * public CountriesLinks[] getLinks() { return links; }
	 * 
	 * public void setLinks(CountriesLinks[] links) { this.links = links; }
	 */
	
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
	    //sb.append("    data: ").append(this.data.toString()).append("\n");
	    //sb.append("    links: ").append(this.links.toString()).append("\n");
	    sb.append("    metadata: ").append(this.metadata).append("\n");
	    sb.append("}");
	    return sb.toString();
	  }
	
	

	      
}
