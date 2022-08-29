package com.shpp.p2p.cs.onimko.assignment11Ext;

import com.shpp.p2p.cs.onimko.assignment11.Assignment11Part1;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class GraphPanel extends JComponent implements ComponentListener, Const{

    /**Division in a cell*/
    int cell = 10;
    /**Cell size*/
    int cellSize;
    /**Number of pixels per division*/
    double pixel;
    /**Storage of chart*/
    Map<String, ArrayList<MyPoint>> charts= new HashMap<>();
    /**Storage of colors for charts*/
    Map<String, Integer> colorCharts = new HashMap<>();
    /**The counter*/
    int count = 0;
    /**Font for axis*/
    Font font = new Font("Verdana",Font.ITALIC,9);

    /**
     * Method draws chart.
     * @param g the Graphics
     */
    private void drawGraph(Graphics g) {
        int x, y, oX=0, oY=0;
        for (String chart:charts.keySet()) {
            for (MyPoint point : charts.get(chart)) {
                g.setColor(COLORS[colorCharts.get(chart)]);
                x = (int) (point.getX() * pixel + pixel * NUMBER_DIV / 2 * cell);
                y = (int) (pixel * NUMBER_DIV / 2 * cell - point.getY() * pixel);
                if (point.getX() != -NUMBER_DIV / 2 * cell) g.drawLine(x, y, oX, oY);
                oX = x;
                oY = y;
            }
        }
    }

    /**
     * Method deletes chart with a formula
     * @param formula the input formula.
     */
    protected void delete(String formula){
        charts.remove(formula);
        colorCharts.remove(formula);
        this.repaint();
    }

    protected void create(String formula){
        if (formula == null || formula.equals("")) return;
        ArrayList<MyPoint> dataForChart = new ArrayList<>();
        double y;
        for (int x = -cell*NUMBER_DIV/2; x <= cell*NUMBER_DIV/2; x++) {
            try {
                y =  calk.getResult(new String[]{formula,"x="+x});
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
            dataForChart.add(new MyPoint(x, y));
        }
        if (charts.get(formula) == null) {
            colorCharts.put(formula,count%COLORS.length);
            count++;
        }
        charts.put(formula,dataForChart);

        this.repaint();
    }

    /**
     * Method for drawing.
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        cellSize = getHeight()/NUMBER_DIV;
        pixel = (double) cellSize/cell;
        drawGrid(g);
        drawAxis(g);
        drawGraph(g);
        drawHistory(g);
    }

    /**
     * Method draws history of charts.
     * @param g the Graphics
     */
    private void drawHistory(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(FONT);
        int x = cellSize*NUMBER_DIV+FONT.getSize()*2;
        int y = FONT.getSize()*2;
        g.drawString("History of charts:",x,y);
        for (String f:colorCharts.keySet()){
            g.setColor(COLORS[colorCharts.get(f)]);
            g.drawLine(x,y+=FONT.getSize()*2,getWidth() - FONT.getSize()*2,y);
            g.drawString("y = "+f,x,y);
        }

    }

    /**
     * Method draws the axis for graph
     * @param g - the Graphics
     */
    private void drawAxis(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine( cellSize *(NUMBER_DIV/2),0, cellSize *(NUMBER_DIV/2), cellSize *NUMBER_DIV);
        g.drawLine(0, cellSize *(NUMBER_DIV/2), cellSize *NUMBER_DIV, cellSize *(NUMBER_DIV/2));
        g.setFont(font);
        int i=-NUMBER_DIV*cell/2;
        for (int x = 0; x <= cellSize *NUMBER_DIV; x += cellSize) {
            g.drawString(i + "", x, cellSize * (NUMBER_DIV / 2)+font.getSize());
            i += cell;
        }
        i -= cell;
        for (int y = 0; y <= cellSize *NUMBER_DIV; y += cellSize) {
            g.drawString(i + "", cellSize * (NUMBER_DIV / 2), y+font.getSize());
            i -= cell;
        }

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
        this.repaint();
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

    /**
     * Method clear all charts in this pane.
     */
    public void clear() {
        charts.clear();
        colorCharts.clear();
        count = 0;
        this.repaint();
    }

    /**Method for set zoom for chart*/
    public void setCell (String cell) {
        switch (cell){
            case "1" -> this.cell = 1;
            case "10" -> this.cell = 10;
            case "100" -> this.cell = 100;
            case "Pi" -> this.cell = 180;
            case "Pi/2" -> this.cell = 90;
            case "Pi/4" -> this.cell = 45;
        }
        this.repaint();
        charts.keySet().forEach(this::create);
    }
}
