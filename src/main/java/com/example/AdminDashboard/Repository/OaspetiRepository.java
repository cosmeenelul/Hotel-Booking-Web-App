package com.example.AdminDashboard.Repository;

import com.example.AdminDashboard.Entity.Oaspete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OaspetiRepository extends JpaRepository<Oaspete,Integer> {

    boolean existsByTelefon(String telefon);

    Optional<Oaspete> findByTelefon(String telefon);

    boolean existsByEmail(String email);

    Optional<Oaspete> findByEmail(String email);

    @Query("SELECT o FROM Oaspete o " +
            "JOIN o.rezervari r " +
            "GROUP BY o " +
            "HAVING COUNT(r) >= 3 " +
            "ORDER BY COUNT(r) DESC")
    List<Oaspete> findLoyalGuestsWithAtLeastThreeReservations();

}
