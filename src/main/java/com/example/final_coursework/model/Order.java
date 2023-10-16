package com.example.final_coursework.model;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Order {
    String ordId;
    String date;
    String empId;
    String custContact;
    double total;
    double arrears;

    public Order(String ordId, String date, String empId, String custContact, double total) {
        this.ordId = ordId;
        this.date = date;
        this.empId = empId;
        this.custContact = custContact;
        this.total = total;
    }
}
