package com.happytech.electronicstore.dtos;

import com.happytech.electronicstore.validate.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collector;

@Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
public class UserDto extends BaseEntityDto {


        private Long userId;
        @Size(min =3, max = 15, message = "Invalid Name !!")
        private String name;
        @Pattern(regexp ="^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&+=])(?=\\S+$).{8,}$",message = "Invalid User Email !!")
        @NotBlank(message = "Email is required !!")
        private String email;

       @NotBlank(message = "Password is requird !!")
        private String password;

         @Size(min = 4, max = 6,message = "Invalid gender !!")
        private String gender;
         @NotBlank(message ="write something about yourself !!")
        private  String about;
          @ImageNameValid
        private String imageName;



}


