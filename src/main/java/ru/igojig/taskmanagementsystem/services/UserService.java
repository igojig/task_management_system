package ru.igojig.taskmanagementsystem.services;

import ru.igojig.taskmanagementsystem.entities.User;

public interface UserService {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    User create(User user);
}
