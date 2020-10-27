package com.javatechie.report.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
@Getter
@Table(name = "vorderdetailproduct")
public class OrderDetailProduct {
    @Id
    private int rank;
    private Date order_date;
    private int product_id;
    private int total_qty;
}
