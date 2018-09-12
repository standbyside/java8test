package examples.stream;

import com.google.common.collect.Lists;
import entity.Fruit;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamGroupingByExample {

  public static void main(String[] args) {
    example4();
  }

  public static void example1() {
    List<String> list = Lists.newArrayList(
        "apple", "apple", "banana", "apple", "orange", "banana", "papaya"
    );

    Map<String, Long> result = list.stream()
        .collect(
            Collectors.groupingBy(
                Function.identity(), Collectors.counting()
            )
        );
    System.out.println(result);
  }

  public static void example2() {
    List<String> list = Lists.newArrayList(
        "apple", "apple", "banana", "apple", "orange", "banana", "papaya"
    );
    Map<String, Long> result = list.stream()
        .collect(
            Collectors.groupingBy(
                Function.identity(), Collectors.counting()
            )
        );

    Map<String, Long> finalMap = new LinkedHashMap<>();

    result.entrySet().stream()
        .sorted(
            Map.Entry.<String, Long>comparingByValue().reversed()
        )
        .forEachOrdered(
            e -> finalMap.put(e.getKey(), e.getValue())
        );
    System.out.println(finalMap);
  }

  public static void example3() {
    List<Fruit> list = Lists.newArrayList(
        new Fruit("apple", 10, new BigDecimal("9.99")),
        new Fruit("banana", 20, new BigDecimal("19.99")),
        new Fruit("orange", 10, new BigDecimal("29.99")),
        new Fruit("watermelon", 10, new BigDecimal("29.99")),
        new Fruit("papaya", 20, new BigDecimal("9.99")),
        new Fruit("apple", 10, new BigDecimal("9.99")),
        new Fruit("banana", 10, new BigDecimal("19.99")),
        new Fruit("apple", 20, new BigDecimal("9.99"))
    );

    Map<String, Long> counting = list.stream()
        .collect(
            Collectors.groupingBy(Fruit::getName, Collectors.counting())
        );
    System.out.println(counting);

    Map<String, Integer> sum = list.stream()
        .collect(
            Collectors.groupingBy(Fruit::getName, Collectors.summingInt(Fruit::getQty))
        );
    System.out.println(sum);
  }

  public static void example4() {
    List<Fruit> list = Lists.newArrayList(
        new Fruit("apple", 10, new BigDecimal("9.99")),
        new Fruit("banana", 20, new BigDecimal("19.99")),
        new Fruit("orange", 10, new BigDecimal("29.99")),
        new Fruit("watermelon", 10, new BigDecimal("29.99")),
        new Fruit("papaya", 20, new BigDecimal("9.99")),
        new Fruit("apple", 10, new BigDecimal("9.99")),
        new Fruit("banana", 10, new BigDecimal("19.99")),
        new Fruit("apple", 20, new BigDecimal("9.99"))
    );

    Map<BigDecimal, List<Fruit>> groupByPriceMap = list.stream()
        .collect(Collectors.groupingBy(Fruit::getPrice));
    System.out.println(groupByPriceMap);

    Map<BigDecimal, Set<String>> result = list.stream()
        .collect(
            Collectors.groupingBy(
                Fruit::getPrice,
                Collectors.mapping(Fruit::getName, Collectors.toSet())
            )
        );
    System.out.println(result);
  }

}
