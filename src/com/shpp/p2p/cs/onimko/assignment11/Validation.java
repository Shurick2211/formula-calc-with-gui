package com.shpp.p2p.cs.onimko.assignment11;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for validation a formula.
 */
public class Validation {
  /** Index error in the formula, if it has two operation one next other. */
  private static String twoOperations;
  /**
   * Method checks an input formula.
   * @param formula the input formula
   * @throws Exception if formula invalid.
   */
  public static void validateFormula(String formula) throws Exception {
    boolean validBrackets = counter(formula,'(') == counter(formula,')');
    if (validBrackets) {
      if (!isRegex(formula.charAt(0),"[-0-9A-Za-z@\\(]"))
        throw new Exception("Formula invalid! Starts with incorrect symbol!");
      if (isTwoOperation(formula) && !(isRegex(twoOperations.charAt(1),"[-]") && isRegex(twoOperations.charAt(0),"[/*^]")))
        throw new Exception("Formula invalid! It has two operation: <"
                + twoOperations + "> of index: " + formula.indexOf(twoOperations));
      if (!isRegex(formula.charAt(formula.length()-1),"[0-9A-Za-z)]"))
        throw new Exception("Formula invalid! Ends of formula is wrong");
      validateSyntaxOfFunction(formula);
    } else throw new Exception("Brackets have no pair!");
  }

  /**Method checks a char ch equals a RegEx-string.
   * @param ch the input char.
   * @param regex the input RegEx-string.
   * @return true or false
   */
  public static boolean isRegex (char ch, String regex) {
    return isPrepared(String.valueOf(ch),regex);
  }

  /**
   * Method checks a char is math operation
   * @param el the input char
   * @return true - it is, false - it is not.
   */
  public static boolean isOperation(char el) {
    return isRegex(el,"[-+*/^]");
  }

  /**
   * Method checks - a formula contains two operation
   * one next other.
   * @param formula the input formula.
   * @return true - it is, false - it is not.
   */
  private static boolean isTwoOperation(String formula) {
    for (int i = 0; i < formula.length()-1; i++) {
      if (isOperation(formula.charAt(i)) && isOperation(formula.charAt(i + 1))) {
        twoOperations = String.valueOf(new char[]{formula.charAt(i), formula.charAt(i + 1)});
        return true;
      }
    }
    return false;
  }

  /**
   * Method checks - an input formula equals str.
   * @param formula the input formula
   * @param str the input string
   * @return if prepared - true
   */
  public static boolean isPrepared(String formula, String str) {
    Pattern pattern = Pattern.compile(str);
    Matcher matcher = pattern.matcher(formula);
    return matcher.matches();
  }

  /**
   * Method counts number of an input char in the input string.
   * @param str the input string
   * @param ch the input char
   * @return the number
   */
  public static int counter(String str, char ch) {
    int count = 0;
    for (char s : str.toCharArray())
      if (s == ch) count++;
    return count;
  }

  /**
   * Method for validate math functions and variables in the input formula.
   * @param fun - ArrayList function in the formula.
   */
  public static void validateFunctionsAndVariables(String fun, HashMap<String, Double> vars) throws Exception {
    if (vars == null || vars.keySet().stream().noneMatch(n -> n.equals(fun)))
      if (fun.length() > 2 && Operation.FUNCTIONS.keySet().stream().anyMatch(f -> f.startsWith(fun.substring(0,2))))
        throw new Exception("Function: <"+fun+"> - is invalid!");
      else throw new Exception("Variable: <"+fun+"> - has no value!");
  }

  /**
   * Method checks syntax of functions on a formula
   * @param formula the input formula.
   * @throws Exception the syntax Exception.
   */
  private static void validateSyntaxOfFunction(String formula) throws Exception {
    int ind;
    for (String f:Operation.FUNCTIONS.keySet()) {
      ind = formula.indexOf(f);
      if ( ind != -1 && (ind + f.length() >= formula.length()||formula.charAt(ind + f.length()) != '('))
          throw new Exception("Function: <" + f + "> has no argument. Argument must be in the brackets - ()!");
    }
  }

  /**
   * Method changes an input array. It returns
   * an array, where array[0] - it's a formula,
   * other elements - variables;
   * @param args the input array
   * @return the changed array.
   */
  public static String[] deleteSpace(String[] args) {
    String ar = Arrays.stream(args).reduce("", (x, y) -> x + y)
            .replaceAll(",",".");
    args = new String[(int) (Arrays.stream(args).filter(s -> s.contains("=")).count() + 1)];
    int start = 0, end;
    for (int i = 0; i < args.length; i++) {
      end =  ar.indexOf("=", start + 2) - 1;
      if (end < 0) end = ar.length();
      args[i] = ar.substring(start, end);
      if (end == ar.length()) break;
      start = end;
    }
    return args;
  }
}