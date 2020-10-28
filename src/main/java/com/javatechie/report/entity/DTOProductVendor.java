package com.javatechie.report.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DTOProductVendor {
    private int product_id;
    private String name;
    private String categoryName;
    private int vendor_id;
    private String vendor;
}
