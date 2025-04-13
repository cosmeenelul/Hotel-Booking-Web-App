package com.example.AdminDashboard.Service;


import com.example.AdminDashboard.Entity.Rezervare;
import com.example.AdminDashboard.Repository.RezervariRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RezervariService {
   private final RezervariRepository rezervariRepository;

    public RezervariService(RezervariRepository rezervariRepository)
    {
        this.rezervariRepository = rezervariRepository;
    }

    public List<Rezervare> findAll()
    {
        return rezervariRepository.findAll();
    }
}
