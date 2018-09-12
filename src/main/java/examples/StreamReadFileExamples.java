package examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhaona
 * @create 2018/5/18 14:51
 */
public class StreamReadFileExamples {

  /**
   * 【Read File + Stream】
   */
  public static void example1() {
    String fileName = "c://lines.txt";
    try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
      stream.forEach(System.out::println);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 【Read File + Stream + Extra】
   */
  public static void example2() {
    String fileName = "c://lines.txt";
    List<String> list = new ArrayList<>();
    try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
      // 1. filter line 3
      list = stream.filter(line -> !line.startsWith("line3"))
          // 2. convert all content to upper case
          .map(String::toUpperCase)
          // 3. convert it into a List
          .collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
    }
    list.forEach(System.out::println);
  }

  /**
   * 【BufferedReader + Stream】
   * <p>
   * A new method lines() has been added since 1.8, it lets BufferedReader returns content as Stream.
   */
  public static void example3() {
    String fileName = "c://lines.txt";
    List<String> list = new ArrayList<>();
    try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
      // br returns as stream and convert it into a List
      list = br.lines().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
    }
    list.forEach(System.out::println);
  }

  /**
   * 【Classic BufferedReader And Scanner】
   */
  public static void example4() {
    String fileName = "c://lines.txt";
    try (Scanner scanner = new Scanner(new File(fileName))) {
      while (scanner.hasNext()) {
        System.out.println(scanner.nextLine());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
