package com.javatechie.report.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String houseNumber;
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    private List<Role> roles;
    private List<Card> cards;
}
