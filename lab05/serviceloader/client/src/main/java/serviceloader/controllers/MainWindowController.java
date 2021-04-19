package serviceloader.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ServiceLoader;

import ex.api.ClusterAnalysisService;
import ex.api.ClusteringException;
import ex.api.DataSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainWindowController {

	@FXML
	private ListView<String> algorithmListView;

	@FXML
	private TableView<TableRecord> inputTableView;

	@FXML
	private TableView<TableRecord> outputTableView;

	@FXML
	private TextField aOrXInput;

	@FXML
	private TextField bOrYInput;

	@FXML
	private Button addNewRowInput;

	@FXML
	private Button analyzeButton;

	@FXML
	private CheckBox clearCheckBox;

	@FXML
	private TableColumn<TableRecord, String> recordIdColumn = new TableColumn<>("ID");

	@FXML
	private TableColumn<TableRecord, String> categoryIdColumn = new TableColumn<>("Kategoria");

	@FXML
	private TableColumn<TableRecord, String> aOrXColumn = new TableColumn<>("a/X");

	@FXML
	private TableColumn<TableRecord, String> bOrYColumn = new TableColumn<>("b/Y");

	@FXML
	private TableColumn<TableRecord, String> recordIdOutputColumn = new TableColumn<>("ID");

	@FXML
	private TableColumn<TableRecord, String> categoryIdOutputColumn = new TableColumn<>("Kategoria");

	@FXML
	private TableColumn<TableRecord, String> aOrXOutputColumn = new TableColumn<>("a/X");

	@FXML
	private TableColumn<TableRecord, String> bOrYOutputColumn = new TableColumn<>("b/Y");

	@FXML
	private Button deleteRowButton;

	@FXML
	private Button mockNumbersButton;

	@FXML
	private Button mockPointsButton;

	@FXML
	private CheckBox toBoSortedComboBox;

	@FXML
	private Button retriveOutputButton;

	private int counter = 0;

	HashMap<String, ClusterAnalysisService> algorithmInfoAndServiceMap = new HashMap<>();
	ArrayList<String> algorithmsInfo = new ArrayList<>();
	private ObservableList<TableRecord> data = FXCollections.observableArrayList();
	private ObservableList<TableRecord> outputData = FXCollections.observableArrayList();

	@FXML
	public void initialize() {
		ServiceLoader<ClusterAnalysisService> loader = ServiceLoader.load(ClusterAnalysisService.class);
		for (ClusterAnalysisService service : loader) {
			algorithmInfoAndServiceMap.put(service.getName(), service);
			algorithmsInfo.add(service.getName());
		}

		algorithmListView.getItems().addAll(FXCollections.observableArrayList(algorithmsInfo));

		algorithmListView.getSelectionModel().selectFirst();

		aOrXInput.setPromptText("a/X");
		bOrYInput.setPromptText("b/Y");

		recordIdColumn.setCellValueFactory(new PropertyValueFactory<>("recordId"));
		categoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
		aOrXColumn.setCellValueFactory(new PropertyValueFactory<>("firstValue"));
		bOrYColumn.setCellValueFactory(new PropertyValueFactory<>("secondValue"));

		recordIdOutputColumn.setCellValueFactory(new PropertyValueFactory<>("recordId"));
		categoryIdOutputColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
		aOrXOutputColumn.setCellValueFactory(new PropertyValueFactory<>("firstValue"));
		bOrYOutputColumn.setCellValueFactory(new PropertyValueFactory<>("secondValue"));

		categoryIdOutputColumn.setMinWidth(110);

		recordIdColumn.setResizable(false);
		categoryIdColumn.setResizable(false);
		aOrXColumn.setResizable(false);
		bOrYColumn.setResizable(false);
		recordIdOutputColumn.setResizable(false);
		categoryIdOutputColumn.setResizable(false);
		aOrXOutputColumn.setResizable(false);
		bOrYOutputColumn.setResizable(false);

		inputTableView.getColumns().add(recordIdColumn);
		inputTableView.getColumns().add(categoryIdColumn);
		inputTableView.getColumns().add(aOrXColumn);
		inputTableView.getColumns().add(bOrYColumn);

		outputTableView.getColumns().add(recordIdOutputColumn);
		outputTableView.getColumns().add(categoryIdOutputColumn);
		outputTableView.getColumns().add(aOrXOutputColumn);
		outputTableView.getColumns().add(bOrYOutputColumn);

		inputTableView.setItems(data);
		outputTableView.setItems(outputData);
	}

	DataSet ds;
	ClusterAnalysisService service;

	@FXML
	void analyzeButtonOnAction(ActionEvent event) {
		outputData.clear();

		service = algorithmInfoAndServiceMap.get(algorithmListView.getSelectionModel().getSelectedItem());

		String[][] dataForDataSet = new String[inputTableView.getItems().size()][4];

		for (int i = 0; i < dataForDataSet.length; i++) {
			TableRecord tableRecord = inputTableView.getItems().get(i);
			String[] record = { tableRecord.getRecordId(), tableRecord.getCategoryId(), tableRecord.getFirstValue(),
					tableRecord.getSecondValue() };
			dataForDataSet[i] = record;
		}

		ds = new DataSet();
		ds.setData(dataForDataSet);
		
		if (algorithmListView.getSelectionModel().getSelectedIndex() == 0) {
			String[] headers = {"RecordID", "CategoryID", "a", "b"};
			ds.setHeader(headers);
		} else {
			String[] headers = {"RecordID", "CategoryID", "X", "Y"};
			ds.setHeader(headers);
		}

		try {
			String[] options = { Boolean.toString(toBoSortedComboBox.isSelected()) };
			service.setOptions(options);
			service.submit(ds);
		} catch (ClusteringException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void retriveOutputButtonOnAction(ActionEvent event) {
		String[][] dataForDataSet = new String[inputTableView.getItems().size()][4];
		service = algorithmInfoAndServiceMap.get(algorithmListView.getSelectionModel().getSelectedItem());
		try {
			ds = service.retrieve(clearCheckBox.isSelected());
		} catch (ClusteringException e) {
			e.printStackTrace();
		}
		dataForDataSet = ds.getData();
		for (int i = 0; i < dataForDataSet.length; i++) {
			TableRecord tableRecord = new TableRecord(dataForDataSet[i][0], dataForDataSet[i][1], dataForDataSet[i][2],
					dataForDataSet[i][3]);
			outputData.add(tableRecord);
		}
	}

	@FXML
	void deleteRowButtonOnAction(ActionEvent event) {
		data.remove(inputTableView.getSelectionModel().getSelectedItem());
	}

	@FXML
	void addNewRowInput(ActionEvent event) {
		String firstInput = aOrXInput.getText();
		String secondInput = bOrYInput.getText();
		TableRecord newRow = new TableRecord(Integer.toString(counter), "-1", firstInput, secondInput);
		counter++;
		data.add(newRow);
	}

	@FXML
	void mockNumbersButtonOnAction(ActionEvent event) {
		data.clear();
		data.add(new TableRecord("0", "-1", "5", "0"));
		data.add(new TableRecord("1", "-1", "-10", "99i"));
		data.add(new TableRecord("2", "-1", "3", "5i"));
		data.add(new TableRecord("3", "-1", "4", "0"));
		data.add(new TableRecord("4", "-1", "0", "0"));
		data.add(new TableRecord("5", "-1", "0", "5i"));
		data.add(new TableRecord("6", "-1", "1", "-4i"));
		data.add(new TableRecord("7", "-1", "-2", "-5i"));
		data.add(new TableRecord("8", "-1", "0", "-4i"));
		data.add(new TableRecord("9", "-1", "3", "0"));
		data.add(new TableRecord("10", "-1", "0", "3i"));
		data.add(new TableRecord("11", "-1", "4", "2i"));
		data.add(new TableRecord("12", "-1", "5", "0"));
		data.add(new TableRecord("13", "-1", "0", "-9i"));
		data.add(new TableRecord("14", "-1", "2", "0"));
		data.add(new TableRecord("15", "-1", "1", "4i"));
		counter = 16;

	}

	@FXML
	void mockPointsButtonOnAction(ActionEvent event) {
		data.clear();
		data.add(new TableRecord("0", "-1", "1", "1"));
		data.add(new TableRecord("1", "-1", "-2", "-4"));
		data.add(new TableRecord("2", "-1", "2", "-3"));
		data.add(new TableRecord("3", "-1", "-11", "3"));
		data.add(new TableRecord("4", "-1", "-4", "-23"));
		data.add(new TableRecord("5", "-1", "7", "3"));
		data.add(new TableRecord("6", "-1", "-4", "4"));
		data.add(new TableRecord("7", "-1", "1", "-1"));
		data.add(new TableRecord("8", "-1", "2", "2"));
		data.add(new TableRecord("9", "-1", "-1", "-9"));
		data.add(new TableRecord("10", "-1", "-1", "1"));
		data.add(new TableRecord("11", "-1", "3", "4"));
		data.add(new TableRecord("12", "-1", "-3", "4"));
		data.add(new TableRecord("13", "-1", "10", "-4"));
		data.add(new TableRecord("14", "-1", "4", "5"));
		data.add(new TableRecord("15", "-1", "-1", "-1"));
		counter = 16;
	}

}
