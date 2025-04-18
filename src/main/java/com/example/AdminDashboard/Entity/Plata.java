package com.example.AdminDashboard.Entity;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tblplati")
public class Plata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idplata")
    private Integer idPlata;

    @Column(name = "dataplata")
    private LocalDate dataPlata;

    @Column(name = "metoda")
    private String metoda;

    public Plata(){}

    public Plata(Integer idPlata, LocalDate dataPlata, String metoda) {
        this.idPlata = idPlata;
        this.dataPlata = dataPlata;
        this.metoda = metoda;
    }

    public void setIdPlata(Integer idPlata) {
        this.idPlata = idPlata;
    }

    public Integer getIdPlata() {
        return idPlata;
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
