package com.bs.demo.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Product {

  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "MEDIUMINT(8) UNSIGNED")
  private int id;

  @Column(nullable = false)
  private String productName;

  @Column(nullable = false)
  private String productAddress;

  @Column(nullable = false)
  private int counting;

  @Column(nullable = false)
  private int totalMoney;

  @Column(nullable = false)
  private int nowMoney;

  @Column(nullable = false)
  private int month;

  @Column(nullable = false)
  private int yield;

  @Column
  private String type;

}
