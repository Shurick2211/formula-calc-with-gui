package com.shpp.p2p.cs.onimko.assignment11;

import java.util.HashMap;

/**
 * Class for test.
 */
public class Test extends Assignment11Part1 {
  private HashMap<String,Double> testVars = new HashMap<>();
  {
    testVars.put("a",2d);
    testVars.put("b",1d);
  }

  public static void main(String[] args)  {
    try {
      Test test = new Test();
      System.out.println("---------------------------");
      System.out.println("MainTest: " + test.testMain());
      System.out.println("---------------------------");
      System.out.println("TestBrackets: " + test.testBrackets());
      System.out.println("---------------------------");
      System.out.println("TestNullPointerException: " + test.testNullPointException());
      System.out.println("---------------------------");
      System.out.println("TestNumberFormatException: " + test.testNumberFormatException());
      System.out.println("---------------------------");
      System.out.println("TestException: " + test.testException());
      System.out.println("---------------------------");
      System.out.println("TestExceptionFormula: " + test.testExceptionFormula("4.0+a/2*b^2+23+"));
      System.out.println("---------------------------");
      System.out.println(test.calculate( "-3 * a+3.5/0.5+ b", test.testVars));
      System.out.println(test.calculate( "a^0+0.3*5/0.5+b/a", test.testVars));
      System.out.println(test.calculate( " - a ^2*( - 4)", test.testVars));
      System.out.println(test.calculate( "(-a+b)*2", test.testVars));
      System.out.println(test.calculate( "-2.2+2", null));
      System.out.println("---------------------------");
      String[] testArgs= {"a+b", "a=2", "b=1"};
      test.calculate(testArgs[0], test.parseVars(testArgs));
      for (int i = 1; i < 5; i++) {
        testArgs[1] = "a=" + (i+1);
        testArgs[2] = "b=" + i;
        test.printResult(testArgs[0], test.parseVars(testArgs));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private boolean testExceptionFormula(String formula) {
    try {
      calculate(formula, testVars);
    } catch (Exception e) {
      return true;
    }
    return false;
  }

  public boolean test() throws Exception {
    return testMain() && testBrackets() && testNullPointException() && testNumberFormatException()
            && testException() && testExceptionFormula("4.0+a/2*b^2+23+")
            && testExceptionFormula("4.0+a/2*b^2+*23") && testExceptionFormula("/4.0+a/2*b^2+23");
  }

  private  boolean testException() {
    try {
      calculate("-4+a/2*b^2",parseVars(new String[]{"-4+a/2*b^2", "a=1=4", "b=2"}));
    } catch (Exception e) {
      return e.getMessage().equals("Your enter variables is wrong!");
    }
    return false;
  }

  private  boolean testNumberFormatException() {
    try {
      testVars.put("c",Double.parseDouble("d"));
      calculate("4*c+a/2*b^2", testVars);
    } catch (Exception e) {
      testVars.remove("c");
      return e.getClass().equals(NumberFormatException.class);
    }
    return false;
  }

  private  boolean testNullPointException() {
    try {
      calculate("4*c+a/2*b^2", testVars);
    } catch (Exception e) {
      return e.getClass().equals(NullPointerException.class);
    }
    return false;
  }

  private  boolean testMain() throws Exception {
    return calculate("-4+a/2*b^2",
            parseVars(new String[] {"-4+a/2*b^2", "a=1", "b=2"})) == -2.0;
  }

  private  boolean testBrackets() throws Exception {
    return calculate("8-(a^2-3*(b+1))^2+b*10/2+(a-b)*b" , testVars) == 10d;
  }
}
