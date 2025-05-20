package com.example.AdminDashboard.Service;


import com.example.AdminDashboard.Converter.PlataDTOConverter;
import com.example.AdminDashboard.Converter.RezervareCreateDTOConverter;
import com.example.AdminDashboard.Converter.RezervareResponseDTOConverter;
import com.example.AdminDashboard.Converter.RezervareSimplaDTOConverter;
import com.example.AdminDashboard.DTO.*;
import com.example.AdminDashboard.Entity.Camera;
import com.example.AdminDashboard.Entity.Oaspete;
import com.example.AdminDashboard.Entity.Plata;
import com.example.AdminDashboard.Entity.Rezervare;
import com.example.AdminDashboard.Exception.GlobalException;
import com.example.AdminDashboard.Repository.CamereRepository;
import com.example.AdminDashboard.Repository.OaspetiRepository;
import com.example.AdminDashboard.Repository.RezervariRepository;
import com.example.AdminDashboard.Utils.Utils;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RezervariService {


    private final RezervariRepository rezervariRepository;
    private final RezervareCreateDTOConverter rezervareCreateDTOConverter;
    private final RezervareResponseDTOConverter rezervareResponseDTOConverter;
    private final CamereRepository camereRepository;
    private final OaspetiRepository oaspetiRepository;
    private final PlataDTOConverter plataDTOConverter;

    @Autowired
    EmailService emailService;

    @Autowired
    private RezervareSimplaDTOConverter rezervareSimplaDTOConverter;

    public RezervariService(RezervariRepository rezervariRepository, RezervareCreateDTOConverter rezervareCreateDTOConverter,
                            RezervareResponseDTOConverter rezervareResponseDTOConverter, CamereRepository camereRepository, OaspetiRepository oaspetiRepository, PlataDTOConverter plataDTOConverter)
    {
        this.rezervareCreateDTOConverter = rezervareCreateDTOConverter;
        this.rezervareResponseDTOConverter = rezervareResponseDTOConverter;
        this.rezervariRepository = rezervariRepository;
        this.camereRepository = camereRepository;
        this.oaspetiRepository = oaspetiRepository;
        this.plataDTOConverter = plataDTOConverter;
    }

    // GET METHOD

    public List<RezervareSimplaDTO> findAll()
    {
        return rezervareSimplaDTOConverter.convertListRezervareToListRezervareSimplaDTO(rezervariRepository.findAll());
    }

    // GET METHOD BY ID

    public RezervareResponseDTO findById(Integer id)
    {
        return rezervareResponseDTOConverter
                .convertRezervareToRezervareResponseDTO(rezervariRepository.findById(id)
                        .orElseThrow(() -> new GlobalException("Rezervarea cu acest ID nu a fost gasita!")));
    }


    // GET REZERVARE BY COD REZERVARE
    public RezervareSimplaDTO findRezervareByCodRezervare(String codRezervare)
    {
        return rezervareSimplaDTOConverter
                .convertRezervareToRezervareSimplaDTO(rezervariRepository.findRezervareByCodRezervare(codRezervare).orElseThrow(() -> new GlobalException("REZERVAREA NU A FOST GASITA!")));

    }


    // POST METHOD

    public RezervareCreateDTO saveRezervare(RezervareCreateDTO rezervareCreateDTO)
    {
        List<Camera> camereValabile = camereRepository
                .findAvailableRoomsByDatesAndCapacity(
                        rezervareCreateDTO.getCheckIn(),rezervareCreateDTO.getCheckOut(),rezervareCreateDTO.getPersoane())
                .orElseThrow(() -> new GlobalException("Camerele nu sunt valabile in acea perioada !"));

        List<Integer> idCamereValabile = new ArrayList<>();
        for(Camera camera : camereValabile)
        {
            idCamereValabile.add(camera.getNrCamera());
        }


        for(Integer idCerute : rezervareCreateDTO.getCamere())
        {
            if(!idCamereValabile.contains(idCerute))
            {
                throw new GlobalException("Cel putin o camera din cele selectate nu este valabila in acea perioada");
            }
        }
        Oaspete oaspete = oaspetiRepository.findByTelefon(rezervareCreateDTO.getTelefon())
                .orElseThrow(() -> new GlobalException("Oaspetele nu exista in sistem"));

        Rezervare rezervare = rezervareCreateDTOConverter.convertRezervareCreateDTOToRezervare(rezervareCreateDTO);
        rezervare.setOaspete(oaspete);

        rezervariRepository.save(rezervare);

        String destinatar = oaspete.getEmail();
        String subiect = "Confirmare Rezervare - Cod Confirmare: " + rezervare.getCodRezervare();
        String mesajHTML = """
            <html>
            <head>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        line-height: 1.6;
                        color: #333;
                        background-color: #f9f9f9;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        max-width: 600px;
                        margin: 20px auto;
                        background: #ffffff;
                        border: 1px solid #e0e0e0;
                        border-radius: 8px;
                        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                        padding: 20px;
                    }
                    .header {
                        background-color: #3498db;
                        color: white;
                        padding: 10px 15px;
                        border-radius: 8px 8px 0 0;
                        text-align: center;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 24px;
                    }
                    .content {
                        margin: 20px 0;
                    }
                    .content h2 {
                        color: #2c3e50;
                        font-size: 20px;
                        margin-bottom: 10px;
                    }
                    .details p {
                        margin: 5px 0;
                        font-size: 16px;
                    }
                    .total {
                        font-size: 18px;
                        font-weight: bold;
                        margin-top: 15px;
                        color: #2c3e50;
                    }
                    .footer {
                        text-align: center;
                        margin-top: 20px;
                        font-size: 14px;
                        color: #888;
                    }
                    .footer p {
                        margin: 5px 0;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Confirmare Rezervare - ETTI Hotel</h1>
                    </div>
                    <div class="content">
                        <h2>Salut, %s!</h2>
                        <p>Rezervarea ta a fost realizată cu succes. Detaliile sunt mai jos:</p>
                        <div class="details">
                            <p><strong>Check-in:</strong> %s</p>
                            <p><strong>Check-out:</strong> %s</p>
                            <p><strong>Camere:</strong> %s</p>
                            <p class="total"><strong>Total:</strong> %s lei</p>
                            <p><strong>Cod Rezervare:</strong> %s</p>
                        </div>
                    </div>
                    <div class="footer">
                        <p>Îți mulțumim pentru alegerea făcută!</p>
                        <p>Cu stimă, echipa ETTI Hotel</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                            oaspete.getNume(),
                            rezervare.getDataCheckIn(),
                            rezervare.getDataCheckOut(),
                            rezervare.getCamere().stream().map(Camera::getNrCamera).map(String::valueOf).collect(Collectors.joining(", ")),
                            rezervare.getTotal(),
                            rezervare.getCodRezervare()
                    );

        emailService.trimiteEmailRezervare(destinatar,subiect,mesajHTML);
        System.out.println("AM TRIMIS EMAILUL!");
        return rezervareCreateDTO;
    }

    public RezervareCreateDTO saveRezervareByEmail(RezervareCreateDTO rezervareCreateDTO,String email)
    {
        List<Camera> camereValabile = camereRepository
                .findAvailableRoomsByDatesAndCapacity(
                        rezervareCreateDTO.getCheckIn(),rezervareCreateDTO.getCheckOut(),rezervareCreateDTO.getPersoane())
                .orElseThrow(() -> new GlobalException("Camerele nu sunt valabile in acea perioada !"));

        List<Integer> idCamereValabile = new ArrayList<>();
        for(Camera camera : camereValabile)
        {
            idCamereValabile.add(camera.getNrCamera());
        }


        for(Integer idCerute : rezervareCreateDTO.getCamere())
        {
            if(!idCamereValabile.contains(idCerute))
            {
                throw new GlobalException("Cel putin o camera din cele selectate nu este valabila in acea perioada");
            }
        }
        Oaspete oaspete = oaspetiRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException("Oaspetele nu exista in sistem"));

        Rezervare rezervare = rezervareCreateDTOConverter.convertRezervareCreateDTOToRezervare(rezervareCreateDTO);
        rezervare.setOaspete(oaspete);

        rezervariRepository.save(rezervare);

        String destinatar = oaspete.getEmail();
        String subiect = "Confirmare Rezervare - Cod Confirmare: " + rezervare.getCodRezervare();
        String mesajHTML = """
            <html>
            <head>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        line-height: 1.6;
                        color: #333;
                        background-color: #f9f9f9;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        max-width: 600px;
                        margin: 20px auto;
                        background: #ffffff;
                        border: 1px solid #e0e0e0;
                        border-radius: 8px;
                        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                        padding: 20px;
                    }
                    .header {
                        background-color: #3498db;
                        color: white;
                        padding: 10px 15px;
                        border-radius: 8px 8px 0 0;
                        text-align: center;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 24px;
                    }
                    .content {
                        margin: 20px 0;
                    }
                    .content h2 {
                        color: #2c3e50;
                        font-size: 20px;
                        margin-bottom: 10px;
                    }
                    .details p {
                        margin: 5px 0;
                        font-size: 16px;
                    }
                    .total {
                        font-size: 18px;
                        font-weight: bold;
                        margin-top: 15px;
                        color: #2c3e50;
                    }
                    .footer {
                        text-align: center;
                        margin-top: 20px;
                        font-size: 14px;
                        color: #888;
                    }
                    .footer p {
                        margin: 5px 0;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Confirmare Rezervare - ETTI Hotel</h1>
                    </div>
                    <div class="content">
                        <h2>Salut, %s!</h2>
                        <p>Rezervarea ta a fost realizată cu succes. Detaliile sunt mai jos:</p>
                        <div class="details">
                            <p><strong>Check-in:</strong> %s</p>
                            <p><strong>Check-out:</strong> %s</p>
                            <p><strong>Camere:</strong> %s</p>
                            <p class="total"><strong>Total:</strong> %s lei</p>
                            <p><strong>Cod Rezervare:</strong> %s</p>
                        </div>
                    </div>
                    <div class="footer">
                        <p>Îți mulțumim pentru alegerea făcută!</p>
                        <p>Cu stimă, echipa ETTI Hotel</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                oaspete.getNume(),
                rezervare.getDataCheckIn(),
                rezervare.getDataCheckOut(),
                rezervare.getCamere().stream().map(Camera::getNrCamera).map(String::valueOf).collect(Collectors.joining(", ")),
                rezervare.getTotal(),
                rezervare.getCodRezervare()
        );

        emailService.trimiteEmailRezervare(destinatar,subiect,mesajHTML);

        return rezervareCreateDTO;
    }




    // UPDATE METHOD

    public void updateRezervare (Integer id, RezervareCreateDTO rezervareCreateDTO)
    {

        System.out.println(rezervareCreateDTO.getTelefon());
        Rezervare rezervare = rezervariRepository.findById(id)
                .orElseThrow(() -> new GlobalException("Rezervarea nu a fost gasita!"));

        List<Camera> camere = camereRepository.findAllById(rezervareCreateDTO.getCamere());

        List<Camera> camereValabile = camereRepository
                .findAvailableRoomsByDatesAndCapacity(
                        rezervareCreateDTO.getCheckIn(),rezervareCreateDTO.getCheckOut(),rezervareCreateDTO.getPersoane())
                .orElseThrow(() -> new GlobalException("Camerele nu sunt valabile in acea perioada !"));

        List<Integer> idCamereValabile = new ArrayList<>();
        for(Camera camera : camereValabile)
        {
            idCamereValabile.add(camera.getNrCamera());
        }


        for(Integer idCerute : rezervareCreateDTO.getCamere())
        {
            if(!idCamereValabile.contains(idCerute))
            {
                throw new GlobalException("Cel putin o camera din cele selectate nu este valabila in acea perioada");
            }
        }


        rezervare.setPersoane(rezervareCreateDTO.getPersoane());
        rezervare.setDataCheckIn(rezervareCreateDTO.getCheckIn());
        rezervare.setDataCheckOut(rezervareCreateDTO.getCheckOut());
        rezervare.setTotal(Utils
                .totalPrice(rezervareCreateDTO.getCheckIn(),rezervareCreateDTO.getCheckOut(),camere));
        rezervare.setCamere(camere);

        String telefon = rezervareCreateDTO.getTelefon();
        Oaspete oaspete = oaspetiRepository.findByTelefon(telefon).orElseThrow(() -> new GlobalException("Oaspetele nu a fost gasit!"));
        rezervare.setOaspete(oaspete);

        Rezervare rezervare1 = rezervareCreateDTOConverter.convertRezervareCreateDTOToRezervare(rezervareCreateDTO);
        String codRezervare = rezervare1.getCodRezervare();
        rezervare.setCodRezervare(codRezervare);

        rezervariRepository.save(rezervare);

        String destinatar = oaspete.getEmail();
        String subiect = "Confirmare Actualizare Rezervare - Cod Confirmare: " + rezervare.getCodRezervare();
        String mesajHTML = """
            <html>
            <head>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        line-height: 1.6;
                        color: #333;
                        background-color: #f9f9f9;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        max-width: 600px;
                        margin: 20px auto;
                        background: #ffffff;
                        border: 1px solid #e0e0e0;
                        border-radius: 8px;
                        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                        padding: 20px;
                    }
                    .header {
                        background-color: #3498db;
                        color: white;
                        padding: 10px 15px;
                        border-radius: 8px 8px 0 0;
                        text-align: center;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 24px;
                    }
                    .content {
                        margin: 20px 0;
                    }
                    .content h2 {
                        color: #2c3e50;
                        font-size: 20px;
                        margin-bottom: 10px;
                    }
                    .details p {
                        margin: 5px 0;
                        font-size: 16px;
                    }
                    .total {
                        font-size: 18px;
                        font-weight: bold;
                        margin-top: 15px;
                        color: #2c3e50;
                    }
                    .footer {
                        text-align: center;
                        margin-top: 20px;
                        font-size: 14px;
                        color: #888;
                    }
                    .footer p {
                        margin: 5px 0;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Confirmare Actualizare Rezervare - ETTI Hotel</h1>
                    </div>
                    <div class="content">
                        <h2>Salut, %s!</h2>
                        <p>Rezervarea ta a fost actualizată cu succes. Detaliile noii tale rezervări sunt mai jos:</p>
                        <div class="details">
                            <p><strong>Check-in:</strong> %s</p>
                            <p><strong>Check-out:</strong> %s</p>
                            <p><strong>Camere:</strong> %s</p>
                            <p class="total"><strong>Total:</strong> %s lei</p>
                            <p><strong>Cod Rezervare:</strong> %s</p>
                        </div>
                    </div>
                    <div class="footer">
                        <p>Îți mulțumim pentru alegerea făcută!</p>
                        <p>Cu stimă, echipa ETTI Hotel</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                oaspete.getNume(),
                rezervare.getDataCheckIn(),
                rezervare.getDataCheckOut(),
                rezervare.getCamere().stream().map(Camera::getNrCamera).map(String::valueOf).collect(Collectors.joining(", ")),
                rezervare.getTotal(),
                rezervare.getCodRezervare()
        );

        emailService.trimiteEmailRezervare(destinatar,subiect,mesajHTML);

        System.out.println("S-A FACUT SAVE UL");
    }

    // DELETE METHOD


    public String deleteById(Integer id)
    {
        if(rezervariRepository.existsById(id))
        {
            rezervariRepository.deleteById(id);
        }
        else
        {
            throw new GlobalException("Rezervarea cu acest id nu exista sau a fost deja stearsa");
        }
        return "Rezervare stearsa cu succes !";
    }

    // GET STATISTICI


    public List<RezervareResponseDTO> findRezervariActive()
    {
        List<RezervareResponseDTO> rezervareResponseDTOS = rezervareResponseDTOConverter
                .convertListRezervareToListRezervareResponseDTO(rezervariRepository.findRezervariActive(LocalDate.now()));

        return rezervareResponseDTOS;

    }

}
