package com.position_swapping_processing;

import java.util.Random;

import com.processing.Processor;
import com.processing.Status;
import com.processing.StatusListener;

public class SwapWordPositionProcessor implements Processor, Runnable {
	private String[] input;
	private String result;
	private StatusListener statusListener;
	private String taskName = "Zamiana miejsacmi losowo wybranej pary wyrazów";
	private int inputLength;
	
	@Override
	public void run() {
		int progressController = this.inputLength / 10;
		
		if (progressController == 0) {
			progressController = 1;
		}
		
		for(int i = 0; i < this.inputLength; i++) {
			String temp = input[i];
			Random random = new Random();
			int randomInt;
			do {
				randomInt = random.nextInt(this.inputLength);
			} while (randomInt < i);
			input[i] = input[randomInt];
			input[randomInt] = temp;
			result += input[i];
			result += " ";
			
			try {
				Thread.sleep(30);
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
		this.input = task.split(" ");
		this.inputLength = input.length;
		this.result = "";
		Thread thread = new Thread(this);
		thread.start();
		return true;
	}
	
	@Override
	public String getInfo() {
		return "Algorytm zamieniaj¹cy losowo wybrane pary wyrazów miejscami.";
	}
	
	@Override
	public String getResult() {
		return this.result;
	}
	
}
