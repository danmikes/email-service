package nl.demo.mail.mailservice.config;

import nl.demo.mail.mailservice.repository.MailRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = MailRepository.class)
@Configuration
public class MongoDBConfig {

}
