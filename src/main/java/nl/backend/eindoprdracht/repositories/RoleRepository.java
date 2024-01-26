package nl.backend.eindoprdracht.repositories;

import nl.backend.eindoprdracht.models.Role;

import nl.backend.eindoprdracht.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRolename(String rolename);
}
