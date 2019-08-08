package com.standbyside.testapi.java8.main;

public class Main {

  public static void main(String[] args) {
    System.out.println(Integer.toString(9, 3));
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