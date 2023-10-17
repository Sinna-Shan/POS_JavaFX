package com.example.final_coursework.entity;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Supplier {
    String id;
    String title;
    String name;
    String number;
    String company;

    public Supplier(String id, String title, String name, String number, String company) {
        this.id = id;
        this.title = title;
        if(!name.equals("")) {
            this.name = name;
        }
        if(number.length() == 10 && number.charAt(0) == '0') {
            this.number = number;
        }
        if(!company.equals("")) {
            this.company = company;
        }

    }

}
