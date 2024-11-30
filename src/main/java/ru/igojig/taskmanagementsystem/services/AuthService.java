package ru.igojig.taskmanagementsystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.igojig.taskmanagementsystem.dto.LoginRequest;
import ru.igojig.taskmanagementsystem.dto.UserDto;
import ru.igojig.taskmanagementsystem.entities.Role;
import ru.igojig.taskmanagementsystem.entities.User;
import ru.igojig.taskmanagementsystem.enums.UserRole;
import ru.igojig.taskmanagementsystem.exceptions.AppError;
import ru.igojig.taskmanagementsystem.mappers.UserMapper;
import ru.igojig.taskmanagementsystem.security.TokenService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final TokenService tokenService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    public String login(LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AppError("Bad credential");
        }
        String token = tokenService.generateAccessToken(user);
        return token;
    }

    public String register(UserDto userDto) {
        if (userService.existsByEmail(userDto.getEmail())) {
            throw new AppError("Email: " + userDto.getEmail() + " exists");
        }

        Role role = roleService.findByRole(UserRole.USER);
        User user = userMapper.toEntity(userDto);
        user.setRoles(List.of(role));

        user = userService.create(user);

        String token = tokenService.generateAccessToken(user);
        return token;
    }

}
