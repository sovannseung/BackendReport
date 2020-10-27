package com.javatechie.report.controller;

import com.javatechie.report.entity.ReportDollarValue;
import com.javatechie.report.entity.ReportProduct;
import com.javatechie.report.repository.ReportDollarValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportDollarValueController {

    @Autowired
    private ReportDollarValueRepository reportDollarValueRepository;

    @GetMapping("/monolith/getAllReportDollarValue")
    public List<ReportDollarValue> getAllReportDollarValueMono() {
        return reportDollarValueRepository.findAll();
    }

    @GetMapping("/monolith/getReportDollarValueByVendorId/{vendor_id}")
    public List<ReportDollarValue> getReportDollarValueByVendorIdMono(@PathVariable int vendor_id) {
        return reportDollarValueRepository.findReportDollarValueByVendorId(vendor_id);
    }
}
