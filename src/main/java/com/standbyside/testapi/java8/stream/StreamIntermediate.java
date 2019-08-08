package com.standbyside.testapi.java8.stream;

import java.util.stream.Stream;

/**
 * Intermediate主要是用来对Stream做出相应转换及限制流，实际上是将源Stream转换为一个新的Stream，以达到需求效果。
 */
public class StreamIntermediate {

  /**
   * 【concat】
   * <p>
   * concat方法将两个Stream连接在一起，合成一个Stream。
   * 若两个输入的Stream都时排序的，则新Stream也是排序的；若输入的Stream中任何一个是并行的，则新的Stream也是并行的。
   * 若关闭新的Stream时，原两个输入的Stream都将执行关闭处理。
   */
  public static void concat() {
    Stream.concat(Stream.of(1, 2, 3), Stream.of(4, 5))
        .forEach(integer -> System.out.print(integer + "  "));
  }

  /**
   * 【distinct】
   * <p>
   * distinct方法以达到去除掉原Stream中重复的元素，生成的新Stream中没有没有重复的元素。
   */
  public static void distinct() {
    Stream.of(1, 2, 3, 1, 2, 3)
        .distinct()
        .forEach(System.out::println);
  }

  /**
   * 【filter】
   * <p>
   * filter方法对原Stream按照指定条件过滤，在新建的Stream中，只包含满足条件的元素，将不满足条件的元素过滤掉。
   * filter传入的Lambda表达式必须是Predicate实例，参数可以为任意类型，而其返回值必须是boolean类型。
   */
  public static void filter() {
    Stream.of(1, 2, 3, 4, 5)
        .filter(item -> item > 3)
        .forEach(System.out::println);
  }

  /**
   * 【map】
   * <p>
   * map方法将对于Stream中包含的元素使用给定的转换函数进行转换操作，新生成的Stream只包含转换生成的元素。
   * 为了提高处理效率，官方已封装好了，三种变形：mapToDouble，mapToInt，mapToLong。
   * 其实很好理解，如果想将原Stream中的数据类型，转换为double,int或者是long是可以调用相对应的方法。
   * map传入的Lambda表达式必须是Function实例，参数可以为任意类型，而其返回值也是任意类型，javac会根据实际情景自行推断。
   */
  public static void map() {
    Stream.of("a", "b", "hello")
        .map(item -> item.toUpperCase())
        .forEach(System.out::println);
  }

  /**
   * 【flatMap】
   * <p>
   * flatMap方法与map方法类似，都是将原Stream中的每一个元素通过转换函数转换，
   * 不同的是，该换转函数的对象是一个Stream，也不会再创建一个新的Stream，而是将原Stream的元素取代为转换的Stream。
   * 如果转换函数生产的Stream为null，应由空Stream取代。
   * flatMap有三个对于原始类型的变种方法，分别是：flatMapToInt，flatMapToLong和flatMapToDouble。
   * flatMap传入的Lambda表达式必须是Function实例，参数可以为任意类型，而其返回值类型必须是一个Stream。
   */
  public static void flatMap() {
    Stream.of(1, 2, 3)
        .flatMap(integer -> Stream.of(integer * 10))
        .forEach(System.out::println);
  }

  /**
   * 【peek】
   * <p>
   * peek方法生成一个包含原Stream的所有元素的新Stream，同时会提供一个消费函数（Consumer实例），
   * 新Stream每个元素被消费的时候都会执行给定的消费函数，并且消费函数优先执行
   */
  public static void peek() {
    Stream.of(1, 2, 3, 4, 5)
        .peek(integer -> System.out.println("accept:" + integer))
        .forEach(System.out::println);
  }

  /**
   * 【skip】
   * <p>
   * skip方法将过滤掉原Stream中的前N个元素，返回剩下的元素所组成的新Stream。
   * 如果原Stream的元素个数大于N，将返回原Stream的后（原Stream长度-N）个元素所组成的新Stream。
   * 如果原Stream的元素个数小于或等于N，将返回一个空Stream。
   */
  public static void skip() {
    Stream.of(1, 2, 3, 4, 5)
        .skip(2)
        .forEach(System.out::println);
  }

  /**
   * 【sorted】
   * <p>
   * sorted方法将对原Stream进行排序，返回一个有序列的新Stream。
   * sorterd有两种变体sorted()，sorted(Comparator)，
   * 前者将默认使用Object.equals(Object)进行排序，
   * 而后者接受一个自定义排序规则函数(Comparator)，可按照意愿排序。
   */
  public static void sorted() {
    Stream.of(5, 4, 3, 2, 1)
        .sorted()
        .forEach(System.out::println);

    Stream.of(1, 2, 3, 4, 5)
        .sorted()
        .forEach(System.out::println);
  }
}
