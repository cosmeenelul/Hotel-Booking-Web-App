package com.example.AdminDashboard.Repository;

import com.example.AdminDashboard.Entity.Token;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    List<Token> token(String token);

    Token findByToken(String token);
}
