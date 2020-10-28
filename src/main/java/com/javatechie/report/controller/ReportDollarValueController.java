package com.javatechie.report.controller;

import com.javatechie.report.Config.MyProperties;
import com.javatechie.report.entity.*;
import com.javatechie.report.repository.ReportDollarValueRepository;
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
public class ReportDollarValueController {

    @Autowired
    private ReportDollarValueRepository reportDollarValueRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MyProperties myProperties;

    private ReportProductController obj = new ReportProductController();

    //@GetMapping("/monolith/getAllReportDollarValue")
    public List<ReportDollarValue> getAllReportDollarValueMono()  {
        return reportDollarValueRepository.findAll();
    }

    //@GetMapping("/monolith/getReportDollarValueByVendorId/{vendor_id}")
    public List<ReportDollarValue> getReportDollarValueByVendorIdMono(@PathVariable int vendor_id) {
        return reportDollarValueRepository.findReportDollarValueByVendorId(vendor_id);
    }

    @GetMapping("/api/getAllReportDollarValue")
    public List<ReportDollarValue> getAllReportDollarValue() {
        return reportDollarValues(0) ;
    }

    @GetMapping("/api/getAllReportDollarValue/{vendor_id}")
    public List<ReportDollarValue> getAllReportDollarValueByVendorId(@PathVariable int vendor_id) {
        return reportDollarValues(vendor_id);
    }

    private List<ReportDollarValue> reportDollarValues(int vendor_id)  {

        //****** 1. Get data from OrderDetailDollarValue by using RestTemplate
        List<OrderDetailDollarValue> orderDetailDollarValueList  = Arrays.asList(restTemplate.getForObject(myProperties.getConfigValue("url.orderdetail") + "getAllOrderDetailDollarValue", OrderDetailDollarValue[].class));

        //****** 2. Mock up Product. Sometime we can filter by vendor_id
        List<Product> productList = obj.mockProduct();

        //****** 3. Mock up User object for vendor
        List<User> userList = obj.mockUser();

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

        List<ReportDollarValue> result = new ArrayList<ReportDollarValue>();
        for (OrderDetailDollarValue o : orderDetailDollarValueList) {
            ReportDollarValue temp = new ReportDollarValue();
            DTOProductVendor dtoObj = dtoProductVendorHashMap.get(o.getProduct_id());
            temp.setRank(o.getRank());
            temp.setVendor_id(dtoObj.getVendor_id());
            temp.setVendor(dtoObj.getVendor());
            temp.setOrder_date(o.getOrder_date());
            temp.setNumberof_order(o.getNumberof_order());
            temp.setTotal(o.getTotal());

            result.add(temp);
        }

        if(vendor_id == 0)
            return result;
        else
            return result.stream().filter(r -> r.getVendor_id() == vendor_id).collect(Collectors.toList());
    }
}
