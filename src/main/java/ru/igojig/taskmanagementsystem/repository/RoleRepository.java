package ru.igojig.taskmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.igojig.taskmanagementsystem.entities.Role;
import ru.igojig.taskmanagementsystem.enums.UserRole;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
   Optional<Role> findByRole(UserRole role);
}
