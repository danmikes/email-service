package nl.demo.mail.mailservice.trial;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import nl.demo.mail.mailservice.model.Mail;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import static nl.demo.mail.mailservice.trial.Input.path;

@Service
public interface Output {

    static void saveJson(Mail mail) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(path + mail.getHeader().getSubject() + ".json"), mail);
    }

    static String makeJson(Mail mail) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(mail);
    }

    // TODO: maak generieke methode voor body/attach -> Base64
    static String toBase64(String input) {
        String output = "base64";
        return output;
    }

}
