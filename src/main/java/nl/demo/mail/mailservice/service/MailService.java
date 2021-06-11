package nl.demo.mail.mailservice.service;

import lombok.extern.slf4j.Slf4j;
import nl.demo.mail.mailservice.model.Mail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class MailService {

    final JavaMailSender sender;

    public MailService(JavaMailSender sender) {
        this.sender = sender;
    }

    // Sequentie van versturen
        // check if body == txt && attach == null/empty TRUE
        // if simple TRUE -> enum mailType = SIMPLE
        // convert Mail -> SimpleMailMessage
        // if simple FALSE -> enum mailType = MIME
        // convert Mail -> MimeMailMessage
        // sendMessage(mail);
        // if (send OK) -> send OK to sender -> remove Mail
        // if (send Not OK) -> save Mail + addDate -> send Not OK to sender -> resend ...
        // if (send Not OK) -> send Not OK to sender -> remove Mail

    // at 24:00 -> remove all Mail in database

    public void sendMail(Mail mail) throws MessagingException {
        sendMessage(mail);
    }

    // TODO: corrigeer if(), zodat onderscheidt tussen txt & rtf/html
    public void sendMessage(Mail mail) throws MessagingException {
//        if (mail.getBody().getBodyType() == TXT &&
//            mail.getAttach().getFile().isEmpty()) {
            send(makeSimple(mail));
//        } else {
            send(makeMime(mail));
//        }
    }

    public SimpleMailMessage makeSimple(Mail mail) {
        return MailMapper.mailToSimple(mail);
    }

    public MimeMessage makeMime(Mail mail) throws MessagingException {
        return MailMapper.mailToMime(mail);
    }

    // TODO: maak generiek
    public void send(SimpleMailMessage message) {
        sender.send(message);
    }

    public void send(MimeMessage message) {
        sender.send(message);
    }

}