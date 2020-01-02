package com.standbyside.testapi.java8.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Fruit {

  private String name;
  private int qty;
  private BigDecimal price;

}
