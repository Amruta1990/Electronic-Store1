package com.happytech.electronicstore.service;


import com.happytech.electronicstore.Service.UserService;
import com.happytech.electronicstore.dtos.PagableResponse;
import com.happytech.electronicstore.dtos.UserDto;
import com.happytech.electronicstore.entity.User;
import com.happytech.electronicstore.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



    @SpringBootTest
    public class UserServiceImplTest {


        @MockBean
        private UserRepository userRepo;


        @Autowired
        private UserService userService;

        @Autowired
        private ModelMapper modelMapper;
        User user;

        User user1;

        User user2;

        List<User> users;

        UserDto userDto;


        @BeforeEach
        public void init() {

            userDto = UserDto.builder()
                    .name("Ashwini A patil ")
                    .email("patilashu@gmail.com")
                    .about("This is testing create method")
                    .gender("Female")
                    .imageName("abc.png")
                    .password("abcdefgh")
                    .build();


            user = User.builder()
                    .name("Ashwini")
                    .email("patilashu@gmail.com")
                    .about("This is testing create method")
                    .gender("Female")
                    .imageName("abc.png")
                    .password("abcdefgh")
                    .build();

            user1 = User.builder()
                    .name("Isha")
                    .email("patilashu@gmail.com")
                    .about("This is testing create method")
                    .gender("Female")
                    .imageName("abc.png")
                    .password("abcdefgh")
                    .build();
            user2 = User.builder()
                    .name("Amruta")
                    .email("patilashu@gmail.com")
                    .about("This is testing create method")
                    .gender("Female")
                    .imageName("abc.png")
                    .password("abcdefgh")
                    .build();

            users = new ArrayList<>();
            users.add(user);
            users.add(user1);
            users.add(user2);
        }

        //create user test
        @Test
        public void createUserTest() {

            //arrange
            Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);

            //act
            UserDto user1 = userService.createUser(modelMapper.map(user, UserDto.class));

            System.out.println(user1.getName());

            //assert
            Assertions.assertNotNull(user1);
            Assertions.assertEquals("Ashwini", user1.getName());
        }


        //update user test
        @Test
        public void updateUserTest() {

            Long userId = 1l;

            //arrange
            Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
            Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);

            //act
            UserDto updatedUser = userService.updateUser(userDto, userId);

            // UserDto updatedUser = modelMapper.map(user, UserDto.class);
            System.out.println(updatedUser.getName());

            //assert
            Assertions.assertNotNull(updatedUser);
            Assertions.assertEquals(userDto.getName(), updatedUser.getName(), "Name is not Validate");
            //  Assertions.assertThrows(UserNotFoundException.class,() -> userService.updateUser(userDto,11l));

            //multiple assertion are valid

        }

        //delete user test case
  /*  @Test
    public void deleteUserTest() {

        Long userId = 1l;

        //arrange
        Mockito.when(userRepo.findById(1l)).thenReturn(Optional.of(user));

        //act
        userService.deleteUser(userId);

        //assert
        Mockito.verify(userRepo, Mockito.times(1)).delete(user);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(11l));

    }*/

        //get All user test case
        @Test
        public void getAllUserTest() {

            Page<User> page = new PageImpl<>(users);

            //arrange
            Mockito.when(userRepo.findAll((Pageable) Mockito.any())).thenReturn(page);

            //Sort sort = Sort.by("name").ascending();
            //Pageable pageable = PageRequest.of(1,2,sort);
            //act
            PagableResponse<UserDto> allUser = userService.getAllUser(1, 2, "name", "asc");

            //assertion
            Assertions.assertEquals(3, allUser.getContent().size());
        }


        @Test
        public void getSingleUser() {

            Long userId = 11l;

            //arrange
            Mockito.when(userRepo.findById(userId)).thenReturn(Optional.of(user));

            //act
            UserDto singleUser = userService.getSingleById(userId);

            //assertion
            Assertions.assertNotNull(singleUser);

            Assertions.assertEquals(user.getUserId(), singleUser.getUserId(), "User id not match..!!");
        }

        @Test
        public void getUserByEmail() {

            String emailId = "amrutamnikam@gmail.com";
            //arrange
            Mockito.when(userRepo.findByEmail(emailId)).thenReturn(Optional.of(user));
            //act
            UserDto userByEmail = userService.getUserByEmail(emailId);
            //assertion
            Assertions.assertNotNull(userByEmail);
            Assertions.assertEquals(user.getEmail(), userByEmail.getEmail(), "Email not match...!!");
        }

        @Test
        public void searchUser() {


            String keywords = "Amruta";

            //arrange
            Mockito.when(userRepo.findByNameContaining(keywords)).thenReturn(users);
            //act
            List<UserDto> userDtos = userService.searchUser(keywords);
            //assertion
            Assertions.assertEquals(3, userDtos.size(), "size not match...!!");


        }
    }







