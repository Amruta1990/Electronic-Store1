package com.happytech.electronicstore.Service.Impl;

import com.happytech.electronicstore.Service.UserService;
import com.happytech.electronicstore.dtos.PagableResponse;
import com.happytech.electronicstore.dtos.UserDto;
import com.happytech.electronicstore.entity.User;
import com.happytech.electronicstore.exception.ResourceNotFoundException;
import com.happytech.electronicstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.management.modelmbean.ModelMBean;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        //generate unique id in string format
       // String userId = UUID.randomUUID().toString();
        //userDto.setUserId(userId);
        //dto->entity

        User user = dtoToEntity(userDto);
        User SaveUser = userRepository.save(user);

        //entity->dto
        UserDto newDto = entityToDto(SaveUser);
        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        User updateUser=userRepository.save(user);
        UserDto updateDto = entityToDto(updateUser);
        return updateDto;
    }

    @Override
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        userRepository.delete(user);

    }

    @Override
    public PagableResponse<UserDto> getAllUser(Integer pagenumber, Integer pagesize, String sortBy, String sortDir ) {


        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : (Sort.by(sortBy).descending());
        Pageable page= PageRequest.of(pagenumber,pagesize,sort);
        Page<User> all = userRepository.findAll(page);
        List<User> users = all.getContent();
        List<UserDto> dtoList=users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());

        PagableResponse pagableResponse=new PagableResponse();
        pagableResponse.setContent(dtoList);
        pagableResponse.setPageNumber(all.getNumber());
        pagableResponse.setPageSize(all.getSize());
        pagableResponse.setLastPage(all.isLast());
        pagableResponse.setTotalPages(all.getTotalPages());
        pagableResponse.setTotalElement(all.getTotalElements());
        return pagableResponse;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user=userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        return entityToDto(user);


    }

    @Override
    public UserDto getSingleById(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user id not found"));

        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
     User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not found"));
     return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {

        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> collect = users.stream().map(user -> this.entityToDto(user)).collect(Collectors.toList());
        return collect;
    }


    private UserDto entityToDto(User savedUser) {
//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .email(savedUser.getEmail())
//                .imageName(savedUser.getImageName())
//                .build();


        return mapper.map(savedUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
//       User user= User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .imageName(userDto.getImageName())
//               .build();
                return mapper.map(userDto,User.class);

    }
}
