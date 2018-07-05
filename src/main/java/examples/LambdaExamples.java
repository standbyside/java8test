package examples;

import com.google.common.collect.Lists;
import entity.Person;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 静态方法引用：ClassName::methodName
 * 实例上的实例方法引用：instanceReference::methodName
 * 超类上的实例方法引用：super::methodName
 * 类型上的实例方法引用：ClassName::methodName
 * 构造方法引用：Class::new
 * 数组构造方法引用：TypeName[]::new
 *
 * @author zhaona
 * @create 2018/5/18 14:51
 */
public class LambdaExamples {

    public static void comparator() {
        List<Person> persons = Arrays.asList(
                new Person("mkyong", 30),
                new Person("jack", 20),
                new Person("lawrence", 40)
        );
        // ex1
        persons.sort((Person o1, Person o2)->o1.getAge()-o2.getAge());
        // ex2
        persons.sort((o1, o2)->o1.getName().compareTo(o2.getName()));
        // ex3
        Comparator<Person> nameComparator = (o1, o2)->o1.getName().compareTo(o2.getName());
        persons.sort(nameComparator.reversed());
        // ex4
        persons.sort(Comparator.comparing(Person::getName));
    }

    public static void mapForEach() {
        Map<String, Integer> items = new HashMap<>(16);
        items.put("A", 10);
        items.put("B", 20);
        items.put("C", 30);
        // ex1
        items.forEach((k, v) -> System.out.println("k : " + k + " v : " + v));
        // ex2
        items.forEach((k, v) -> {
            System.out.println("k : " + k + " v : " + v);
            if ("B".equals(k)) {
                System.out.println("Hello B");
            }
        });
    }

    public static void listForEach() {
        List<String> items = Lists.newArrayList("A", "B", "C");
        // ex1
        items.forEach(o -> System.out.println(o));
        // ex2
        items.forEach(o -> {
            if ("C".equals(o)) {
                System.out.println(o);
            }
        });
        // ex3
        items.forEach(System.out::println);
        // ex4
        items.stream()
                .filter(s -> s.contains("B"))
                .forEach(System.out::println);
    }
}
