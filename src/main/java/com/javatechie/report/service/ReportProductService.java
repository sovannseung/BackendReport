package com.javatechie.report.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.report.Config.MyProperties;
import com.javatechie.report.entity.ReportProduct;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportProductService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MyProperties myProperties;

    @Autowired
    private UploadFile uploadFile;


    public String exportReportProduct(int vendor_id) throws FileNotFoundException, JRException, JsonProcessingException {
        String path = myProperties.getConfigValue("report.path") + "ReportProduct.pdf";
        //List<ReportProduct> reportProducts = reportProductRepository.findAll();
        //List<ReportProduct> reportProducts = Arrays.asList(restTemplate.getForObject(myProperties.getConfigValue("url.orderdetail") + "getAllReportProduct/" + vendor_id, ReportProduct[].class));
        List<ReportProduct> reportProducts = Arrays.asList(restTemplate.getForObject(myProperties.getConfigValue("url.own") + "getAllReportProduct/" + vendor_id, ReportProduct[].class));

        //load file and compile it
        //File file = ResourceUtils.getFile("classpath:ReportProduct.jrxml");
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream file = cl.getResourceAsStream("ReportProduct.jrxml");

        //JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JasperReport jasperReport = JasperCompileManager.compileReport(file);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportProducts);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, path);

        return uploadFile.myUpload(path);
    }
}
