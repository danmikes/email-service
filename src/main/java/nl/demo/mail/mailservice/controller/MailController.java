package nl.demo.mail.mailservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.demo.mail.mailservice.model.Body;
import nl.demo.mail.mailservice.model.Header;
import nl.demo.mail.mailservice.model.Mail;
import nl.demo.mail.mailservice.service.DataService;
import nl.demo.mail.mailservice.service.FileService;
import nl.demo.mail.mailservice.service.MailMapper;

import nl.demo.mail.mailservice.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class MailController implements MailApi {

    final DataService dataService;
    final FileService fileService;
    final MailService mailService;
    final JavaMailSender sender;

    public MailController(DataService dataService, FileService fileService, MailService mailService, JavaMailSender sender) {
        this.dataService = dataService;
        this.fileService = fileService;
        this.mailService = mailService;
        this.sender = sender;
    }


    // ***************************************************
    // Hieronder de methoden die gehele Message ineens versturen
    // TODO: weghalen na testen

    public ResponseEntity<String> sendSimple(@RequestBody Mail mail) {
        dataService.create(mail);

        try {
            SimpleMailMessage message = MailMapper.mailToSimple(mail);
            sender.send(message);
            log.info("sendSimple geslaagd");
            dataService.delete(mail);

        } catch (MailException e) {
            e.printStackTrace();
            log.info("sendSimple gefaald");
        }

        return ResponseEntity.ok(mail.getId());
    }

    public ResponseEntity<String> sendMime(@RequestBody Mail mail) {
        dataService.create(mail);

        try {
            MimeMessage message = MailMapper.mailToMime(mail);
            sender.send(message);
            log.info("sendMime geslaagd");
            dataService.delete(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.info("sendMime gefaald");
        }

        return ResponseEntity.ok(mail.getId());
    }

    public ResponseEntity<String> sendMimeAttach(@RequestParam String json, @RequestParam MultipartFile[] file) throws IOException {
        Mail mail = new ObjectMapper().readValue(json, Mail.class);
        mail.getAttach().setFile(fileService.multipartFileToFile(file));
        dataService.create(mail);

        try {
            MimeMessage message = MailMapper.mailToMime(mail);
            sender.send(message);
            log.info("sendMimeAttach geslaagd");
            dataService.delete(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.info("sendMimeAttach gefaald");
        }

        return ResponseEntity.ok(mail.getId());
    }

    public ResponseEntity<String> sendInlineHtml(@RequestParam String json, @RequestParam MultipartFile file) throws JsonProcessingException {
        Mail mail = new ObjectMapper().readValue(json, Mail.class);
        dataService.create(mail);

        try {
            MimeMessage message = MailMapper.mailInlineHtml(mail, file);
            sender.send(message);
            log.info("sendInLineHtml geslaagd");
            dataService.delete(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.info("sendInlineHtml gefaald");
        }

        return ResponseEntity.ok(mail.getId());
    }

    public ResponseEntity<String> sendAttach(@PathVariable String id, @RequestParam MultipartFile file) {
        Mail mail = dataService.find(id);
        // File toekennen aan Mail.Attachment
        dataService.update(mail);
        log.info("Bijlage bij Mail met id <" + id + "> opgeslagen");

        return ResponseEntity.ok(mail.getId());
    }


    // Hierboven: wat er is!
    // ***************************************************
    // Hieronder: wat er komt?

    // TODO: check procedure
    // This is a wrapper for sendMail: it will contain all elements:
    // Consume all parts of mail
    // Store parts while receiving
    // Choose to send Simple or Mime
    // Send mail
    // Send message to sender: success/failure
    // Store mail if failure
    // Retry several times
    // Send message to sender: success/failure

    // give Id to user via cookie
    // receive header -> store header -> request body
    // receive body (String json / File file) -> story body -> request attach
    // receive attach (List<File> fileList) -> store file -> request next attach -> store fileList
    // show mail (Mail mail)
    // sendMail (<5 times):
    // if (body == String json && attach == null) -> mailToSimple -> sendSimple
    // else (if (body == File File ^^ attach != null)) -> mailToMime -> sendMime
    // if (HttpStatus = OK) -> sendSenderOK -> remove Mail
    // if (HttpStatus != OK) -> sendSenderNotOK -> keep Mail
    // if (HttpStatus != OK == 5) -> sendSenderNotOk -> remove Mail

    // TODO: weghalen na testen
    public ResponseEntity<String> testMail(@RequestParam String json, @RequestParam MultipartFile[] attach) throws IOException, MessagingException {
        Mail mail = new ObjectMapper().readValue(json, Mail.class);
        mail.getAttach().setFile(fileService.multipartFileToFile(attach));
        dataService.create(new Mail());
        log.info("Mail met id <" + mail.getId() + "> opgeslagen");

        mail.setHeader(mail.getHeader());
        dataService.update(mail);
        log.info("Header bij Mail met id <" + mail.getId() + "> opgeslagen");

        mail.setBody(mail.getBody());
        dataService.update(mail);
        log.info("Body bij Mail met id <" + mail.getId() + "> opgeslagen");

        mail.getAttach().setFile(fileService.multipartFileToFile(attach));
        dataService.update(mail);
        log.info("Bijlage bij Mail met id <" + mail.getId() + "> opgeslagen");

        mailService.sendMail(mail);
        log.info("Mail met id <" + mail.getId() + "> verzonden");

        dataService.delete(mail);

        return ResponseEntity.ok(mail.getId());
    }

    public ResponseEntity<String> makeMail() {
        // Mail aanmaken en opslaan in database, id teruggeven
        Mail mail = dataService.create(new Mail());
        log.info("Mail met id <" + mail.getId() + "> opgeslagen");

        // Vraag om Header en geef id mee
        return ResponseEntity.ok(mail.getId());
    }

    public ResponseEntity<String> setHeader(@PathVariable String id, @RequestBody Header header) {
        Mail mail = dataService.find(id);
        mail.setHeader(header);
        dataService.update(mail);
        log.info("Header bij Mail met id <" + id + "> opgeslagen");

        // Vraag om Body en geef id mee
        return ResponseEntity.ok(id);
    }

    public ResponseEntity<String> setBody(@PathVariable String id, @RequestBody Body body) {
        Mail mail = dataService.find(id);
        mail.setBody(body);
        dataService.update(mail);
        log.info("Body bij Mail met id <" + id + "> opgeslagen");

        // Vraag om bijlage en geef id mee
        return ResponseEntity.ok(id);
    }

    public ResponseEntity<String> setBody(@PathVariable String id, @RequestParam MultipartFile body) throws IOException {
        Mail mail = dataService.find(id);
        mail.getBody().setText(fileService.multipartFileToFile(body).toString());
        dataService.update(mail);
        log.info("Body bij Mail met id <" + id + "> opgeslagen");

        // Vraag om bijlage en geef id mee
        return ResponseEntity.ok(id);
    }

    public ResponseEntity<String> setAttach(@PathVariable String id, @RequestParam MultipartFile[] attach) throws IOException, MessagingException {
        Mail mail = dataService.find(id);
        mail.getAttach().setFile(fileService.multipartFileToFile(attach));
        dataService.update(mail);
        log.info("Bijlage bij Mail met id <" + id + "> opgeslagen");

        // Verstuur en stuur bevestiging met (id en) mail
        mailService.sendMail(mail);
        dataService.delete(mail);

        return ResponseEntity.ok(id);
    }


    // ***************************************************
    // Hieronder de Requests die door de Beheerder Console / Interface aangeroepen gaan worden

    public Mail get(@ModelAttribute Mail mail) {
        log.info("Mail gevonden met id <" + mail.getId() + ">");

        return dataService.find(mail);
    }

    public List<Mail> getAll() {
        log.info("Alle mails gevonden");

        return dataService.findAll();
    }

    public Mail create(@ModelAttribute Mail mail) {
        log.info("Mail aangemaakt met id <" + mail.getId() + ">");
        dataService.create(mail);

        return mail;
    }

    public Mail update(@ModelAttribute Mail mail) {
        log.info("Mail aangepast met id <" + mail.getId() + ">");
        dataService.update(mail);

        return mail;
    }

    public Mail delete(@ModelAttribute Mail mail) {
        log.info("Mail verwijderd met id <" + mail.getId() + ">");
        dataService.delete(mail);

        return mail;
    }

    public List<Mail> deleteAll() {
        log.info("Alle Mails verwijderd");
        List<Mail> mailList = dataService.findAll();
        dataService.deleteAll();

        return mailList;
    }

}
