package ru.igojig.taskmanagementsystem.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.igojig.taskmanagementsystem.dto.UserDto;
import ru.igojig.taskmanagementsystem.entities.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;


    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userDto.getPassword()))")
    public abstract User toEntity(UserDto userDto);

}
