package com.example.final_coursework.entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Item extends RecursiveTreeObject<Item> {
    String itemCode;
    String supplierID;
    String description;
    int qty;
    double buyPrice;
    double sellPrice;
    String type;
    String size;
    double profit;

    public void setQty(int qty) {
        this.qty = qty>0 ? qty : 0;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice>0 ? buyPrice : 0;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice>0 ? sellPrice : 0;
    }

    public void setProfit(double profit) {
        this.profit = profit>0 ? profit : 0;
    }
}
