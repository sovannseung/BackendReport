package com.javatechie.report.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Card {
    private String cardNumber;
    private String type;
    private String expireDate;
    private String defaults;
}
