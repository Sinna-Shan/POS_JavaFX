package com.example.final_coursework.entity;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class OrderDetail {
    String itmCode;
    String ordId;
    int qty;
    double unitPrice;
    double discount;
    double total;


}
