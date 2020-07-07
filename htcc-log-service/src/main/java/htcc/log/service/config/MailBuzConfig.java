package htcc.log.service.config;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class MailBuzConfig {

    public String mailCreateNewUserTemplate = "";
    public String mailResetPasswordTemplate = "";
    public String mailCreateOrderTemplate = "";

    @Value("${mail.buz.subject.createNewUserMail}")
    public String mailCreateNewUserSubject;

    @Value("${mail.buz.subject.resetPasswordMail}")
    public String mailResetPasswordSubject;

    @Value("${mail.buz.subject.createOrderMail}")
    public String mailCreateOrderSubject;

    @PostConstruct
    public void loadMailContent() throws IOException {
        // new user mail
        Resource resource = new ClassPathResource("new-user-mail.html");

        InputStream inputStream = resource.getInputStream();
        mailCreateNewUserTemplate = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());

        // reset password mail
        Resource resource2 = new ClassPathResource("reset-password-mail.html");

        InputStream inputStream2 = resource2.getInputStream();
        mailResetPasswordTemplate = IOUtils.toString(inputStream2, StandardCharsets.UTF_8.name());

        // create order mail
        Resource resource3 = new ClassPathResource("create-order-mail.html");

        InputStream inputStream3 = resource3.getInputStream();
        mailCreateOrderTemplate = IOUtils.toString(inputStream3, StandardCharsets.UTF_8.name());
    }
}
