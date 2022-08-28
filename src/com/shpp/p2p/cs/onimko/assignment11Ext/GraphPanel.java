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
import javax.swing.JComponent;

public class GraphPanel extends JComponent implements ComponentListener, Const{

    /**Division in a cell*/
    int cell = 10;
    int trigonometric = 1;
    /**Cell size*/
    int cellSize;
    /**Number of pixels per division*/
    double pixel;
    /**Storage of chart*/
    Map<String, ArrayList<MyPoint>> charts= new HashMap<>();
    /**Font for axis*/
    Font font = new Font("Verdana",Font.ITALIC,8);

    private void drawGraph(Graphics g) {
        int x, y, oX=0, oY=0;
        for (String chart:charts.keySet())
            for (MyPoint point:charts.get(chart)){
                g.setColor(Color.RED);
                x = (int) (point.getX() * pixel + pixel * NUMBER_DIV / 2 * cell);
                y = (int) (pixel * NUMBER_DIV / 2 * cell - point.getY() * pixel);
                if (point.getX() != - NUMBER_DIV / 2 * cell) g.drawLine(x, y, oX, oY);
                oX = x;
                oY = y;
            }
    }


    protected void delete(String formula){
        charts.remove(formula);
        update(this.getGraphics());
    }

    protected void create(String formula){
        if (formula == null || formula.equals("")) return;
        if (formula.contains("sin") || formula.contains("cos") )
            trigonometric = 180;
        else trigonometric = 1;
        Assignment11Part1 calk= new Assignment11Part1();
        ArrayList<MyPoint> dataForChart = new ArrayList<>();
        double y = 0;
        for (int x = -cell*NUMBER_DIV/2; x <= cell*NUMBER_DIV/2; x++) {
            try {
                y = calk.getResult(new String[]{formula,"x="+x})*trigonometric;
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
        this.removeAll();
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

    public void clear() {
        charts.clear();
        this.update(this.getGraphics());
    }

    public void setCell (String cell) {
        switch (cell){
            case "1" -> this.cell = 1;
            case "10" -> this.cell = 10;
            case "100" -> this.cell = 100;
            case "Pi" -> this.cell = 180;
            case "Pi/2" -> this.cell = 90;
            case "Pi/4" -> this.cell = 45;
        }
        this.removeAll();
        update(this.getGraphics());
    }
}
