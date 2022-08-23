package com.shpp.p2p.cs.onimko.assignment11;

import java.util.HashMap;

/**
 * Class for test.
 */
public class Test extends Assignment11Part1 {
  /**Test data*/
  private final HashMap<String,Double> testVars = new HashMap<>();
  {
    testVars.put("a",2d);
    testVars.put("b",1d);
  }

  /**
   * Start method for test
   */
  public static void main(String[] args)  {
    try {
      Test test = new Test();
      String[] testArgs;
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
      System.out.println("TestExceptionFormula: " + (test.testExceptionFormula("4.0+a/2*b^2+23+")
              && test.testExceptionFormula("4.0+a/2*b^2+*23") && test.testExceptionFormula("/4.0+a/2*b^2+23")));
      System.out.println("---------------------------");
      System.out.println("TestExceptionFunction: " + test.testExceptionFunction());
      System.out.println("---------------------------");
      System.out.println("TestExceptionFunction: " + test.testExceptionFunctionSyntax());
      System.out.println("---------------------------");

      // test some formulas
      testArgs= new String[] {"log10(a)", "a=100"};
      Assignment11Part1.main(testArgs);
      System.out.println("---------------------------");

      testArgs= new String[] {"log2(b)", "b=" + Math.E};//
      Assignment11Part1.main(testArgs);
      System.out.println("---------------------------");

      testArgs= new String[] {"2+2*2"};
      Assignment11Part1.main(testArgs);
      System.out.println("---------------------------");

      for (int i = 1; i < 5; i++) {
        testArgs= new String[]{"a*sin(30)+b*sqrt(4)", "a=" + i, "b=" + (i + 1)};
        Assignment11Part1.main(testArgs);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private boolean testExceptionFunctionSyntax() {
    try {
      calculate("sin60",null);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return true;
    }
    return false;
  }

  private boolean testExceptionFunction() {
    try {
      calculate("loga10(10)",null);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return true;
    }
    return false;
  }

  private boolean testExceptionFormula(String formula) {
    try {
      calculate(formula, testVars);
    } catch (Exception e) {
      return true;
    }
    return false;
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
    return calculate("1+(1+3*(4+5.5-sin(60*cos(a))))/7",
            parseVars(new String[] {"", "a=-60"})) == 5.0;
  }

  private  boolean testBrackets() throws Exception {
    return calculate("8-(a^2-3*(b+1))^2+b*10/2+(a-b)*b" , testVars) == 10d;
  }
}
