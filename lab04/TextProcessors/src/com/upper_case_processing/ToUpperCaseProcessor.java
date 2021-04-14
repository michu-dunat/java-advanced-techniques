package com.upper_case_processing;

import com.processing.Processor;
import com.processing.Status;
import com.processing.StatusListener;

public class ToUpperCaseProcessor implements Processor, Runnable {
	private String input;
	private String result;
	private StatusListener statusListener;
	private String taskName = "Zmiana wielkoœci liter na du¿¹";
	private int inputLength;
	
	@Override
	public void run() {
		int progressController = this.inputLength / 10;
		
		if (progressController == 0) {
			progressController = 1;
		}
		
		for(int i = 0; i < this.inputLength; i++) {
			this.result += Character.toUpperCase(input.charAt(i));
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (i % progressController == 0) {
				this.statusListener.statusChanged(new Status (this.taskName, i * 100 / this.inputLength));
			}
		}
		this.statusListener.statusChanged(new Status(this.taskName, 100));
	}
	
	@Override
	public boolean submitTask(String task, StatusListener statusListener) {
		this.statusListener = statusListener;
		this.input = task;
		this.inputLength = input.length();
		this.result = "";
		Thread thread = new Thread(this);
		thread.start();
		return true;
	}
	
	@Override
	public String getInfo() {
		return "Algorytm zmieniaj¹cy wielkoœæ liter na du¿¹.";
	}
	
	@Override
	public String getResult() {
		return this.result;
	}
	
}
