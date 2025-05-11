package com.example.AdminDashboard.Repository;

import com.example.AdminDashboard.Entity.Oaspete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OaspetiRepository extends JpaRepository<Oaspete,Integer> {

    boolean existsByTelefon(String telefon);

    Optional<Oaspete> findByTelefon(String telefon);

    boolean existsByEmail(String email);

    Optional<Oaspete> findByEmail(String email);
}
