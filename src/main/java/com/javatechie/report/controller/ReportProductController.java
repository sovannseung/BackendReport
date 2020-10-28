package com.javatechie.report.controller;

import com.javatechie.report.Config.MyProperties;
import com.javatechie.report.entity.*;
import com.javatechie.report.repository.ReportProductRepository;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
public class ReportProductController {
    @Autowired
    private ReportProductRepository repositoryProduct;

    @Autowired
    private RestTemplate restTemplate;

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

    @GetMapping("/api/test")
    public List<Product> test() throws IOException, ParseException {
        //return restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", PostTest[].class);

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        FileReader readProduct = new FileReader("c:\\product.json");
        Object objProduct = jsonParser.parse(readProduct);
        //JSONArray productArray = (JSONArray) objProduct;
        List<Product> productList = (List<Product>) objProduct; // Arrays.asList(productArray);
        return productList;
    }


    @GetMapping("/api/getAllReportProduct")
    public List<ReportProduct> getAllReportProduct() {
        return null;
    }

    @GetMapping("/api/getReportProductByVendorId/{vendor_id}")
    public List<ReportProduct> getReportProductByVendorId(@PathVariable int vendor_id) {
        return null;
    }

    private List<ReportProduct> reportProduct(int vendor_id) throws IOException, ParseException {

        //Get data from OrderDetailProduct by using RestTemplate
        List<OrderDetailProduct> orderDetailProductList  = Arrays.asList(restTemplate.getForObject(
                        myProperties.getConfigValue("url.orderdetail") + "getAllOrderDetailProduct",
                        OrderDetailProduct[].class));

        //****** 1. JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        //****** 2. Mock up Product. Sometime we can filter by vendor_id
        FileReader readProduct = new FileReader("c:\\product.json");
        Object objProduct = jsonParser.parse(readProduct);
        List<Product> productList = (List<Product>) objProduct;

        //****** 3. Mock up User object for vendor
        FileReader readUser = new FileReader("c:\\user.json");
        Object objUser = jsonParser.parse(readUser);
        List<User> userList = (List<User>) objUser;


        
        return null;
    }

}
