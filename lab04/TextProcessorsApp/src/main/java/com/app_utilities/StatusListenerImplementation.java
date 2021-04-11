package com.app_utilities;

import com.processing.Status;
import com.processing.StatusListener;

public class StatusListenerImplementation implements StatusListener {
	
	private int progress;

	@Override
	public void statusChanged(Status status) {
		progress = status.getProgress();
	}

	public int getProgress() {
		return progress;
	}

}
