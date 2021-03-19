package com.example.app;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.data.CityTimeData;
import com.example.data.Metadata;
import com.example.data.CountriesQuote;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

@SpringBootApplication
public class GeoQuestionsApplication extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(GeoQuestionsApplication.class);
		builder.headless(false).web(WebApplicationType.NONE);
		ConfigurableApplicationContext context = builder.run(args);
		
		
		RestTemplate restTemplate = context.getBean(RestTemplate.class);

		// request url
		String url = "https://wft-geo-db.p.rapidapi.com/v1/geo/cities/Q60/nearbyCities?radius=100&minPopulation=1000000";

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-rapidapi-key", "10b9e8e422msh6aa3da9800a8072p11911cjsncd509e7e86f7");
		headers.add("x-rapidapi-host", "wft-geo-db.p.rapidapi.com");
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// build the request
		HttpEntity<String> request = new HttpEntity<String>(headers);

		// make an HTTP GET request with headers
		ResponseEntity<CountriesQuote> response = restTemplate.exchange(url, HttpMethod.GET, request, CountriesQuote.class);

		// check response
		if (response.getStatusCode() == HttpStatus.OK) {
			System.out.println("Request Successful.");
			System.out.println(response.getBody());
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}

		CountriesQuote quote = response.getBody();
		
		launch(args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
