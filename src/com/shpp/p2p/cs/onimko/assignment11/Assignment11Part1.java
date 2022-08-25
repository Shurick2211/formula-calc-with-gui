package com.shpp.p2p.cs.onimko.assignment11;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Assignment11Part1 {

  /**The numbers on formula*/
  private final ArrayList<Double> numbers= new ArrayList<>();
  /**The functions on formula*/
  private final ArrayList<String> functions= new ArrayList<>();
  /**The indexes of variables in an input formula*/
  private final HashMap<Integer,String> numVars = new HashMap<>();
  /** The prepared formula for calculation */
  private String parsedFormula;
  /** The start formula for calculation */
  private String startFormula;
  /**The entity for Main*/
  static final private Assignment11Part1 entity = new Assignment11Part1();

  /**
   * The main method.
   * If variable in args or formula are wrong then program will be ends
   * and print exception's message.
   * @param args the input array of string
   */
  public static void main(String[] args)  {
    try {
      if (args.length == 0) throw new Exception("You don't input formula!");
      for (int i = 0; i < args.length; i++)
        args[i] = args[i].replaceAll(" ","").replaceAll(",",".");
      //args = Validation.deleteSpace(args);
      entity.checksOldFormula(args[0]);
      entity.printResult(entity.startFormula, entity.parseVars(args));
    } catch (NumberFormatException e) {
      System.err.println("Your values of variables wrong! " + e.getMessage());
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * Method checks that an input formula equals
   * the formula of last calculated.
   * If it is true - don't parse the input formula,
   * else formula - parse.
   * @param formula - the input formula.
   */
  private void checksOldFormula(String formula) throws Exception {
    formula = formula.trim().replaceAll(" ","");
    if(startFormula == null || !startFormula.equals(formula)) {
      Validation.validateFormula(formula);
      startFormula = formula;
      parsedFormula = parseFormula(startFormula);
    }
  }

  /**
   * Method print result of calculation
   * @param variables the input variables
   */
  protected void printResult (String startFormula, HashMap<String,Double> variables) throws Exception {
    DecimalFormat decimalFormat = new DecimalFormat("#.#####");
    System.out.println(startFormula + " = " + decimalFormat.format(calculate(parsedFormula, variables)));
    if (!variables.isEmpty()) System.out.println("Where: " + variables);
  }

  /**
   * Method for parse arrays of string to HashMap,
   * where string key - name of variable,
   * double value - value of variable.
   * @param args the input array of string
   * @return the HashMap
   * @throws Exception if variables input invalid.
   */
  protected HashMap<String,Double> parseVars(String[] args) throws Exception {
    HashMap<String,Double> variables = new HashMap<>();
    String [] keyVal;
    double value;
    for (int i = 1; i < args.length; i++){
      keyVal = args[i].replaceAll(" ", "").split("=");
      if (keyVal.length != 2) throw new Exception("Your enter variables is wrong!");
      value = Double.parseDouble(keyVal[1].replaceAll(",", "."));
      variables.put(keyVal[0], value);
    }
    return variables;
  }

  /**
   * Method calculates result.
   * @param formula the input formula.
   * @param variables the input variable.
   * @return the result.
   */
  public double calculate(String formula, HashMap<String,Double> variables) throws Exception {
    if (!Validation.isPrepared(formula,"[-+*/^()@]*") && !formula.equals(startFormula))
      checksOldFormula(formula);
    formula = this.parsedFormula;
    ArrayList<Double> nums = substitutionsVariables(variables);
    double rez;
    if (formula.contains("(")) formula = doOperationInBracket(formula, nums);
    rez = doQueOfOperation(formula,nums);
    return rez;
  }

  /**
   * Method does math operation for que, that wrote in the formula.
   * @param formula the input formula.
   * @param nums the ArrayList of numbers for operations.
   * @return result.
   */
  private  double doQueOfOperation(String formula, ArrayList<Double> nums) throws Exception {
    String[] operation = {"^","/","*","-","+"};
    int index;
    for (String o:operation)
      while (formula.contains(o)){
        index = formula.indexOf(o);
        if (o.equals("/") && nums.get(index+1) == 0.0) throw new Exception("Can't divide by zero");
        if (o.equals("^") && nums.get(index) == 0.0 && nums.get(index+1) < 0.0)
          throw new Exception("Can't raise zero to a negative power");
        nums.add(index, Operation.ACTIONS.get(formula.charAt(index)).apply(nums.get(index),nums.get(index+1)));
        nums.remove(index + 1);
        if (index + 2 <= nums.size()) nums.remove(index +1);
        formula = formula.substring(0, index)+formula.substring(index+1);
      }
    return nums.get(0);
  }

  /**
   * Method calculates values on the brackets,
   * and changes formula and ArrayList.
   * @param formula the input formula
   * @param nums the ArrayList of numbers for operations.
   * @return formula without brackets
   */
  private  String doOperationInBracket(String formula, ArrayList<Double> nums) throws Exception {
    int indexStart, indexEnd, tmp;
    ArrayList<Double> temp = new ArrayList<>();
    while (formula.contains("(")){
      indexStart = formula.lastIndexOf("(");
      indexEnd = formula.indexOf(")",indexStart);
      tmp = indexStart-Validation.counter(formula,'(')-Validation.counter(formula.substring(0,indexStart),')')+1;
      if (!functions.isEmpty()) tmp -=Validation.counter(formula,'@');
      for (int i = indexStart ; i < indexEnd; i++) {
        temp.add(nums.get(tmp));
        nums.remove(tmp);
      }
      if (indexEnd-indexStart == 1 && formula.charAt(indexStart-1) == '@') {
        if (functions.get(Validation.counter(formula,'@')-1).equals("sqrt") && temp.get(0) < 0.0)
          throw new Exception("Can't get root of a negative number");
        nums.add(tmp, Operation.FUNCTIONS.get(functions.get(Validation.counter(formula,'@')-1)).calculate(temp.get(0)));
        indexStart--;
      }
      else {
        nums.add(tmp, doQueOfOperation(formula.substring(indexStart + 1, indexEnd), temp));
        if (indexStart > 0 && formula.charAt(indexStart-1) == '@'){
          indexStart++;
          indexEnd--;
        }
      }
      formula = formula.substring(0,indexStart)+formula.substring(indexEnd+1);
      temp.clear();
    }
    return formula;
  }

  /**
   * Method changed string formula and return:
   * formula consist with sign of math operations,
   * and an ArrayList for numbers for operation.
   * @param formula the input formula
   * @return string formula consist with sign of math operations.
   */
  private String parseFormula(String formula) {
    System.out.println("Parse formula");
    clearArrays();
    String  function;
    StringBuilder number = new StringBuilder();
    StringBuilder operation = new StringBuilder();
    double num=0;
    char el;
    if (formula.startsWith("-")) {
      number.append('-');
      formula = formula.substring(1);
    }
    for(int i = 0; i < formula.length(); i++) {
      el = formula.charAt(i);
      if (el == '(' && formula.charAt(i+1) == '-') {
        number.append('-');
        i++;
      }
      if (Character.isDigit(el) || el == '.') number.append(el);
      if (Character.isLetter(el)) {
        function = getFunctionName(formula,i);
        if (Operation.FUNCTIONS.get(function) != null) {
          functions.add(function);
          operation.append('@');
        } else {
          num = '!';
          if(number.toString().equals("-"))  num = -el;
          number = new StringBuilder();
          numVars.put(numbers.size(), function);
        }
        i += function.length() - 1;
      }
      if (Validation.isOperation(el) || el == '(' || el ==')') operation.append(el);
      if (Validation.isOperation(el) || i == formula.length()-1) {
        if (!number.isEmpty() && !Character.isLetter(el)) {
          num = Double.parseDouble(number.toString());
          number = new StringBuilder();
        }
        numbers.add(num);
      }
    }
    return operation.toString();
  }

  /**
   * Method returns a function's name for parse, getting with input formula.
   * @param formula the input formula.
   * @param i the start position function in the formula.
   * @return the function's name
   */
  private String getFunctionName(String formula, int i) {
    StringBuilder operation = new StringBuilder();
    for (;i < formula.length() && !Validation.isRegex(formula.charAt(i), "[-+*/^\\(\\)]"); i++ )
      operation.append(formula.charAt(i));
    return operation.toString().toLowerCase();
  }

  /**
   * Method substitutions variables and return a list
   * of numbers for calculation.
   * @param vars hashmap of vars.
   * @return ArrayList of numbers for calculation.
   */
  private  ArrayList<Double> substitutionsVariables(HashMap<String, Double> vars) throws Exception {
    ArrayList<Double> variables = new ArrayList<>(numbers);
    for (Integer numVar : numVars.keySet()) {
      Validation.validateFunctionsAndVariables(numVars.get(numVar),vars);
      Double temp = 1d;
      if ( numbers.get(numVar) < 0) temp = -temp;
      temp *= vars.get(numVars.get(numVar));
      variables.set(numVar, temp);
    }
    return variables;
  }

  /**
   * Method clears arrays (ArrayList) before parsing formula.
   */
  private void clearArrays() {
    numbers.clear();
    numVars.clear();
    functions.clear();
  }
}