package serviceloader.impl;

import java.util.ArrayList;


import ex.api.ClusterAnalysisService;
import ex.api.ClusteringException;
import ex.api.DataSet;

public class ClusterAnalysisServiceImplNumberTypeDeterminator implements ClusterAnalysisService, Runnable {
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
		return "Algorytm okreúlajπcy czy dana liczba jest rzeczywsita, zespolona bπdü urojona.";
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
			String numberType = null;
			try {
				numberType = determineNumberType(record[2], record[3]);
			} catch (ClusteringException e) {
				e.printStackTrace();
			}
			record[1] = numberType;
		}

		if (toBeSorted) {
			reorganizeDataSet();
		}
	}
	
	private String determineNumberType(String a, String b) throws ClusteringException {
		if (a.contains("i") || (!b.equals("0") && !b.contains("i"))) {
			throw new ClusteringException("B≥Ídne dane.");
		}
		if (b.contains("i") && !a.equals("0")) {
			return "Liczba zespolona";
		} else if (b.contains("i") && a.equals("0")) {
			return "Liczba urojona";
		} else {
			return "Liczba rzeczywista";
		}
	}
	
	private void reorganizeDataSet() {
		ArrayList<String[]> realNumbers = new ArrayList<>();
		ArrayList<String[]> complexNumbers = new ArrayList<>();
		ArrayList<String[]> imaginaryNumbers = new ArrayList<>();
		ArrayList<String[]> allNumbers = new ArrayList<>();

		for (String[] record : dataSet.getData()) {
			switch (record[1]) {
			case "Liczba rzeczywista":
				realNumbers.add(record);
				break;
			case "Liczba zespolona":
				complexNumbers.add(record);
				break;
			case "Liczba urojona":
				imaginaryNumbers.add(record);
				break;

			default:
				break;
			}
		}

		allNumbers.addAll(realNumbers);
		allNumbers.addAll(complexNumbers);
		allNumbers.addAll(imaginaryNumbers);

		String[][] organizedData = new String[allNumbers.size()][4];

		for (int i = 0; i < organizedData.length; i++) {
			organizedData[i] = allNumbers.get(i);
		}

		dataSet.setData(organizedData);

	}

}
