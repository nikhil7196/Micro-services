package com.notification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.notification.DTO.NotificationDTO;
import com.notification.DTO.NotificationResponseDTO;
import com.notification.entities.Notification;
import com.notification.exception.NotificationNotfoundException;
import com.notification.service.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService ns;
    @PostMapping("/add")
    public ResponseEntity<NotificationResponseDTO> addNotificationAlias(
            @RequestBody NotificationDTO notificationDto) {
        return ResponseEntity.ok(this.ns.addNotification(notificationDto));
    }
    @PostMapping("/insertnotificationdata")
    public ResponseEntity<NotificationResponseDTO> addNotification(
            @RequestBody NotificationDTO notificationDto) {
        return ResponseEntity.ok(this.ns.addNotification(notificationDto));
    }
    @GetMapping("/getpatientbyid/{id}")
    public ResponseEntity<List<NotificationResponseDTO>> getPatient(
            @PathVariable("id") int id) {
        return ResponseEntity.ok(this.ns.getPatient(id));
    }
    @GetMapping("/getdoctorbyid/{id}")
    public ResponseEntity<List<NotificationResponseDTO>> getDoctor(
            @PathVariable("id") int id) {
        return ResponseEntity.ok(this.ns.getDoctor(id));
    }
    @GetMapping("/getdoctorbyemail")
    public ResponseEntity<List<NotificationResponseDTO>> getDoctorByEmail(
            @RequestParam("email") String email) {
        return ResponseEntity.ok(this.ns.getDoctorByEmail(email));
    }
    @DeleteMapping("/deletenotification/{nid}")
    public ResponseEntity<String> deleteNotification(
            @PathVariable("nid") int nid) throws NotificationNotfoundException {
        return ResponseEntity.ok(this.ns.deleteNotification(nid));
    }
    @GetMapping("/fetchallnotifications")
    public ResponseEntity<List<NotificationResponseDTO>> getAllNotification() {
        return ResponseEntity.ok(ns.getAllNotification());
    }
    @GetMapping("/fetchAllNotificationsPaginated")
    public ResponseEntity<Page<Notification>> getAllNotificationsPaginated(
            @RequestParam("pgno") int pgno,
            @RequestParam("size") int size,
            @RequestParam("sorting") String sorting,
            @RequestParam("asc") boolean asc) {
        Sort sort = asc ? Sort.by(sorting).ascending() : Sort.by(sorting).descending();
        Pageable pageable = PageRequest.of(pgno, size, sort);
        return ResponseEntity.ok(this.ns.getAllNotificationsWithPagination(pageable));
    }
    @GetMapping("/findNotificationById/{id}")
    public ResponseEntity<Notification> findbyid(
            @PathVariable("id") int id) throws NotificationNotfoundException {
        return ResponseEntity.ok(this.ns.findById(id));
    }
}