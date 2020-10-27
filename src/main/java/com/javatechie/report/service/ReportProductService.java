package com.javatechie.report.service;

import com.javatechie.report.entity.Employee;
import com.javatechie.report.entity.ReportProduct;
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
public class ReportProductService {

    @Autowired
    private ReportProductRepository reportProductRepository;

    public String exportReportProduct(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\sovann\\Desktop\\Report";
        List<ReportProduct> reportProducts = reportProductRepository.findAll();
        //load file and compile it
        File file = ResourceUtils.getFile("classpath:ReportProduct.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportProducts);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\ReportProduct.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\ReportProduct.pdf");
        }

        return "report generated in path : " + path;
    }
}
