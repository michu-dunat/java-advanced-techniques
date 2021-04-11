module geoQuestions {
	exports com.example.data;
	exports com.example.app;
	exports com.example.controllers;
	
	opens com.example.app to spring.core;
	opens com.example.controllers to javafx.fxml;

	requires com.fasterxml.jackson.annotation;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires spring.beans;
	requires spring.boot;
	requires spring.boot.autoconfigure;
	requires spring.context;
	requires spring.core;
	requires spring.web;
}