package nl.demo.mail.mailservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection="mail")
@JsonInclude(Include.NON_NULL)
public class Mail {

    @Id
    private String id;

    // TODO: haal Date uit ObjectId?
    // IntelliJ tijd en MongoDb tijd verschillen 2 uur?
    private LocalDateTime date = LocalDateTime.now();

    private Header header = new Header();

    private Body body = new Body();

    private Attach attach = new Attach();

}
