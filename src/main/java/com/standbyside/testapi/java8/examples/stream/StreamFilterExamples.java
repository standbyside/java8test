package com.standbyside.testapi.java8.examples.stream;

import com.standbyside.testapi.java8.entity.Person;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamFilterExamples {

  public static void example1() {
    List<String> list = Arrays.asList("spring", "node", "mkyong");
    List<String> result = list.stream()
        // 还可以写成.filter(line -> !filter.equals(line))
        .filter(line -> !"mkyong".equals(line))
        .collect(Collectors.toList());
    result.forEach(System.out::println);
  }

  public static void example2() {
    List<Person> list = Arrays.asList(
        new Person("mkyong", 30),
        new Person("jack", 20),
        new Person("lawrence", 40)
    );

    Person result = list.stream()
        // we want "jack" only
        .filter(x -> "jack".equals(x.getName()))
        // If 'findAny' then return found
        .findAny()
        // If not found, return null
        .orElse(null);
    System.out.println(result);
  }

  public static void example3() {
    List<Person> list = Arrays.asList(
        new Person("mkyong", 30),
        new Person("jack", 20),
        new Person("lawrence", 40)
    );

    Person result = list.stream()
        .filter((p) -> "jack".equals(p.getName()) && 20 == p.getAge())
        .findAny()
        .orElse(null);
    System.out.println(result);
  }

  public static void example4() {
    List<Person> list = Arrays.asList(
        new Person("mkyong", 30),
        new Person("jack", 20),
        new Person("lawrence", 40)
    );

    String name = list.stream()
        .filter(x -> "jack".equals(x.getName()))
        // convert com.standbyside.testapi.java8.stream to String
        .map(Person::getName)
        .findAny()
        .orElse("");
    System.out.println(name);

    List<String> collect = list.stream()
        .map(Person::getName)
        .collect(Collectors.toList());
    collect.forEach(System.out::println);
  }

  public static void example5() {
    // Stream cannot be reused, once it is consumed or used, the com.standbyside.testapi.java8.stream will be closed.
    // Each get() will return a new com.standbyside.testapi.java8.stream.
    Supplier<Stream<String>> supplier = () -> Stream.of(new String[] {"java", "python", "node", null, "ruby", null, "php"});
    // ex1
    List<String> result1 = supplier.get().filter(Objects::nonNull).collect(Collectors.toList());
    System.out.println(result1);
    // ex2
    List<String> result2 = supplier.get().filter(x -> x != null).collect(Collectors.toList());
    System.out.println(result2);
  }

  /**
   * Map -> Stream -> Filter -> String
   */
  public static void example6() {
    Map<Integer, String> hosting = new HashMap<>(16);
    hosting.put(1, "linode.com");
    hosting.put(2, "heroku.com");
    hosting.put(3, "digitalocean.com");
    hosting.put(4, "aws.amazon.com");

    String result = hosting.entrySet().stream()
        .filter(map -> "aws.amazon.com".equals(map.getValue()))
        .map(map -> map.getValue())
        .collect(Collectors.joining());
    System.out.println(result);
  }

  /**
   * Map -> Stream -> Filter -> Map
   */
  public static void example7() {
    Map<Integer, String> hosting = new HashMap<>(16);
    hosting.put(1, "linode.com");
    hosting.put(2, "heroku.com");
    hosting.put(3, "digitalocean.com");
    hosting.put(4, "aws.amazon.com");

    Map<Integer, String> collect = hosting.entrySet().stream()
        .filter(map -> map.getKey() == 2)
        .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
    System.out.println(collect);
  }
}
