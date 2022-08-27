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
        mainContainer.setBackground(Color.WHITE);

        JPanel upPanel = new JPanel();
        upPanel.setBackground(Color.lightGray);
        upPanel.add(new Label("f(x) = "));
        upPanel.add(new TextField(TEXT_FIELD));
        mainContainer.add(upPanel, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.lightGray);

        leftPanel.setBackground(Color.CYAN);
        leftPanel.add(new Button("Create"));
        mainContainer.add(leftPanel, BorderLayout.WEST);

        mainContainer.add(new GraphPanel());
    }

    /**
     * Method makes the window is visible.
     */
    protected void show() {
        frame.setVisible(true);
    }


}
