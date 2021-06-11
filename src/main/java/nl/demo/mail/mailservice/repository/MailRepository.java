package nl.demo.mail.mailservice.repository;

import nl.demo.mail.mailservice.model.Mail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MailRepository extends MongoRepository<Mail,String> {

    // TODO: check queries?
    @Query(value = "{'from':?0}")
    Mail findByFrom(String from);

    @Query(value = "{'replyTo':?0}")
    Mail findByReplyTo(String replyTo);

    @Query(value = "{'to':?0}")
    Mail findByTo(String to);

    @Query(value = "{'subject':?0}")
    Mail findBySubject(String subject);

    @Query(value = "{'date':?0}")
    Mail findByDate(LocalDateTime date);

}
