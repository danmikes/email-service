package nl.demo.mail.mailservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.demo.mail.mailservice.model.Body;
import nl.demo.mail.mailservice.model.Header;
import nl.demo.mail.mailservice.model.Mail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
// TODO: bij aanmaken van Mail bijlagen opslaan als Bson in MongoDB?
//https://fcd-emailen.kok.nl/fcd/v1/api/mail/
// Test:3000 NPO:4200
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/mail/")
public interface MailApi {

    @PostMapping(value = "simple")
    ResponseEntity<String> sendSimple(@RequestBody Mail mail);

    // TODO: geef Mail en File mee met json

    @PostMapping(value = "mime")
    ResponseEntity<String> sendMime(@RequestBody Mail mail);

    @PostMapping(value = "mime/attach")
    ResponseEntity<String> sendMimeAttach(@RequestParam String json, @RequestParam MultipartFile[] file) throws IOException;

    @PostMapping(value = "html")
    ResponseEntity<String> sendInlineHtml(@RequestParam String json, @RequestParam MultipartFile file) throws JsonProcessingException;

    @PostMapping(value = "mime/attach/{id}")
    ResponseEntity<String> sendAttach(@PathVariable String id, @RequestParam MultipartFile file);


    // Hierboven: wat er is!
    // ***************************************************
    // Hieronder: wat er komt!

    // TODO: weghalen na testen
    @PostMapping(value = "test")
    ResponseEntity<String> testMail(@RequestParam String json, @RequestParam MultipartFile[] attach) throws IOException, MessagingException;

    @GetMapping(value = "make")
    ResponseEntity<String> makeMail();

    @PostMapping(value = "header/{id}")
    ResponseEntity<String> setHeader(@PathVariable String id, @RequestBody Header header);

    @PostMapping(value = "body/{id}")
    ResponseEntity<String> setBody(@PathVariable String id, @RequestBody Body body);

    @PostMapping(value = "body/rich/{id}")
    ResponseEntity<String> setBody(@PathVariable String id, @RequestParam MultipartFile body) throws IOException;

    @PostMapping(value = "attach/{id}")
    ResponseEntity<String> setAttach(@PathVariable String id, @RequestParam MultipartFile[] attach) throws IOException, MessagingException;


    // ***************************************************
    // Hieronder de Requests die door de Beheerder Console / Interface aangeroepen gaan worden

    @GetMapping(value = "search/{id}")
    Mail get(@ModelAttribute Mail mail);

    @GetMapping(value = "search/all")
    List<Mail> getAll();

    @PostMapping(value = "create")
    Mail create(@ModelAttribute Mail mail);

    @PutMapping(value = "update/{id}")
    Mail update(@ModelAttribute Mail mail);

    @DeleteMapping(value = "delete/{id}")
    Mail delete(@ModelAttribute Mail mail);

    @DeleteMapping(value = "delete/all")
    List<Mail> deleteAll();

}
