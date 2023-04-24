package com.happytech.electronicstore.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto extends BaseEntityDto {
    private long productId;
@NotBlank
    private String title;
@NotBlank
    private String description;

    private double price;
    private String discountedPrice;
    private int quantity;
    //private Date addedDate;
    private boolean live;
    private boolean stock;
}
