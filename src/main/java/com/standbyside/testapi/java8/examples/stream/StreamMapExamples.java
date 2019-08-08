package com.standbyside.testapi.java8.examples.stream;

import com.google.common.collect.Lists;
import com.standbyside.testapi.java8.entity.Person;
import com.standbyside.testapi.java8.entity.Student;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMapExamples {

  public static void main(String[] args) {
    example4();
  }

  public static void example1() {
    List<String> list = Lists.newArrayList("a", "b", "c", "d");
    List<String> collect = list.stream().map(String::toUpperCase).collect(Collectors.toList());
    System.out.println(collect);
  }

  public static void example2() {
    List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
    List<Integer> collect = list.stream().map(n -> n * 2).collect(Collectors.toList());
    System.out.println(collect);
  }

  public static void example3() {
    List<Person> list = Lists.newArrayList(
        new Person("mkyong", 30, BigDecimal.valueOf(10000)),
        new Person("jack", 27, BigDecimal.valueOf(20000)),
        new Person("lawrence", 33, BigDecimal.valueOf(30000))
    );
    List<String> collect = list.stream().map(x -> x.getName()).collect(Collectors.toList());
    System.out.println(collect);
  }

  public static void example4() {
    List<Person> list = Lists.newArrayList(
        new Person("mkyong", 30, BigDecimal.valueOf(10000)),
        new Person("jack", 27, BigDecimal.valueOf(20000)),
        new Person("lawrence", 33, BigDecimal.valueOf(30000))
    );
    List<Student> result = list.stream().map(temp -> {
      Student obj = new Student();
      obj.setName(temp.getName());
      obj.setSex("other");
      return obj;
    }).collect(Collectors.toList());
    System.out.println(result);
  }
}
