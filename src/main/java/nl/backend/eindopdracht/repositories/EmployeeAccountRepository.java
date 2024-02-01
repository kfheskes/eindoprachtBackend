package nl.backend.eindopdracht.repositories;

import nl.backend.eindopdracht.models.EmployeeAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeAccountRepository extends JpaRepository<EmployeeAccount, Long> {

}
