package com.shpp.p2p.cs.onimko.assignment11Ext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainWindow implements Const{
    /**Window for App*/
    private final JFrame frame = new JFrame("Builder Graphic!");
    /**The chart's panel*/
    private final GraphPanel graphPanel = new GraphPanel();
    /**The text field*/
    private final JTextField textField = new JTextField(TEXT_FIELD);
    /**Combo box with cell's division*/
    private final JComboBox comboBox = new JComboBox(items);
    /**
     * Create the window
     */
    public MainWindow() {
        frame.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        init();
    }

    /**
     * Init element of the window.
     */
    private void init() {
        Container mainContainer = frame.getContentPane();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setBackground(Color.WHITE);
        //add control panel
        JPanel upPanel = new JPanel();
        upPanel.setBackground(Color.lightGray);

        upPanel.add(createLable("Cell:"));
        comboBox.setFont(FONT);
        comboBox.setSelectedItem(items[1]);
        comboBox.addActionListener(this::actionPerformed);
        comboBox.setActionCommand("Box");
        upPanel.add(comboBox);

        upPanel.add(createLable("f(x) = "));
        textField.setFont(FONT);
        textField.setActionCommand("Enter");
        textField.addActionListener(this::actionPerformed);
        upPanel.add(textField);
        upPanel.add(createButton("Create"));
        upPanel.add(createButton("Clear"));
        upPanel.add(createButton("Delete"));
        mainContainer.add(upPanel, BorderLayout.NORTH);
        //Add chart's panel
        mainContainer.add(graphPanel);
    }

    /**
     * Method makes the window is visible.
     */
    protected void show() {
        frame.setVisible(true);
    }

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Create") || e.getActionCommand().equals("Enter")) {
            graphPanel.create(textField.getText());
        } else if (e.getActionCommand().equals("Clear")) {
            textField.setText("");
            graphPanel.clear();
        } else if (e.getActionCommand().equals("Delete")) {
            graphPanel.delete(textField.getText());
            textField.setText("");
        } else if (e.getActionCommand().equals("Box")) {
            graphPanel.setCell(comboBox.getSelectedItem().toString());
        }
    }

    /**
     * Method creates button with action listener.
     * @param name the name of button
     * @return JButton
     */
    private JButton createButton(String name){
        JButton button = new JButton(name);
        button.addActionListener(this::actionPerformed);
        return button;
    }

    /**
     * Method create a label with message.
     * @param msg the message
     * @return JLabel
     */
    private JLabel createLable(String msg) {
        JLabel label = new JLabel(msg);
        label.setFont(FONT);
        return label;
    }

}
