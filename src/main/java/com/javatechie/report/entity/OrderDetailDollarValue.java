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
@Getter
public class OrderDetailDollarValue {
    private Timestamp order_date;
    private int product_id;
    private int number_of_order;
    private double total;
}
