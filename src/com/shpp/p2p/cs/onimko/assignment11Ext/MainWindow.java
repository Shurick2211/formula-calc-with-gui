package com.shpp.p2p.cs.onimko.assignment11Ext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainWindow implements Const{
    /**Window for App*/
    JFrame frame = new JFrame("Builder Graphic!");

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

        JPanel upPanel = new JPanel();
        upPanel.setBackground(Color.lightGray);
        upPanel.add(new Label("f(x) = "));
        TextField textField = new TextField(TEXT_FIELD);
        textField.addActionListener(this::actionPerformed);

        upPanel.add(textField);
        upPanel.add(new Button("Create"));
        mainContainer.add(upPanel, BorderLayout.NORTH);

        mainContainer.add(new GraphPanel());
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

    }


}
