package com.huce.edu.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Table(name = "PRODUCT")
public class ProductEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PID")
    private Integer pid;
    @Basic
    @Column(name = "NAME")
    private String name;
    @Basic
    @Column(name = "PRICE")
    private Double price;
    @Basic
    @Column(name = "IMAGE")
    private String image;
    @Basic
    @Column(name = "REMAIN")
    private Integer remain;
    @Basic
    @Column(name = "ISDELETED")
    private Boolean isDeleted;

}
