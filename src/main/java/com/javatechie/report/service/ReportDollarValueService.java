package com.javatechie.report.service;

import com.javatechie.report.entity.ReportDollarValue;
import com.javatechie.report.entity.ReportProduct;
import com.javatechie.report.repository.ReportDollarValueRepository;
import com.javatechie.report.repository.ReportProductRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportDollarValueService {

    @Autowired
    private ReportDollarValueRepository reportDollarValueRepository;

    public String exportReportDollarValue(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\sovann\\Desktop\\Report";
        List<ReportDollarValue> reportDollarValues = reportDollarValueRepository.findAll();
        //load file and compile it
        File file = ResourceUtils.getFile("classpath:ReportDollarValue.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportDollarValues);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\ReportDollarValue.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\ReportDollarValue.pdf");
        }

        return "report generated in path : " + path;
    }
}
