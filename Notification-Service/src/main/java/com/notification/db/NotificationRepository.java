package com.notification.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notification.entities.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer>{
	List<Notification> findByPatientId(int patientId);
	List<Notification> findByDoctorId(int doctorId);
}
