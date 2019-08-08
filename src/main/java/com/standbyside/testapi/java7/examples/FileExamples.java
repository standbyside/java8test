package com.standbyside.testapi.java7.examples;

import com.standbyside.testapi.common.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileExamples {

  private static final Path PATH_A = Paths.get(Constant.TEST_DIR + "/a.txt");
  private static final Path PATH_B = Paths.get(Constant.TEST_DIR + "/b.txt");

  /**
   * 读文件.
   */
  public void readFile() throws IOException {
    // 取文件内容
    byte[] data = Files.readAllBytes(PATH_A);
    String content = new String(data, StandardCharsets.UTF_8);
    System.out.println(content);

    // 按照行读取文件
    List<String> lines = Files.readAllLines(PATH_A);
    System.out.println(lines);
  }

  /**
   * 写文件.
   */
  public void writeFile() throws IOException {
    byte[] bytes = "Hello World!".getBytes();

    // 覆写文件
    Files.write(PATH_B, bytes);
    // 追加文件
    Files.write(PATH_B, bytes, StandardOpenOption.APPEND);
  }

  /**
   * 获取流对象.
   */
  public void getStreams() throws IOException {
    InputStream ins = Files.newInputStream(PATH_A);
    System.out.println(ins);

    OutputStream ops = Files.newOutputStream(PATH_A);
    System.out.println(ops);

    Reader reader = Files.newBufferedReader(PATH_A);
    System.out.println(reader);

    Writer writer = Files.newBufferedWriter(PATH_A);
    System.out.println(writer);
  }

  /**
   * 创建、移动、删除.
   */
  public void operateFiles() throws IOException {
    if (!Files.exists(PATH_A)) {
      // 创建文件
      Files.createFile(PATH_A);
      // 创建目录
      Files.createDirectory(PATH_A);
    }
    // 复制文件
    Files.copy(Files.newInputStream(PATH_A), PATH_B);
    // 移动文件
    Files.move(PATH_A, PATH_B);
    // 删除文件
    Files.delete(PATH_A);
  }
}
