package com.happytech.electronicstore.dtos;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagableResponse <T> {
    private List<T> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElement;

    private Integer totalPages;
    private boolean lastPage;

}
