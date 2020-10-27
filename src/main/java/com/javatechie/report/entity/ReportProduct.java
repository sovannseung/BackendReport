package com.javatechie.report.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name = "vreportbyproduct")
public class ReportProduct {
    @Id
    private int rank;
    private int vendor_id;
    private String vendor;
    private Date order_date;
    private int product_id;
    private String product_name;
    private String category_name;
    private int total_qty;
}
