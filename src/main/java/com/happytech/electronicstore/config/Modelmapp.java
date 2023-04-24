package com.happytech.electronicstore.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Modelmapp {
     @Bean
    public ModelMapper modelMapper(){


         return new ModelMapper();



    }
}
