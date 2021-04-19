import ex.api.ClusterAnalysisService;

module cluster.client {
	exports serviceloader.controllers;
	exports serviceloader.client;

	requires cluster.api;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens serviceloader.controllers;
	
	uses ClusterAnalysisService;
}