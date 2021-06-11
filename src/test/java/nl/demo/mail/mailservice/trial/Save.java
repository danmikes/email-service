package nl.demo.mail.mailservice.trial;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import nl.demo.mail.mailservice.service.DataService;
import nl.demo.mail.mailservice.model.Mail;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static nl.demo.mail.mailservice.trial.Input.*;
import static nl.demo.mail.mailservice.trial.Make.*;

// TODO: File -> Binary
// TODO: communicatie met MongoDb: bson <-> json <-> java

@Slf4j
@SpringBootTest
class Save implements Make {

    @Autowired
    DataService service;

//    SaveMailTest(JavaMailSender sender, DataService service) {
//        this.sender = sender;
//        this.service = service;
//    }

    @Test
    void createDatabase() {
        MongoClient mongo = new MongoClient("localhost", 27017);
        mongo.getDatabase("MailServiceDatabase");
        log.info("MailServiceDatabase aangemaakt op " + mongo.getMongoClientOptions().toString());
    }

    @Test
    void deleteDatabase() {
        MongoClient mongo = new MongoClient("localhost", 27017);
        mongo.dropDatabase("MailServiceDatabase");
        log.info("MailServiceDatabase verwijderd van " + mongo.getMongoClientOptions().toString());
    }

    @Test
    void findAll() {
        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoDatabase database = mongo.getDatabase("MailServiceDatabase");
        MongoCollection collection = database.getCollection("mail");
        log.info("Collection <mail> aangemaakt op " + mongo.getMongoClientOptions().toString());

        Bson bson = (Bson) collection.find().first();
        log.info(bson.toString() + " aangemaakt");
    }

    @Test
    void getOne() {
        String id = "";
        Mail mail = service.find(id);
        log.info("Mail gevonden met id <" + mail.getId() + ">");
    }

    @Test
    void getAll() {
        List<Mail> mailList = service.findAll();
        for (Mail mail : mailList) {
            log.info("Mail gevonden met id <" + mail.getId() + ">");
        }
    }

    @Test
    void count() {
        int count = service.count();
        log.info("Aantal mails in database: " + count);
    }

    @Test
    void getFirst() {
        Mail mail = service.findAll().get(0);
        log.info("Eerste Mail gevonden met id <" + mail.getId() + ">");
    }

    @Test
    void getLast() {
        int last = service.count() - 1;
        Mail mail = service.findAll().get(last);
        log.info("Laatste Mail gevonden met id <" + mail.getId() + ">");
    }

    @Test
    void saveSimple() {
        Mail mail = new Mail();
        service.create(mail);
        log.info("Mail opgeslagen met id <" + mail.getId() + ">");

        setHeader(mail);
        service.update(mail);
        log.info("Header toegevoegd aan Mail met id <" + mail.getId() + ">");

        setBody(mail);
        service.update(mail);
        log.info("Body toegevoegd aan Mail met id <" + mail.getId() + ">");
    }

    @Test
    void saveMime() {
        Mail mail = new Mail();
        service.create(mail);
        log.info("Mail opgeslagen met id <" + mail.getId() + ">");

        setHeader(mail);
        service.update(mail);
        log.info("Header toegevoegd aan Mail met id <" + mail.getId() + ">");

        setBody(mail);
        service.update(mail);
        log.info("Body toegevoegd aan Mail met id <" + mail.getId() + ">");

//        setAttach(pdf, mail);
//        service.update(mail);

        setAttach(fileList, mail);
        service.update(mail);
        log.info("Attach toegevoegd aan Mail met id <" + mail.getId() + ">");
    }

    @Test
    void editSimple() {
        String subject = "saveSimple";
        Mail mail = service.findBySubject(subject).get(0);
        mail.getHeader().setSubject(new Object() {
        }.getClass().getEnclosingMethod().getName());
        log.info("Mail opgehaald met id <" + mail.getId() + ">");

        service.update(mail);
        log.info("Mail opgeslagen met id <" + mail.getId() + ">");
    }

    @Test
    void editMime() {
        String subject = "saveMime";
        Mail mail = service.findBySubject(subject).get(0);
        log.info("Mail opgehaald met id <" + mail.getId() + ">");

        mail.getHeader().setSubject(new Object() {
        }.getClass().getEnclosingMethod().getName());

        service.update(mail);
        log.info("Mail opgeslagen met id <" + mail.getId() + ">");
    }

    @Test
    void deleteSimple() {
        String subject = "editSimple";
        Mail mail = service.findBySubject(subject).get(0);
        log.info("Mail opgehaald met id <" + mail.getId() + ">");

        service.delete(mail);
        log.info("Mail verwijderd met id <" + mail.getId() + ">");
    }

    @Test
    void deleteMime() {
        String subject = "editMime";
        Mail mail = service.findBySubject(subject).get(0);
        log.info("Mail opgehaald met id <" + mail.getId() + ">");

        service.delete(mail);
        log.info("Mail verwijderd met id <" + mail.getId() + ">");
    }

    @Test
    void deleteFirst() {
        Mail mail = service.findAll().get(0);
        log.info("Mail opgehaald met id <" + mail.getId() + ">");

        service.delete(mail);
        log.info("Mail verwijderd met id <" + mail.getId() + ">");
    }

    @Test
    void deleteLast() {
        int last = service.count() - 1;
        Mail mail = service.findAll().get(last);
        log.info("Mail opgehaald met id <" + mail.getId() + ">");

        service.delete(mail);
        log.info("Mail verwijderd met id <" + mail.getId() + ">");
    }

    @Test
    void deleteAll() {
        service.deleteAll();
        log.info("Alle Mails verwijderd uit database");
    }

}
