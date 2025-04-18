package com.example.AdminDashboard.Repository;

import com.example.AdminDashboard.DTO.CameraDTO;
import com.example.AdminDashboard.Entity.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CamereRepository extends JpaRepository<Camera,Integer> {

    @Query("SELECT DISTINCT c.tipCamera FROM Camera c")
    List<String> findDistinctRoomTypes();

    @Query("SELECT c FROM Camera c WHERE c.nrPersoaneStandard >= :persoane " +
            "AND c.nrCamera NOT IN (" +
            "SELECT rc.nrCamera FROM Rezervare r JOIN r.camere rc " +
            "WHERE r.dataCheckIn < :checkOut AND r.dataCheckOut > :checkIn)")
    Optional<List<Camera>> findAvailableRoomsByDatesAndCapacity(@Param("checkIn") LocalDate checkIn,
                                                                @Param("checkOut") LocalDate checkOut,
                                                                @Param("persoane") Integer persoane);


}
