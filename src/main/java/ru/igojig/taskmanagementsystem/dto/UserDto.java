package ru.igojig.taskmanagementsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String name;
    private String email;
    private String password;
}
