package com.javatechie.report.repository;

import com.javatechie.report.entity.ReportDollarValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportDollarValueRepository extends JpaRepository<ReportDollarValue,Integer> {
    @Query(value = "SELECT * FROM vreportbydollarvalue where vendor_id = ?1", nativeQuery = true)
    List<ReportDollarValue> findReportDollarValueByVendorId(Integer vendor_id);
}
