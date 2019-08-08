package com.standbyside.testapi.java8.examples.faker;

import com.github.javafaker.Faker;

import java.util.Locale;

public class JavaFakerExamples {

  public static void main(String[] args) {
    Faker faker = new Faker(new Locale("zh-CN"));
    System.out.println(faker.book().title());
  }
}
