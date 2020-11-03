package com.javatechie.report.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class ReportDollarValue {
    private int rank;
    private int vendor_id;
    private String vendor;
    private Timestamp order_date;
    private int number_of_order;
    private double total;

}