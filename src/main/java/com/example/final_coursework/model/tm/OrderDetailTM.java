package com.example.final_coursework.model.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailTM extends RecursiveTreeObject<OrderDetailTM> {
    String ord_id;
    String date;
    double total;
    String cus_name;
    String contact;
    String email;
    String emp;
    double arrears;
}
