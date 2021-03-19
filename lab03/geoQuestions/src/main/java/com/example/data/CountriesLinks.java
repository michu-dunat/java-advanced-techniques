package com.example.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountriesLinks {
	private String rel;
	private String href;
	
	public String getRel() {
		return rel;
	}
	
	public void setRel(String rel) {
		this.rel= rel;
	}
	
	public String getHref() {
		return href;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	
	@Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class CountriesLinks {\n");
	    sb.append("    rel: ").append(this.rel).append("\n");
	    sb.append("    href: ").append(this.href).append("\n");   
	    sb.append("}");
	    return sb.toString();
	  }
	
	
}
