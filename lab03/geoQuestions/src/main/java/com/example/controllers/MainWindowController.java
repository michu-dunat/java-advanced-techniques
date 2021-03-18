package com.example.controllers;

import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainWindowController {
	
	@FXML
	private Button printButton;
	
	public void printSomethingFromSpring(ActionEvent event) throws IOException {
		System.out.println("Hej");
	}

}
