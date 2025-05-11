package com.example.AdminDashboard.Security;

import com.example.AdminDashboard.Entity.Oaspete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OaspeteRegistrationDetails implements UserDetails {

    private String userName;
    private String parola;
    private boolean esteActivat;
    private List<GrantedAuthority> authorities;

    public OaspeteRegistrationDetails(Oaspete oaspete) {
        this.userName = oaspete.getEmail();
        this.parola = oaspete.getParola();
        this.esteActivat = oaspete.getEsteActivat();
        this.authorities = Arrays.stream(oaspete.getRol()
                .split(",")).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return parola;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return esteActivat;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public boolean isEsteActivat() {
        return esteActivat;
    }

    public void setEsteActivat(boolean esteActivat) {
        this.esteActivat = esteActivat;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OaspeteRegistrationDetails that = (OaspeteRegistrationDetails) o;
        return esteActivat == that.esteActivat && Objects.equals(userName, that.userName) && Objects.equals(parola, that.parola) && Objects.equals(authorities, that.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, parola, esteActivat, authorities);
    }
}
