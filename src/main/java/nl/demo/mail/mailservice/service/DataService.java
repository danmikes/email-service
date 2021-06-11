package nl.demo.mail.mailservice.service;

import nl.demo.mail.mailservice.model.Mail;

import java.time.LocalDateTime;
import java.util.List;

// TODO: Abstract / Default / Static methoden?
// Methoden update / delete ook overloaded voor (Mail mail) maken?
// Of juist Methode create alleen maken voor (String id)?
public interface DataService {

    Mail create(Mail mail);

    void update(Mail mail);

    void delete(Mail mail);

    void deleteAll();

    int count();

    // TODO: haalt Date uit ObjectId
    String idToDate(Mail mail);

    LocalDateTime dateToId(LocalDateTime date);

    Mail find(String id);

    Mail find(Mail mail);

    List<Mail> findAll();

    List<Mail> findByFrom(String from);

    List<Mail> findByReplyTo(String replyTo);

    List<Mail> findByTo(String to);

    List<Mail> findBySubject(String subject);

//    List<Mail> findByDate(LocalDateTime date);

}
