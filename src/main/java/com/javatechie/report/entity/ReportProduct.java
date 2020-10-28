package com.javatechie.report.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Setter
@Getter
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
    private Timestamp order_date;
    private int product_id;
    private String product_name;
    private String category_name;
    private int total_qty;
}
