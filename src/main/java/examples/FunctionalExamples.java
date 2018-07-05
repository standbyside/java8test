package examples;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author zhaona
 * @create 2018/7/5 上午11:40
 */
public class FunctionalExamples {

  public static void main(String[] args) {
    function();
    predicate();
    consumer();
    supplier();
    functionalParam();
  }

  /**
   * lambda的4种内置函数之一，有入参，有返回值.
   */
  public static void function() {
    Function<String, String> function = v -> v + " in java";
    String result = function.apply("function test: think");
    System.out.println(result);
  }

  /**
   * lambda的4种内置函数之一，有入参，有boolean类型返回值.
   */
  public static void predicate() {
    Predicate<String> predicate = p1 -> p1.startsWith("a");
    boolean result = predicate.test("apple");
    System.out.println("Predicate test: "+result);
  }

  /**
   * lambda的4种内置函数之一，有入参，无返回值.
   */
  public static void consumer() {
    Consumer<String> consumer = c1 -> System.out.println("Consumer test: " + c1);
    consumer.accept("how are you!");
  }

  /**
   * lambda的4种内置函数之一，无入参，无返回值，主要是工厂模式的一种实现.
   */
  public static void supplier() {
    Supplier<String> supplier = () -> Objects.toString(System.currentTimeMillis());
    String result = supplier.get();
    System.out.println("Supplier test: " + result);
  }

  /**
   * 将内置函数作为参数传入方法.
   */
  public static void functionalParam() {
    CustomizedFunctional<String> customized = val -> System.out.println(val);
    functionalParamMethod(val -> val.startsWith("h"), customized, "hello");
  }

  public static void functionalParamMethod(Predicate<String> predicate,
                                           CustomizedFunctional<String> customized,
                                           String a) {
    System.out.println("method processing start");
    if (predicate.test(a)) {
      customized.apply(a);
    }
    System.out.println("method processing start");
  }

  /**
   * 自定义函数接口.
   */
  @FunctionalInterface
  private interface CustomizedFunctional<T> {
    
    void apply(T a);

    default void test1() {
      System.out.println("MyInterface test1");
    }

    static void test2() {
      System.out.println("MyInterface test2");
    }
  }
}
