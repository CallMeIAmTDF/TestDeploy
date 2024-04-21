package com.huce.edu.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Table(name = "USERS")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "UID")
    private Integer uid;
    @Basic
    @Column(name = "EMAIL")
    private String email;
    @Basic
    @Column(name = "NAME")
    private String name;
    @Basic
    @Column(name = "PASSWORD")
    private String password;
    @Basic
    @Column(name = "COIN")
    private Double coin;
    @Basic
    @Column(name = "STATUS")
    private Integer status;
}
