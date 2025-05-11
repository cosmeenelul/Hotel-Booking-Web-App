package com.example.AdminDashboard.Service;

import com.example.AdminDashboard.Exception.GlobalException;
import com.example.AdminDashboard.Repository.OaspetiRepository;
import com.example.AdminDashboard.Security.OaspeteRegistrationDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OaspeteRegistrationDetailsService implements UserDetailsService {
    private OaspetiRepository oaspetiRepository;

    public OaspeteRegistrationDetailsService(OaspetiRepository oaspetiRepository) {
        this.oaspetiRepository = oaspetiRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return oaspetiRepository.findByEmail(email)
                .map(OaspeteRegistrationDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Oaspetele cu acest mail nu a fost gasit"));
    }
}
