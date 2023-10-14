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
    String custId;
}
