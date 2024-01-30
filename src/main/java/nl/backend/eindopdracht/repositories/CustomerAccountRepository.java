package nl.backend.eindopdracht.repositories;

import nl.backend.eindopdracht.models.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Long> {

}
