package com.example.AdminDashboard.DTO;

import java.time.LocalDate;
import java.util.List;

public class RezervareSimplaDTO {
    private Integer idRezervare;
    private Integer persoane;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Double total;
    private String codRezervare;

    public RezervareSimplaDTO(LocalDate checkIn, Double total, Integer idRezervare, String codRezervare, LocalDate checkOut, Integer persoane) {
        this.checkIn = checkIn;
        this.total = total;
        this.idRezervare = idRezervare;
        this.codRezervare = codRezervare;
        this.checkOut = checkOut;
        this.persoane = persoane;
    }

    public RezervareSimplaDTO() {
    }

    public LocalDate getCheckIn() {
        return checkIn;
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

    public Integer getIdRezervare() {
        return idRezervare;
    }

    public void setIdRezervare(Integer idRezervare) {
        this.idRezervare = idRezervare;
    }

    public Integer getPersoane() {
        return persoane;
    }

    public void setPersoane(Integer persoane) {
        this.persoane = persoane;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCodRezervare() {
        return codRezervare;
    }

    public void setCodRezervare(String codRezervare) {
        this.codRezervare = codRezervare;
    }
}
