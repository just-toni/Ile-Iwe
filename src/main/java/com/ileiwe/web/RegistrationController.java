package com.ileiwe.web;

import com.ileiwe.service.exception.UserAlreadyExistsException;
import com.ileiwe.service.instructor.InstructorPartyDto;
import com.ileiwe.service.instructor.InstructorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api")
public class RegistrationController {

    @Autowired
    InstructorServiceImpl instructorService;

    @PostMapping("/instructor")
    public ResponseEntity<?>
    registerAsInstructor(@RequestBody
                                 InstructorPartyDto
                                 instructorPartyDto) {
        log.info("instructor object --> {}", instructorPartyDto);
        try {
            return
                    ResponseEntity.ok()
                            .body(instructorService.save(instructorPartyDto));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }

    }
}
