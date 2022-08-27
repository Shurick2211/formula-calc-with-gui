package com.shpp.p2p.cs.onimko.assignment11Ext;

import javax.swing.*;
import java.awt.*;

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

        JPanel upPanel = new JPanel();
        upPanel.setBackground(Color.lightGray);
        mainContainer.add(upPanel, BorderLayout.NORTH);
        upPanel.add(new TextField(20));

        mainContainer.add(new GraphPanel(),BorderLayout.SOUTH);

    }

    /**
     * Method makes the window is visible.
     */
    protected void show() {
        frame.setVisible(true);
    }


}
