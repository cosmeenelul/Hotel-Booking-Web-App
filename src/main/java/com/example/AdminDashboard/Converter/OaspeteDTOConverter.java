package com.example.AdminDashboard.Converter;


import com.example.AdminDashboard.DTO.OaspeteDTO;
import com.example.AdminDashboard.Entity.Oaspete;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OaspeteDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public OaspeteDTO convertOaspeteToOaspeteDTO(Oaspete oaspete)
    {
        return modelMapper.map(oaspete,OaspeteDTO.class);
    }

    public Oaspete convertOaspeteDTOToOaspete(OaspeteDTO oaspeteDTO)
    {
        return modelMapper.map(oaspeteDTO,Oaspete.class);
    }
    public List<OaspeteDTO> convertListOaspetiToListOaspetiDTO(List<Oaspete> oaspeti)
    {
        return oaspeti
                .stream()
                .map(oaspete -> modelMapper.map(oaspete,OaspeteDTO.class))
                .collect(Collectors.toList());
    }

}
