package com.shpp.p2p.cs.onimko.assignment11Ext;

import com.shpp.p2p.cs.onimko.assignment11.Assignment11Part1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphPanel extends JPanel implements ComponentListener, Const{

    /**Cell size*/
    int cellSize;
    int cell = 10;
    double pixel;
    /**Storage of chart*/
    Map<String, ArrayList<MyPoint>> charts= new HashMap<>();


    private void drawGraph(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        charts.keySet().forEach(chart -> {
            charts.get(chart).forEach(point -> {
                g2.setColor(Color.RED);
                g2.fillOval( (int)(point.getX()*pixel+getHeight()/2),
                    (int) (getHeight()/2-point.getY()*pixel),2,2);
            });
        });
    }


    protected void delete(String formula){
        charts.remove(formula);
        update(this.getGraphics());
    }

    protected void create(String formula){
        if (formula == null || formula.equals("")) return;
        Assignment11Part1 calk= new Assignment11Part1();
        ArrayList<MyPoint> dataForChart = new ArrayList<>();
        double y = 0;
        for (int x = -cell*NUMBER_DIV/2; x <= cell*NUMBER_DIV/2; x++) {
            try {
                y = calk.getResult(new String[]{formula,"x="+x});
            } catch (Exception e) {
                e.printStackTrace();
            }
            dataForChart.add(new MyPoint(x, y));
        }
        charts.put(formula,dataForChart);
        update(this.getGraphics());
    }

    @Override
    public void paint(Graphics g) {
        removeAll();
        cellSize = getHeight()/NUMBER_DIV;
        pixel = (double) cellSize/cell;
        drawGrid(g);
        drawAxis(g);
        drawGraph(g);
    }

    /**
     * Method draws the axis for graph
     * @param g - the Graphics
     */
    private void drawAxis(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine( cellSize *(NUMBER_DIV/2),0, cellSize *(NUMBER_DIV/2), cellSize *NUMBER_DIV);
        g.drawLine(0, cellSize *(NUMBER_DIV/2), cellSize *NUMBER_DIV, cellSize *(NUMBER_DIV/2));
    }

    /**
     * Method draws the grid for graph
     * @param g - the Graphics
     */
    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x <= cellSize *NUMBER_DIV; x += cellSize)
            g.drawLine(x,0,x, cellSize *NUMBER_DIV);
        for (int y = 0; y <= cellSize *NUMBER_DIV; y += cellSize)
            g.drawLine(0, y, cellSize *NUMBER_DIV, y);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        update(this.getGraphics());
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
