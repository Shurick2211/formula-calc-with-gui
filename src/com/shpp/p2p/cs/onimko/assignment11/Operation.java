package com.shpp.p2p.cs.onimko.assignment11;

import java.util.HashMap;
import java.util.function.BinaryOperator;

/**
 * Class for math operations
 */
public class Operation {

    /**Math functions*/
    public static final HashMap<String, IAction> FUNCTIONS = new HashMap<>();
    static {
        FUNCTIONS.put("sin", v -> Math.sin(Math.toRadians(v)));
        FUNCTIONS.put("cos", v -> Math.cos(Math.toRadians(v)));
        FUNCTIONS.put("tan", v -> Math.tan(Math.toRadians(v)));
        FUNCTIONS.put("atan", Math::atan);
        FUNCTIONS.put("log10", Math::log10);
        FUNCTIONS.put("log2", Math::log);
        FUNCTIONS.put("sqrt", Math::sqrt);
    }

    /**Simple actions with two numbers*/
    public static final HashMap<Character, BinaryOperator<Double>> ACTIONS = new HashMap<>();
    static {
        ACTIONS.put('+', Double::sum);
        ACTIONS.put('-', (x, y) -> x - y);
        ACTIONS.put('*', (x, y) -> x * y);
        ACTIONS.put('/', (x, y) -> x / y);
        ACTIONS.put('^', Math::pow);
    }
}