package com.ileiwe.data.repository;

import com.ileiwe.data.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/insert.sql"})
class InstructorRepositoryTest {

    @Autowired
    InstructorRepository instructorRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void saveInstructorAsLearningParty(){
        //create a learning party
        LearningParty user =
                new LearningParty("trainer@email.com", "trainer123", new Authority(Role.ROLE_INSTRUCTOR));
        //create instructor
        Instructor instructor = Instructor.builder().firstName("Nico").lastName("Mbaga").learningParty(user).build();

        log.info("Instructor before saving -> {}", instructor);
        instructorRepository.save(instructor);
        //save instructor

        assertThat(instructor.getId()).isNotNull();
        assertThat(instructor.getLearningParty().getId()).isNotNull();
        log.info("Instructor after saving -> {}", instructor);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @Sql(scripts = "/db/insert.sql")
    void canUpdateInstructorInformation(){
        LearningParty user = new LearningParty("student@know.gmail.com", "password", new Authority(Role.ROLE_STUDENT));
        Instructor instructor = new Instructor("John", "Alao", user, new Course("Introduction to programming in Assembly Language"));
        instructorRepository.save(instructor);


        Course course = new Course("Introduction to Machine Learning");
        Instructor fromDb = instructorRepository.findById(instructor.getId()).orElse(null);
        assertThat(fromDb).isNotNull();
        fromDb.setFirstName("John");

        fromDb.setLastName("Lamade");
        fromDb.setBio("John Lamade is a Doctor of Engineering at the University of Benin");
        fromDb.setGender(Gender.MALE);
        fromDb.getCourses().add(course);

        log.info("Before updating instructor --> {}", fromDb);
        instructorRepository.save(fromDb);
        log.info("After updating instructor --> {}", fromDb);

        assertThat(fromDb.getFirstName()).isEqualTo("John");
        assertThat(fromDb.getLastName()).isEqualTo("Lamade");
        assertThat(fromDb.getCourses()).hasSize(2);
        assertThat(fromDb.getBio()).isEqualTo("John Lamade is a Doctor of Engineering at the University of Benin");
        assertThat(fromDb.getGender()).isEqualTo(Gender.MALE);
    }

    @Test
    void test_ThatInstructorFirstNameAndLastNameCannotBeNull(){
        Instructor instructor = new Instructor(null, null, null, null);
        assertThatThrownBy(()->instructorRepository.save(instructor)).isInstanceOf(ConstraintViolationException.class);
    }
    @Test
    void testThatCannotCreateInstructorsWithEmptyStringValues(){
        LearningParty user = new LearningParty("student@know.gmail.com", "password", new Authority(Role.ROLE_STUDENT));
        Instructor instructor = Instructor.builder().firstName(" ").lastName(" ").learningParty(user).courses(List.of(new Course("Machine Language 101"))).build();
        log.info("Before saving --> {}", instructor);
        assertThatThrownBy(()-> instructorRepository.save(instructor)).isInstanceOf(ConstraintViolationException.class);
    }

    @AfterEach
    void tearDown() {

    }
}