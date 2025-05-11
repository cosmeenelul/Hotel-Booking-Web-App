package com.example.AdminDashboard.Converter;


import com.example.AdminDashboard.DTO.OaspeteDTOSimplu;
import com.example.AdminDashboard.Entity.Oaspete;
import com.example.AdminDashboard.Security.OaspeteRegistrationSecurityConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OaspeteDTOSimpluConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OaspeteRegistrationSecurityConfig oaspeteRegistrationSecurityConfig;

    public OaspeteDTOSimplu convertOaspeteToOaspeteDTOSimplu(Oaspete oaspete)
    {

        return modelMapper.map(oaspete,OaspeteDTOSimplu.class);
    }

    public List<OaspeteDTOSimplu> convertListOaspeteToListOaspeteDTOSimplu(List<Oaspete> oaspeti)
    {
        return oaspeti
                .stream()
                .map(oaspete -> modelMapper.map(oaspete,OaspeteDTOSimplu.class))
                .collect(Collectors.toList());
    }

    public Oaspete convertOaspeteDTOSimpluToOaspete(OaspeteDTOSimplu oaspeteDTOSimplu)
    {

        oaspeteDTOSimplu.setParola(oaspeteRegistrationSecurityConfig.passwordEncoder().encode(oaspeteDTOSimplu.getParola()));
        return modelMapper.map(oaspeteDTOSimplu,Oaspete.class);
    }
}
