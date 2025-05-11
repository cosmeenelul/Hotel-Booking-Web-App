package com.example.AdminDashboard.Entity;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;


@Entity
@Table(name = "tbltoken")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idtoken")
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "timpexpirare", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timpExpirare;
    private static final int TIMP_EXPIRARE = 10;


    @OneToOne
    @JoinColumn(name = "idoaspete")
    private Oaspete oaspete;

    public Token(Oaspete oaspete,String token) {
        this.oaspete = oaspete;
        this.token = token;
        this.setTimpExpirare(calculareTimpExpirare());
    }

    public Token(String token) {

        super();
        this.setTimpExpirare(calculareTimpExpirare());
        this.token = token;
    }


    public Token() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private Date calculareTimpExpirare() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,10);

        return new Date(calendar.getTime().getTime());
    }

    public Date getTimpExpirare() {
        return timpExpirare;
    }


    public void setTimpExpirare(Date timpExpirare) {
        this.timpExpirare = timpExpirare;
    }

    public Oaspete getOaspete() {
        return oaspete;
    }

    public void setOaspete(Oaspete oaspete) {
        this.oaspete = oaspete;
    }
}
