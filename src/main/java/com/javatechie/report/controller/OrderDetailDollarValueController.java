package com.javatechie.report.controller;

import com.javatechie.report.entity.OrderDetailDollarValue;
import com.javatechie.report.entity.OrderDetailProduct;
import com.javatechie.report.repository.OrderDetailDollarValueRepository;
import com.javatechie.report.repository.OrderDetailProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderDetailDollarValueController {

    @Autowired
    private OrderDetailDollarValueRepository orderDetailDollarValueRepository;

    @GetMapping("/api/getAllOrderDetailDollarValue")
    public List<OrderDetailDollarValue> getAllOrderDetailDollarValue() {
        return orderDetailDollarValueRepository.findAll();
    }
}
