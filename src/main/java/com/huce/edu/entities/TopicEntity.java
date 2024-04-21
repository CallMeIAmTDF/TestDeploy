package com.huce.edu.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Table(name = "TOPICS")
public class TopicEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "TID")
    private Integer tid;
    @Basic
    @Column(name = "TOPIC")
    private String topic;
    @Basic
    @Column(name = "LID")
    private Integer lid;
}
