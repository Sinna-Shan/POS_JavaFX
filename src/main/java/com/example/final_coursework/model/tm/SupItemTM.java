package com.example.final_coursework.model.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SupItemTM extends RecursiveTreeObject<SupItemTM> {
    String itemCode;
    String description;
    int qty;
}
