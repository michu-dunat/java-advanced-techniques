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

    MainWindowControllerMXBean proxy;
    @FXML
    private ListView<CaseCategory> categoryListView;
    @FXML
    private Button addCategoryButton;
    @FXML
    private Button deleteCategoryButton;
    @FXML
    private TextField categoryPriorityTextField;
    @FXML
    private Button editPriorityButton;
    @FXML
    private TextField categoryNameTextField;
    private ObservableList categoryObservableList = FXCollections.observableArrayList();
    @FXML
    private TextField letterTextField;
    @FXML
    private TextArea notificationTextArea;

    @FXML
    void addCategoryButtonOnAction(ActionEvent event) {
        CaseCategory caseCategory = new CaseCategory(categoryNameTextField.getText(), Integer.parseInt(categoryPriorityTextField.getText()), letterTextField.getText());
        synchronized (categoryObservableList) {
            categoryObservableList.add(caseCategory);
        }
        proxy.setCaseCategoryList(categoryObservableList);
    }

    @FXML
    void deleteCategoryButtonOnAction(ActionEvent event) {
        synchronized (categoryObservableList) {
            categoryObservableList.remove(categoryListView.getSelectionModel().getSelectedItem());
        }
        proxy.setCaseCategoryList(categoryObservableList);
    }

    @FXML
    void editPriorityButtonOnAction(ActionEvent event) {
        CaseCategory caseCategory = categoryListView.getSelectionModel().getSelectedItem();
        caseCategory.setPriority(Integer.parseInt(categoryPriorityTextField.getText()));
        synchronized (categoryObservableList) {
            categoryObservableList.set(categoryListView.getSelectionModel().getSelectedIndex(), caseCategory);
        }
        proxy.setCaseCategoryList(categoryObservableList);
    }

    @FXML
    public void initialize() throws Exception {
        categoryListView.setItems(categoryObservableList);

        int jmxPort = 8008;
        JMXServiceURL target = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + jmxPort + "/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(target);
        MBeanServerConnection mbs = connector.getMBeanServerConnection();
        proxy = JMX.newMXBeanProxy(mbs, new ObjectName("org.example.controllers:name=" + "MainWindowController"), MainWindowControllerMXBean.class);
        mbs.addNotificationListener(new ObjectName("org.example.controllers:name=" + "MainWindowController"), this, null, null);

        categoryObservableList.addAll(proxy.getCaseCategoryList());
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {
        synchronized (categoryObservableList) {
            Platform.runLater(() -> {
                categoryObservableList.clear();
                categoryObservableList.addAll(proxy.getCaseCategoryList());
                notificationTextArea.appendText(notification.getMessage() + System.getProperty("line.separator"));
            });
        }
    }
}

