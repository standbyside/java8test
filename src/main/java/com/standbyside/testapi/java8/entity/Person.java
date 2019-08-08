package com.standbyside.testapi.java8.entity;

import com.standbyside.testapi.java8.anno.ExcelKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Person {

  @ExcelKey(name = "nickname")
  @ExcelKey(name = "name")
  private String name;
  private int age;
  private BigDecimal salary;


  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }
}
