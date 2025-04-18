package com.example.AdminDashboard.Repository;


import com.example.AdminDashboard.DTO.RezervareResponseDTO;
import com.example.AdminDashboard.Entity.Rezervare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RezervariRepository extends JpaRepository<Rezervare,Integer> {

    Optional<Rezervare> findRezervareByCodRezervare(String codRezervare);

    @Query("SELECT r FROM Rezervare r WHERE r.oaspete.telefon = :telefon")
    List<Rezervare> findRezervareByTelefonOaspete(@Param("telefon") String telefon);
}
