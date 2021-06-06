package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.example.utility.CommunicationHandler;
import org.example.utility.MyListener;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatController implements MyListener {

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextArea inputMessageTextArea;

    @FXML
    private Button sendButton;

    @FXML
    private Button connectButton;

    @FXML
    private Button listenPublicKeyButton;

    @FXML
    private Button sendPublicKeyButton;

    private String host;
    private int sendingPort;
    private int listeningPort;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private CommunicationHandler communicationHandler;

    @FXML
    public void initialize() throws NoSuchPaddingException, NoSuchAlgorithmException {
        communicationHandler = new CommunicationHandler();
        sendPublicKeyButton.setDisable(true);
        connectButton.setDisable(true);
    }

    @FXML
    void sendPublicKeyButtonOnAction(ActionEvent event) throws NoSuchAlgorithmException {
        communicationHandler.sendKey(host, sendingPort);
        sendPublicKeyButton.setDisable(true);
        connectButton.setDisable(false);
    }

    @FXML
    void listenPublicKeyButtonOnAction(ActionEvent event) {
        communicationHandler.startListeningForPublicKey(listeningPort);
        listenPublicKeyButton.setDisable(true);
        sendPublicKeyButton.setDisable(false);
    }

    @FXML
    void sendButtonOnAction(ActionEvent event) {
        String message = inputMessageTextArea.getText();
        communicationHandler.sendMessage(host, sendingPort, message);
        chatTextArea.appendText("\n" + dateTimeFormatter.format(LocalDateTime.now()) + " - You: " + message);
    }

    @FXML
    void connectButtonOnAction(ActionEvent event) throws InvalidKeyException, IOException, InterruptedException {
        communicationHandler.initCiphers();

        connectButton.setDisable(true);

        communicationHandler.addMyListener(ChatController.this);
        communicationHandler.startListeningForMessages(listeningPort);

    }

    public void setChatHostAndPorts(String host, int sendingPort, int listeningPort) {
        this.host = host;
        this.sendingPort = sendingPort;
        this.listeningPort = listeningPort;
    }

    @Override
    public void messageReceived(String theLine) {
        chatTextArea.appendText("\n" + dateTimeFormatter.format(LocalDateTime.now()) + " - Friend: " + theLine);
    }


}
