package com.standbyside.testapi.java8.examples.stream;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SortMapExamples {

  /**
   * Steps to sort a Map in Java 8.
   * (1) Convert a Map into a Stream
   * (2) Sort it
   * (3) Collect and return a new LinkedHashMap (keep the order)
   * By default, Collectors.toMap will returns a HashMap
   */
  public static void quickExplanation() {
    Map<String, Integer> unsortMap = getExampleMap();

    System.out.println("Original...");
    System.out.println(unsortMap);

    Map<String, Integer> result = unsortMap.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .collect(
            Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new
            )
        );
    System.out.println("Sorted...");
    System.out.println(result);
  }

  public static void sortByKeys() {
    Map<String, Integer> unsortMap = getExampleMap();

    System.out.println("Original...");
    System.out.println(unsortMap);

    // sort by keys, a,b,c..., and return a new LinkedHashMap
    // toMap() will returns HashMap by default, we need LinkedHashMap to keep the order.
    Map<String, Integer> result1 = unsortMap.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .collect(
            Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new
            )
        );


    // Not Recommend, but it works.
    // Alternative way to sort a Map by keys, and put it into the "result" map
    Map<String, Integer> result2 = new LinkedHashMap<>();
    unsortMap.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .forEachOrdered(x -> result2.put(x.getKey(), x.getValue()));

    System.out.println("Sorted...");
    System.out.println(result1);
    System.out.println(result2);

  }

  public static void sortByValues() {
    Map<String, Integer> unsortMap = getExampleMap();

    System.out.println("Original...");
    System.out.println(unsortMap);

    // sort by values, and reserve it, 10,9,8,7,6...
    Map<String, Integer> result1 = unsortMap.entrySet().stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .collect(
            Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new
            )
        );


    // Alternative way
    Map<String, Integer> result2 = new LinkedHashMap<>();
    unsortMap.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .forEachOrdered(x -> result2.put(x.getKey(), x.getValue()));

    System.out.println("Sorted...");
    System.out.println(result1);
    System.out.println(result2);
  }

  private static Map<String, Integer> getExampleMap() {
    Map<String, Integer> map = new HashMap<>(16);
    map.put("z", 10);
    map.put("b", 5);
    map.put("a", 6);
    map.put("c", 20);
    map.put("d", 1);
    map.put("e", 7);
    map.put("y", 8);
    map.put("n", 99);
    map.put("g", 50);
    map.put("m", 2);
    map.put("f", 9);
    return map;
  }
}
