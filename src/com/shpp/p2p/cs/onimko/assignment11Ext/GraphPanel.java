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
    /**This graphics*/
    Graphics g;
    /**Cell size*/
    int cell;
    /**Storage of chart*/
    Map<String, ArrayList<Point>> charts= new HashMap<>();

    @Override
    public void update(Graphics g) {
        cell = getHeight()/NUMBER_DIV;
        drawGrid(g);
        drawAxis(g);
        drawGraph(g);
    }

    private void drawGraph(Graphics g) {
        System.out.println(charts);
        charts.keySet().forEach(chart -> {
            charts.get(chart).forEach(point -> {
                g.setColor(Color.RED);
                g.drawOval(point.x,point.y,2,2);
            });
        });

    }

    protected void clear(){
        this.removeAll();
        this.update(g);
    }

    protected void delete(){
       System.out.println("Delete");
    }

    protected void create(String formula){
        if (formula == null || formula.equals("")) return;
        Assignment11Part1 calk= new Assignment11Part1();
        ArrayList<Point> dataForChart = new ArrayList<>();
        double y = 0;
        for (int x = -getHeight()/2; x < getHeight()/2; x++) {
            try {
                y = calk.getResult(new String[]{formula,"x="+x});
            } catch (Exception e) {
                e.printStackTrace();
            }
            dataForChart.add(new Point(x+getHeight()/2, (int) (getHeight()/2-y)));
        }
        charts.put(formula,dataForChart);

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
        g.drawLine( cell*(NUMBER_DIV/2),0, cell*(NUMBER_DIV/2), cell*NUMBER_DIV);
        g.drawLine(0, cell*(NUMBER_DIV/2), cell*NUMBER_DIV, cell*(NUMBER_DIV/2));
    }

    /**
     * Method draws the grid for graph
     * @param g - the Graphics
     */
    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x <= cell*NUMBER_DIV; x += cell)
            g.drawLine(x,0,x,cell*NUMBER_DIV);
        for (int y = 0; y <= cell*NUMBER_DIV; y += cell)
            g.drawLine(0, y, cell*NUMBER_DIV, y);
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
