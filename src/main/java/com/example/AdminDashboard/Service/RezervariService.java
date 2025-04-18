package com.example.AdminDashboard.Service;


import com.example.AdminDashboard.Converter.PlataDTOConverter;
import com.example.AdminDashboard.Converter.RezervareCreateDTOConverter;
import com.example.AdminDashboard.Converter.RezervareResponseDTOConverter;
import com.example.AdminDashboard.DTO.CameraDTO;
import com.example.AdminDashboard.DTO.OaspeteDTO;
import com.example.AdminDashboard.DTO.RezervareCreateDTO;
import com.example.AdminDashboard.DTO.RezervareResponseDTO;
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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class RezervariService {
    private final RezervariRepository rezervariRepository;
    private final RezervareCreateDTOConverter rezervareCreateDTOConverter;
    private final RezervareResponseDTOConverter rezervareResponseDTOConverter;
    private final CamereRepository camereRepository;
    private final OaspetiRepository oaspetiRepository;
    private final PlataDTOConverter plataDTOConverter;

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

    public List<RezervareResponseDTO> findAll()
    {
        return rezervareResponseDTOConverter.
                convertListRezervareToListRezervareResponseDTO(rezervariRepository.findAll());
    }

    // GET METHOD BY ID

    public RezervareResponseDTO findById(Integer id)
    {
        return rezervareResponseDTOConverter
                .convertRezervareToRezervareResponseDTO(rezervariRepository.findById(id)
                        .orElseThrow(() -> new GlobalException("Rezervarea cu acest ID nu a fost gasita!")));
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
        Oaspete oaspete = oaspetiRepository.findById(rezervareCreateDTO.getIdOaspete())
                .orElseThrow(() -> new GlobalException("Oaspetele nu exista in sistem"));

        Rezervare rezervare = rezervareCreateDTOConverter.convertRezervareCreateDTOToRezervare(rezervareCreateDTO);
        rezervare.setOaspete(oaspete);

        rezervariRepository.save(rezervare);

        return rezervareCreateDTO;
    }

    // UPDATE METHOD

    public void updateRezervare (Integer id, RezervareCreateDTO rezervareCreateDTO)
    {
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

        Plata plata = plataDTOConverter.convertPlataDTOToPlata(rezervareCreateDTO.getPlata());
        rezervare.setPlata(plata);

        Integer idOaspete = rezervareCreateDTO.getIdOaspete();
        Oaspete oaspete = oaspetiRepository.findById(idOaspete).orElseThrow(() -> new GlobalException("Oaspetele nu a fost gasit!"));
        rezervare.setOaspete(oaspete);

        rezervariRepository.save(rezervare);


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

}
