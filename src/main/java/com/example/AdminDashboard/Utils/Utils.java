package com.example.AdminDashboard.Utils;

import com.example.AdminDashboard.Entity.Camera;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Utils {

    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateRandomCodRezervare(){
        StringBuilder stringBuilder = new StringBuilder();

            for(int i = 0; i<10; i++)
            {
                int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
                char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
                stringBuilder.append(randomChar);
            }
            return stringBuilder.toString();
    }
    public static Double totalPrice(LocalDate checkIn, LocalDate checkOut, List<Camera> camere)
    {
        double total = 0;
        long zile = ChronoUnit.DAYS.between(checkIn,checkOut);

        for(Camera camera : camere)
        {
            total += camera.getPretPeNoapte() * zile;
        }

        return total;
    }


}
