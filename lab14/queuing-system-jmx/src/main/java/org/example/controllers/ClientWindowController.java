package org.example.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.MainWindowControllerMXBean;
import org.example.cases.CaseCategory;

import javax.management.*;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class ClientWindowController implements NotificationListener {

    @FXML
    private ListView<CaseCategory> categoryListView;

    @FXML
    private Button addCategoryButton;

    @FXML
    private Button deleteCategoryButton;

    @FXML
    private TextField categoryPirorityTextField;

    @FXML
    private Button editPiorityButton;

    @FXML
    private TextField categoryNameTextField;

    private ObservableList caseObservableList = FXCollections.observableArrayList();

    @FXML
    void addCategoryButtonOnAction(ActionEvent event) {
        CaseCategory caseCategory = new CaseCategory(categoryNameTextField.getText(), Integer.parseInt(categoryPirorityTextField.getText()), currentChar);
        currentChar += 1;
        caseObservableList.add(caseCategory);
        proxy.setCaseCategoryList(caseObservableList);
    }

    @FXML
    void deleteCategoryButtonOnAction(ActionEvent event) {
        caseObservableList.remove(categoryListView.getSelectionModel().getSelectedItem());
        proxy.setCaseCategoryList(caseObservableList);
    }

    @FXML
    void editPriorityButtonOnAction(ActionEvent event) {
        CaseCategory caseCategory = categoryListView.getSelectionModel().getSelectedItem();
        caseCategory.setPriority(Integer.parseInt(categoryPirorityTextField.getText()));
        caseObservableList.set(categoryListView.getSelectionModel().getSelectedIndex(), caseCategory);
        proxy.setCaseCategoryList(caseObservableList);
    }

    private char currentChar = 'A';

    MainWindowControllerMXBean proxy;

    @FXML
    public void initialize() throws Exception {
        categoryListView.setItems(caseObservableList);

        int jmxPort = 8008;
        JMXServiceURL target = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + jmxPort + "/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(target);
        MBeanServerConnection mbs = connector.getMBeanServerConnection();
        proxy = JMX.newMXBeanProxy(mbs, new ObjectName("org.example.controllers:name=" + "MainWindowController"), MainWindowControllerMXBean.class);
        mbs.addNotificationListener(new ObjectName("org.example.controllers:name=" + "MainWindowController"), this, null, null);

        caseObservableList.addAll(proxy.getCaseCategoryList());
    }

    @FXML
    private TextArea notificationTextArea;

    @Override
    public void handleNotification(Notification notification, Object handback) {
        Platform.runLater(() -> {
            caseObservableList.clear();
            caseObservableList.addAll(proxy.getCaseCategoryList());
            notificationTextArea.appendText(notification.getMessage() + System.getProperty("line.separator"));
        });
    }
}

