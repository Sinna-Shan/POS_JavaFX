package com.example.final_coursework.dto.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderItemTM extends RecursiveTreeObject<OrderItemTM> {
   String item_code;
   String desc;
   int qty;
   double u_price;
   String date;
   double discount;
   String type;
   String size;
   double amount;
}
