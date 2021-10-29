package com.ileiwe.data.repository;

import com.ileiwe.data.model.Authority;
import com.ileiwe.data.model.LearningParty;
import com.ileiwe.data.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
@Sql("/db/insert.sql")
//@Rollback(value = false)
class LearningPartyRepositoryTest {

    @Autowired
    LearningPartyRepository learningPartyRepository;

    @BeforeEach
    void setUp() {

    }

    @Transactional
    @Test
    @Rollback(value = false)
    void createLearningPartyTest(){
        LearningParty learningUser =
                new LearningParty("yomi@gmail.com",
                        "yomi123",
                        new Authority(Role.ROLE_STUDENT));
        log.info("Before saving --> {}" , learningUser);
        learningPartyRepository.save(learningUser);
        assertThat(learningUser.getId()).isNotNull();
        assertThat(learningUser.getEmail()).isEqualTo("yomi@gmail.com");
        assertThat(learningUser.getAuthorities().get(0).getAuthority())
                .isEqualTo(Role.ROLE_STUDENT);
        log.info("After saving --> {}" , learningUser);
    }

    @Test
    void createLearningPartyUniqueEmailsTest(){
        // create a learning party
        LearningParty learningUser = new LearningParty("daphne@gmail.com", "daphne123",
                        new Authority(Role.ROLE_STUDENT));
        // save to db
        learningPartyRepository.save(learningUser);
        assertThat(learningUser.getId()).isNotNull();
        assertThat(learningUser.getEmail()).isEqualTo("daphne@gmail.com");
        // create another learning party
        LearningParty learningUser2 = new LearningParty("daphne@gmail.com", "roy123",
                        new Authority(Role.ROLE_STUDENT));
        // save and catch exception
        assertThrows(DataIntegrityViolationException.class, () -> learningPartyRepository.save(learningUser2));
    }

    @Test
    void learningPartyWithNullValuesTest(){
        LearningParty learningUser = new LearningParty(null, null,
                new Authority(Role.ROLE_STUDENT));
        assertThrows(ConstraintViolationException.class, () -> learningPartyRepository.save(learningUser));
    }

    @Test
    void learningPartyWithEmptyStringTest(){
        LearningParty learningUser =  new LearningParty("", "",
                new Authority(Role.ROLE_STUDENT));
        assertThrows(ConstraintViolationException.class, () -> learningPartyRepository.save(learningUser));
    }

    @Test
    void learningPartyWithWhiteSpaceStringTest(){
        LearningParty learningUser =  new LearningParty(" ", " ",
                new Authority(Role.ROLE_STUDENT));
        assertThrows(ConstraintViolationException.class, () -> learningPartyRepository.save(learningUser));
    }

    @Test
    void findByUserNameTest(){
        LearningParty learningParty = learningPartyRepository.findByEmail("tomi@mail.com");
        assertThat(learningParty).isNotNull();
        assertThat(learningParty.getEmail()).isEqualTo("tomi@mail.com");

        log.info("Learning party object --> {}", learningParty);
    }

    @AfterEach
    void tearDown() {

    }
}