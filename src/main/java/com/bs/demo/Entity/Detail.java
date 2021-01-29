package com.bs.demo.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Detail {
  
  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "MEDIUMINT(8) UNSIGNED")
  private int id;

  @Column
  private String projectName;

  @Column
  private String type;

  @Column
  private String kinfOf;

  @Column
  private String strategy;

  @Column
  private String safety;

  @Column
  private String period;

}
