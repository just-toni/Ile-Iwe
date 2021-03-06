package com.ileiwe.data.model;

import lombok.Data;
import org.hibernate.annotations.Entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToOne
    private LearningParty learningParty;
    @ManyToMany
    private List<Course> courses;

}
