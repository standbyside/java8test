package examples.stream;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * For object arrays, both are calling the same Arrays.stream.
 * For primitive arrays, I prefer Arrays.stream as well, because it returns fixed size IntStream directly, easier to manipulate it.
 */
public class StreamArrayExamples {

    public static void example1() {
        String[] array = {"a", "b", "c", "d", "e"};

        // Arrays.stream
        Stream<String> stream1 = Arrays.stream(array);
        stream1.forEach(System.out::println);

        // Stream.of
        Stream<String> stream2 = Stream.of(array);
        stream2.forEach(System.out::println);
    }

    public static void example2() {
        int[] intArray = {1, 2, 3, 4, 5};

        // 1. Arrays.stream -> IntStream
        IntStream intStream1 = Arrays.stream(intArray);
        intStream1.forEach(System.out::println);

        // 2. Stream.of -> Stream<int[]>
        Stream<int[]> temp = Stream.of(intArray);
        // Can't print Stream<int[]> directly, convert / flat it to IntStream
        IntStream intStream2 = temp.flatMapToInt(x -> Arrays.stream(x));
        intStream2.forEach(System.out::println);
    }
}
