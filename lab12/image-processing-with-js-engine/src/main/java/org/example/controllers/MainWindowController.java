package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;

public class MainWindowController {

    @FXML
    private Button loadImageButton;

    @FXML
    private Button loadScriptButton;

    @FXML
    private Button UnloadScriptButton;

    @FXML
    private Button processImageButton;

    @FXML
    private ListView<String> scriptsListView;

    @FXML
    private ImageView imageView;

    @FXML
    private ImageView outputImageView;

    BufferedImage img = null;
    BufferedImage outputImg = null;
    FileChooser fileChooser = new FileChooser();
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine jsEngine = mgr.getEngineByName("nashorn");
    HashMap<String, InputStream> namesAndScriptsHashMap = new HashMap<>();
    ObservableList scriptNamesArrayList = FXCollections.observableArrayList();

    @FXML
    void loadImageButtonOnAction(ActionEvent event) {
        img = null;
        try {
            img = ImageIO.read(fileChooser.showOpenDialog(loadImageButton.getScene().getWindow()));
        } catch (IOException e) {
            e.printStackTrace();

        }
        imageView.setImage(SwingFXUtils.toFXImage(img, null));
    }

    @FXML
    void loadScriptButtonOnAction(ActionEvent event) {
        File loadedFile = fileChooser.showOpenDialog(loadImageButton.getScene().getWindow());
        InputStream script = null;
        try {
            script = new FileInputStream(loadedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        namesAndScriptsHashMap.put(loadedFile.getName(), script);
        scriptNamesArrayList.add(loadedFile.getName());
    }

    @FXML
    void processImageButtonOnAction(ActionEvent event) {
        Invocable invocableEngine = (Invocable)jsEngine;
        try {
            Reader reader = new InputStreamReader(namesAndScriptsHashMap.get(scriptsListView.getSelectionModel().getSelectedItem()));
            jsEngine.eval(reader);
            Object object = invocableEngine.invokeFunction("process", img);
            outputImg = (BufferedImage) object;
        }
        catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        outputImageView.setImage(SwingFXUtils.toFXImage(outputImg, null));
        }

    @FXML
    void unloadScriptButtonOnAction(ActionEvent event) {
        String scriptToBeUnloaded = scriptsListView.getSelectionModel().getSelectedItem();
        namesAndScriptsHashMap.remove(scriptToBeUnloaded);
        scriptNamesArrayList.remove(scriptToBeUnloaded);
    }

    @FXML
    public void initialize() {
        fileChooser.setTitle("Open Resource File");
        scriptsListView.setItems(scriptNamesArrayList);
    }

}
