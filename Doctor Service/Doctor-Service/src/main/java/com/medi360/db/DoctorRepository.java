package com.medi360.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medi360.entities.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findByEmail(String email);
    List<Doctor> findByNameContainingIgnoreCase(String name);

}