package com.example.AdminDashboard.DTO;

import java.time.LocalDate;

public class PlataDTO {

    private Integer idPlata;
    private LocalDate dataPlata;
    private String metoda;

    public PlataDTO(){}

    public PlataDTO(LocalDate dataPlata, String metoda, Integer idPlata) {
        this.dataPlata = dataPlata;
        this.metoda = metoda;
        this.idPlata = idPlata;
    }

    public LocalDate getDataPlata() {
        return dataPlata;
    }

    public void setDataPlata(LocalDate dataPlata) {
        this.dataPlata = dataPlata;
    }

    public String getMetoda() {
        return metoda;
    }

    public void setMetoda(String metoda) {
        this.metoda = metoda;
    }

    public Integer getIdPlata() {
        return idPlata;
    }

    public void setIdPlata(Integer idPlata) {
        this.idPlata = idPlata;
    }
}
