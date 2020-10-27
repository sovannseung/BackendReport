package com.javatechie.report.controller;

import com.javatechie.report.Config.MyProperties;
import com.javatechie.report.entity.ReportProduct;
import com.javatechie.report.repository.ReportProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ReportProductController {
    @Autowired
    private ReportProductRepository repositoryProduct;

    @Autowired
    private MyProperties myProperties;

    @GetMapping("/monolith/getAllReportProduct")
    public List<ReportProduct> getAllReportProductMono() {
        return repositoryProduct.findAll();
    }

    @GetMapping("/monolith/getReportProductByVendorId/{vendor_id}")
    public List<ReportProduct> getReportProductByVendorIdMono(@PathVariable int vendor_id) {
        return repositoryProduct.findReportProductByVendorId(vendor_id);
    }

    @GetMapping("/api/getAllReportProduct")
    //public List<ReportProduct> getAllReportProduct() {
    public List<ReportProduct> getAllReportProduct() {


        return null;

        //return myProperties.getConfigValue("app.url.product");
    }

    @GetMapping("/api/getReportProductByVendorId/{vendor_id}")
    public List<ReportProduct> getReportProductByVendorId(@PathVariable int vendor_id) {
        return null;
    }

    private List<ReportProduct> reportProduct(int vendor_id) {

        RestTemplate restTemplate = new RestTemplate();

        //Mock up User object for vendor


        //Mock up Product


        return null;
    }

}
