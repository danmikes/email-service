package nl.demo.mail.mailservice.trial;

import java.io.File;
import java.util.List;

// TODO: File -> Binary

public interface Input {

    String arnout = "arnout.steffelaar@kvk.nl";
    String boudewijn = "boudewijn.van.beckhoven@kvk.nl";
    String daniel = "daniel.mikes@kvk.nl";
    String erwin = "erwin.berkouwer@kvk.nl";
    String geert = "geert.vander.sman@kvk.nl";
    String jolanthe = "jolanthe.machgeels@kvk.nl";
    String team = "paperplane.planes@gmail.com";

    // TODO: Bij testen: zet actor/reactor op eigen naam
    String actor = daniel;
    String reactor = daniel;

    String from = actor;
    String replyTo = actor;

    List<String> toOne = List.of(actor);
    List<String> toTwo = List.of(actor, reactor);
    List<String> toTeam = List.of(arnout, boudewijn, daniel, jolanthe);
    List<String> toBoss = List.of(erwin, geert);

    String[] toOneArray = toOne.toArray(new String[0]);
    String[] toTwoArray = toTwo.toArray(new String[0]);
    String[] toTeamArray = toTeam.toArray(new String[0]);
    String[] ToBossArray = toBoss.toArray(new String[0]);

    String text = "text";
    String subject = "subject";
    StringBuilder bodyBuilder = new StringBuilder();
    String body = bodyBuilder.toString();

    // TODO: is deze nodig?
    String path = "src/test/resources/";

    File gif = new File("file.gif");
    File jpg = new File("file.jpg");
    File pdf = new File("file.pdf");
    File png = new File("file.png");
    File txt = new File("file.txt");
    File rtf = new File("file.rtf");
    File html = new File("file.html");

    File file = txt;
    List<File> fileList = List.of(gif, html, jpg, pdf, png, rtf, txt);

    File[] fileArray = fileList.toArray(new File[0]);

}
