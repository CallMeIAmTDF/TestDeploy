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
@Table(name = "TESTHISTORY")
public class TestHistoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "THID")
    private Integer thid;
    @Basic
    @Column(name = "UID")
    private Integer uid;
    @Basic
    @Column(name = "NUMQUES")
    private Integer numques;
    @Basic
    @Column(name = "NUMCORRECTQUES")
    private Integer numcorrectques;
    @Basic
    @Column(name = "TDATE")
    private Date tdate;
}
