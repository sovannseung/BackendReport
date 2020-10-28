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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
        return reportProduct(0);
    }

    @GetMapping("/api/getAllReportProduct/{vendor_id}")
    public List<ReportProduct> getAllReportProductByVendorId(@PathVariable int vendor_id) {
        return reportProduct(vendor_id);
    }

    private List<ReportProduct> reportProduct(int vendor_id)  {

        //****** 1. Get data from OrderDetailProduct by using RestTemplate
        List<OrderDetailProduct> orderDetailProductList  = Arrays.asList(restTemplate.getForObject(myProperties.getConfigValue("url.orderdetail") + "getAllOrderDetailProduct", OrderDetailProduct[].class));

        //****** 2. Mock up Product. Sometime we can filter by vendor_id
        List<Product> productList = mockProduct();

        //****** 3. Mock up User object for vendor
        List<User> userList = mockUser();

        // Add userList to HashMap
        HashMap<Integer, User> userHashMap = new HashMap<Integer, User>();
        userList.stream().forEach(u -> {
            userHashMap.put(u.getId(), u);
        });

        // Join Product and User Object Then add it to HasMap
        HashMap<Integer, DTOProductVendor> dtoProductVendorHashMap = new HashMap<Integer, DTOProductVendor>();
        List<DTOProductVendor> dtoProductVendorList = productList.stream().map(p -> {
            DTOProductVendor dto = new DTOProductVendor();
            dto.setProduct_id(p.getId());
            dto.setName(p.getName());
            dto.setCategoryName(p.getCategoryName());
            dto.setVendor_id(p.getVendorId());

            User objUser = userHashMap.get(p.getVendorId());
            dto.setVendor(objUser.getFirstName() + ' ' + objUser.getLastName());

            return dto;
        }).collect(Collectors.toList());

        dtoProductVendorList.stream().forEach(dto -> {
            dtoProductVendorHashMap.put(dto.getProduct_id(), dto);
        });

        // Final result. ReportProduct Object
//        List<ReportProduct> result = orderDetailProductList
//                .stream()
//                .map(o -> {
//                    ReportProduct temp = new ReportProduct();
//                    DTOProductVendor dtoObj = dtoProductVendorHashMap.get(o.getProduct_id());
//                    temp.setRank(o.getRank());
//                    temp.setVendor_id(dtoObj.getVendor_id());
////                    temp.setVendor(dtoObj.getVendor());
////                    temp.setOrder_date(o.getOrder_date());
////                    temp.setProduct_id(o.getProduct_id());
////                    temp.setProduct_name(dtoObj.getName());
////                    temp.setCategory_name(dtoObj.getCategoryName());
////                    temp.setTotal_qty(o.getTotal_qty());
//
//                    return temp;
//                }).collect(Collectors.toList());

        List<ReportProduct> result = new ArrayList<ReportProduct>();
        for (OrderDetailProduct o : orderDetailProductList) {
            ReportProduct temp = new ReportProduct();
            DTOProductVendor dtoObj = dtoProductVendorHashMap.get(o.getProduct_id());
            temp.setRank(o.getRank());
            temp.setVendor_id(dtoObj.getVendor_id());
            temp.setVendor(dtoObj.getVendor());
            temp.setOrder_date(o.getOrder_date());
            temp.setProduct_id(o.getProduct_id());
            temp.setProduct_name(dtoObj.getName());
            temp.setCategory_name(dtoObj.getCategoryName());
            temp.setTotal_qty(o.getTotal_qty());

            result.add(temp);
        }

        if(vendor_id == 0)
            return result;
        else
            return result.stream().filter(r -> r.getVendor_id() == vendor_id).collect(Collectors.toList());
    }

    private List<Product> mockProduct () {
        return Arrays.asList(
                new Product(1,
                        "Books",
                        "Crash Course in Python",
                        "Learn Python at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!",
                        14.99,
                        "assets/images/products/books/book-luv2code-1000.png",
                        true,
                        100,
                        "2020-10-25T10:24:14.000+00:00",
                        "2020-10-25T10:24:14.000+00:00",
                        1),
                new Product(2,
                        "Books",
                        "Become a Guru in JavaScript",
                        "Learn JavaScript at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!",
                        20.99,
                        "assets/images/products/books/book-luv2code-1000.pnassets/images/products/books/book-luv2code-1001.png",
                        true,
                        100,
                        "2020-10-25T10:24:14.000+00:00",
                        "2020-10-25T10:24:14.000+00:00",
                        1),
                new Product(3,
                        "Books",
                        "Become a pro .Net Developer",
                        "Learn .net in an easy way",
                        9.99,
                        "assets/images/products/books/book-luv2code-1000.pnassets/images/products/books/book-luv2code-1001.png",
                        true,
                        10,
                        "2020-10-25T10:24:14.000+00:00",
                        "2020-10-25T10:24:14.000+00:00",
                        2)
        );
    }

    private List<User> mockUser() {
        return Arrays.asList(
                new User(1,
                        "admin",
                        "$16$P.ON3vlP/hvnsScxLMwRCeMvqLRcOluaDqhO8b/cA1u5E6TUDAuPO",
                        "Peter",
                        "John",
                        "admin@miu.edu",
                        "(800) 369-6480",
                        "1000",
                        "N 4th street",
                        "Fairfield",
                        "IA",
                        "52557",
                        "US",
                        Arrays.asList(new Role(1)),
                        Arrays.asList(new Card("999999999", "Master", "2022-10-10T00:00:00.000+00:00", true))
                        ),
                new User(2,
                        "mary",
                        "$2a$16$P.ON3vlP/hvnsScxLMwRCeMvqLRcOluaDqhO8b/cA1u5E6TUDAuPO",
                        "Marry",
                        "Chan",
                        "marry@miu.edu",
                        "(807) 654-6180",
                        "1000",
                        "N 4th street",
                        "Fairfield",
                        "IA",
                        "52557",
                        "US",
                        Arrays.asList(new Role(2)),
                        Arrays.asList(new Card("888888888", "Visa", "2022-10-10T00:00:00.000+00:00", true))
                )
        );
    }

}
