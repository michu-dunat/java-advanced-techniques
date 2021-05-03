package org.example.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class LogsViewController {

    @FXML
    private TextArea nextPaymentsTextArea;

    @FXML
    private TextArea missedPaymentsTextArea;

    private LocalDate currentDate;

    private boolean isThreadFinish = false;

    @FXML
    private Text dateDisplay;

    @FXML
    public void initialize() {
        nextPaymentsTextArea.setEditable(false);
        missedPaymentsTextArea.setEditable(false);
        currentDate = LocalDate.now();

        isThreadFinish = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isThreadFinish) {
                    System.out.println(currentDate.toString());
                    dateDisplay.setText(currentDate.toString());
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    currentDate = currentDate.plusDays(1);
                }


            }
        }

        ).start();

        }

    }

