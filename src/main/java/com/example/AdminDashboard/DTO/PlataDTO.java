package com.example.AdminDashboard.DTO;

import java.time.LocalDate;

public class PlataDTO {
    private LocalDate dataPlata;
    private String metoda;

    public PlataDTO(){}

    public PlataDTO(LocalDate dataPlata, String metoda) {
        this.dataPlata = dataPlata;
        this.metoda = metoda;
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
}
