package com.javatechie.report.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    private int id;
    private Category category;
    private String name;
    private String description;
    private Double unitPrice;
    private String imageUrl;
    private boolean active;
    private int unitsInStock;
    private String dateCreated;
    private String lastUpdated;
    private int vendorId;
}
