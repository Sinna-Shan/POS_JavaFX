package com.example.final_coursework.model.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class SupplierTM extends RecursiveTreeObject<SupplierTM> {
    String id;
    String title;
    String name;
    String number;
    String company;
    JFXButton btnDelete;
}
