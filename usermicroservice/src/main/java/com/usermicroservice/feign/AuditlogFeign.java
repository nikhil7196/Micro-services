package com.usermicroservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auditlog")
public interface AuditlogFeign {

	@PostMapping("/auditlog/log")
	void log(@RequestParam("action") String action,
	         @RequestParam("id") int id); 

    @PostMapping("/auditlog/logFailure")
    void logFailure(@RequestParam("action") String action,
                    @RequestParam("errorMessage") String errorMessage);
}