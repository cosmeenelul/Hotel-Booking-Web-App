package com.example.AdminDashboard.Converter;

import com.example.AdminDashboard.DTO.RezervareResponseDTO;
import com.example.AdminDashboard.Entity.Rezervare;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RezervareResponseDTOConverter {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OaspeteDTOConverter oaspeteDTOConverter;

    @Autowired
    private OaspeteDTOSimpluConverter oaspeteDTOSimpluConverter;

    public RezervareResponseDTO convertRezervareToRezervareResponseDTO(Rezervare rezervare)
    {
        RezervareResponseDTO rezervareResponseDTO = modelMapper.map(rezervare,RezervareResponseDTO.class);
        rezervareResponseDTO.setOaspeteDTO(oaspeteDTOSimpluConverter.convertOaspeteToOaspeteDTOSimplu(rezervare.getOaspete()));
        return rezervareResponseDTO;
    }
    public List<RezervareResponseDTO> convertListRezervareToListRezervareResponseDTO(List<Rezervare> rezervari)
    {
        List<RezervareResponseDTO> rezervareResponseDTOS = rezervari.stream()
                .map(rezervare -> {
                    RezervareResponseDTO dto = modelMapper.map(rezervare, RezervareResponseDTO.class);
                    dto.setOaspeteDTO(oaspeteDTOSimpluConverter.convertOaspeteToOaspeteDTOSimplu(rezervare.getOaspete()));
                    return dto;
                })
                .toList();

        return rezervareResponseDTOS;
    }
}
