package nl.demo.mail.mailservice.trial;

import lombok.extern.slf4j.Slf4j;
import nl.demo.mail.mailservice.service.DataService;
import nl.demo.mail.mailservice.model.Body;
import nl.demo.mail.mailservice.model.Header;
import nl.demo.mail.mailservice.model.Mail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

import static nl.demo.mail.mailservice.trial.Input.*;
import static nl.demo.mail.mailservice.service.MailMapper.*;
import static nl.demo.mail.mailservice.trial.Output.*;

@Slf4j
@SpringBootTest
class Send {

    // TODO: waarom in MailController JavaMailSender via constructor en hier via field?

    @Autowired
    DataService service;

//    SendMailTest(JavaMailSender sender, DataService service) {
//        this.sender = sender;
//        this.service = service;
//    }

    @Test
    void sendSimple() throws IOException {
        Mail mail = new Mail();
        log.info("Mail aangemaakt");

        Header header = new Header();
        header.setFrom(from);
        header.setReplyTo(replyTo);
        header.setTo(toOne);
        header.setSubject(new Object() {
        }.getClass().getEnclosingMethod().getName());
        log.info("Header aangemaakt");

        Body body = new Body();
        body.setText(text);
        log.info("Body aangemaakt");

        mail.setHeader(header);
        log.info("Header toegevoegd aan Mail");
        mail.setBody(body);
        log.info("Body toegevoegd aan Mail");

        sender.send(mailToSimple(mail));
        log.info("Mail verstuurd als SimpleMailMessage");
        saveJson(mail);
        log.info("Mail opgeslagen als Json");
    }

    @Test
    void sendSimpleToMany() throws IOException {
        Mail mail = new Mail();
        log.info("Mail aangemaakt");

        Header header = new Header();
        header.setFrom(from);
        header.setReplyTo(replyTo);
        header.setTo(toTwo);
        header.setCc(toTwo);
        header.setBcc(toTwo);
        header.setSubject(new Object() {
        }.getClass().getEnclosingMethod().getName());
        log.info("Header aangemaakt");

        Body body = new Body();
        body.setText(text);
        log.info("Body aangemaakt");

        mail.setHeader(header);
        log.info("Header toegevoegd aan Mail");
        mail.setBody(body);
        log.info("Body toegevoegd aan Mail");

        sender.send(mailToSimple(mail));
        log.info("Mail verstuurd als MimeMessage");
        saveJson(mail);
        log.info("Mail opgeslagen als Json");
    }

    @Test
    void sendMime() throws Exception {
        Mail mail = new Mail();
        log.info("Mail aangemaakt");

        Header header = new Header();
        header.setFrom(from);
        header.setReplyTo(replyTo);
        header.setTo(toOne);
        header.setSubject(new Object() {
        }.getClass().getEnclosingMethod().getName());
        log.info("Header aangemaakt");

        Body body = new Body();
        body.setText(text);
        log.info("Body aangemaakt");

        mail.setHeader(header);
        log.info("Header toegevoegd aan Mail");
        mail.setBody(body);
        log.info("Body toegevoegd aan Mail");

        sender.send(mailToMime(mail));
        log.info("Mail verstuurd als MimeMessage");
        saveJson(mail);
        log.info("Mail opgeslagen als Json");
    }

    @Test
    void sendMimeToMany() throws Exception {
        Mail mail = new Mail();
        log.info("Mail aangemaakt");

        Header header = new Header();
        header.setFrom(from);
        header.setReplyTo(replyTo);
        header.setTo(toTwo);
        header.setCc(toTwo);
        header.setBcc(toTwo);
        header.setSubject(new Object() {
        }.getClass().getEnclosingMethod().getName());
        log.info("Header aangemaakt");

        Body body = new Body();
        body.setText(text);
        log.info("Body aangemaakt");

        mail.setHeader(header);
        log.info("Header toegevoegd aan Mail");
        mail.setBody(body);
        log.info("Body toegevoegd aan Mail");

        sender.send(mailToMime(mail));
        log.info("Mail verstuurd als MimeMessage");
        saveJson(mail);
        log.info("Mail opgeslagen als Json");
    }

    @Test
    void sendMimeBodyTxt() throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setReplyTo(replyTo);
        helper.setTo(toOneArray);
        helper.setSubject(new Object() {
        }.getClass().getEnclosingMethod().getName());

        // TODO: setText from File
//        helper.setText(txtFile);

        sender.send(message);
//        saveJson(mail);
    }

    @Test
    void sendMimeBodyRtf() throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setReplyTo(replyTo);
        helper.setTo(toOneArray);
// below line updates the subject with the current method (Subject: sendMimeBodyRtf) ;-)
        helper.setSubject(new Object() {
        }.getClass().getEnclosingMethod().getName());

        // TODO: setText from File
//        helper.setText(rtfFile);

        sender.send(message);
//        saveJson(mail);
    }

    @Test
    void sendMimeBodyHtml() throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setReplyTo(replyTo);
        helper.setTo(toOneArray);
        helper.setSubject(new Object() {
        }.getClass().getEnclosingMethod().getName());

        // TODO: setText from File
//        helper.setText(htmlFile);

        sender.send(message);
//        saveJson(mail);
    }

    @Test
    void sendMimeAddOne() throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setReplyTo(replyTo);
        helper.setTo(toOneArray);
        helper.setSubject(new Object() {
        }.getClass().getEnclosingMethod().getName());
        helper.setText(text);

        ClassPathResource resource = new ClassPathResource(pdf.toString());
        helper.addAttachment(resource.toString(), resource);

        sender.send(message);
//        saveJson(mail);
    }

    @Test
    void sendMimeAddMany() throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setReplyTo(replyTo);
        helper.setTo(toOneArray);
        helper.setSubject(new Object() {
        }.getClass().getEnclosingMethod().getName());
        helper.setText(text);

        for (File file : fileList) {
            ClassPathResource resource = new ClassPathResource(file.toString());
            helper.addAttachment(resource.toString(), resource);
        }

        sender.send(message);
//        saveJson(mail);
    }

    @Test
    void sendMimeBodyBuild() throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setReplyTo(replyTo);
        helper.setTo(toTwoArray);
//        helper.setTo(toTeamArray);
//        helper.setCc(toBossArray);
        helper.setSubject(new Object() {
        }.getClass().getEnclosingMethod().getName());

        bodyBuilder.append("Hallo PaperPlaners,\n\n");
        bodyBuilder.append("Dit bericht heb ik gemaakt met mijn microMailService.\n");
        bodyBuilder.append("Die verstuurt emails aan meerdere ontvangers.\n");
        bodyBuilder.append("En stuurt meerdere bijlagen mee.\n\n");
        bodyBuilder.append("Met Vriendelijke groet,\n");
        bodyBuilder.append("Daniel Mikes");
        helper.setText(bodyBuilder.toString());

        sender.send(message);
//        saveJson(mail);
    }

    @Test
    void sendMimeInlineRtf() throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setReplyTo(replyTo);
        helper.setTo(toOneArray);
        helper.setSubject(new Object() {
        }.getClass().getEnclosingMethod().getName());

        sender.send(message);
//        saveJson(mail);
    }

    @Test
    void sendMimeInlineHtml() {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setReplyTo(replyTo);
            helper.setTo(toOneArray);
            helper.setSubject(new Object() {
            }.getClass().getEnclosingMethod().getName());
            helper.setText(
                "<html><body>" +
                    "<H1>Hallo!</H1>" +
                    "<p>Dit is een html tekst met daarin een plaatje</p>" +
                    "<div><img src='cid:id101'></div>" +
                    "</body></html>",
                true);
            helper.addInline("id101", new ClassPathResource("file.png"));

            message.setHeader("Content-Transfer-Encoding", "BASE64");

            sender.send(message);
        } catch (MessagingException e) {
            //TODO vervang door iets beters
            e.printStackTrace();
        }
//        saveJson(mail);
    }

}
