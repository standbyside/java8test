package com.standbyside.testapi.java8.stream;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhaona
 * @create 2018/5/18 14:51
 * <p>
 * <p>
 * 实际开发过程中，List又是我们经常用到的数据结构，但是有时候我们也希望Stream能够转换生成其他的值，比如Map或者set，甚至希望定制生成想要的数据结构。
 * collect也就是收集器，是Stream一种通用的、从流生成复杂值的结构。只要将它传给collect方法，也就是所谓的转换方法，其就会生成想要的数据结构。
 * (1) <R, A> R collect(Collector<? super T, A, R> collector);
 * (2) <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner);
 * <p>
 * 这里不得不提下，Collectors这个工具库，在该库中封装了相应的转换方法。当然，Collectors工具库仅仅封装了常用的一些情景，如果有特殊需求，那就要自定义了。
 * <p>
 * <p>
 * <p>
 * 【辅助接口】
 * (1) Supplier<T>接口是一个函数接口，该接口声明了一个get方法，主要用来创建返回一个指定数据类型的对象
 * (2) BiConsumer<T, U>接口是一个函数接口，该接口声明了accept方法，并无返回值，该函数接口主要用来声明一些预期操作。
 * 同时，该接口定义了一个默认方法andThen，该方法接受一个BiConsumer，并返回一个组合的BiConsumer，其会按照顺序执行操作。
 * 如果执行任一操作抛出异常，则将其传递给组合操作的调用者。 如果执行此操作抛出异常，将不执行后操作(after)。
 * (3) BinaryOperator接口继承于BiFunction接口，该接口指定了apply方法执行的参数类型及返回值类型均为T。
 * (4) Funtion是一个函数接口，其内定义了一个转换函数，将T转换为R。比如Stream中的map方法便是接受该函数参数，将T转换为R。
 * <p>
 * <p>
 * <p>
 * 【Collector】
 * Collector是Stream的可变减少操作接口，可变减少操作包括：将元素累积到集合中，使用StringBuilder连接字符串;计算元素相关的统计信息，例如sum，min，max或average等。Collectors(类收集器)提供了许多常见的可变减少操作的实现。
 * <p>
 * Collector<T, A, R>接受三个泛型参数，对可变减少操作的数据类型作相应限制：
 * (1) T：输入元素类型
 * (2) A：缩减操作的可变累积类型（通常隐藏为实现细节）
 * (3) R：可变减少操作的结果类型
 * Collector接口声明了4个函数，这四个函数一起协调执行以将元素目累积到可变结果容器中，并且可以选择地对结果进行最终的变换.
 * (1) Supplier<A> supplier(): 创建新的结果结
 * (2) BiConsumer<A, T> accumulator()：将元素添加到结果容器
 * (3) BinaryOperator<A> combiner()：将两个结果容器合并为一个结果容器
 * (4) Function<A, R> finisher()：对结果容器作相应的变换
 * <p>
 * 在Collector接口的characteristics方法内，可以对Collector声明相关约束
 * (1) Set<Characteristics> characteristics()
 * <p>
 * 而Characteristics是Collector内的一个枚举类，声明了CONCURRENT、UNORDERED、IDENTITY_FINISH等三个属性，用来约束Collector的属性。
 * (1) CONCURRENT：表示此收集器支持并发，意味着允许在多个线程中，累加器可以调用结果容器
 * (2) UNORDERED：表示收集器并不按照Stream中的元素输入顺序执行
 * (3) IDENTITY_FINISH：表示finisher实现的是识别功能，可忽略。
 * 注：如果一个容器仅声明CONCURRENT属性，而不是UNORDERED属性，那么该容器仅仅支持无序的Stream在多线程中执行。
 * <p>
 * <p>
 * <p>
 * 【身份约束和相关性约束】
 * Stream可以顺序执行，或者并发执行，或者顺序并发执行，为了保证Stream可以产生相同的结果，收集器函数必须满足身份约束和相关项约束。
 * 身份约束说，对于任何部分累积的结果，将其与空结果容器组合必须产生等效结果。也就是说，对于作为任何系列的累加器和组合器调用的结果的部分累加结果a，a必须等于combiner.apply(a, supplier.get())。
 * 相关性约束说，分裂计算必须产生等效的结果。也就是说，对于任何输入元素t1和t2，以下计算中的结果r1和r2必须是等效的：
 * A a1 = supplier.get();
 * accumulator.accept(a1, t1);
 * accumulator.accept(a1, t2);
 * R r1 = finisher.apply(a1);
 * <p>
 * A a2 = supplier.get();
 * accumulator.accept(a2, t1);
 * A a3 = supplier.get();
 * accumulator.accept(a3, t2);
 * R r2 = finisher.apply(combiner.apply(a2, a3));
 * <p>
 * <p>
 * <p>
 * 【基于Collector工具库】
 * 在Collector工具库中，声明了许多常用的收集器,以供我们快速创建一个收集器。前面我们已经了解到，收集器函数必须满足身份约束和相关项约束。
 * 而基于Collector实现简化的库(如Stream.collect(Collector))创建收集器时，必须遵守以下约束：
 * (1) 第一个参数传递给accumulator()函数，两个参数都传递给combiner()函数，传递给finisher()函数的参数必须是上一次调用supplier()，accumulator()或combiner()函数的结果。
 * (2) 实现不应该对任何accumulator()，combiner()或finisher()函数的结果做任何事情，除非收集器将返回的结果返回给调用者
 * (3) 如果结果传递到combiner()或finisher()函数，而且返回对象与传入的不相同，则不会再将对象传递给accumulator()函数调用。
 * (4) 一旦结果传递到combiner()或finisher()函数，它就不会再次传递到accumulator()函数。
 * (5) 对于串行收集器，supplier()，accumulator()或combiner()函数返回的任何结果必须是限制串行的。
 * 这使得收集器可以并行进行，而收集器不需要执行任何额外的同步。
 * reduce操作实现必须管理Stream的元素被正确区别并分别处理，并且仅在累积完成之后，对累加器中的数据合并。
 * (6) 对于并发收集器，实现可以自由地(但不是必须)同时实现reduce操作。
 * accumulator()可以在多个线程同时调用，而不是在累积期间保持结果的独立性。
 * 仅当收集器具有Collector.Characteristics.UNORDERED特性或者原始数据是无序的时才应用并发还原。
 */
public class StreamCollect {


  /**
   * 【toList】
   */
  public static void toList() {
    List<Integer> collectList = Stream.of(1, 2, 3, 4)
        .collect(Collectors.toList());
    System.out.println("collectList: " + collectList);
  }

  /**
   * 【toSet】
   */
  public static void toSet() {
    Set<Integer> collectSet = Stream.of(1, 2, 3, 4)
        .collect(Collectors.toSet());
    System.out.println("collectSet: " + collectSet);
  }

  /**
   * 【toCollection】
   * <p>
   * 调用toList或者toSet方法时，不需要指定具体的类型，Stream类库会自动推断并生成合适的类型。
   * 有时候我们对转换生成的集合有特定要求，比如希望生成一个TreeSet，而不是由Stream类库自动指定的一种类型。此时使用toCollection，它接受一个函数作为参数，来创建集合。
   * 看Collectors的源码，因为其接受的函数参数必须继承于Collection，也就是Collection并不能转换所有的继承类，比如不能通过toCollection转换成Map
   */
  public static void toCollection() {

  }

  /**
   * 【toMap】
   * <p>
   * toMap(Function<? super T, ? extends K> keyMapper,Function<? super T, ? extends U> valueMapper)
   * keyMapper: 该Funtion用来生成Key
   * valueMapper：该Funtion用来生成value
   * 若Stream中有重复的值，导致Map中key重复，在运行时会报异常java.lang.IllegalStateException: Duplicate key
   */
  public static void toMap() {

  }

  /**
   * 【转成值】
   * <p>
   * 使用collect可以将Stream转换成值。maxBy和minBy允许用户按照某个特定的顺序生成一个值
   * <p>
   * (1) averagingDouble:求平均值，Stream的元素类型为double
   * (2) averagingInt:求平均值，Stream的元素类型为int
   * (3) averagingLong:求平均值，Stream的元素类型为long
   * (4) counting:Stream的元素个数
   * (5) maxBy:在指定条件下的，Stream的最大元素
   * (6) minBy:在指定条件下的，Stream的最小元素
   * (7) reducing: reduce操作
   * (8) summarizingDouble:统计Stream的数据(double)状态，其中包括count，min，max，sum和平均。
   * (9) summarizingInt:统计Stream的数据(int)状态，其中包括count，min，max，sum和平均。
   * (10) summarizingLong:统计Stream的数据(long)状态，其中包括count，min，max，sum和平均。
   * (11) summingDouble:求和，Stream的元素类型为double
   * (12) summingInt:求和，Stream的元素类型为int
   * (13) summingLong:求和，Stream的元素类型为long
   */
  public static void toValue() {
    Optional<Integer> optional = Stream.of(1, 2, 3, 4)
        .collect(Collectors.maxBy(Comparator.comparingInt(o -> o)));
    System.out.println("optional:" + optional.get());
  }

  /**
   * 【partitioningBy】
   * <p>
   * 它接受一个流，并将其分成两部分：使用Predicate对象，指定条件并判断一个元素应该属于哪个部分，并根据布尔值返回一个Map到列表。
   * key为true所对应的List中的元素，满足Predicate对象中指定的条件；
   * key为false所对应的List中的元素，不满足Predicate对象中指定的条件
   */
  public static void partitioningBy() {
    Map<Boolean, List<Integer>> collectParti = Stream.of(1, 2, 3, 4)
        .collect(Collectors.partitioningBy(it -> it % 2 == 0));
    System.out.println("collectParti : " + collectParti);
  }

  /**
   * 【groupingBy】
   * <p>
   * 与将数据分成true和false两部分不同，可以使用任意值对数据分组。
   * 调用Stream的collect方法，传入一个收集器,groupingBy接受一个分类函数，用来对数据分组，就像partitioningBy一样，接受一个Predicate对象将数据分成true和false两部分。
   * 我们使用的分类器是一个Function对象，和map操作用到的一样
   */
  public static void groupingBy() {
    Map<Boolean, List<Integer>> collectGroup = Stream.of(1, 2, 3, 4)
        .collect(Collectors.groupingBy(it -> it > 3));
    System.out.println("collectGroup : " + collectGroup);
  }

  /**
   * 【joining】
   * <p>
   * 收集Stream中的值，该方法可以方便地将Stream得到一个字符串。joining函数接受三个参数，分别表示允(用以分隔元素)、前缀和后缀
   */
  public static void joining() {
    String strJoin = Stream.of("1", "2", "3", "4")
        .collect(Collectors.joining(",", "[", "]"));
    System.out.println("strJoin: " + strJoin);
  }

  /**
   * 【组合Collector】
   * <p>
   * 在partitioningBy方法中，我们不仅传递了条件函数，同时传入了第二个收集器，用以收集最终结果的一个子集，这些收集器叫作下游收集器。
   * 收集器是生成最终结果的一剂配方，下游收集器则是生成部分结果的配方，主收集器中会用到下游收集器。这种组合使用收集器的方式， 使得它们在 Stream 类库中的作用更加强大。
   * 那些为基本类型特殊定制的函数，如averagingInt、summarizingLong等，事实上和调用特殊Stream上的方法是等价的，加上它们是为了将它们当作下游收集器来使用的
   */
  public static void collectGroup() {
    // 分割数据块
    Map<Boolean, List<Integer>> collectParti = Stream.of(1, 2, 3, 4)
        .collect(Collectors.partitioningBy(it -> it % 2 == 0));

    Map<Boolean, Integer> mapSize = new HashMap<>(16);
    collectParti.entrySet()
        .forEach(entry -> mapSize.put(entry.getKey(), entry.getValue().size()));

    System.out.println("mapSize : " + mapSize);
    // 得到分组后列表的个数-----------------------------------------------------------
    Map<Boolean, Long> partiCount = Stream.of(1, 2, 3, 4)
        .collect(
            Collectors.partitioningBy(
                it -> it.intValue() % 2 == 0,
                Collectors.counting()
            )
        );
    System.out.println("partiCount: " + partiCount);
  }
}
