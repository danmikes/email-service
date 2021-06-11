package nl.demo.mail.mailservice.service;

import lombok.extern.slf4j.Slf4j;
import nl.demo.mail.mailservice.model.Mail;
import nl.demo.mail.mailservice.repository.MailRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// TODO: als static methoden in DataService dan deze klasse verwijderen
@Slf4j
@Service("DataService")
public class DataServiceImpl implements DataService {

    final MailRepository repository;
    final MongoTemplate mongoTemplate;

    DataServiceImpl(MailRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Mail create(Mail mail) {
        repository.insert(mail);
        return mail;
    }

    @Override
    public void update(Mail mail) {
        repository.save(mail);
    }

    @Override
    public void delete(Mail mail) {
        repository.delete(mail);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public int count() {
        return (int) repository.count();
    }

    // TODO: haalt datum uit ObjectId
    @Override
    public String idToDate(Mail mail) {
        // convert id to date
        return null;
    }

    @Override
    public LocalDateTime dateToId(LocalDateTime date) {
        // convert date to id
        return null;
    }

    @Override
    public Mail find(String id) {
        return repository.findById(id).get();
    }

    @Override
    public Mail find(Mail mail) {
        return repository.findById(mail.getId()).get();
    }

    @Override
    public List<Mail> findAll() {
        return repository.findAll();
    }

    // TODO: maak methoden
    @Override
    public List<Mail> findByFrom(String from) {
//        return (List<Mail>) repository.findByFrom(from);
          return mongoTemplate.find(Query.query(Criteria.where("from").is(from)), Mail.class);
    }

    @Override
    public List<Mail> findByReplyTo(String replyTo) {
//        return (List<Mail>) repository.findByReplyTo(replyTo);
        return mongoTemplate.find(Query.query(Criteria.where("replyTo").is(replyTo)), Mail.class);
    }

    @Override
    public List<Mail> findByTo(String to) {
//        return (List<Mail>) repository.findByTo(to);
        return mongoTemplate.find(Query.query(Criteria.where("to").is(to)), Mail.class);
    }

    @Override
    public List<Mail> findBySubject(String subject) {
//        return (List<Mail>) repository.findBySubject(subject);
        return mongoTemplate.find(Query.query(Criteria.where("subject").is(subject)), Mail.class);
    }

//    @Override
//    public List<Mail> findByDate(LocalDateTime date) {
//        return (List<Mail>) repository.findByDate(date);
//    }

}
