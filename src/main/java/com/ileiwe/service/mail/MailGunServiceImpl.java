package com.ileiwe.service.mail;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("mailgun")
@Slf4j
public class MailGunServiceImpl implements EmailService {

    @Value("${MAILGUN_SANDBOX_DOMAIN}")
    private String DOMAIN_NAME;

    @Value("${MAILGUN_API_KEY}")
    private String API_KEY;

    @Override
    public MailResponse send(Message message) {
        try{
            log.info("Sending emails");
            HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN_NAME + "/messages")
                    .basicAuth("api", API_KEY)
                    .field("to", message.getTo())
                    .field("from", message.getFrom())
                    .field("subject", message.getSubject())
                    .field("body", message.getBody())
                    .asJson();
            log.info("Email response --> {} ", request.getStatus());

            return request.getStatus() == 200 ?
                    new MailResponse(true):
                    new MailResponse(false);

        }
        catch (UnirestException e){
            log.info("Exception occurred --> {} ", e.getMessage());
            return new MailResponse(false);
        }
    }
}
