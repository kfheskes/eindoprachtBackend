package nl.backend.eindopdracht.repositories;




import nl.backend.eindopdracht.models.ManagerAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerAccountRepository extends JpaRepository<ManagerAccount, Long> {
}
