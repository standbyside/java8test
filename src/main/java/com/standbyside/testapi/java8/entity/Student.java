package com.standbyside.testapi.java8.entity;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Student {

  private int no;
  private String name;
  private String sex;
  private float height;
  private Set<String> book;

  public void addBook(String book) {
    if (this.book == null) {
      this.book = new HashSet<>();
    }
    this.book.add(book);
  }
}
