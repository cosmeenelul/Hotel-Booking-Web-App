package com.example.AdminDashboard.DTO;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class RezervareCreateDTO {

    private Integer idRezervare;
    private Integer persoane;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;
    private List<Integer> camere;
    private PlataDTO plata;
    private Double total;
    private String telefon;

    public RezervareCreateDTO(){}

    public RezervareCreateDTO(Integer idRezervare, Integer persoane, LocalDate checkIn, List<Integer> camere, PlataDTO plata, Double total, LocalDate checkOut, String telefon) {
        this.idRezervare = idRezervare;
        this.persoane = persoane;
        this.checkIn = checkIn;
        this.camere = camere;
        this.plata = plata;
        this.total = total;
        this.checkOut = checkOut;
        this.telefon = telefon;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public PlataDTO getPlata() {
        return plata;
    }

    public void setPlata(PlataDTO plata) {
        this.plata = plata;
    }

    public List<Integer> getCamere() {
        return camere;
    }

    public void setCamere(List<Integer> camere) {
        this.camere = camere;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public Integer getPersoane() {
        return persoane;
    }

    public void setPersoane(Integer persoane) {
        this.persoane = persoane;
    }

    public Integer getIdRezervare() {
        return idRezervare;
    }

    public void setIdRezervare(Integer idRezervare) {
        this.idRezervare = idRezervare;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
