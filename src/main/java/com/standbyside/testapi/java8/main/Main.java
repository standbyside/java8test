package com.standbyside.testapi.java8.main;

public class Main {

  public static void main(String[] args) {
    test1();
  }

  public static void test1() {
    synchronized (Main.class) {
      System.out.println("test1");
      test2();
    }

  }

  public static void test2() {
    synchronized (Main.class) {
      System.out.println("test2");
    }
  }

  /**
   * 判断数字是否为3的幂
   *
   * @param n
   * @return
   */
  public static boolean isPowerOfThree(int n) {
    if (n <= 0) {
      return false;
    } else {
      return Integer.toString(n, 3).matches("10*");
    }
  }
}