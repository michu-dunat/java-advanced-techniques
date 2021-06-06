package com.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JTextField vectorATextField;
    private JTextField vectorBTextField;
    private JTextField productTextField;

    public static void main(String[] args) {
        System.loadLibrary("scalar-product-native");

        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }

    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Calculate scalar product");
        setBounds(100, 100, 330, 300);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(panel);
        panel.setLayout(null);

        JLabel labelA = new JLabel("Vector A: ");
        labelA.setBounds(20, 10, 70, 20);
        panel.add(labelA);

        JLabel labelB = new JLabel("Vector B: ");
        labelB.setBounds(20, 50, 70, 20);
        panel.add(labelB);

        vectorATextField = new JTextField();
        vectorATextField.setBounds(90, 10, 200, 20);
        panel.add(vectorATextField);
        vectorATextField.setColumns(10);

        vectorBTextField = new JTextField();
        vectorBTextField.setColumns(10);
        vectorBTextField.setBounds(90, 50, 200, 20);
        panel.add(vectorBTextField);

        JLabel labelProduct = new JLabel("Product: ");
        labelProduct.setBounds(20, 90, 70, 20);
        panel.add(labelProduct);

        productTextField = new JTextField();
        productTextField.setEditable(false);
        productTextField.setBounds(90, 90, 200, 20);
        panel.add(productTextField);
        productTextField.setColumns(10);

        JButton multi01Button = new JButton("multi01");
        multi01Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    ScalarProduct scalarProduct = new ScalarProduct();

                    Double product = scalarProduct.multi01(parseVectorInput(vectorATextField),
                            parseVectorInput(vectorBTextField));
                    if (product != null)
                        productTextField.setText(product.toString());
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Incorrect input.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        multi01Button.setBounds(20, 130, 200, 20);
        panel.add(multi01Button);

        JButton multi02Button = new JButton("multi02");
        multi02Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    ScalarProduct scalarProduct = new ScalarProduct();
                    scalarProduct.b = parseVectorInput(vectorBTextField);
                    Double product = scalarProduct.multi02(parseVectorInput(vectorATextField));
                    if (product != null)
                        productTextField.setText(product.toString());
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Incorrect input.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        multi02Button.setBounds(20, 170, 200, 20);
        panel.add(multi02Button);

        JButton multi03Button = new JButton("multi03");
        multi03Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        ScalarProduct scalarProduct = new ScalarProduct();
                        scalarProduct.multi03();
                        if (scalarProduct.c != null)
                            productTextField.setText(scalarProduct.c.toString());
                    }
                }).start();
            }
        });
        multi03Button.setBounds(20, 210, 200, 20);
        panel.add(multi03Button);

    }

    public Double[] parseVectorInput(JTextField textField) throws NumberFormatException {

        String[] stringSplit = textField.getText().split(",");

        ArrayList<Double> vectorArrayList = new ArrayList<>();

        for (String string : stringSplit)
            vectorArrayList.add(Double.parseDouble(string));

        return (Double[]) vectorArrayList.toArray(new Double[vectorArrayList.size()]);
    }
}
