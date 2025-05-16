package com.example.AdminDashboard.DTO;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDate;

public class DetaliiRezervare {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;
    private Integer persoane;

    public DetaliiRezervare(){}

    public DetaliiRezervare(LocalDate checkIn, LocalDate checkOut, Integer persoane) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.persoane = persoane;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public Integer getPersoane() {
        return persoane;
    }

    public void setPersoane(Integer persoane) {
        this.persoane = persoane;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }
}
