package com.javatechie.report.controller;

import com.javatechie.report.service.ReportDollarValueService;
import com.javatechie.report.service.ReportProductService;
import com.javatechie.report.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
public class GenerateController {
    @Autowired
    private ReportService service;

    @Autowired
    private ReportProductService reportProductService;

    @Autowired
    private ReportDollarValueService reportDollarValueService;

    @GetMapping("/monolith/generateReport/{format}")
    public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return service.exportReport(format);
    }

    @GetMapping("/monolith/generateReportProduct/{format}")
    public String generateReportProductMono(@PathVariable String format) throws FileNotFoundException, JRException {
        return reportProductService.exportReportProduct(format);
    }

    @GetMapping("/monolith/generateReportDollarValue/{format}")
    public String generateReportDollarValueMono(@PathVariable String format) throws FileNotFoundException, JRException {
        return reportDollarValueService.exportReportDollarValue(format);
    }
}
