package stream;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author zhaona
 * @create 2018/5/18 14:51
 */
public class StreamShortCircuiting {

    /**
     * 【allMatch】
     *
     * 用于判断Stream中的元素是否全部满足指定条件。如果全部满足条件返回true，否则返回false。
     */
    public static void allMatch() {
        System.out.println("allMatch: " + Stream.of(1, 2, 3, 4).allMatch(integer -> integer > 0));
    }

    /**
     * 【anyMatch】
     *
     * anyMatch操作用于判断Stream中的是否有满足指定条件的元素。如果最少有一个满足条件返回true，否则返回false。
     */
    public static void anyMatch() {
        System.out.println("allMatch: " + Stream.of(1, 2, 3, 4).anyMatch(integer -> integer > 3));
    }

    /**
     * 【findAny】
     *
     * findAny操作用于获取含有Stream中的某个元素的Optional，如果Stream为空，则返回一个空的Optional。
     * 由于此操作的行动是不确定的，其会自由的选择Stream中的任何元素。在并行操作中，在同一个Stream中多次调用，可能会不同的结果。
     * 在串行调用时，默认的是获取第一个元素。
     */
    public static void findAny() {
        Optional<Integer> any = Stream.of(1, 2, 3, 4).findAny();
    }

    /**
     * 【findFirst】
     *
     * findFirst操作用于获取含有Stream中的第一个元素的Optional，如果Stream为空，则返回一个空的Optional。
     * 若Stream并未排序，可能返回含有Stream中任意元素的Optional。
     */
    public static void findFirst() {
        Optional<Integer> any = Stream.of(1, 2, 3, 4).findFirst();
    }

    /**
     * 【limit】
     *
     * limit方法将截取原Stream，截取后Stream的最大长度不能超过指定值N。
     * 如果原Stream的元素个数大于N，将截取原Stream的前N个元素。
     * 如果原Stream的元素个数小于或等于N，将截取原Stream中的所有元素。
     */
    public static void limit() {
        Stream.of(1, 2, 3,4,5)
                .limit(2)
                .forEach(System.out::println);
    }

    /**
     * 【noneMatch】
     *
     * noneMatch方法将判断Stream中的所有元素是否满足指定的条件，如果所有元素都不满足条件，返回true；否则，返回false。
     */
    public static void noneMatch() {
        System.out.println("noneMatch1:" + Stream.of(1, 2, 3, 4, 5).noneMatch(integer -> integer > 10));
        System.out.println("noneMatch2:" + Stream.of(1, 2, 3, 4, 5).noneMatch(integer -> integer < 3));
    }
}
