package com.medi360.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medi360.entities.KPIReport;

@Repository
public interface KPIReportRepository
        extends JpaRepository<KPIReport, Integer> {

}
