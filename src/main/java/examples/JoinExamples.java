package examples;

import com.google.common.collect.Lists;
import entity.Person;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author zhaona
 * @create 2018/5/18 14:51
 */
public class JoinExamples {

    public static void stringJoiner() {
        // ex1
        StringJoiner sj1 = new StringJoiner(",");
        sj1.add("aaa");
        sj1.add("bbb");
        sj1.add("ccc");
        System.out.println(sj1.toString());

        // ex2
        StringJoiner sj2 = new StringJoiner("/", "prefix-", "-suffix");
        sj2.add("2016");
        sj2.add("02");
        sj2.add("26");
        System.out.println(sj2.toString());

        // ex3
        System.out.println(String.join("-", "2015", "10", "31" ));

        // ex4
        System.out.println(String.join(", ", Lists.newArrayList("java", "python", "nodejs", "ruby")));
    }

    public static void collectorsJoining() {
        // ex1
        List<String> list1 = Lists.newArrayList("java", "python", "nodejs", "ruby");
        String result1 = list1.stream().collect(Collectors.joining(" | "));
        System.out.println(result1);

        // ex2
        List<Person> list2 = Lists.newArrayList(
                new Person("Dragon Blaze", 5),
                new Person("Angry Bird", 5),
                new Person("Candy Crush", 5)
        );
        String result2 = list2.stream().map(x -> x.getName())
                .collect(Collectors.joining(", ", "{", "}"));
        System.out.println(result2);
    }

    public static void streamJoinArray() {
        // ex1，join object type array
        String[] s1 = new String[]{"a", "b", "c"};
        String[] s2 = new String[]{"d", "e", "f"};
        String[] s3 = new String[]{"g", "h", "i"};
        String[] result = Stream.of(s1, s2, s3).flatMap(Stream::of).toArray(String[]::new);
        System.out.println(Arrays.toString(result));

        int[] int1 = new int[]{1, 2, 3};
        int[] int2 = new int[]{4, 5, 6};
        int[] int3 = new int[]{7, 8, 9};

        // ex2，primitive type array
        int[] result2 = IntStream.concat(Arrays.stream(int1), IntStream.concat(Arrays.stream(int2), Arrays.stream(int3))).toArray();
        System.out.println(Arrays.toString(result2));

        // ex3
        int[] result3 = Stream.of(int1, int2, int3).flatMapToInt(IntStream::of).toArray();
        System.out.println(Arrays.toString(result3));
    }
}
