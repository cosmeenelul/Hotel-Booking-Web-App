package com.example.AdminDashboard.Entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tblrezervari")
public class Rezervare {

    // Coloane tabel

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idrezervare")
    private Integer idRezervare;

    @Column(name = "persoane")
    private Integer persoane;

    @Column(name = "datacheckin")
    private LocalDate dataCheckIn;

    @Column(name = "datacheckout")
    private LocalDate dataCheckOut;

    @Column(name = "total")
    private Double total;

    @ManyToMany
    @JoinTable(
            name = "tblrezervaricamere",
            joinColumns = @JoinColumn(name = "idrezervare"),
            inverseJoinColumns = @JoinColumn(name = "nrcamera")
    )
    private List<Camera> camere;

    @ManyToMany
    @JoinTable(
            name = "tblrezervarioaspeti",
            joinColumns = @JoinColumn(name = "idrezervare"),
            inverseJoinColumns = @JoinColumn(name = "idoaspete")
    )
    private List<Oaspete> oaspeti;

    public Rezervare(){}

    public Rezervare(Integer idRezervare, Integer persoane, LocalDate dataCheckIn, LocalDate dataCheckOut, Double total, List<Camera> camere, List<Oaspete> oaspeti) {
        this.idRezervare = idRezervare;
        this.persoane = persoane;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.total = total;
        this.camere = camere;
        this.oaspeti = oaspeti;
    }

    // Getters Setters


    public void setIdRezervare(Integer idRezervare) {
        this.idRezervare = idRezervare;
    }

    public Integer getIdRezervare() {
        return idRezervare;
    }

    public Integer getPersoane() {
        return persoane;
    }

    public void setPersoane(Integer persoane) {
        this.persoane = persoane;
    }

    public LocalDate getDataCheckOut() {
        return dataCheckOut;
    }

    public void setDataCheckOut(LocalDate dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
    }

    public LocalDate getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(LocalDate dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Camera> getCamere() {
        return camere;
    }

    public void setCamere(List<Camera> camere) {
        this.camere = camere;
    }

    public List<Oaspete> getOaspeti() {
        return oaspeti;
    }

    public void setOaspeti(List<Oaspete> oaspeti) {
        this.oaspeti = oaspeti;
    }


}
