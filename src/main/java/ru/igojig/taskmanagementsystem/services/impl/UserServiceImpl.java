package ru.igojig.taskmanagementsystem.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.igojig.taskmanagementsystem.entities.User;
import ru.igojig.taskmanagementsystem.exceptions.AppError;
import ru.igojig.taskmanagementsystem.repository.RoleRepository;
import ru.igojig.taskmanagementsystem.repository.UserRepository;
import ru.igojig.taskmanagementsystem.services.UserService;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new AppError("User with email: " + email + " not found"));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public User create(User user) {
       return userRepository.save(user);
    }
}
