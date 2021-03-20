package com.example.app;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

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
	
	private static RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(GeoQuestionsApplication.class);
		builder.headless(false).web(WebApplicationType.NONE);
		ConfigurableApplicationContext context = builder.run(args);
		restTemplate = context.getBean(RestTemplate.class);
		
		launch(args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	public static RestTemplate getRestTemplate() {
		return restTemplate;
	}

}
