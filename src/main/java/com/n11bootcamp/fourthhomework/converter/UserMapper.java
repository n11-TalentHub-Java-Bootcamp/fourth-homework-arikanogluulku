package com.n11bootcamp.fourthhomework.converter;

import com.n11bootcamp.fourthhomework.dto.UserDto;
import com.n11bootcamp.fourthhomework.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User convertToUser(UserDto userDto);

    UserDto convertToUserDto(User user);

    List<UserDto> convertAllToUserDto(List<User> users);
}
