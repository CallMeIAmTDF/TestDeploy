package com.huce.edu.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Table(name = "KEYTOKEN")
public class KeytokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Basic
    @Column(name = "UID")
    private Integer uid;
    @Basic
    @Column(name = "AID")
    private Integer aid;
    @Basic
    @Column(name = "PRIVATEKEY")
    private String privatekey;
    @Basic
    @Column(name = "PUBLICKEY")
    private String publickey;
    @Basic
    @Column(name = "REFRESHTOKEN")
    private String refreshtoken;
}
