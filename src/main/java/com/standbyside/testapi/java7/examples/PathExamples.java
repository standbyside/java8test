package com.standbyside.testapi.java7.examples;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathExamples {

  public static void main(String[] args) {
    test1();
  }

  public static void test1() {
    Path path = Paths.get(".");
    // 包含路径名的数量
    System.out.println(path.getNameCount());
    // null
    System.out.println(path.getRoot());

    // 绝对路径
    Path absolutePath = path.toAbsolutePath();
    System.out.println(absolutePath);
    System.out.println(absolutePath.getRoot());
    System.out.println(absolutePath.getNameCount());

    // 多个连接路径
    Path path1 = Paths.get("/", "Users", "data");
    System.out.println(path1);
  }
}
