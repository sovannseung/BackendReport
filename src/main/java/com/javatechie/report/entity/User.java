package com.javatechie.report.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private boolean enable;
    private String houseNumber;
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    private List<Role> roles;
    private List<Card> cards;
}
