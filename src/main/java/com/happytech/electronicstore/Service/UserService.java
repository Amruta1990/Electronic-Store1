package com.happytech.electronicstore.Service;

import com.happytech.electronicstore.dtos.PagableResponse;
import com.happytech.electronicstore.dtos.UserDto;
import com.happytech.electronicstore.entity.User;

import java.util.List;

public interface UserService {
      //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto,Long userId);

    //delete

    void deleteUser(Long userId);

    //get all users

    PagableResponse<UserDto> getAllUser(Integer pagenumber, Integer pagesize, String sortBy, String sortDir);

    //get all userId

    UserDto getUserById(Long userId);
     // get single id
    UserDto getSingleById(Long userId);

    UserDto getUserByEmail(String email);

    List<UserDto> searchUser(String keyword);



}
