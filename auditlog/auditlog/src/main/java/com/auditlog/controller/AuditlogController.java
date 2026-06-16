package com.auditlog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auditlog.DTO.AuditlogResponseDTO;
import com.auditlog.entities.AuditLog;
import com.auditlog.exception.AuditNotFoundException;
import com.auditlog.service.AuditlogService;

@RestController
@RequestMapping("/auditlog")
public class AuditlogController {
	
	@Autowired
	private AuditlogService as;
	
	@GetMapping("/fetchallauditlog")
	public ResponseEntity<List<AuditlogResponseDTO>> getAllAuditlog(){
		
		return ResponseEntity.ok(this.as.getAllAuditlog());
	}
	
	@GetMapping("/fetchAllAuditlogsPaginated")
	public Page<AuditLog> f6(@RequestParam(name="pgno") int pgno,
							@RequestParam(name="size") int size,
							@RequestParam(name="sorting") String sorting,
							@RequestParam(name="asc") boolean asc){
		Sort sort=asc?Sort.by(sorting).ascending() : Sort.by(sorting).descending();
		
		Pageable pageable=PageRequest.of(pgno, size,sort);
		return this.as.getAllAuditlogsWithPagination(pageable);
	}
	
	@GetMapping("/findauditlogbyid/{id}")
	public ResponseEntity<AuditlogResponseDTO> findById(@PathVariable int id) throws AuditNotFoundException{
		return ResponseEntity.ok(this.as.findById(id));
	}
	
//	@GetMapping("/findAllAuditsOfUser/{id}")
//	public List<AuditLog> findAllAuditsOfUser(@PathVariable int id){
//		return this.as.findAllAuditsOfUser(id);
//	}
	
	@PostMapping("/log")
    public ResponseEntity<Void> log(@RequestParam String action,@RequestParam int id) {
        as.log(action,id);	
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logFailure")
    public ResponseEntity<Void> logFailure(@RequestParam String action,
                                           @RequestParam String errorMessage) {
        as.logFailure(action, errorMessage);
        return ResponseEntity.ok().build();
    }
}
