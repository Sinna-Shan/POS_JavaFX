package com.example.final_coursework.dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class CartTm extends RecursiveTreeObject<CartTm> {
    String item_code;
    String description;
    int qty;
    double unitPrice;
    String date;
    double discount;
    String type;
    String size;
    double amount;
    JFXButton btnDelete;
}
