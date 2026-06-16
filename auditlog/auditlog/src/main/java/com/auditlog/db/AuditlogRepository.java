package com.auditlog.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auditlog.entities.AuditLog;


@Repository
public interface AuditlogRepository extends JpaRepository<AuditLog,Integer>{
	

}

