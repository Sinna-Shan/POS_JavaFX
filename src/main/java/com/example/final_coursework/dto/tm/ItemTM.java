package com.example.final_coursework.dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemTM extends RecursiveTreeObject<ItemTM> {
    String itemCode;
    String supplierID;
    String description;
    int qty;
    double buyPrice;
    double sellPrice;
    String type;
    String size;
    double profit;
    JFXButton btnDelete;
}
