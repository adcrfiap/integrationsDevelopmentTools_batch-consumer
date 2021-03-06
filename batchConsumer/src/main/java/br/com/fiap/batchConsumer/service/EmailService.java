package br.com.fiap.batchConsumer.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.subject}")
    private String subject;

    @Value("${spring.mail.to}")
    private String to;

    @Value("${spring.mail.from}")
    private String from;

    public void send( String text){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setText(text);
        simpleMailMessage.setSubject(subject);

        try {
        mailSender.send(simpleMailMessage);
    } catch (Exception e) {
        e.printStackTrace();
    }

    }

}
