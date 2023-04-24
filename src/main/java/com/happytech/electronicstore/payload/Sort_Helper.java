package com.happytech.electronicstore.payload;

import com.happytech.electronicstore.dtos.PagableResponse;
import com.happytech.electronicstore.dtos.UserDto;
import com.happytech.electronicstore.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Sort_Helper {
    public static  <U,V> PagableResponse<V> getPageableresponse(Page<U> page, Class<V> type) {

        List<U> users = page.getContent();
        List<V> userDtoList = users.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());

        PagableResponse<V> pagableResponse = new PagableResponse();
        pagableResponse.setContent(userDtoList);
        pagableResponse.setPageNumber(page.getNumber());
        pagableResponse.setPageSize(page.getSize());
        pagableResponse.setLastPage(page.isLast());
        pagableResponse.setTotalPages(page.getTotalPages());
        return pagableResponse;


    }


}
