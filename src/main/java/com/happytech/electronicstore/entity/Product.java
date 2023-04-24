package com.happytech.electronicstore.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@Builder
public class Product extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String title;
    @Column(length =  10000)
     private String description;
     private double price;
     private String discountedPrice;
     private int quantity;
     //private Date addedDate;
     private boolean live;
     private boolean stock;
}