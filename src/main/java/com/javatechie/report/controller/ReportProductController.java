package com.javatechie.report.controller;

import com.javatechie.report.Config.MyProperties;
import com.javatechie.report.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReportProductController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MyProperties myProperties;

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
        // For example: https://backend-report.herokuapp.com/api/getAllOrderDetailProduct
        List<OrderDetailProduct> orderDetailProductList  = Arrays.asList(restTemplate.getForObject(myProperties.getConfigValue("url.orderdetail") + "first-report", OrderDetailProduct[].class));

        //****** 2. Mock up Product. Sometime we can filter by vendor_id
        // For example: https://pm-user-service-v2.herokuapp.com/api/user/list
        ProductContent productContent = restTemplate.getForObject(myProperties.getConfigValue("url.product") + "products/?size=9999", ProductContent.class);
        List<Product> productList = productContent.getContent();
        //List<Product> productList = mockProduct();

        //****** 3. Mock up User object for vendor
        // For example: https://product-service.herokuapp.com/api/product
        List<User> userList = Arrays.asList(restTemplate.getForObject(myProperties.getConfigValue("url.user") + "users", User[].class));

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
            dto.setCategoryName(p.getCategory().getCategoryName());
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

    public List<Product> mockProduct () {
        return Arrays.asList(
                new Product(13,
                        new Category(1, "Books"),
                        "Crash Course in Python",
                        "Learn Python at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!",
                        14.99,
                        "assets/images/products/books/book-luv2code-1000.png",
                        true,
                        100,
                        "2020-10-25T10:24:14.000+00:00",
                        "2020-10-25T10:24:14.000+00:00",
                        1),
                new Product(14,
                        new Category(1, "Books"),
                        "Become a Guru in JavaScript",
                        "Learn JavaScript at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!",
                        20.99,
                        "assets/images/products/books/book-luv2code-1000.pnassets/images/products/books/book-luv2code-1001.png",
                        true,
                        100,
                        "2020-10-25T10:24:14.000+00:00",
                        "2020-10-25T10:24:14.000+00:00",
                        2),
                new Product(3,
                        new Category(1, "Books"),
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

    public List<User> mockUser() {
        return Arrays.asList(
                new User(1,
                        "admin",
                        "Peter",
                        "John",
                        "admin@miu.edu",
                        "(800) 369-6480",
                        true,
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
                        "Marry",
                        "Chan",
                        "marry@miu.edu",
                        "(807) 654-6180",
                        true,
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
