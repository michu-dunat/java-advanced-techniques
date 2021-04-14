package com.controllers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import com.app_utilities.ClassLoaderExtension;
import com.app_utilities.StatusListenerImplementation;
import com.processing.StatusListener;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;

public class MainWindowController {

	@FXML
	private Button submitButton;

	@FXML
	private TextArea textInput;

	@FXML
	private TextArea textOutput;

	@FXML
	private ListView<String> classListView;

	@FXML
	private ProgressBar progressBar;

	@FXML
	private Button chooseDirectoryButton;
	
	HashMap<String, Path> nameInfoAndPathMap;
	ClassLoaderExtension classLoaderExtension;
	Class<?> processorClass;
	Method getInfoMethod;
	Object loadedClassObject;
	String processorInfo;
	Class<?>[] parameterTypes;
	Method submitTaskMethod;
	Method getResultMethod;

	@FXML
	void chooseDirectoryButtonOnAction(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(submitButton.getScene().getWindow());
		Set<Path> classPaths = null;
		try {
			classPaths = getFilesInDirectory(selectedDirectory.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<String> infoList = new ArrayList<>();
		classLoaderExtension = new ClassLoaderExtension();
		
		for (Path path : classPaths) {
			try {
				processorClass = classLoaderExtension.findClass(path.toString());
				getInfoMethod = processorClass.getMethod("getInfo");
				loadedClassObject = processorClass.getDeclaredConstructor().newInstance();
				processorInfo = (String) getInfoMethod.invoke(loadedClassObject);
				infoList.add(path.getFileName() + " - " + processorInfo);
				nameInfoAndPathMap.put(path.getFileName() + " - " + processorInfo, path);
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}	
		}
		
		classLoaderExtension = null;
		processorClass = null;
		getInfoMethod = null;
		loadedClassObject = null;
		processorInfo = null;
		System.gc();
		
		ObservableList<String> classNameAndInfoObservableList = FXCollections.observableArrayList(infoList);
		classListView.getItems().addAll(classNameAndInfoObservableList);
		
		submitButton.setDisable(false);
	}

	@FXML
	public void initialize() {
		submitButton.setDisable(true);
		nameInfoAndPathMap = new HashMap<>();
		textInput.setText("Litwa. PóŸnym wieczorem w cmentarnej kaplicy gromadz¹ siê wieœniacy przybyli na\n"
				+ "„ucztê koz³a”, czyli noc Dziadów. Przewodz¹cy obrzêdom Guœlarz przywo³uje duchy zmar³ych\n"
				+ "osób („czyscowe duszeczki” - dusze czyœæcowe), które nie mog¹ po œmierci zaznaæ wiecznego\n"
				+ "spoczynku, lecz musz¹ odbywaæ karê za b³êdy i przewinienia pope³nione za ¿ycia.\n");
	}

	@FXML
	void submitButtonOnAction(ActionEvent event) {
		textOutput.clear();
		progressBar.setProgress(0.0);
		submitButton.setDisable(true);
		
		String selectedInfo = classListView.getSelectionModel().getSelectedItem();
		Path absolutePath = nameInfoAndPathMap.get(selectedInfo);
		
		classLoaderExtension = new ClassLoaderExtension();
		StatusListenerImplementation statusListenerImplementation = new StatusListenerImplementation();
		
		try {
			processorClass = classLoaderExtension.findClass(absolutePath.toString());
			parameterTypes = new Class<?>[] {String.class, StatusListener.class};
			Object[] submitTaskArguments = {textInput.getText(), statusListenerImplementation};
			loadedClassObject = processorClass.getDeclaredConstructor().newInstance();
			submitTaskMethod = processorClass.getMethod("submitTask", parameterTypes);
			getResultMethod = processorClass.getMethod("getResult");
			submitTaskMethod.invoke(loadedClassObject, submitTaskArguments);

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}	

		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				while (statusListenerImplementation.getProgress() < 100) {
					updateProgress(statusListenerImplementation.getProgress(), 100);
				}

				updateProgress(1, 1);
				textOutput.setText( (String) getResultMethod.invoke(loadedClassObject) );
				submitButton.setDisable(false);
				progressBar.progressProperty().unbind();
				
				processorClass = null;
				parameterTypes = null;
				loadedClassObject = null;
				submitTaskMethod = null;
				getResultMethod = null;
				classLoaderExtension = null;
				System.gc();

				return null;
			}
		};

		progressBar.progressProperty().bind(task.progressProperty());
		new Thread(task).start();

	}

	private Set<Path> getFilesInDirectory(Path pathToDirectory) throws IOException {
		Set<Path> allFilesInDirectory = Files.list(pathToDirectory).filter(Files::isRegularFile)
				.collect(Collectors.toSet());

		return allFilesInDirectory;
	}

}
