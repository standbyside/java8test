package examples.stream;

import com.google.common.collect.Lists;
import entity.Hosting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConvertExamples {

    public static void listToMap() {
        List<Hosting> list = Lists.newArrayList(
                new Hosting(1, "liquidweb.com", 80000),
                new Hosting(2, "linode.com", 90000),
                new Hosting(3, "digitalocean.com", 120000),
                new Hosting(4, "aws.amazon.com", 200000),
                new Hosting(5, "mkyong.com", 1)
        );

        // ex1
        Map<Integer, String> result1 = list.stream().collect(
                Collectors.toMap(Hosting::getId, Hosting::getName)
        );
        System.out.println("result1 : " + result1);

        // ex2, same with result1, just different syntax
        Map<Integer, String> result2 = list.stream().collect(
                Collectors.toMap(x -> x.getId(), x -> x.getName())
        );
        System.out.println("result2 : " + result2);

        // ex3
        Map<Integer, Hosting> result3 = list.stream().collect(
                Collectors.toMap(h -> h.getId(), h -> h)
        );
        System.out.println("result3 : " + result3);

        // ex4, duplicated Key
        list.add(new Hosting(6, "linode.com", 100000));
        Map<String, Long> result4 = list.stream().collect(
                Collectors.toMap(Hosting::getName, Hosting::getWebsites,
                        //  If the key is duplicated, do you prefer oldValue or newValue?
                        (oldValue, newValue) -> newValue
                )
        );
        System.out.println("result4 : " + result4);

        // ex4, sort & collect
        Map<String, Long> result5 = list.stream()
                .sorted(Comparator.comparingLong(Hosting::getWebsites).reversed())
                .collect(
                        Collectors.toMap(
                                Hosting::getName, Hosting::getWebsites, // key = name, value = websites
                                (oldValue, newValue) -> newValue,       // if same key, take the newValue
                                LinkedHashMap::new                      // returns a LinkedHashMap, keep order
                        )
                );
        System.out.println("result5 : " + result5);
    }

    public static void stringToCharArray() {
                // IntStream
        "password123".chars()
                // Stream<Character>
                .mapToObj(x -> (char) x)
                .forEach(System.out::println);
    }

    public static void primitiveArrayToList() {
        int[] number = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List<Integer> list = Arrays.stream(number).boxed().collect(Collectors.toList());
        list.forEach(System.out::println);
    }
}
