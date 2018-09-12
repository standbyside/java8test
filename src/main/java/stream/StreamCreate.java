package stream;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author zhaona
 * @create 2018/5/18 14:51
 * <p>
 * <p>
 * 我们有多种方式生成Stream：
 * (1) Stream接口的静态工厂方法（注意：Java8里接口可以带静态方法）；
 * (2) Collection接口和数组的默认方法（默认方法,也使Java的新特性之一，后续介绍），把一个Collection对象转换成Stream
 * (3) 其他：
 * Random.ints()
 * BitSet.stream()
 * Pattern.splitAsStream(java.lang.CharSequence)
 * JarFile.stream()
 */
public class StreamCreate {

  /**
   * 【of】
   * <p>
   * of方法，其生成的Stream是有限长度的，Stream的长度为其内的元素个数。
   */
  public static void of() {
    Stream<Integer> s1 = Stream.of(1, 2, 3);
    Stream<String> s2 = Stream.of("A");
  }

  /**
   * 【generator】
   * <p>
   * generator方法，返回一个无限长度的Stream,其元素由Supplier接口的提供。
   * 在Supplier是一个函数接口，只封装了一个get()方法，其用来返回任何泛型的值，
   * 该结果在不同的时间内，返回的可能相同也可能不相同，没有特殊的要求。
   * 1.这种情形通常用于随机数、常量的 Stream，或者需要前后元素间维持着某种状态信息的 Stream。
   * 2.把 Supplier 实例传递给 Stream.generate() 生成的 Stream，默认是串行（相对 parallel 而言）但无序的（相对 ordered 而言）。
   */
  public static void generator() {
    /**
     * 以下三种形式达到的效果是一样的，只不过是下面的两个采用了Lambda表达式，简化了代码，其实际效果就是返回一个随机值。
     * 一般无限长度的Stream会与filter、limit等配合使用，否则Stream会无限制的执行下去
     */
    Stream<Double> a = Stream.generate(new Supplier<Double>() {
      @Override
      public Double get() {
        return java.lang.Math.random();
      }
    });

    Stream<Double> b = Stream.generate(() -> java.lang.Math.random());
    Stream<Double> c = Stream.generate(java.lang.Math::random);
  }

  /**
   * 【iterate】
   * <p>
   * iterate方法，其返回的也是一个无限长度的Stream，
   * 与generate方法不同的是，其是通过函数f迭代对给指定的元素种子而产生无限连续有序Stream，
   * 其中包含的元素可以认为是：seed，f(seed),f(f(seed))无限循环。
   */
  public static void iterate() {
    /**
     * 种子为1，也可认为该Stream的第一个元素，通过f函数来产生第二个元素。接着，第二个元素，作为产生第三个元素的种子，从而产生了第三个元素，以此类推下去。
     * 需要注意的是，该Stream是无限长度的，应该使用filter、limit等来截取Stream，否则会一直循环下去
     */
    Stream.iterate(1, item -> item + 1)
        .limit(10)
        .forEach(System.out::println);
  }

  /**
   * 【empty】
   * <p>
   * empty方法返回一个空的顺序Stream，该Stream里面不包含元素项。
   */
  public static void empty() {
    Stream.empty();
  }
}
