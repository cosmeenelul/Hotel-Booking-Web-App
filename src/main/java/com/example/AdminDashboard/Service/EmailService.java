package com.example.AdminDashboard.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailService;

    public void trimiteEmailRezervare(String destinatar, String subiect, String mesaj)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(destinatar);
        mailMessage.setSubject(subiect);
        mailMessage.setText(mesaj);

        emailService.send(mailMessage);
    }

}
