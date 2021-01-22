package com.bs.demo.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name="users", schema="public")
public class User {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "MEDIUMINT(8) UNSIGNED")
    private int id;

    @Column(length=50, nullable = false)
    private String userName;

    @Column(length=10, unique=true, nullable = false, updatable = false)
    private String userId;

    @Column(length=50, nullable = false, unique=true)
    private String userEmail;

    @Column(nullable = false)
    private String userPassword;

    @Column
    private String userRole;

    @Column(nullable = false, insertable = false, updatable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Timestamp createDate;

}
