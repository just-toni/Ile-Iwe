package com.ileiwe.service.mail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MailGunServiceImpl {

    @Autowired
    @Qualifier("mailgun")
    EmailService emailService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void sendEmailWithMailGunTest() {
        //create Message
        Message mail = Message.builder()
                .from("tboydv1@gmail.com")
                .to("sage@mailpoof.com")
                .subject("Test Email")
                .body("This is the body" +
                        "of my test email").build();
        //Call send method
        MailResponse response = emailService.send(mail);
        //verify the mail response
        assertTrue(response.isSuccessful());
    }

}
