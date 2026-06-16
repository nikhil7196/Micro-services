package com.notification.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.notification.DTO.DoctorResponseDTO;
import com.notification.DTO.NotificationDTO;
import com.notification.DTO.NotificationResponseDTO;
import com.notification.DTO.PatientResponseDTO;
import com.notification.db.NotificationRepository;
import com.notification.entities.Notification;
import com.notification.exception.NotificationNotfoundException;
import com.notification.exception.ResourceNotFoundException;
import com.notification.feign.DoctorFeign;
import com.notification.feign.PatientFeign;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationrepo;

    @Autowired
    private PatientFeign pf;

    @Autowired
    private DoctorFeign df;
    public NotificationResponseDTO addNotification(NotificationDTO dto) {

        Notification notification = new Notification();
        notification.setMessage(dto.getMessage());
        notification.setCategory(dto.getCategory());
        notification.setStatus(dto.getStatus() != null ? dto.getStatus() : "UNREAD");
        notification.setCreatedDate(LocalDateTime.now());
        int doctorId = dto.getDoctorId() != 0 ? dto.getDoctorId() : dto.getUserID();
        int patientId = dto.getPatientId();

        notification.setDoctorId(doctorId);
        notification.setPatientId(patientId);

        return mapToDTO(notificationrepo.save(notification));
    }
    public String deleteNotification(int id) throws NotificationNotfoundException {
        if (!notificationrepo.existsById(id)) {
            throw new NotificationNotfoundException("Notification not found with id " + id);
        }
        this.notificationrepo.deleteById(id);
        return "Successfully deleted";
    }
    public List<NotificationResponseDTO> getAllNotification() {
        return notificationrepo.findAll()
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    public Page<Notification> getAllNotificationsWithPagination(Pageable pageable) {
        return this.notificationrepo.findAll(pageable);
    }
    public Notification findById(int id) throws NotificationNotfoundException {
        return notificationrepo.findById(id)
                .orElseThrow(() -> new NotificationNotfoundException(
                        "Notification not found with id " + id));
    }
    public List<NotificationResponseDTO> getPatient(int id) {
        try {
            PatientResponseDTO resp = pf.getPatientById(id).getBody();
            if (resp == null || resp.getPatient() == null) {
                throw new ResourceNotFoundException("Patient not found with ID: " + id);
            }
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Patient service unavailable");
        }

        return notificationrepo.findByPatientId(id)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    public List<NotificationResponseDTO> getDoctor(int id) {
        try {
            DoctorResponseDTO resp = df.getDoctorById(id).getBody();
            if (resp == null || resp.getDoctor() == null) {
                throw new ResourceNotFoundException("Doctor not found with ID: " + id);
            }
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Doctor service unavailable");
        }

        return notificationrepo.findByDoctorId(id)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    public List<NotificationResponseDTO> getDoctorByEmail(String email) {
        DoctorResponseDTO resp;
        try {
            resp = df.getDoctorByEmail(email).getBody();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Doctor service unavailable");
        }

        if (resp == null || resp.getDoctor() == null) {
            throw new ResourceNotFoundException("Doctor not found with email: " + email);
        }

        return notificationrepo.findByDoctorId(resp.getDoctorId())
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    private NotificationResponseDTO mapToDTO(Notification n) {
        NotificationResponseDTO dto = new NotificationResponseDTO();
        dto.setNotificationId(n.getNotificationId());
        dto.setMessage(n.getMessage());
        dto.setCategory(n.getCategory());
        dto.setStatus(n.getStatus());
        dto.setCreatedDate(n.getCreatedDate());
        dto.setDoctorId(n.getDoctorId());
        dto.setPatientId(n.getPatientId());
        return dto;
    }
}