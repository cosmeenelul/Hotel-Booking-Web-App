package com.example.AdminDashboard.Converter;


import com.example.AdminDashboard.DTO.PlataDTO;
import com.example.AdminDashboard.Entity.Plata;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlataDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Plata convertPlataDTOToPlata(PlataDTO plataDTO)
    {
        return modelMapper.map(plataDTO,Plata.class);
    }
    public PlataDTO convertPlataToPlataDTO(Plata plata)
    {
        return modelMapper.map(plata,PlataDTO.class);
    }
}
