package com.example.AdminDashboard.Converter;


import com.example.AdminDashboard.DTO.OaspeteDTOSimplu;
import com.example.AdminDashboard.Entity.Oaspete;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OaspeteDTOSimpluConverter {

    @Autowired
    private ModelMapper modelMapper;

    public OaspeteDTOSimplu convertOaspeteToOaspeteDTOSimplu(Oaspete oaspete)
    {
        return modelMapper.map(oaspete,OaspeteDTOSimplu.class);
    }
}
