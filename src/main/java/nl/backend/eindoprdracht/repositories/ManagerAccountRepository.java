package nl.backend.eindoprdracht.repositories;

import nl.backend.eindoprdracht.models.ManagerAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerAccountRepository extends JpaRepository<ManagerAccount, Long> {
}
