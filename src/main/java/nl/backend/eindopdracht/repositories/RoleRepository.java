package nl.backend.eindopdracht.repositories;

import nl.backend.eindopdracht.models.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);

    Optional<Role> findByRoleNameIgnoreCase(String s);

    Optional<Role> findByRoleNameContainingIgnoreCase(String s);
}
