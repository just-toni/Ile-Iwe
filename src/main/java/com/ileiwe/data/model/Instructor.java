package com.ileiwe.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull    @NotBlank
    @Column(nullable = false)
    private String firstName;
    @NotNull    @NotBlank
    @Column(nullable = false)
    private String lastName;
    private Gender gender;
    private String specialization;
    @Column(length = 1000)
    private String bio;
    @OneToOne (cascade = CascadeType.PERSIST)
    private LearningParty learningParty;
    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Course> courses;

    public Instructor(String firstName, String lastName, LearningParty learningParty, Course course) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.learningParty = learningParty;
        addCourse(course);
    }

    private void addCourse(Course course) {
        if (this.courses == null){
            this.courses = new ArrayList<>();
        }
        this.courses.add(course);
    }

}
