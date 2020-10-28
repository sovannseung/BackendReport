package com.javatechie.report.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Product {
    private int id;
    private String categoryName;
    private String name;
    private String description;
    private Double unitPrice;
    private String imageUrl;
    private boolean active;
    private String unitsInStock;
    private String dateCreated;
    private String lastUpdated;
    private int vendorId;
}
