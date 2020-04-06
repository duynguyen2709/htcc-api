package htcc.log.service.service.mail;

import htcc.log.service.config.MailBuzConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@Log4j2
public class MailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendMail(String receiver, String subject, String contentHtml) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            message.setContent(contentHtml, "text/html");

            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setTo(receiver);
            helper.setSubject(subject);

            emailSender.send(message);

            log.info("Send Mail to receiver [{}] with subject [{}] succeed", receiver, subject);
        } catch (Exception e) {
            log.error("[sendMail] to receiver [{}] ex", receiver, e);
        }
    }
}
