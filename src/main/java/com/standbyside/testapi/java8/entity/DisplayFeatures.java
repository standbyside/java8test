package com.standbyside.testapi.java8.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Optional;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DisplayFeatures {

  private String size;
  private Optional<ScreenResolution> resolution;

}
