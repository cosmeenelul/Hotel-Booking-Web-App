package com.example.AdminDashboard.Converter;

import com.example.AdminDashboard.DTO.RezervareResponseDTO;
import com.example.AdminDashboard.DTO.RezervareSimplaDTO;
import com.example.AdminDashboard.Entity.Rezervare;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RezervareSimplaDTOConverter{

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OaspeteDTOConverter oaspeteDTOConverter;

    @Autowired
    private OaspeteDTOSimpluConverter oaspeteDTOSimpluConverter;

    public RezervareSimplaDTO convertRezervareToRezervareSimplaDTO(Rezervare rezervare)
    {
        RezervareSimplaDTO rezervareResponseDTO = modelMapper.map(rezervare, RezervareSimplaDTO.class);
        return rezervareResponseDTO;
    }
    public List<RezervareSimplaDTO> convertListRezervareToListRezervareSimplaDTO(List<Rezervare> rezervari)
    {
        List<RezervareSimplaDTO> rezervareResponseDTOS = rezervari.stream()
                .map(rezervare -> {
                    RezervareSimplaDTO dto = modelMapper.map(rezervare,  RezervareSimplaDTO.class);
                    return dto;
                })
                .toList();

        return rezervareResponseDTOS;
    }
}
