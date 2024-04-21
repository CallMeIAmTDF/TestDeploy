package com.huce.edu.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Table(name = "WORDS")
public class WordEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "WID")
    private Integer wid;
    @Basic
    @Column(name = "WORD")
    private String word;
    @Basic
    @Column(name = "PRONUN")
    private String pronun;
    @Basic
    @Column(name = "ENTYPE")
    private String entype;
    @Basic
    @Column(name = "VIETYPE")
    private String vietype;
    @Basic
    @Column(name = "VOICE")
    private String voice;
    @Basic
    @Column(name = "PHOTO")
    private String photo;
    @Basic
    @Column(name = "MEANING")
    private String meaning;
    @Basic
    @Column(name = "ENDESC")
    private String endesc;
    @Basic
    @Column(name = "VIEDESC")
    private String viedesc;
    @Basic
    @Column(name = "TID")
    private Integer tid;
}
