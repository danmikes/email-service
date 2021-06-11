package nl.demo.mail.mailservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Header {

    @NotNull
    private String from;

    @NotNull
    private String replyTo;

    @NotNull
    private List<String> to;

    private List<String> cc;

    private List<String> bcc;

    @NotNull
    private String subject;

}
