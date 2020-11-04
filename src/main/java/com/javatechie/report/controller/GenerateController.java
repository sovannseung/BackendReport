package com.javatechie.report.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javatechie.report.entity.ResultReport;
import com.javatechie.report.service.ReportDollarValueService;
import com.javatechie.report.service.ReportProductService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
public class GenerateController {

    @Autowired
    private ReportProductService reportProductService;

    @Autowired
    private ReportDollarValueService reportDollarValueService;

    @GetMapping("/api/generateReportProduct")
    public ResultReport generateReportProduct() throws FileNotFoundException, JRException, JsonProcessingException {
        String result = reportProductService.exportReportProduct(0);
        return new ResultReport(result);
    }

    @GetMapping("/api/generateReportProduct/{vendor_id}")
    public ResultReport generateReportProductByVendorId(@PathVariable int vendor_id) throws FileNotFoundException, JRException, JsonProcessingException {
        String result = reportProductService.exportReportProduct(vendor_id);
        return new ResultReport(result);
    }

    @GetMapping("/api/generateReportDollarValue")
    public ResultReport generateReportDollarValue() throws FileNotFoundException, JRException, JsonProcessingException {
        String result = reportDollarValueService.exportReportDollarValue(0);
        return new ResultReport(result);
    }

    @GetMapping("/api/generateReportDollarValue/{vendor_id}")
    public ResultReport generateReportDollarValue(@PathVariable int vendor_id) throws FileNotFoundException, JRException, JsonProcessingException {
        String result = reportDollarValueService.exportReportDollarValue(vendor_id);
        return new ResultReport(result);
    }

}
