package group6.ecommerce.service.impl;

import group6.ecommerce.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpls implements EmailService {
    public final JavaMailSender javaMailSender;
    public EmailServiceImpls(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }
    @Override
    public void SendEmailTo(String email, String node) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Forget PassWord");
        simpleMailMessage.setText("Your password: " + node);
        javaMailSender.send(simpleMailMessage);
    }
}
