package com.example.AdminDashboard.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailService;

    // Modificarea metodei pentru suport HTML
    public void trimiteEmailRezervare(String destinatar, String subiect, String mesaj) {
        try {
            // Creează mesajul de tip MIME
            MimeMessage mimeMessage = emailService.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Setează detaliile pentru email
            helper.setTo(destinatar);
            helper.setSubject(subiect);
            helper.setText(mesaj, true); // Al doilea parametru `true` setează conținutul ca HTML

            // Trimite emailul
            emailService.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Eroare la trimiterea emailului HTML", e);
        }
    }
}