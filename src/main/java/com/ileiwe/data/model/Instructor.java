package com.ileiwe.data.model;

import lombok.Data;
import org.hibernate.annotations.Entity;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String firstName;
    private String lastName;
    private Gender gender;
    private String specialization;
    @Column(length = 1000)
    private String bio;
    @OneToOne
    private LearningParty learningParty;
    @OneToMany
    private List<Course> courses;
}
