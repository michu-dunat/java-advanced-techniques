import ex.api.ClusterAnalysisService;
import serviceloader.impl.ClusterAnalysisServiceImplNumberTypeDeterminator;
import serviceloader.impl.ClusterAnalysisServiceImplQuarterCalculator;

module cluster.impl {
	exports serviceloader.impl;

	requires cluster.api;
	//requires com.google.auto.service;
	
	provides ClusterAnalysisService
	with ClusterAnalysisServiceImplNumberTypeDeterminator, ClusterAnalysisServiceImplQuarterCalculator;
}