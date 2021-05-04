package org.example.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import org.example.services.ReceivableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LogsViewController {

    @FXML
    private TextArea nextPaymentsTextArea;

    @FXML
    private TextArea missedPaymentsTextArea;

    private LocalDate currentDate;

    private boolean isThreadFinish = false;

    @FXML
    private Text dateDisplay;

    @Autowired
    ReceivableService receivableService;

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
                    dateDisplay.setText(currentDate.toString());
                    nextPaymentsTextArea.appendText(receivableService.charge(currentDate));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    missedPaymentsTextArea.appendText(receivableService.checkPayments(currentDate));
                    currentDate = currentDate.plusDays(1);
                }


            }
        }

        ).start();

        }

    }

