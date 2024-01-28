package nl.backend.eindoprdracht.repositories;

import nl.backend.eindoprdracht.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yaml.snakeyaml.events.Event;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByUsername(String username);
    }

