package com.example.AdminDashboard.Converter;

import com.example.AdminDashboard.DTO.OaspeteDTO;
import com.example.AdminDashboard.DTO.PlataDTO;
import com.example.AdminDashboard.DTO.RezervareCreateDTO;
import com.example.AdminDashboard.Entity.Camera;
import com.example.AdminDashboard.Entity.Oaspete;
import com.example.AdminDashboard.Entity.Plata;
import com.example.AdminDashboard.Entity.Rezervare;
import com.example.AdminDashboard.Exception.GlobalException;
import com.example.AdminDashboard.Repository.CamereRepository;
import com.example.AdminDashboard.Repository.OaspetiRepository;
import com.example.AdminDashboard.Utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class RezervareCreateDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PlataDTOConverter plataDTOConverter;

    @Autowired
    private CamereRepository camereRepository;
    @Autowired
    private OaspeteDTOConverter oaspeteDTOConverter;
    @Autowired
    private OaspetiRepository oaspetiRepository;

    public Rezervare convertRezervareCreateDTOToRezervare(RezervareCreateDTO rezervareCreateDTO)
    {
        Rezervare rezervare = modelMapper.map(rezervareCreateDTO,Rezervare.class);

        List<Camera> camere = camereRepository.findAllById(rezervareCreateDTO.getCamere());
        PlataDTO plataDTO = rezervareCreateDTO.getPlata();
        Plata plata = plataDTOConverter.convertPlataDTOToPlata(plataDTO);

//        OaspeteDTO oaspeteDTO = rezervareCreateDTO.getOaspeteDTO();
//        Oaspete oaspete = oaspetiRepository.findById(oaspeteDTO.getIdOaspete()).orElseThrow(
//                () -> new GlobalException("Oaspetele nu exista !"));
//        oaspeteDTOConverter.convertOaspeteDTOToOaspete(oaspeteDTO);
        LocalDate checkIn = rezervareCreateDTO.getCheckIn();
        LocalDate checkOut = rezervareCreateDTO.getCheckOut();

        rezervare.setDataCheckIn(checkIn);
        rezervare.setDataCheckOut(checkOut);
        rezervare.setCamere(camere);
        rezervare.setPlata(plata);
//        rezervare.setOaspete(oaspete);

        String codConfirmare = Utils.generateRandomCodRezervare();
        rezervare.setCodRezervare(codConfirmare);

        double total = Utils.totalPrice(checkIn,checkOut,camere);

        rezervare.setTotal(total);

        return rezervare;
    }
}
