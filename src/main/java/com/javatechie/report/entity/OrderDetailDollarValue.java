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
@Data
@Getter
@Entity
@Table(name = "vorderdetaildollarvalue")
public class OrderDetailDollarValue {
    @Id
    private int rank;
    private Timestamp order_date;
    private int product_id;
    private int numberof_order;
    private double total;
}
