package com.huce.edu.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Table(name = "ORDERS")
public class OrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "OID")
    private Integer oid;
    @Basic
    @Column(name = "UID")
    private Integer uid;
    @Basic
    @Column(name = "DATE")
    private Date date;
    @Basic
    @Column(name = "ADDRESS")
    private String address;
    @Basic
    @Column(name = "PID")
    private Integer pid;
    @Basic
    @Column(name = "PRICE")
    private Double price;
    @Basic
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Basic
    @Column(name = "PHONE")
    private String phone;
}
