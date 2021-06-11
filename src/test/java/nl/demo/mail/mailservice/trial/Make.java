package nl.demo.mail.mailservice.trial;

import nl.demo.mail.mailservice.model.Body;
import nl.demo.mail.mailservice.model.Header;
import nl.demo.mail.mailservice.model.Mail;

import java.io.File;
import java.util.List;

import static nl.demo.mail.mailservice.trial.Input.*;

public interface Make {

    static Mail setHeader(Mail mail) {
        Header header = new Header();
        header.setFrom(from);
        header.setReplyTo(replyTo);
        header.setTo(toOne);
        header.setSubject(new Object() {
        }.getClass().getEnclosingMethod().getName());
        mail.setHeader(header);

        return mail;
    }

    static Mail setBody(Mail mail) {

        Body body = new Body();
        body.setText(text);
        mail.setBody(body);

        return mail;
    }

    static Mail setAttach(List<File> fileList, Mail mail) {
        mail.getAttach().setFile(fileList);

        return mail;
    }

}
