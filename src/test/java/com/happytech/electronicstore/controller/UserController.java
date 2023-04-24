package com.happytech.electronicstore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.happytech.electronicstore.BaseTest;
import com.happytech.electronicstore.Service.UserService;
import com.happytech.electronicstore.dtos.PagableResponse;
import com.happytech.electronicstore.dtos.UserDto;
import com.happytech.electronicstore.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class UserController extends BaseTest {
    @MockBean
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;
    User user;

    UserDto userDto,userDto1,userDto2;

    List<UserDto> userDtos;

    public void init() {

        user = User.builder()
                .name("Amruta")
                .about("Software Enginner")
                .email("amrutamnikam@gmail.com").gender("Female")
                .imageName("abc.png").password("aamu@123").build();

        userDto = UserDto.builder().about("My name")
                .name("Amruta Nikam")
                .imageName("xyz.png")
                .email("amrutamnikam@gmail.com")
                .password("123@aamunikam").build();

        userDto1 = UserDto.builder().about("My name")
                .name("Amruta Nikam")
                .imageName("xyz.png")
                .email("amrutamnikam@gmail.com")
                .password("123@aamunikam").build();

        userDto2 = UserDto.builder().about("My name")
                .name("Amruta Nikam")
                .imageName("xyz.png")
                .email("amrutamnikam@gmail.com")
                .password("123@aamunikam").build();

        userDtos = new ArrayList<>();
        userDtos.add(userDto);
        userDtos.add(userDto1);
        userDtos.add(userDto2);
    }
@Test
public void createUserTest() throws Exception {

        //user+post+json user data as json
        //json +status created

    UserDto dto = modelMapper.map(user, UserDto.class);
    //mocking


    Mockito.when(userService.createUser(Mockito.any())).thenReturn(dto);
//        Actual request for url

    this.mockMvc.perform(MockMvcRequestBuilders.post("/Api/Users/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(converobjectTojsonString(user))
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect((ResultMatcher) jsonPath("$.email").exists());


}

    @Test
    public void updateUserTest() throws Exception {
        UserDto dto = modelMapper.map(user, UserDto.class);
        Long id=1l;
        Mockito.when(userService.updateUser(Mockito.any(),Mockito.anyLong())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/Api/Users/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(converobjectTojsonString(user)).
                        accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.name").exists());
    }

    @Test
    public void getAllUserTest() throws Exception {
        PagableResponse<UserDto> pagableResponse=new PagableResponse<>();
        pagableResponse.setContent(userDtos);
        pagableResponse.setPageNumber(1);
        pagableResponse.setPageSize(3);
        pagableResponse.setTotalPages(2);

        Mockito.when(userService.getAllUser(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pagableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/Api/Users/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getUserTest() throws Exception {
        Long userid=2l;
        Mockito.when(userService.getSingleById(Mockito.anyLong())).thenReturn(userDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/Api/Users/getbyid/"+userid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

    }
    @Test
    public void getUserbyemailTest() throws Exception {
        String email="isha@gmail.com";
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(userDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/Api/Users/byemail/"+email)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());


    }
    @Test
    public void deleteUserTest() throws Exception {
        doNothing().when(userService).deleteUser(Mockito.<Long>any());

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/Api/Users/delete/"+1l)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }
    @Test
    public void searchuserTest() throws Exception {
        Mockito.when(userService.searchUser(Mockito.anyString())).thenReturn(userDtos);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/Api/Users/search/"+"isha")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());

    }

    private String converobjectTojsonString(User user) {
        try
        {
            return new ObjectMapper().writeValueAsString(user);
        } catch (RuntimeException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }



}

















