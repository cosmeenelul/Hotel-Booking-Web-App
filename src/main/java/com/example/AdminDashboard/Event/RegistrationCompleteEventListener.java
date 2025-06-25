package com.example.AdminDashboard.Event;

import com.example.AdminDashboard.DTO.OaspeteDTOSimplu;
import com.example.AdminDashboard.Entity.Oaspete;
import com.example.AdminDashboard.Service.EmailService;
import com.example.AdminDashboard.Service.OaspetiService;
import org.modelmapper.internal.bytebuddy.matcher.ElementMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationCompleteEventListener
        implements ApplicationListener<RegistrationCompleteEvent> {

    public static final Logger log = LoggerFactory.getLogger(RegistrationCompleteEventListener.class);
    private final OaspetiService oaspetiService;
    private final EmailService emailService;

    public RegistrationCompleteEventListener(OaspetiService oaspetiService, EmailService emailService) {
        this.oaspetiService = oaspetiService;
        this.emailService = emailService;
    }


    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        OaspeteDTOSimplu oaspeteDTOSimplu = event.getOaspeteDTOSimplu();

        Oaspete oaspete = oaspetiService.findOrSaveOaspete(oaspeteDTOSimplu);

        String verificationToken = UUID.randomUUID().toString();

        oaspetiService.saveOaspeteVerificationToken(oaspete, verificationToken);
        log.info("SUCCES LA SALVAREA TOKENULUI!!");

        String url = event.getApplicationUrl() + "/register/verificare-email?token=" + verificationToken;

        String subiect = "Bine ai venit la Golden Peak Hotel! Confirmă-ți înregistrarea";
        String mesaj =  """
<html>
<head>
    <style>
        h1 {
            color: #2c3e50;
            font-family: Arial, sans-serif;
        }
        p {
            font-family: Arial, sans-serif;
            font-size: 16px;
        }
        .button {
            display: inline-block;
            padding: 10px 20px;
            font-size: 16px;
            color: #fff;
            background-color: #3498db;
            text-decoration: none;
            border-radius: 5px;
        }
        .danger {
            color: #b71c1c;
            background-color: #ffebee;
            border: 1px solid #f44336;
            padding: 10px 15px;
            text-align: center;
            font-family: Arial, sans-serif;
            font-size: 18px;
            border-radius: 5px;
            margin: 10px 0;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
    <h1>Bine ai venit la Golden Peak Hotel!</h1>
    <p>Salut,</p>
    <p>Îți mulțumim că te-ai înregistrat cu succes pe platforma noastră. 
        Pentru a activa contul și a finaliza procesul de înregistrare, 
        te rugăm să confirmi adresa ta de email apăsând pe butonul de mai jos:</p>
    <h1 class="danger">ATENȚIE, ACEST LINK EXPIRĂ ÎN 10 MINUTE!</h1>
    <p style="text-align: center;">
        <a href="%s" class="button">Confirmă-ți contul</a>
    </p>
    <p>Dacă nu ai solicitat acest cont, te rugăm să ignori acest mesaj.</p>
    <p>Cu stimă,<br> Echipa Golden Peak Hotel</p>
</body>
</html>
""".formatted(url);

        emailService.trimiteEmailRezervare(oaspeteDTOSimplu.getEmail(),subiect,mesaj);

        log.info("Apăsați link-ul pentru a vă verifica înregistrarea: {}", url);
    }
}
