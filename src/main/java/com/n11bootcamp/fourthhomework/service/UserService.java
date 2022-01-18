package com.n11bootcamp.fourthhomework.service;

import com.n11bootcamp.fourthhomework.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getOneUser(Long userId);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long userId, UserDto userDto);

    void deleteOneUser(Long userId);


}
