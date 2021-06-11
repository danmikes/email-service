package nl.demo.mail.mailservice.service;

import nl.demo.mail.mailservice.model.Attach;
import nl.demo.mail.mailservice.model.Body;
import nl.demo.mail.mailservice.model.Header;
import nl.demo.mail.mailservice.model.Mail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.List;

// TODO: maak generieke mapper voor alle typen met switches via enum?

public interface MailMapper {

    JavaMailSender sender = new JavaMailSenderImpl();
    FileService service = new FileService();

    // Simple
    static SimpleMailMessage mailToSimple(Mail mail) {

        SimpleMailMessage message = new SimpleMailMessage();

        addHeader(message, mail.getHeader());
        addBody(message, mail.getBody());

        return message;
    }

    // Mime zonder bijlage
    static MimeMessage mailToMime(Mail mail) throws MessagingException {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        addHeader(helper, mail.getHeader());
        addBody(helper, mail.getBody());

        return message;
    }

    // Mime
    static MimeMessage mailToMimeAttach(Mail mail, MultipartFile attach) throws MessagingException {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false);

        addHeader(helper, mail.getHeader());
        addBody(helper, mail.getBody());
        addAttach(helper, mail.getAttach());

        return message;
    }

    // Mime met inline html
    static MimeMessage mailInlineHtml(Mail mail, MultipartFile html) throws MessagingException {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        addHeader(helper, mail.getHeader());
        addBody(helper, mail.getBody());
        addAttach(helper, mail.getAttach());

        return message;
    }

    // TODO: generieke methoden / labda function ?

    static SimpleMailMessage addHeader(SimpleMailMessage message, Header header) {
        message.setFrom(header.getFrom());
        message.setReplyTo(header.getReplyTo());
        message.setTo(header.getTo().toArray(new String[0]));
        if (header.getCc() != null) {
            message.setCc(header.getCc().toArray(new String[0]));
        }
        if (header.getBcc() != null) {
            message.setBcc(header.getBcc().toArray(new String[0]));
        }
        return message;
    }

    static MimeMessageHelper addHeader(MimeMessageHelper helper, Header header) throws MessagingException {
        helper.setFrom(header.getFrom());
        helper.setReplyTo(header.getReplyTo());
        helper.setTo(header.getTo().toArray(new String[0]));
        if (header.getCc() != null) {
            helper.setCc(header.getCc().toArray(new String[0]));
        }
        if (header.getBcc() != null) {
            helper.setBcc(header.getBcc().toArray(new String[0]));
        }
        return helper;
    }

    static SimpleMailMessage addBody(SimpleMailMessage message, Body body) {
        if (body.getText() != null) {
            message.setText(body.getText());
        }
        return message;
    }

    static MimeMessageHelper addBody(MimeMessageHelper helper, Body body) throws MessagingException {
        if (body.getText() != null) {
            helper.setText(body.getText());
        }
        return helper;
    }

    static MimeMessageHelper addAttach(MimeMessageHelper helper, Attach attach) throws MessagingException {
        List<File> fileList = attach.getFile();
        for (File file : fileList) {
            // TODO: check
            helper.addAttachment(file.getName(), file);
//            helper.addAttachment(file.getName(), new File(file.toString()));
        }

        return helper;
    }

    static MimeMessageHelper addInline(MimeMessageHelper helper, Attach attach) {
        if (attach.getFile() != null) {
//        File file2 = service.multipartFileToFile(file);
//        helper.addInline("file", new ClassPathResource(file.getName()));
//        helper.addInline("file", new ClassPathResource(attach.getFile().toString()));

//        helper.addAttachment("file",mailservice.multipartFileToFile(file));
//        helper.addInline("file",mailservice.multipartFileToFile(file));
        }
        return helper;
    }

}
