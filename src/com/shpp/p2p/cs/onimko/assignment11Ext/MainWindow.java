package com.shpp.p2p.cs.onimko.assignment11Ext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class MainWindow implements Const{
    /**Window for App*/
    private final JFrame frame = new JFrame("Builder Graphic!");
    /**The chart's panel*/
    private final GraphPanel graphPanel = new GraphPanel();
    /**The text field*/
    private final JTextField textField = new JTextField(TEXT_FIELD);
    /**Combo box with cell's division*/
    private final JComboBox comboBox = new JComboBox(items);
    /**The text field*/
    private final JTextField formula = new JTextField(TEXT_FIELD*2);
    /**The text field*/
    private final JTextField variables = new JTextField(TEXT_FIELD);
    /**The text field*/
    private final JLabel result = createLable("");

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
        JTabbedPane tabbedPane = new JTabbedPane();
        frame.add(tabbedPane);
        tabbedPane.setBackground(Color.WHITE);
        JPanel charts = new JPanel();
        charts.setLayout(new BorderLayout());
        JPanel calc = new JPanel();
        calc.setLayout(new BorderLayout());
        tabbedPane.addTab("Charts",charts);
        tabbedPane.addTab("Calculate",calc);
        chartsTab(charts);
        calcTab(calc);

    }

    /**
     * Method creates panel with charts
     * @param charts the parent's panel
     */
    private void chartsTab(JPanel charts) {
        //control panel
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
        charts.add(upPanel, BorderLayout.NORTH);
        //Add chart's panel charts
        charts.add(graphPanel);
    }

    /**
     * Method creates panel for calculating.
     * @param calc the parent's panel
     */
    private void calcTab(JPanel calc) {
        //up panel calculate
        JPanel upCalc = new JPanel();
        upCalc.add(createLable("Formula:"));
        upCalc.add(formula);
        calc.add(upCalc,BorderLayout.NORTH);
        //center panel calculate
        JPanel center = new JPanel();
        center.add(createLable("Enter variables key=value and space between pair:"));
        center.add(variables);
        center.add(createButton("Calculate"));
        calc.add(center,BorderLayout.CENTER);
        //bottom panel for result
        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);


        bottom.add(result);
        calc.add(bottom,BorderLayout.SOUTH);
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
        if (e.getActionCommand().equals("Calculate")) {
            calculate();
        }
    }

    private void calculate() {
        ArrayList<String> args = new ArrayList<>();
        String formula = this.formula.getText();
        String vars = this.variables.getText();
        String error;
        if (!formula.isEmpty()) {
            args.add(formula);
            if (!vars.isEmpty()) args.addAll(Arrays.asList(vars.split(" ")));
        }
        try {
            String text = formula + " = " + CALK.getResult(args.toArray(String[]::new));
            if (!vars.isEmpty()) text +="     Where: " + vars;
            result.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
            result.setText(e.getMessage());
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
    private static JLabel createLable(String msg) {
        JLabel label = new JLabel(msg);
        label.setFont(FONT);
        return label;
    }

}
