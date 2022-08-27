package com.shpp.p2p.cs.onimko.assignment11Ext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GraphPanel extends JPanel implements ComponentListener, Const{
    /**This graphics*/
    Graphics g;

    @Override
    public void update(Graphics g) {
        drawGrid(g);
        drawAxis(g);
    }

    @Override
    public void paint(Graphics g) {
        this.g = g;
        update(g);
    }

    /**
     * Method draws the axis for graph
     * @param g - the Graphics
     */
    private void drawAxis(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(getWidth()/NUMBER_DIV*(NUMBER_DIV/2) ,0, getWidth()/NUMBER_DIV*(NUMBER_DIV/2), getHeight());
        g.drawLine(0, getHeight()/NUMBER_DIV*(NUMBER_DIV/2), getWidth(), getHeight()/NUMBER_DIV*(NUMBER_DIV/2));
    }

    /**
     * Method draws the grid for graph
     * @param g - the Graphics
     */
    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x <= getWidth(); x += getWidth()/NUMBER_DIV)
            g.drawLine(x,0,x,getHeight());
        for (int y = 0; y <= getHeight(); y += getHeight()/NUMBER_DIV)
            g.drawLine(0, y, getWidth(), y);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        update(g);
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}