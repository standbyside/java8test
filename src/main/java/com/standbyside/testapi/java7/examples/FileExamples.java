package com.standbyside.testapi.java7.examples;

import com.standbyside.testapi.common.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileStore;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class FileExamples {

  private static final Path PATH_ROOT = Paths.get(Constant.TEST_DIR);
  private static final Path PATH_A = Paths.get(Constant.TEST_DIR + "/a.txt");
  private static final Path PATH_B = Paths.get(Constant.TEST_DIR + "/b.txt");

  /**
   * 读文件.
   */
  public static void readFile() throws IOException {
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
  public static void writeFile() throws IOException {
    byte[] bytes = "Hello World!".getBytes();

    // 覆写文件
    Files.write(PATH_B, bytes);
    // 追加文件
    Files.write(PATH_B, bytes, StandardOpenOption.APPEND);
  }

  /**
   * 获取流对象.
   */
  public static void getStreams() throws IOException {
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
  public static void operateFiles() throws IOException {
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

  public static void fileProperties() throws IOException {
    // 是否是隐藏文件
    System.out.println(Files.isHidden(PATH_A));
    // 文件大小
    System.out.println(Files.size(PATH_A));
    // 磁盘空间
    FileStore fileStore = Files.getFileStore(PATH_A);
    System.out.println(fileStore.getTotalSpace());
    System.out.println(fileStore.getUsableSpace());
  }

  public static void path() {

    System.out.println(PATH_A.getNameCount());
    System.out.println(PATH_A.getRoot());

    // 绝对路径
    Path absolutePath = PATH_A.toAbsolutePath();
    System.out.println(absolutePath);
    System.out.println(absolutePath.getRoot());
    System.out.println(absolutePath.getNameCount());

    // 多个连接路径
    Path path = Paths.get("/", "Users", "data");
    System.out.println(path);
  }

  /**
   * Files工具类提供了遍历文件和目录的方法：
   * walkFileTree(): 遍历文件和子目录， 可以设置maxDepth深度。
   * 需要 FileVisitor参数，代表一个文件访问器。
   * <p>
   * CONTINUE： 继续
   * SKIP_SIBLINGS: 继续，但不放问兄弟文件或目录
   * SKIP_SUBTREE: 继续，但不放问子目录树
   * TERMINATE: 终止
   */
  public static void fileVisitor() throws IOException {
    Files.walkFileTree(Paths.get("."), new MyFileVisitor());
  }

  public static void watchService() throws IOException, InterruptedException {
    Path path = PATH_ROOT;
    WatchService watchService = path.getFileSystem().newWatchService();
    path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

    WatchKey watchKey;
    while (true) {
      watchKey = watchService.poll(5, TimeUnit.SECONDS);
      if (watchKey != null) {
        watchKey.pollEvents().stream().forEach(event -> System.out.println(event.context()));
        watchKey.reset();
      }
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    watchService();
  }

  private static class MyFileVisitor extends SimpleFileVisitor<Path> {
    // 访问子目录前触发
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
      return super.preVisitDirectory(dir, attrs);
    }

    // 访问文件时触发
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
      return super.visitFile(file, attrs);
    }

    // 访问文件失败时触发
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
      return super.visitFileFailed(file, exc);
    }

    // 访问子目录后触发
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
      return super.postVisitDirectory(dir, exc);
    }
  }
}
