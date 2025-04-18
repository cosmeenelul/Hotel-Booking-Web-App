package com.example.AdminDashboard.Service;

import com.example.AdminDashboard.Converter.CameraDTOConverter;
import com.example.AdminDashboard.Converter.OaspeteDTOConverter;
import com.example.AdminDashboard.DTO.OaspeteDTO;
import com.example.AdminDashboard.Entity.Oaspete;
import com.example.AdminDashboard.Exception.GlobalException;
import com.example.AdminDashboard.Repository.OaspetiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OaspetiService {

    @Autowired
    OaspeteDTOConverter oaspeteDTOConverter;

    private final OaspetiRepository oaspetiRepository;

    public OaspetiService(OaspetiRepository oaspetiRepository)
    {
        this.oaspetiRepository = oaspetiRepository;
    }

    public List<OaspeteDTO> findAll()
    {
        return oaspeteDTOConverter.convertListOaspetiToListOaspetiDTO(oaspetiRepository.findAll());
    }

    public OaspeteDTO findById(Integer id)
    {
        Oaspete oaspete = oaspetiRepository.findById(id).orElseThrow(() -> new GlobalException("Oaspetele cu acest id nu exista !"));

        return oaspeteDTOConverter.convertOaspeteToOaspeteDTO(oaspete);
    }

    public boolean existsByTelefon(String telefon)
    {
        return oaspetiRepository.existsByTelefon(telefon);
    }


    public Oaspete save(OaspeteDTO oaspete)
    {
        return oaspetiRepository.save(oaspeteDTOConverter.convertOaspeteDTOToOaspete(oaspete));
    }

    public OaspeteDTO findByTelefon(String telefon) {
        Oaspete oaspete = oaspetiRepository.findByTelefon(telefon).orElseThrow(() -> new GlobalException("Oaspetele cu acest telefon nu exista!"));

        return oaspeteDTOConverter.convertOaspeteToOaspeteDTO(oaspete);
    }

    public String deleteOaspeteById(Integer id)
    {
        if(!oaspetiRepository.existsById(id))
        {
            throw new GlobalException("Oaspetele cu acest ID nu exista!");
        }
        oaspetiRepository.deleteById(id);
        return "Oaspetele a fost sters cu succes!";
    }

    public Oaspete updateOaspete(Integer id, OaspeteDTO updatedOaspeteDTO)
    {
        Oaspete oaspeteExistent = oaspetiRepository.findById(id)
                .orElseThrow(()-> new GlobalException("Oaspetele cu acest ID nu exista!"));

        Oaspete updatedOaspeteDTOConvertedToOaspete = oaspeteDTOConverter.convertOaspeteDTOToOaspete(updatedOaspeteDTO);

        oaspeteExistent.setEmail(updatedOaspeteDTOConvertedToOaspete.getEmail());
        oaspeteExistent.setNume(updatedOaspeteDTOConvertedToOaspete.getNume());
        oaspeteExistent.setPrenume(updatedOaspeteDTOConvertedToOaspete.getPrenume());
        oaspeteExistent.setStrada(updatedOaspeteDTOConvertedToOaspete.getStrada());
        oaspeteExistent.setOras(updatedOaspeteDTOConvertedToOaspete.getOras());
        oaspeteExistent.setTara(updatedOaspeteDTOConvertedToOaspete.getTara());
        oaspeteExistent.setTelefon(updatedOaspeteDTOConvertedToOaspete.getTelefon());

        return oaspetiRepository.save(oaspeteExistent);
    }
}
