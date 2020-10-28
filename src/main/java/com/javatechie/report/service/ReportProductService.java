package com.javatechie.report.service;

import com.javatechie.report.Config.MyProperties;
import com.javatechie.report.entity.Employee;
import com.javatechie.report.entity.OrderDetailProduct;
import com.javatechie.report.entity.ReportProduct;
import com.javatechie.report.repository.ReportProductRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
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

    public String exportReportProduct(int vendor_id) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\sovann\\Desktop\\Report";
        //List<ReportProduct> reportProducts = reportProductRepository.findAll();
        //List<ReportProduct> reportProducts = Arrays.asList(restTemplate.getForObject(myProperties.getConfigValue("url.orderdetail") + "getAllReportProduct/" + vendor_id, ReportProduct[].class));
        List<ReportProduct> reportProducts = Arrays.asList(restTemplate.getForObject(myProperties.getConfigValue("url.orderdetail") + "getAllReportProduct/" + vendor_id, ReportProduct[].class));

        //load file and compile it
        File file = ResourceUtils.getFile("classpath:ReportProduct.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportProducts);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\ReportProduct.pdf");

        return "report generated in path : " + path;
    }
}
