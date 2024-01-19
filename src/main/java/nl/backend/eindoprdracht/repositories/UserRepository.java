package nl.backend.eindoprdracht.repositories;

import nl.backend.eindoprdracht.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
