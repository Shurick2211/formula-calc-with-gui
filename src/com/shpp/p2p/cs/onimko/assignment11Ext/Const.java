package com.shpp.p2p.cs.onimko.assignment11Ext;

import com.shpp.p2p.cs.onimko.assignment11.Assignment11Part1;

import java.awt.*;

public interface Const {

    /**The height of the application window*/
    int APPLICATION_HEIGHT = 600;

    /**The width of the application window*/
    int APPLICATION_WIDTH = APPLICATION_HEIGHT +200;

    /**Number of divisions on the graph*/
    int NUMBER_DIV = 20;

    /**Size of text field*/
    int TEXT_FIELD = 30;

    /**The font for label*/
    Font FONT = new Font("BOLD", Font.ITALIC,14);

    /**The calculates for project*/
    Assignment11Part1 calk= new Assignment11Part1();

    /** Colors for graphs lines */
    Color[] COLORS =
            {Color.BLUE, Color.RED, Color.MAGENTA, Color.GREEN};



}
