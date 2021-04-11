package com.infantilization_processing;

import com.processing.Processor;
import com.processing.Status;
import com.processing.StatusListener;

public class InfantilizateProcessor implements Processor, Runnable {
	private String[] input;
	private String result;
	private StatusListener statusListener;
	private String taskName = "Pope�nianie b�ed�w j�zykowych";
	private int inputLength;
	
	@Override
	public void run() {
		int progressController = this.inputLength / 10;
		
		for(int i = 0; i < this.inputLength; i++) {
			String word = input[i];
			word = word.replaceAll("(?i)wzi��", "wzi���");
			word = word.replaceAll("(?i)w og�le", "wog�le");
			word = word.replaceAll("(?i)nie wiem", "niewiem");
			word = word.replaceAll("(?i)poza tym", "pozatym");
			word = word.replaceAll("(?i)na razie", "narazie");
			word = word.replaceAll("(?i)co najmniej", "conajmniej");
			word = word.replaceAll("(?i)wszed�em", "wesz�em");
			word = word.replaceAll("(?i)naprzeciwko", "na przeciwko");
			word = word.replaceAll("(?i)jako", "jak");
			
			word = word.replaceAll(",", "");
				
			word = word.replaceAll("(?i)jestem", "by�");
			word = word.replaceAll("(?i)�", "ni");
			
			word = word.replaceAll("�", "a");
			word = word.replaceAll("�", "e");
			word = word.replaceAll("�", "u");
			word = word.replaceAll("(?i)�", "l");
			word = word.replaceAll("(?i)�", "rz");
			word += " ";
			result += word;
			
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
		return "Algorytm wprowadzaj�cy b��dy j�zykowe.";
	}
	
	@Override
	public String getResult() {
		return this.result;
	}
	
}

