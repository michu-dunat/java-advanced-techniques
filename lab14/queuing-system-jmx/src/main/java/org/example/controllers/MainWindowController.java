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
import org.example.cases.*;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;

public class MainWindowController extends NotificationBroadcasterSupport implements MainWindowControllerMXBean {

    ObservableList categoryObservableList = FXCollections.observableArrayList();
    ObservableList caseObservableList = FXCollections.observableArrayList();
    @FXML
    private ListView<CaseCategory> categoryListView;
    @FXML
    private Button fillCaseButton;
    @FXML
    private ListView<Case> caseListView;
    @FXML
    private TextField currentCaseTextField;

    ArrayList<Case> allCases = new ArrayList<>();

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
                while(true) {
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

    private Integer sequenceNumber = 0;

    @Override
    public void setCaseCategoryList(List observableList) {
        synchronized (categoryObservableList) {
            categoryObservableList.clear();
            categoryObservableList.addAll(observableList);
            notifyCategoryChange("Halo?");
        }

    }

    public void notifyCategoryChange(String msg) {
        Notification notification = new Notification("queuesyspack.AgentFrame", this, sequenceNumber++, msg);
        sendNotification(notification);
    }

}
