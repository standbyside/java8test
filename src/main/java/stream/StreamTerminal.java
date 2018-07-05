package stream;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

/**
 * @author zhaona
 * @create 2018/5/18 14:51
 */
public class StreamTerminal {

    public static void main(String[] args) {
        reduce_3();
    }

    /**
     * 【collect】
     * TODO
     */
    public static void collect() {

    }


    /**
     * 【count】
     *
     * 返回Stream中元素的个数。
     */
    public static void count() {
        System.out.println("count:" + Stream.of(1, 2, 3, 4, 5).count());
    }

    /**
     * 【forEach】
     *
     * 用于遍历Stream中的所元素，避免了使用for循环，让代码更简洁，逻辑更清晰
     */
    public static void forEach() {
        Stream.of(5, 4, 3, 2, 1)
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * 【forEachOrdered】
     *
     * 与forEach类似，都是遍历Stream中的所有元素，不同的是，如果该Stream预先设定了顺序，会按照预先设定的顺序执行(Stream是无序的)，默认为元素插入的顺序
     */
    public static void forEachOrdered() {
        Stream.of(5, 2, 1, 4, 3)
                .forEachOrdered(integer -> {
                    System.out.println("integer:"+integer);
                });
    }

    /**
     * 【max/min】
     *
     * 根据指定的Comparator，返回一个Optional，该Optional中的value值就是Stream中最大/最小的元素
     * 不管是最大值还是最小值起决定作用的是Comparator，它决定了元素比较大小的原则
     */
    public static void max() {
        Optional<Integer> max = Stream.of(1, 2, 3, 4, 5)
                .max((o1, o2) -> o2 - o1);
        System.out.println("max:" + max.get());
    }

    /**
     * 【reduce】
     *
     * reduce 操作可以实现从Stream中生成一个值，其生成的值不是随意的，而是根据指定的计算模型。
     *
     * reduce方法有三个override的方法：
     *   (1) Optional<T> reduce(BinaryOperator<T> accumulator);
     *   (2) T reduce(T identity, BinaryOperator<T> accumulator);
     *   (3) <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner);
     *
     * TODO
     */


    /**
     * (1)
     * 其接受一个函数接口BinaryOperator<T>，而这个接口又继承于BiFunction<T, T, T>
     * BinaryOperator接口，可以看到reduce方法接受一个函数，这个函数有两个参数，第一个参数是上次函数执行的返回值（也称为中间结果），第二个参数是stream中的元素，
     * 这个函数把这两个值相加，得到的和会被赋值给下次执行这个函数的第一个参数。
     * 要注意的是：第一次执行的时候第一个参数的值是Stream的第一个元素，第二个参数是Stream的第二个元素。这个方法返回值类型是Optional。
     * 未定义初始值，第一次执行的时候第一个参数的值是Stream的第一个元素，第二个参数是Stream的第二个元素
     */
    public static void reduce_1() {
        Optional accResult = Stream.of(1, 2, 3, 4)
                .reduce((acc, item) -> {
                    System.out.println("acc : "  + acc);
                    acc += item;
                    System.out.println("item: " + item);
                    System.out.println("acc+ : "  + acc);
                    System.out.println("--------");
                    return acc;
                });
        System.out.println("accResult: " + accResult.get());
        System.out.println("--------");
    }

    /**
     * (2)
     * 与第一种变形相同的是都会接受一个BinaryOperator函数接口，不同的是其会接受一个identity参数，用来指定Stream循环的初始值。
     * 如果Stream为空，就直接返回该值。
     * 该方法不会返回Optional，因为该方法不会出现null。
     * 定义了初始值，第一次执行的时候第一个参数的值是初始值，第二个参数是Stream的第一个元素
     */
    public static void reduce_2() {
        int accResult = Stream.of(1, 2, 3, 4)
                .reduce(0, (acc, item) -> {
                    System.out.println("acc : "  + acc);
                    acc += item;
                    System.out.println("item: " + item);
                    System.out.println("acc+ : "  + acc);
                    System.out.println("--------");
                    return acc;
                });
        System.out.println("accResult: " + accResult);
        System.out.println("--------");
    }

    /**
     * (3)
     * 第一个参数返回实例u，传递你要返回的U类型对象的初始化实例u，
     * 第二个参数累加器accumulator，可以使用二元?表达式（即二元lambda表达式），声明你在u上累加你的数据来源t的逻辑，
     * 例如(u,t)->u.sum(t),此时lambda表达式的行参列表是返回实例u和遍历的集合元素t，函数体是在u上累加t，
     * 第三个参数组合器combiner，同样是二元?表达式，(u,t)->u。
     *
     * 因为第三个参数用来处理并发操作，如何处理数据的重复性，应多做考虑，否则会出现重复数据
     */
    public static void reduce_3() {

        /**
         * 传递给第一个参数是ArrayList, 在第二个函数参数中打印了"BiFunction"，而在第三个参数接口中打印了函数接口中打印了"BinaryOperator"。
         * 可是，看打印结果，只是打印了"BiFunction"，而没有打印"BinaryOperator"，说明第三个函数参数病没有执行。
         * 因为Stream是支持并发操作的，为了避免竞争，对于reduce线程都会有独立的result，combiner的作用在于合并每个线程的result得到最终结果。
         * 这也说明了了第三个函数参数的数据类型必须为返回数据类型了
         */
        ArrayList<Integer> accResult_ = Stream.of(1, 2, 3, 4)
                .reduce(new ArrayList<>(),
                        new BiFunction<ArrayList<Integer>, Integer, ArrayList<Integer>>() {
                            @Override
                            public ArrayList<Integer> apply(ArrayList<Integer> acc, Integer item) {
                                acc.add(item);
                                System.out.println("item: " + item);
                                System.out.println("acc+ : " + acc);
                                System.out.println("BiFunction");
                                return acc;
                            }
                        },
                        new BinaryOperator<ArrayList<Integer>>() {
                            @Override
                            public ArrayList<Integer> apply(ArrayList<Integer> acc, ArrayList<Integer> item) {
                                System.out.println("BinaryOperator");
                                acc.addAll(item);
                                System.out.println("item: " + item);
                                System.out.println("acc+ : " + acc);
                                System.out.println("--------");
                                return acc;
                            }
                        });
        System.out.println("accResult_: " + accResult_);
    }
}
