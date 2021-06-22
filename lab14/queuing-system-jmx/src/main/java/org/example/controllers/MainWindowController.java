package org.example.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.MainWindowControllerMXBean;
import org.example.cases.Case;
import org.example.cases.CaseCategory;
import org.example.cases.CaseComparator;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainWindowController extends NotificationBroadcasterSupport implements MainWindowControllerMXBean {

    ObservableList categoryObservableList = FXCollections.observableArrayList();
    ObservableList caseObservableList = FXCollections.observableArrayList();
    ArrayList<Case> allCases = new ArrayList<>();
    @FXML
    private ListView<CaseCategory> categoryListView;
    @FXML
    private Button fillCaseButton;
    @FXML
    private ListView<Case> caseListView;
    @FXML
    private TextField currentCaseTextField;
    private Integer sequenceNumber = 0;
    @FXML
    private TextField categoryNameTextField;
    @FXML
    private TextField categoryPriorityTextField;
    @FXML
    private TextField letterTextField;
    @FXML
    private Button addCategoryButton;
    @FXML
    private Button deleteCategoryButton;
    @FXML
    private Button editPriorityButton;

    @FXML
    void fillCaseButtonOnAction(ActionEvent event) {
        synchronized (categoryObservableList) {
            Integer counter = 0;
            for (Case filledCase : allCases) {
                if (filledCase.getCaseCategory().getName() == categoryListView.getSelectionModel().getSelectedItem().getName()) {
                    counter++;
                }
            }
            Case newCase = new Case(categoryListView.getSelectionModel().getSelectedItem(), counter);
            allCases.add(newCase);
            caseObservableList.add(newCase);
            Collections.sort(caseObservableList, new CaseComparator());
        }
    }

    @FXML
    public void initialize() {
        categoryListView.setItems(categoryObservableList);
        caseListView.setItems(caseObservableList);
        try {
            ManagementFactory.getPlatformMBeanServer().registerMBean(this,
                    new ObjectName("org.example.controllers:name=" + "MainWindowController"));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Case currentCase = null;
                    if (!caseObservableList.isEmpty()) {
                        currentCase = (Case) caseObservableList.get(0);

                        Case finalCurrentCase = currentCase;
                        Platform.runLater(() -> {
                            currentCaseTextField.setText(finalCurrentCase.getId());
                            caseObservableList.remove(0);
                        });

                        try {
                            Thread.sleep(9500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Platform.runLater(() -> {
                        currentCaseTextField.setText("");
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();
    }

    @Override
    public ObservableList getCaseCategoryList() {
        synchronized (categoryObservableList) {
            return categoryObservableList;
        }
    }

    @Override
    public void setCaseCategoryList(List observableList) {
        synchronized (categoryObservableList) {
            Platform.runLater(() -> {
                categoryObservableList.clear();
                categoryObservableList.addAll(observableList);
            });
        }
    }

    public void notifyClient(String msg) {
        Notification notification = new Notification("Notification", this, sequenceNumber++, msg);
        sendNotification(notification);
    }

    @FXML
    void addCategoryButtonOnAction(ActionEvent event) {
        CaseCategory caseCategory = new CaseCategory(categoryNameTextField.getText(), Integer.parseInt(categoryPriorityTextField.getText()), letterTextField.getText());
        synchronized (categoryObservableList) {
            categoryObservableList.add(caseCategory);
        }
        String msg = "Dodano kategorię " + caseCategory.getName() + " o priorytecie " + caseCategory.getPriority() + " i symbolu " + caseCategory.getSymbol() + ".";
        String timestamp = "[ " + new Timestamp(System.currentTimeMillis()) + " ] ";
        notifyClient(timestamp + msg);
    }

    @FXML
    void deleteCategoryButtonOnAction(ActionEvent event) {
        CaseCategory caseCategory = categoryListView.getSelectionModel().getSelectedItem();
        synchronized (categoryObservableList) {
            categoryObservableList.remove(categoryListView.getSelectionModel().getSelectedItem());
        }
        String timestamp = "[ " + new Timestamp(System.currentTimeMillis()) + " ] ";
        String msg = "Usunięto kategorię " + caseCategory.getName() + " o priorytecie " + caseCategory.getPriority() + " i symbolu " + caseCategory.getSymbol() + ".";
        notifyClient(timestamp + msg);
    }

    @FXML
    void editPriorityButtonOnAction(ActionEvent event) {
        CaseCategory caseCategory = categoryListView.getSelectionModel().getSelectedItem();
        String msg = "Zmieniono priorytet kategorii " + caseCategory.getName() + " o symbolu " + caseCategory.getSymbol() + " z " + caseCategory.getPriority();
        caseCategory.setPriority(Integer.parseInt(categoryPriorityTextField.getText()));
        synchronized (categoryObservableList) {
            categoryObservableList.set(categoryListView.getSelectionModel().getSelectedIndex(), caseCategory);
        }
        msg += " na " + caseCategory.getPriority();
        String timestamp = "[ " + new Timestamp(System.currentTimeMillis()) + " ] ";
        notifyClient(timestamp + msg);
    }

}
