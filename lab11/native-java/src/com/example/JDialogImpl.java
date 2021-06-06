package com.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JDialogImpl extends JFrame {

    private static final long serialVersionUID = 2L;
    private JPanel panel;
    private JTextField vectorATextField;
    private JTextField vectorBTextField;
    private boolean wasSubmitButtonPressed = false;

    public JDialogImpl() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Input vectors");
        setLocationRelativeTo(null);
        setBounds(100, 100, 330, 170);
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

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wasSubmitButtonPressed = true;
            }
        });
        submitButton.setBounds(20, 90, 200, 20);
        panel.add(submitButton);

        setVisible(true);
    }

    public boolean getWasSubmitButtonPressed() {
        return this.wasSubmitButtonPressed;
    }

    public Double[] parseVectorAInput() {

        String[] stringSplit = vectorATextField.getText().split(",");

        ArrayList<Double> vectorArrayList = new ArrayList<>();

        for (String string : stringSplit)
            vectorArrayList.add(Double.parseDouble(string));

        return (Double[]) vectorArrayList.toArray(new Double[vectorArrayList.size()]);
    }

    public Double[] parseVectorBInput() {

        String[] stringSplit = vectorBTextField.getText().split(",");

        ArrayList<Double> vectorArrayList = new ArrayList<>();

        for (String string : stringSplit)
            vectorArrayList.add(Double.parseDouble(string));

        return (Double[]) vectorArrayList.toArray(new Double[vectorArrayList.size()]);
    }

}
