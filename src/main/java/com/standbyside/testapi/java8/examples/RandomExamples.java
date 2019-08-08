package com.standbyside.testapi.java8.examples;

import java.util.Random;

/**
 * @author zhaona
 * @create 2018/5/18 14:51
 */
public class RandomExamples {

  public static void example1(int min, int max) {
    System.out.println((int) (Math.random() * ((max - min) + 1)) + min);
  }

  /**
   * The Random.nextInt(n) is more efficient than Math.random() * n, read this Oracle forum post.
   * https://community.oracle.com/message/6596485#thread-message-6596485
   *
   * @param min
   * @param max
   */
  public static void example2(int min, int max) {
    System.out.println(new Random().nextInt((max - min) + 1) + min);
  }

  public static void example3(int min, int max) {
    System.out.println(new Random().ints(min, (max + 1)).limit(1).findFirst().getAsInt());
  }

  /**
   * Generates random integers in a range between min (inclusive) and max (exclusive), with com.standbyside.testapi.java8.stream size of 10. And print out the items with forEach.
   *
   * @param min
   * @param max
   */
  public static void example4(int min, int max) {
    new Random().ints(10, min, max).forEach(System.out::println);
  }
}
