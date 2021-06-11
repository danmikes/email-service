package nl.demo.mail.mailservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Body {

    private String text;

    // TODO: geef contentType aan Body via enum (TXT,RTF,HTML)
    // private Content contentType;

}
