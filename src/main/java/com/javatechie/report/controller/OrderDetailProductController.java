package com.javatechie.report.controller;

import com.javatechie.report.entity.OrderDetailProduct;
import com.javatechie.report.repository.OrderDetailProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderDetailProductController {

    @Autowired
    private OrderDetailProductRepository orderDetailProductRepository;

    @GetMapping("/api/getAllOrderDetailProduct")
    public List<OrderDetailProduct> getAllOrderDetailProduct() {
        return orderDetailProductRepository.findAll();
    }

}
