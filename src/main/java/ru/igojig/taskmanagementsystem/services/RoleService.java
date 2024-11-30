package ru.igojig.taskmanagementsystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.igojig.taskmanagementsystem.entities.Role;
import ru.igojig.taskmanagementsystem.enums.UserRole;
import ru.igojig.taskmanagementsystem.exceptions.AppError;
import ru.igojig.taskmanagementsystem.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findByRole(UserRole role){
       return roleRepository.findByRole(role)
                .orElseThrow(()->new AppError("Role: " + role.name() + " not found"));
    }

}
