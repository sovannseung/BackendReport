package com.javatechie.report.controller;

import com.javatechie.report.entity.ResultReport;
import com.javatechie.report.service.ReportDollarValueService;
import com.javatechie.report.service.ReportProductService;
import com.javatechie.report.service.ReportService;
import net.bytebuddy.asm.Advice;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.Arrays;

@RestController
public class GenerateController {
    @Autowired
    private ReportService service;

    @Autowired
    private ReportProductService reportProductService;

    @Autowired
    private ReportDollarValueService reportDollarValueService;


    //@GetMapping("/monolith/generateReport/{format}")
    public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return service.exportReport(format);
    }

    //@GetMapping("/monolith/generateReportProduct/{format}")
    public String generateReportProductMono(@PathVariable String format) throws FileNotFoundException, JRException {
        return reportProductService.exportReportProduct(0);
    }

    //@GetMapping("/monolith/generateReportDollarValue/{format}")
    public String generateReportDollarValueMono(@PathVariable String format) throws FileNotFoundException, JRException {
        return reportDollarValueService.exportReportDollarValue(0);
    }

    @GetMapping("/api/generateReportProduct")
    public ResultReport generateReportProduct() throws FileNotFoundException, JRException {
        String result = reportProductService.exportReportProduct(0);
        return new ResultReport(result);
    }

//    @GetMapping("/api/generateReportProduct")
//    public ResultReport generateReportProduct() throws FileNotFoundException, JRException {
//        return new ResultReport("Hello new path");
//    }

    @GetMapping("/api/generateReportProduct/{vendor_id}")
    public ResultReport generateReportProductByVendorId(@PathVariable int vendor_id) throws FileNotFoundException, JRException {
        String result = reportProductService.exportReportProduct(vendor_id);
        return new ResultReport(result);
    }

    @GetMapping("/api/generateReportDollarValue")
    public ResultReport generateReportDollarValue() throws FileNotFoundException, JRException {
        String result = reportDollarValueService.exportReportDollarValue(0);
        return new ResultReport(result);
    }

    @GetMapping("/api/generateReportDollarValue/{vendor_id}")
    public ResultReport generateReportDollarValue(@PathVariable int vendor_id) throws FileNotFoundException, JRException {
        String result = reportDollarValueService.exportReportDollarValue(vendor_id);
        return new ResultReport(result);
    }

}
