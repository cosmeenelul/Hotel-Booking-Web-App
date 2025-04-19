package com.example.AdminDashboard.Service;

import com.example.AdminDashboard.Converter.CameraDTOConverter;
import com.example.AdminDashboard.Converter.OaspeteDTOConverter;
import com.example.AdminDashboard.Converter.OaspeteDTOSimpluConverter;
import com.example.AdminDashboard.DTO.OaspeteDTO;
import com.example.AdminDashboard.DTO.OaspeteDTOSimplu;
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
    private OaspeteDTOConverter oaspeteDTOConverter;

    @Autowired
    private OaspeteDTOSimpluConverter oaspeteDTOSimpluConverter;

    private final OaspetiRepository oaspetiRepository;

    public OaspetiService(OaspetiRepository oaspetiRepository)
    {
        this.oaspetiRepository = oaspetiRepository;
    }

    // GET ALL METHOD

    public List<OaspeteDTOSimplu> findAll()
    {
        return oaspeteDTOSimpluConverter.convertListOaspeteToListOaspeteDTOSimplu(oaspetiRepository.findAll());
    }

    // GET BY ID

    public OaspeteDTO findById(Integer id)
    {
        Oaspete oaspete = oaspetiRepository.findById(id).orElseThrow(() -> new GlobalException("Oaspetele cu acest id nu exista !"));

        return oaspeteDTOConverter.convertOaspeteToOaspeteDTO(oaspete);
    }

    public boolean existsByTelefon(String telefon)
    {
        return oaspetiRepository.existsByTelefon(telefon);
    }

    // POST OASPETE

    public Oaspete save(OaspeteDTOSimplu oaspeteDTOSimplu)
    {
        if(oaspetiRepository.existsByTelefon(oaspeteDTOSimplu.getTelefon()))
        {
            throw new GlobalException("Acest oaspete exista deja in baza de date!");
        }
        return oaspetiRepository.save(oaspeteDTOSimpluConverter.convertOaspeteDTOSimpluToOaspete(oaspeteDTOSimplu));
    }

    // GET BY TELEFON


    public OaspeteDTO findByTelefon(String telefon) {
        Oaspete oaspete = oaspetiRepository.findByTelefon(telefon).orElseThrow(() -> new GlobalException("Oaspetele cu acest telefon nu exista!"));

        return oaspeteDTOConverter.convertOaspeteToOaspeteDTO(oaspete);
    }

    // DELETE BY ID

    public String deleteOaspeteById(Integer id)
    {
        if(!oaspetiRepository.existsById(id))
        {
            throw new GlobalException("Oaspetele cu acest ID nu exista!");
        }
        oaspetiRepository.deleteById(id);
        return "Oaspetele a fost sters cu succes!";
    }

    // PUT METHOD OASPETE

    public String updateOaspete(Integer id, OaspeteDTOSimplu updatedOaspeteDTO)
    {
        Oaspete oaspeteExistent = oaspetiRepository.findById(id)
                .orElseThrow(()-> new GlobalException("Oaspetele cu acest ID nu exista!"));

        Oaspete updatedOaspeteDTOConvertedToOaspete = oaspeteDTOSimpluConverter.convertOaspeteDTOSimpluToOaspete(updatedOaspeteDTO);

        oaspeteExistent.setEmail(updatedOaspeteDTOConvertedToOaspete.getEmail());
        oaspeteExistent.setNume(updatedOaspeteDTOConvertedToOaspete.getNume());
        oaspeteExistent.setPrenume(updatedOaspeteDTOConvertedToOaspete.getPrenume());
        oaspeteExistent.setStrada(updatedOaspeteDTOConvertedToOaspete.getStrada());
        oaspeteExistent.setOras(updatedOaspeteDTOConvertedToOaspete.getOras());
        oaspeteExistent.setTara(updatedOaspeteDTOConvertedToOaspete.getTara());
        oaspeteExistent.setTelefon(updatedOaspeteDTOConvertedToOaspete.getTelefon());

        oaspetiRepository.save(oaspeteExistent);

        return "Oaspetele a fost modificat cu succes !";
    }
}
