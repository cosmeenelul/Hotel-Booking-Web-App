package com.example.AdminDashboard.Service;

import com.example.AdminDashboard.Converter.CameraDTOConverter;
import com.example.AdminDashboard.Converter.OaspeteDTOConverter;
import com.example.AdminDashboard.Converter.OaspeteDTOSimpluConverter;
import com.example.AdminDashboard.DTO.OaspeteDTO;
import com.example.AdminDashboard.DTO.OaspeteDTOSimplu;
import com.example.AdminDashboard.Entity.Oaspete;
import com.example.AdminDashboard.Entity.Token;
import com.example.AdminDashboard.Exception.GlobalException;
import com.example.AdminDashboard.Repository.OaspetiRepository;
import com.example.AdminDashboard.Repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class OaspetiService {

    @Autowired
    private OaspeteDTOConverter oaspeteDTOConverter;

    @Autowired
    private OaspeteDTOSimpluConverter oaspeteDTOSimpluConverter;

    private final OaspetiRepository oaspetiRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenRepository tokenRepository;

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

    // FIND BY EMAIL

    public Optional<Oaspete> findByEmail(String email)
    {
        Oaspete oaspete = oaspetiRepository.findByEmail(email).orElseThrow(() -> new GlobalException("Utilizatorul cu acest email nu exista!"));

        return Optional.ofNullable(oaspete);

    }

    // GET STATISTICI
    public List<OaspeteDTOSimplu> findLoyalGuestsWithAtLeastThreeReservations()
    {
        List<OaspeteDTOSimplu> oaspeteDTOSimpli = oaspeteDTOSimpluConverter
                .convertListOaspeteToListOaspeteDTOSimplu(oaspetiRepository.findLoyalGuestsWithAtLeastThreeReservations());

        return oaspeteDTOSimpli;
    }

    // AUTHENTICATION LOGIC

    public Oaspete findOrSaveOaspete(OaspeteDTOSimplu oaspeteDTOSimplu) {
        Optional<Oaspete> existingOaspete = oaspetiRepository.findByEmail(oaspeteDTOSimplu.getEmail());

        if (existingOaspete.isPresent()) {
            return existingOaspete.get();
        } else {
            Oaspete oaspete = oaspeteDTOSimpluConverter.convertOaspeteDTOSimpluToOaspete(oaspeteDTOSimplu);
            return oaspetiRepository.save(oaspete);
        }
    }


    public void saveOaspeteVerificationToken(Oaspete oaspete, String verificationToken) {
        Token token = new Token(oaspete, verificationToken);

        tokenRepository.save(token);
    }

    public String validareToken(String verificareToken) {
        Token token = tokenRepository.findByToken(verificareToken);

        if(token == null)
        {
            return "Verificare invalida a token-ului!";
        }
        Oaspete oaspete = token.getOaspete();
        Calendar calendar = Calendar.getInstance();

        if(token.getTimpExpirare().getTime() <= calendar.getTime().getTime())
        {
            tokenRepository.delete(token);
            return "Link-ul de verificare a expirat";
        }
        oaspete.setEsteActivat(true);
        oaspetiRepository.save(oaspete);

        return "valid";
    }
}
