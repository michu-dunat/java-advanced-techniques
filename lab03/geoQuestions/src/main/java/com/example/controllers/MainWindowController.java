package com.example.controllers;

import java.text.ChoiceFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import com.example.data.CityTimeData;
import com.example.data.CountriesQuote;
import com.example.data.RequestMaker;

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
    private TextField firstAnswerTextField;

    @FXML
    private TextField secondAnswerTextField;

    @FXML
    private TextField thirdAnswerTextField;
    
    @FXML
    private TextField populationInputTextField;
    
    @FXML
    private ComboBox<String> cityFirstQuestionComboBox;
    
    @FXML
    private ComboBox<String> currencyComboBox;
    
    @FXML
    private ComboBox<String> cityThirdQuestionComboBox;
    
    @FXML
    private Label firstQuestionOutputLabel;

    @FXML
    private Label secondQuestionOutputLabel;

    @FXML
    private Label thirdQuestionOutputLabel;
    
    String language = "pl";
	String country = "PL";
	ResourceBundle resourceBundle;
    
    @FXML
	public void initialize() {
    	resourceBundle = ResourceBundle.getBundle("MyBundle", new Locale(language, country));
    	translate();
	}

    @FXML
    void firstQuestionCheckButtonOnAction(ActionEvent event) {
    	String population = populationInputTextField.getText();
    	String[] cityCodes = {"Q270", "Q1799", "Q588", "Q31487", "Q1792", "Q64", "Q90", "Q727", "Q220"};
    	int index = cityFirstQuestionComboBox.getSelectionModel().getSelectedIndex();
    	String url = RequestMaker.buildUrlForCountCities(cityCodes[index], population);
    	CountriesQuote countriesQuote = RequestMaker.makeRequestAndGetBodyForCount(url);
    	Long answer = countriesQuote.getMetadata().getTotalCount();
    	boolean isAnswerRight = answer.equals(Long.parseLong(firstAnswerTextField.getText()));
    	setColorOnAnswer(isAnswerRight, firstAnswerTextField);
    	firstQuestionOutputLabel.setText(formatFirstQuestionOutput(population, answer));
    	
    }

    @FXML
    void secondQuestionCheckButtonOnAction(ActionEvent event) {
    	String[] currencyCodes = {"PLN", "EUR", "USD", "JPY", "GBP", "CHF"};
    	int index = currencyComboBox.getSelectionModel().getSelectedIndex();
    	String url = RequestMaker.buildUrlForCountCountries(currencyCodes[index]);
    	CountriesQuote countriesQuote = RequestMaker.makeRequestAndGetBodyForCount(url);
    	Long answer = countriesQuote.getMetadata().getTotalCount();
    	boolean isAnswerRight = answer.equals(Long.parseLong(secondAnswerTextField.getText()));
    	setColorOnAnswer(isAnswerRight, secondAnswerTextField);
    	secondQuestionOutputLabel.setText(formatSecondQuestionOutput(answer));
    }

    @FXML
    void thirdQuestionCheckButtonOnAction(ActionEvent event) {
    	String[] cityCodes = {"Q60", "Q62", "Q1297", "Q16554", "Q649", "Q1490", "Q956", "Q987", "Q84", "Q270"};
    	int index = cityThirdQuestionComboBox.getSelectionModel().getSelectedIndex();
    	String url = RequestMaker.buildUrlForTime(cityCodes[index]);
    	CityTimeData cityTimeData = RequestMaker.makeRequestAndGetBodyForTime(url);
    	String answer = cityTimeData.getData().substring(0, 5);
    	boolean isAnswerRight = answer.equals(thirdAnswerTextField.getText());
    	setColorOnAnswer(isAnswerRight, thirdAnswerTextField);
    	thirdQuestionOutputLabel.setText(formatThirdQuestionOutput(answer));
    }
    
    private void setColorOnAnswer(boolean isAnswerRight, TextField textField) {
    	if (isAnswerRight) {
			textField.setStyle("-fx-control-inner-background: #00ff00;");
		}else {
			textField.setStyle("-fx-control-inner-background: #ff0000;");
		}
    }

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
    	resourceBundle = ResourceBundle.getBundle("MyBundle", locale);
    	translate();
    }
    
    void translate() {
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
    	
    	String[] cityFirstQuestion = resourceBundle.getString("cityListForFirstQuestion").split(",");
    	ObservableList<String> cityFirstQuestionObservableList = FXCollections.observableArrayList(cityFirstQuestion);
    	cityFirstQuestionComboBox.setItems(cityFirstQuestionObservableList);
    	
    	String[] currency = resourceBundle.getString("currencyList").split(",");
    	ObservableList<String> observableListCurrency = FXCollections.observableArrayList(currency);
    	currencyComboBox.setItems(observableListCurrency);
    	
    	String[] cityThirdQuestion = resourceBundle.getString("cityListForThirdQuestion").split(",");
    	ObservableList<String> cityThirdQuestionObservableList = FXCollections.observableArrayList(cityThirdQuestion);
    	cityThirdQuestionComboBox.setItems(cityThirdQuestionObservableList);
    	
    	firstQuestionOutputLabel.setText("");
    	secondQuestionOutputLabel.setText("");
    	thirdQuestionOutputLabel.setText("");
    }
    
    private String formatFirstQuestionOutput(String population, Long answer) {
    	MessageFormat messageFormat = new MessageFormat("");
    	messageFormat.setLocale(resourceBundle.getLocale());
    	Object[] arguments = {population, cityFirstQuestionComboBox.getSelectionModel().getSelectedItem(), answer};
    	messageFormat.applyPattern(resourceBundle.getString("firstQuestionOutput"));
    	String output = messageFormat.format(arguments);
    	return output;
    }
    
    private String formatSecondQuestionOutput(Long answer) {
    	MessageFormat messageFormat = new MessageFormat("");
    	messageFormat.setLocale(resourceBundle.getLocale());
    	double[] limits = {1, 2};
    	String[] values = {resourceBundle.getString("oneCountry"), resourceBundle.getString("moreCountries")};
    	ChoiceFormat choiceFormat = new ChoiceFormat(limits, values);
    	Format[] formats = {null, NumberFormat.getInstance(), choiceFormat};
    	messageFormat.applyPattern(resourceBundle.getString("secondQuestionOutput"));
    	messageFormat.setFormats(formats);
    	Object[] arguments = {currencyComboBox.getSelectionModel().getSelectedItem(), answer, answer};
    	String output = messageFormat.format(arguments);
    	return output;		
    }
    
    private String formatThirdQuestionOutput(String time) {
    	MessageFormat messageFormat = new MessageFormat("");
    	messageFormat.setLocale(resourceBundle.getLocale());
    	Object[] arguments = {cityThirdQuestionComboBox.getSelectionModel().getSelectedItem(), time};
    	messageFormat.applyPattern(resourceBundle.getString("thirdQuestionOutput"));
    	String output = messageFormat.format(arguments);
    	return output;
    }
    
}
