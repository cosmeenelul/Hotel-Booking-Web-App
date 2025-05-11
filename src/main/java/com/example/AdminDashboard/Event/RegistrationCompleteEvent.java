package com.example.AdminDashboard.Event;

import com.example.AdminDashboard.DTO.OaspeteDTOSimplu;
import org.springframework.context.ApplicationEvent;

public class RegistrationCompleteEvent extends ApplicationEvent {
    private OaspeteDTOSimplu oaspeteDTOSimplu;
    private String applicationUrl;

    public RegistrationCompleteEvent(String applicationUrl, OaspeteDTOSimplu oaspeteDTOSimplu) {
        super(oaspeteDTOSimplu);
        this.applicationUrl = applicationUrl;
        this.oaspeteDTOSimplu = oaspeteDTOSimplu;
    }

    public OaspeteDTOSimplu getOaspeteDTOSimplu() {
        return oaspeteDTOSimplu;
    }

    public void setOaspeteDTOSimplu(OaspeteDTOSimplu oaspeteDTOSimplu) {
        this.oaspeteDTOSimplu = oaspeteDTOSimplu;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }
}
