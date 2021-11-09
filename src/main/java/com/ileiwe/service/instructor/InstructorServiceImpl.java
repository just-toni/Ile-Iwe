package com.ileiwe.service.instructor;

import com.ileiwe.data.model.Authority;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.model.LearningParty;
import com.ileiwe.data.model.Role;
import com.ileiwe.data.repository.InstructorRepository;
import com.ileiwe.data.repository.LearningPartyRepository;
import com.ileiwe.service.event.OnRegistrationCompleteEvent;
import com.ileiwe.service.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImpl implements InstructorService{

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    LearningPartyRepository learningPartyRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public Instructor save(InstructorPartyDto instructorDto) throws UserAlreadyExistsException {

        if(instructorDto == null){
            throw new IllegalArgumentException("Instructor cannot be null");
        }

        if(learningPartyRepository.findByEmail(instructorDto.getEmail())
                == null){

            LearningParty learningParty
                    = new LearningParty(instructorDto.getEmail()
                    ,passwordEncoder.encode(instructorDto.getPassword())
                    , new Authority(Role.ROLE_INSTRUCTOR));

            Instructor instructor = Instructor.builder()
                    .lastName(instructorDto.getLastname())
                    .firstName(instructorDto.getFirstname())
                    .learningParty(learningParty).build();

            eventPublisher.publishEvent
                    (new OnRegistrationCompleteEvent(learningParty));

            return instructorRepository.save(instructor);

        }else{
            throw new UserAlreadyExistsException(
                    "user with email "+instructorDto.getEmail()
                            + " already exists");
        }
    }

}
