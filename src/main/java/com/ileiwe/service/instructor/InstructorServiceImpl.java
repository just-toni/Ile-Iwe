package com.ileiwe.service.instructor;

import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class InstructorServiceImpl implements InstructorService{

    @Autowired
    InstructorRepository instructorRepository;

    @Override
    public Instructor save(Instructor instructor) {
        if (instructor == null){
            throw new IllegalArgumentException("Instruction can't be null");
        }
        return instructorRepository.save(instructor);
    }
}
