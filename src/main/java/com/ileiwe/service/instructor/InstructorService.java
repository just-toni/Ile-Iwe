package com.ileiwe.service.instructor;

import com.ileiwe.data.model.Instructor;
import com.ileiwe.service.exception.UserAlreadyExistsException;

public interface InstructorService {

    Instructor save(InstructorPartyDto instructorDto) throws UserAlreadyExistsException;
}
