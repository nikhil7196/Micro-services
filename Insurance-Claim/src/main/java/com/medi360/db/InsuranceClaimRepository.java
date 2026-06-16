package com.medi360.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.medi360.entities.InsuranceClaim;

@Repository
public interface InsuranceClaimRepository extends JpaRepository<InsuranceClaim, Integer> {
    List<InsuranceClaim> findByPatient_PatientId(int patientId);

    List<InsuranceClaim> findByStatus(String status);

    long countByStatus(String status);

    long count();
    long countByPatient_PatientId(int patientId);

    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM InsuranceClaim i")
    Double getTotalClaimAmount();

    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM InsuranceClaim i WHERE i.status = :status")
    Double getAmountByStatus(String status);
}