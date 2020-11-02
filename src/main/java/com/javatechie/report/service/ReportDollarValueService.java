package com.javatechie.report.service;

import com.javatechie.report.Config.MyProperties;
import com.javatechie.report.entity.ReportDollarValue;
import com.javatechie.report.entity.ReportProduct;
import com.javatechie.report.repository.ReportDollarValueRepository;
import com.javatechie.report.repository.ReportProductRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportDollarValueService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MyProperties myProperties;

    public String exportReportDollarValue(int vendor_id) throws FileNotFoundException, JRException {
        String path = myProperties.getConfigValue("report.path") + "\\ReportDollarvalue.pdf";
        List<ReportDollarValue> reportProducts = Arrays.asList(restTemplate.getForObject(myProperties.getConfigValue("url.orderdetail") + "getAllReportDollarValue/" + vendor_id, ReportDollarValue[].class));

        //load file and compile it
        //File file = ResourceUtils.getFile("classpath:ReportDollarValue.jrxml");
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream file = cl.getResourceAsStream("ReportDollarValue.jrxml");

        //JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JasperReport jasperReport = JasperCompileManager.compileReport(file);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportProducts);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        //JasperExportManager.exportReportToPdfFile(jasperPrint, path );

        return path;
    }
}
