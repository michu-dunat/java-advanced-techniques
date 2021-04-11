package com.example.data;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.example.app.GeoQuestionsApplication;

public class RequestMaker {

	private static HttpEntity<String> createHeadersAndBuildRequest() {
		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-rapidapi-key", "10b9e8e422msh6aa3da9800a8072p11911cjsncd509e7e86f7");
		headers.add("x-rapidapi-host", "wft-geo-db.p.rapidapi.com");
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// build the request
		HttpEntity<String> request = new HttpEntity<String>(headers);

		return request;
	}

	public static String buildUrlForCountCities(String cityCode, String population) {
		StringBuilder stringBuilder = new StringBuilder("https://wft-geo-db.p.rapidapi.com/v1/geo/cities/");
		stringBuilder.append(cityCode);
		stringBuilder.append("/nearbyCities?radius=25&minPopulation=");
		stringBuilder.append(population);
		return stringBuilder.toString();
	}

	public static String buildUrlForTime(String cityCode) {
		StringBuilder stringBuilder = new StringBuilder("https://wft-geo-db.p.rapidapi.com/v1/geo/cities/");
		stringBuilder.append(cityCode);
		stringBuilder.append("/time");
		return stringBuilder.toString();
	}

	public static String buildUrlForCountCountries(String currencyCode) {
		StringBuilder stringBuilder = new StringBuilder(
				"https://wft-geo-db.p.rapidapi.com/v1/geo/countries?currencyCode=");
		stringBuilder.append(currencyCode);
		return stringBuilder.toString();
	}

	public static CountriesQuote makeRequestAndGetBodyForCount(String url) {
		// make an HTTP GET request with headers
		HttpEntity<String> request = createHeadersAndBuildRequest();
		ResponseEntity<CountriesQuote> response = GeoQuestionsApplication.getRestTemplate().exchange(url,
				HttpMethod.GET, request, CountriesQuote.class);

		CountriesQuote quote = response.getBody();

		return quote;
	}

	public static CityTimeData makeRequestAndGetBodyForTime(String url) {
		// make an HTTP GET request with headers
		HttpEntity<String> request = createHeadersAndBuildRequest();
		ResponseEntity<CityTimeData> response = GeoQuestionsApplication.getRestTemplate().exchange(url, HttpMethod.GET,
				request, CityTimeData.class);

		CityTimeData quote = response.getBody();

		return quote;
	}

}
