package serviceloader.impl;

import java.util.ArrayList;

import com.google.auto.service.AutoService;

import ex.api.ClusterAnalysisService;
import ex.api.ClusteringException;
import ex.api.DataSet;

@AutoService(ClusterAnalysisService.class)
public class ClusterAnalysisServiceImplQuarterCalculator implements ClusterAnalysisService, Runnable {
	DataSet dataSet;
	Thread thread = new Thread(this);
	boolean toBeSorted = false;

	@Override
	public void setOptions(String[] options) throws ClusteringException {
		if (options[0].equals("true")) {
			toBeSorted = true;
		} else {
			toBeSorted = false;
		}
	}

	@Override
	public String getName() {
		return "Algorytm klasyfikuj¹cy æwiartkê do której przynale¿y dany punkt na uk³adzie wspó³rzêdnych.";
	}

	@Override
	public void submit(DataSet ds) throws ClusteringException {
		if (thread.isAlive()) {
			throw new ClusteringException("Trwa przetwarzanie danych.");
		}
		thread = new Thread(this);
		dataSet = ds;
		thread.start();
	}

	@Override
	public DataSet retrieve(boolean clear) throws ClusteringException {
		if (thread.isAlive() || dataSet.equals(null)) {
			return null;
		}

		DataSet output = dataSet;

		if (clear) {
			dataSet = null;
		}

		return output;
	}

	@Override
	public void run() {
		for (String[] record : dataSet.getData()) {
			String quarter = null;
			try {
				quarter = calculateQuarter(record[2], record[3]);
			} catch (ClusteringException e) {
				e.printStackTrace();
			}
			record[1] = quarter;
		}

		if (toBeSorted) {
			reorganizeDataSet();
		}
	}

	private String calculateQuarter(String x, String y) throws ClusteringException {
		try {
			Float.parseFloat(x);
			Float.parseFloat(y);
		} catch (Exception e) {
			throw new ClusteringException("B³edne dane.");
		}
		
		if (!x.startsWith("-") && !y.startsWith("-")) {
			return "Pierwsza æwiartka";
		} else if (x.startsWith("-") && !y.startsWith("-")) {
			return "Druga æwiartka";
		} else if (x.startsWith("-") && y.startsWith("-")) {
			return "Trzecia æwiartka";
		} else {
			return "Czwarta æwiartka";
		}
	}

	private void reorganizeDataSet() {
		ArrayList<String[]> firstQuarter = new ArrayList<>();
		ArrayList<String[]> secondQuarter = new ArrayList<>();
		ArrayList<String[]> thirdQuarter = new ArrayList<>();
		ArrayList<String[]> fourthQuarter = new ArrayList<>();
		ArrayList<String[]> allQuarters = new ArrayList<>();

		for (String[] record : dataSet.getData()) {
			switch (record[1]) {
			case "Pierwsza æwiartka":
				firstQuarter.add(record);
				break;
			case "Druga æwiartka":
				secondQuarter.add(record);
				break;
			case "Trzecia æwiartka":
				thirdQuarter.add(record);
				break;
			case "Czwarta æwiartka":
				fourthQuarter.add(record);
				break;

			default:
				break;
			}
		}

		allQuarters.addAll(firstQuarter);
		allQuarters.addAll(secondQuarter);
		allQuarters.addAll(thirdQuarter);
		allQuarters.addAll(fourthQuarter);

		String[][] organizedData = new String[allQuarters.size()][4];

		for (int i = 0; i < organizedData.length; i++) {
			organizedData[i] = allQuarters.get(i);
		}

		dataSet.setData(organizedData);

	}

}
