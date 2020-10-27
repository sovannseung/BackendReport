package com.javatechie.report.repository;

import com.javatechie.report.entity.ReportProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportProductRepository extends JpaRepository<ReportProduct,Integer> {
    @Query(value = "SELECT * FROM vreportbyproduct where vendor_id = ?1", nativeQuery = true)
    List<ReportProduct> findReportProductByVendorId(Integer vendor_id);

}