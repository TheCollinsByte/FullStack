package com.addaboy.api.mappers;

import com.addaboy.api.dto.SignUpDto;
import com.addaboy.api.dto.UserDto;
import com.addaboy.api.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto userDto);
}
