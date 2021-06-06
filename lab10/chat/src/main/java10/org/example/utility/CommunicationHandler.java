package org.example.utility;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class CommunicationHandler {

    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Cipher cipherEncode;
    private Cipher cipherDecode;
    private List<MyListener> myListeners;

    public CommunicationHandler() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipherEncode = Cipher.getInstance("RSA");
        cipherDecode = Cipher.getInstance("RSA");
        myListeners = new ArrayList<MyListener>();
    }

    public void sendKey(String host, int sendingPort) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        privateKey = keyPair.getPrivate();

        Socket socket;
        try {
            socket = new Socket(host, sendingPort - 1);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(keyPair.getPublic().getEncoded());
            outputStream.flush();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startListeningForMessages(int listeningPort) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(listeningPort);
                    while (true) {
                        Socket socketFromServerSocket = serverSocket.accept();
                        InputStream inputStream = socketFromServerSocket.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String theLine = bufferedReader.readLine();
                        socketFromServerSocket.close();
                        byte[] result = cipherDecode.doFinal(Base64.getDecoder().decode(theLine));
                        String resultString = new String(result);
                        myListeners.forEach((item) -> item.messageReceived(resultString));
                    }
                } catch (IOException | BadPaddingException | IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendMessage(String host, int sendingPort, String message) {
        Socket socket;
        try {
            socket = new Socket(host, sendingPort);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, false);
            byte[] encodedString = cipherEncode.doFinal(message.getBytes());
            printWriter.println(Base64.getEncoder().encodeToString(encodedString));
            printWriter.flush();
            printWriter.close();
            socket.close();
        } catch (IOException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    public void startListeningForPublicKey(int listeningPort) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(listeningPort - 1);
                    while (true) {
                        Socket socketFromServerSocket = serverSocket.accept();
                        byte[] servPubKeyBytes = new byte[294];
                        socketFromServerSocket.getInputStream().read(servPubKeyBytes);
                        publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(servPubKeyBytes));
                        socketFromServerSocket.close();
                    }
                } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void initCiphers() throws InvalidKeyException {
        cipherEncode.init(Cipher.ENCRYPT_MODE, publicKey);
        cipherDecode.init(Cipher.DECRYPT_MODE, privateKey);
    }

    public void addMyListener(MyListener m) {
        myListeners.add(m);
    }

}
