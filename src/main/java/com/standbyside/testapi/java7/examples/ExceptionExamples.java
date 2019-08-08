package com.standbyside.testapi.java7.examples;

import com.standbyside.testapi.common.Constant;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ExceptionExamples {

  /**
   * try-with-resources.
   */
  public void tryWithReources() {
    try (InputStream is  = new FileInputStream(Constant.TEST_DIR + "/a.txt");
         OutputStream os = new FileOutputStream(Constant.TEST_DIR + "/b.txt")
    ) {
      char charStr = (char) is.read();
      os.write(charStr);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 捕获多个Exception.
   */
  public void catchMultipleExceptions() {
    try {
      Thread.sleep(20000);
      new FileInputStream(Constant.TEST_DIR + "/a.txt");
    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 处理反射异常.
   */
  public void reflectiveOperationException() {
    try {
      Class<?> clazz = Class.forName("com.standbyside.testapi.java7.entity.User");
      clazz.getMethods()[0].invoke(new Object());
    } catch (ReflectiveOperationException e){
      e.printStackTrace();
    }
  }
}
