package examples.stream;

import com.google.common.collect.Lists;
import entity.Student;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FlatMapExamples {

    /**
     * String[] + flatMap
     */
    public static void example1() {
        // Stream<String[]>
        Stream<String[]> temp = Arrays.stream(new String[][]{{"a", "b"}, {"c", "d"}, {"e", "f"}});
        // Stream<String>
        Stream<String> flatStream = temp.flatMap(x -> Arrays.stream(x));
        Stream<String> stream = flatStream.filter(x -> "a".equals(x.toString()));
        stream.forEach(System.out::println);
    }

    /**
     * Set + flatMap
     */
    public static void example2() {
        Student obj1 = new Student();
        obj1.setName("mkyong");
        obj1.addBook("Java 8 in Action");
        obj1.addBook("Spring Boot in Action");
        obj1.addBook("Effective Java (2nd Edition)");

        Student obj2 = new Student();
        obj2.setName("zilap");
        obj2.addBook("Learning Python, 5th Edition");
        obj2.addBook("Effective Java (2nd Edition)");

        List<Student> list = Lists.newArrayList(obj1, obj2);
        // Try comments the flatMap(x -> x.stream()) the Collectors.toList() will prompts a compiler error,
        // because it has no idea how to collect a stream of Set object.
        List<String> collect = list.stream()
                        .map(x -> x.getBook())      // Stream<Set<String>>
                        .flatMap(x -> x.stream())   // Stream<String>
                        .distinct()
                        .collect(Collectors.toList());

        collect.forEach(System.out::println);
    }

    /**
     * Primitive + flatMapToInt
     * For primitive type, you can use flatMapToInt
     */
    public static void example3() {
        // Stream<int[]>
        Stream<int[]> streamArray = Stream.of(new int[]{1, 2, 3, 4, 5, 6});
        // Stream<int[]> -> flatMap -> IntStream
        IntStream intStream = streamArray.flatMapToInt(x -> Arrays.stream(x));
        intStream.forEach(System.out::println);
    }
}
