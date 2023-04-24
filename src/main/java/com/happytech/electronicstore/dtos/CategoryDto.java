package com.happytech.electronicstore.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto extends BaseEntityDto {
    private Long categoryId;

    private String title;

    private String description;


    private String coverImage;


}
