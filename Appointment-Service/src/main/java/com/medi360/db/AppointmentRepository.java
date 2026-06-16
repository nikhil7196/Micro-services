package com.medi360.db;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medi360.entities.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findByDoctorIdAndDateOrderByTimeAsc(int doctorId, LocalDate date);

    List<Appointment> findByDoctorIdOrderByDateAscTimeAsc(int doctorId);

    long count();
    long countByDate(LocalDate date);
    long countByStatus(String status);
    long countByDoctorId(int doctorId);
    long countByDoctorIdAndDate(int doctorId, LocalDate date);
}