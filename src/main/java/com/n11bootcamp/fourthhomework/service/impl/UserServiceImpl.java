package com.n11bootcamp.fourthhomework.service.impl;

import com.n11bootcamp.fourthhomework.converter.UserMapper;
import com.n11bootcamp.fourthhomework.dao.UserRepository;
import com.n11bootcamp.fourthhomework.dto.UserDto;
import com.n11bootcamp.fourthhomework.entity.User;
import com.n11bootcamp.fourthhomework.exception.NotFoundException;
import com.n11bootcamp.fourthhomework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private  final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = UserMapper.INSTANCE.convertAllToUserDto(users);
        return userDtos;
    }

    @Override
    public UserDto getOneUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            UserDto userDto = UserMapper.INSTANCE.convertToUserDto(user.get());
            return userDto;
        }else{
           throw new NotFoundException(" User Id :" + userId + " User Not Found ! ");
        }
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.INSTANCE.convertToUser(userDto);
        userRepository.save(user);
        return UserMapper.INSTANCE.convertToUserDto(user);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        UserDto oneUser = getOneUser(userDto.getId());
        if(oneUser != null){
            User user = UserMapper.INSTANCE.convertToUser(userDto);
            userRepository.save(user);
            return UserMapper.INSTANCE.convertToUserDto(user);
        }else{
            return null;
        }
    }

    @Override
    public void deleteOneUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
