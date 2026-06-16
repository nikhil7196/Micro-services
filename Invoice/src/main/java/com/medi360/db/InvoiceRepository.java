package com.medi360.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.medi360.entities.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

	List<Invoice> findByPatient_PatientId(int patientId);

    List<Invoice> findByPaymentStatus(String paymentStatus);

    long countByPaymentStatus(String paymentStatus);

    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Invoice i")
    Double getTotalBilledAmount();

    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Invoice i WHERE i.paymentStatus = :status")
    Double getTotalAmountByStatus(String status);

    long count();

}