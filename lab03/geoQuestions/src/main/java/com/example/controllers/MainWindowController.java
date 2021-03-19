package com.example.controllers;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainWindowController {
	
	@FXML
    private Button translateButton;

    @FXML
    private Label firstQuestionLabel;
    
    @FXML
    private Label firstQuestionPart2Label;

    @FXML
    private Label secondQuestionLabel;
    
    @FXML
    private Label secondQuestionPart2Label;

    @FXML
    private Label thirdQuestionLabel;

    @FXML
    private Button firstQuestionCheckButton;

    @FXML
    private Button secondQuestionCheckButton;

    @FXML
    private Button thirdQuestionCheckButton;
    
    @FXML
    private Label userFirstAnswerLabel;
    
    @FXML
    private Label userSecondAnswerLabel;

    @FXML
    private Label userThirdAnswerLabel;

    @FXML
    void firstQuestionCheckButtonOnAction(ActionEvent event) {

    }

    @FXML
    void secondQuestionCheckButtonOnAction(ActionEvent event) {

    }

    @FXML
    void thirdQuestionCheckButtonOnAction(ActionEvent event) {

    }
    
    String language = "pl";
	String country = "PL";

    @FXML
    void translateButtonOnAction(ActionEvent event) {
    	if (language.equals("pl") || country.equals("PL")) {
    		language = "en";
    		country = "EN";
		} else {
			language = "pl";
			country = "PL";
		}
    	Locale locale = new Locale(language, country);
    	ResourceBundle resourceBundle = ResourceBundle.getBundle("MyBundle", locale);
    	
    	firstQuestionLabel.setText(resourceBundle.getString("firstQuestion"));
    	firstQuestionPart2Label.setText(resourceBundle.getString("firstQuestionPart2"));
    	secondQuestionLabel.setText(resourceBundle.getString("secondQuestion"));
    	secondQuestionPart2Label.setText(resourceBundle.getString("secondQuestionPart2"));
    	thirdQuestionLabel.setText(resourceBundle.getString("thirdQuestion"));
    	
    	userFirstAnswerLabel.setText(resourceBundle.getString("userAnswer"));
    	userSecondAnswerLabel.setText(resourceBundle.getString("userAnswer"));
    	userThirdAnswerLabel.setText(resourceBundle.getString("userAnswer"));
    	
    	
    	firstQuestionCheckButton.setText(resourceBundle.getString("check"));
    	secondQuestionCheckButton.setText(resourceBundle.getString("check"));
    	thirdQuestionCheckButton.setText(resourceBundle.getString("check"));
    	
    	
    }

}
