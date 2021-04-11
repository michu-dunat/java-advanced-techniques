package com.infantilization_processing;

import com.processing.Processor;
import com.processing.Status;
import com.processing.StatusListener;

public class InfantilizateProcessor implements Processor, Runnable {
	private String[] input;
	private String result;
	private StatusListener statusListener;
	private String taskName = "Pope³nianie b³edów jêzykowych";
	private int inputLength;
	
	@Override
	public void run() {
		int progressController = this.inputLength / 10;
		
		for(int i = 0; i < this.inputLength; i++) {
			String word = input[i];
			word = word.replaceAll("(?i)wzi¹æ", "wzi¹œæ");
			word = word.replaceAll("(?i)w ogóle", "wogóle");
			word = word.replaceAll("(?i)nie wiem", "niewiem");
			word = word.replaceAll("(?i)poza tym", "pozatym");
			word = word.replaceAll("(?i)na razie", "narazie");
			word = word.replaceAll("(?i)co najmniej", "conajmniej");
			word = word.replaceAll("(?i)wszed³em", "wesz³em");
			word = word.replaceAll("(?i)naprzeciwko", "na przeciwko");
			word = word.replaceAll("(?i)jako", "jak");
			
			word = word.replaceAll(",", "");
				
			word = word.replaceAll("(?i)jestem", "byæ");
			word = word.replaceAll("(?i)ñ", "ni");
			
			word = word.replaceAll("¹", "a");
			word = word.replaceAll("ê", "e");
			word = word.replaceAll("ó", "u");
			word = word.replaceAll("(?i)³", "l");
			word = word.replaceAll("(?i)¿", "rz");
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
		return "Algorytm wprowadzaj¹cy b³êdy jêzykowe.";
	}
	
	@Override
	public String getResult() {
		return this.result;
	}
	
}

