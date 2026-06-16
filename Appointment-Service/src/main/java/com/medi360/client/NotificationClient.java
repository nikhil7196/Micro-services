package com.medi360.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.medi360.DTO.NotificationDTO;

@FeignClient(name = "NOTIFICATION")
public interface NotificationClient {

    @PostMapping("/notification/add")
    void addNotification(@RequestBody NotificationDTO dto);
}