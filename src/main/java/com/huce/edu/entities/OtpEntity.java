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
@Table(name = "OTP")
public class OtpEntity {
    @Id
    @Column(name = "UID")
    private Integer uid;
    @Basic
    @Column(name = "EMAIL")
    private String email;
    @Basic
    @Column(name = "CODE")
    private String code;
    @Basic
    @Column(name = "FAILATTEMPTS")
    private Integer failattempts;
    @Basic
    @Column(name = "EXP")
    private Date exp;
}
