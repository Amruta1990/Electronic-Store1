package com.happytech.electronicstore.controller;

import com.happytech.electronicstore.Service.FileService;
import com.happytech.electronicstore.Service.UserService;
import com.happytech.electronicstore.dtos.ImageResponse;
import com.happytech.electronicstore.dtos.PagableResponse;
import com.happytech.electronicstore.dtos.UserDto;
import com.happytech.electronicstore.payload.ApiResponseMessage;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {


@Autowired
    private UserService userService;

@Autowired
private FileService fileService;
@Value("${user.profile.image.path}")
private String imageuploadpath;



    //create
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

        log.info("Initialing Dao call  for save user");
        UserDto user = userService.createUser(userDto);

        log.info("completed Dao call  for save user");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUsers(@PathVariable("userId") Long userId,@Valid @RequestBody UserDto userDto)
    {

        log.info("Entering the request for update the user data with userId {}:",userId);
        UserDto userDto1 = userService.updateUser(userDto,userId);
        log.info("completed the request for update the user data with userId {}:",userId);
        return new ResponseEntity<>(userDto1,HttpStatus.OK);
    }
//delete
    @DeleteMapping("/{userId}")
public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable Long userId){
        log.info("Entering the request for delete the user data with userId {}:",userId);
        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage.builder().message("User is deleted successfully !!").success(true).status(HttpStatus.OK).build();
        log.info("completed the request for delete the user data with userId {}:",userId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    //get all
    @GetMapping("/")
    public ResponseEntity<PagableResponse> getAllUsers(@RequestParam (value = "pagenumber", defaultValue = "0", required = false)Integer pagenumber,
                                                       @RequestParam(value = "pagesize", defaultValue = "5", required = false)Integer pagesize,
                                                       @RequestParam(value = "sortBy", defaultValue = "userId", required = false) String sortBy,
                                                       @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){


        PagableResponse<UserDto> allUser = userService.getAllUser(pagenumber, pagesize, sortBy, sortDir);
        return new ResponseEntity<>(allUser,HttpStatus.OK);
    }
    @GetMapping("/{userId}")
   public ResponseEntity<UserDto> getSingleUser(@PathVariable Long userId){
        log.info("Initialing dao call for single user");

        UserDto single = userService.getSingleById(userId);
        log.info("completed Dao call  for single user");
        return  new ResponseEntity<>(single,HttpStatus.OK);
    }


    //get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){

        log.info("Initialing dao call for userbyemail",email);
        UserDto user = userService.getUserByEmail(email);
        log.info("completed Dao call  for userbyemail",email);
        return new ResponseEntity<>(user,HttpStatus.OK);


    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword){

        log.info("Initialing dao call for searchuser");
        List<UserDto> user = userService.searchUser(keyword);
        log.info("completed Dao call  for searchuser");
        return  new ResponseEntity<>(user,HttpStatus.OK);
    }


    //upload user image


    @PostMapping("/images/{userId}/")
    public ResponseEntity<ImageResponse> uploadImage(@PathVariable Long userId,@RequestParam("userimage") MultipartFile file) throws IOException, IOException {
        log.info("Upload the image with userid:{}",userId);
        UserDto user = this.userService.getSingleById(userId);

        String uploadImage = this.fileService.uploadImage(file, imageuploadpath);

        user.setImageName(uploadImage);
        UserDto userDto = this.userService.updateUser(user, userId);


        ImageResponse imageResponse=ImageResponse.builder().ImageName(uploadImage).message("Image Uploaded").status(HttpStatus.OK).success(true).build();
        log.info("Completed the upload image process",userId);
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    @GetMapping("/image/{userId}")
    public void  serveUserimage(@PathVariable Long userId, HttpServletResponse response) throws IOException {

        UserDto user = this.userService.getUserById(userId);
        InputStream resource = this.fileService.getResource(imageuploadpath, user.getImageName());
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }


    }


