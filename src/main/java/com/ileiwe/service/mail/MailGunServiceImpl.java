package com.ileiwe.service.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailGunServiceImpl implements EmailService {

    private String DOMAIN_NAME;

    @Override
    public MailResponse send(Message message) {
        try{
            log.info("Sending emails");
            HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" +DOMAIN_NAME + "/messages")
        }
    }
}
