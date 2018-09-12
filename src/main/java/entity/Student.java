package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Student {

  private int no;
  private String name;
  private String sex;
  private float height;
  private Set<String> book;

  public Student(int no, String name, String sex, float height) {
    this.no = no;
    this.name = name;
    this.sex = sex;
    this.height = height;
  }


  public void addBook(String book) {
    if (this.book == null) {
      this.book = new HashSet<>();
    }
    this.book.add(book);
  }
}
